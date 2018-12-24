package se.proxus.DreamPvP.Mods;

import static org.lwjgl.input.Keyboard.*;
import net.minecraft.src.*;
import se.proxus.DreamPvP.Interfaces.*;

public class m_Instant extends Base_Mod implements Updates, ClickBlock {

	public float mineSpeed = 0.3F;

	public m_Instant() {
		super('7', "Instant", "Makes you mine faster.", KEY_NONE, "World", "[§eW§r] ");
	}

	@Override
	public void onEnabled() {
		dp.interfaces.updateArray.add(this);
		dp.interfaces.clickBlockArray.add(this);
	}

	@Override
	public void onDisabled() {
		dp.interfaces.updateArray.remove(this);
		dp.interfaces.clickBlockArray.remove(this);
	}
	
	@Override
	public void onClickBlock(int i1, int i2, int i3, int i4) {
		dp.utils.sendPacket(new Packet14BlockDig(2, i1, i2, i3, i4));
	}

	@Override
	public void onUpdate() {
		int x = 0, y = 0, z = 0;
		if(dp.mc.objectMouseOver != null) {
			if(x != dp.mc.objectMouseOver.blockX || y != dp.mc.objectMouseOver.blockY || z != dp.mc.objectMouseOver.blockZ) {
				x = dp.mc.objectMouseOver.blockX;
				y = dp.mc.objectMouseOver.blockY;
				z = dp.mc.objectMouseOver.blockZ;
				dp.utils.sendPacket(new Packet18Animation(dp.mc.thePlayer, 1));
				
				for(int xx = -1; xx < 1; xx++) {
					for(int yy = -1; yy < 1; yy++) {
						for(int zz = -1; zz < 1; zz++) {
							dp.utils.sendPacket(new Packet14BlockDig(0, x + xx, y + yy, z + zz, 0));
						}
					}
				}
			} else {
				dp.utils.sendPacket(new Packet18Animation(dp.mc.thePlayer, 1));
				dp.utils.sendPacket(new Packet14BlockDig(1, x, y, z, 0));
			}
		}
	}
}