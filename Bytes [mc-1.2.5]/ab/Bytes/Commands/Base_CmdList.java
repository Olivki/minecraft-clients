package ab.Bytes.Commands;

import java.util.ArrayList;
import java.util.HashMap;

import ab.Bytes.*;

public final class Base_CmdList {

	public static HashMap cmdHash = new HashMap();
	protected static Bytes bs = new Bytes();
	public static char pre = '.';
	protected static int I1;
	protected static String args[], cmd;

	public Base_CmdList() {
		checkAllCmds();
	}

	public static void checkAllCmds() {
		cmdHash.clear();
		cmdHash.put("xray", new c_xRay());
		cmdHash.put("help", new c_Help());
		cmdHash.put("info", new c_Info());
		cmdHash.put("waypoint", new c_Waypoint());
	}

	public static boolean checkCmdArgs(String s) {
		if(s.charAt(0) == pre) {
			try {
				s = s.substring(1).trim();
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
						bs.utils.addChat("Unknown command.");
					} else {
						bCmd.command(args);
					}
				} else {
					bs.utils.addChat("Unknown command.");
				}
			} catch (Exception e ) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}

}
