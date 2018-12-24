package se.proxus.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;

import org.lwjgl.input.Keyboard;

import se.proxus.screens.slots.SlotMod;

public class ScreenModManager extends GuiScreen {

    private SlotMod guiSlot;
    private final GuiScreen parentScreen;
    private GuiButton buttonKeybind;
    private GuiButton buttonToggle;
    private boolean bindPressed;
    private boolean secondEscapeTry;

    public ScreenModManager(final GuiScreen parentScreen) {
	this.parentScreen = parentScreen;
    }

    @Override
    public void initGui() {
	Minecraft.getMinecraft().dp.getMods().sortMods(
		Minecraft.getMinecraft().dp.getMods().getRegisteredMods());
	guiSlot = new SlotMod(this);
	guiSlot.registerScrollButtons(getButtonList(), 7, 8);
	getButtonList().add(
		buttonKeybind = new GuiButton(0, width / 2 - 100, height - 48,
			98, 20, "Key: "
				+ mc.dp.getMods().getLoadedMods()
					.get(guiSlot.getSelected()).getKey()));
	getButtonList()
		.add(buttonToggle = new GuiButton(1, width / 2 + 2,
			height - 48, 98, 20, "State: "
				+ mc.dp.getMods().getLoadedMods()
					.get(guiSlot.getSelected()).getState()));
	buttonToggle.enabled = mc.thePlayer != null;
	getButtonList().add(
		new GuiButton(2, width / 2 + 2, height - 26, 98, 20, "Done"));
	getButtonList().add(
		new GuiButton(3, width / 2 - 100, height - 26, 98, 20,
			"Unbind key"));
    }

    @Override
    public void updateScreen() {
	getButtonList().clear();
	getButtonList().add(
		buttonKeybind = new GuiButton(0, width / 2 - 100, height - 48,
			98, 20, "Key: "
				+ (bindPressed ? "*" : mc.dp.getMods()
					.getRegisteredMods()[guiSlot
					.getSelected()].getKey())));
	getButtonList().add(
		buttonToggle = new GuiButton(1, width / 2 + 2, height - 48, 98,
			20, "State: "
				+ mc.dp.getMods().getRegisteredMods()[guiSlot
					.getSelected()].getState()));
	buttonToggle.enabled = mc.thePlayer != null;
	getButtonList().add(
		new GuiButton(2, width / 2 + 2, height - 26, 98, 20, "Done"));
	getButtonList().add(
		new GuiButton(3, width / 2 - 100, height - 26, 98, 20,
			"Unbind key"));
    }

    @Override
    protected void actionPerformed(final GuiButton button) {
	if (button.enabled)
	    switch (button.id) {
	    case 0:
		bindPressed = !bindPressed;
		break;

	    case 1:
		mc.dp.getMods().getRegisteredMods()[guiSlot.getSelected()]
			.toggle();
		break;

	    case 2:
		mc.displayGuiScreen(parentScreen);
		break;

	    case 3:
		mc.dp.getMods().getRegisteredMods()[guiSlot.getSelected()]
			.setKey("NONE", true);
		break;
	    }

	guiSlot.actionPerformed(button);
    }

    @Override
    public void drawScreen(final int x, final int y, final float ticks) {
	guiSlot.drawScreen(x, y, ticks);
	drawCenteredString(fontRenderer,
		"Mod Manager (" + String.valueOf(guiSlot.getSelected() + 1)
			+ "/" + mc.dp.getMods().getLoadedMods().size() + ")",
		width / 2, 10, 0xFFFFFFFF);
	super.drawScreen(x, y, ticks);
    }

    @Override
    public void keyTyped(final char keyChar, final int keyID) {
	try {
	    if (bindPressed
		    && !Keyboard.getKeyName(keyID).equalsIgnoreCase("ESCAPE")) {
		mc.dp.getMods().getRegisteredMods()[guiSlot.getSelected()]
			.setKey(Keyboard.getKeyName(keyID), true);
		bindPressed = false;
	    } else if (Keyboard.getKeyName(keyID).equalsIgnoreCase("ESCAPE")
		    && bindPressed)
		bindPressed = false;
	    else if (!bindPressed
		    && Keyboard.getKeyName(keyID).equalsIgnoreCase("ESCAPE")) {
		secondEscapeTry = true;

		if (secondEscapeTry) {
		    secondEscapeTry = false;
		    mc.displayGuiScreen(parentScreen);
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}