package de.redstoneworld.redworldguardflags.flagtypes;

import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.FlagContext;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.SetFlag;
import org.jetbrains.annotations.Nullable;

public class StringFlag extends Flag<String> {
    
    public static SetFlag<String> ALLOW_PLACE;
    public static SetFlag<String> DENY_PLACE;
    public static SetFlag<String> ALLOW_BREAK;
    public static SetFlag<String> DENY_BREAK;
    
    public static SetFlag<String> ALLOW_BLOCK_INTERACT;
    public static SetFlag<String> DENY_BLOCK_INTERACT;
    public static SetFlag<String> ALLOW_ENTITY_INTERACT;
    public static SetFlag<String> DENY_ENTITY_INTERACT;
    
    public static SetFlag<String> ALLOW_ENTITY_DAMAGE;
    public static SetFlag<String> DENY_ENTITY_DAMAGE;
    
    public StringFlag(String name, @Nullable RegionGroup defaultGroup) {
        super(name, defaultGroup);
    }

    @Override
    public String parseInput(FlagContext context) {
        return context.getUserInput();
    }

    @Override
    public String unmarshal(@Nullable Object o) {
        return o.toString();
    }

    @Override
    public Object marshal(String o) {
        return o;
    }
}
