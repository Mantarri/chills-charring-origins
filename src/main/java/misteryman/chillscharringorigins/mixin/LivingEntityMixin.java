package misteryman.chillscharringorigins.mixin;

import mc.rpgstats.main.RPGStats;
import misteryman.chillscharringorigins.common.ChillsCharringOrigins;
import misteryman.chillscharringorigins.common.RPGStatsIntegration;
import misteryman.chillscharringorigins.common.registry.CCComponents;
import misteryman.chillscharringorigins.common.registry.CCPowers;
import misteryman.chillscharringorigins.common.registry.CCStatusEffects;
import misteryman.chillscharringorigins.common.registry.CCTags;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	@Shadow public abstract Iterable<ItemStack> getArmorItems();

	@Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

	@Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect, @Nullable Entity source);

	@Shadow @Nullable private LivingEntity attacker;

	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@ModifyVariable(method = "damage", at = @At("HEAD"))
	private float damage(float amount, DamageSource damageSource) {
		Entity attacker = damageSource.getAttacker();
		Entity source = damageSource.getSource();

		if(CCPowers.FLAMING_BODY.isActive(attacker) && damageSource.isNeutral()) {
			this.setOnFireFor(4);
			RPGStats.addXpAndLevelUp(ChillsCharringOrigins.id("abilityuse"), (ServerPlayerEntity) attacker, 1);
		}

		if(CCPowers.CHILLED_ARROWS.isActive(attacker) || CCPowers.FROZEN_QUIVER.isActive(attacker)) {
			// If chilled arrows active, no amplifier, if it isn't (and thus quiver is) then the amplifier is 1
			int amplifier = CCPowers.CHILLED_ARROWS.isActive(attacker) ? 0: 1;

			if(!(((LivingEntity) (Object) this) instanceof StrayEntity) ||
				(((LivingEntity) (Object) this) instanceof PolarBearEntity) ||
				(((LivingEntity) (Object) this) instanceof SnowGolemEntity) &&
				damageSource.isProjectile()) {
				if (source instanceof ProjectileEntity) {
					((LivingEntity) (Object) this).addStatusEffect(CCStatusEffects.newStatusEffectInstance(CCStatusEffects.FROSTBITE, 600, amplifier));
					if(!this.world.isClient()) {
						System.out.println("Current origin: " + CCComponents.ORIGIN.get(attacker).getOrigin());
						RPGStatsIntegration.levelUp("originlevel", (ServerPlayerEntity) attacker, 2);
					}
				}
			}
		}

		if(CCPowers.NUMBING_ARROWS.isActive(attacker) && damageSource.isProjectile()) {
			this.addStatusEffect(CCStatusEffects.newStatusEffectInstance(StatusEffects.SLOWNESS, 600, 1));
			if(!this.world.isClient()) {
				RPGStatsIntegration.levelUp("abilityuse", (ServerPlayerEntity) attacker, 2);
			}
		}

		if(CCPowers.SWORD_SWINE.isActive(attacker) && damageSource.isNeutral()) {
			if(attacker instanceof ServerPlayerEntity) {
				ItemStack mainHandItemStack = ((ServerPlayerEntity) (Object) attacker).getMainHandStack();
				Item mainHandItem = mainHandItemStack.getItem();
				if (FabricToolTags.SWORDS.contains(mainHandItem)) {
					amount *= 1.5F;
					if(!this.world.isClient()) {
						RPGStats.addXpAndLevelUp(ChillsCharringOrigins.id("abilityuse"), (ServerPlayerEntity) attacker, 2);
					}
				}
			}
		}

		if(CCPowers.MARKSHOG.isActive(attacker)) {
			if(attacker instanceof ServerPlayerEntity) {
				ItemStack mainHandItemStack = ((ServerPlayerEntity) (Object) attacker).getMainHandStack();
				Item mainHandItem = mainHandItemStack.getItem();
				if (mainHandItem instanceof BowItem && damageSource.isProjectile()) {
					amount *= 1.5F;
					if(!this.world.isClient()) {
						RPGStats.addXpAndLevelUp(ChillsCharringOrigins.id("abilityuse"), (ServerPlayerEntity) attacker, 2);
					}
				} else if(mainHandItem instanceof CrossbowItem && damageSource.isProjectile()) {
					amount *= 2.0F;
					if(!this.world.isClient()) {
						RPGStats.addXpAndLevelUp(ChillsCharringOrigins.id("abilityuse"), (ServerPlayerEntity) attacker, 3);
					}
				}
			}
		}

		if(CCPowers.MIGHTY_AXE.isActive(attacker) && damageSource.isNeutral()) {
			if(attacker instanceof ServerPlayerEntity) {
				ItemStack mainHandItemStack = ((ServerPlayerEntity) (Object) attacker).getMainHandStack();
				Item mainHandItem = mainHandItemStack.getItem();
				if (FabricToolTags.AXES.contains(mainHandItem)) {
					amount *= 1.75F;
					if(!this.world.isClient()) {
						RPGStats.addXpAndLevelUp(ChillsCharringOrigins.id("abilityuse"), (ServerPlayerEntity) attacker, 2);
					}
				}
			}
		}
		
		if(CCPowers.BOLD_GOLD.isActive(this)) {
			int armorPieces = 0;
			for(ItemStack stack : getArmorItems()) {
				if(CCTags.GOLD_ARMOUR.contains(stack.getItem())) {
					armorPieces++;
				}
			}
			amount *= (.65 - (armorPieces * 0.08f));
		}

		return amount;
	}
}
