package se.proxus.DreamPvP.Commands;

public class c_Help extends Base_Cmd {

	public c_Help() {
		super(".help");
	}

	@Override
	public void onCommand(String[] s, String s1) {
		if(s.length < 1) {
			String s2 = "Commands avaible : ";

			for(Object o : dp.bCmdList.cmdHash.keySet()) {
				String s3 = (String)o;
				s2 = s2 + (dp.settings.chatMode == 2 ? "" : ".") + s3 + ", ";
			}

			dp.utils.addChat(s2);
		} else {
			String s2 = s[0];

			if(dp.bCmdList.cmdHash.containsKey(s2)) {
				Base_Cmd bCmd = (Base_Cmd)dp.bCmdList.cmdHash.get(s2);

				if(bCmd == null) {
					dp.utils.addChat("\"" + s1 + "\" is not a command.");
				}
				
				String s3 = "Help section for the command \"" + bCmd.getCmd() + "\".";
				String s4 = "Syntax : " + bCmd.getSyntax();
				String s5 = "Usage : " + bCmd.getUsage();
				
				dp.utils.addChat(s3);
				dp.utils.addChat(s4);
				dp.utils.addChat(s5);
			} else {
				dp.utils.addChat("\"" + s1 + "\" is not a command.");
			}
		}
	}

	@Override
	public String getSyntax() {
		return ".help [nothing / command]";
	}

	@Override
	public String getUsage() {
		return "Used to get help on shit.";
	}
}