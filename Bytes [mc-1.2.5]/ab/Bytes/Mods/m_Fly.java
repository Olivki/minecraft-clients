package ab.Bytes.Mods;

import net.minecraft.src.*;

import org.lwjgl.input.Keyboard;

import ab.Bytes.Interfaces.*;

public class m_Fly extends Base_Mod {
	
	public float flySpeed = 2.0F;

	public m_Fly() {
		super('6', "Fly", Keyboard.KEY_F);
	}

	@Override
	public void onEnabled() {
		EntityPlayerSP tp = bs.mc.thePlayer;
		
		tp.capabilities.isFlying = true;
		tp.onGround = true;
	}

	@Override
	public void onDisabled() {
		EntityPlayerSP tp = bs.mc.thePlayer;
		
		tp.capabilities.isFlying = false;
		tp.onGround = tp.onGround;
	}
}