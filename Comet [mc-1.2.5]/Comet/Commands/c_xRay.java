package Comet.Commands;

import java.util.ArrayList;

import Comet.Mods.*;
import Comet.Utils.Color;
import Comet.Utils.Utils;

public class c_xRay extends Base_Command {

	public void run(String as[]) throws Exception {
		try {
			ArrayList blocks = mc.comet.settings.blockList;
			String S1 = as[0];
			int S2 = Integer.parseInt(as[1]);

			if(S1.equalsIgnoreCase("add")) {
				if(!blocks.contains(S2)) {
					blocks.add(S2);
					if(mc.comet.keys.xray.isRunning()) {mc.comet.utils.rerender();}
					mc.comet.utils.addChat("Successfully added '" + Color.GOLD + S2  + Color.WHITE + "' to your xRay list!");
					mc.comet.settings.savexRay();
				} else {
					mc.comet.utils.addChat("'" + Color.GREEN + S2  + Color.WHITE + "' is already in your xRay list!");
				}
			}

			if(S1.equalsIgnoreCase("del")) {
				if(blocks.contains(S2)) {
					blocks.remove(S2);
					if(mc.comet.keys.xray.isRunning()) {mc.comet.utils.rerender();}
					mc.comet.utils.addChat("Successfully deleted '" + Color.GREEN + S2  + Color.WHITE + "' from your xRay list!");
					mc.comet.settings.savexRay();
				} else {
					mc.comet.utils.addChat("'" + Color.GREEN + S2  + Color.WHITE + "' is not in your xRay list!");
				}
			}
		} catch (Exception e) {
			mc.comet.utils.addChat("Syntax : -xray [add / del] [id]");
			e.printStackTrace();
		}
	}

	public String getHelp() throws Exception {return "Adds / removes people to your friend list.";}

	public String getUsage() throws Exception {return ", can be used to add / remove people from your friend list.";}

}
