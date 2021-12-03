package misteryman.chillscharringorigins.common.registry;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeReference;
import misteryman.chillscharringorigins.common.ChillsCharringOrigins;

public class CCPowers {
	public static final PowerType<Power> ENDLESS_QUIVER;
	public static final PowerType<Power> NUMBING_ARROWS;

	public static final PowerType<Power> HERD;
	public static final PowerType<Power> BOLD_GOLD;
	public static final PowerType<Power> FOOD_BARTERING;
	public static final PowerType<Power> MIGHTY_AXE;
	public static final PowerType<Power> MARKSHOG;
	public static final PowerType<Power> SWORD_SWINE;

	public static final PowerType<Power> BLAZEBORN;
	public static final PowerType<Power> FLAMING_BODY;
	public static final PowerType<Power> CHEMICAL_BURN;

	public static final PowerType<Power> MAGMA_MEMBER;



	public static final PowerType<Power> FROZEN_HEART;
	public static final PowerType<Power> CHILLED_ARROWS;
	public static final PowerType<Power> FROZEN_QUIVER;
	public static final PowerType<Power> FREEZE_FRAME;
	public static final PowerType<Power> FREEZE_FRAME_TOGGLE;

	static {
		ENDLESS_QUIVER = new PowerTypeReference<>(ChillsCharringOrigins.id("endless_quiver"));
		NUMBING_ARROWS = new PowerTypeReference<>(ChillsCharringOrigins.id("numbing_arrows"));

		HERD = new PowerTypeReference<>(ChillsCharringOrigins.id("herd"));
		BOLD_GOLD = new PowerTypeReference<>(ChillsCharringOrigins.id("bold_gold"));
		FOOD_BARTERING = new PowerTypeReference<>(ChillsCharringOrigins.id("food_bartering"));
		MIGHTY_AXE = new PowerTypeReference<>(ChillsCharringOrigins.id("food_bartering"));
		MARKSHOG = new PowerTypeReference<>(ChillsCharringOrigins.id("food_bartering"));
		SWORD_SWINE = new PowerTypeReference<>(ChillsCharringOrigins.id("sword_swine"));

		BLAZEBORN = new PowerTypeReference<>(ChillsCharringOrigins.id("blazeborn"));
		FLAMING_BODY = new PowerTypeReference<>(ChillsCharringOrigins.id("flaming_body"));
		CHEMICAL_BURN = new PowerTypeReference<>(ChillsCharringOrigins.id("chemical_burn"));

		MAGMA_MEMBER = new PowerTypeReference<>(ChillsCharringOrigins.id("magma_member"));



		FROZEN_HEART = new PowerTypeReference<>(ChillsCharringOrigins.id("frozen_heart"));
		CHILLED_ARROWS = new PowerTypeReference<>(ChillsCharringOrigins.id("chilled_arrows"));
		FROZEN_QUIVER = new PowerTypeReference<>(ChillsCharringOrigins.id("frozen_quiver"));
		FREEZE_FRAME = new PowerTypeReference<>(ChillsCharringOrigins.id("freeze_frame"));
		FREEZE_FRAME_TOGGLE = new PowerTypeReference<>(ChillsCharringOrigins.id("freeze_frame_toggle"));
	}
}
