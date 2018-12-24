package se.proxus.kanon.event4j.events.client;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.GuiScreen;
import se.proxus.kanon.event4j.events.EventCancellable;

public final class EventDisplayGuiScreen extends EventCancellable {

    @Getter @Setter private GuiScreen screen;

    public EventDisplayGuiScreen(final GuiScreen screen) {
        this.screen = screen;
    }
    
    public final void closeScreen() {
        setScreen(null);
    }
}
