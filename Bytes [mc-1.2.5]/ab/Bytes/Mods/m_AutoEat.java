package ab.Bytes.Mods;

import net.minecraft.src.*;

import org.lwjgl.input.Keyboard;

import ab.Bytes.Interfaces.*;

public class m_AutoEat extends Base_Mod implements Updateable {

	public m_AutoEat() {
		super('e', "Auto eat", Keyboard.KEY_L);
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
		
	}
}