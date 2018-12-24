package se.proxus.commands;

import org.lwjgl.input.Keyboard;

import se.proxus.hacks.*;

public class m_Help extends Base_Command {

	@Override
	public void runCommand(String[] var1, String var2) {
		try {
			if(var1.length < 1) {
				String s2 = "Commands avaible : ";

				for(Object o : this.cmds.cmdHash.keySet()) {
					String s3 = (String)o;
					s2 = s2 + s3 + ", ";
				}

				addChat(s2);
			} else {
				String s2 = var1[0];

				if(this.cmds.cmdHash.containsKey(s2)) {
					Base_Command bCmd = (Base_Command)this.cmds.cmdHash.get(s2);

					if(bCmd == null) {
						addChat("\"" + var1 + "\" is not a command.");
					}

					String s3 = "Help section for the command \"" + bCmd.getName(false) + "\".";
					String s4 = "Syntax : " + bCmd.getSyntax();
					String s5 = "Usage : " + bCmd.getUsage();

					addChat(s3);
					addChat(s4);
					addChat(s5);
				} else {
					addChat("\"" + var1 + "\" is not a command.");
				}
			}
		} catch (Exception e) {
			addSyntax(getSyntax());
		}
	}

	@Override
	public String getName(boolean var1) {
		return var1 ? "he" : "help";
	}

	@Override
	public String getSyntax() {
		return "help [name / nothing]";
	}

	@Override
	public String getUsage() {
		return "Used to list all the commanda avaible in the client / more info on one command.";
	}
}