package de.redstoneworld.redworldguardflags.flags.player;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import de.redstoneworld.redutilities.entity.EntityHelper;
import de.redstoneworld.redworldguardflags.FlagManager;
import de.redstoneworld.redworldguardflags.RedWorldGuardFlags;
import de.redstoneworld.redworldguardflags.flagtypes.StringFlag;
import de.redstoneworld.redworldguardflags.util.WorldGuardUtil;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Set;

public class EntityDamageByEntity implements Listener {

    private final RedWorldGuardFlags plugin;

    public EntityDamageByEntity(RedWorldGuardFlags plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        Player damager = (Player) event.getDamager();
        
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(damager);
        EntityType targetEntityType = event.getEntityType();
        
        if (!WorldGuardUtil.isRestrictedWorld(localPlayer.getWorld()) || WorldGuardUtil.hasWorldBypassPermission(localPlayer)) return;
        
        ApplicableRegionSet set = WorldGuardUtil.getRegionSet(event.getEntity().getLocation());
        
        // Get the resulting MATERIAL list from the flag definition.
        Set<EntityType> allowInteract = EntityHelper.getEntityTypes(set.queryValue(null, StringFlag.ALLOW_ENTITY_DAMAGE));
        Set<EntityType> denyInteract = EntityHelper.getEntityTypes(set.queryValue(null, StringFlag.DENY_ENTITY_DAMAGE));
        
        if ((denyInteract != null) && (denyInteract.contains(targetEntityType))) {
            event.setCancelled(true);
            plugin.getLogger().info("Cancelled entity-damage with " + targetEntityType + " because of the regional '" 
                    + FlagManager.FlagEnum.DENY_DAMAGE_ENTITY.getFlagObj().getName() + "' flag result.");
            return;
        }
        
        if ((allowInteract != null) && (!allowInteract.contains(targetEntityType))) {
            event.setCancelled(true);
            plugin.getLogger().info("Cancelled entity-damage with " + targetEntityType + " because it was not found in the regional '" 
                    + FlagManager.FlagEnum.ALLOW_DAMAGE_ENTITY.getFlagObj().getName() + "' flag result.");
            
        }
    }
    
}