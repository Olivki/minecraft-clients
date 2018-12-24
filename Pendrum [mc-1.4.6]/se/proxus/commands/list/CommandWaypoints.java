package se.proxus.commands.list;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import se.proxus.commands.*;
import se.proxus.threads.*;
import se.proxus.utils.placeholders.*;

public class CommandWaypoints extends BaseCommand {

	public CommandWaypoints(String name) {
		super(name, "Used to add waypoints.", "<add/remove> <name>", "Oliver");
	}

	@Override
	public void onCommand(String[] var0, String var1) {
		try {
			if(var0[0].equalsIgnoreCase("add")) {
				if(!(this.sett.waypointArray.contains(new PlaceholderWaypoint(var0[1].substring(0, 1).toUpperCase() + var0[1].substring(1).toLowerCase(), 
						(int)this.wrapper.getPlayer().posX, 
						(int)this.wrapper.getPlayer().posY - 1,
						(int)this.wrapper.getPlayer().posZ - 1,
						0xFFFFFFFF,
						this.sett.curIP)))) {
					this.sett.addWaypoint(new PlaceholderWaypoint(var0[1].substring(0, 1).toUpperCase() + var0[1].substring(1).toLowerCase(), 
							(int)this.wrapper.getPlayer().posX, 
							(int)this.wrapper.getPlayer().posY - 1,
							(int)this.wrapper.getPlayer().posZ - 1,
							0xFFFFFFFF,
							this.sett.curIP));
					this.addChat('r', "Added the waypoint: " + var0[1].substring(0, 1).toUpperCase() + var0[1].substring(1).toLowerCase());
				} else {
					this.addChat('r', "The waypoint array already contains: " + var0[1].substring(0, 1).toUpperCase() + var0[1].substring(1).toLowerCase());
				}
			} if(var0[0].equalsIgnoreCase("remove")) {
				if(this.sett.waypointArray.contains(new PlaceholderWaypoint(var0[1].substring(0, 1).toUpperCase() + var0[1].substring(1).toLowerCase(), 
						(int)this.wrapper.getPlayer().posX, 
						(int)this.wrapper.getPlayer().posY - 1,
						(int)this.wrapper.getPlayer().posZ - 1,
						0xFFFFFFFF,
						this.sett.curIP))) {
					this.sett.removeWaypoint(new PlaceholderWaypoint(var0[1].substring(0, 1).toUpperCase() + var0[1].substring(1).toLowerCase(), 
							(int)this.wrapper.getPlayer().posX, 
							(int)this.wrapper.getPlayer().posY - 1,
							(int)this.wrapper.getPlayer().posZ - 1,
							0xFFFFFFFF,
							this.sett.curIP));
					this.addChat('r', "Removed the waypoint: " + var0[1].substring(0, 1).toUpperCase() + var0[1].substring(1).toLowerCase());
				} else {
					this.addChat('r', "The waypoint array doesn't contain: " + var0[1].substring(0, 1).toUpperCase() + var0[1].substring(1).toLowerCase());
				}
			}
		} catch(Exception e) {
			this.addChat('c', "Syntax§r: -" + this.getName() + " " + this.getSyntax());
		}
	}
}