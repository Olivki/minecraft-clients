package se.proxus.DreamPvP.Commands;

public class c_Identify extends Base_Cmd {

	public c_Identify() {
		super(".identify");
	}

	@Override
	public void onCommand(String[] s, String s1) {
		try {
			dp.main.bot.identify(s[0]);
		} catch (Exception e) {
			dp.utils.addChat(getSyntax());
		}
	}

	@Override
	public String getSyntax() {
		return "Syntax : .identify password";
	}

	@Override
	public String getUsage() {
		return "IRC stuff.";
	}
}