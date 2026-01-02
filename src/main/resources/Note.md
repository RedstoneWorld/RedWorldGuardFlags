# Alternative Flag-Registration

```java

    private HashMap<Class<?>, HashMap<String, Flag<?>>> registeredFlags;
    
    /**
     * Registering a Flag with WorldGuard
     *
     * @param flag The new Flag to register
     */
    private void registerFlag(Flag<?> flag) {
        
        // Code modified, original from: https://worldguard.enginehub.org/en/latest/developer/regions/custom-flagManager/#registering-new-flagManager
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        
        if (registeredFlags == null) {
            registeredFlags = new HashMap<>();
        }
        
        try {
            registry.register(flag);
            if (registeredFlags.containsKey(flag.getClass())) {
                registeredFlags.get(flag.getClass()).put(flag.getName(), flag);
            } else {
                registeredFlags.put(flag.getClass(), new HashMap<>());
                registeredFlags.get(flag.getClass()).put(flag.getName(), flag);
            }
        } catch (FlagConflictException e) {
            Flag<?> existing = registry.get(flag.getName());
            if (existing instanceof StateFlag) {
                if (registeredFlags.containsKey(flag.getClass())) {
                    this.registeredFlags.get(flag.getClass()).put(flag.getName(), flag);
                } else {
                    registeredFlags.put(flag.getClass(), new HashMap<>());
                    registeredFlags.get(flag.getClass()).put(flag.getName(), flag);
                }
            } else {
                plugin.getLogger().log(Level.SEVERE, "Flag: " + flag.getName() + " could not be registered!");
            }
        }
    }

    /**
     * Gets flagManager HashMap.
     *
     * @return the flagManager HashMap
     */
    public HashMap<Class<?>, HashMap<String, Flag<?>>> getFlags() {
        return registeredFlags;
    }

```