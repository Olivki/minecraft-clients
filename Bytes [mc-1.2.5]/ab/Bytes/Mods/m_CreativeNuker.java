package ab.Bytes.Mods;

import net.minecraft.src.*;

import org.lwjgl.input.Keyboard;

import ab.Bytes.Interfaces.*;

public class m_CreativeNuker extends Base_Mod implements Updateable {

	public int nukeSize = 5;

	public m_CreativeNuker() {
		super('r', "Creative nuker", Keyboard.KEY_NONE);
	}

	@Override
	public void onEnabled() {
		bs.utils.updates.add(this);
	}

	@Override
	public void onDisabled() {
		bs.utils.updates.remove(this);
	}

	@Override
	public void onUpdate() {
		for(int I1 = nukeSize; I1 > -nukeSize; I1--) {
			for(int I2 = nukeSize; I2 > -nukeSize; I2--) {
				for(int I3 = nukeSize; I3 > -nukeSize; I3--) {
					EntityPlayerSP tp = bs.mc.thePlayer;
					double I4 = tp.posX + (double)I1;
					double I5 = tp.posY + (double)I2;
					double I6 = tp.posZ + (double)I3;
					int I7 = bs.mc.theWorld.getBlockId((int)I4, (int)I5, (int)I6);
					Block I8 = Block.blocksList[I7];
					boolean I9 = I8 != null && I7 != 0 && tp.capabilities.isCreativeMode;

					if(I9) {
						bs.utils.sendPacket(new Packet14BlockDig(0, (int)I4, (int)I5, (int)I6, 1));
						bs.utils.sendPacket(new Packet14BlockDig(2, (int)I4, (int)I5, (int)I6, 1));
					}
				}
			}
		}
	}
}