package Comet.Gui.Menu;

import java.awt.Cursor;

import net.minecraft.src.*;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import Comet.Utils.Color;

public class m_World extends WindowBase {

	public static boolean enabled = false;
	public static boolean pinned = false;
	public static boolean dragging;
	private static int dragX = 108;
	private static int dragY = 3;
	private static int xstart;
	private static int ystart;

	@Override
	public void drawScreen(int i, int j, float f) {
		FontRenderer font = mc.fontRenderer;

		mouseDragged(i, j);
		if(enabled) {
			drawBorderedUpperRect(dragX, dragY - 2, dragX + 106, dragY + 12, 0xFF000000, 0x500E0E0E);
			drawBorderedUpperRect(dragX, dragY - 2, dragX + 106, dragY + 12, 0xFF000000, 0x500E0E0E);
		} else {
			drawBorderedRect(dragX, dragY - 2, dragX + 106, dragY + 12, 0xFF000000, 0x500E0E0E);
			drawBorderedRect(dragX, dragY - 2, dragX + 106, dragY + 12, 0xFF000000, 0x500E0E0E);
		}
		
		drawPin(dragX + 94, dragY, enabled, i, j);
		drawPin(dragX + 82, dragY, pinned(), i, j);
		//font.drawStringWithShadow("World", dragX + 2, dragY + 1, 0xFFFFFFFF);
		drawSmallTTFString2("World", dragX + 2, dragY - 3);

		if(enabled) {
			drawBorderedRect(dragX, dragY + 11, dragX + 106, dragY + 87, 0xFF000000, 0x500E0E0E);
			drawButton(dragX, dragY + 9, "Server speed", mc.comet.keys.serverspeed.isRunning(), i, j, true, "Speeds up the server time.", sW("Speeds up the server time.") - 60);
			drawButton(dragX, dragY + 21, "Fly", mc.comet.keys.fly.isRunning(), i, j, false, "", 0);
			drawButton(dragX, dragY + 33, "Placeholder 1", mc.comet.keys.sneak.isRunning(), i, j, false, "", 0);
			drawButton(dragX, dragY + 45, "Placeholder 2", mc.comet.keys.fly.isRunning(), i, j, false, "", 0);
			drawButton(dragX, dragY + 57, "Placeholder 3", mc.comet.keys.sneak.isRunning(), i, j, false, "", 0);
			drawButton(dragX, dragY + 69, "Placeholder 4", mc.comet.keys.fly.isRunning(), i, j, false, "", 0);
		}
	}

	@Override
	public void mouseClicked(int i, int j, int k) {
		if(i > dragX && i < dragX + 80 && j > dragY - 2 && j < dragY + 12){
			dragging = true;
			xstart = i - dragX;
			ystart = j - dragY;
			playSound();
		}

		if(i > dragX + 94 && i < dragX + 104 && j > dragY && j < dragY + 10) {enabled = !enabled;playSound();}

		if(i > dragX + 82 && i < dragX + 92 && j > dragY && j < dragY + 10) {pinned = !pinned; playSound();}

		if(enabled) {
			if(i > dragX + 72 && i < dragX + 104 && j > dragY + (9 + 4) && j < dragY + (12 + 12)) {mc.comet.keys.serverspeed.toggle(); playSound();}
			if(i > dragX + 72 && i < dragX + 104 && j > dragY + (21 + 4) && j < dragY + (20 + 16)) {mc.comet.keys.fly.toggle(); playSound();}
			if(i > dragX + 72 && i < dragX + 104 && j > dragY + (33 + 4) && j < dragY + (32 + 16)) {mc.comet.keys.fly.toggle(); playSound();}
			if(i > dragX + 72 && i < dragX + 104 && j > dragY + (45 + 4) && j < dragY + (44 + 16)) {mc.comet.keys.sneak.toggle(); playSound();}
			if(i > dragX + 72 && i < dragX + 104 && j > dragY + (57 + 4) && j < dragY + (56 + 16)) {mc.comet.keys.sneak.toggle(); playSound();}
			if(i > dragX + 72 && i < dragX + 104 && j > dragY + (69 + 4) && j < dragY + (68 + 16)) {mc.comet.keys.fly.toggle(); playSound();}
		}
	}

	@Override
	public void mouseMovedOrUp(int i, int j, int k) {
		if (k == 0) {
			dragging = false;

		}
	}

	public void mouseDragged(int i, int j) {
		if(dragging) {
			dragX = (i - xstart);
			dragY = (j - ystart);
		}
	}

	public boolean pinned() {
		return pinned;
	}

}
