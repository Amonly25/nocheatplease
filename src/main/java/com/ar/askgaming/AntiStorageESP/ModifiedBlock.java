package com.ar.askgaming.AntiStorageESP;

import org.bukkit.Location;

import com.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;

public class ModifiedBlock {
    private final Integer blockId;
    private final Location location;
    private final WrappedBlockState blockState;

    public ModifiedBlock(WrappedBlockState block, int blockId, Location location) {
        blockState = block;
        this.blockId = blockId;
        this.location = location;
    }

    public Integer getBlockId() {
        return blockId;
    }

    public WrappedBlockState getBlockState() {
        return blockState;
    }

    public Location getLocation() {
        return location;
    }
}
