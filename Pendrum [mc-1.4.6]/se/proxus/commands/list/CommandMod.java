package se.proxus.commands.list;

import se.proxus.commands.*;
import se.proxus.mods.*;

public class CommandMod extends BaseCommand {

	public BaseMod mod = null;

	public boolean custom = false;

	public CommandMod(BaseMod mod, boolean custom, String syntax, String name) {
		super(name, mod.getInfo().getDescription(), syntax, mod.getInfo().getAuthor());
		this.mod = mod;
		this.custom = custom;
	}

	@Override
	public void onCommand(String[] var0, String var1) {
		try {
			if(var0[0].equalsIgnoreCase("toggle")) {
				this.mod.toggle();
				this.mod.getConfig().saveConfig();
				this.addChat('r', this.mod.getName().replace("_", " ") + ": " + (this.mod.getState() ? "Enabled." : "Disabled"));
				
				if(this.mod.getName().equalsIgnoreCase("xRay")) {
					this.renderBlocks();
				}
			} if(var0[0].equalsIgnoreCase("bind")) {
				this.mod.getInfo().setKey(var0[1].toUpperCase(), true);
				this.addChat('r', this.mod.getName() + " keybind: " + var0[1].toUpperCase());
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