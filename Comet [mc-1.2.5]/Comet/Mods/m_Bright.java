package Comet.Mods;

import net.minecraft.src.*;
import Comet.Utils.Utils;
import Comet.Utils.Hooks.Tickable;

public class m_Bright extends Base_Mod implements Tickable {

	public float brightness;

	public m_Bright() {
		super(Base_Enum.Bright);
	}

	@Override
	public void onStart() {
		enabled = true;
		mc.comet.ingame.array.add("Bright");
		mc.comet.utils.addToTick(this);
	}

	@Override
	public void onStop() {
		enabled = false;
		mc.comet.ingame.array.remove("Bright");
		if(brightness == 0){mc.comet.utils.removeFromTick(this);}
	}

	@Override
	public void onTick() {
		if (isRunning() && brightness < 10)
			brightness += 0.7F;
		if (!isRunning() && brightness > 0)
			brightness -= 0.7F;
	}

}
