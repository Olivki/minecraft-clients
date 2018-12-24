package se.proxus.commands;

import se.proxus.utils.Teleport;

public class m_Teleport extends Base_Command {

	@Override
	public void runCommand(String[] var1, String var2) {
		try {
			Teleport var3 = new Teleport();
			
			var3.teleportTo(Double.parseDouble(var1[0]), Double.parseDouble(var1[1]), Double.parseDouble(var1[2]));
			addChat("Teleported to X:" + var1[0] + ", Y:" + var1[1] + ", Z:" + var1[2] + ".");
		} catch (Exception e) {
			addSyntax(getSyntax());
		}
	}

	@Override
	public String getSyntax() {
		return "teleport [x] [y] [z]";
	}

	@Override
	public String getUsage() {
		return "Used to teleport to the given cords.";
	}

	@Override
	public String getName(boolean var1) {
		return var1 ? "tel" : "teleport";
	}
}