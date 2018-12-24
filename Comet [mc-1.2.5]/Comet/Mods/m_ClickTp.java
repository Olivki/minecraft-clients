package Comet.Mods;

import Comet.*;
import Comet.Utils.Hooks.ClickBlock;
import net.minecraft.src.*;

public class m_ClickTp extends Base_Mod implements ClickBlock {

	public static float oldReach;
	
	public m_ClickTp() {
		super(Base_Enum.ClickTp);
	}

	@Override
	public void onStart() {
		Comet.utils.addToClickBlock(this);
		enabled = true;
		Comet.ingame.array.add("Click tp");
		oldReach = Comet.settings.reach;
		Comet.settings.reach = 999F;
		System.out.print("\n" + "[COMET] Old reach : " + oldReach + "\n");
		System.out.print("[COMET] New reach : " + Comet.settings.reach + "\n" + "\n");
	}

	@Override
	public void onStop() {
		Comet.utils.removeFromClickBlock(this);
		enabled = false;
		Comet.ingame.array.remove("Click tp");
		Comet.settings.reach = oldReach;
	}

	@Override
	public void onClickBlock(int I1, int I2, int I3, int I4) {
		Comet.teleport.TeleportTo((double)I1, (double)I2 - mc.thePlayer.posY, (double)I3);
		System.out.print("\n" + "[COMET] Click teleporting to : " + I1 + ", " + I2 + ", " + I3 + "." + "\n");
	}

}
