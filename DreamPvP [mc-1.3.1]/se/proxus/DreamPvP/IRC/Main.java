package se.proxus.DreamPvP.IRC;

import se.proxus.DreamPvP.Utils.*;

public class Main extends Utils {
	
	public static Bot bot;

	public static void init() throws Exception {
		bot = new Bot();
		bot.setVerbose(true);
		bot.connect("irc.va.us.mibbit.net");
		bot.joinChannel("#DreamPvPMc");
	}
}