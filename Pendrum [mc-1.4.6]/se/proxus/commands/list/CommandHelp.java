package se.proxus.commands.list;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import se.proxus.commands.*;
import se.proxus.threads.*;

public class CommandHelp extends BaseCommand {

	public CommandHelp(String name) {
		super(name, "Used to generate a help file.", "<>", "Oliver");
	}

	@Override
	public void onCommand(String[] var0, String var1) {
		try {
			this.createHelpFile(new File(this.wrapper.getMinecraft().getMinecraftDir() + "/Pendrum/", "Commands.txt"));
			this.utils.openFile(this.wrapper.getMinecraft().getMinecraftDir() + "/Pendrum/Commands.txt");
			this.addChat('r', "Created the help file.");
		} catch(Exception e) {
			
		}
	}

	public void createHelpFile(File var0) {
		try {
			PrintWriter var1 = new PrintWriter(new FileWriter(var0));

			var1.println("#If something in the syntax is sorrounded by <<(thing)>> that means that only some commands use it.");
			var1.println("#");
			var1.println("#The predection for the commands follows the syntax of [Name - Syntax - Description]");
			var1.println("#");
			var1.println("#If something in the syntax is sorrounded by >>(thing)<< that means that it's a example.");
			var1.println();

			for(int var2 = 0; var2 < this.cmds.cmdArray.size(); var2++) {
				BaseCommand var3 = (BaseCommand)this.cmds.cmdArray.get(var2);

				var1.println("  -" + var3.getName());
				var1.println("    Description: " + var3.getDescription());
				var1.println("    Syntax: " + var3.getSyntax());
				var1.println("    Author: " + var3.getAuthor());
				var1.println("    Index: " + var2);
				var1.println();
			}

			var1.close();
		} catch(Exception e) {
			this.addChat('c', "Syntax§r: -" + this.getName() + " " + this.getSyntax());
			e.printStackTrace();
		}
	}
}