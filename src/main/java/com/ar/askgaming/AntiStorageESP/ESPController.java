package com.ar.askgaming.AntiStorageESP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.ar.askgaming.NoCheatPlease;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.world.chunk.BaseChunk;
import com.github.retrooper.packetevents.protocol.world.chunk.Column;
import com.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerChunkData;

public class ESPController {

    public ESPController(NoCheatPlease plugin) {
    }

    private boolean enabled = true;
    private final Map<Player, List<ModifiedBlock>> modifiedBlocks = new HashMap<>();

    public void onChunkData(PacketSendEvent event, Player player) {

        if (!enabled) {
            return;
        }

        WrapperPlayServerChunkData e = new WrapperPlayServerChunkData(event);

        Column column = e.getColumn();

        // Iterate over all chunks in the column (chunks are stored in sections)
        BaseChunk[] chunks = column.getChunks();
        for (int sectionIndex = 0; sectionIndex < chunks.length; sectionIndex++) {
            BaseChunk chunk = chunks[sectionIndex];

            // Iterate over every block in the chunk (16x16x16 block area)
            for (int x = 0; x < 16; x++) {
                for (int y = 0; y < 16; y++) {
                    for (int z = 0; z < 16; z++) {

                        // Get the block ID for the current coordinates in the chunk
                        int blockId = chunk.getBlockId(x, y, z);
        
                        // Check if the block is a chest (based on block IDs)
                        if (StorageIdBlocks.getChestTileEntities().contains(blockId)) {
                            
                            //plugin.getLogger().info("Chest detected at X: " + x + " Y: " + y + " Z: " + z + " in chunk " + column.getX() + " " + column.getZ());
                            
                            // Convert chunk-local coordinates to world coordinates
                            int worldX = (column.getX() << 4) + x; 
                            int worldY = -64 + (sectionIndex * 16) + y;
                            int worldZ = (column.getZ() << 4) + z;

                            // Create a Location object using the player's world
                            Location location = new Location(player.getWorld(), worldX, worldY, worldZ);

                            //plugin.getLogger().info("Comparing with location X: " + worldX + " Y: " + worldY + " Z: " + worldZ + " type " + location.getBlock().getType());

                            double distanceSquared = player.getLocation().distance(location);
                            if (distanceSquared < 32) {
                                //plugin.getLogger().info("Chest ignored, too close.");
                                continue;
                            }

                            // Create the list of modified blocks if it doesn't exist for the player
                            modifiedBlocks.putIfAbsent(player, new ArrayList<>());

                            // Save the modified block in the player's list
                            WrappedBlockState blockState = chunk.get(x, y, z);
                            modifiedBlocks.get(player).add(new ModifiedBlock(blockState, blockId, location));
                            
                            // Hide the chest by changing it to AIR (set block ID to 0)
                            chunk.set(x, y, z, 0);
                        }
                    }
                }
            }
        }
    }
    public Map<Player, List<ModifiedBlock>> getModifiedblocks() {
        return modifiedBlocks;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
