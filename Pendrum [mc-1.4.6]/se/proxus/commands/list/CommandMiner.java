package se.proxus.commands.list;

import se.proxus.commands.*;
import se.proxus.mods.*;

public class CommandMiner extends BaseCommand {

	public BaseMod mod = null;

	public boolean custom = false;

	public CommandMiner(BaseMod mod, boolean custom, String name) {
		super(name, mod.getInfo().getDescription(), "<toggle/bind/speed> <<value>>", mod.getInfo().getAuthor());
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
			}

			try {
				if(var0[0].equalsIgnoreCase("speed")) {
					if(!(Float.parseFloat(var0[1]) > 1.0F)) {
						this.mod.setOption("Miner_speed", Float.parseFloat(var0[1]), true);
						this.addChat('r', "Miner speed: " + Float.parseFloat(var0[1]));
					} else {
						this.addChat('r', "The value can't be greater then 1.0.");
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