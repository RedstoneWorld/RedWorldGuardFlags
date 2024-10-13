package de.redstoneworld.redworldguardflags.util;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.config.WorldConfiguration;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Location;

public class WorldGuardUtil {
    
    public static ApplicableRegionSet getRegionSet(Location location) {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        
        return query.getApplicableRegions(BukkitAdapter.adapt(location));
    }

    /**
     * This method returns whether the "regions.enable" option is activated for the world or not. 
     * The setting is taken from the WorldGuard main config (`config.yml`) and can be specified 
     * with the world config (`/worlds/%world-name%/config.yml`).
     * 
     * @param world (WorldGuard world) the target world
     * @return 'true' if the region-feature is activated in this world
     */
    public static boolean isRestrictedWorld(World world) {
        WorldConfiguration worldConfig = WorldGuardPlugin.inst().getConfigManager().get(world);
        
        return (worldConfig.useRegions);
    }

    /**
     * Check whether a player has bypass permission in the corresponding world of target location.
     * 
     * @param localPlayer (LocalPlayer) the target player
     * @return 'true' if the player has bypass permission in the corresponding world
     */
    public static boolean hasWorldBypassPermission(LocalPlayer localPlayer) {
        return WorldGuard.getInstance().getPlatform().getSessionManager().hasBypass(localPlayer, localPlayer.getWorld());
    }

}
