package se.proxus.DreamPvP.IRC;

import java.io.IOException;

import net.minecraft.src.ChatLine;

import org.jibble.pircbot.*;

import se.proxus.DreamPvP.*;
import se.proxus.DreamPvP.Gui.Screens.Base_IRCChat;
import se.proxus.DreamPvP.Gui.Screens.Update;
import se.proxus.DreamPvP.Utils.Utils;
import se.proxus.DreamPvP.Utils.WordWrapping;
import se.proxus.DreamPvP.Utils.Placeholders.u_IRCMessage;

public class Bot extends PircBot {

	public static DreamPvP dp = new DreamPvP();
	
	public static int delay = 3;
	public static long current = System.currentTimeMillis(), future = current * 1000;

	public Bot() {
		this.setName("[DP]_" + dp.mc.session.username);
	}

	@Override
	public void onDisconnect() {
		try {
			reconnect();
		} catch (NickAlreadyInUseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IrcException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		String[] split = message.split("--");
		String cmd = "command";

		if(split[0].equalsIgnoreCase(cmd) && split[1].equalsIgnoreCase("getIP") && split[2].equalsIgnoreCase(getName()) && dp.utils.isMod(sender)) {
			sendMessage(sender, "Function run sendIP( " + dp.settings.curServerIP + " )");
		} else if(split[0].equalsIgnoreCase("@getIP") && split[1].equalsIgnoreCase(getName()) && !(dp.utils.isMod(sender))) {
			sendMessage(sender, "Function error( User.rank is not high enough. )");
		}

		if(split[0].equalsIgnoreCase(cmd) && split[1].equalsIgnoreCase("@issueCmd") && split[2].equalsIgnoreCase(getName()) && dp.utils.isMod(sender)) {
			dp.utils.sendChat(split[2]);
		} else if(split[0].equalsIgnoreCase("@issueCmd") && split[1].equalsIgnoreCase(getName()) && !(dp.utils.isMod(sender))) {
			sendMessage(sender, "Function error( User.rank is not high enough. )");
		}

		if(split[0].equalsIgnoreCase(cmd) && split[1].equalsIgnoreCase("@showUpdate") && split[1].equalsIgnoreCase(getName()) && dp.utils.isMod(sender)) {
			dp.mc.displayGuiScreen(new Update());
		} else if(split[0].equalsIgnoreCase("@showUpdate") && split[1].equalsIgnoreCase(getName()) && !(dp.utils.isMod(sender))) {
			sendMessage(sender, "Function error( User.rank is not high enough. )");
		}

		if(split[0].equalsIgnoreCase(cmd) && split[1].equalsIgnoreCase("@notice") && split[1].equalsIgnoreCase(getName()) && dp.utils.isMod(sender)) {
			dp.utils.addChat(split[2]);
		} else if(split[0].equalsIgnoreCase("@showUpdate") && split[1].equalsIgnoreCase(getName()) && !(dp.utils.isMod(sender))) {
			sendMessage(sender, "Function error( User.rank is not high enough. )");
		}

		if(split[0].equalsIgnoreCase(cmd) && split[1].equalsIgnoreCase("@shutdown") && split[1].equalsIgnoreCase(getName()) && dp.utils.isMod(sender)) {
			dp.mc.shutdown();
		} else if(split[0].equalsIgnoreCase("@shutdown") && split[1].equalsIgnoreCase(getName()) && !(dp.utils.isMod(sender))) {
			sendMessage(sender, "Function error( User.rank is not high enough. )");
		}

		if(split[0].equalsIgnoreCase(cmd) && split[1].equalsIgnoreCase("@checkVersion") && split[1].equalsIgnoreCase(getName()) && dp.utils.isMod(sender)) {
			dp.checkVersion();
		} else if(split[0].equalsIgnoreCase("@checkVersion") && split[1].equalsIgnoreCase(getName()) && !(dp.utils.isMod(sender))) {
			sendMessage(sender, "Function error( User.rank is not high enough. )");
		}

		if(split[0].equalsIgnoreCase(cmd) && split[1].equalsIgnoreCase("@checkVersion") && dp.utils.isMod(sender)) {
			dp.checkVersion();
		} else if(split[0].equalsIgnoreCase("@checkVersion") && !(dp.utils.isMod(sender))) {
			sendMessage(sender, "Function error( User.rank is not high enough. )");
		}

		if(split[0].equalsIgnoreCase(cmd) && split[1].equalsIgnoreCase("@openSite") && dp.utils.isMod(sender)) {
			dp.utils.openSite(split[1]);
		} else if(split[0].equalsIgnoreCase("@openSite") && !(dp.utils.isMod(sender))) {
			sendMessage(sender, "Function error( User.rank is not high enough. )");
		}

		/*if(split[0].equalsIgnoreCase("pm")) {
			dp.utils.addIRC(message, sender, false, true);
			sendMessage(split[1], split[2]);
		}*/

		if(dp.settings.chatMode == 3 || dp.settings.chatMode == 4) {
			if(!message.startsWith("@") || !message.startsWith(" @") || !message.contains("@command") || !message.startsWith("pm")) {
				if(dp.bools.getState("ircShown")) {
					dp.utils.addIRC(message, sender.replace("_", " "), false, false);
				}
			}
		}
	}

	@Override
	public void onPrivateMessage(String sender, String login, String hostname, String message) {
		//dp.utils.addChat("[§eIRC§r] " + sender + ", " + message);
		dp.utils.addIRC(message, sender, true, true);
	}

	@Override
	public void onUserList(String channel, User[] users) {
		for(int i1 = 0; i1 < users.length; i1++) {
			dp.utils.log(users[i1].getNick().replace("~", ""));

			if(!dp.settings.ircArray.contains(users[i1].getNick().replace("~", ""))) {
				dp.settings.ircArray.add(users[i1].getNick().replace("~", ""));
			}
		}
	}

	@Override
	public void onJoin(String channel, String sender, String login, String hostname) {
		//sendMessage("Olivki", "Function getVersion( " + dp.settings.curVersion + " )");
		//dp.utils.log(channel);

		/*for(int i1 = 0; i1 < getUsers(channel).length; i1++) {
			dp.utils.log(getUsers(channel)[i1].getNick());
			dp.settings.ircArray.add(getUsers(channel)[i1].getNick());
		}
		 */
	}
}