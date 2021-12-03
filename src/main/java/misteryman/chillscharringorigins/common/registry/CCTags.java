package misteryman.chillscharringorigins.common.registry;

import misteryman.chillscharringorigins.common.ChillsCharringOrigins;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;

public class CCTags {
	public static final Tag<Item> PIGLIN_FOOD_BARTERING_ITEMS = TagFactory.ITEM.create(ChillsCharringOrigins.id("piglin_food_bartering_items"));
	public static final Tag<Item> GOLD_ARMOUR = TagFactory.ITEM.create(ChillsCharringOrigins.id("gold_armour"));
	public static final Tag<Item> GOLD_TOOLS = TagFactory.ITEM.create(ChillsCharringOrigins.id("gold_tools"));
	public static final Tag<Item> CONSUMABLE_FIRE = TagFactory.ITEM.create(ChillsCharringOrigins.id("consumable_fire"));

	public static void init() {

	}
}
