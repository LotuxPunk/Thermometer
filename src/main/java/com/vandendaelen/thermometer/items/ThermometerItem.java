package com.vandendaelen.thermometer.items;

import com.vandendaelen.thermometer.capabilities.Capabilities;
import com.vandendaelen.thermometer.capabilities.IThermal;
import com.vandendaelen.thermometer.helpers.MathHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ThermometerItem extends Item {
    private static final String THERMAL = "thermal";


    public ThermometerItem() {
        super(new Properties().maxStackSize(1).group(ItemGroup.TOOLS));
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);

        if (!worldIn.isRemote()){
            if (entityIn instanceof ServerPlayerEntity){
                stack.getCapability(Capabilities.THERMAL).ifPresent(cap -> cap.tick((ServerPlayerEntity)entityIn));
            }
            if (worldIn.getGameTime() % 20 == 0) {
                syncCapability(stack);
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        stack.getCapability(Capabilities.THERMAL).ifPresent(cap -> {
            tooltip.add(getTemperatureInformation(cap));
        });
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        playerIn.getHeldItem(handIn).getCapability(Capabilities.THERMAL).ifPresent(cap -> {
            playerIn.sendStatusMessage(getTemperatureInformation(cap), true);
        });
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    private TranslationTextComponent getTemperatureInformation(IThermal cap){
        double fTemp = MathHelper.getFahrenheitTemperature(cap.getTemperature());
        double cTemp = MathHelper.getCelsiusTemperature(fTemp);
        return new TranslationTextComponent("thermometer.temp", (int)cTemp , (int)fTemp);
    }

    public static void syncCapability(ItemStack stack) {
        if (stack.getShareTag() != null) {
            stack.getOrCreateTag().merge(stack.getShareTag());
        }
    }

    public static void readCapability(ItemStack stack) {
        if (stack.getShareTag() != null) {
            stack.readShareTag(stack.getOrCreateTag());
        }
    }

    @Nullable
    @Override
    public CompoundNBT getShareTag(ItemStack stack) {
        CompoundNBT tag = stack.getOrCreateTag();
        stack.getCapability(Capabilities.THERMAL).ifPresent(handler -> tag.put("cap_sync", handler.serializeNBT()));
        return tag;

    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
        super.readShareTag(stack, nbt);
        if (nbt != null) {
            if (nbt.contains("cap_sync")) {
                stack.getCapability(Capabilities.THERMAL).ifPresent(handler -> handler.deserializeNBT(nbt.getCompound("cap_sync")));
            }
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }
}
