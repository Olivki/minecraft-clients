package Comet.Mods;

import Comet.*;
import Comet.Utils.Settings;
import Comet.Utils.Utils;
import Comet.Utils.Hooks.ClickBlock;
import Comet.Utils.Hooks.Tickable;
import net.minecraft.src.*;

public class m_InstantMine extends Base_Mod implements ClickBlock, Tickable {

	public m_InstantMine() {
		super(Base_Enum.InstantMine);
	}

	@Override
	public void onStart() {
		enabled = true;
		Comet.utils.addToClickBlock(this);
		Comet.utils.addToTick(this);
		Comet.ingame.array.add("Instant mine");
	}

	@Override
	public void onStop() {
		enabled = false;
		Comet.utils.removeFromClickBlock(this);
		Comet.utils.removeFromTick(this);
		Comet.ingame.array.remove("Instant mine");
	}

	@Override
	public void onClickBlock(int I1, int I2, int I3, int I4) {
		Utils.sendPacket(new Packet14BlockDig(2, I1, I2, I3, I4));
	}

	@Override
	public void onTick() {
		//TODO: Rape small kids.
		if (mc.objectMouseOver!=null) {
			if(Settings.instantX!=mc.objectMouseOver.blockX || Settings.instantY!=mc.objectMouseOver.blockY || Settings.instantZ!=mc.objectMouseOver.blockZ) {
				Settings.instantX = mc.objectMouseOver.blockX;
				Settings.instantY = mc.objectMouseOver.blockY;
				Settings.instantZ = mc.objectMouseOver.blockZ;
				Utils.sendPacket(new Packet18Animation(mc.thePlayer, 1));
				for (int x = -1; x < 1; x++)
					for (int y = -1; y < 1; y++)
						for (int z = -1; z < 1; z++)
							Utils.sendPacket(new Packet14BlockDig(0, Settings.instantX + x, Settings.instantY + y, Settings.instantZ + z, 0));
			} else {
				Utils.sendPacket(new Packet18Animation(mc.thePlayer, 1));
				Utils.sendPacket(new Packet14BlockDig(1, Settings.instantX, Settings.instantY, Settings.instantZ, 0));
			}
		}
	}

}
