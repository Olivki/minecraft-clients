package se.proxus.commands.list;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import se.proxus.commands.*;
import se.proxus.threads.*;

public class CommandFriends extends BaseCommand {

	public CommandFriends(String name) {
		super(name, "Used to add friends.", "<add/remove> <username> >>Notch/iVietJr<<", "Oliver");
	}

	@Override
	public void onCommand(String[] var0, String var1) {
		try {
			if(var0[0].equalsIgnoreCase("add")) {
				if(!(this.sett.friendArray.contains(var0[1].toLowerCase()))) {
					this.sett.addFriend(var0[1].toLowerCase());
					this.sett.saveBaseFile(new File(this.mainFolder, "Friends.cfg"), this.sett.friendArray, true);
					this.addChat('r', "Added the friend: " + var0[1]);
				} else {
					this.addChat('r', "The friend array already contains: " + var0[1]);
				}
			} if(var0[0].equalsIgnoreCase("remove")) {
				if(this.sett.friendArray.contains(var0[1].toLowerCase())) {
					this.sett.removeFriend(var0[1].toLowerCase());
					this.sett.saveBaseFile(new File(this.mainFolder, "Friends.cfg"), this.sett.friendArray, true);
					this.addChat('r', "Removed the friend: " + var0[1]);
				} else {
					this.addChat('r', "The friend array doesn't contain: " + var0[1]);
				}
			}
		} catch(Exception e) {
			this.addChat('c', "Syntax§r: -" + this.getName() + " " + this.getSyntax());
		}
	}
}