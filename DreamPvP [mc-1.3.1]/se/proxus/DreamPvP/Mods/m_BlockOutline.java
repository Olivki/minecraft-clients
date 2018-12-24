package se.proxus.DreamPvP.Mods;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import net.minecraft.src.*;
import se.proxus.DreamPvP.Interfaces.*;
import se.proxus.DreamPvP.Utils.*;
import se.proxus.DreamPvP.Utils.Placeholders.Mark;
import se.proxus.DreamPvP.Utils.Placeholders.u_OBlock;

import static org.lwjgl.input.Keyboard.*;

import static org.lwjgl.opengl.GL11.*;

public class m_BlockOutline extends Base_Mod implements Renderer {

	public ArrayList<u_OBlock> damageArray = new ArrayList<u_OBlock>();

	public m_BlockOutline() {
		super('7', "BlockOutline", "Draws a box around blocks.", KEY_NONE, "World", "[§eW§r] ");
	}

	@Override
	public void onEnabled() {
		dp.ingame.guiArray.remove(getName());
		dp.interfaces.renderArray.add(this);
	}

	@Override
	public void onDisabled() {
		dp.interfaces.renderArray.remove(this);
	}

	@Override
	public void onRendered() {
		if(dp.mc.objectMouseOver != null) {
			drawOutline();
			
			for(int var1 = 0; var1 < damageArray.size() / 10; var1++) {
				drawOutline();
			}
		}
	}

	public void drawOutline() {
		int id = dp.mc.theWorld.getBlockId(dp.mc.objectMouseOver.blockX, dp.mc.objectMouseOver.blockY, dp.mc.objectMouseOver.blockZ);
		Block block = Block.blocksList[id];
		double playerX = dp.mc.thePlayer.lastTickPosX + (dp.mc.thePlayer.posX - dp.mc.thePlayer.lastTickPosX) * (double)dp.mc.timer.renderPartialTicks;
		double playerY = dp.mc.thePlayer.lastTickPosY + (dp.mc.thePlayer.posY - dp.mc.thePlayer.lastTickPosY) * (double)dp.mc.timer.renderPartialTicks;
		double playerZ = dp.mc.thePlayer.lastTickPosZ + (dp.mc.thePlayer.posZ - dp.mc.thePlayer.lastTickPosZ) * (double)dp.mc.timer.renderPartialTicks;
		double x = dp.mc.objectMouseOver.blockX - playerX, y = dp.mc.objectMouseOver.blockY - playerY, z = dp.mc.objectMouseOver.blockZ - playerZ;

		dp.render.enableDefaults();
		//dp.utils.log();
		glColor4f(dp.settings.bC1, dp.settings.bC2, dp.settings.bC3, 0.15F);
		dp.render.drawBox(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D);
		glColor4f(dp.settings.bC1, dp.settings.bC2, dp.settings.bC3, 1.0F);
		dp.render.drawOutlinedBox(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D, 1.0F);
		dp.render.disableDefaults();
	}
}