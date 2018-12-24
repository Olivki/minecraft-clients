package se.proxus.DreamPvP.Commands;

import java.io.File;

public class c_xRay extends Base_Cmd {

	public c_xRay() {
		super(".friend");
	}

	@Override
	public void onCommand(String[] s, String s1) {
		try {
			if(s[0].equalsIgnoreCase("add")) {
				if(!dp.settings.xrayArray.contains(Integer.parseInt(s[1]))) {
					dp.settings.xrayArray.add(Integer.parseInt(s[1]));
					dp.utils.addChat("\"" + s[1] + "\" is now in your xRay list.");
					dp.files.saveBaseFile(new File(dp.files.baseFolder, "xRay.txt"), dp.settings.xrayArray, false);

					if(dp.bModList.xray.getState()) {
						dp.mc.renderGlobal.loadRenderers();
					}
				} else {
					dp.utils.addChat("\"" + s[1] + "\" is already in your xRay list.");
				}
			} if(s[0].equalsIgnoreCase("del")) {
				if(dp.settings.xrayArray.contains(Integer.parseInt(s[1]))) {
					dp.settings.xrayArray.remove(dp.settings.xrayArray.indexOf(Integer.parseInt(s[1])));
					dp.utils.addChat("\"" + s[1] + "\" is no longer in your xRay list.");
					dp.files.saveBaseFile(new File(dp.files.baseFolder, "xRay.txt"), dp.settings.xrayArray, false);

					if(dp.bModList.xray.getState()) {
						dp.mc.renderGlobal.loadRenderers();
					}
				} else {
					dp.utils.addChat("\"" + s[1] + "\" isn't in your xRay list.");
				}
			}
		} catch (Exception e) {
			dp.utils.addChat(getSyntax());
		}
	}

	@Override
	public String getSyntax() {
		return "Syntax : .xray [add/del] [id]";
	}

	@Override
	public String getUsage() {
		return "Used to do stuff with xRay.";
	}
}