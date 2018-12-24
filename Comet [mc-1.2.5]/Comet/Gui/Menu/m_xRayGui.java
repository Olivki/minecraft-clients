package Comet.Gui.Menu;

import java.awt.Cursor;

import net.minecraft.src.*;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import Comet.Utils.Color;

public class m_xRayGui extends WindowBase {

	public static boolean enabled = false;
	public static boolean pinned = false;
	public static boolean dragging;
	private static int dragX = 1;
	private static int dragY = 18;
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
		//font.drawStringWithShadow("xRay", dragX + 2, dragY + 1, 0xFFFFFFFF);
		drawSmallTTFString2("xRay", dragX + 2, dragY - 4);

		if(enabled) {
			drawBorderedRect(dragX, dragY + 11, dragX + 106, dragY + 109, 0xFF000000, 0x500E0E0E);

			drawxRayButton(Block.oreCoal.blockID, dragX + 1, dragY + 12, i, j, dXC(Block.oreCoal.blockID));
			drawxRayButton(Block.oreIron.blockID, dragX + 23, dragY + 12, i, j, dXC(Block.oreIron.blockID));
			drawxRayButton(Block.oreGold.blockID, dragX + 44, dragY + 12, i, j, dXC(Block.oreGold.blockID));
			drawxRayButton(Block.oreDiamond.blockID, dragX + 65, dragY + 12, i, j, dXC(Block.oreDiamond.blockID));
			drawxRayButton(Block.oreRedstone.blockID, dragX + 87, dragY + 12, i, j, dXC(Block.oreRedstone.blockID));

			drawxRayButton(Block.cobblestone.blockID, dragX + 1, dragY + 31, i, j, dXC(Block.cobblestone.blockID));
			drawxRayButton(Block.cobblestoneMossy.blockID, dragX + 23, dragY + 31, i, j, dXC(Block.cobblestoneMossy.blockID));
			drawxRayButton(Block.stoneBrick.blockID, dragX + 44, dragY + 31, i, j, dXC(Block.stoneBrick.blockID));
			drawxRayButton(Block.stoneOvenActive.blockID, dragX + 65, dragY + 31, i, j, dXC(Block.stoneOvenActive.blockID));
			drawxRayButton(Block.sandStone.blockID, dragX + 87, dragY + 31, i, j, dXC(Block.sandStone.blockID));

			drawxRayButton(Block.bookShelf.blockID, dragX + 1, dragY + 50, i, j, dXC(Block.bookShelf.blockID));
			drawxRayButton(Block.planks.blockID, dragX + 23, dragY + 50, i, j, dXC(Block.planks.blockID));
			drawxRayButton(Block.workbench.blockID, dragX + 44, dragY + 50, i, j, dXC(Block.workbench.blockID));
			drawxRayButton(Block.trapdoor.blockID, dragX + 65, dragY + 50, i, j, dXC(Block.trapdoor.blockID));
			drawxRayButton(Block.wood.blockID, dragX + 87, dragY + 50, i, j, dXC(Block.wood.blockID));

			drawxRayButton(Block.tnt.blockID, dragX + 1, dragY + 70, i, j, dXC(Block.tnt.blockID));
			drawxRayButton(Block.glass.blockID, dragX + 23, dragY + 70, i, j, dXC(Block.glass.blockID));
			drawxRayButton(Block.glowStone.blockID, dragX + 44, dragY + 70, i, j, dXC(Block.glowStone.blockID));
			drawxRayButton(Block.netherrack.blockID, dragX + 65, dragY + 70, i, j, dXC(Block.netherrack.blockID));
			drawxRayButton(Block.slowSand.blockID, dragX + 87, dragY + 70, i, j, dXC(Block.slowSand.blockID));

			drawxRayButton(Block.blockClay.blockID, dragX + 1, dragY + 90, i, j, dXC(Block.blockClay.blockID));
			drawxRayButton(Block.blockSteel.blockID, dragX + 23, dragY + 90, i, j, dXC(Block.blockSteel.blockID));
			drawxRayButton(Block.blockGold.blockID, dragX + 44, dragY + 90, i, j, dXC(Block.blockGold.blockID));
			drawxRayButton(Block.blockDiamond.blockID, dragX + 65, dragY + 90, i, j, dXC(Block.blockDiamond.blockID));
			drawxRayButton(Block.blockLapis.blockID, dragX + 87, dragY + 90, i, j, dXC(Block.blockLapis.blockID));
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
			if(i > dragX + 1 && i < dragX + 18 && j > dragY + (12) && j < dragY + (18 + 12)) {block(Block.oreCoal.blockID); playSound(); ifxRay();}
			if(i > dragX + 23 && i < dragX + 18 + 23 && j > dragY + (12) && j < dragY + (18 + 12)) {block(Block.oreIron.blockID); playSound(); ifxRay();}
			if(i > dragX + 44 && i < dragX + 18 + 44 && j > dragY + (12) && j < dragY + (18 + 12)) {block(Block.oreGold.blockID); playSound(); ifxRay();}
			if(i > dragX + 65 && i < dragX + 18 + 65 && j > dragY + (12) && j < dragY + (18 + 12)) {block(Block.oreDiamond.blockID); playSound(); ifxRay();}
			if(i > dragX + 87 && i < dragX + 18 + 87 && j > dragY + (12) && j < dragY + (18 + 12)) {block(Block.oreRedstone.blockID); block(Block.oreRedstoneGlowing.blockID); playSound(); ifxRay();}

			if(i > dragX + 1 && i < dragX + 18 && j > dragY + (31) && j < dragY + (18 + 31)) {block(Block.cobblestone.blockID); playSound(); ifxRay();}
			if(i > dragX + 23 && i < dragX + 18 + 23 && j > dragY + (31) && j < dragY + (18 + 31)) {block(Block.cobblestoneMossy.blockID); playSound(); ifxRay();}
			if(i > dragX + 44 && i < dragX + 18 + 44 && j > dragY + (31) && j < dragY + (18 + 31)) {block(Block.stoneBrick.blockID); playSound(); ifxRay();}
			if(i > dragX + 65 && i < dragX + 18 + 65 && j > dragY + (31) && j < dragY + (18 + 31)) {block(Block.stoneOvenActive.blockID); playSound(); ifxRay();}
			if(i > dragX + 87 && i < dragX + 18 + 87 && j > dragY + (31) && j < dragY + (18 + 31)) {block(Block.sandStone.blockID); playSound(); ifxRay();}

			if(i > dragX + 1 && i < dragX + 18 && j > dragY + (50) && j < dragY + (18 + 50)) {block(Block.bookShelf.blockID); playSound(); ifxRay();}
			if(i > dragX + 23 && i < dragX + 18 + 23 && j > dragY + (50) && j < dragY + (18 + 50)) {block(Block.planks.blockID); playSound(); ifxRay();}
			if(i > dragX + 44 && i < dragX + 18 + 44 && j > dragY + (50) && j < dragY + (18 + 50)) {block(Block.workbench.blockID); playSound(); ifxRay();}
			if(i > dragX + 65 && i < dragX + 18 + 65 && j > dragY + (50) && j < dragY + (18 + 50)) {block(Block.trapdoor.blockID); playSound(); ifxRay();}
			if(i > dragX + 87 && i < dragX + 18 + 87 && j > dragY + (50) && j < dragY + (18 + 50)) {block(Block.wood.blockID); playSound(); ifxRay();}

			if(i > dragX + 1 && i < dragX + 18 && j > dragY + (70) && j < dragY + (18 + 70)) {block(Block.tnt.blockID); playSound(); ifxRay();}
			if(i > dragX + 23 && i < dragX + 18 + 23 && j > dragY + (70) && j < dragY + (18 + 70)) {block(Block.glass.blockID); playSound(); ifxRay();}
			if(i > dragX + 44 && i < dragX + 18 + 44 && j > dragY + (70) && j < dragY + (18 + 70)) {block(Block.glowStone.blockID); playSound(); ifxRay();}
			if(i > dragX + 65 && i < dragX + 18 + 65 && j > dragY + (70) && j < dragY + (18 + 70)) {block(Block.netherrack.blockID); playSound(); ifxRay();}
			if(i > dragX + 87 && i < dragX + 18 + 87 && j > dragY + (70) && j < dragY + (18 + 70)) {block(Block.slowSand.blockID); playSound(); ifxRay();}

			if(i > dragX + 1 && i < dragX + 18 && j > dragY + (90) && j < dragY + (18 + 90)) {block(Block.blockClay.blockID); playSound(); ifxRay();}
			if(i > dragX + 23 && i < dragX + 18 + 23 && j > dragY + (90) && j < dragY + (18 + 90)) {block(Block.blockSteel.blockID); playSound(); ifxRay();}
			if(i > dragX + 44 && i < dragX + 18 + 44 && j > dragY + (90) && j < dragY + (18 + 90)) {block(Block.blockGold.blockID); playSound(); ifxRay();}
			if(i > dragX + 65 && i < dragX + 18 + 65 && j > dragY + (90) && j < dragY + (18 + 90)) {block(Block.blockDiamond.blockID); playSound(); ifxRay();}
			if(i > dragX + 87 && i < dragX + 18 + 87 && j > dragY + (90) && j < dragY + (18 + 90)) {block(Block.blockLapis.blockID); playSound(); ifxRay();}
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
