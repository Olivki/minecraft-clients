package se.proxus.utils;

import net.minecraft.client.*;
import net.minecraft.src.*;
import se.proxus.*;
import se.proxus.hooks.*;

public class Wrapper {
	
	public Minecraft getMinecraft() {
		return Minecraft.getMinecraft();
	}

	public HookEntityClientPlayer getPlayer() {
		return this.getMinecraft().thePlayer;
	}
	
	public HookPlayerController getController() {
		return this.getMinecraft().playerController;
	}
	
	public WorldClient getWorld() {
		return this.getMinecraft().theWorld;
	}
	
	public GuiIngame getIngameGui() {
		return this.getMinecraft().ingameGUI;
	}
	
	public RenderEngine getRenderEngine() {
		return this.getMinecraft().renderEngine;
	}
	
	public NetClientHandler getSendQueue() {
		return this.getMinecraft().getSendQueue();
	}
	
	public FontRenderer getFontRenderer() {
		return this.getMinecraft().fontRenderer;
	}
	
	public MovingObjectPosition getMouseOver() {
		return this.getMinecraft().objectMouseOver;
	}
	
	public Pendrum getPendrum() {
		return this.getMinecraft().pm;
	}
	
	public GameSettings getGameSettings() {
		return this.getMinecraft().gameSettings;
	}
	
	public ScaledResolution getScaledResolution() {
		return new ScaledResolution(this.getGameSettings(), this.getWidth(), this.getHeight());
	}
	
	public double getX() {
		return this.getPlayer().posX;
	}
	
	public double getY() {
		return this.getPlayer().posY;
	}
	
	public double getZ() {
		return this.getPlayer().posZ;
	}
	
	public String getUsername() {
		return this.getMinecraft().session.username;
	}
	
	public int getWidth() {
		return this.getMinecraft().displayWidth;
	}
	
	public int getHeight() {
		return this.getMinecraft().displayHeight;
	}
}