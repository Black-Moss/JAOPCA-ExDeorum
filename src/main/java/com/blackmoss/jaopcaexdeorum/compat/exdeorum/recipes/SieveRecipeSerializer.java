package com.blackmoss.jaopcaexdeorum.compat.exdeorum.recipes;

import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import thedarkcolour.exdeorum.recipe.sieve.SieveRecipe;
import thelm.jaopca.api.recipes.IRecipeSerializer;
import thelm.jaopca.utils.MiscHelper;

import java.util.Objects;

public class SieveRecipeSerializer implements IRecipeSerializer {
    public final ResourceLocation key;
    public final Object input;
    public final Object result;
    public final int resultCount;
    public final int rolls;
    public final float chance;
    public final Object mesh;

    public SieveRecipeSerializer(ResourceLocation key, Object input, Object result, int resultCount, int rolls, float chance, Object mesh) {
        this.key = Objects.requireNonNull(key);
        this.input = input;
        this.result = result;
        this.resultCount = resultCount;
        this.rolls = rolls;
        this.chance = chance;
        this.mesh = mesh;
    }

    @Override
    public JsonElement get() {
        return MiscHelper.INSTANCE.serializeRecipe(new SieveRecipe(
                resolveIngredient(input, "input"),
                resolveResult(),
                BinomialDistributionGenerator.binomial(rolls, chance),
                resolveIngredient(mesh, "mesh"),
                false));
    }

    protected ItemStack resolveResult() {
        ItemStack stack = MiscHelper.INSTANCE.getItemStack(result, resultCount);
        if (stack == null || stack.isEmpty()) {
            throw new IllegalArgumentException("Empty result in recipe " + key + ": " + result);
        }
        return stack;
    }

    protected Ingredient resolveIngredient(Object obj, String name) {
        Ingredient ingredient = MiscHelper.INSTANCE.getIngredient(obj);
        if (ingredient == null) {
            throw new IllegalArgumentException("Empty " + name + " in recipe " + key + ": " + obj);
        }
        return ingredient;
    }
}
