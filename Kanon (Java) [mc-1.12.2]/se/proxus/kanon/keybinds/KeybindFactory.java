package se.proxus.kanon.keybinds;

import se.proxus.kanon.Kanon;
import se.proxus.kanon.modules.Module;
import se.proxus.kanon.utils.system.Filez;

import javax.annotation.Nullable;
import java.io.File;
import java.util.HashMap;

public final class KeybindFactory {
    
    public final static File DIRECTORY = Filez.getDirectory(Kanon.DIRECTORY, "keybinds");
    private final static HashMap<String, Keybind> BINDINGS = new HashMap<>();
    
    public final Keybind addKeybind(final Keybind keybind) {
        if (!contains(keybind.getName())) {
            BINDINGS.put(keybind.getName(), keybind);
            return keybind;
        } else {
            throw new NullPointerException(String.format("Keybind[%s] IS ALREADY REGISTERED!", keybind.getName()));
        }
    }
    
    public final KeybindModule addModuleKeybind(final String key, final Module module) {
        return (KeybindModule) addKeybind(new KeybindModule(key, module));
    }
    
    public final KeybindMacro addMacroKeybind(final String name, final String key, final String... messages) {
        return (KeybindMacro) addKeybind(new KeybindMacro(name, key, messages));
    }
    
    public final Keybind removeKeybind(final String name) {
        if (contains(name)) {
            final Keybind keybind = getKeybind(name);
            BINDINGS.remove(name);
            return keybind;
        } else {
            throw new NullPointerException(String.format("Keybind[%s] DOES NOT EXIST!", name));
        }
    }
    
    @Nullable
    public final Keybind getKeybind(final String name) {
        if (contains(name)) {
            return BINDINGS.get(name);
        } else {
            throw new NullPointerException(String.format("Keybind[%s] DOES NOT EXIST!", name));
        }
    }
    
    public final boolean contains(final String name) {
        return BINDINGS.containsKey(name);
    }
    
    public final Keybind[] getBindings() {
        return BINDINGS.values().toArray(new Keybind[0]);
    }
}
