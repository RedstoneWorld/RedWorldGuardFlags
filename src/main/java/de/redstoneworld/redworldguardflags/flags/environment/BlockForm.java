package de.redstoneworld.redworldguardflags.flags.environment;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import de.redstoneworld.redworldguardflags.FlagManager;
import de.redstoneworld.redworldguardflags.RedWorldGuardFlags;
import de.redstoneworld.redworldguardflags.util.WorldGuardUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

public class BlockForm implements Listener {

    private final RedWorldGuardFlags plugin;

    public BlockForm(RedWorldGuardFlags plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockForm(BlockFormEvent event) {
        ApplicableRegionSet set = WorldGuardUtil.getRegionSet(event.getBlock().getLocation());

        // Check if the flag applies and if it is set to deny:
        if (!set.testState(null, (StateFlag) FlagManager.FlagEnum.STONE_GENERATION.getFlagObj())) {

            // Check if the generated result is one of the solid blocks:
            if ((event.getNewState().getType() == Material.STONE) || (event.getNewState().getType() == Material.COBBLESTONE) 
                    || (event.getNewState().getType() == Material.OBSIDIAN)) {
                event.setCancelled(true);
            }
        }

    }

}