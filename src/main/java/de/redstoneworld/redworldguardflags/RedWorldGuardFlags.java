/**
 * This is a plugin that adds some new 
 * WorldGuard flags as a WorldGuard add-on.
 * 
 * @author Robert Rauh alias RedstoneFuture
 */

package de.redstoneworld.redworldguardflags;

import de.redstoneworld.redworldguardflags.listener.damage.EntityDamageByEntity;
import de.redstoneworld.redworldguardflags.listener.interaction.PlayerInteract;
import de.redstoneworld.redworldguardflags.listener.interaction.PlayerInteractEntity;
import de.redstoneworld.redworldguardflags.listener.misc.*;
import de.redstoneworld.redworldguardflags.listener.building.BlockBreak;
import de.redstoneworld.redworldguardflags.listener.building.BlockPlace;
import io.papermc.lib.PaperLib;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class RedWorldGuardFlags extends JavaPlugin {
	
	public void onEnable() {
		
		registerEvents();
	}
	
	public void onLoad() {
        Flags flags = new Flags(this);
        flags.registerWorldGuardFlags();
        
	}

	public void onDisable() {
        
    }

	private void registerEvents() {
        PluginManager pluginMgn = getServer().getPluginManager();
        
        // register Events with Bukkit:
        pluginMgn.registerEvents(new BlockBreak(this), this);
        pluginMgn.registerEvents(new BlockPlace(this), this);
        
        pluginMgn.registerEvents(new EntityDamageByEntity(this), this);
        
        pluginMgn.registerEvents(new PlayerInteract(this), this);
        pluginMgn.registerEvents(new PlayerInteractEntity(this), this);
        
        pluginMgn.registerEvents(new BlockDispense(this), this);
        pluginMgn.registerEvents(new BlockFade(this), this);
        pluginMgn.registerEvents(new CreatureSpawn(this), this);
        pluginMgn.registerEvents(new EntityTargetLivingEntity(this), this);
        pluginMgn.registerEvents(new EntityToggleGlide(this), this);
        pluginMgn.registerEvents(new HangingBreak(this), this);
        pluginMgn.registerEvents(new LecternBookPlace(this), this);
        pluginMgn.registerEvents(new PlayerFish(this), this);
        pluginMgn.registerEvents(new VehicleEntityCollision(this), this);


        // register Events with PaperMC:
        PaperLib.suggestPaper(this);
        
        if (PaperLib.isPaper()) {
            pluginMgn.registerEvents(new PlayerTrade(this), this);
        }
		
	}
		
}
