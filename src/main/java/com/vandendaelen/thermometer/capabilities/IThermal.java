package com.vandendaelen.thermometer.capabilities;

import com.vandendaelen.thermometer.misc.ThermalLevels;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public interface IThermal extends INBTSerializable<CompoundTag> {
    void tick(Player entity);
    ThermalLevels getThermalLevel();
    float getTemperature();

    public static class Provider implements ICapabilitySerializable<CompoundTag>{
        private IThermal thermal;

        public Provider(IThermal thermal) {
            this.thermal = thermal;
        }

        public Provider(){
            this.thermal = new ThermalCapability();
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
            return cap == Capabilities.THERMAL ? (LazyOptional<T>) LazyOptional.of(() -> (T) thermal) : LazyOptional.empty();
        }

        @Override
        public CompoundTag serializeNBT() {
            return thermal.serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            thermal.deserializeNBT(nbt);
        }
    }
}
