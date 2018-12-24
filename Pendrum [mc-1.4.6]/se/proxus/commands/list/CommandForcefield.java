package se.proxus.commands.list;

import se.proxus.commands.*;
import se.proxus.mods.*;

public class CommandForcefield extends BaseCommand {

	public BaseMod mod = null;

	public boolean custom = false;

	public CommandForcefield(BaseMod mod, boolean custom, String name) {
		super(name, mod.getInfo().getDescription(), "<toggle/bind/threshhold/range/silent_aimbot/mobs/animals/players/crits/infected> <<value/key/state>>", mod.getInfo().getAuthor());
		this.mod = mod;
		this.custom = custom;
	}

	@Override
	public void onCommand(String[] var0, String var1) {
		try {
			if(var0[0].equalsIgnoreCase("toggle")) {
				this.mod.toggle();
				this.addChat('r', this.mod.getName() + ": " + (this.mod.getState() ? "Enabled." : "Disabled"));
			} if(var0[0].equalsIgnoreCase("bind")) {
				this.mod.getInfo().setKey(var0[1].toUpperCase(), true);
				this.addChat('r', this.mod.getName() + " keybind: " + var0[1].toUpperCase());
			} if(var0[0].equalsIgnoreCase("silent_aimbot")) {
				this.mod.setOption("Forcefield_silent_aimbot", Boolean.parseBoolean(var0[1]), true);
				this.addChat('r', "Forcefield silent aimbot: " + Boolean.parseBoolean(var0[1]));
			} if(var0[0].equalsIgnoreCase("mobs")) {
				this.mod.setOption("Forcefield_mobs", Boolean.parseBoolean(var0[1]), true);
				this.addChat('r', "Forcefield mobs: " + Boolean.parseBoolean(var0[1]));
			} if(var0[0].equalsIgnoreCase("animals")) {
				this.mod.setOption("Forcefield_animals", Boolean.parseBoolean(var0[1]), true);
				this.addChat('r', "Forcefield animals: " + Boolean.parseBoolean(var0[1]));
			} if(var0[0].equalsIgnoreCase("players")) {
				this.mod.setOption("Forcefield_players", Boolean.parseBoolean(var0[1]), true);
				this.addChat('r', "Forcefield players: " + Boolean.parseBoolean(var0[1]));
			} if(var0[0].equalsIgnoreCase("crits")) {
				this.mod.setOption("Forcefield_crits", Boolean.parseBoolean(var0[1]), true);
				this.addChat('r', "Forcefield crits: " + Boolean.parseBoolean(var0[1]));
			} if(var0[0].equalsIgnoreCase("infected")) {
				this.mod.setOption("Forcefield_infected", Boolean.parseBoolean(var0[1]), true);
				this.addChat('r', "Forcefield infected: " + Boolean.parseBoolean(var0[1]));
			}

			try {
				if(var0[0].equalsIgnoreCase("threshhold")) {
					if(!(Long.parseLong(var0[1]) >= 100L)) {
						this.mod.setOption("Forcefield_threshhold", Long.parseLong(var0[1]), true);
						this.addChat('r', "Forcefield threshhold: " + Long.parseLong(var0[1]));
					} else {
						this.addChat('r', "The threshhold can't be bigger then 100.");
					} 
				} if(var0[0].equalsIgnoreCase("range")) {
					if(!(Float.parseFloat(var0[1]) >= 6.0F)) {
						this.mod.setOption("Forcefield_range", Float.parseFloat(var0[1]), true);
						this.addChat('r', "Forcefield range: " + Float.parseFloat(var0[1]));
					} else {
						this.addChat('r', "The range can't be larger then 6.");
					}
				}
			} catch(Exception e) {
				this.addChat('c', "Numbers only.");
			}
		} catch(Exception e) {
			this.addChat('c', "Syntax§r: " + "-" + this.getName() + " " + this.getSyntax());
		}
	}
}