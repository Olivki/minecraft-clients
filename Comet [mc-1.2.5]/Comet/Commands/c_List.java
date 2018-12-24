package Comet.Commands;

import java.util.ArrayList;

import Comet.Mods.*;
import Comet.Utils.Color;

public class c_List extends Base_Command {

	public void run(String as[]) throws Exception {
		try {
			ArrayList friends = mc.comet.settings.friends;
			ArrayList enemys = mc.comet.settings.enemys;
			ArrayList waypoints = mc.comet.settings.waypoints;
			String S1 = as[0];

			if(S1.equalsIgnoreCase("friends")) {
				if(friends.isEmpty()) {mc.comet.utils.addChat("You currently have (0) friends."); mc.comet.utils.addChat("Forever alone..");}
				for(int fList = 0; fList < friends.size(); fList++) {
					String S3 = fList + ". " + (String) friends.get(fList);
					mc.comet.utils.addChat(S3);
				}
			}

			if(S1.equalsIgnoreCase("enemys")) {
				if(enemys.isEmpty()) {mc.comet.utils.addChat("You currently have (0) enemys."); mc.comet.utils.addChat("To nice to have any enemys? Pfftt..");}
				for(int eList = 0; eList < enemys.size(); eList++) {
					String S3 = eList + ". " + (String) enemys.get(eList);
					mc.comet.utils.addChat(S3);
				}
			}


			if(S1.equalsIgnoreCase("waypoints")) {
				if(waypoints.isEmpty()) {mc.comet.utils.addChat("You currently have (0) waypoints.");}
				for(int wList = 0; wList < waypoints.size(); wList++) {
					String S3 = wList + ". " + (String) waypoints.get(wList);
					mc.comet.utils.addChat(S3);
				}
			}
		} catch (Exception e) {
			mc.comet.utils.addChat("Syntax : -list [friends / enemys / waypoints]");
			e.printStackTrace();
		}
	}

	public String getHelp() throws Exception {return "Shows all your current friends / enemys / waypoints.";}

	public String getUsage() throws Exception {return ", can be used to see all your friends / enemys / waypoints.";}

}
