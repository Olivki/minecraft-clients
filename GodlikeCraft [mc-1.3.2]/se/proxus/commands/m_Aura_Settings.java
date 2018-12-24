package se.proxus.commands;

import org.lwjgl.input.Keyboard;

import se.proxus.hacks.*;

public class m_Aura_Settings extends Base_Command {

	@Override
	public void runCommand(String[] var1, String var2) {
		try {
			if(var1[0].equalsIgnoreCase("soupdrop")) {
				this.vars.dropItemsInAutoSoup =! this.vars.dropItemsInAutoSoup;
				this.addToggle("Soup item drop", this.vars.dropItemsInAutoSoup);
				this.files.saveAuraSettings();
			} if(var1[0].equalsIgnoreCase("kitpvp")) {
				this.addChat("Old String \"" + this.vars.kitPvPString + "\" new String \"" + var1[1] + "\".");
				this.vars.kitPvPString = var1[1];
				this.files.saveAuraSettings();
			} if(var1[0].equalsIgnoreCase("aimbot")) {
				if(!(Integer.parseInt(var1[1]) < 1) && !(Integer.parseInt(var1[1]) > 2)) {
					this.vars.aimbotMode = Integer.parseInt(var1[1]);
					this.addChat("Current aimbot mode : " + (this.vars.aimbotMode == 1 ? "Server sided." : "Client sided."));
					this.files.saveAuraSettings();
				} else {
					this.addChat("The aimbot mode can only be between [1/2]!");
				}
			} if(var1[0].equalsIgnoreCase("blocking")) {
				this.vars.isAuraBlocking =! this.vars.isAuraBlocking;
				this.addToggle("Aura blocking", this.vars.isAuraBlocking);
				this.files.saveAuraSettings();
			}
		} catch (Exception e) {
			addSyntax(getSyntax());
		}
	}

	@Override
	public String getName(boolean var1) {
		return var1 ? "au" : "aura";
	}

	@Override
	public String getSyntax() {
		return "aura [soupdrop/kitpvp/aimbot/blocking]";
	}

	@Override
	public String getUsage() {
		return "Used to change the settings of the aura.";
	}
}