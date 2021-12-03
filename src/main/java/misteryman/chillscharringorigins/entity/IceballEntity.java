package misteryman.chillscharringorigins.entity;

import misteryman.chillscharringorigins.common.ChillsCharringOrigins;
import misteryman.chillscharringorigins.client.ChillsCharringOriginsClient;
import misteryman.chillscharringorigins.common.registry.CCStatusEffects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class IceballEntity extends ThrownItemEntity {

    public IceballEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public IceballEntity(World world, LivingEntity owner) {
        super(ChillsCharringOrigins.IceballEntityType, owner, world); // null will be changed later
    }

    public IceballEntity(World world, double x, double y, double z) {
        super(ChillsCharringOrigins.IceballEntityType, x, y, z, world); // null will be changed later
    }

    @Override
    protected Item getDefaultItem() {
        return ChillsCharringOrigins.IceballItem;
    }

    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() { // Not entirely sure, but probably has do to with the snowball's particles. (OPTIONAL)
        ItemStack itemStack = this.getItem();
        return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) { // Also not entirely sure, but probably also has to do with the particles. This method (as well as the previous one) are optional, so if you don't understand, don't include this one.
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for(int i = 0; i < 8; ++i) {
                this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if(entity instanceof LivingEntity) {
            ((LivingEntity) entity).setStatusEffect(
                CCStatusEffects.newStatusEffectInstance(
                    CCStatusEffects.FROSTBITE,
                    600,
                    0),
                    this.getOwner());
        }
    }


    protected void onCollision(HitResult hitResult) { // called on collision with a block
        super.onCollision(hitResult);
        if (!this.world.isClient) { // checks if the world is client
            this.world.sendEntityStatus(this, (byte)3); // particle?
            this.kill(); // kills the projectile
        }

    }

    @Override
    public Packet createSpawnPacket() {
        return EntitySpawnPacket.create(this, ChillsCharringOriginsClient.PacketID);
    }
}
