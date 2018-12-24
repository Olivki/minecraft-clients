package se.proxus.DreamPvP.Commands;

import org.lwjgl.input.*;

import se.proxus.DreamPvP.Mods.*;
import se.proxus.DreamPvP.Utils.Utils;

public class c_Bind extends Base_Cmd {

	public c_Bind() {
		super(".bind");
	}

	@Override
	public void onCommand(String[] s, String s1) {
		try {
			int key = Keyboard.getKeyIndex(s[1].toUpperCase());
			for(Base_Mod bMod : dp.bModList.modArray) {
				if(s[0].equalsIgnoreCase(bMod.getName())) {
					dp.utils.addChat("The keybind for the mod \"" + bMod.getName() + "\" has changed from \"" + Keyboard.getKeyName(bMod.getKey()) + "\" to \"" + Keyboard.getKeyName(key) + "\".");
					bMod.setKey(key);
				}
			}
		} catch (Exception e) {
			dp.utils.addChat(getSyntax());
		}
	}

	@Override
	public String getSyntax() {
		return "Syntax : .bind [modname] [key]";
	}

	@Override
	public String getUsage() {
		return "Used to bind keys to mods.";
	}
}