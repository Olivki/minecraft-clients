package se.proxus.commands;

public class m_Echo extends Base_Command {

	@Override
	public void runCommand(String[] var1, String var2) {
		try {
			addChat(var2.substring(5));
		} catch (Exception e) {
			addSyntax(getSyntax());
		}
	}

	@Override
	public String getSyntax() {
		return "echo [String]";
	}

	@Override
	public String getUsage() {
		return "Used to echo things in the console.";
	}

	@Override
	public String getName(boolean var1) {
		return var1 ? "ec" : "echo";
	}
}