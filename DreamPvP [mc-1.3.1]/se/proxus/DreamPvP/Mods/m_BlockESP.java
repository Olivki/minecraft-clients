package se.proxus.DreamPvP.Mods;

import net.minecraft.src.*;
import se.proxus.DreamPvP.Interfaces.*;
import se.proxus.DreamPvP.Utils.*;
import se.proxus.DreamPvP.Utils.Placeholders.Mark;

import static org.lwjgl.input.Keyboard.*;

import static org.lwjgl.opengl.GL11.*;

public class m_BlockESP extends Base_Mod implements Renderer {
	
	private Mark espBlock[];
	private int espSize = 0;
	private int espRefreshTimer = 10;
	public int id = 10000;

	public m_BlockESP() {
		super('7', "Block_ESP", "Draws a box around blocks.", KEY_V, "World", "[§eW§r] ");
		espBlock= new Mark[10000000];
	}

	@Override
	public void onEnabled() {
		dp.interfaces.renderArray.add(this);
	}

	@Override
	public void onDisabled() {
		dp.interfaces.renderArray.remove(this);
	}

	@Override
	public void onRendered() {
		espRefreshTimer--;

		if(espRefreshTimer == 0) {
			espRefresh();
			espRefreshTimer = 40;
		}

		double playerX = dp.mc.thePlayer.lastTickPosX + (dp.mc.thePlayer.posX - dp.mc.thePlayer.lastTickPosX) * (double)dp.mc.timer.renderPartialTicks;
		double playerY = dp.mc.thePlayer.lastTickPosY + (dp.mc.thePlayer.posY - dp.mc.thePlayer.lastTickPosY) * (double)dp.mc.timer.renderPartialTicks;
		double playerZ = dp.mc.thePlayer.lastTickPosZ + (dp.mc.thePlayer.posZ - dp.mc.thePlayer.lastTickPosZ) * (double)dp.mc.timer.renderPartialTicks;

		for(int cur = 0; cur < espSize; cur++) {
			if(espBlock[cur].id == id) {
				float col1 = dp.settings.bC1, col2 = dp.settings.bC2, col3 = dp.settings.bC3;
				dp.render.drawBlockESP((double)espBlock[cur].posX, (double)espBlock[cur].posY, (double)espBlock[cur].posZ, col1, col2, col3);
			}
		}
	}

	public void espRefresh() {
		espSize=0;
		for(int y = 0; y < 256; y++) {
			for(int x = 0; x < dp.settings.espCheckSize; x++) {
				for(int z = 0; z < dp.settings.espCheckSize; z++) {

					int cX = (int)dp.mc.thePlayer.posX - (int)dp.settings.espCheckSize/2+x;
					int cY = y;
					int cZ = (int)dp.mc.thePlayer.posZ - (int)dp.settings.espCheckSize/2+z;
					int ids = dp.mc.theWorld.getBlockId(cX, cY, cZ);
					
					if (ids == id) {
						espBlock[espSize++] = new Mark(cX, cY, cZ, ids);
					}
				}
			}
		}
	}
}
