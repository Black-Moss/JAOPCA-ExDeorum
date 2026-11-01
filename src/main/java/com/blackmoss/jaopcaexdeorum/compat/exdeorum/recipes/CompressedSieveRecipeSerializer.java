package com.blackmoss.jaopcaexdeorum.compat.exdeorum.recipes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import thedarkcolour.exdeorum.recipe.sieve.CompressedSieveRecipe;
import thelm.jaopca.utils.MiscHelper;

public class CompressedSieveRecipeSerializer extends SieveRecipeSerializer {
    public CompressedSieveRecipeSerializer(ResourceLocation key, Object input, Object result, int resultCount, int rolls, float chance, Object mesh) {
        super(key, input, result, resultCount, rolls, chance, mesh);
    }

    @Override
    public com.google.gson.JsonElement get() {
        return MiscHelper.INSTANCE.serializeRecipe(new CompressedSieveRecipe(
                resolveIngredient(input, "input"),
                resolveResult(),
                BinomialDistributionGenerator.binomial(rolls, chance),
                resolveIngredient(mesh, "mesh"),
                false));
    }
}
