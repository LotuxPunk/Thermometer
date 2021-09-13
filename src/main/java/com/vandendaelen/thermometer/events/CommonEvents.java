package com.vandendaelen.thermometer.events;

import com.vandendaelen.thermometer.Thermometer;
import com.vandendaelen.thermometer.capabilities.IThermal;
import com.vandendaelen.thermometer.capabilities.ThermalCapability;
import com.vandendaelen.thermometer.items.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Thermometer.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEvents {
    public static final ResourceLocation THERMAL_CAP = new ResourceLocation(Thermometer.MODID, "thermal");

    @SubscribeEvent
    public static void attachItemStackCap(AttachCapabilitiesEvent<ItemStack> event){
        if (event.getObject().getItem() == Items.THERMOMETER.get())
            event.addCapability(THERMAL_CAP, new IThermal.Provider(new ThermalCapability()));
    }
}
