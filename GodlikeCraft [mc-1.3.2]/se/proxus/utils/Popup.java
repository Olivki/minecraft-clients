package se.proxus.utils;

import java.util.*;

import net.minecraft.client.*;
import net.minecraft.src.*;

import org.lwjgl.opengl.GL11;

import se.proxus.utils.placeholders.*;

public class Popup extends Utils {
	
	private static RenderItem itemRenderer = new RenderItem();	
	public ArrayList<p_Popup> popupArray = new ArrayList<p_Popup>();
	public static float update1 = 0.0F;
	public static float update2 = 0.0F;
	
	public static void drawPopup(int var1, int var2) {
		update1 += 0.2F;
		update2 += 0.2F;
		ingame.drawButtonRect(1, 1 + update1, 15, 15 + update2, false);
	}
	
	private void drawItem(int x, int y, int id) {
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		itemRenderer.renderItemIntoGUI(Minecraft.theMinecraft.fontRenderer, Minecraft.theMinecraft.renderEngine, new ItemStack(Block.chest), 999, 999);
		itemRenderer.drawItemIntoGui(Minecraft.theMinecraft.fontRenderer, Minecraft.theMinecraft.renderEngine, id, 0, Item.itemsList[id].getIconFromDamage(0), x + 1, y + 1);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
	}
}