package com.vandendaelen.thermometer.capabilities;

import com.vandendaelen.thermometer.misc.ThermalLevels;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IThermal extends INBTSerializable<CompoundNBT> {
    void tick(PlayerEntity entity);
    ThermalLevels getThermalLevel();
    int getTemperature();

    public static  class Storage implements Capability.IStorage<IThermal> {

        @Nullable
        @Override
        public INBT writeNBT(Capability<IThermal> capability, IThermal instance, Direction side) {
            return instance.serializeNBT();
        }

        @Override
        public void readNBT(Capability<IThermal> capability, IThermal instance, Direction side, INBT nbt) {
            instance.deserializeNBT((CompoundNBT) nbt);
        }
    }

    public static class Provider implements ICapabilitySerializable<CompoundNBT>{
        private IThermal thermal;

        public Provider(IThermal thermal) {
            this.thermal = thermal;
        }

        public Provider(){
            this.thermal = new ThermalCapability();
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return cap == Capabilities.THERMAL ? (LazyOptional<T>) LazyOptional.of(() -> (T) thermal) : LazyOptional.empty();
        }

        @Override
        public CompoundNBT serializeNBT() {
            return thermal.serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) {
            thermal.deserializeNBT(nbt);
        }
    }
}
