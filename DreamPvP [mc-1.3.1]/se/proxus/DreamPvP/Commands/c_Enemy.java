package se.proxus.DreamPvP.Commands;

import java.io.File;

public class c_Enemy extends Base_Cmd {

	public c_Enemy() {
		super(".enemy");
	}

	@Override
	public void onCommand(String[] s, String s1) {
		try {
			if(s[0].equalsIgnoreCase("add")) {
				if(!dp.settings.enemyArray.contains(s[1])) {
					dp.settings.enemyArray.add(s[1]);
					dp.utils.addChat("\"" + s[1] + "\" is now in your enemy's list.");
					dp.files.saveBaseFile(new File(dp.files.baseFolder, "Enemys.txt"), dp.settings.enemyArray, true);
				} else {
					dp.utils.addChat("\"" + s[1] + "\" is already in your enemy's list.");
				}
			} if(s[0].equalsIgnoreCase("del")) {
				if(dp.settings.enemyArray.contains(s[1])) {
					dp.settings.enemyArray.remove(s[1]);
					dp.utils.addChat("\"" + s[1] + "\" is no longer in your enemy's list.");
					dp.files.saveBaseFile(new File(dp.files.baseFolder, "Enemys.txt"), dp.settings.enemyArray, true);
				} else {
					dp.utils.addChat("\"" + s[1] + "\" isn't in your enemy's list.");
				}
			}
		} catch (Exception e) {
			dp.utils.addChat(getSyntax());
		}
	}

	@Override
	public String getSyntax() {
		return "Syntax : .enemy [add/del] [username]";
	}

	@Override
	public String getUsage() {
		return "Used to do stuff with enemy's.";
	}
}