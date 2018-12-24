package se.proxus.commands;

import java.io.File;

import net.minecraft.src.Tessellator;

import org.lwjgl.input.Keyboard;

import se.proxus.hacks.*;

public class m_xRay extends Base_Command {

	@Override
	public void runCommand(String[] var1, String var2) {
		try {
			if(var1[0].equalsIgnoreCase("add")) {
				if(!(this.vars.xrayArray.contains(Integer.parseInt(var1[1])))) {
					this.vars.xrayArray.add(Integer.parseInt(var1[1]));
					this.addChat("\"" + var1[1] + "\" is now in your xray array.");
					this.files.saveBaseFile(new File(this.files.baseFolder, "xRay.txt"), this.vars.xrayArray, false);
					
					if(this.hacks.xray.getState()) {
						this.mc.renderGlobal.loadRenderers();
					}
				} else {
					this.addChat("\"" + var1[1] + "\" is already in your xray array.");
				}
			} if(var1[0].equalsIgnoreCase("del")) {
				if(this.vars.xrayArray.contains(Integer.parseInt(var1[1]))) {
					this.vars.xrayArray.remove(this.vars.xrayArray.indexOf(Integer.parseInt(var1[1])));
					this.addChat("\"" + var1[1] + "\" is no longer in your xray array.");
					this.files.saveBaseFile(new File(this.files.baseFolder, "xRay.txt"), this.vars.xrayArray, false);
					
					if(this.hacks.xray.getState()) {
						this.mc.renderGlobal.loadRenderers();
					}
				} else {
					this.addChat("\"" + var1[1] + "\" is not in your xray array.");
				}
			} if(var1[0].equalsIgnoreCase("opacity")) {
				this.addChat("\"Opacity\", old \"" + this.vars.opacity + "\" new \"" + Integer.parseInt(var1[1]) + "\".");
				Tessellator.opacity = Integer.parseInt(var1[1]);
				this.vars.opacity = Integer.parseInt(var1[1]);
				
				if(this.hacks.xray.getState()) {
					this.mc.renderGlobal.loadRenderers();
				}
			}
		} catch (Exception e) {
			this.addSyntax(this.getSyntax());
		}
	}

	@Override
	public String getName(boolean var1) {
		return var1 ? "xr" : "xray";
	}

	@Override
	public String getSyntax() {
		return "xray [add/del/opacity] [id/integer]";
	}

	@Override
	public String getUsage() {
		return "Used to [add/delete] blocks [from/to] your xray array.";
	}
}