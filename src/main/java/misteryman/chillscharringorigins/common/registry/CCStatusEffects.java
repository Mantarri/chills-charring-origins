package misteryman.chillscharringorigins.common.registry;

import misteryman.chillscharringorigins.common.ChillsCharringOrigins;
import misteryman.chillscharringorigins.effects.FirestormStatusEffect;
import misteryman.chillscharringorigins.effects.FrostbiteStatusEffect;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.registry.Registry;

public class CCStatusEffects {
	public static final StatusEffect FROSTBITE;
	public static final Potion FROSTBITE_POTION;
	public static final Potion FROSTBITE_POTION_STRONG;
	public static final Potion FROSTBITE_POTION_LONG;

	public static final StatusEffect FIRESTORM;
	public static final Potion FIRESTORM_POTION;
	public static final Potion FIRESTORM_POTION_STRONG;
	public static final Potion FIRESTORM_POTION_LONG;

	static {
		FROSTBITE = registerStatusEffect(
				"frostbite",
				new FrostbiteStatusEffect(),
				EntityAttributes.GENERIC_MOVEMENT_SPEED,
				"7107DE5E-7CE8-4030-940E-514C1F160890",
				-0.15000000596046448D,
				EntityAttributeModifier.Operation.MULTIPLY_TOTAL
		);

		FIRESTORM = registerStatusEffect(
				"firestorm",
				new FirestormStatusEffect()
		);

		FROSTBITE_POTION = registerPotion("frostbite", newStatusEffectInstance(FROSTBITE, 1800, 0));
		FROSTBITE_POTION_STRONG = registerPotion("strong_frostbite", newStatusEffectInstance(FROSTBITE, 1800, 1));
		FROSTBITE_POTION_LONG = registerPotion("long_frostbite", newStatusEffectInstance(FROSTBITE, 3600, 0));

		FIRESTORM_POTION = registerPotion("firestorm", newStatusEffectInstance(FIRESTORM, 1800, 0));
		FIRESTORM_POTION_STRONG = registerPotion("strong_firestorm", newStatusEffectInstance(FIRESTORM, 1800, 1));
		FIRESTORM_POTION_LONG = registerPotion("long_firestorm", newStatusEffectInstance(FIRESTORM, 3600, 0));
	}

	public static StatusEffect registerStatusEffect(String id, StatusEffect entry) {
		return Registry.register(Registry.STATUS_EFFECT, ChillsCharringOrigins.id(id), entry);
	}
	public static StatusEffect registerStatusEffect(String id, StatusEffect entry, EntityAttribute attribute, String uuid, double amount, EntityAttributeModifier.Operation operation) {
		return Registry.register(Registry.STATUS_EFFECT, ChillsCharringOrigins.id(id), entry.addAttributeModifier(attribute, uuid, amount, operation));
	}

	public static StatusEffectInstance newStatusEffectInstance(StatusEffect effect, int duration, int amplifier) {
		return new StatusEffectInstance(effect, duration, amplifier);
	}

	public static Potion registerPotion(String id, StatusEffectInstance entry) {
		return Registry.register(Registry.POTION, ChillsCharringOrigins.id(id), new Potion(id, entry));
	}

	public static void init() {

	}
}