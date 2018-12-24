package se.proxus.panels;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import se.proxus.gui.utils.*;
import se.proxus.panels.panels.*;
import net.minecraft.client.*;
import net.minecraft.src.*;

public class Base_Panels extends GuiScreen {

	public static ArrayList<Base_Panel> 
	panelArray = new ArrayList<Base_Panel>(),
	panelBeforeArray = new ArrayList<Base_Panel>();

	public static TTF ttf = new TTF(Minecraft.getMinecraft().ingameGUI, Minecraft.getMinecraft(), Minecraft.getMinecraft().dm.vars.fontName, Minecraft.getMinecraft().dm.vars.fontSize);

	public static Base_Panel
	player = null,
	aura = null,
	render = null,
	server = null,
	panels = null,
	config = null;

	public static int scaledWidth;
	public static int scaledHeight;
	public static double scaledWidthD;
	public static double scaledHeightD;
	public static int scaleFactor;

	public static boolean panelAdded = false;

	public Base_Panels() {
		this.init();
	}

	public void setScale() {
		scaledWidth = mc.displayWidth;
		scaledHeight = mc.displayHeight;
		scaleFactor = 1;
		int k = mc.gameSettings.guiScale;

		if (k == 0) {
			k = 1000;
		}

		for (; scaleFactor < k && scaledWidth / (scaleFactor + 1) >= 320 && scaledHeight / (scaleFactor + 1) >= 240; scaleFactor++) {}

		scaledWidthD = (double)scaledWidth / (double)scaleFactor;
		scaledHeightD = (double)scaledHeight / (double)scaleFactor;
		scaledWidth = (int)Math.ceil(scaledWidthD);
		scaledHeight = (int)Math.ceil(scaledHeightD);
	}

	@Override
	public void drawScreen(int var1, int var2, float var3) {
		this.setScale();

		for(Base_Panel var4 : this.panelArray) {
			var4.draw(var1, var2);
		}
	}

	@Override
	public void mouseClicked(int var1, int var2, int var3) {
		try {
			for(Base_Panel var4 : this.panelArray) {
				var4.mouseClicked(var1, var2);
			}
		} catch (Exception e) {

		}
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();

		for(Base_Panel var1 : this.panelArray) {
			var1.handleMouseInput();
		}
	}

	public void init() {
		this.addPanel(player = new p_Player());
		this.addPanel(aura = new p_Aura());
		this.addPanel(config = new p_Config());

		if(!(this.panelAdded)) {
			this.panelArray.add(panels = new p_Panels());
			mc.dm.files.loadPanelSettings();
			this.panelAdded = true;
		}
	}

	public void addPanel(Base_Panel var1) {
		if(!(this.panelBeforeArray.contains(var1))) {
			this.panelBeforeArray.add(var1);
		}
	}

	public static void startCut(int x, int y, int x2, int y2) {
		GL11.glScissor(x * scaleFactor, (scaledHeight - y2) * scaleFactor, (x2 - x) * scaleFactor, (y2 - y) * scaleFactor);
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
	}

	public static void stopCut() {
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}
	
	public static void sendPanelToTop(Base_Panel var1) {
		if(panelArray.contains(var1)) {
			panelArray.remove(panelArray.indexOf(var1));
			panelArray.add(panelArray.size(), var1);
		}
	}
}