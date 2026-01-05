# RedWorldguardFlags
New helpful [WorldGuard](https://dev.bukkit.org/projects/worldguard) Flags provided and supported by the [RedstoneWorld](https://redstoneworld.de) development team.

## Used Flag-Types

| Type             | Description                                                                                                                     | Example          |
|------------------|---------------------------------------------------------------------------------------------------------------------------------|------------------|
| Boolean          | Specify a normal boolean value via `allow` or `deny`.                                                                           | `allow`          |
| List of Blocks   | Define a list of Block Materials without spaces. The _Extended Object List_ is supported here.                                  | `stone,#beds`    |
| List of Entities | Define a list of Entity-Types without spaces. The _Extended Object List_ is supported here.                                     | `sheep,#zombies` |
| Integer          | Specify a natural number for the corresponding flag. (The used value range and the representation depend on the specific flag.) | `5`              |

### Extended Object List

Minecraft Block Materials and Entity-Types may be defined in different ways. There are situations where you have similar objects that differ only in texture or a few properties, but have their own names internally. For these multiple entries, this plugin supports the use of wildcards, Regex and Material-Tags.

#### Block Materials

- clean `MATERIAL` names
- clean `MATERIAL` names with wildcards via "*"
- [Regex](https://regexr.com/) definition via "r="
- Material-Tag starting with "#" or "tag=" - see [Minecraft-Wiki](https://minecraft.wiki/w/Tag#Block_tags_2) 
  _(Vanilla Minecraft implementation)_ and [PaperMC Java-Doc](https://jd.papermc.io/paper/1.21.1/org/bukkit/Tag.html) 
  _(Bukkit implementation)_ for the Material-Tag lists

#### Entity-Types

- clean `ENTITY-TYPE` names
- clean `ENTITY-TYPE` names with wildcards via "*"
- [Regex](https://regexr.com/) definition via "r="
- EntityType-Tag starting with "#" or "tag=" - see [Minecraft-Wiki](https://minecraft.wiki/w/Tag#Entity_type_tags_2) 
  _(Vanilla Minecraft implementation)_ and [PaperMC Java-Doc](https://jd.papermc.io/paper/1.21.1/org/bukkit/Tag.html) 
  _(Bukkit implementation)_ for the EntityType-Tag lists

## Flags

### Player Flags (for Non-Member)

"Player Flags" are flags that usually represent restrictive settings to protect the region or limit the gameplay feature for the player. It depends on the membership of the player in the specific region: The flag setting only applies to "non-members" (= not defined as "Owner" or "Member" in the region) and without admin / bypass permission for WorldGuard in the target world.

The flag update after a flag change is dependent on the specific flag. Some flags are applied to players immediately through verification at all relevant events. Others are applied through a regular check in the backend. (Currently, there are no flags in this plugin that are only applied to the player when they enter the region.)

| Flag                    | Value-Type        | Description                                                                                                   | Default State |
|-------------------------|-------------------|---------------------------------------------------------------------------------------------------------------|---------------|
| `allow-break-blocks`    | List of Blocks    | Specifies which blocks can be break. ¹ (subordinate to the "build" / "block-break" flag)                      | `[]`          |
| `deny-break-blocks`     | List of Blocks    | Specifies which blocks can _not_ be break. ¹ (subordinate to the "build" / "block-break" flag)                | `[]`          |
| `allow-place-blocks`    | List of Blocks    | Specifies which blocks can be place. ¹ (subordinate to the "build" / "block-place" flag)                      | `[]`          |
| `deny-place-blocks`     | List of Blocks    | Specifies which blocks can _not_ be place. ¹ (subordinate to the "build" / "block-place" flag)                | `[]`          |
| `reset-blocks`          | Integer (Seconds) | Activate a block reset after a specified time. If the value is ≤ 0, the flag is ignored.                      | _null_        |
| `allow-damage-entity`   | List of Entities  | Specifies which (target) entities can be damaged. ¹ (subordinate to the "damage-animals" flag)                | `[]`          |
| `deny-damage-entity`    | List of Entities  | Specifies which (target) entities can _not_ be damaged. ¹ (subordinate to the "damage-animals" flag)          | `[]`          |
| `elytra-use`            | Boolean           | Toggles whether players can fly with the `elytra`. (subordinate to the "interact" flag)                       | allow         |
| `allow-fishing`         | Boolean           | Toggles whether players can fish.                                                                             | allow         |
| `allow-interact-blocks` | List of Blocks    | Specifies which (target) block can be interacted with. ¹ (subordinate to the "interact" flag)                 | `[]`          |
| `deny-interact-blocks`  | List of Blocks    | Specifies which (target) block can _not_ be interacted with. ¹ (subordinate to the "interact" flag)           | `[]`          |
| `allow-interact-entity` | List of Entities  | Specifies which (target) entities can be interacted with. ¹ (subordinate to the "interact" flag)              | `[]`          |
| `deny-interact-entity`  | List of Entities  | Specifies which (target) entities can _not_ be interacted with. ¹ (subordinate to the "interact" flag)        | `[]`          |
| `lectern-book-place`    | Boolean           | Toggles whether players can place books on lecterns. (subordinate to the "build" / "block-place" flag)        | allow         |
| `allow-trading`         | Boolean           | Toggles whether players can trade with a villager or wandering trader. ² (subordinate to the "interact" flag) | allow         |

² No item can be taken from the trade inventory and there are no XPs. The increasing [career level](https://minecraft.wiki/w/Trading#Level) and [trade stop](https://minecraft.wiki/w/Trading#Trades) are displayed in the GUI, but this is only on the client side and resets itself after the entity is addressed again.

¹ If the flag value is empty, the next lower region is requested as usual (inheritance without a combination). The default case (without entries) is like the Vanilla behavior: All blocks / entities are allowed for the action (if the player still has the primary region-rights like "build", "interact" or "damage-animals"). With existing entry, the "ALLOW" flag type is considered as a whitelist and everything else is disallowed, and the "DENY" flag type is considered as a blacklist and everything else is allowed. The "DENY" definition is queried before the "ALLOW" definition, which must be taken into account if both types are used!

### Environment Flags

"Environment Flags" are flags that affect the behavior of entities and world mechanics. They are independent of player memberships in the specific region.

The new flag value are applied immediately after changing a flag.

| Flag                       | Value-Type | Description                                                                                                                                                                                              | Default State |
|----------------------------|------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------|
| `dispense-nbt-spawneggs`   | Boolean    | Toggles whether spawn-eggs with custom NBT data will be dispensed.                                                                                                                                       | allow         |
| `fire-burning-out`         | Boolean    | Toggles whether an ignited `fire` / `soul_fire` stays lit forever or can go out.                                                                                                                         | allow         |
| `spawnegg-use`             | Boolean    | Toggles whether entities can spawn when a player uses spawn-eggs. (overrides the "mob-spawning" flag, subordinate to the "build" flag ¹)                                                                 | allow         |
| `spawnegg-dispense`        | Boolean    | Toggles whether entities can spawn when a `dispenser` uses spawn-eggs. (overrides the "mob-spawning" flag)                                                                                               | allow         |
| `weaving-death-place`      | Boolean    | Toggles whether entities can "spawn" `cobweb` blocks on death by the [Weaving status effect](https://minecraft.wiki/w/Weaving).                                                                          | allow         |
| `entity-target`            | Boolean    | Toggles whether entities can set other entities as their target (e.g. for attacking) within their target-range. Activating it afterwards is useless, as the event can only be canceled at the beginning. | allow         |
| `boat-breakthrough`        | Boolean    | Toggles whether boats can destroy hanging entities.                                                                                                                                                      | allow         |
| `vehicle-entity-collision` | Boolean    | Toggles whether players or mobs can collide with vehicles, so the vehicle will not move.                                                                                                                 | allow         |

¹ This flag only restricts the spawn event. However, the use of spawn eggs also depends on the membership of the player in the specific region.

## Development

### Event handling

- `event.setCancelled(false);` means that the event will continue to be processed by WorldGuard in this context (not used).
- `event.setCancelled(true);` means that the event is canceled and WorldGuard does not continue to do anything natively.