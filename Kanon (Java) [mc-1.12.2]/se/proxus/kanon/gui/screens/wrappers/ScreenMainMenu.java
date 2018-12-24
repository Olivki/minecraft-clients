package se.proxus.kanon.gui.screens.wrappers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.resources.I18n;
import org.jetbrains.annotations.NotNull;
import se.proxus.kanon.Kanon;
import se.proxus.kanon.event4j.events.client.EventDisplayGuiScreen;

import java.io.IOException;

public class ScreenMainMenu extends GuiMainMenu {

    //TODO: Make this work later

    private static boolean shouldSkipInitial;
    private static boolean shouldNotLoad;

    //@EventSubscribe
    public final void onDisplayGuiScreen(final EventDisplayGuiScreen event) {
        if (event.getScreen() instanceof GuiMainMenu) {
            event.setScreen(this);
        }
    }

    @Override
    public void initGui() {
        try {
            super.initGui();
        } catch (Exception e) {
            e.printStackTrace();
        }

		/*
		 * if (!getShouldSkipInitial()) { ScreenInitial screen = new
		 * ScreenInitial(this); mc.displayGuiScreen(screen); }
		 */

        if (!getShouldNotLoad()) {
            try {
                //getKanon().getLogger().info("More memes");
                //getKanon().load();
            } catch (Exception e) {
                e.printStackTrace();
            }
            setShouldLoad(true);
        }

        for (int buttons = 0; buttons < buttonList.size(); buttons++) {
            GuiButton button = buttonList.get(buttons);
            if (button.displayString.equalsIgnoreCase(I18n.format("menu.online"))) {
                Kanon.LOGGER.info("Minecraft Realms button contains been found. [INDEX:" + buttons + "]");
                button.id = 100;
                button.enabled = false;
                Kanon.LOGGER.info("Successfully disabled the Minecraft Realms button..");
                break;
            }
        }

    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(@NotNull final GuiButton button) {
        try {
            super.actionPerformed(button);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (button.enabled) {
            switch (button.id) {
                case 100:
                    // mc.displayGuiScreen(new ScreenPluginShop(this));
                    break;
            }
        }
    }

    public Kanon getClient() {
        return Minecraft.kanon;
    }

    public boolean getShouldSkipInitial() {
        return shouldSkipInitial;
    }

    public void setShouldSkipInitial(final boolean shouldDisplayInitial) {
        shouldSkipInitial = shouldDisplayInitial;
    }

    public boolean getShouldNotLoad() {
        return shouldNotLoad;
    }

    public void setShouldLoad(final boolean shouldNotLoad) {
        ScreenMainMenu.shouldNotLoad = shouldNotLoad;
    }
}
