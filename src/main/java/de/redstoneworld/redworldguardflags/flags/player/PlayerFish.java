package de.redstoneworld.redworldguardflags.flags.player;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import de.redstoneworld.redworldguardflags.FlagManager;
import de.redstoneworld.redworldguardflags.RedWorldGuardFlags;
import de.redstoneworld.redworldguardflags.util.WorldGuardUtil;
import org.bukkit.entity.Entity;
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
        if (event.getCaught() == null) return;
        Entity caught = event.getCaught();
        
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(event.getPlayer());
        
        if (!WorldGuardUtil.isRestrictedWorld(localPlayer.getWorld()) || WorldGuardUtil.hasWorldBypassPermission(localPlayer)) return;
        
        ApplicableRegionSet set = WorldGuardUtil.getRegionSet(caught.getLocation());

        // Check if the flag applies and if it is set to deny:
        if (!set.testState(null, (StateFlag) FlagManager.FlagEnum.ALLOW_FISHING.getFlagObj())) {

            event.setCancelled(true);
        }

    }

}