package misteryman.chillscharringorigins.common;

import mc.rpgstats.event.LevelUpCallback;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import misteryman.chillscharringorigins.common.registry.CCEntityActions;
import misteryman.chillscharringorigins.common.registry.CCEntityConditions;
import misteryman.chillscharringorigins.common.registry.CCStatusEffects;
import misteryman.chillscharringorigins.config.ModConfig;
import misteryman.chillscharringorigins.entity.IceballEntity;
import misteryman.chillscharringorigins.item.IceballItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChillsCharringOrigins implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MODID = "chillscharringorigins";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	public static boolean configRegistered = false;

	public static final EntityType<IceballEntity> IceballEntityType = Registry.register(
		Registry.ENTITY_TYPE,
		id("iceball"),
		FabricEntityTypeBuilder.<IceballEntity>create(SpawnGroup.MISC, IceballEntity::new)
			.dimensions(EntityDimensions.fixed(0.25F, 0.25F)) // dimensions in Minecraft units of the projectile
			.trackRangeBlocks(4).trackedUpdateRate(10) // necessary for all thrown projectiles
			.build() // VERY IMPORTANT
	);

	public static final Item IceballItem = new IceballItem(new Item.Settings().group(ItemGroup.MISC).maxCount(16));

	public static Identifier id(String id) {
		return(new Identifier(MODID, id));
	}

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		if(!configRegistered) {
			AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
			configRegistered = true;
		}

		LOGGER.info("Hello Frozen world!");

		Registry.register(Registry.ITEM, id("iceball"), IceballItem);

		CCStatusEffects.init();

		CCEntityConditions.register();

		CCEntityActions.register();

		LevelUpCallback.EVENT.register(((player, id, newLevel, hideMessages) -> {
			System.out.println("Levelled up: " + id + " to level: " + newLevel);

			if((
				newLevel == RPGStatsIntegration.stageZeroLevelRequired ||
				newLevel == RPGStatsIntegration.stageOneLevelRequired ||
				newLevel == RPGStatsIntegration.stageTwoLevelRequired)
				&& id.equals(ChillsCharringOrigins.id("originlevel"))) {
				RPGStatsIntegration.originLevelUp(player.getEntityWorld(), player);
			}

		}));

	}
}
