package Comet.Mods;

import Comet.*;
import Comet.Utils.Hooks.Tickable;
import net.minecraft.src.*;

public class m_NoFall extends Base_Mod implements Tickable {

	public m_NoFall() {
		super(Base_Enum.Sneak);
	}

	@Override
	public void onStart() {
		Comet.utils.addToTick(this);
		enabled = true;
		Comet.ingame.array.add("NoFall");
	}

	@Override
	public void onStop() {
		Comet.utils.removeFromTick(this);
		enabled = false;
		Comet.ingame.array.remove("NoFall");
	}

	@Override
	public void onTick() {
		
	}

}
