package com.thegates.smobs;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.spawner.Spawner;

import java.util.Random;

public class AmbushSpawner implements Spawner {

    int cooldown = 0;


    @Override
    public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
        if (!spawnMonsters) {
            return 0;
        }
        --cooldown;
        if (cooldown > 0) {
            return 0;
        }

        this.cooldown += 11000 + world.getRandom().nextInt(2400);

        Random random = world.getRandom();

        int i = world.getPlayers().size();
        if (i < 1) {
            return 0;
        }
        PlayerEntity playerEntity = world.getPlayers().get(random.nextInt(i));
        if (playerEntity.isSpectator()) {
            return 0;
        }
        if (world.isNearOccupiedPointOfInterest(playerEntity.getBlockPos(), 2)) {
            return 0;
        }

        new Ambush(world, playerEntity.getBlockPos()).spawn();

        return 1;
    }
}
