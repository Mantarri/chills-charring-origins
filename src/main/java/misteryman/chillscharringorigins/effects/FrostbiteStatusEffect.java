package misteryman.chillscharringorigins.effects;

import net.minecraft.client.gui.hud.BackgroundHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class FrostbiteStatusEffect extends StatusEffect {
	public FrostbiteStatusEffect() {
		super(StatusEffectCategory.HARMFUL, BackgroundHelper.ColorMixer.getArgb(255, 138, 235, 241));
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return true;
	}

	@Override
	public void applyUpdateEffect(LivingEntity entity, int amplifier) {
		entity.damage(DamageSource.FREEZE, 0.25F + (0.25F * amplifier));
	}

	@Override
	public boolean isInstant() {
		return false;
	}

	@Override
	public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
		super.onApplied(entity, attributes, amplifier);
		entity.damage(DamageSource.FREEZE, 0.5F + (0.5F * amplifier));
	}
}
