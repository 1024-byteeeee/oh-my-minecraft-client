package com.plusls.ommc.mixin.feature.blockModelNoOffset.fabric;

import com.mojang.blaze3d.vertex.PoseStack;
import com.plusls.ommc.feature.blockModelNoOffset.BlockModelNoOffsetUtil;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.TerrainBlockRenderInfo;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.TerrainRenderContext;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = TerrainRenderContext.class, remap = false)
public abstract class MixinTerrainRenderContext implements RenderContext {

    @Final
    @Shadow
    private TerrainBlockRenderInfo blockInfo;

    @Dynamic
    @Inject(method = {
            "tessellateBlock", // For fabric-renderer-indigo 0.5.0 and above
            "tesselateBlock" // For fabric-renderer-indigo 0.5.0 below
    }, at = @At(value = "HEAD"))
    private void blockModelNoOffset(BlockState blockState, BlockPos blockPos, BakedModel model, PoseStack matrixStack, CallbackInfoReturnable<Boolean> cir) {
        Vec3 offsetPos = blockState.getOffset(blockInfo.blockView, blockPos);
        if (BlockModelNoOffsetUtil.shouldNoOffset(blockState)) {
            matrixStack.translate(-offsetPos.x, -offsetPos.y, -offsetPos.z);
        }
    }
}