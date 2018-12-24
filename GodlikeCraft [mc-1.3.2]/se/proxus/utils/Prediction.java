package se.proxus.utils;

import java.util.ArrayList;

import se.proxus.*;

public class Prediction extends GodlikeCraft {

	/** The command usage array, used to get the usage of the command, for the prediction. **/
	public static ArrayList cmdArrayUsage = new ArrayList();

	/** The command syntax array, used to get the syntax of the command, for the prediction. **/
	public static ArrayList cmdArraySyntax = new ArrayList();
	
	public static ArrayList 
	predArray = new ArrayList(),
	predUsage = new ArrayList(),
	predSyntax = new ArrayList();

	public static void think(String s) {
		predArray = predictionList(s);
		predSyntax = predictionHelp(s);
		predUsage = predictionUsage(s);
	}

	public static String getBestCommand() {
		return predArray.get(0).toString();
	}

	public static String getBestCommandString() {
		return (predArray.get(0).toString() + (predSyntax.get(0).toString().length() > 0 ? " " + predSyntax.get(0).toString() : "")  + " - " + predUsage.get(0).toString());
	}

	public static boolean isFullCommand(String message) {
		return cmds.cmdArray.contains(message.trim().split(" ")[0]);
	}

	private static ArrayList predictionList(String msg) {
		ArrayList t = new ArrayList();
		for(int i = 0; i < cmds.cmdArray.size(); i++) {
			msg = msg.trim();
			if(msg == "" || msg == null || msg == " " || msg.length() < 1) continue;
			if(cmds.cmdArray.get(i).toString().startsWith(msg)) {
				t.add(cmds.cmdArray.get(i));
			}
		}
		return t;
	}

	private static ArrayList predictionHelp(String msg) {
		ArrayList t = new ArrayList();
		for(int i = 0; i < cmds.cmdArray.size(); i++) {
			msg = msg.trim();
			if(msg == "" || msg == null || msg == " " || msg.length() < 1) continue;
			if(cmds.cmdArray.get(i).toString().startsWith(msg)) {
				t.add(cmdArrayUsage.get(i));
			}
		}
		return t;
	}

	private static ArrayList predictionUsage(String msg) {
		ArrayList t = new ArrayList();
		for(int i = 0; i < cmds.cmdArray.size(); i++) {
			msg = msg.trim();
			if(msg == "" || msg == null || msg == " " || msg.length() < 1) continue;
			if(cmds.cmdArray.get(i).toString().startsWith(msg)) {
				t.add(cmdArraySyntax.get(i));
			}
		}
		return t;
	}
}