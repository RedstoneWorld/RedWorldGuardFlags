# RedWorldguardFlags
New helpful [WorldGuard](https://dev.bukkit.org/projects/worldguard) Flags provided and supported by the [RedstoneWorld](https://redstoneworld.de) development team.

## Flags

### Used Flag-Types

| Type           | Description                                                                                     | Example          |
|----------------|-------------------------------------------------------------------------------------------------|------------------|
| Boolean        | Specify a normal boolean value via `allow` or `deny`.                                           | `allow`          |
| List of Blocks | Define a list of blocks with the extended spelling support of Minecraft objects by this plugin. | `stone,tag=beds` |

### Extended Object List

- `MATERIAL` names
- `MATERIAL` names with wildcards via "*"
- Regex via "r="
- Material-Tag starting with "#" or "tag=" (see <a href="https://jd.papermc.io/paper/1.21.1/org/bukkit/Tag.html">PaperMC Java-Doc</a> and 
  <a href="https://minecraft.wiki/w/Tag">Minecraft-Wiki</a> for the tag lists)

| Flag                       | Type               | Description                                                                                                                                                                                              | Default State |
|----------------------------|--------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------|
| `dispense-nbt-spawneggs`   | Boolean            | Toggles whether spawn-eggs with custom NBT data will be dispensed.                                                                                                                                       | allow         |
| `vehicle-entity-collision` | Boolean            | Toggles whether players or mobs can collide with vehicles, so the vehicle will not move.                                                                                                                 | allow         |
| `lectern-book-place`       | Boolean            | Toggles whether players can place books on lecterns. (subordinate to the "build" / "block-place" flag)                                                                                                   | allow         |
| `spawnegg-use`             | Boolean            | Toggles whether entities can spawn when a player uses spawn-eggs. (overrides the "mob-spawning" flag, subordinate to the "build" flag)                                                                   | allow         |
| `spawnegg-dispense`        | Boolean            | Toggles whether entities can spawn when a dispenser dispense spawn-eggs. (overrides the "mob-spawning" flag)                                                                                             | allow         |
| `elytra-use`               | Boolean            | Toggles whether players can fly with the Elytra. (subordinate to the "interact" flag)                                                                                                                    | allow         |
| `allow-fishing`            | Boolean            | Toggles whether players can fish.                                                                                                                                                                        | allow         |
| `boat-breakthrough`        | Boolean            | Toggles whether boats can destroy hanging entities.                                                                                                                                                      | allow         |
| `allow-trading`            | Boolean            | Toggles whether players can trade with a villager or wandering trader. (subordinate to the "interact" flag)                                                                                              | allow         |
| `entity-target`            | Boolean            | Toggles whether entities can set other entities as their target (e.g. for attacking) within their target-range. Activating it afterwards is useless, as the event can only be canceled at the beginning. | allow         |
| `fire-burning-out`         | Boolean            | Toggles whether an ignited `fire` / `soul_fire` stays lit forever or can go out.                                                                                                                         | allow         |
| `allow-place-blocks`       | List of Blocks     | Specifies which blocks can be place. ¹ (subordinate to the "build" / "block-place" flag and specified the restriction)                                                                                   | `[]`          |
| `deny-place-blocks`        | List of Blocks     | Specifies which blocks can _not_ be place. ¹ (subordinate to the "build" / "block-place" flag and specified the restriction)                                                                             | `[]`          |
| `allow-break-blocks`       | List of Blocks     | Specifies which blocks can be break. ¹ (subordinate to the "build" / "block-break" flag and specified the restriction)                                                                                   | `[]`          |
| `deny-break-blocks`        | List of Blocks     | Specifies which blocks can _not_ be break. ¹ (subordinate to the "build" / "block-break" flag and specified the restriction)                                                                             | `[]`          |
| `reset-blocks`             | Integer (Seconds)  | Activate a block reset after a specified time. If the value is ≤ 0, the flag is ignored.                                                                                                                 | _null_        |

¹ If no definition is found, the default case is like the Vanilla behavior: allowed for all blocks (if the player still has building rights). As soon as a block has been defined, the flag is considered a whitelist and everything else is disallowed. For empty entries, the next lower region is requested as usual (inheritance without a combination). The DENY definition is queried before the ALLOW definition, which should be taken into account if the block definition overlaps!

## Development

### Event handling

- `event.setCancelled(false);` means that the event will continue to be processed by WorldGuard in this context.
- `event.setCancelled(true);` means that the event is canceled and WorldGuard does not continue to do anything natively.