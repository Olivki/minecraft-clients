package se.proxus.commands.list;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import se.proxus.commands.*;
import se.proxus.threads.*;

public class CommandBlocks extends BaseCommand {

	public CommandBlocks(String name) {
		super(name, "Used to add blocks to the xRay.", "<add/remove> <block name> >>Gold-ore/Stone<<", "Oliver");
	}

	@Override
	public void onCommand(String[] var0, String var1) {
		try {
			if(var0[0].equalsIgnoreCase("add")) {
				if(!(this.sett.blockArray.contains(this.utils.blockNameToID(var0[1].substring(0, 1).toUpperCase() + var0[1].substring(1))))) {
					this.sett.addBlock(this.utils.blockNameToID(var0[1].substring(0, 1).toUpperCase() + var0[1].substring(1)));
					this.addChat('r', "Added the block: " + this.utils.blockIDToName(this.utils.blockNameToID(var0[1].substring(0, 1).toUpperCase() + var0[1].substring(1))));
					this.renderBlocks();
				} else {
					this.renderBlocks();
					this.addChat('r', "The block array already contains: " + this.utils.blockIDToName(this.utils.blockNameToID(var0[1].substring(0, 1).toUpperCase() + var0[1].substring(1))));
				}
			} if(var0[0].equalsIgnoreCase("remove")) {
				if(this.sett.blockArray.contains(this.utils.blockNameToID(var0[1].substring(0, 1).toUpperCase() + var0[1].substring(1)))) {
					this.sett.removeBlock(this.utils.blockNameToID(var0[1].substring(0, 1).toUpperCase() + var0[1].substring(1)));
					this.addChat('r', "Removed the block: " + this.utils.blockIDToName(this.utils.blockNameToID(var0[1].substring(0, 1).toUpperCase() + var0[1].substring(1))));
					this.renderBlocks();
				} else {
					this.renderBlocks();
					this.addChat('r', "The block array doesn't contain: " + this.utils.blockIDToName(this.utils.blockNameToID(var0[1].substring(0, 1).toUpperCase() + var0[1].substring(1))));
				}
			}
		} catch(Exception e) {
			this.addChat('c', "Syntax§r: -" + this.getName() + " " + this.getSyntax());
		}
	}
	
	private void renderBlocks() {
		int var0 = (int)this.wrapper.getPlayer().posX;
		int var1 = (int)this.wrapper.getPlayer().posY;
		int var2 = (int)this.wrapper.getPlayer().posZ;
		this.wrapper.getMinecraft().renderGlobal.markBlockRangeForRenderUpdate(var0 - 200, var1 - 200, var2 - 200, var0 + 200, var1 + 200, var2 + 200);
	}
}