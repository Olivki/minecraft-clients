package Comet.Mods;

import Comet.*;
import net.minecraft.src.*;

public class m_Miner extends Base_Mod {
	
	public m_Miner() {
		super(Base_Enum.Miner);
	}

	@Override
	public void onStart() {
		enabled = true;
		Comet.ingame.array.add("Miner");
	}

	@Override
	public void onStop() {
		enabled = false;
		Comet.ingame.array.remove("Miner");
	}

}
