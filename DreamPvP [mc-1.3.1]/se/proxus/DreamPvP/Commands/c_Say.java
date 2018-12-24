package se.proxus.DreamPvP.Commands;

public class c_Say extends Base_Cmd {

	public c_Say() {
		super(".say");
	}

	@Override
	public void onCommand(String[] s, String s1) {
		try {
			dp.utils.sendChat(s1.substring(3).replace(".", "%|%|%"));
		} catch (Exception e) {
			dp.utils.addChat(getSyntax());
		}
	}

	@Override
	public String getSyntax() {
		return "Syntax : .say [Your message.]";
	}

	@Override
	public String getUsage() {
		return "Used to send a chat message to the server.";
	}
}