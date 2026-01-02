package de.redstoneworld.redworldguardflags.flags.player;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import de.redstoneworld.redworldguardflags.FlagManager;
import de.redstoneworld.redworldguardflags.RedWorldGuardFlags;
import de.redstoneworld.redworldguardflags.util.WorldGuardUtil;
import io.papermc.paper.event.player.PlayerTradeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PlayerTrade implements Listener {

    private final RedWorldGuardFlags plugin;

    public PlayerTrade(RedWorldGuardFlags plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerTrade(PlayerTradeEvent event) {
        ApplicableRegionSet set = WorldGuardUtil.getRegionSet(event.getPlayer().getLocation());

        // Check if the flag applies and if it is set to deny:
        if (!set.testState(null, (StateFlag) FlagManager.FlagEnum.ALLOW_TRADING.getFlagObj())) {

            event.setCancelled(true);
        }

    }

}