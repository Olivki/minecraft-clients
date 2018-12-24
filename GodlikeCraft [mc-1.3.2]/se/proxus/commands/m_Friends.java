package se.proxus.commands;

import java.io.File;

import org.lwjgl.input.Keyboard;

import se.proxus.hacks.*;

public class m_Friends extends Base_Command {

	@Override
	public void runCommand(String[] var1, String var2) {
		try {
			if(var1[0].equalsIgnoreCase("add")) {
				if(!(this.utils.isFriend(var1[1]))) {
					this.vars.friendArray.add(var1[1].toLowerCase());
					this.addChat("\"" + var1[1] + "\" is now one of your friends.");
					this.files.saveBaseFile(new File(this.files.baseFolder, "Friends.txt"), this.vars.friendArray, true);
				} else {
					this.addChat("\"" + var1[1] + "\" is already one of your friends.");
				}
			} if(var1[0].equalsIgnoreCase("del")) {
				if(this.utils.isFriend(var1[1])) {
					this.vars.friendArray.remove(this.vars.friendArray.indexOf(var1[1].toLowerCase()));
					this.addChat("\"" + var1[1] + "\" is no longer one of your friends.");
					this.files.saveBaseFile(new File(this.files.baseFolder, "Friends.txt"), this.vars.friendArray, true);
				} else {
					this.addChat("\"" + var1[1] + "\" is not one of your friends.");
				}
			}
		} catch (Exception e) {
			this.addSyntax(this.getSyntax());
		}
	}

	@Override
	public String getName(boolean var1) {
		return var1 ? "fri" : "friend";
	}

	@Override
	public String getSyntax() {
		return "friend [add/del] [username]";
	}

	@Override
	public String getUsage() {
		return "Used to [add/delete] people [from/to] your friends.";
	}
}