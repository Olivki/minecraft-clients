package se.proxus.screens.list.screens;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import se.proxus.Pendrum;
import se.proxus.screens.list.slots.*;
import se.proxus.threads.*;

import net.minecraft.src.*;

public class ScreenBlocksList extends GuiScreen {

	private ScreenSlotBlocksList guiSlot = null;

	public GuiScreen parentScreen = null;

	public boolean deletePressed = false;

	public GuiTextField searchField = null;

	public ScreenBlocksList(GuiScreen parentScreen) {
		this.parentScreen = parentScreen;
	}

	@Override
	public void initGui() {
		this.guiSlot = new ScreenSlotBlocksList(this);
		this.guiSlot.registerScrollButtons(this.controlList, 7, 8);

		this.searchField = new GuiTextField(this.fontRenderer, 2, 10, 100, 12);

		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height - 48, "Add block to the Blocks array."));
		this.controlList.add(new GuiButton(100, this.width / 2 - 100, this.height - 26, "Done"));
	}

	@Override
	protected void actionPerformed(GuiButton var0) {
		switch(var0.id) {
		case 0:
			//this.mc.pd.wrapper.getPlayer().sendChatMessage("-bls add " + Block.blocksList[this.guiSlot.getSelected()].blockID);
			this.mc.pm.sett.addBlock(Pendrum.sett.defBlockArray.get(this.guiSlot.getSelected()).blockID);
			this.renderBlocks();
			this.mc.pm.sett.saveBaseFile(new File(this.mc.pm.mainFolder, "Blocks.cfg"), this.mc.pm.sett.blockArray, false);
			this.mc.displayGuiScreen(this.parentScreen);
			break;

		case 100:
			this.mc.displayGuiScreen(this.parentScreen);
			this.mc.pm.sett.saveBaseFile(new File(this.mc.pm.mainFolder, "Blocks.cfg"), this.mc.pm.sett.blockArray, false);
			break;
		}

		this.guiSlot.actionPerformed(var0);
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		this.guiSlot.drawScreen(I1, I2, I3);

		this.drawCenteredString(this.fontRenderer, "Blocks manager [" + String.valueOf(this.guiSlot.getSelected() + 1) + "/" + this.mc.pm.sett.defBlockArray.size() + "]", this.width / 2, 10, 0xFFFFFFFF);

		this.searchField.drawTextBox();

		super.drawScreen(I1, I2, I3);
	}

	@Override
	public void mouseClicked(int var0, int var1, int var2) {
		super.mouseClicked(var0, var1, var2);

		this.searchField.mouseClicked(var0, var1, var2);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();

		this.searchField.updateCursorCounter();
	}

	@Override
	public void keyTyped(char var0, int var1) {
		super.keyTyped(var0, var1);

		this.searchField.textboxKeyTyped(var0, var1);
		String nigger = "";

		try {
			nigger = this.searchField.getText().substring(0, 1).toUpperCase() + this.searchField.getText().substring(1).toLowerCase();
			nigger = nigger.replace(" ", "-");
		} catch(Exception e) {
			
		}

		this.searchField.setText(nigger);

		ArrayList<Block> toAdd = new ArrayList<Block>();

		for(int var2 = 0; var2 < this.mc.pm.sett.defBlockArray.size(); var2++) {
			Block var3 = (Block)this.mc.pm.sett.defBlockArray.get(var2);

			String var4 = "";

			try {
				var4 = this.searchField.getText().substring(0, 1).toUpperCase() + this.searchField.getText().substring(1).toLowerCase();
				var4 = var4.replace(" ", "-");
			} catch(Exception e) {

			}

			try {

				if(this.mc.pm.utils.blockIDToName(var3.blockID).startsWith(var4.trim()) && var4.length() > 0) {
					if(!(toAdd.contains(var3))) {
						toAdd.add(var3);
					}
				} else {
					toAdd.remove(toAdd.indexOf(Block.bedrock));
					this.mc.pm.sett.defSearchBlockArray.remove(Block.bedrock);
				}
			} catch(Exception e) {

			}
		}

		this.mc.pm.sett.defSearchBlockArray.clear();

		if(this.searchField.getText().isEmpty()) {
			this.mc.pm.sett.setupDefualtBlockSearch();
		}

		for(int var5 = 0; var5 < toAdd.size(); var5++) {
			Block var6 = (Block)toAdd.get(var5);

			if(!(this.mc.pm.sett.defSearchBlockArray.contains(var6))) {
				this.mc.pm.sett.defSearchBlockArray.add(var6);
			}
		}

		toAdd.clear();
	}
	
	private void renderBlocks() {
		int var0 = (int)this.mc.pm.wrapper.getPlayer().posX;
		int var1 = (int)this.mc.pm.wrapper.getPlayer().posY;
		int var2 = (int)this.mc.pm.wrapper.getPlayer().posZ;
		this.mc.pm.wrapper.getMinecraft().renderGlobal.markBlockRangeForRenderUpdate(var0 - 200, var1 - 200, var2 - 200, var0 + 200, var1 + 200, var2 + 200);
	}
}