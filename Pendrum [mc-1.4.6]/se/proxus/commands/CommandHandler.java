package se.proxus.commands;

import java.util.ArrayList;
import java.util.HashMap;

import se.proxus.*;
import se.proxus.commands.list.*;
import se.proxus.mods.*;
import se.proxus.panels.BasePanel;

public class CommandHandler extends Pendrum {

	public HashMap<String, BaseCommand> cmdHash = new HashMap<String, BaseCommand>();

	public ArrayList<BaseCommand> cmdArray = new ArrayList<BaseCommand>();
	
	public static int state = 0;

	public void initCommandHandler() {
		this.addCommand("Help", new CommandHelp("Help".toLowerCase()));
		this.addCommand("Forcefield", new CommandForcefield(this.mods.forcefield, true, "Forcefield".toLowerCase()));
		this.addCommand("Miner", new CommandMiner(this.mods.miner, true, "Miner".toLowerCase()));
		this.addCommand("Step", new CommandStep(this.mods.step, true,"Step".toLowerCase()));
		this.addCommand("xRay", new CommandxRay(this.mods.xray, true, "xRay".toLowerCase()));
		this.addCommand("Blocks", new CommandBlocks("Blocks".toLowerCase()));
		this.addCommand("Friends", new CommandFriends("Friends".toLowerCase()));
		this.addCommand("Waypoints", new CommandWaypoints("Waypoints".toLowerCase()));

		for(BaseMod var0 : this.mods.modArray) {
			if(var0 != this.mods.forcefield && var0 != this.mods.miner && var0 != this.mods.step && var0 != this.mods.xray) {
				this.addCommand(var0.getName(), new CommandMod(var0, false, "<toggle/bind> <<key>>", var0.getName().toLowerCase().replace("_", "")));
			}
		}
	}

	/**
	 * The method for adding the commands.
	 * @param var1 - The String of the command.
	 * @param var2 - The Command class.
	 **/
	private void addCommand(String var1, BaseCommand var2) {
		if(!(this.cmdArray.contains(var2))) {
			this.cmdHash.put(var1.toLowerCase().replace("_", ""), var2);
			this.cmdArray.add(var2);

			this.utils.log("Command", "Added the command: " + var1.toLowerCase().replace("_", ""));
			//this.utils.log("Command", "Author: " + var2.getAuthor());
		}
	}

	/**
	 * The method for running the commands.
	 * @param var1 - The String that has been entered.
	 **/
	public void runCommands(String var1, int state) {
		this.state = state;
		
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

			if(this.cmdHash.containsKey(var3)) {
				BaseCommand var5 = (BaseCommand)this.cmdHash.get(var3);

				if(var5 == null) {
					if(state == 0) {
						this.utils.addMessage("Unknown command.");
					}
				} else {
					var5.onCommand(var2, var1);
				}
			} else {
				if(state == 0) {
					this.utils.addMessage("Unknown command.");
				}
			}
		} catch(Exception e) { 
			this.utils.addMessage("Command error.");
		}		
	}
}