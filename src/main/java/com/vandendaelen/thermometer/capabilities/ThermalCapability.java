package com.vandendaelen.thermometer.capabilities;

import com.vandendaelen.thermometer.misc.ThermalLevels;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;

public class ThermalCapability implements IThermal {
    private ThermalLevels thermal = ThermalLevels.TEMPERATE;
    private float temperature = 0F;

    public ThermalCapability() {
    }

    @Override
    public void tick(PlayerEntity entity) {
        if (entity instanceof ServerPlayerEntity){
            temperature = ((ServerPlayerEntity) entity).getServerWorld().getBiome(entity.getPosition()).getTemperature(entity.getPosition());
            thermal = ThermalLevels.from(temperature);
        }
    }

    @Override
    public ThermalLevels getThermalLevel() {
        return this.thermal;
    }

    @Override
    public int getTemperature() {
        return 0;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putFloat("thermal", (float)thermal.ordinal());
        tag.putInt("temperature", getTemperature());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        thermal = ThermalLevels.values()[(int)nbt.getFloat("thermal")];
        temperature = nbt.getInt("temperature");
    }
}
