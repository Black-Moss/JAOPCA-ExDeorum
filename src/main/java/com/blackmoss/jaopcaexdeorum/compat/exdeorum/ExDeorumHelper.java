package com.blackmoss.jaopcaexdeorum.compat.exdeorum;

import com.blackmoss.jaopcaexdeorum.compat.exdeorum.recipes.CompressedSieveRecipeSerializer;
import com.blackmoss.jaopcaexdeorum.compat.exdeorum.recipes.OreChunkRecipeSerializer;
import com.blackmoss.jaopcaexdeorum.compat.exdeorum.recipes.SieveRecipeSerializer;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import thelm.jaopca.utils.ApiImpl;

public class ExDeorumHelper {
    public static final ExDeorumHelper INSTANCE = new ExDeorumHelper();

    private ExDeorumHelper() {
    }

    public boolean registerOreChunkRecipe(Identifier key, Object oreChunk, TagKey<Item> ore) {
        return ApiImpl.INSTANCE.registerRecipe(key, new OreChunkRecipeSerializer(key, oreChunk, ore));
    }

    public boolean registerSieveRecipe(Identifier key, Object input, Object result, int resultCount, int rolls, float chance, Object mesh) {
        return ApiImpl.INSTANCE.registerRecipe(key, new SieveRecipeSerializer(key, input, result, resultCount, rolls, chance, mesh));
    }

    public boolean registerCompressedSieveRecipe(Identifier key, Object input, Object result, int resultCount, int rolls, float chance, Object mesh) {
        return ApiImpl.INSTANCE.registerRecipe(key, new CompressedSieveRecipeSerializer(key, input, result, resultCount, rolls, chance, mesh));
    }
}
