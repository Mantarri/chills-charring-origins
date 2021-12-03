package misteryman.chillscharringorigins.mixin;

import misteryman.chillscharringorigins.common.registry.CCPowers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.StrayEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractSkeletonEntity.class)
public abstract class AbstractSkeletonEntityMixin {
	@Redirect(at = @At(value = "NEW", target = "net/minecraft/entity/ai/goal/ActiveTargetGoal", ordinal = 0), method = "initGoals")
	private <T extends LivingEntity> ActiveTargetGoal<T> replaceFollowPlayerGoal(MobEntity mobEntity, Class<T> clazz, boolean checkVisibility) {
		return new ActiveTargetGoal<>(mobEntity, clazz, 10, checkVisibility, false, e ->(
				!(CCPowers.FROZEN_HEART.isActive(e) && (mobEntity instanceof StrayEntity)))
		);
	}
}
