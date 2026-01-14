package de.redstoneworld.redworldguardflags.listener.environment;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import de.redstoneworld.redworldguardflags.FlagManager;
import de.redstoneworld.redworldguardflags.RedWorldGuardFlags;
import de.redstoneworld.redworldguardflags.util.WorldGuardUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeath implements Listener {

    private final RedWorldGuardFlags plugin;

    public EntityDeath(RedWorldGuardFlags plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDeath(EntityDeathEvent event) {
        ApplicableRegionSet set = WorldGuardUtil.getRegionSet(event.getEntity().getLocation());

        // Check if the flag applies and if it is set to deny:
        if (!set.testState(null, (StateFlag) FlagManager.FlagEnum.ENTITY_LOOT_ITEMS.getFlagObj())) {
            
            // Removing the dropped items:
            event.getDrops().clear();
        }

    }

}