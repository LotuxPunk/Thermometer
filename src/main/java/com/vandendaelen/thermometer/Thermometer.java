package com.vandendaelen.thermometer;

import com.vandendaelen.thermometer.capabilities.IThermal;
import com.vandendaelen.thermometer.data.RecipeBuilder;
import com.vandendaelen.thermometer.items.Items;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod(Thermometer.MODID)
public class Thermometer {
    public static final String MODID = "thermometer";

    public Thermometer() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onRegisterCapabilities);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onGatherData);

        Items.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void onRegisterCapabilities(final RegisterCapabilitiesEvent event) {
        event.register(IThermal.class );
    }

    private void onGatherData(GatherDataEvent e) {
        DataGenerator generator = e.getGenerator();
        generator.addProvider(new RecipeBuilder(generator));
    }
}
