package de.redstoneworld.redworldguardflags.flags.environment;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import de.redstoneworld.redworldguardflags.FlagManager;
import de.redstoneworld.redworldguardflags.RedWorldGuardFlags;
import de.redstoneworld.redworldguardflags.util.WorldGuardUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawn implements Listener {

    private final RedWorldGuardFlags plugin;

    /**
     * Native entities can be spawned if the "mob-spawning" flag is enabled. The flags implemented here,
     * override the restriction for:
     *
     * (A) spawning entities by manually using spawn eggs and
     * (B) spawning entities by dispensing spawn eggs.
     */
    public CreatureSpawn(RedWorldGuardFlags plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if ((event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.SPAWNER_EGG) &&
                (event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.DISPENSE_EGG)) return;
        
        ApplicableRegionSet set = WorldGuardUtil.getRegionSet(event.getLocation());

        // Spawn-egg using:
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG) {
            // Check if the flag applies and if it is set to deny:
            if (set.testState(null, (StateFlag) FlagManager.FlagEnum.SPAWNEGG_USE.getFlagObj())) return;
            
            event.setCancelled(true);
        }

        // Spawn-egg dispensing:
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.DISPENSE_EGG) {
            // Check if the flag applies and if it is set to deny:
            if (set.testState(null, (StateFlag) FlagManager.FlagEnum.SPAWNEGG_DISPENSE.getFlagObj())) return;
            
            event.setCancelled(true);
        }

    }

}