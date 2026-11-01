package com.blackmoss.jaopcaexdeorum.compat.exdeorum.recipes;

import com.google.gson.JsonElement;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import thedarkcolour.exdeorum.recipe.OreChunkRecipe;
import thelm.jaopca.api.recipes.IRecipeSerializer;
import thelm.jaopca.utils.MiscHelper;

import java.util.Objects;

public record OreChunkRecipeSerializer(Identifier key, Object oreChunk, TagKey<Item> ore) implements IRecipeSerializer {
    public OreChunkRecipeSerializer(Identifier key, Object oreChunk, TagKey<Item> ore) {
        this.key = Objects.requireNonNull(key);
        this.oreChunk = oreChunk;
        this.ore = Objects.requireNonNull(ore);
    }

    @Override
    public JsonElement get() {
        Ingredient ingredient = MiscHelper.INSTANCE.getIngredient(oreChunk);
        if (ingredient == null) {
            throw new IllegalArgumentException("Empty ore chunk ingredient in recipe " + key + ": " + oreChunk);
        }
        return MiscHelper.INSTANCE.serializeRecipe(new OreChunkRecipe(ingredient, ore));
    }
}
