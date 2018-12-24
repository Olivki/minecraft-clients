package se.proxus.commands.commands;

import se.proxus.commands.Base_Command;
import se.proxus.hacks.*;

public class c_Bind extends Base_Command {

	@Override
	public void runCommand(String[] var1, String var2) {
		try  {
			for(Base_Hack var3 : this.hacks.hackArray) {
				if(var1[1].equalsIgnoreCase(var3.getName())) {
					var3.setKey(var1[0], true);
				}
			}
		} catch (Exception e) {
			this.addSyntax(this.getSyntax());
		}
	}

	@Override
	public String getSyntax() {
		return "-bind [key] [name]";
	}

	@Override
	public String getUsage() {
		return "Used to bind a hack to a certain key.";
	}

	@Override
	public String getName(boolean var1) {
		return "-bind";
	}
}