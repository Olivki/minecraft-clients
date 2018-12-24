package se.proxus.kanon.keybinds;

import se.proxus.kanon.config.Configuration;
import se.proxus.kanon.wrapper.minecraft.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/** TODO:
 * Implement some wait between sending the messages as to make sure you won't get kicked for flooding.
 * Might just implement this directly into the sendMessage method?
 */
public final class KeybindMacro extends Keybind {
    
    public KeybindMacro(final String key, final Configuration config, final String... messages) {
        this(config.getName(), key, config, messages);
    }
    
    public KeybindMacro(final String name, final String key, final String... messages) {
        this(name, key, new Configuration(name, KeybindFactory.DIRECTORY), messages);
    }
    
    public KeybindMacro(final String name, final String key, final Configuration config, final String... messages) {
        super(name, key, config);
        if (Objects.isNull(messages)) {
            getConfig().addEntry("Messages", new ArrayList<String>())
                        .setDescription("The messages that will be sent when the keybind is activated.")
                        .setGeneric(String.class);
        } else {
            getConfig().addEntry("Messages", new ArrayList<>(Arrays.asList(messages)))
                        .setDescription("The messages that will be sent when the keybind is activated.")
                        .setGeneric(String.class);
        }
    }
    
    @Override
    public final void onPress(final boolean inGame) {
        if (inGame)
            getMessages().forEach(Player::sendMessage);
    }
    
    public final KeybindMacro add(final String entry) {
        if (!getMessages().contains(entry))
            getMessages().add(entry);
    
        getConfig().getHandler().notify(getConfig().getEntry("Messages"));
        
        return this;
    }
    
    public final KeybindMacro remove(final String entry) {
        getMessages().remove(entry);
        
        getConfig().getHandler().notify(getConfig().getEntry("Messages"));
        
        return this;
    }
    
    final List<String> getMessages() {
        return getConfig().getList("Messages", String.class);
    }
    
    public final String[] getEntries() {
        return getMessages().toArray(new String[0]);
    }
}
