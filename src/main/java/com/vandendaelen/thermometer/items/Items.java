package com.vandendaelen.thermometer.items;

import com.vandendaelen.thermometer.Thermometer;
import net.minecraft.world.item.Item;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Items {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Thermometer.MODID);

    public static final RegistryObject<Item> THERMOMETER = ITEMS.register("thermometer", ThermometerItem::new);
}
