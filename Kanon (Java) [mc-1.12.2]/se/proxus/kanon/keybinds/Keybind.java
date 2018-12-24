package se.proxus.kanon.keybinds;

import lombok.Getter;
import org.lwjgl.input.Keyboard;
import se.proxus.kanon.config.Configuration;

@Getter
public abstract class Keybind {
    
    private final String name;
    private final Configuration config;
    
    public Keybind(final String name, final String key, final Configuration config) {
        this.name = name;
        this.config = config;
        this.config.addEntry("Keybind", key.toUpperCase())
                    .setDescription("The currently set keybind.")
                    .setCommandMutable(false);
    }
    
    public Keybind(final String name, final String key) {
        this(name, key, new Configuration(name, null));
    }
    
    public Keybind(final String key, final Configuration config) {
        this(config.getName(), key, config);
    }
    
    public Keybind(final String name, final int id, final Configuration config) {
        this(name, Keyboard.getKeyName(id), config);
    }
    
    public Keybind(final int id, final Configuration config) {
        this(config.getName(), id, config);
    }
    
    public abstract void onPress(final boolean inGame);
    
    public final int getId() {
        return Keyboard.getKeyIndex(getKey());
    }
    
    public final String getKey() {
        return config.getString("Keybind");
    }
    
    public final Keybind setKey(final String key) {
        config.setValue("Keybind", key);
        
        return this;
    }
}
