package com.vandendaelen.thermometer;

import com.vandendaelen.thermometer.capabilities.IThermal;
import com.vandendaelen.thermometer.capabilities.ThermalCapability;
import com.vandendaelen.thermometer.data.RecipeBuilder;
import com.vandendaelen.thermometer.items.Items;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Thermometer.MODID)
public class Thermometer {
    public static final String MODID = "thermometer";

    public Thermometer() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onGatherData);

        Items.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(IThermal.class, new IThermal.Storage(), ThermalCapability::new);
    }

    private void onGatherData(GatherDataEvent e) {
        DataGenerator generator = e.getGenerator();
        generator.addProvider(new RecipeBuilder(generator));
    }
}
