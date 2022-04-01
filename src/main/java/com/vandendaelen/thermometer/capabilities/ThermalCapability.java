package com.vandendaelen.thermometer.capabilities;

import com.vandendaelen.thermometer.misc.ThermalLevels;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class ThermalCapability implements IThermal {
    private ThermalLevels thermal = ThermalLevels.TEMPERATE;
    private float temperature = 0F;

    public ThermalCapability() {
    }

    @Override
    public void tick(Player entity) {
        if (entity instanceof ServerPlayer){
            temperature = ((ServerPlayer) entity).getLevel().getBiome(entity.getOnPos()).value().getHeightAdjustedTemperature(entity.getOnPos());
            thermal = ThermalLevels.from(temperature);
        }
    }

    @Override
    public ThermalLevels getThermalLevel() {
        return this.thermal;
    }

    @Override
    public float getTemperature() {
        return this.temperature;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("thermal", (float)thermal.ordinal());
        tag.putFloat("temperature", getTemperature());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        thermal = ThermalLevels.values()[(int)nbt.getFloat("thermal")];
        temperature = nbt.getFloat("temperature");
    }
}
