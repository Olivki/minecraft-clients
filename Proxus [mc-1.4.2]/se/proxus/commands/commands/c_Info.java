package se.proxus.commands.commands;

import se.proxus.commands.Base_Command;
import se.proxus.hacks.*;

public class c_Info extends Base_Command {

	@Override
	public void runCommand(String[] var1, String var2) {
		try  {
			for(Base_Hack var3 : this.hacks.hackArray) {
				if(var1[0].equalsIgnoreCase(var3.getName())) {
					this.addChat("§" + var3.getCol() + var3.getName() + "§f, " + var3.getDescription());
				}
			}
		} catch (Exception e) {
			this.addSyntax(this.getSyntax());
		}
	}

	@Override
	public String getSyntax() {
		return "-info [modname]";
	}

	@Override
	public String getUsage() {
		return "Used to get the info of a mod.";
	}

	@Override
	public String getName(boolean var1) {
		return "-info";
	}
}