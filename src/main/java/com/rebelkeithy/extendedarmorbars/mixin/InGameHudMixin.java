package com.rebelkeithy.extendedarmorbars.mixin;

import com.rebelkeithy.extendedarmorbars.ToughnessBar;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

	@Inject(at = @At("TAIL"), method = "renderStatusBars(Lnet/minecraft/client/util/math/MatrixStack;)V")
	private void init(MatrixStack matrices, CallbackInfo info) {
		ToughnessBar.render(matrices);
	}


	@ModifyArg(method = "renderStatusBars(Lnet/minecraft/client/util/math/MatrixStack;)V", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/hud/InGameHud.drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"), index = 5)
	private int render(MatrixStack matrices, int x, int y, int u, int v, int width, int height) {
		if(ToughnessBar.config.isArmorEnable() && (u == 34 || u == 25 || u == 16) && v == 9) {
			ToughnessBar.armorYValue = y;
			return 0;
		} else {
			return width;
		}
	}
}
