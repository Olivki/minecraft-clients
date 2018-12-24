package Comet.Mods;

import org.lwjgl.input.Keyboard;

import Comet.*;
import Comet.Utils.Hooks.Tickable;
import net.minecraft.src.*;

public class m_Fly extends Base_Mod implements Tickable {

	public m_Fly() {
		super(Base_Enum.Fly);
	}

	@Override
	public void onStart() {
		enabled = true;
		Comet.utils.addToTick(this);
		Comet.ingame.array.add("Fly");
	}

	@Override
	public void onStop() {
		enabled = false;
		Comet.utils.removeFromTick(this);
		Comet.ingame.array.remove("Fly");
	}

	@Override
	public void onTick() {
		mc.thePlayer.onGround = true;
		double posY = mc.thePlayer.posY;
		if(Keyboard.isKeyDown(mc.gameSettings.keyBindJump.keyCode) && mc.inGameHasFocus) {posY += 2.0D;}
	}

}
