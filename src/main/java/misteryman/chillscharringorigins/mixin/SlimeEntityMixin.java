package misteryman.chillscharringorigins.mixin;

import misteryman.chillscharringorigins.common.registry.CCPowers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SlimeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Predicate;

@Mixin(SlimeEntity.class)
public abstract class SlimeEntityMixin {
	@Redirect(at = @At(value = "NEW", target = "net/minecraft/entity/ai/goal/ActiveTargetGoal", ordinal = 0), method = "initGoals")
	private <T extends LivingEntity> ActiveTargetGoal<T> replacePlayerTargetGoal(MobEntity mobEntity, Class<T> clazz, int reciprocalChance, boolean checkVisibility, boolean checkCanNavigate, Predicate<LivingEntity> entity) {
		return new ActiveTargetGoal<>(mobEntity, clazz, 10, checkVisibility, false, e ->(
			!(CCPowers.MAGMA_MEMBER.isActive(e) && (mobEntity instanceof MagmaCubeEntity)))
		);
	}
}
