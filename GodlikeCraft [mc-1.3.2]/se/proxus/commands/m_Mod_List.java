package se.proxus.commands;

import org.lwjgl.input.Keyboard;

import se.proxus.hacks.*;

public class m_Mod_List extends Base_Command {

	@Override
	public void runCommand(String[] var1, String var2) {
		try {
			for(Base_Hack var3 : hacks.hackArray) {
				addChat(Keyboard.getKeyName(var3.getKey()) + " - §" + var3.getCol() + var3.getName() + "§f - " + var3.getDescription());
			}
		} catch (Exception e) {
			addSyntax(getSyntax());
		}
	}
	
	@Override
	public String getName(boolean var1) {
		return var1 ? "listm" : "listmods";
	}

	@Override
	public String getSyntax() {
		return "listmods";
	}

	@Override
	public String getUsage() {
		return "Used to list all the mods avaible in the client.";
	}
}