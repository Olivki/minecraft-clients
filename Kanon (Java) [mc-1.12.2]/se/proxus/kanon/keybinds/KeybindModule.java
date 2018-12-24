package se.proxus.kanon.keybinds;

import lombok.Getter;
import se.proxus.kanon.modules.Module;

@Getter
public final class KeybindModule extends Keybind {
    
    private final Module module;
    
    public KeybindModule(final String key, final Module module) {
        super(key, module.getConfig());
        
        this.module = module;
    }
    
    @Override
    public final void onPress(final boolean inGame) {
        if (!inGame && !module.isGlobal())
            return;
        
        switch (module.getController()) {
            case TRIGGER: {
                module.onTrigger();
                break;
            }
        
            default: {
                module.toggle();
                break;
            }
        }
    }
}
