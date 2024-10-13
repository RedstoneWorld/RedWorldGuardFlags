package de.redstoneworld.redworldguardflags.listener.misc;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import de.redstoneworld.redworldguardflags.Flags;
import de.redstoneworld.redworldguardflags.RedWorldGuardFlags;
import de.redstoneworld.redworldguardflags.util.WorldGuardUtil;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.Lectern;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;

public class LecternBookPlace implements Listener {

    private final RedWorldGuardFlags plugin;

    /**
     * Players can open books at the lectern when the "use" flag (or "interact" flag) is enabled. 
     * They can also take the books if the "chest-access" flag is enabled (or activated "build" flag). 
     * But to place a book on an empty lectern, natively the player must have build rights 
     * (or activated "build" or "block-place" flag). For this purpose, there is now the new 
     * "lectern-book-place" flag that has been implemented here.
     */
    public LecternBookPlace(RedWorldGuardFlags plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
        ApplicableRegionSet set = WorldGuardUtil.getRegionSet(event.getPlayer().getLocation());

        // Check if the flag applies and if it is set to deny
        if (set.testState(null, (StateFlag) Flags.FlagEnum.LECTERN_BOOK_PLACE.getFlagObj())) {

            PlayerInventory playerInventory = event.getPlayer().getInventory();
            ItemStack bookItemStack = playerInventory.getItemInMainHand().clone();
            if ((bookItemStack.getType() != Material.WRITABLE_BOOK) && (bookItemStack.getType() != Material.WRITTEN_BOOK)) return;

            Block block = event.getClickedBlock();
            if (block == null) return;
            if (block.getType() != Material.LECTERN) return;

            if (!(block.getBlockData() instanceof Lectern lecternData)) return;
            if (lecternData.hasBook()) return;

            BlockState state = block.getState();
            if (!(state instanceof InventoryHolder holder)) return;

            Inventory inventory = holder.getInventory();
            if (!(inventory instanceof LecternInventory lecternInventory)) return;

            // Accept book placements only in survival or creative mode. This is the vanilla behavior.
            if (!(event.getPlayer().getGameMode() == GameMode.SURVIVAL || event.getPlayer().getGameMode() == GameMode.CREATIVE)) return;

            int originalAmount = bookItemStack.getAmount();
            bookItemStack.setAmount(1);
            lecternInventory.setBook(bookItemStack);
            
            // Remove the placed book from inventory
            if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) playerInventory.getItemInMainHand()
                    .setAmount(originalAmount - 1);

            event.setCancelled(true);
        }

    }

}
