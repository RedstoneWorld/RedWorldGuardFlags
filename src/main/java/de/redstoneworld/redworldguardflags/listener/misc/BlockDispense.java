package de.redstoneworld.redworldguardflags.listener.misc;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import de.redstoneworld.redworldguardflags.Flags;
import de.redstoneworld.redworldguardflags.RedWorldGuardFlags;
import de.redstoneworld.redworldguardflags.util.WorldGuardUtil;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockDispense implements Listener {

    private final RedWorldGuardFlags plugin;

    public BlockDispense(RedWorldGuardFlags plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockDispense(BlockDispenseEvent event) {
        ApplicableRegionSet set = WorldGuardUtil.getRegionSet(event.getBlock().getLocation());

        // Check if the flag applies and if it is set to deny
        if (!set.testState(null, (StateFlag) Flags.FlagEnum.DISPENSE_NBT_SPAWNEGGS.getFlagObj())) {

            // Check if the item is a SpawnEgg and has the "EntityTag" NBT tag
            NBTItem nbtItem = new NBTItem(event.getItem());
            if (nbtItem.hasKey("EntityTag") && event.getItem().getType().toString().contains("SPAWN_EGG")) {
                event.setCancelled(true);

                // Get BlockState to delete all illegal items inside
                Dispenser dispenser = (Dispenser) event.getBlock().getState();
                new BukkitRunnable() {  // Create runnable to delete all items after a tick (necessary)
                    public void run() {
                        dispenser.getSnapshotInventory().remove(event.getItem().getType());
                        dispenser.update();
                    }
                }.runTaskLater(plugin, 1);
            }
        }

    }

}