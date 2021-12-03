package misteryman.chillscharringorigins.mixin;

import misteryman.chillscharringorigins.common.registry.CCPowers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffectInstance.class)
public abstract class StatusEffectInstanceMixin {
	@Shadow public abstract int getDuration();

	@Inject(method = "update", at = @At("RETURN"), cancellable = true)
	public void modifyUpdate(LivingEntity entity, Runnable overwriteCallback, CallbackInfoReturnable<Boolean> cir) {
		if(CCPowers.FREEZE_FRAME_TOGGLE.isActive(entity)) {
			((StatusEffectInstanceAccessor) this).setDuration(getDuration() + 1);
		}
	}
}
