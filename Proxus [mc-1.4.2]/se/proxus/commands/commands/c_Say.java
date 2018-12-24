package se.proxus.commands.commands;

import se.proxus.commands.Base_Command;
import se.proxus.hacks.*;

public class c_Say extends Base_Command {

	@Override
	public void runCommand(String[] var1, String var2) {
		try  {
			this.utils.sendMessage(var2.substring(3));
		} catch (Exception e) {
			this.addSyntax(this.getSyntax());
		}
	}

	@Override
	public String getSyntax() {
		return "-say [Text]";
	}

	@Override
	public String getUsage() {
		return "Used to send chat messages to the server.";
	}

	@Override
	public String getName(boolean var1) {
		return "-say";
	}
}