package com.ar.askgaming;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.User;

public class PacketSendListener implements PacketListener{

    private final NoCheatPlease plugin;
    public PacketSendListener(NoCheatPlease plugin) { 
        this.plugin = plugin;

        plugin.getLogger().info("PacketEvents packet listener has been registered.");

    }

    @Override
    public void onPacketSend(PacketSendEvent event) {

        User user = event.getUser();
        UUID uuid = user.getUUID();
        if (uuid == null) {
            return;
        }
        Player player = plugin.getServer().getPlayer(uuid);
            
        // Useful to read the block id

        // if (event.getPacketType() == PacketType.Play.Server.BLOCK_CHANGE) {
        //     // Use the correct wrapper to process this packet.
        //     WrapperPlayServerBlockChange e = new WrapperPlayServerBlockChange(event);
        //     plugin.getLogger().info("Block change packet send to " + e.getBlockId());
        // }

        if (event.getPacketType() == PacketType.Play.Server.CHUNK_DATA) {
           plugin.getESPController().onChunkData(event, player);            
        }
    }
}