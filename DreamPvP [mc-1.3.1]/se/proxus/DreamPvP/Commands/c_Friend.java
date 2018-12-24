package se.proxus.DreamPvP.Commands;

import java.io.File;

public class c_Friend extends Base_Cmd {

	public c_Friend() {
		super(".friend");
	}

	@Override
	public void onCommand(String[] s, String s1) {
		try {
			if(s[0].equalsIgnoreCase("add")) {
				if(!dp.settings.friendArray.contains(s[1])) {
					dp.settings.friendArray.add(s[1]);
					dp.utils.addChat("\"" + s[1] + "\" is now in your friend's list.");
					dp.files.saveBaseFile(new File(dp.files.baseFolder, "Friends.txt"), dp.settings.friendArray, true);
				} else {
					dp.utils.addChat("\"" + s[1] + "\" is already in your friend's list.");
				}
			} if(s[0].equalsIgnoreCase("del")) {
				if(dp.settings.friendArray.contains(s[1])) {
					dp.settings.friendArray.remove(s[1]);
					dp.utils.addChat("\"" + s[1] + "\" is no longer in your friend's list.");
					dp.files.saveBaseFile(new File(dp.files.baseFolder, "Friends.txt"), dp.settings.friendArray, true);
				} else {
					dp.utils.addChat("\"" + s[1] + "\" isn't in your friend's list.");
				}
			}
		} catch (Exception e) {
			dp.utils.addChat(getSyntax());
		}
	}

	@Override
	public String getSyntax() {
		return "Syntax : .friend [add/del] [username]";
	}

	@Override
	public String getUsage() {
		return "Used to do stuff with friends.";
	}
}