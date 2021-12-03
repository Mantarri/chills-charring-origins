package misteryman.chillscharringorigins.common.registry;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.apoli.util.Comparison;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import mc.rpgstats.main.RPGStats;
import misteryman.chillscharringorigins.common.ChillsCharringOrigins;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CCEntityConditions {
	public static void register() {
		registerCondition(new ConditionFactory<>(ChillsCharringOrigins.id("player_rpgstats_level"), new SerializableData()
			.add("namespace", SerializableDataTypes.STRING)
			.add("stat", SerializableDataTypes.STRING)
			.add("comparison", ApoliDataTypes.COMPARISON)
			.add("compare_to", SerializableDataTypes.INT),
			(data, entity) -> {
				if(entity instanceof ServerPlayerEntity) {
					System.out.println("Stat: " + new Identifier(data.getString("namespace"), data.getString("stat")) + " Level: " + RPGStats.getComponentLevel(new Identifier(data.getString("namespace"), data.getString("stat")), ((ServerPlayerEntity) entity)));
					System.out.println("Required stat: " + data.getString("stat") + " " + ((Comparison) data.get("comparison")) + " " + data.getInt("compare_to"));
					System.out.println();

					System.out.println("Condition: " + (((Comparison) data.get("comparison")).compare(RPGStats.getComponentLevel(new Identifier(data.getString("namespace"), data.getString("stat")), ((ServerPlayerEntity) entity)), data.getInt("compare_to"))));
					System.out.println();
					return (((Comparison) data.get("comparison")).compare(RPGStats.getComponentLevel(new Identifier(data.getString("namespace"), data.getString("stat")), ((ServerPlayerEntity) entity)), data.getInt("compare_to")));
				}
				return false;
			}));
	}

	private static void registerCondition(ConditionFactory<Entity> conditionFactory) {
		Registry.register(ApoliRegistries.ENTITY_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
	}
}
