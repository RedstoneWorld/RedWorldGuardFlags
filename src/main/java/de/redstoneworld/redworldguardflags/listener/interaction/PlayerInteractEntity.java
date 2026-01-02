package de.redstoneworld.redworldguardflags.listener.interaction;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import de.redstoneworld.redutilities.entity.EntityHelper;
import de.redstoneworld.redworldguardflags.FlagManager;
import de.redstoneworld.redworldguardflags.RedWorldGuardFlags;
import de.redstoneworld.redworldguardflags.flagtypes.StringFlag;
import de.redstoneworld.redworldguardflags.util.WorldGuardUtil;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.Set;

public class PlayerInteractEntity implements Listener {

    private final RedWorldGuardFlags plugin;

    public PlayerInteractEntity(RedWorldGuardFlags plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(event.getPlayer());
        EntityType targetEntityType = event.getRightClicked().getType();
        
        if (!WorldGuardUtil.isRestrictedWorld(localPlayer.getWorld()) || WorldGuardUtil.hasWorldBypassPermission(localPlayer)) return;
        
        ApplicableRegionSet set = WorldGuardUtil.getRegionSet(event.getRightClicked().getLocation());
        
        // Get the resulting MATERIAL list from the flag definition.
        Set<EntityType> allowInteract = EntityHelper.getEntityTypes(set.queryValue(null, StringFlag.ALLOW_ENTITY_INTERACT));
        Set<EntityType> denyInteract = EntityHelper.getEntityTypes(set.queryValue(null, StringFlag.DENY_ENTITY_INTERACT));
        
        if ((denyInteract != null) && (denyInteract.contains(targetEntityType))) {
            event.setCancelled(true);
            plugin.getLogger().info("Cancelled entity-interaction with " + event.getRightClicked().getType() + " because of the regional '" 
                    + FlagManager.FlagEnum.DENY_INTERACT_ENTITY.getFlagObj().getName() + "' flag result.");
            return;
        }
        
        if ((allowInteract != null) && (!allowInteract.contains(targetEntityType))) {
            event.setCancelled(true);
            plugin.getLogger().info("Cancelled entity-interaction with " + event.getRightClicked().getType() + " because it was not found in the regional '" 
                    + FlagManager.FlagEnum.ALLOW_INTERACT_ENTITY.getFlagObj().getName() + "' flag result.");
            
        }
    }
    
}