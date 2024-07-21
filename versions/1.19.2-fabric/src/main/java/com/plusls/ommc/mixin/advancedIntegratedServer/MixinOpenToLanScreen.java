package com.plusls.ommc.mixin.advancedIntegratedServer;

import com.plusls.ommc.config.Configs;
import net.minecraft.client.gui.screens.ShareToLanScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * The implementation for mc [1.14.4, ~)
 */
@Mixin(ShareToLanScreen.class)
public class MixinOpenToLanScreen {
    @SuppressWarnings("InvalidInjectorMethodSignature")
    @ModifyVariable(
            method = "method_19851",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/util/HttpUtil;getAvailablePort()I",
                    ordinal = 0,
                    remap = true
            ),
            ordinal = 0,
            remap = false
    )
    private int modifyPort(int port) {
        int ret = Configs.port;

        if (ret == 0) {
            ret = port;
        }

        return ret;
    }
}
