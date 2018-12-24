package se.proxus.rori.screens.mods.slots;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSlot;

import org.lwjgl.opengl.GL11;

import se.proxus.rori.mods.Mod;
import se.proxus.rori.mods.ModManager;
import se.proxus.rori.screens.mods.ScreenModManager;

public class SlotMod extends GuiSlot {

	private final ScreenModManager guiScreen;
	private int selected;

	public SlotMod(final ScreenModManager guiScreen) {
		super(Minecraft.getMinecraft(), guiScreen.width, guiScreen.height, 32,
				guiScreen.height - 59, 44);
		this.guiScreen = guiScreen;
		selected = 0;
	}

	@Override
	protected int getSize() {
		return ModManager.getInstance().getRegisteredMods().length;
	}

	@Override
	protected int getContentHeight() {
		return getSize() * 44;
	}

	@Override
	protected void drawBackground() {
		guiScreen.drawDefaultBackground();
	}

	@Override
	protected boolean isSelected(final int var0) {
		return var0 == selected;
	}

	public int getSelected() {
		return selected;
	}

	@Override
	protected void elementClicked(final int slotIndex, final boolean isDoubleClick,
			final int mouseX, final int mouseY) {
		selected = slotIndex;
	}

	@Override
	public int getListWidth() {
		return super.getListWidth() - 40;
	}

	@Override
	protected void drawSlot(final int entryID, final int var1, final int var2, final int var3,
			final int mouseX, final int mouseY) {
		Mod mod = ModManager.getInstance().getRegisteredMods()[entryID];
		FontRenderer font = mod.getClient().getMinecraft().fontRendererObj;
		guiScreen
				.drawCenteredString(font, mod.getName(), guiScreen.width / 2, var2 + 1, 0xFFFFAA00);
		guiScreen.drawCenteredString(font, "Key: " + mod.getKeybind(), guiScreen.width / 2,
				var2 + 11, 0xFF808080);
		guiScreen.drawCenteredString(font, "State: " + mod.getState(), guiScreen.width / 2,
				var2 + 21, 0xFF808080);
		guiScreen.drawCenteredString(font, "Category: " + mod.getCategory().getName(),
				guiScreen.width / 2, var2 + 31, 0xFF808080);
		if (mouseX >= var1 && mouseY >= var2 - 3 && mouseX <= var1 + 215 && mouseY <= var2 + 40) {
			GL11.glPushMatrix();
			GL11.glScaled(0.5D, 0.5D, 0.5D);
			Gui.drawRect(mouseX * 2 + 12, mouseY * 2 + 3,
					mouseX * 2 + 13 + font.getStringWidth(mod.getDescription()) + 2,
					mouseY * 2 + 25, 0x60000000);
			guiScreen.drawString(font, mod.getDescription(), mouseX * 2 + 14, mouseY * 2 + 5,
					0xFFFFFFFF);
			guiScreen.drawString(font, "Values: " + mod.getValues().size(), mouseX * 2 + 14,
					mouseY * 2 + 15, 0xFFFFFFFF);
			GL11.glPopMatrix();
		}
	}
}