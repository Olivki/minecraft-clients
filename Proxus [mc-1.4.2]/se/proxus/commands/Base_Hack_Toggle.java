package se.proxus.commands;

import se.proxus.hacks.*;

public class Base_Hack_Toggle extends Base_Command {
	
	public Base_Hack
	hack = null;
	
	public Base_Hack_Toggle(Base_Hack hack) {
		this.hack = hack;
	}

	@Override
	public void runCommand(String[] var1, String var2) {
		try {
			this.hack.toggle();
			this.addToggle(this.hack.getName(), this.hack.getState());
		} catch (Exception e) {
			this.addSyntax(this.getSyntax());
		}
	}

	@Override
	public String getSyntax() {
		return "-" + this.hack.getName().toLowerCase();
	}

	@Override
	public String getUsage() {
		return "Used to toggle the \"" + this.hack.getName() + "\" hack.";
	}

	@Override
	public String getName(boolean var1) {
		return this.hack.getName();
	}
}