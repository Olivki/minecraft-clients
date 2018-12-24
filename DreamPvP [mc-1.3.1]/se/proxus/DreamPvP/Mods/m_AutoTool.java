package se.proxus.DreamPvP.Mods;

import static org.lwjgl.input.Keyboard.*;

import org.lwjgl.input.Mouse;

import net.minecraft.src.*;
import se.proxus.DreamPvP.Interfaces.*;

public class m_AutoTool extends Base_Mod implements ClickBlock {

	private int prevTool;
	private boolean wasKeyDown;

	public m_AutoTool() {
		super('7', "AutoTool", "Makes you switch to the best tool.", KEY_J, "World", "[§eW§r] ");
	}

	@Override
	public void onEnabled() {
		dp.interfaces.clickBlockArray.add(this);
	}

	@Override
	public void onDisabled() {
		dp.interfaces.clickBlockArray.remove(this);
	}

	@Override
	public void onClickBlock(int i1, int i2, int i3, int i4) {
		blockAutoTool();
	}
	
	public void blockAutoTool() {
		if(dp.mc.objectMouseOver == null || dp.mc.currentScreen != null) {return;}

		try {
			int bestTool = 0;
			float bestStr = 0;

			if(wasKeyDown && !Mouse.isButtonDown(0)) {
				wasKeyDown = false;
				dp.mc.thePlayer.inventory.currentItem = prevTool;
			}

			if (!Mouse.isButtonDown(0)) {prevTool = dp.mc.thePlayer.inventory.currentItem; return;}

			wasKeyDown = true;

			if(dp.mc.objectMouseOver.typeOfHit == EnumMovingObjectType.TILE) {
				int blockid = dp.mc.theWorld.getBlockId(dp.mc.objectMouseOver.blockX, dp.mc.objectMouseOver.blockY, dp.mc.objectMouseOver.blockZ);
				Block block = Block.blocksList[blockid];

				for (int g = 0; g < 9; g++) {
					if (dp.mc.thePlayer.inventory.mainInventory[g]!=null && dp.mc.thePlayer.inventory.getStackInSlot(g).getStrVsBlock(block) > bestStr) {
						bestStr = dp.mc.thePlayer.inventory.getStackInSlot(g).getStrVsBlock(block);
						bestTool = g;
					}
				}

				dp.mc.thePlayer.inventory.currentItem = bestTool;
			}
		} catch (Exception e) {
			dp.utils.log("Prevented a auto tool crash.");
		}
	}
	
	public void entityAutoTool(Entity e) {
		if(dp.mc.objectMouseOver == null || dp.mc.currentScreen != null) {return;}

		try {
			int bestTool = 0;
			float bestStr = 0;

			if(wasKeyDown && !Mouse.isButtonDown(0)) {
				wasKeyDown = false;
				dp.mc.thePlayer.inventory.currentItem = prevTool;
			}

			if (!Mouse.isButtonDown(0)) {prevTool = dp.mc.thePlayer.inventory.currentItem; return;}

			wasKeyDown = true;
			if (dp.mc.objectMouseOver.typeOfHit == EnumMovingObjectType.ENTITY) {

				for (int g = 0; g < 9; g++) {
					if (dp.mc.thePlayer.inventory.mainInventory[g] != null && dp.mc.thePlayer.inventory.getStackInSlot(g).getDamageVsEntity(e) > bestStr) {
						bestStr = dp.mc.thePlayer.inventory.getStackInSlot(g).getDamageVsEntity(e);
						bestTool = g;
					}
				}

				dp.mc.thePlayer.inventory.currentItem = bestTool;
			}
		} catch (Exception er) {
			dp.utils.log("Prevented a auto tool crash.");
		}
	}
}