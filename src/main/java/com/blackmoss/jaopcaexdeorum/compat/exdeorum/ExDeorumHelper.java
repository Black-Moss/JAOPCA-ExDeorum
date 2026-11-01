package com.blackmoss.jaopcaexdeorum.compat.exdeorum;

import com.blackmoss.jaopcaexdeorum.compat.exdeorum.recipes.CompressedSieveRecipeSerializer;
import com.blackmoss.jaopcaexdeorum.compat.exdeorum.recipes.SieveRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import thelm.jaopca.utils.ApiImpl;

public class ExDeorumHelper {
    public static final ExDeorumHelper INSTANCE = new ExDeorumHelper();

    private ExDeorumHelper() {
    }

    public boolean registerSieveRecipe(ResourceLocation key, Object input, Object result, int resultCount, int rolls, float chance, Object mesh) {
        return ApiImpl.INSTANCE.registerRecipe(key, new SieveRecipeSerializer(key, input, result, resultCount, rolls, chance, mesh));
    }

    public boolean registerCompressedSieveRecipe(ResourceLocation key, Object input, Object result, int resultCount, int rolls, float chance, Object mesh) {
        return ApiImpl.INSTANCE.registerRecipe(key, new CompressedSieveRecipeSerializer(key, input, result, resultCount, rolls, chance, mesh));
    }
}
