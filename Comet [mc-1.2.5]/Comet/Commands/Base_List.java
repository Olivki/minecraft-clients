package Comet.Commands;

import java.util.ArrayList;
import java.util.HashMap;

public final class Base_List {

	public static HashMap commands = new HashMap();
	public static ArrayList cmds = new ArrayList();

	public static void init() {
		commands.clear();
		commands.put("tpclick", new c_ClickTp());
		commands.put("tppos", new c_Teleport());
		commands.put("tpdeath", new c_DeathTp());
		commands.put("tpplayer", new c_PlayerTp());
		commands.put("tpcrash", new c_CrashTp());
		commands.put("sneak", new c_Sneak());
		commands.put("help", new c_Help());
		commands.put("waypoint", new c_Waypoint());
		commands.put("friend", new c_Friend());
		commands.put("enemy", new c_Enemy());
		commands.put("list", new c_List());
		commands.put("lwc", new c_Lwc());
		commands.put("spam", new c_Spam());
		commands.put("xray", new c_xRay());
		commands.put("textradar", new c_TextRadar());
		commands.put("nuker", new c_Nuker());
		commands.put("autotool", new c_AutoTool());
		
		cmds.add("tpclick");
		cmds.add("tppos");
		cmds.add("tpdeath");
		cmds.add("tpplayer");
		cmds.add("tpcrash");
		cmds.add("sneak");
		cmds.add("help");
		cmds.add("waypoint");
		cmds.add("friend");
		cmds.add("enemy");
		cmds.add("list");
		cmds.add("lwc");
		cmds.add("spam");
		cmds.add("xray");
		cmds.add("textradar");
		cmds.add("nuker");
		cmds.add("autotool");
	}
	
	public Base_List() {
		init();
	}

	public static boolean onCommand(String s) {
		if(s.charAt(0) == '-'){
			try{
				s = s.substring(1).trim();
				int i = s.indexOf(' ');
				String s1;
				String as[];
				if(i < 0){
					s1 = s;
					as = new String[0];
				} else {
					s1 = s.substring(0, i).toLowerCase();
					as = s.substring(i).trim().split(" ");}
				if(commands.containsKey(s1)) {
					Base_Command basecommand = (Base_Command)commands.get(s1);
					if(basecommand == null){
						Comet.Comet.utils.addChat("Unknown command! Did you mean -help?");
					} else {
						basecommand.run(as);
					}
				} else {
					Comet.Comet.utils.addChat("Unknown command! Did you mean -help?");
				}}
			catch(Exception exception){
				System.out.print((new StringBuilder()).append("Command error: ").append(exception.getMessage()).toString());
				exception.printStackTrace();}
			return false;
		} else {
			return true;}
	}

}
