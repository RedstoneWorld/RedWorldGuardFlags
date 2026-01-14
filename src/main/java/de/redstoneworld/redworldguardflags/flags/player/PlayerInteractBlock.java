package de.redstoneworld.redworldguardflags.flags.player;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import de.redstoneworld.redutilities.material.MaterialHelper;
import de.redstoneworld.redworldguardflags.FlagManager;
import de.redstoneworld.redworldguardflags.RedWorldGuardFlags;
import de.redstoneworld.redworldguardflags.flagtypes.StringFlag;
import de.redstoneworld.redworldguardflags.util.WorldGuardUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Set;

public class PlayerInteractBlock implements Listener {

    private final RedWorldGuardFlags plugin;

    public PlayerInteractBlock(RedWorldGuardFlags plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        Block clickedBlock = event.getClickedBlock();
        
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(event.getPlayer());
        
        if (!WorldGuardUtil.isRestrictedWorld(localPlayer.getWorld()) || WorldGuardUtil.hasWorldBypassPermission(localPlayer)) return;
        
        ApplicableRegionSet set = WorldGuardUtil.getRegionSet(clickedBlock.getLocation());
        
        // Get the resulting MATERIAL list from the flag definition.
        Set<Material> allowInteract = MaterialHelper.getMaterials(set.queryValue(null, StringFlag.ALLOW_BLOCK_INTERACT));
        Set<Material> denyInteract = MaterialHelper.getMaterials(set.queryValue(null, StringFlag.DENY_BLOCK_INTERACT));
        
        if ((denyInteract != null) && (denyInteract.contains(clickedBlock.getType()))) {
            event.setCancelled(true);
            plugin.getLogger().info("Cancelled block-interaction with " + event.getClickedBlock().getType() + " because of the regional '" 
                    + FlagManager.FlagEnum.DENY_INTERACT_BLOCKS.getFlagObj().getName() + "' flag result.");
            return;
        }
        
        if ((allowInteract != null) && (!allowInteract.contains(clickedBlock.getType()))) {
            event.setCancelled(true);
            plugin.getLogger().info("Cancelled block-interaction with " + event.getClickedBlock().getType() + " because it was not found in the regional '" 
                    + FlagManager.FlagEnum.ALLOW_INTERACT_BLOCKS.getFlagObj().getName() + "' flag result.");
            
        }
    }
    
}