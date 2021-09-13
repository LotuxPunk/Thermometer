package com.vandendaelen.thermometer.data;

import com.vandendaelen.thermometer.items.Items;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;


import java.util.function.Consumer;

public class RecipeBuilder extends RecipeProvider {
    public RecipeBuilder(DataGenerator generatorIn) {
        super(generatorIn);
    }


    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ResourceLocation ID_THERMOMETER = new ResourceLocation("data_gen", "thermometer");

        ShapedRecipeBuilder
                .shaped(Items.THERMOMETER.get(), 1)
                .pattern(" G ")
                .pattern("GRG")
                .pattern(" G ")
                .define('G', Blocks.GLASS_PANE)
                .define('R', net.minecraft.world.item.Items.REDSTONE)
                .group("")
                .unlockedBy("has_dirt", has(Blocks.DIRT))
                .save(consumer);
    }
}
