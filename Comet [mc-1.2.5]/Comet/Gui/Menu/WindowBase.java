package Comet.Gui.Menu;

import net.minecraft.src.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Comet.Gui.Methods;
import Comet.Gui.TTF;
import Comet.Utils.Color;
import Comet.Utils.Settings;

import net.minecraft.client.Minecraft;

public abstract class WindowBase extends GuiScreen {

	public static Minecraft mc = Minecraft.theMinecraft;

	public static Methods meth = new Methods();

	public abstract boolean pinned();

	public abstract void drawScreen(int i, int j, float f);

	public abstract void mouseClicked(int i, int j, int k);

	public abstract void mouseMovedOrUp(int i, int j, int k);

	private static RenderItem itemRenderer = new RenderItem();

	public static TTF ttfFont;
	public static TTF ttfFont2;
	public static TTF ttfFont3;
	public static TTF ttfFont4;

	public int mx;
	public int my;

	public WindowBase() {
		if (ttfFont == null) {ttfFont = new TTF(mc, "Lucida Console Bold", 16);}
		if (ttfFont2 == null) {ttfFont2 = new TTF(mc, "Lucida Console Bold", 20);}
		if (ttfFont3 == null) {ttfFont3 = new TTF(mc, "Lucida Console Bold", 11);}
		if (ttfFont4 == null) {ttfFont4 = new TTF(mc, "Lucida Console Bold", 11);}
	}

	/** Drawing functions */
	public void drawBorderedRect(int x, int y, int x1, int y1, int inside, int border) {
		meth.drawSmallWBorderedRect(x, y, x1, y1, inside, border);
	}

	public void drawBorderedGradientRect(int x, int y, int x1, int y1, int inside, int inside2, int border) {
		meth.drawSmallWBorderedSideGradientRect(x, y, x1, y1, inside, inside2, border);
	}

	public void drawBorderedUpperRect(int x, int y, int x1, int y1, int inside, int border) {
		meth.drawSmallWDaFuqBorderedRect(x, y, x1, y1, inside, border);
	}

	public void drawMinimize(int x, int y, boolean flag) {
		FontRenderer font = mc.fontRenderer;
		drawBorderedRect(x, y, x + 10, y + 10, 0xFF000000, 0x904D4D4D);
		drawCenteredSmallString(font, x + 5, y + 3, flag ? "-" : "+");
	}

	public void drawPin(int x, int y, boolean flag, int i, int j) {
		FontRenderer font = mc.fontRenderer;
		if(mc.currentScreen instanceof Clickable && isMouseAround(x, x + 10, y, y + 10, i, j)) {
			if(!flag) {drawBorderedRect(x, y, x + 10, y + 10, 0xFF000000, 0x4300AA00);
			} else {
				drawBorderedRect(x, y, x + 10, y + 10, 0xFF000000, 0x43FF0000);
			}
		} else {
			drawBorderedRect(x, y, x + 10, y + 10, 0xFF000000, flag ? 0x9000AA00 : 0x90FF0000);
		}
	}

	public void drawCenteredTTFString(String name, int x, int y) {
		Gui gui = new Gui();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
		GL11.glHint(GL11.GL_POINT_SMOOTH_HINT, GL11.GL_NICEST);
		GL11.glDepthMask(false);
		ttfFont3.drawStringS(gui, name, x - ttfFont.getStringWidth(name) / 2, y);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_POINT_SMOOTH);
		GL11.glDepthMask(true);
	}

	public void drawCenteredSmallString(FontRenderer font, int x, int y, String s) {
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		drawCenteredString(font, s, x * 2, y * 2, 0xFFFFFFFF);
		GL11.glScalef(2F, 2F, 2F);
	}

	public void drawCenteredSmallTTFString(String s, int x, int y) {
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		drawCenteredTTFString(s, x * 2, y * 2);
		GL11.glScalef(2F, 2F, 2F);
	}

	public void drawSmallString(FontRenderer font, int x, int y, String s) {
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		drawString(font, s, x * 2, y * 2, 0xFFFFFFFF);
		GL11.glScalef(2F, 2F, 2F);
	}

	public void drawSmallTTFString(String s, int x, int y) {
		Gui gui = new Gui();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
		GL11.glHint(GL11.GL_POINT_SMOOTH_HINT, GL11.GL_NICEST);
		GL11.glDepthMask(false);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		ttfFont.drawStringS(gui, Color.WHITE + s, x * 2, y * 2);
		GL11.glScalef(2F, 2F, 2F);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_POINT_SMOOTH);
		GL11.glDepthMask(true);
	}

	public void drawSmallTTFString2(String s, int x, int y) {
		Gui gui = new Gui();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
		GL11.glHint(GL11.GL_POINT_SMOOTH_HINT, GL11.GL_NICEST);
		GL11.glDepthMask(false);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		ttfFont2.drawStringS(gui, Color.WHITE + s, x * 2, y * 2);
		GL11.glScalef(2F, 2F, 2F);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_POINT_SMOOTH);
		GL11.glDepthMask(true);
	}

	public void drawSmallTTFString3(String s, int x, int y) {
		Gui gui = new Gui();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
		GL11.glHint(GL11.GL_POINT_SMOOTH_HINT, GL11.GL_NICEST);
		GL11.glDepthMask(false);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		ttfFont4.drawString(gui, Color.WHITE + s, x * 2, y * 2, false);
		GL11.glScalef(2F, 2F, 2F);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_POINT_SMOOTH);
		GL11.glDepthMask(true);
	}

	public void drawButton(int x, int y, String name, boolean flag, int i, int j, boolean showInfo, String info, int x2) {
		FontRenderer font = mc.fontRenderer;
		Gui gui = new Gui();

		if(mc.currentScreen instanceof Clickable && isMouseAround(x + 72, x + 104, y + 4, y + 15, i, j)) {
			drawBorderedGradientRect(x, y + 4, x + 106, y + 16, 0xFF000000, 0x43FFFFFF, 0x43000000);
			drawBorderedRect(x + 73, y + 5, x + 104, y + 15, 0xFF000000, 0x904D4D4D);
			if(showInfo) {drawBorderedRect(x + 107, y + 6, x + 107 + x2, y + 14, 0xFF000000, 0x500E0E0E);}
		} else {
			drawBorderedRect(x + 73, y + 5, x + 104, y + 15, 0xFF000000, 0x904D4D4D);
		}

		if(mc.currentScreen instanceof Clickable && isMouseAround(x + 72, x + 104, y + 4, y + 15, i, j)) {
			drawCenteredSmallTTFString(flag ? Color.YELLOW + " ENABLED" : Color.YELLOW + " DISABLED", x + 100, y + 5);
			drawSmallTTFString(Color.YELLOW + name, x + 2, y + 3);
			if(showInfo) {drawSmallTTFString3(info, x + 109, y + 5);}
		} else {
			drawCenteredSmallTTFString(flag ? Color.WHITE + "ENABLED" : Color.WHITE + "DISABLED", x + 99, y + 5);
			drawSmallTTFString(name, x + 2, y + 3);
		}
	}

	public void drawxRayButton(int id, int x, int y, int i, int j, boolean isInxRay) {
		FontRenderer fr = mc.fontRenderer;

		GL11.glPushMatrix();
		RenderHelper.enableStandardItemLighting();
		RenderHelper.enableGUIStandardItemLighting();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)240 / 1.0F, (float)240 / 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		itemRenderer.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, new ItemStack(Item.itemsList[Block.chest.blockID], 1), -16, -16);
		itemRenderer.drawItemIntoGui(fr, mc.renderEngine, id, 0, 53, x + 1, y + 1);
		/*itemRenderer.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, new ItemStack(Item.itemsList[id], 1), x, y);
		itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, new ItemStack(Item.itemsList[id],1), x, y);*/
		RenderHelper.disableStandardItemLighting();
		GL11.glPopMatrix();

		if(mc.currentScreen instanceof Clickable && isMouseAround(x, x + 18, y, y + 18, i, j)) {
			if(!isInxRay) {
				drawBorderedRect(x, y, x + 18, y + 18, 0xFF000000, 0x4300AA00);
			} else {
				drawBorderedRect(x, y, x + 18, y + 18, 0xFF000000, 0x43FF0000);}
		} else {
			drawBorderedRect(x, y, x + 18, y + 18, 0xFF000000, isInxRay ? 0x9000AA00 : 0x90FF0000);
		}
	}

	public void drawSettingsThing(String name, float amount, int x, int y, int i, int j) {
		FontRenderer fr = mc.fontRenderer;
		Methods meth = new Methods();

		if(mc.currentScreen instanceof Clickable && isMouseAround(x + 1, x + 11, y, y + 11, i, j)) {
			drawBorderedGradientRect(x + 1, y, x + 103, y + 11, 0xFF000000, 0x43FFFFFF, 0x43000000);

			meth.drawSmallWBorderedRect(x + 1, y, x + 11, y + 11, 0xFF000000, 0x904D4D4D);
			meth.drawSmallWBorderedRect(x + 94, y, x + 103, y + 11, 0xFF000000, 0x904D4D4D);
			fr.drawString(Color.YELLOW + "<", x + 3, y + 2, 0xFFFFFFFF);
			fr.drawString(">", x + 97, y + 2, 0xFFFFFFFF);
			drawCenteredString(fr, Color.YELLOW + " " + name + " : " + (int)amount, x + 50, y + 2, 0xFFFFFFFF);
		} else if(mc.currentScreen instanceof Clickable && isMouseAround(x + 93, x + 103, y, y + 11, i, j)) {
			drawBorderedGradientRect(x + 1, y, x + 103, y + 11, 0xFF000000, 0x43000000, 0x43FFFFFF);

			meth.drawSmallWBorderedRect(x + 1, y, x + 11, y + 11, 0xFF000000, 0x904D4D4D);
			meth.drawSmallWBorderedRect(x + 94, y, x + 103, y + 11, 0xFF000000, 0x904D4D4D);
			fr.drawString("<", x + 3, y + 2, 0xFFFFFFFF);
			fr.drawString(Color.YELLOW + ">", x + 97, y + 2, 0xFFFFFFFF);
			drawCenteredString(fr, Color.YELLOW + " " + name + " : " + (int)amount, x + 50, y + 2, 0xFFFFFFFF);
		} else {
			meth.drawSmallWHollowBorderedRect(x + 1, y, x + 103, y + 11, 0xFF000000);
			meth.drawSmallWBorderedRect(x + 1, y, x + 11, y + 11, 0xFF000000, 0x904D4D4D);
			meth.drawSmallWBorderedRect(x + 94, y, x + 103, y + 11, 0xFF000000, 0x904D4D4D);
			fr.drawString("<", x + 3, y + 2, 0xFFFFFFFF);
			fr.drawString(">", x + 97, y + 2, 0xFFFFFFFF);
			drawCenteredString(fr, name + " : " + (int)amount, x + 50, y + 2, 0xFFFFFFFF);
		}
	}

	public int sW(String s) {
		return ttfFont4.getStringWidth(s);
	}

	public static boolean isMouseAround(int x, int x1, int y, int y1, int i, int j){
		if(i > x && i < x1 && j > y && j < y1) {
			return true;
		}else{
			return false;
		}
	}

	public void drawToolTip(String s, int x, int y, int x1, int y1) {
		drawBorderedRect(x, y, x1, y1, 0xFF000000, 0x500E0E0E);
		drawCenteredSmallString(mc.fontRenderer, x + 2 + 4, y + 2, s);
	}

	public void drawPlayer(int x, int y) {
		EntityOtherPlayerMP player = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.username);
		int j = x + 14;
		int k = y + 56;
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glTranslatef(j, k, 50F);
		float f = 30F;
		GL11.glScalef(-f, f, f);
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		player.rotationYaw = 0;
		player.rotationYawHead = 0;
		player.renderYawOffset = 0;
		player.isDead = true;
		RenderManager.instance.renderEntityWithPosYaw(player, 0.0D, 0.0D, 0.0D, 0.0F, 98.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	public static void playSound() {
		mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
	}

	public static void drawCenteredStrings(FontRenderer par1FontRenderer, String par2Str, int par3, int par4, int par5) {
		par1FontRenderer.drawString(par2Str, par3 - par1FontRenderer.getStringWidth(par2Str) / 2, par4, par5);
	}

	public static boolean dXC(int id) {
		return mc.comet.settings.blockList.contains(id);
	}

	public static void ifxRay() {
		if(mc.comet.keys.xray.isRunning()) {mc.comet.utils.rerender();}
	}

	public static void addBlock(int id) {
		if(!dXC(id)) {
			mc.comet.settings.blockList.add(id);
		} else {
			System.out.print("\n" + "[COMET] xRay already contains that ID!" + "\n");
		}
	}

	public static void delBlock(int id) {
		if(dXC(id)) {
			mc.comet.settings.blockList.remove(mc.comet.settings.blockList.indexOf(id));
		} else {
			System.out.print("\n" + "[COMET] xRay doesn't contain that ID!" + "\n");
		}
	}

	public static void block(int id) {
		if(!dXC(id)) {addBlock(id);} else {delBlock(id);}
	}

}
