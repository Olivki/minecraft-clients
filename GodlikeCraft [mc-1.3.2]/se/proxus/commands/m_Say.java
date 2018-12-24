package se.proxus.commands;

public class m_Say extends Base_Command {

	@Override
	public void runCommand(String[] var1, String var2) {
		try {
			this.utils.sendChat(var2.substring(4));
		} catch (Exception e) {
			addSyntax(getSyntax());
		}
	}

	@Override
	public String getSyntax() {
		return "say [String]";
	}

	@Override
	public String getUsage() {
		return "Used to send chat messages to the server.";
	}

	@Override
	public String getName(boolean var1) {
		return var1 ? "sa" : "say";
	}
}