package de.redstoneworld.redworldguardflags.listener.environment;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import de.redstoneworld.redworldguardflags.FlagManager;
import de.redstoneworld.redworldguardflags.RedWorldGuardFlags;
import de.redstoneworld.redworldguardflags.util.WorldGuardUtil;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;

public class VehicleEntityCollision implements Listener {

    private final RedWorldGuardFlags plugin;

    public VehicleEntityCollision(RedWorldGuardFlags plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onVehicleEntityCollision(VehicleEntityCollisionEvent event) {
        ApplicableRegionSet set = WorldGuardUtil.getRegionSet(event.getEntity().getLocation());

        // Check if the flag applies and if it is set to deny:
        if (!set.testState(null, (StateFlag) FlagManager.FlagEnum.VEHICLE_ENTITY_COLLISION.getFlagObj())) {

            // Check if the collided entity is a PLAYER or a MOB:
            if (event.getEntity() instanceof Player || event.getEntity() instanceof Mob) {
                event.setCancelled(true);
            }
        }

    }

}