package Comet.Mods;

import net.minecraft.src.*;

import org.lwjgl.input.Keyboard;

import Comet.Utils.Utils;
import Comet.Utils.Hooks.Tickable;

public class m_TextRadar extends Base_Mod implements Tickable {

	public m_TextRadar() {
		super(Base_Enum.TextRadar);
	}

	@Override
	public void onStart() {
		enabled = true;
		Utils.addToTick(this);
	}

	@Override
	public void onStop() {
		enabled = false;
		Utils.removeFromTick(this);
	}

	@Override
	public void onTick() {
		for(Object o : mc.theWorld.playerEntities) {
			Entity e = (Entity)o;
			FontRenderer fr = mc.fontRenderer;
			EntityPlayerSP tp = mc.thePlayer;
			int I2 = 0;

			if(e instanceof EntityPlayer) {
				EntityPlayer ep = (EntityPlayer)o;
				boolean I1 = ep.username != tp.username;
				int I3 = (int) Math.sqrt((tp.posX - ep.posX) * (tp.posX - ep.posX) + (tp.posZ - ep.posZ) * (tp.posZ - ep.posZ));
				int I5 = ep.getCurrentEquippedItem().itemID;
				String I4 = " [" + I3 + "] ";
				if(I1) {
					I2++;
					String s = ep.username + I4;
					fr.drawStringWithShadow(s, 1, 8 * I2 + 40, 0xFFFFFFFF);
				}
			}
		}
	}

}
