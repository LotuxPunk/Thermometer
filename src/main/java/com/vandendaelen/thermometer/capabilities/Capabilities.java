package com.vandendaelen.thermometer.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class Capabilities {
    @CapabilityInject(IThermal.class)
    public static final Capability<IThermal> THERMAL = null;
}
