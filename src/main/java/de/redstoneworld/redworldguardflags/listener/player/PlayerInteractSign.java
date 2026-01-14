package de.redstoneworld.redworldguardflags.listener.player;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import de.redstoneworld.redworldguardflags.FlagManager;
import de.redstoneworld.redworldguardflags.RedWorldGuardFlags;
import de.redstoneworld.redworldguardflags.util.WorldGuardUtil;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.HangingSign;
import org.bukkit.block.data.type.WallHangingSign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;

public class PlayerInteractSign implements Listener {

    private final RedWorldGuardFlags plugin;
    
    public PlayerInteractSign(RedWorldGuardFlags plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        Block clickedBlock = event.getClickedBlock();
        
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(event.getPlayer());
        
        if (!WorldGuardUtil.isRestrictedWorld(localPlayer.getWorld()) || WorldGuardUtil.hasWorldBypassPermission(localPlayer)) return;
        
        ApplicableRegionSet set = WorldGuardUtil.getRegionSet(clickedBlock.getLocation());

        // Check if the flag applies and if it is set to deny
        if (!set.testState(null, (StateFlag) FlagManager.FlagEnum.SIGN_EDIT.getFlagObj())) {
            
            if (!(event.getClickedBlock().getState() instanceof Sign)) return;
            
            if (!(event.getAction().isRightClick())) return;
            
            ItemStack item = event.getItem();
            
            // Check if the player try to place a block by sneaking:
            if (event.getPlayer().isSneaking()) {
                if ((item != null) && (item.getType().isBlock())) return;
            }
            
            // Check if the player try to place a new hanging sign on it:
            if ((item != null) && (item.getType().name().endsWith("_HANGING_SIGN"))) {

                BlockData blockData = clickedBlock.getBlockData();
                BlockFace clickedFace = event.getBlockFace();
                
                // HangingSign:
                if (blockData instanceof HangingSign hangingSign) {
                    BlockFace signFacing = hangingSign.getRotation();
                    
                    // Front side or back side?
                    if ((clickedFace != signFacing) && (clickedFace != signFacing.getOppositeFace())) return;
                }
                
                // WallHangingSign:
                if (blockData instanceof WallHangingSign wallHangingSign) {
                    BlockFace signFacing = wallHangingSign.getFacing();
                    
                    // Front side or back side?
                    if ((clickedFace != signFacing) && (clickedFace != signFacing.getOppositeFace())) return;
                }
            }
            
            event.setCancelled(true);
        }

    }

}