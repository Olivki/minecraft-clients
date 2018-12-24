package se.proxus.commands;

import se.proxus.hacks.*;
import se.proxus.panels.Base_Panels;

public class m_Mod_Toggle extends Base_Command {

	@Override
	public void runCommand(String[] var1, String var2) {
		try {
			for(Base_Hack var3 : hacks.hackArray) {
				if(var1[0].equalsIgnoreCase(var3.getName())) {
					var3.toggle();
					addToggle(var3.getName().replace("_", " "), var3.getState());
				}
			}
		} catch (Exception e) {
			addSyntax(getSyntax());
		}
	}
	
	@Override
	public String getName(boolean var1) {
		return var1 ? "to" : "toggle";
	}

	@Override
	public String getSyntax() {
		return "toggle [name]";
	}

	@Override
	public String getUsage() {
		return "Used to toggle mods using commands.";
	}
}