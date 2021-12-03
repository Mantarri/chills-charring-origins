package misteryman.chillscharringorigins.mixin;

import misteryman.chillscharringorigins.common.registry.CCPowers;
import misteryman.chillscharringorigins.common.registry.CCTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
	@Shadow
	public abstract Item getItem();

	@Inject(method = "damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V", at = @At("HEAD"), cancellable = true)
	private <T extends LivingEntity> void modifyDamage(int amount, T entity, Consumer<T> breakCallback, CallbackInfo callbackInfo) {
		if(CCPowers.BOLD_GOLD.isActive(entity)) {
			Item item = getItem();

			if(CCTags.GOLD_TOOLS.contains(item) && entity.world.random.nextFloat() < 15 / 16f) {
				callbackInfo.cancel();
			}
			if(CCTags.GOLD_ARMOUR.contains(item) && entity.world.random.nextFloat() < 3 / 4f) {
				callbackInfo.cancel();
			}
		}
	}
}
