package se.proxus.commands;

import java.util.ArrayList;
import java.util.HashMap;

import se.proxus.*;
import se.proxus.commands.commands.*;
import se.proxus.hacks.Base_Hack;

public final class Base_Commands extends Main {

	/** The variables. **/
	/** The hashmap for the commands. **/
	public HashMap cmdHash = new HashMap();

	/** The command array, used for prediction. **/
	public ArrayList<String> cmdArray = new ArrayList<String>();

	/** The object command array, used for prediction. **/
	public ArrayList<Base_Command> cmdObjectArray = new ArrayList<Base_Command>();
	
	public static HashMap<String, String> precmds = new HashMap<String, String>();

	/** The methods. **/
	/** The constructor. **/
	public Base_Commands() {
		setupCommands();
	}

	/** The method for setting up all the commands. **/
	private void setupCommands() {
		addCommand("info", new c_Info());
		addCommand("help", new c_Help());
		addCommand("bind", new c_Bind());
		addCommand("unbind", new c_Unbind());
		addCommand("say", new c_Say());
		addCommand("blocks", new c_xRay());
		
		for(Base_Hack var1 : this.hacks.hackArray) {
			addCommand(var1.getName(), new Base_Hack_Toggle(var1));
		}
	}

	/**
	 * The method for adding the commands.
	 * @param var1 - The String of the command.
	 * @param var2 - The Command class.
	 **/
	private void addCommand(String var1, Base_Command var2) {
		cmdHash.put(var1.toLowerCase(), var2);
		cmdArray.add(var1);
		cmdObjectArray.add(cmdObjectArray.size(), var2);
		precmds.put(var1.toLowerCase(), var2.getUsage());

		try {
			/*pred.cmdArrayUsage.add(var2.getUsage());
			pred.cmdArraySyntax.add(var2.getSyntax());*/
		} catch (Exception e) { 
			/*pred.cmdArrayUsage.add("N/A");
			pred.cmdArraySyntax.add("N/A");*/
		}
		
		this.utils.log("[Commands] Added the command \"" + var1 + "\" to the command array.");
	}

	/**
	 * The method for running the commands.
	 * @param var1 - The String that has been entered.
	 **/
	public void runCommands(String var1) {
		try {
			var1 = var1.substring(0).trim();
			String[] var2;
			String var3 = "";
			int var4 = var1.indexOf(' ');

			if(var4 < 0) {
				var3 = var1;
				var2 = new String[0];
			} else {
				var3 = var1.substring(0, var4).toLowerCase();
				var2 = var1.substring(var4).trim().split(" ");
			}

			if(cmdHash.containsKey(var3)) {
				Base_Command var5 = (Base_Command)cmdHash.get(var3);

				if(var5 == null) {
					this.utils.addMessage("Do -help for help on commands.");
				} else {
					var5.runCommand(var2, var1);
				}
			} else {
				this.utils.addMessage("Do -help for help on commands.");
			}
		} catch(Exception e) { 
			this.utils.addMessage("Command error.");
			utils.log("Error : " + e.getMessage());
		}		
	}

	/**
	 * The method for getting the predection.
	 * @param var1 - The input String.
	 * @param var2 - The integer for the String array.
	 */
	public String getPrediction(String var1) {
		String var3 = "";

		for(int var4 = 0; var4 < cmdObjectArray.size(); var4++) {
			Base_Command var5 = (Base_Command)cmdObjectArray.get(var4);

			if(var1.length() > 1 && this.sUtil.startsWithIgnoreCase(var1, var5.getName(true)) && !(var1.contains(" "))) {
				var3 = var5.getName(false) + " - " + var5.getUsage();
			}
		}

		return var3;
	}
	
	public Base_Command getCommand(String var1) {
		Base_Command var2 = null;
		
		for(Base_Command var3 : this.cmdObjectArray) {
			if(var3.getName(false).equalsIgnoreCase(var1)) {
				var2 = null;
			}
		}
		
		return var2;
	}
}