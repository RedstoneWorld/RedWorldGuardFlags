package de.redstoneworld.redworldguardflags.listener.misc;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import de.redstoneworld.redworldguardflags.Flags;
import de.redstoneworld.redworldguardflags.RedWorldGuardFlags;
import de.redstoneworld.redworldguardflags.util.WorldGuardUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;

public class EntityToggleGlide implements Listener {

    private final RedWorldGuardFlags plugin;

    public EntityToggleGlide(RedWorldGuardFlags plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityToggleGlide(EntityToggleGlideEvent event) {
        ApplicableRegionSet set = WorldGuardUtil.getRegionSet(event.getEntity().getLocation());

        // Check if the flag applies and if it is set to deny
        if (!set.testState(null, (StateFlag) Flags.FlagEnum.ELYTRA_USE.getFlagObj())) {

            // Check if the entity is a player
            if (event.getEntity() instanceof Player) {
                event.setCancelled(true);
            }
        }

    }

}