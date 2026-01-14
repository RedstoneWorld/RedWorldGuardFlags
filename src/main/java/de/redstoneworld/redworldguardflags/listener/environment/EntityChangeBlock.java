package de.redstoneworld.redworldguardflags.listener.environment;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import de.redstoneworld.redworldguardflags.FlagManager;
import de.redstoneworld.redworldguardflags.RedWorldGuardFlags;
import de.redstoneworld.redworldguardflags.util.WorldGuardUtil;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
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
        
        if (event.getTo() == Material.COBWEB) {
            
            // Check if the flag applies and if it is set to deny:
            if (!set.testState(null, (StateFlag) FlagManager.FlagEnum.WEAVING_DEATH_PLACE.getFlagObj())) {
                event.setCancelled(true);
            }
            return;
        }
        
        if ((event.getBlock().getType() == Material.GRASS_BLOCK) && (event.getTo() == Material.DIRT)) {
            
            // The only mob that changes a block when eating is the sheep.
            if (event.getEntityType() != EntityType.SHEEP) return;
            
            // Check if the flag applies and if it is set to deny:
            if (!set.testState(null, (StateFlag) FlagManager.FlagEnum.ENTITY_EATING_DESTROY.getFlagObj())) {
                event.setCancelled(true);
            }
        }
        
    }

}