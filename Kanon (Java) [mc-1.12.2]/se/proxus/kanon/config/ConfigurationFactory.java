package se.proxus.kanon.config;

import se.proxus.kanon.Kanon;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ConfigurationFactory {

    private final Map<String, Configuration> REGISTERED_CONFIGURATIONS = new LinkedHashMap<>();

    /**
     * A wrapper method used for registering Configurations, it makes sure that there is no prior entry
     * registered under the given key.
     *
     * @param key
     *         The key to register the Configuration under, this will also be used as the name for the
     *         Configuration.
     * @param directory
     *         The DIRECTORY in which the Configuration will save it's file to. Keep in mind that upon first
     *         run the Configuration will automatically create a file if it can't find one matching it's
     *         name.
     * @return If there is no prior entry with the given key, it returns the newly registered Configuration
     * object, otherwise it will return null.
     */
    public final Configuration registerConfig(final String key, final File directory) {
        if (!REGISTERED_CONFIGURATIONS.containsKey(key)) {
            lombok.val config = new Configuration(key, directory);
            Kanon.LOGGER.info(config.toString().replace("Configuration[",
                                                                  "ConfigurationRegister["));
            REGISTERED_CONFIGURATIONS.put(key, config);
            return config;
        } else {
            return null;
        }
    }

    /**
     * A wrapper method for unregistering configurations, makes sure that the given key is a valid one before
     * trying to remove it.
     *
     * @param key
     *         The key that the Configuration instance was registered under.
     * @return If the given key is valid, it will return the instance of the removed Configuration, otherwise
     * it will return null.
     */
    public final Configuration unregisterConfig(final String key) {
        if (REGISTERED_CONFIGURATIONS.containsKey(key)) {
            lombok.val config = get(key);
            Kanon.LOGGER.info(config.toString().replace("Configuration[",
                                                                  "ConfigurationUnregister["));
            return REGISTERED_CONFIGURATIONS.remove(key);
        } else {
            return null;
        }
    }

    /**
     * A simple wrapper method for the get method in the Map.
     *
     * @param key
     *         The key that was used to register the parent.
     * @return The parent instance stored with the given key. If no instance is found with the given
     * key, it will return null.
     */
    public final Configuration get(final String key) {
        return REGISTERED_CONFIGURATIONS.get(key);
    }

    /**
     * To be used in case an external class needs to iterate through all the registered configurations.
     *
     * @return Returns an array rather than the raw Map, this is to prevent any external classes from
     * modifying the Map outside of adding and removing configurations.
     */
    public final Configuration[] getConfigs() {
        return REGISTERED_CONFIGURATIONS.values()
                .toArray(new Configuration[REGISTERED_CONFIGURATIONS.values().size()]);
    }

    private Kanon getKanon() {
        return Kanon.getInstance();
    }
}
