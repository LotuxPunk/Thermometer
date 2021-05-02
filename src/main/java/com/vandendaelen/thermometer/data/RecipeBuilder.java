package com.vandendaelen.thermometer.data;

import com.vandendaelen.thermometer.items.Items;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;

public class RecipeBuilder extends RecipeProvider {
    public RecipeBuilder(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ResourceLocation ID_THERMOMETER = new ResourceLocation("data_gen", "thermometer");

        ShapedRecipeBuilder
                .shapedRecipe(Items.THERMOMETER.get(), 1)
                .patternLine(" G ")
                .patternLine("GRG")
                .patternLine(" G ")
                .key('G', Blocks.GLASS_PANE)
                .key('R', net.minecraft.item.Items.REDSTONE)
                .setGroup("")
                .addCriterion("has_dirt", hasItem(Blocks.DIRT))
                .build(consumer);
    }
}
