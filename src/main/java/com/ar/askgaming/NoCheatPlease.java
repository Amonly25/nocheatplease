package com.ar.askgaming;

import org.bukkit.plugin.java.JavaPlugin;

import com.ar.askgaming.AntiStorageESP.ESPController;
import com.ar.askgaming.Listeners.PlayerMoveListener;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;

import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;

public class NoCheatPlease extends JavaPlugin {

    private ESPController espController;

    @Override
    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        //On Bukkit, calling this here is essential, hence the name "load"
        PacketEvents.getAPI().load();

        PacketEvents.getAPI().getEventManager().registerListener(
        new PacketSendListener(this), PacketListenerPriority.NORMAL);
    }

    @Override
    public void onEnable() {
        //Initialize!
        PacketEvents.getAPI().init();

        new PlayerMoveListener(this);

        espController = new ESPController(this);

        new Commands(this);
    }

    @Override
    public void onDisable() {
        //Terminate the instance (clean up process)
        PacketEvents.getAPI().terminate();
    }
    public ESPController getESPController() {
        return espController;
    }
}