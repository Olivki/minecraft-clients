package se.proxus.kanon.modules.list.none;

import net.minecraft.client.gui.GuiChat;
import se.proxus.kanon.modules.Module;
import se.proxus.kanon.wrapper.minecraft.Screen;

public final class ModuleCommandChat extends Module {

    public ModuleCommandChat() {
        super("Command Chat", "Period", "Opens the chat window with '.' already entered.",
                Controller.TRIGGER);
    }

    @Override
    public void onTrigger() {
        Screen.display(new GuiChat());
    }
}