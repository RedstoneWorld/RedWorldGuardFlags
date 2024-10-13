package de.redstoneworld.redworldguardflags.util;

import org.bukkit.Location;
import org.bukkit.block.*;
import org.bukkit.block.banner.Pattern;

public class BukkitUtil {
    
    public static void resetBlock(BlockState originalBlockState, Location targetLocation) {
        
        // Restore the basic BlockData (block type, orientation, etc.)
        targetLocation.getBlock().setBlockData(originalBlockState.getBlockData());
        BlockState currentState = targetLocation.getBlock().getState();
        
        
        // If the block is a Sign, restore the text written on it
        if (originalBlockState instanceof Sign) {
            Sign originalSign = (Sign) originalBlockState;
            Sign newSign = (Sign) currentState;
            for (int i = 0; i < originalSign.getLines().length; i++) {
                newSign.setLine(i, originalSign.getLine(i));
            }
        }

        // If the block is a CreatureSpawner (e.g., Mob Spawner), restore the entity type and spawn data
        if (originalBlockState instanceof CreatureSpawner) {
            CreatureSpawner originalSpawner = (CreatureSpawner) originalBlockState;
            CreatureSpawner newSpawner = (CreatureSpawner) currentState;
            newSpawner.setSpawnedType(originalSpawner.getSpawnedType());
            newSpawner.setDelay(originalSpawner.getDelay());
        }
        
        // If the block is a Banner, restore its patterns
        if (originalBlockState instanceof Banner) {
            Banner originalBanner = (Banner) originalBlockState;
            Banner newBanner = (Banner) currentState;
            newBanner.setBaseColor(originalBanner.getBaseColor());  // Restore the base color
            for (Pattern pattern : originalBanner.getPatterns()) {
                newBanner.addPattern(pattern);  // Restore all patterns
            }
        }
        
        // Optionally, other specific block type handlings here (e.g. flowers, beds, etc.)
        
        
        // Force update the block state to apply all changes to the world
        currentState.update(true, false);  // 'true' forces the state to update, 'false' prevents physics updates
        
    }
}
