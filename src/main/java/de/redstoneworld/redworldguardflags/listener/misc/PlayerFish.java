package de.redstoneworld.redworldguardflags.listener.misc;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import de.redstoneworld.redworldguardflags.Flags;
import de.redstoneworld.redworldguardflags.RedWorldGuardFlags;
import de.redstoneworld.redworldguardflags.util.WorldGuardUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class PlayerFish implements Listener {

    private final RedWorldGuardFlags plugin;

    public PlayerFish(RedWorldGuardFlags plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerFish(PlayerFishEvent event) {
        ApplicableRegionSet set = WorldGuardUtil.getRegionSet(event.getPlayer().getLocation());

        // Check if the flag applies and if it is set to deny:
        if (!set.testState(null, (StateFlag) Flags.FlagEnum.ALLOW_FISHING.getFlagObj())) {

            event.setCancelled(true);
        }

    }

}