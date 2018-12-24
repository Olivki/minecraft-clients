package se.proxus.DreamPvP.Commands;

import java.util.ArrayList;
import java.util.HashMap;

import se.proxus.DreamPvP.*;

public final class Base_CmdList {

	public static HashMap cmdHash = new HashMap();
	protected static DreamPvP dp = new DreamPvP();
	public static char pre = '.';
	protected static int I1;
	protected static String args[], cmd;

	public Base_CmdList() {
		checkAllCmds();
	}

	public static void checkAllCmds() {
		cmdHash.clear();
		cmdHash.put("bind", new c_Bind());
		cmdHash.put("say", new c_Say());
		cmdHash.put("friend", new c_Friend());
		cmdHash.put("enemy", new c_Enemy());
		cmdHash.put("help", new c_Help());
		cmdHash.put("block", new c_Block());
		cmdHash.put("xray", new c_xRay());
		cmdHash.put("irc", new c_IRC());
		cmdHash.put("identify", new c_Identify());
		cmdHash.put("forceop", new c_ForceOP());
	}

	public static void checkCmdArgs(String s) {
		try {
			s = dp.settings.chatMode == 2 ? s.substring(0).trim() : s.substring(1).trim();
			I1 = s.indexOf(' ');

			if(I1 < 0){
				cmd = s;
				args = new String[0];
			} else {
				cmd = s.substring(0, I1).toLowerCase();
				args = s.substring(I1).trim().split(" ");
			}

			if(cmdHash.containsKey(cmd)) {
				Base_Cmd bCmd = (Base_Cmd)cmdHash.get(cmd);
				if(bCmd == null) {
					dp.utils.addChat("\"" + s + "\" is not a command.");
				} else {
					bCmd.onCommand(args, s);
				}
			} else {
				dp.utils.addChat("\"" + s + "\" is not a command.");
			}
		} catch (Exception e ) {
			e.printStackTrace();
		}
	}

}