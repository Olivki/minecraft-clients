package ab.Bytes.Mods;

import org.lwjgl.input.Keyboard;

public class m_Miner extends Base_Mod {
	
	public float mineSpeed = 0.35F;
	
	public m_Miner() {
		super('c', "Miner", Keyboard.KEY_R);
	}

	@Override
	public void onEnabled() {
		
	}

	@Override
	public void onDisabled() {
		
	}
}