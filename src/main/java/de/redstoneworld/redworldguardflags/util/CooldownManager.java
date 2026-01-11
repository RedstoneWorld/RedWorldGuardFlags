package de.redstoneworld.redworldguardflags.util;

import de.redstoneworld.redworldguardflags.RedWorldGuardFlags;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class CooldownManager {
    
    private final RedWorldGuardFlags plugin;
    
    // BukkitTask map for the per-player cooldowns:
    private final HashMap<UUID, BukkitTask> cooldownTasks = new HashMap<>();


    /**
     * This is a helper class for working with a per-player cooldown.
     * 
     * @param plugin (RedWorldGuardFlags) the plugin
     */
    public CooldownManager(RedWorldGuardFlags plugin) {
        this.plugin = plugin;
    }

    /**
     * This method starts the cooldown for the specific player. The cooldown 
     * will automatically stop after 2 seconds if it has not been canceled 
     * beforehand.
     * 
     * @param player (Player) the target player
     */
    public void startCooldown(Player player) {
        // Is the player already in cooldown?
        if (isPlayerInCooldown(player)) return;
        
        // Deactivate cooldown after short time (2 seconds).
        BukkitTask task = Bukkit.getScheduler().runTaskLater(plugin, () -> {
            cooldownTasks.remove(player.getUniqueId());
        }, 40);
        
        cooldownTasks.put(player.getUniqueId(), task);
    }

    /**
     * This method cancels the cooldown (task) for the specific player.
     * 
     * @param player (Player) the target player
     */
    public void cancelCooldown(Player player) {
        BukkitTask task = cooldownTasks.remove(player.getUniqueId());
        
        if (task != null) task.cancel();
    }

    /**
     * This method checks whether the cooldown is currently active 
     * for the specific player.
     * 
     * @param player (Player) the target player
     * @return 'true', if the player is in cooldown
     */
    public boolean isPlayerInCooldown(Player player) {
        if (cooldownTasks.containsKey(player.getUniqueId())) return true;
        return false;
    }

    /**
     * This method stops all per-player cooldown tasks. This is mainly 
     * recommended when disabling or reloading the plugin.
     */
    public void cancelAllCooldownTasks() {
        cooldownTasks.values().forEach(BukkitTask::cancel);
        cooldownTasks.clear();
    }
    
}
