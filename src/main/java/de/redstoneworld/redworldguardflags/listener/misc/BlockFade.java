package de.redstoneworld.redworldguardflags.listener.misc;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import de.redstoneworld.redworldguardflags.FlagManager;
import de.redstoneworld.redworldguardflags.RedWorldGuardFlags;
import de.redstoneworld.redworldguardflags.util.WorldGuardUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;

public class BlockFade implements Listener {

    private final RedWorldGuardFlags plugin;

    public BlockFade(RedWorldGuardFlags plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockFade(BlockFadeEvent event) {
        ApplicableRegionSet set = WorldGuardUtil.getRegionSet(event.getBlock().getLocation());

        // Check if the flag applies and if it is set to deny:
        if (!set.testState(null, (StateFlag) FlagManager.FlagEnum.FIRE_BURNING_OUT.getFlagObj())) {

            // Check if the target block is "FIRE":
            if ((event.getBlock().getType() == Material.FIRE) || (event.getBlock().getType() == Material.SOUL_FIRE)) {
                event.setCancelled(true);
            }
        }

    }

}