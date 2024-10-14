package de.redstoneworld.redworldguardflags.listener.building;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import de.redstoneworld.redworldguardflags.Flags;
import de.redstoneworld.redworldguardflags.RedWorldGuardFlags;
import de.redstoneworld.redworldguardflags.flagtypes.StringFlag;
import de.redstoneworld.redworldguardflags.util.BukkitUtil;
import de.redstoneworld.redworldguardflags.util.WorldGuardUtil;
import de.redstoneworld.redutilities.material.MaterialHelper;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

public class BlockPlace implements Listener {

    private final RedWorldGuardFlags plugin;

    public BlockPlace(RedWorldGuardFlags plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event) {
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(event.getPlayer());
        Material targetMaterial = event.getBlock().getType();
        
        if (!WorldGuardUtil.isRestrictedWorld(localPlayer.getWorld()) || WorldGuardUtil.hasWorldBypassPermission(localPlayer)) return;
        
        ApplicableRegionSet set = WorldGuardUtil.getRegionSet(event.getBlock().getLocation());
        
        // Get the resulting MATERIAL list from the flag definition.
        Set<Material> allowPlace = MaterialHelper.getMaterials(set.queryValue(null, StringFlag.ALLOW_PLACE));
        Set<Material> denyPlace = MaterialHelper.getMaterials(set.queryValue(null, StringFlag.DENY_PLACE));
        
        if ((denyPlace != null) && (denyPlace.contains(targetMaterial))) {
            event.setCancelled(true);
            plugin.getLogger().info("Cancelled block-place because of the regional '" + Flags.FlagEnum.DENY_PLACE_BLOCKS.getFlagObj().getName() 
                    + "' flag result.");
            return;
        }
        
        if ((allowPlace != null) && (!allowPlace.contains(targetMaterial))) {
            event.setCancelled(true);
            plugin.getLogger().info("Cancelled block-place because it was not found in the regional '" 
                    + Flags.FlagEnum.ALLOW_PLACE_BLOCKS.getFlagObj().getName() + "' flag result.");
            return;
        }
        
        // Check if the Integer flag is set:
        Object resetFlagResult = set.queryValue(null, Flags.FlagEnum.RESET_BLOCKS.getFlagObj());
        int resetDelay = 0;
        if (resetFlagResult != null) resetDelay = (int) resetFlagResult;
        
        if (resetDelay > 0) {
            BlockState blockStateCache = event.getBlockReplacedState();
            
            plugin.getLogger().info("Allow temporary block-place because of the regional '" + Flags.FlagEnum.RESET_BLOCKS.getFlagObj().getName() 
                    + "' flag result.");
            
            new BukkitRunnable() {
                public void run() {
                    
                    BukkitUtil.resetBlock(blockStateCache, event.getBlock().getLocation());
                
                }
            }.runTaskLater(plugin, resetDelay * 20L);
            
            return;
        }
        
/*        plugin.getLogger().info("Allow block-place because of the regional '" + Flags.FlagEnum.ALLOW_PLACE_BLOCKS.getFlagObj().getName() 
                + "' flag result.");*/
        
    }
    
}