package se.proxus.DreamPvP.Commands;

import se.proxus.DreamPvP.Utils.Placeholders.u_Block;

public class c_Block extends Base_Cmd {

	public c_Block() {
		super(".block");
	}

	@Override
	public void onCommand(String[] s, String s1) {
		try {
			if(s[0].equalsIgnoreCase("set")) {
				dp.settings.blockArray.clear();
				dp.bModList.blockESP.id = Integer.parseInt(s[1]);
				dp.utils.addChat("The block ESP will now render around the id \"" + s[1] + "\".");
				dp.bModList.blockESP.espRefresh();
			} if(s[0].equalsIgnoreCase("clear")) {
				dp.bModList.blockESP.id = 1337;
				dp.utils.addChat("Block list has been cleared.");
			}
		} catch (Exception e) {
			dp.utils.addChat(getSyntax());
		}
	}

	@Override
	public String getSyntax() {
		return "Syntax : .block [set/clear] [id]";
	}

	@Override
	public String getUsage() {
		return "Used to do stuff with block's.";
	}
}