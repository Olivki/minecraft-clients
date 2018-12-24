package se.proxus.eien.screens.mods;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

import se.proxus.eien.mods.ModManager;
import se.proxus.eien.screens.mods.slots.SlotMod;

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
		ModManager mods = ModManager.getInstance();
		// mods.sortMods(mods.getRegisteredMods());
		guiSlot = new SlotMod(this);
		guiSlot.registerScrollButtons(7, 8);
		buttonList.add(buttonKeybind = new GuiButton(0, width / 2 - 100, height - 48, 98, 20,
				"Keybind: " + mods.getMods().get(guiSlot.getSelected()).getKeybind()));
		buttonList.add(buttonToggle = new GuiButton(1, width / 2 + 2, height - 48, 98, 20,
				"State: " + mods.getMods().get(guiSlot.getSelected()).getState()));
		buttonToggle.enabled = mc.thePlayer != null;
		buttonList.add(new GuiButton(2, width / 2 + 2, height - 26, 98, 20, "Done"));
		buttonList.add(new GuiButton(3, width / 2 - 100, height - 26, 98, 20, "Unbind key"));
	}

	@Override
	public void updateScreen() {
		buttonList.clear();
		ModManager mods = ModManager.getInstance();
		buttonList.add(buttonKeybind = new GuiButton(0, width / 2 - 100, height - 48, 98, 20,
				"Key: "
						+ (bindPressed ? "*" : mods.getRegisteredMods()[guiSlot.getSelected()]
								.getKeybind())));
		buttonList.add(buttonToggle = new GuiButton(1, width / 2 + 2, height - 48, 98, 20,
				"State: " + mods.getRegisteredMods()[guiSlot.getSelected()].getState()));
		buttonToggle.enabled = mc.thePlayer != null;
		buttonList.add(new GuiButton(2, width / 2 + 2, height - 26, 98, 20, "Done"));
		buttonList.add(new GuiButton(3, width / 2 - 100, height - 26, 98, 20, "Unbind key"));
	}

	@Override
	protected void actionPerformed(final GuiButton button) {
		if (button.enabled) {
			ModManager mods = ModManager.getInstance();
			switch (button.id) {
				case 0:
					bindPressed = !bindPressed;
					break;

				case 1:
					mods.getRegisteredMods()[guiSlot.getSelected()].toggle();
					break;

				case 2:
					mc.displayGuiScreen(parentScreen);
					break;

				case 3:
					mods.getRegisteredMods()[guiSlot.getSelected()].setValue("Keybind", "NONE",
							true);
					break;
			}
		}

		guiSlot.actionPerformed(button);
	}

	@Override
	public void handleMouseInput() {
		try {
			super.handleMouseInput();
		} catch (Exception e) {
			e.printStackTrace();
		}
		guiSlot.handleMouseInput();
	}

	@Override
	public void drawScreen(final int x, final int y, final float ticks) {
		ModManager mods = ModManager.getInstance();
		guiSlot.drawScreen(x, y, ticks);
		drawCenteredString(fontRendererObj,
				"Mod Manager (" + String.valueOf(guiSlot.getSelected() + 1) + "/"
						+ mods.getMods().size() + ")", width / 2, 10, 0xFFFFFFFF);
		super.drawScreen(x, y, ticks);
	}

	@Override
	public void keyTyped(final char keyChar, final int keyID) {
		try {
			ModManager mods = ModManager.getInstance();
			if (bindPressed && !Keyboard.getKeyName(keyID).equalsIgnoreCase("ESCAPE")) {
				mods.getRegisteredMods()[guiSlot.getSelected()].setValue("Keybind",
						Keyboard.getKeyName(keyID), true);
				bindPressed = false;
			} else if (Keyboard.getKeyName(keyID).equalsIgnoreCase("ESCAPE") && bindPressed) {
				bindPressed = false;
			} else if (!bindPressed && Keyboard.getKeyName(keyID).equalsIgnoreCase("ESCAPE")) {
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