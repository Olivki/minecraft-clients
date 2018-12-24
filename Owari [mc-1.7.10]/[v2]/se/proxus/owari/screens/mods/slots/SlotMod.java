package se.proxus.owari.screens.mods.slots;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSlot;

import org.lwjgl.opengl.GL11;

import se.proxus.owari.mods.Mod;
import se.proxus.owari.mods.ModManager;
import se.proxus.owari.screens.mods.ScreenModManager;

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

	protected int getContentHeight() {
		return getSize() * 44;
	}

	@Override
	protected void drawBackground() {
		guiScreen.drawDefaultBackground();
	}

	@Override
	protected boolean isSelected(final int var0) {
		return selected == var0;
	}

	public int getSelected() {
		return selected;
	}

	@Override
	protected void elementClicked(final int p_148144_1_, final boolean p_148144_2_,
			final int p_148144_3_, final int p_148144_4_) {
		selected = p_148144_1_;
	}

	@Override
	protected void drawSlot(int selected, int var1, int var2, int var3, int p_180791_5_,
			int p_180791_6_) {
		Mod mod = ModManager.getInstance().getRegisteredMods()[selected];
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

	@Override
	public int getListWidth() {
		return super.getListWidth() - 40;
	}
}