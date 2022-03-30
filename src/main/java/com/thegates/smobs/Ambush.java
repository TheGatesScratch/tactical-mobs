package com.thegates.smobs;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;

import java.util.Random;

public class Ambush {

    final ServerWorld world;
    final BlockPos blockPos;


    public Ambush(ServerWorld world, BlockPos blockPos) {
        this.world = world;
        this.blockPos = blockPos;
    }


    public void spawn() {
        Random random = world.getRandom();
        // Radius of blocks between x and z -24 and 24.
        int j = (24 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
        int k = (24 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
        BlockPos.Mutable mutable = blockPos.mutableCopy().move(j, 0, k);
        if (!world.isRegionLoaded(mutable.getX() - 10, mutable.getZ() - 10, mutable.getX() + 10, mutable.getZ() + 10)) {
            return;
        }

        int o = (int) Math.ceil(world.getLocalDifficulty(mutable).getLocalDifficulty()) + 1;

        ItemStack itemStack = new ItemStack(Items.LEATHER_HELMET);
        for (AmbushEntity ambushEntity : AmbushEntity.VALUES) {

            int spawns = world.getRandom().nextInt(ambushEntity.maxSpawn + ambushEntity.minSpawn) + ambushEntity.minSpawn;
            for (int i = 0; i < spawns; i++) {
                mutable.setY(world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, mutable).getY());

                MobEntity mobEntity = ambushEntity.entityType.create(world);
                if (mobEntity == null) {
                    continue;
                }
                mobEntity.setPosition(mutable.getX(), mutable.getY(), mutable.getZ());
                mobEntity.equipStack(EquipmentSlot.HEAD, itemStack);
                world.spawnNewEntityAndPassengers(mobEntity);

                mutable.setX(mutable.getX() + random.nextInt(5) - random.nextInt(5));
                mutable.setZ(mutable.getZ() + random.nextInt(5) - random.nextInt(5));
            }
        }
    }


    public enum AmbushEntity {
        SKELETON(EntityType.SKELETON, 1, 3),
        ZOMBIE(EntityType.ZOMBIE, 2, 5);

        static final AmbushEntity[] VALUES;

        static {
            VALUES = AmbushEntity.values();
        }

        final EntityType<? extends MobEntity> entityType;
        final int minSpawn, maxSpawn;


        AmbushEntity(EntityType<? extends MobEntity> entityType, int minSpawn, int maxSpawn) {
            this.entityType = entityType;
            this.minSpawn = minSpawn;
            this.maxSpawn = maxSpawn;
        }
    }
}
