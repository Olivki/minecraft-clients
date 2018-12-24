package se.proxus.commands.list;

import se.proxus.commands.*;
import se.proxus.mods.*;

public class CommandxRay extends BaseCommand {

	public BaseMod mod = null;

	public boolean custom = false;

	public CommandxRay(BaseMod mod, boolean custom, String name) {
		super(name, mod.getInfo().getDescription(), "<toggle/bind/opacity> <<value>>", mod.getInfo().getAuthor());
		this.mod = mod;
		this.custom = custom;
	}

	@Override
	public void onCommand(String[] var0, String var1) {
		try {
			if(var0[0].equalsIgnoreCase("toggle")) {
				this.mod.toggle();
				this.addChat('r', this.mod.getName() + ": " + (this.mod.getState() ? "Enabled." : "Disabled"));
				this.renderBlocks();
			} if(var0[0].equalsIgnoreCase("bind")) {
				this.mod.getInfo().setKey(var0[1].toUpperCase(), true);
				this.addChat('r', this.mod.getName() + " keybind: " + var0[1].toUpperCase());
			}

			try {
				if(var0[0].equalsIgnoreCase("opacity")) {
					if(!(Integer.parseInt(var0[1]) >= 255)) {
						this.mod.setOption("xRay_opacity", Integer.parseInt(var0[1]), true);
						this.addChat('r', "xRay opacity: " + Integer.parseInt(var0[1]));
						this.renderBlocks();
					} else {
						this.renderBlocks();
						this.addChat('r', "The value can't be greater then 255.");
					}
				}
			} catch(Exception e) {
				this.addChat('c', "Numbers only.");
			}
		} catch(Exception e) {
			this.addChat('c', "Syntax§r: " + "-" + this.getName() + " " + this.getSyntax());
		}
	}

	private void renderBlocks() {
		int var0 = (int)this.wrapper.getPlayer().posX;
		int var1 = (int)this.wrapper.getPlayer().posY;
		int var2 = (int)this.wrapper.getPlayer().posZ;
		this.wrapper.getMinecraft().renderGlobal.markBlockRangeForRenderUpdate(var0 - 200, var1 - 200, var2 - 200, var0 + 200, var1 + 200, var2 + 200);
	}
}