package com.blackmoss.jaopcaexdeorum.compat.exdeorum.recipes;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import thelm.jaopca.utils.MiscHelper;
import thedarkcolour.exdeorum.recipe.sieve.CompressedSieveRecipe;

public class CompressedSieveRecipeSerializer extends SieveRecipeSerializer {
    public CompressedSieveRecipeSerializer(Identifier key, Object input, Object result, int resultCount, int rolls, float chance, Object mesh) {
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
