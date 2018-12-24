package bt.Olivki.Biti.Mods;

import net.minecraft.src.*;
import bt.Olivki.Biti.*;
import bt.Olivki.Biti.Utils.*;

public class m_Sneak extends Base_Mod { //Extends Base_Mod.java so we get everything inside it.

	public m_Sneak(){
		super(Base_Enum.Sneak); //Refers our Base_Enum.
	}

	@Override
	public void onStart() { //When our Sneak mod / hack get's started, it will do this. //Also, Eclipse will help you with adding this.
		Biti.vars.Sneak = true; //Once this method get's started, it will set the boolean Sneak to true.
		Utils.sendPacket(new Packet19EntityAction(Biti.mc.thePlayer, 1)); //Send the crouching / sneak packet, which will make your character sneak / crouch.
	}

	@Override
	public void onStop() { //When our Sneak mod / hack get's stopped, it will do this. //Also, Eclipse will help you with adding this.
		Biti.vars.Sneak = false; //Once this method get's started, it will set the boolean Sneak to false.
		Utils.sendPacket(new Packet19EntityAction(Biti.mc.thePlayer, 2)); //Sends the packet that stops you from Sneaking.
	}

}
