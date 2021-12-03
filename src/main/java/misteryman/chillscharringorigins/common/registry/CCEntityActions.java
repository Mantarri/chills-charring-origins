package misteryman.chillscharringorigins.common.registry;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.apace100.origins.component.OriginComponent;
import io.github.apace100.origins.origin.Origin;
import io.github.apace100.origins.origin.OriginLayer;
import io.github.apace100.origins.registry.ModComponents;
import mc.rpgstats.main.RPGStats;
import misteryman.chillscharringorigins.common.ChillsCharringOrigins;
import misteryman.chillscharringorigins.mixin.StatusEffectInstanceAccessor;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Potion;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class CCEntityActions {
	public static void register() {
		register_action(new ActionFactory<>(ChillsCharringOrigins.id("chemical_burn"), new SerializableData(),
			(data, entity) -> {
				PlayerEntity pe = (PlayerEntity) entity;
				Map<StatusEffect, StatusEffectInstance> activeStatusEffects = pe.getActiveStatusEffects();

				activeStatusEffects.entrySet().removeIf(key -> !key.getKey().isBeneficial());

				for(Map.Entry<StatusEffect, StatusEffectInstance> entry : activeStatusEffects.entrySet()) {
					StatusEffectInstance statusEffectInstance = entry.getValue();
					((StatusEffectInstanceAccessor) statusEffectInstance).setAmplifier(entry.getValue().getAmplifier() + 1);
				}
				if(!pe.world.isClient()) {
					RPGStats.addXpAndLevelUp(ChillsCharringOrigins.id("abilityuse"), (ServerPlayerEntity) pe, 5);
				}
			}));

		register_action(new ActionFactory<>(ChillsCharringOrigins.id("spawn_effect_cloud"), new SerializableData()
			.add("potion", SerializableDataType.registry(Potion.class, Registry.POTION))
			.add("radius_end_size", SerializableDataTypes.FLOAT)
			.add("radius_start_size", SerializableDataTypes.FLOAT)
			.add("warm_up_time", SerializableDataTypes.INT)
			.add("cloud_duration", SerializableDataTypes.INT),
			(data, entity) -> {
				PlayerEntity pe = (PlayerEntity) entity;

				AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(
					pe.world, pe.getX(), pe.getY(), pe.getZ());
				areaEffectCloudEntity.setOwner(pe);

				areaEffectCloudEntity.setRadius(data.getFloat("radius_end_size"));
				areaEffectCloudEntity.setRadiusOnUse(data.getFloat("radius_start_size"));
				areaEffectCloudEntity.setWaitTime(data.getInt("warm_up_time"));
				areaEffectCloudEntity.setDuration(data.getInt("cloud_duration"));
				areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float)areaEffectCloudEntity.getDuration());
				areaEffectCloudEntity.setPotion(((Potion) data.get("potion")));

				for(StatusEffectInstance statusEffectInstance : ((Potion) data.get("potion")).getEffects()) {
					areaEffectCloudEntity.addEffect(new StatusEffectInstance(statusEffectInstance));
				}

				pe.world.spawnEntity(areaEffectCloudEntity);
				if(!pe.world.isClient()) {
					RPGStats.addXpAndLevelUp(ChillsCharringOrigins.id("abilityuse"), (ServerPlayerEntity) pe, 5);
				}
			}));

		register_action(new ActionFactory<>(ChillsCharringOrigins.id("freeze_frame_clear"), new SerializableData(),
			(data, entity) -> {
				PlayerEntity pe = (PlayerEntity) entity;

				Map<StatusEffect, StatusEffectInstance> activeStatusEffects = pe.getActiveStatusEffects();

				activeStatusEffects.entrySet().removeIf(key -> key.getKey().isBeneficial());
				if(!pe.world.isClient()) {
					RPGStats.addXpAndLevelUp(ChillsCharringOrigins.id("abilityuse"), (ServerPlayerEntity) pe, 5);
				}
			}));
	}

	private static void register_action(ActionFactory<Entity> actionFactory) {
		Registry.register(ApoliRegistries.ENTITY_ACTION, actionFactory.getSerializerId(), actionFactory);
	}
}
