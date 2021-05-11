package com.vandendaelen.thermometer.events;

import com.vandendaelen.thermometer.Thermometer;
import com.vandendaelen.thermometer.capabilities.Capabilities;
import com.vandendaelen.thermometer.capabilities.IThermal;
import com.vandendaelen.thermometer.items.Items;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Thermometer.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {
    @SubscribeEvent
    public static void register(FMLClientSetupEvent event) {
        ItemModelsProperties.registerProperty(Items.THERMOMETER.get(), new ResourceLocation("thermal"), (stack, world, entity) -> {
            IThermal cap = stack.getCapability(Capabilities.THERMAL).orElse(null);
            if (cap == null){
                return 0f;
            }
            return (float) cap.getThermalLevel().ordinal();
        });
    }
}
