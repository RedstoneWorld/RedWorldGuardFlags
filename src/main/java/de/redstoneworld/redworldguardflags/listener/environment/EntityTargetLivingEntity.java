package de.redstoneworld.redworldguardflags.listener.environment;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import de.redstoneworld.redworldguardflags.FlagManager;
import de.redstoneworld.redworldguardflags.RedWorldGuardFlags;
import de.redstoneworld.redworldguardflags.util.WorldGuardUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class EntityTargetLivingEntity implements Listener {

    private final RedWorldGuardFlags plugin;

    public EntityTargetLivingEntity(RedWorldGuardFlags plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event) {
        ApplicableRegionSet set = WorldGuardUtil.getRegionSet(event.getEntity().getLocation());

        // Check if the flag applies and if it is set to deny:
        if (!set.testState(null, (StateFlag) FlagManager.FlagEnum.ENTITY_TARGET.getFlagObj())) {

            event.setCancelled(true);
        }

    }

}