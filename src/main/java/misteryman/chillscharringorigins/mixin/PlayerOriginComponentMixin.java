package misteryman.chillscharringorigins.mixin;

import io.github.apace100.origins.component.PlayerOriginComponent;
import io.github.apace100.origins.origin.Origin;
import io.github.apace100.origins.origin.OriginLayer;
import misteryman.chillscharringorigins.common.registry.CCComponents;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerOriginComponent.class, remap = false)
public abstract class PlayerOriginComponentMixin {

	@Shadow private PlayerEntity player;

	@Inject(method = "setOrigin", at = @At("HEAD"))
	public void modifySetOrigin(OriginLayer layer, Origin origin, CallbackInfo ci) {
		CCComponents.ORIGIN.get(player).setOrigin(origin);
	}
}
