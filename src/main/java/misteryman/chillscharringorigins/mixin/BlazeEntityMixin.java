package misteryman.chillscharringorigins.mixin;

import misteryman.chillscharringorigins.common.registry.CCPowers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlazeEntity.class)
public abstract class BlazeEntityMixin {
	@Redirect(at = @At(value = "NEW", target = "net/minecraft/entity/ai/goal/ActiveTargetGoal", ordinal = 0), method = "initGoals")
	private <T extends LivingEntity> ActiveTargetGoal<T> replacePlayerTargetGoal(MobEntity mobEntity, Class<T> clazz, boolean checkVisibility) {
		return new ActiveTargetGoal<>(mobEntity, clazz, 10, checkVisibility, false, e ->!CCPowers.BLAZEBORN.isActive(e));
	}
}
