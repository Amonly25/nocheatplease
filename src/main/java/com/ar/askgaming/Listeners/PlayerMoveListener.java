package com.ar.askgaming.Listeners;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.ar.askgaming.NoCheatPlease;
import com.ar.askgaming.AntiStorageESP.ModifiedBlock;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.util.Vector3i;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerBlockChange;

public class PlayerMoveListener implements Listener{

    private NoCheatPlease plugin;
    public PlayerMoveListener(NoCheatPlease plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        List<ModifiedBlock> blocks = plugin.getESPController().getModifiedblocks().get(player);
        
        if (blocks == null || blocks.isEmpty()) {
            return;
        }
        if (event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockY() == event.getTo().getBlockY() && event.getFrom().getBlockZ() == event.getTo().getBlockZ()) {
            return;
        }
    
        Iterator<ModifiedBlock> iterator = blocks.iterator();
        while (iterator.hasNext()) {
            ModifiedBlock modifiedBlock = iterator.next();
            Location location = modifiedBlock.getLocation();
            
            if (location.getWorld().equals(player.getWorld()) == false) {
                continue;
            }

            if (player.getLocation().distance(location) < 32) {
                Block block = location.getBlock();
                // plugin.getLogger().info("Player is near the block at x " + location.getBlockX() + 
                //                         " y " + location.getBlockY() + 
                //                         " z " + location.getBlockZ() + " type " + block.getType());
                
                Vector3i blockPos = new Vector3i(block.getX(), block.getY(), block.getZ());
                Integer blockId = modifiedBlock.getBlockId();
                
                WrapperPlayServerBlockChange blockChange = new WrapperPlayServerBlockChange(blockPos, blockId);
                PacketEvents.getAPI().getPlayerManager().sendPacket(player, blockChange);
        
                iterator.remove();
            }
        }  
    }
}
