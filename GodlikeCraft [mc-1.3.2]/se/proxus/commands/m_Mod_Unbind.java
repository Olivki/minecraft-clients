package se.proxus.commands;

import org.lwjgl.input.Keyboard;

import se.proxus.hacks.*;

public class m_Mod_Unbind extends Base_Command {

	@Override
	public void runCommand(String[] var1, String var2) {
		try {
			for(Base_Hack var3 : hacks.hackArray) {
				if(var1[0].equalsIgnoreCase(var3.getName())) {
					var3.setKey(Keyboard.getKeyIndex("NONE"), true);
					this.files.saveKeys();
				}
			}
		} catch (Exception e) {
			addSyntax(getSyntax());
		}
	}
	
	@Override
	public String getName(boolean var1) {
		return var1 ? "unb" : "unbind";
	}

	@Override
	public String getSyntax() {
		return "unbind [name]";
	}

	@Override
	public String getUsage() {
		return "Used to unbind keys for different mods.";
	}
}