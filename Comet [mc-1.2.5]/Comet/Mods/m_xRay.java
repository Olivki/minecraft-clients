package Comet.Mods;

import Comet.Utils.Utils;
import Comet.Utils.Hooks.*;

public class m_xRay extends Base_Mod implements Tickable {
	
	public float brightness;
	
	public m_xRay() {
		super(Base_Enum.xRay);
	}

	@Override
	public void onStart() {	
		enabled = true;
		mc.comet.ingame.array.add("xRay");
		mc.comet.utils.addToTick(this);
		mc.comet.utils.rerender();
	}

	@Override
	public void onStop() {
		enabled = false;
		mc.comet.ingame.array.remove("xRay");
		if(brightness == 0){mc.comet.utils.removeFromTick(this);}
		mc.comet.utils.rerender();
	}
	
	@Override
	public void onTick() {
		if (isRunning() && brightness < 10)
			brightness += 0.7F;
		if (!isRunning() && brightness > 0)
			brightness -= 0.7F;
	}

}
