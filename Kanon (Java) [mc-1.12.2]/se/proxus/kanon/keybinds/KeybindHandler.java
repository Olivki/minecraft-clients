package se.proxus.kanon.keybinds;

import org.lwjgl.input.Keyboard;
import se.proxus.kanon.Kanon;
import se.proxus.kanon.event4j.EventPriority;
import se.proxus.kanon.event4j.EventSubscribe;
import se.proxus.kanon.event4j.events.client.EventKeyPressed;

public final class KeybindHandler {
    
    @EventSubscribe(priority = EventPriority.HIGHEST)
    public void handleKeyPresses(final EventKeyPressed event) {
        if (event.getKeyID() == Keyboard.KEY_NONE)
            return;
        
        for (final Keybind bind : Kanon.KEYBINDS.getBindings()) {
            if (bind.getId() == event.getKeyID()) {
                bind.onPress(event.isInGame());
            }
        }
    }

}
