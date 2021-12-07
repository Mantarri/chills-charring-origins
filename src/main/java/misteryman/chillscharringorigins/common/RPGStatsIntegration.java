package misteryman.chillscharringorigins.common;

import io.github.apace100.origins.component.OriginComponent;
import io.github.apace100.origins.networking.ModPackets;
import io.github.apace100.origins.origin.Origin;
import io.github.apace100.origins.origin.OriginLayer;
import io.github.apace100.origins.origin.OriginLayers;
import io.github.apace100.origins.origin.OriginRegistry;
import io.github.apace100.origins.registry.ModComponents;
import io.netty.buffer.Unpooled;
import mc.rpgstats.main.CustomComponents;
import mc.rpgstats.main.RPGStats;
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
	public static final int stageZeroLevelRequired = 0;
	public static final int stageOneLevelRequired = 1;
	public static final int stageTwoLevelRequired = 2;

	public static int getPlayerLevel(Identifier id, PlayerEntity player) {
		if (CustomComponents.components.containsKey(id)) {
			return CustomComponents.STATS.get(player).getOrCreateID(id).getLevel();
		} else {
			return -1;
		}
	}

	public static void levelUp(String statName, ServerPlayerEntity spe, int addedXP) {
		Identifier id = ChillsCharringOrigins.id(statName);
		System.out.println("Adding: " + addedXP + " to stat: " + id);
		RPGStats.addXpAndLevelUp(id, spe, addedXP);
	}

	public static void originLevelUp(World world, PlayerEntity pe) {
		if(!world.isClient) {
			OriginComponent component = ModComponents.ORIGIN.get(pe);
			Map<OriginLayer, Origin> targets = getTargets();
			if(targets.size() > 0) {
				for (Map.Entry<OriginLayer, Origin> target : targets.entrySet()) {
					component.setOrigin(target.getKey(), target.getValue());
				}
			} else {
				for (OriginLayer layer : OriginLayers.getLayers()) {
					if (layer.isEnabled()) {
						component.setOrigin(layer, Origin.EMPTY);
					}
				}
			}

			component.checkAutoChoosingLayers(pe, false);
			component.sync();
			PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
			data.writeBoolean(false);
			ServerPlayNetworking.send((ServerPlayerEntity) pe, ModPackets.OPEN_ORIGIN_SCREEN, data);
		}
	}

	private static Map<OriginLayer, Origin> getTargets() {
		HashMap<OriginLayer, Origin> targets = new HashMap<>();
		return targets;
	}
}
