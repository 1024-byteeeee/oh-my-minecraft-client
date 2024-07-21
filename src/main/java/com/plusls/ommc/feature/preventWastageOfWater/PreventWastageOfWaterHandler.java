package com.plusls.ommc.feature.preventWastageOfWater;

import com.plusls.ommc.config.Configs;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

//#if MC <= 11404
//$$ import net.minecraft.world.InteractionResult;
//#endif

public class PreventWastageOfWaterHandler implements UseItemCallback {
    public static void init() {
        PreventWastageOfWaterHandler handler = new PreventWastageOfWaterHandler();
        UseItemCallback.EVENT.register(handler);
    }

    //#if MC > 11404
    @Override
    public InteractionResultHolder<ItemStack> interact(Player player, Level world, InteractionHand hand) {
        return (Configs.preventWastageOfWater.getBooleanValue()
                && world.isClientSide
                && player.getItemInHand(hand).getItem() == Items.WATER_BUCKET
                //#if MC > 11502
                && world.dimensionType().ultraWarm())
                //#else
                //$$ && world.getDimension().isUltraWarm())
                //#endif
                ? InteractionResultHolder.fail(ItemStack.EMPTY)
                : InteractionResultHolder.pass(ItemStack.EMPTY);
    }
    //#else
    //$$ @Override
    //$$ public InteractionResult interact(Player player, Level world, InteractionHand hand) {
    //$$     return (Configs.preventWastageOfWater
    //$$             && world.isClientSide
    //$$             && player.getItemInHand(hand).getItem() == Items.WATER_BUCKET
    //$$             && world.getDimension().isUltraWarm())
    //$$             ? InteractionResult.FAIL
    //$$             : InteractionResult.PASS;
    //$$ }
    //#endif
}
