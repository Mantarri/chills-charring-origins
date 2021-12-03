package misteryman.chillscharringorigins.mixin;

import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StatusEffectInstance.class)
public interface StatusEffectInstanceAccessor {
	@Accessor("amplifier")
	public void setAmplifier(int amplifier);

	@Accessor("duration")
	public void setDuration(int duration);
}
