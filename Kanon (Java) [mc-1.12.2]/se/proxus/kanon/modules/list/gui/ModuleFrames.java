package se.proxus.kanon.modules.list.gui;


import se.proxus.kanon.Kanon;
import se.proxus.kanon.event4j.EventSubscribe;
import se.proxus.kanon.event4j.EventWrapper;
import se.proxus.kanon.event4j.events.render.EventRender2D;
import se.proxus.kanon.modules.Module;
import se.proxus.kanon.wrapper.minecraft.Screen;

public class ModuleFrames extends Module {

    public ModuleFrames() {
        super("Frames",
              "RSHIFT",
              "Renders the in-game overlay of the client.",
              Controller.TRIGGER,
              Category.GUI);
    }

    @Override
    public void initialize() {
        EventWrapper.registerListener(this);
    }

    @Override
    public void onTrigger() {
        Screen.display(Kanon.FRAMES_SCREEN);
    }

    @EventSubscribe
    public final void onRender2D(final EventRender2D event) {
        getKanon().getFramesManager().renderPinned();
    }
}