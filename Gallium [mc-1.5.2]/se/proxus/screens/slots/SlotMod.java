package se.proxus.screens.slots;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiSlot;
import net.minecraft.src.Tessellator;

import org.lwjgl.opengl.GL11;

import se.proxus.mods.Mod;
import se.proxus.screens.ScreenModManager;

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
	return Minecraft.getMinecraft().dp.getMods().getRegisteredMods().length;
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
    protected void drawSlot(final int selected, final int var1, final int var2,
	    final int var3, final Tessellator tesselator) {
	Mod mod = Minecraft.getMinecraft().dp.getMods().getRegisteredMods()[selected];
	FontRenderer font = mod.getClient().getMinecraft().fontRenderer;
	guiScreen.drawCenteredString(font, mod.getName(), guiScreen.width / 2,
		var2 + 1, 0xFFFFAA00);
	guiScreen.drawCenteredString(font, "Key: " + mod.getKey(),
		guiScreen.width / 2, var2 + 11, 0xFF808080);
	guiScreen.drawCenteredString(font, "State: " + mod.getState(),
		guiScreen.width / 2, var2 + 21, 0xFF808080);
	guiScreen.drawCenteredString(font, "Category: "
		+ mod.getCategory().getName(), guiScreen.width / 2, var2 + 31,
		0xFF808080);
	if (mouseX >= var1 && mouseY >= var2 - 3 && mouseX <= var1 + 215
		&& mouseY <= var2 + 40) {
	    GL11.glPushMatrix();
	    GL11.glScaled(0.5D, 0.5D, 0.5D);
	    guiScreen.drawRect(mouseX * 2 + 12, mouseY * 2 + 3, mouseX * 2 + 13
		    + font.getStringWidth(mod.getDescription()) + 2,
		    mouseY * 2 + 25, 0x60000000);
	    guiScreen.drawString(font, mod.getDescription(), mouseX * 2 + 14,
		    mouseY * 2 + 5, 0xFFFFFFFF);
	    guiScreen.drawString(font, "Settings: "
		    + mod.getLoadedSettings().size(), mouseX * 2 + 14,
		    mouseY * 2 + 15, 0xFFFFFFFF);
	    GL11.glPopMatrix();
	}
    }

    @Override
    protected void elementClicked(final int selected, final boolean var0) {
	this.selected = selected;
    }

    @Override
    protected boolean isSelected(final int var0) {
	return selected == var0;
    }

    public int getSelected() {
	return selected;
    }
}