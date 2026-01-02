package de.redstoneworld.redworldguardflags;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.IntegerFlag;
import com.sk89q.worldguard.protection.flags.SetFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import de.redstoneworld.redworldguardflags.flagtypes.StringFlag;

import java.util.logging.Level;

public class FlagManager {

    private final RedWorldGuardFlags plugin;
    
    
    public FlagManager(RedWorldGuardFlags plugin) {
        this.plugin = plugin;
    }

    public enum FlagEnum {
        
        DISPENSE_NBT_SPAWNEGGS (new StateFlag("dispense-nbt-spawneggs", true)),
        VEHICLE_ENTITY_COLLISION (new StateFlag("vehicle-entity-collision", true)),
        LECTERN_BOOK_PLACE (new StateFlag("lectern-book-place", true)),
        SPAWNEGG_USE (new StateFlag("spawnegg-use", true)),
        SPAWNEGG_DISPENSE (new StateFlag("spawnegg-dispense", true)),
        ELYTRA_USE (new StateFlag("elytra-use", true)),
        ALLOW_FISHING (new StateFlag("allow-fishing", true)),
        BOAT_BREAKTHROUGH (new StateFlag("boat-breakthrough", true)),
        ALLOW_TRADING (new StateFlag("allow-trading", true)),
        ENTITY_TARGET (new StateFlag("entity-target", true)),
        FIRE_BURNING_OUT (new StateFlag("fire-burning-out", true)),
        WEAVING_DEATH_PLACE (new StateFlag("weaving-death-place", true)),
        
        ALLOW_PLACE_BLOCKS (StringFlag.ALLOW_PLACE = new SetFlag<>("allow-place-blocks", new StringFlag(null, null))),
        DENY_PLACE_BLOCKS (StringFlag.DENY_PLACE = new SetFlag<>("deny-place-blocks", new StringFlag(null, null))),
        ALLOW_BREAK_BLOCKS (StringFlag.ALLOW_BREAK = new SetFlag<>("allow-break-blocks", new StringFlag(null, null))),
        DENY_BREAK_BLOCKS (StringFlag.DENY_BREAK = new SetFlag<>("deny-break-blocks", new StringFlag(null, null))),
        
        ALLOW_INTERACT_BLOCKS (StringFlag.ALLOW_BLOCK_INTERACT = new SetFlag<>("allow-interact-blocks", new StringFlag(null, null))),
        DENY_INTERACT_BLOCKS (StringFlag.DENY_BLOCK_INTERACT = new SetFlag<>("deny-interact-blocks", new StringFlag(null, null))),
        ALLOW_INTERACT_ENTITY (StringFlag.ALLOW_ENTITY_INTERACT = new SetFlag<>("allow-interact-entity", new StringFlag(null, null))),
        DENY_INTERACT_ENTITY (StringFlag.DENY_ENTITY_INTERACT = new SetFlag<>("deny-interact-entity", new StringFlag(null, null))),
        
        ALLOW_DAMAGE_ENTITY (StringFlag.ALLOW_ENTITY_DAMAGE = new SetFlag<>("allow-damage-entity", new StringFlag(null, null))),
        DENY_DAMAGE_ENTITY (StringFlag.DENY_ENTITY_DAMAGE = new SetFlag<>("deny-damage-entity", new StringFlag(null, null))),
        
        RESET_BLOCKS (new IntegerFlag("reset-blocks", null));
        
        
        private final Flag<?> flag;
        
        FlagEnum(Flag<?> flagObject) {
            flag = flagObject;
        }
        
        public Flag<?> getFlagObj() {
            return flag;
        }
    }

    /**
     * This method registers all WorldGuard flags that 
     * are handled with this plugin.
     */
    public void registerWorldGuardFlags() {
        
        // Reference: WorldGuard API Usage at https://worldguard.enginehub.org/en/latest/developer/regions/custom-flags/#registering-new-flags
        
        for (FlagManager.FlagEnum value : FlagManager.FlagEnum.values()) {
            this.registerFlag(value.getFlagObj());
        }
    }
    
    /**
     * Registering a new WorldGuard flag
     *
     * @param flag (type of "Flag") the new WorldGuard flag for the registration
     */
    private void registerFlag(Flag<?> flag) {
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        
        try {
            registry.register(flag);
        } catch (FlagConflictException e) {
            plugin.getLogger().log(Level.SEVERE, "Flag: " + flag.getName() + " could not be registered!");
        }
    }
    
}
