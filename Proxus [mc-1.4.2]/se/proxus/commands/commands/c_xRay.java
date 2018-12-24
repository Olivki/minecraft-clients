package se.proxus.commands.commands;

import java.io.File;

import se.proxus.commands.Base_Command;
import se.proxus.hacks.*;

public class c_xRay extends Base_Command {

	@Override
	public void runCommand(String[] var1, String var2) {
		try {
			if(var1[0].equalsIgnoreCase("add")) {
				if(!(this.vars.XRAY_ARRAY.contains(Integer.parseInt(var1[1])))) {
					this.vars.XRAY_ARRAY.add(Integer.parseInt(var1[1]));
					this.addChat("\"" + var1[1] + "\" is now in your xRay array.");
					this.files.saveBaseFile(new File(this.files.baseFolder, "xRay.txt"), this.vars.XRAY_ARRAY, false);
					
					if(this.hacks.HACK_XRAY.getState()) {
						this.hacks.HACK_XRAY.updateRenders();
					}
				} else {
					this.addChat("\"" + var1[1] + "\" is already in your xRay array.");
				}
			} if(var1[0].equalsIgnoreCase("del")) {
				if(this.vars.XRAY_ARRAY.contains(Integer.parseInt(var1[1]))) {
					this.vars.XRAY_ARRAY.remove(this.vars.XRAY_ARRAY.indexOf(Integer.parseInt(var1[1])));
					this.addChat("\"" + var1[1] + "\" is no longer in your HACK_XRAY array.");
					this.files.saveBaseFile(new File(this.files.baseFolder, "xRay.txt"), this.vars.XRAY_ARRAY, false);
					
					if(this.hacks.HACK_XRAY.getState()) {
						this.hacks.HACK_XRAY.updateRenders();
					}
				} else {
					this.addChat("\"" + var1[1] + "\" is not in your xRay array.");
				}
			}
		} catch (Exception e) {
			this.addSyntax(this.getSyntax());
		}
	}

	@Override
	public String getSyntax() {
		return "-blocks [add/del] [id]";
	}

	@Override
	public String getUsage() {
		return "Used to [add/del] stuff [from/to] your xRay array.";
	}

	@Override
	public String getName(boolean var1) {
		return "-blocks";
	}
}