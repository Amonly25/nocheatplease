package com.ar.askgaming.AntiStorageESP;

import java.util.HashSet;
import java.util.Set;

public class StorageIdBlocks {

    private static final Set<Integer> chestTileEntities = new HashSet<>();

    static {
        addSimpleChests(chestTileEntities);
        addDoubleChests(chestTileEntities); 
        addEnderChests(chestTileEntities);
        addTrappedChests(chestTileEntities);
        addDoubleTrappedChests(chestTileEntities);
        addBarrels(chestTileEntities);
        addShulkerBoxes(chestTileEntities);
    }
    // Why a simple chest have 4 different ids?
    // For each facing direction.
    private static void addSimpleChests(Set<Integer> set) {
        int[] simpleChests = {3022, 3010, 3028, 3016};
        for (int chest : simpleChests) {
            set.add(chest);
        }
    }

    private static void addDoubleChests(Set<Integer> set) {
        int[] doubleChests = {3030, 3018, 3024, 3012};
        for (int chest : doubleChests) {
            set.add(chest);
        }
    }

    private static void addEnderChests(Set<Integer> set) {
        int[] enderChests = {8288, 8294, 8290, 8292};
        for (int chest : enderChests) {
            set.add(chest);
        }
    }

    private static void addTrappedChests(Set<Integer> set) {
        int[] trappedChests = {9931, 9919, 9937, 9925};
        for (int chest : trappedChests) {
            set.add(chest);
        }
    }

    private static void addDoubleTrappedChests(Set<Integer> set) {
        int[] doubleTrappedChests = {9927, 9933, 9921, 9939};
        for (int chest : doubleTrappedChests) {
            set.add(chest);
        }
    }

    private static void addBarrels(Set<Integer> set) {
        int[] barrels = {19430, 19422, 19424, 19426, 19428};
        for (int barrel : barrels) {
            set.add(barrel);
        }
    }
    // Note this only apply to uncolored shulker boxes
    // Colored shulker boxes have different ids
    private static void addShulkerBoxes(Set<Integer> set) {
        int[] shulkerBoxes = {13579, 13575, 13576, 13577, 13578};
        for (int shulker : shulkerBoxes) {
            set.add(shulker);
        }
    }

    public static Set<Integer> getChestTileEntities() {
        return chestTileEntities;
    }

}
