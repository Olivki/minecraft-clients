package Comet.Commands;

import java.util.ArrayList;

import Comet.Mods.*;
import Comet.Utils.Waypoints;

public class c_Waypoint extends Base_Command {

	public void run(String as[]) throws Exception {
		try {
			ArrayList waypoint = mc.comet.settings.waypoints;
			double P1 = mc.thePlayer.posX;
			double P2 = mc.thePlayer.posY + 2.8;
			double P3 = mc.thePlayer.posZ - 0.5;
			String S1 = as[0];
			String S2 = as[1];

			if(S1.equalsIgnoreCase("add")) {
				mc.comet.utils.addChat("Added waypoint : " + S2 + ", at : X:" + (int)P1 + ", Y:" + (int)P2 + ", Z:" + (int)P3 + ".");
				mc.comet.settings.waypoints.add(new Waypoints(S2, P1, P2, P3));
			}

			if(S1.equalsIgnoreCase("del")) {
				mc.comet.utils.addChat("Deleted waypoint : " + S2 + ".");
				for(int l = 0; l < mc.comet.settings.waypoints.size(); l++) {
					Waypoints point = (Waypoints)mc.comet.settings.waypoints.get(l);
					String pointName = point.name;
					if(pointName.equals(S2)) {mc.comet.settings.waypoints.remove(point);}
				}
			}
		}catch (Exception e) {
			mc.comet.utils.addChat("Syntax : -waypoint [add / del / enable] [Name]");}
	}

	public String getHelp() throws Exception {return "Adds / deletes / enables a waypoint.";}

	public String getUsage() throws Exception {return ", can be used to remember certain locations.";}

}
