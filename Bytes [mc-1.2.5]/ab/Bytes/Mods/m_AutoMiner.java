package ab.Bytes.Mods;

import net.minecraft.src.EnumMovingObjectType;

import org.lwjgl.input.Keyboard;

import ab.Bytes.Bytes;
import ab.Bytes.Interfaces.*;

public class m_AutoMiner extends Base_Mod implements Updateable {

	public m_AutoMiner() {
		super('c', "Auto miner", Keyboard.KEY_M);
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
		if(bs.mc.objectMouseOver != null) {
			int x = bs.mc.objectMouseOver.blockX;
			int y = bs.mc.objectMouseOver.blockY;
			int z = bs.mc.objectMouseOver.blockZ;
			boolean I1 = bs.mc.objectMouseOver != null && bs.mc.objectMouseOver.typeOfHit == EnumMovingObjectType.TILE;
			boolean I2 = x != 0 && y != 0 && z != 0;

			if(I1 && I2) {
				bs.mc.playerController.onPlayerDamageBlock(x, y, z, bs.mc.objectMouseOver.sideHit);
				
				if (bs.mc.thePlayer.canPlayerEdit(x, y, z)) {
					bs.mc.effectRenderer.addBlockHitEffects(x, y, z, bs.mc.objectMouseOver.sideHit);
					bs.mc.thePlayer.swingItem();
				}
			}
		}
	}

}
