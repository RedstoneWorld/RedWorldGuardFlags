/**
 * This is a plugin that adds some new 
 * WorldGuard flags as a WorldGuard add-on.
 * 
 * @author Robert Rauh alias RedstoneFuture
 */

package de.redstoneworld.redworldguardflags;

import de.redstoneworld.redworldguardflags.flags.environment.*;
import de.redstoneworld.redworldguardflags.flags.player.*;
import io.papermc.lib.PaperLib;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class RedWorldGuardFlags extends JavaPlugin {
	
	public void onEnable() {
		
		registerEvents();
	}
	
	public void onLoad() {
        FlagManager flagManager = new FlagManager(this);
        flagManager.registerWorldGuardFlags();
        
	}

	public void onDisable() {
        
    }

	private void registerEvents() {
        PluginManager pluginMgn = getServer().getPluginManager();
        
        // register Events with Bukkit:
        pluginMgn.registerEvents(new BlockDispense(this), this);
        pluginMgn.registerEvents(new BlockFade(this), this);
        pluginMgn.registerEvents(new CreatureSpawn(this), this);
        pluginMgn.registerEvents(new EntityChangeBlock(this), this);
        pluginMgn.registerEvents(new EntityTargetLivingEntity(this), this);
        pluginMgn.registerEvents(new HangingBreak(this), this);
        pluginMgn.registerEvents(new VehicleEntityCollision(this), this);

        pluginMgn.registerEvents(new BlockBreak(this), this);
        pluginMgn.registerEvents(new BlockPlace(this), this);
        pluginMgn.registerEvents(new EntityDamageByEntity(this), this);
        pluginMgn.registerEvents(new EntityToggleGlide(this), this);
        pluginMgn.registerEvents(new PlayerFish(this), this);
        pluginMgn.registerEvents(new PlayerInteract(this), this);
        pluginMgn.registerEvents(new PlayerInteractEntity(this), this);
        pluginMgn.registerEvents(new PlayerInteractLectern(this), this);
        
        
        // register Events with PaperMC:
        PaperLib.suggestPaper(this);
        
        if (PaperLib.isPaper()) {
            pluginMgn.registerEvents(new PlayerTrade(this), this);
        }
		
	}
		
}
