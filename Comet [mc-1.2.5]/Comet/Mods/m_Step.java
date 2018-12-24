package Comet.Mods;

import Comet.*;
import net.minecraft.src.*;

public class m_Step extends Base_Mod {

	public static float oldStep;
	public static float newStep = 1.5F;

	public m_Step() {
		super(Base_Enum.Step);
	}

	@Override
	public void onStart() {
		enabled = true;
		oldStep = Comet.settings.step;
		Comet.settings.step = newStep;
		Comet.ingame.array.add("Step");
		System.out.print("\n" + "[COMET] Old step : " + oldStep + "\n");
		System.out.print("[COMET] New step : " + Comet.settings.step + "\n" + "\n");
	}

	@Override
	public void onStop() {
		enabled = false;
		Comet.settings.step = oldStep;
		Comet.ingame.array.remove("Step");
	}

}
