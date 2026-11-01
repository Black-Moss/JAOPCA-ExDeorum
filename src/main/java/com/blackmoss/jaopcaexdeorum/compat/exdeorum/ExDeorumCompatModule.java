package com.blackmoss.jaopcaexdeorum.compat.exdeorum;

import com.blackmoss.jaopcaexdeorum.JAOPCAExDeorum;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.config.IDynamicSpecConfig;
import thelm.jaopca.api.forms.IForm;
import thelm.jaopca.api.forms.IFormRequest;
import thelm.jaopca.api.helpers.IMiscHelper;
import thelm.jaopca.api.items.IItemFormType;
import thelm.jaopca.api.items.IItemInfo;
import thelm.jaopca.api.materials.IMaterial;
import thelm.jaopca.api.materials.MaterialType;
import thelm.jaopca.api.modules.IModule;
import thelm.jaopca.api.modules.IModuleData;
import thelm.jaopca.api.modules.JAOPCAModule;
import thelm.jaopca.items.ItemFormType;
import thelm.jaopca.utils.ApiImpl;
import thelm.jaopca.utils.MiscHelper;

import java.util.*;

@JAOPCAModule(modDependencies = "exdeorum")
public class ExDeorumCompatModule implements IModule {
    public static final String FORM_NAME = "exdeorum_ore_chunk";

    private static final Set<String> BLACKLIST = new TreeSet<>(List.of(
            "aluminium",
            "aluminum",
            "amethyst",
            "boron",
            "coal",
            "cobalt",
            "copper",
            "diamond",
            "emerald",
            "gold",
            "iridium",
            "iron",
            "lapis",
            "lead",
            "lithium",
            "magnesium",
            "netherite_scrap",
            "nickel",
            "osmium",
            "platinum",
            "quartz",
            "silver",
            "thorium",
            "tin",
            "uranium",
            "zinc"));

    private static final Identifier COMPRESSED_GRAVEL = Identifier.parse("exdeorum:compressed/gravel");

    private static final List<MeshDrop> MESH_DROPS = List.of(
            new MeshDrop("string_mesh", "string", 0.04F),
            new MeshDrop("flint_mesh", "flint", 0.05F),
            new MeshDrop("iron_mesh", "iron", 0.06F),
            new MeshDrop("golden_mesh", "golden", 0.07F),
            new MeshDrop("diamond_mesh", "diamond", 0.08F),
            new MeshDrop("netherite_mesh", "netherite", 0.09F));
    private static final Set<String> EXTRA_BLACKLIST = new TreeSet<>();
    private static boolean addSieveRecipes = true;
    private static boolean addCompressedSieveRecipes = true;
    private static double sieveChanceScale = 1.0;
    private static IDynamicSpecConfig moduleConfig;
    private final IForm oreChunkForm = ApiImpl.INSTANCE.newForm(this, FORM_NAME, ItemFormType.INSTANCE).
            setMaterialTypes(MaterialType.INGOT).
            setSecondaryName("exdeorum:ore_chunks").
            setDefaultMaterialBlacklist(BLACKLIST);

    private static void readModuleConfig(IDynamicSpecConfig config) {
        addSieveRecipes = config.getDefinedBoolean("recipes.sieve_recipes", addSieveRecipes,
                "Should sieve recipes dropping Ex Deorum ore chunks from sieving gravel be added.");
        addCompressedSieveRecipes = config.getDefinedBoolean("recipes.compressed_sieve_recipes", addCompressedSieveRecipes,
                "Should compressed sieve recipes dropping Ex Deorum ore chunks be added.");
        sieveChanceScale = config.getDefinedDouble("recipes.sieve_chance_scale", sieveChanceScale, 0.0, 1.0,
                "Scales the chance of Ex Deorum ore chunks dropping from sieves.");
        List<String> extraBlacklist = config.getDefinedStringList("recipes.material_blacklist", new ArrayList<>(),
                MiscHelper.INSTANCE.configMaterialPredicate(),
                "Extra materials that should not get Ex Deorum ore chunks.");
        EXTRA_BLACKLIST.clear();
        MiscHelper.INSTANCE.caclulateMaterialSet(extraBlacklist, EXTRA_BLACKLIST);
    }

    private static boolean isSkipped(IMaterial material) {
        return matches(material, EXTRA_BLACKLIST) || matches(material, BLACKLIST);
    }

    private static boolean matches(IMaterial material, Set<String> names) {
        if (names.contains(material.getName())) {
            return true;
        }
        for (String alternativeName : material.getAlternativeNames()) {
            if (names.contains(alternativeName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getName() {
        return "exdeorum";
    }

    @Override
    public List<IFormRequest> getFormRequests() {
        return List.of(oreChunkForm.toRequest());
    }

    @Override
    public Set<MaterialType> getMaterialTypes() {
        return EnumSet.of(MaterialType.INGOT);
    }

    @Override
    public Set<String> getDefaultMaterialBlacklist() {
        return BLACKLIST;
    }

    @Override
    public void defineModuleConfig(IModuleData moduleData, IDynamicSpecConfig config) {
        moduleConfig = Objects.requireNonNull(config);
        readModuleConfig(config);
    }

    @Override
    public void onCommonSetup(IModuleData moduleData, FMLCommonSetupEvent event) {
        if (moduleConfig != null) {
            readModuleConfig(moduleConfig);
        }
        JAOPCAApi api = ApiImpl.INSTANCE;
        ExDeorumHelper helper = ExDeorumHelper.INSTANCE;
        IMiscHelper miscHelper = MiscHelper.INSTANCE;
        IItemFormType itemFormType = ItemFormType.INSTANCE;
        Set<Identifier> itemTags = api.getItemTags();
        int chunkCount = 0;
        int recipeCount = 0;
        for (IMaterial material : oreChunkForm.getMaterials()) {
            if (isSkipped(material)) {
                continue;
            }
            IItemInfo chunkInfo = itemFormType.getMaterialFormInfo(oreChunkForm, material);
            if (chunkInfo == null) {
                continue;
            }
            Identifier oreLocation = miscHelper.getTagLocation("ores", material.getName());
            Identifier rawMaterialLocation = miscHelper.getTagLocation("raw_materials", material.getName());
            Identifier ingotLocation = miscHelper.getTagLocation(material.getType().getFormName(), material.getName());
            if (!itemTags.contains(oreLocation) || !itemTags.contains(rawMaterialLocation) || !itemTags.contains(ingotLocation)) {
                JAOPCAExDeorum.LOGGER.warn(
                        "Skipping Ex Deorum ore chunk for material {} since it lacks the ore tag {}, raw ore tag {} or ingot tag {}",
                        material.getName(), oreLocation, rawMaterialLocation, ingotLocation);
                continue;
            }
            ++chunkCount;
            if (api.registerShapedRecipe(
                    Identifier.fromNamespaceAndPath("exdeorum", material.getName() + "_from_chunks"),
                    CraftingBookCategory.BUILDING,
                    oreLocation, 1,
                    "CC",
                    "CC",
                    'C', chunkInfo)) {
                ++recipeCount;
            }
            if (addSieveRecipes) {
                for (MeshDrop meshDrop : MESH_DROPS) {
                    Item mesh = BuiltInRegistries.ITEM.getValue(Identifier.fromNamespaceAndPath("exdeorum", meshDrop.meshName()));
                    if (mesh == Items.AIR) {
                        continue;
                    }
                    float chance = (float) Math.clamp(meshDrop.chance() * sieveChanceScale, 0.0, 1.0);
                    if (helper.registerSieveRecipe(
                            Identifier.fromNamespaceAndPath("exdeorum",
                                    "sieve/gravel/" + meshDrop.folderName() + '/' + material.getName() + "_ore_chunk"),
                            Items.GRAVEL, chunkInfo, 1, 1, chance, mesh)) {
                        ++recipeCount;
                    }
                    if (addCompressedSieveRecipes && helper.registerCompressedSieveRecipe(
                            Identifier.fromNamespaceAndPath("exdeorum",
                                    "compressed_sieve/gravel/" + meshDrop.folderName() + '/' + material.getName() + "_ore_chunk"),
                            COMPRESSED_GRAVEL, chunkInfo, 1, 7, chance, mesh)) {
                        ++recipeCount;
                    }
                }
            }
        }
        JAOPCAExDeorum.LOGGER.info("Registered Ex Deorum ore chunks for {} materials with {} recipes", chunkCount, recipeCount);
    }

    private record MeshDrop(String meshName, String folderName, float chance) {
    }
}
