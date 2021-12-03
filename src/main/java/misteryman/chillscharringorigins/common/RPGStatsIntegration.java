package misteryman.chillscharringorigins.common;

import io.github.apace100.origins.Origins;
import io.github.apace100.origins.component.OriginComponent;
import io.github.apace100.origins.networking.ModPackets;
import io.github.apace100.origins.origin.Origin;
import io.github.apace100.origins.origin.OriginLayer;
import io.github.apace100.origins.origin.OriginLayers;
import io.github.apace100.origins.origin.OriginRegistry;
import io.github.apace100.origins.registry.ModComponents;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class RPGStatsIntegration {
	public static final int stageTwoRequiredLevel = 25;
	public static final int stageThreeRequiredLevel = 50;

	public static void originLevelUp(World world, PlayerEntity pe) {
		if(!world.isClient) {
			HashMap<OriginLayer, Origin> originData = Origin.get(pe);
			OriginLayer originLayer = originData.keySet().iterator().next();
			Origin origin = originData.get(originLayer);
			OriginComponent component = ModComponents.ORIGIN.get(pe);
			String strippedOriginLayerName = originLayer.getIdentifier().toString();
			String strippedOriginName = origin.getIdentifier().toString();
			System.out.println("layer: " + originLayer.getIdentifier());
			System.out.println("origin: " + origin.getIdentifier());

			for (OriginLayer layer : OriginLayers.getLayers()) {
				if (strippedOriginLayerName.equals(strippedOriginName)) {
					component.setOrigin(layer, Origin.EMPTY);
				} else {
					component.setOrigin(layer, OriginRegistry.get(ChillsCharringOrigins.id("null")));
				}
			}
			component.checkAutoChoosingLayers(pe, false);
			component.sync();
			PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
			data.writeBoolean(false);
			ServerPlayNetworking.send((ServerPlayerEntity) pe, ModPackets.OPEN_ORIGIN_SCREEN, data);
		}
	}

	private Map<OriginLayer, Origin> getTargets(ItemStack stack) {
		HashMap<OriginLayer, Origin> targets = new HashMap<>();
		if(!stack.hasNbt()) {
			return targets;
		}
		NbtCompound nbt = stack.getNbt();
		if(!nbt.contains("Targets", NbtType.LIST)) {
			return targets;
		}
		NbtList targetList = (NbtList)nbt.get("Targets");
		for (NbtElement nbtElement : targetList) {
			if(nbtElement instanceof NbtCompound targetNbt) {
				if(targetNbt.contains("Layer", NbtType.STRING)) {
					try {
						Identifier id = new Identifier(targetNbt.getString("Layer"));
						OriginLayer layer = OriginLayers.getLayer(id);
						Origin origin = Origin.EMPTY;
						if(targetNbt.contains("Origin", NbtType.STRING)) {
							Identifier originId = new Identifier(targetNbt.getString("Origin"));
							origin = OriginRegistry.get(originId);
						}
						if(layer.isEnabled() && (layer.contains(origin) || origin.isSpecial())) {
							targets.put(layer, origin);
						}
					} catch (Exception e) {
						// no op
					}
				}
			}
		}
		return targets;
	}
}
