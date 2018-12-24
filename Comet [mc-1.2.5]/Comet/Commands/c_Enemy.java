package Comet.Commands;

import java.util.ArrayList;

import Comet.Mods.*;
import Comet.Utils.Color;

public class c_Enemy extends Base_Command {

	public void run(String as[]) throws Exception {
		try {
			ArrayList enemys = mc.comet.settings.enemys;
			String S1 = as[0];
			String S2 = as[1];

			if(S1.equalsIgnoreCase("add")) {
				if(!enemys.contains(S2)) {
					mc.comet.utils.addEnemy(S2);
					mc.comet.utils.addChat("Successfully added '" + Color.RED + S2  + Color.WHITE + "' to your enemy list!");
					mc.comet.settings.saveEnemys();
				} else {
					mc.comet.utils.addChat("'" + Color.RED + S2  + Color.WHITE + "' is already in your enemy list!");
				}
			}

			if(S1.equalsIgnoreCase("del")) {
				if(enemys.contains(S2)) {
					mc.comet.utils.removeFriend(S2);
					mc.comet.utils.addChat("Successfully deleted '" + Color.RED + S2  + Color.WHITE + "' from your enemy list!");
					mc.comet.settings.saveEnemys();
				} else {
					mc.comet.utils.addChat("'" + Color.RED + S2  + Color.WHITE + "' is not in your enemy list!");
				}
			}
		} catch (Exception e) {
			mc.comet.utils.addChat("Syntax : -enemy [add / del] [username]");
			e.printStackTrace();
		}
	}

	public String getHelp() throws Exception {return "Adds / removes people to your enemy list.";}

	public String getUsage() throws Exception {return ", can be used to add / remove people to your enemy list.";}

}
