package se.proxus.DreamPvP.Mods;

import static org.lwjgl.input.Keyboard.*;
import net.minecraft.src.*;

public class m_Info extends Base_Mod {

	public m_Info() {
		super('7', "Info", "Makes it so you can see info about other players.", KEY_NONE, "Player", "[§eP§r] ");
	}

	@Override
	public void onEnabled() {
		
	}

	@Override
	public void onDisabled() {
		
	}
}