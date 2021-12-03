package misteryman.chillscharringorigins.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import misteryman.chillscharringorigins.common.ChillsCharringOrigins;

import java.util.ArrayList;
import java.util.Arrays;

@Config(name = ChillsCharringOrigins.MODID)
public class ModConfig implements ConfigData {
	public ArrayList<ConfigFoodItem> hot_food = new ArrayList<ConfigFoodItem>(Arrays.asList(
			new ConfigFoodItem("minecraft:magma_cream", 6, 6F),
			new ConfigFoodItem("minecraft:blaze_rod", 6, 6F),
			new ConfigFoodItem("minecraft:blaze_powder", 3, 3F)
	));
}
