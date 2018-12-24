package se.proxus.kanon.modules.list.gui;

import net.minecraft.client.gui.FontRenderer;
import se.proxus.kanon.Kanon;
import se.proxus.kanon.event4j.EventSubscribe;
import se.proxus.kanon.event4j.events.render.EventRender2D;
import se.proxus.kanon.modules.Module;
import se.proxus.kanon.modules.ModuleSignature;
import se.proxus.kanon.utils.minecraft.client.Colourz;
import se.proxus.kanon.wrapper.minecraft.Player;
import se.proxus.kanon.wrapper.minecraft.Screen;

@ModuleSignature(author = "Oliver Berg", date = "2018/09/12 (10:34)")
public final class ModuleOverlay extends Module {
    
    public ModuleOverlay() {
        super("Overlay",
              "Renders the in-game overlay of the client.",
              Controller.TOGGLE_HIDDEN,
              Category.GUI);
    }

    @Override
    public void initialize() {
        getConfig().addEntry("Colourless", false)
                .setDescription("Whether the active modules events should be rendered without any colour.");
    
        getConfig().addEntry("Should Render Potions", false)
                .setDescription("To avoid issues.")
                .setMutable(false);
        
        getConfig().setValue("State", true, false);
    }

    @Override
    public void onEnable() {
        getConfig().setValue("Should Render Potions", false, false);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        getConfig().setValue("Should Render Potions", true, false);
        super.onDisable();
    }

    @EventSubscribe
    public final void renderOverlay(final EventRender2D event) {
        final FontRenderer font = event.getFont();

        font.drawStringWithShadow(String.format("%s%s %sv%s",
                                                Colourz.CLIENT_COLOUR,
                                                getKanon().getName(),
                                                Colourz.GREY,
                                                getKanon().getVersion()),
                                  2, 2, 0xFFFFFFFF);
        
        font.drawStringWithShadow(Colourz.GREY + Player.getLocation().getDisplay(),
                                  2, 12, 0xFFFFFFFF);

        final Module[] modulesToDisplay = Kanon.MODULES.getModulesToDisplay();

        for (int i = 0; i < modulesToDisplay.length; i++) {
            final Module module = modulesToDisplay[i];
            final String colour = getConfig().getBoolean("Colourless")
                                  ? Colourz.RESET
                                  : module.getCategory().getColour();
            final String name = colour + module.getName();
            final float x = Screen.getScaledWidth() - font.getStringWidth(name) - 2;

            font.drawStringWithShadow(name, x, 3 + i * 10, 0xFFFFFFFF);
        }
    }
}