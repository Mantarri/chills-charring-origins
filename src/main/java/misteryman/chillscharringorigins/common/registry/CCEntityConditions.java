package misteryman.chillscharringorigins.common.registry;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.apoli.util.Comparison;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.apace100.origins.origin.OriginRegistry;
import misteryman.chillscharringorigins.common.ChillsCharringOrigins;
import misteryman.chillscharringorigins.common.RPGStatsIntegration;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Objects;

public class CCEntityConditions {
	public static void register() {
		registerCondition(new ConditionFactory<>(ChillsCharringOrigins.id("player_rpgstats_level"), new SerializableData()
			.add("namespace", SerializableDataTypes.STRING)
			.add("stat", SerializableDataTypes.STRING)
			.add("comparison", ApoliDataTypes.COMPARISON)
			.add("compare_to", SerializableDataTypes.INT),
			(data, entity) -> {
			/*
				System.out.println("Stat: " + new Identifier(data.getString("namespace"), data.getString("stat")) + " Level: " + RPGStatsIntegration.getPlayerLevel(new Identifier(data.getString("namespace"), data.getString("stat")), ((PlayerEntity) entity)));
				System.out.println("Required stat: " + data.getString("stat") + " " + ((Comparison) data.get("comparison")) + " " + data.getInt("compare_to"));
				System.out.println();

				if(!((Comparison) data.get("comparison")).compare(RPGStatsIntegration.getPlayerLevel(new Identifier(data.getString("namespace"), data.getString("stat")), ((PlayerEntity) entity)), data.getInt("compare_to"))) {
					System.out.println("Condition: " + (((Comparison) data.get("comparison")).compare(RPGStatsIntegration.getPlayerLevel(new Identifier(data.getString("namespace"), data.getString("stat")), ((PlayerEntity) entity)), data.getInt("compare_to"))));
					System.out.println();
				}

			 */

				return (((Comparison) data.get("comparison")).compare(RPGStatsIntegration.getPlayerLevel(new Identifier(data.getString("namespace"), data.getString("stat")), ((PlayerEntity) entity)), data.getInt("compare_to")));
			}));

		registerCondition(new ConditionFactory<>(ChillsCharringOrigins.id("layer_compatible_origin"), new SerializableData()
			.add("origin", SerializableDataTypes.IDENTIFIER),
			(data, entity) -> {
				if(
					CCComponents.ORIGIN.get(entity).getOrigin() == null ||
					CCComponents.ORIGIN.get(entity).getOrigin() == "minecraft" ||
					CCComponents.ORIGIN.get(entity).getOrigin() == "default") {
					return false;
				} else {

					System.out.println("Requires: " + OriginRegistry.get(data.getId("origin")) + " Got: " + OriginRegistry.get(new Identifier(CCComponents.ORIGIN.get(entity).getOrigin())));
					System.out.println("Required Identifier: " + data.getId("origin") + " Saved identifier: " + CCComponents.ORIGIN.get(entity).getOrigin());

					return (OriginRegistry.get(data.getId("origin")).equals(OriginRegistry.get(new Identifier(CCComponents.ORIGIN.get(entity).getOrigin()))));
				}
			}));
	}



	private static void registerCondition(ConditionFactory<Entity> conditionFactory) {
		Registry.register(ApoliRegistries.ENTITY_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
	}
}
