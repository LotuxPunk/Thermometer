package com.vandendaelen.thermometer.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class Capabilities {
    public static final Capability<IThermal> THERMAL = CapabilityManager.get(new CapabilityToken<>() {
    });
}
