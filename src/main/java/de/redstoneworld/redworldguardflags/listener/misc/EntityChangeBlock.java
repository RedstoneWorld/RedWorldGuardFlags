package de.redstoneworld.redworldguardflags.listener.misc;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import de.redstoneworld.redworldguardflags.Flags;
import de.redstoneworld.redworldguardflags.RedWorldGuardFlags;
import de.redstoneworld.redworldguardflags.util.WorldGuardUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class EntityChangeBlock implements Listener {

    private final RedWorldGuardFlags plugin;

    public EntityChangeBlock(RedWorldGuardFlags plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        ApplicableRegionSet set = WorldGuardUtil.getRegionSet(event.getBlock().getLocation());

        // Check if the flag applies and if it is set to deny
        if (!set.testState(null, (StateFlag) Flags.FlagEnum.WEAVING_DEATH_PLACE.getFlagObj())) {
            
            // Check whether the block change is a "COBWEB" block
            if (event.getTo() == Material.COBWEB) {
                event.setCancelled(true);
            }
        }

    }

}