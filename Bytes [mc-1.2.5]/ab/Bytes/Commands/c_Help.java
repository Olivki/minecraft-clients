package ab.Bytes.Commands;

public class c_Help extends Base_Cmd {

	public c_Help() {
		super(".help");
	}

	@Override
	public void command(String[] s) {
		if(s.length < 1) {
			String s1 = "Commands avaible : ";

			for(Object o : bs.basecmdlist.cmdHash.keySet()) {
				String s2 = (String)o;
				s1 = s1 + "." + s2 + ", ";
			}

			bs.utils.addChat(s1);
		} else {
			String s1 = s[0];

			if(bs.basecmdlist.cmdHash.containsKey(s1)) {
				Base_Cmd bCmd = (Base_Cmd)bs.basecmdlist.cmdHash.get(s1);

				if(bCmd == null) {
					bs.utils.addChat("Unknown command.");
				}
				
				String s2 = "This is the help section for the command '" + bCmd.getName() + "'.";
				String s3 = "Help : " + bCmd.getHelp();
				String s4 = "Usage : " + bCmd.getUsage();
				
				bs.utils.addChat(s2);
				bs.utils.addChat(s3);
				bs.utils.addChat(s4);
			} else {
				bs.utils.addChat("Unknown command.");
			}
		}
	}

	@Override
	public String getUsage() {
		return ".help [command]";
	}

	@Override
	public String getHelp() {
		return "Is used to see all the commands and to see the usage and more of a command.";
	}
}