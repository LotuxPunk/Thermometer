package com.vandendaelen.thermometer.items;

import com.vandendaelen.thermometer.capabilities.Capabilities;
import com.vandendaelen.thermometer.capabilities.IThermal;
import com.vandendaelen.thermometer.helpers.MathHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ThermometerItem extends Item {
    private static final String THERMAL = "thermal";


    public ThermometerItem() {
        super(new Properties().stacksTo(1).tab(CreativeModeTab.TAB_TOOLS));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);

        if (!worldIn.isClientSide()){
            if (entityIn instanceof ServerPlayer){
                stack.getCapability(Capabilities.THERMAL).ifPresent(cap -> cap.tick((ServerPlayer)entityIn));
            }
            if (worldIn.getGameTime() % 20 == 0) {
                syncCapability(stack);
            }
        }
    }



    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        stack.getCapability(Capabilities.THERMAL).ifPresent(cap -> {
            tooltip.add(getTemperatureInformation(cap));
        });
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        playerIn.getItemInHand(handIn).getCapability(Capabilities.THERMAL).ifPresent(cap -> {
            playerIn.displayClientMessage(getTemperatureInformation(cap), true);
        });
        return super.use(worldIn, playerIn, handIn);
    }

    private TranslatableComponent getTemperatureInformation(IThermal cap){
        double fTemp = MathHelper.getFahrenheitTemperature(cap.getTemperature());
        double cTemp = MathHelper.getCelsiusTemperature(fTemp);
        return new TranslatableComponent("thermometer.temp", (int)cTemp , (int)fTemp);
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
    public CompoundTag getShareTag(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        stack.getCapability(Capabilities.THERMAL).ifPresent(handler -> tag.put("cap_sync", handler.serializeNBT()));
        return tag;

    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
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
