package com.thegates.smobs;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;


public class Main implements ModInitializer {

    public static String MOD_ID = "tg_smartmobs";

    public static String MOD_PREFIX = "[smobs]: ";


    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(this::serverStarted);
    }


    private void serverStarted(MinecraftServer server) {

    }
}
