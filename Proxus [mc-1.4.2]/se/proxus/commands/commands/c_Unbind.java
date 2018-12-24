package se.proxus.commands.commands;

import se.proxus.commands.Base_Command;
import se.proxus.hacks.*;

public class c_Unbind extends Base_Command {

	@Override
	public void runCommand(String[] var1, String var2) {
		try  {
			for(Base_Hack var3 : this.hacks.hackArray) {
				if(var1[0].equalsIgnoreCase(var3.getName())) {
					var3.setKey("NONE", true);
				}
			}
		} catch (Exception e) {
			this.addSyntax(this.getSyntax());
		}
	}

	@Override
	public String getSyntax() {
		return "-unbind [name]";
	}

	@Override
	public String getUsage() {
		return "Used to unbind a hack to a certain key.";
	}

	@Override
	public String getName(boolean var1) {
		return "-unbind";
	}
}