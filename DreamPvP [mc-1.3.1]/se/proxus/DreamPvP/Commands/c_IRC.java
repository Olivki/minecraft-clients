package se.proxus.DreamPvP.Commands;

import se.proxus.DreamPvP.Utils.Placeholders.u_Block;

public class c_IRC extends Base_Cmd {

	public c_IRC() {
		super(".irc");
	}

	@Override
	public void onCommand(String[] s, String s1) {
		try {
			dp.bools.toggle("ircShown");
			dp.utils.addChat("IRC Shown : " + dp.bools.getState("ircShown"));
		} catch (Exception e) {
			dp.utils.addChat(getSyntax());
		}
	}

	@Override
	public String getSyntax() {
		return "Syntax : .irc";
	}

	@Override
	public String getUsage() {
		return "Used to do stuff with irc.";
	}
}