package misteryman.chillscharringorigins.mixin;

import misteryman.chillscharringorigins.common.registry.CCPowers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

	@Inject(at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerEntity;abilities:Lnet/minecraft/entity/player/PlayerAbilities;"), method = "getArrowType", cancellable = true)
	private void modifyGetArrowType(ItemStack weapon, CallbackInfoReturnable<ItemStack> cir) {
		if(CCPowers.ENDLESS_QUIVER.isActive(((PlayerEntity) (Object) this))){
			cir.setReturnValue(new ItemStack(Items.ARROW));
		}
	}
}
