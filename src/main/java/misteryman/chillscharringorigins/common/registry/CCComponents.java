package misteryman.chillscharringorigins.common.registry;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import misteryman.chillscharringorigins.common.ChillsCharringOrigins;
import misteryman.chillscharringorigins.components.SyncedOriginComponent;

public final class CCComponents implements EntityComponentInitializer {
	public static final ComponentKey<SyncedOriginComponent> ORIGIN = ComponentRegistryV3.INSTANCE.getOrCreate(ChillsCharringOrigins.id("origin"), SyncedOriginComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		// Add the component to every PlayerEntity instance, and copy it on respawn
		registry.registerForPlayers(ORIGIN, player -> new SyncedOriginComponent(player), RespawnCopyStrategy.ALWAYS_COPY);
	}
}
