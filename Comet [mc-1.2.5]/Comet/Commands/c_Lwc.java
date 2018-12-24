package Comet.Commands;

import java.util.ArrayList;

import Comet.Mods.*;
import Comet.Utils.Color;
import Comet.Utils.Settings;
import Comet.Utils.Utils;

public class c_Lwc extends Base_Command {

	public void run(String as[]) throws Exception {
		try {
			ArrayList lwclist = mc.comet.settings.lwcList;
			String S1 = as[0];
			String S2 = as[1];

			if(S1.equalsIgnoreCase("add")) {
				if(!lwclist.contains(S2)) {
					Utils.addLwcName(S2);
					Utils.addChat("Successfully added '" + Color.AQUA + S2  + Color.WHITE + "' to your LWC crack list!");
					Settings.saveLwcList();
				} else {
					Utils.addChat("Your LWC crack list already contains the name '" + Color.AQUA + S2  + Color.WHITE + "'!");
				}
			}

			if(S1.equalsIgnoreCase("del")) {
				if(lwclist.contains(S2)) {
					Utils.removeLwcName(S2);
					Utils.addChat("Successfully removed '" + Color.AQUA + S2  + Color.WHITE + "' from your LWC crack list!");
					Settings.saveLwcList();
				} else {
					Utils.addChat("Your LWC crack doesn't the name '" + Color.AQUA + S2  + Color.WHITE + "'!");
				}
			}

			if(S1.equalsIgnoreCase("crack") && S2.equalsIgnoreCase("on")) {
				if(!lwclist.isEmpty()) {
					for(int crack = 0; crack < lwclist.size(); crack++) {
						Utils.sendChat("/cunlock " + lwclist.get(crack));
					}
				} else {
					Utils.addChat("Your LWC crack list doesn't contain any names!");
				}
			}

		} catch (Exception e) {
			mc.comet.utils.addChat("Syntax : -lwc [crack / add / del] [on / name]");
			e.printStackTrace();
		}
	}

	public String getHelp() throws Exception {return "Adds / removes / cracks LWC protected chests, also adds possible password to the cracker..";}

	public String getUsage() throws Exception {return ", can be used to get into password protected LWC chests..";}

}
