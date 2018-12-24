package bt.Olivki.Biti.Utils;

import org.lwjgl.input.Keyboard;

import bt.Olivki.Biti.*;
import bt.Olivki.Biti.Mods.*;

public class Keys extends Utils {
	
	public static m_Sneak sneak = new m_Sneak(); //Creates a new instance for the class m_Sneak, so we can toggle it.

	public static void checkKeys(int K) { //The method to see if a key got toggled. //I also changed the checkKey method, thanks to TeamRamis / ram.
		switch(K){
		case Keyboard.KEY_Z : {sneak.toggle();} //Toggles the Sneak hack / mod.
		break;
		case Keyboard.KEY_Y : {Biti.vars.Test = !Biti.vars.Test;} //Toggles the Sneak hack / mod.
		break;
		}}

}
