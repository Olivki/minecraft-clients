package Comet.Commands;

import java.util.ArrayList;

import Comet.Mods.*;
import Comet.Utils.Color;

public class c_Friend extends Base_Command {

	public void run(String as[]) throws Exception {
		try {
			ArrayList friends = mc.comet.settings.friends;
			String S1 = as[0];
			String S2 = as[1];

			if(S1.equalsIgnoreCase("add")) {
				if(!friends.contains(S2)) {
					mc.comet.utils.addFriend(S2);
					mc.comet.utils.addChat("Successfully added '" + Color.GREEN + S2  + Color.WHITE + "' to your friend list!");
					mc.comet.settings.saveFriends();
				} else {
					mc.comet.utils.addChat("'" + Color.GREEN + S2  + Color.WHITE + "' is already in your friend list!");
				}
			}

			if(S1.equalsIgnoreCase("del")) {
				if(friends.contains(S2)) {
					mc.comet.utils.removeFriend(S2);
					mc.comet.utils.addChat("Successfully deleted '" + Color.GREEN + S2  + Color.WHITE + "' from your friend list!");
					mc.comet.settings.saveFriends();
				} else {
					mc.comet.utils.addChat("'" + Color.GREEN + S2  + Color.WHITE + "' is not in your friend list!");
				}
			}
		} catch (Exception e) {
			mc.comet.utils.addChat("Syntax : -friend [add / del] [username]");
			e.printStackTrace();
		}
	}

	public String getHelp() throws Exception {return "Adds / removes people to your friend list.";}

	public String getUsage() throws Exception {return ", can be used to add / remove people from your friend list.";}

}
