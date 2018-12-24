package se.proxus.commands;

import org.lwjgl.input.Keyboard;

import se.proxus.hacks.*;

public class m_Mod_Bind extends Base_Command {

	@Override
	public void runCommand(String[] var1, String var2) {
		try {
			for(Base_Hack var3 : hacks.hackArray) {
				if(var1[0].equalsIgnoreCase(var3.getName())) {
					var3.setKey(Keyboard.getKeyIndex(var1[1].toUpperCase()), true);
					this.files.saveKeys();
				}
			}
		} catch (Exception e) {
			addSyntax(getSyntax());
		}
	}
	
	@Override
	public String getName(boolean var1) {
		return var1 ? "bi" : "bind";
	}

	@Override
	public String getSyntax() {
		return "bind [name] [key]";
	}

	@Override
	public String getUsage() {
		return "Used to bind keys for different hacks.";
	}
}