package se.proxus.screens.list.screens;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import se.proxus.screens.list.slots.*;
import se.proxus.threads.*;

import net.minecraft.src.*;

public class ScreenBlocksManager extends GuiScreen {

	private ScreenSlotBlocks guiSlot = null;

	public GuiScreen parentScreen = null;

	public GuiButton deleteBlockButton = null;

	public boolean deletePressed = false;

	public ScreenBlocksManager(GuiScreen parentScreen) {
		this.parentScreen = parentScreen;
	}

	@Override
	public void initGui() {
		this.guiSlot = new ScreenSlotBlocks(this);
		this.guiSlot.registerScrollButtons(this.controlList, 7, 8);
		
		this.deleteBlockButton = new GuiButton(1, this.width / 2 + 1, this.height - 48, 98, 20, "Remove");
		this.deleteBlockButton.enabled = this.guiSlot.getSelected() >= 0 && this.guiSlot.getSelected() < this.mc.pm.sett.altArray.size();
		
		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height - 48, 98, 20, "Add"));
		this.controlList.add(this.deleteBlockButton);
		this.controlList.add(new GuiButton(100, this.width / 2 - 100, this.height - 26, "Done"));
	}

	@Override
	protected void actionPerformed(GuiButton var0) {
		switch(var0.id) {
		case 0:
			this.mc.displayGuiScreen(new ScreenBlocksList(this));
			break;
			
		case 1:
			String var1 = this.mc.pm.utils.blockIDToName(this.mc.pm.sett.blockArray.get(this.guiSlot.getSelected()));

			if(var1 != null) {
				this.deletePressed = true;

				String var2 = "Are you sure you want to delete this block?";

				String var3 = "\"" + var1 + "\" will be lost forever! (A long time!)";

				String var4 = "Delete.";

				String var5 = "Cancel.";

				GuiYesNo var6 = new GuiYesNo(this, var2, var3, var4, var5, this.mc.pm.sett.blockArray.get(this.guiSlot.getSelected()));

				this.mc.displayGuiScreen(var6);

				this.mc.pm.sett.saveBaseFile(new File(this.mc.pm.mainFolder, "Blocks.cfg"), this.mc.pm.sett.blockArray, false);
			}
			break;

		case 100:
			this.mc.displayGuiScreen(this.parentScreen);
			break;
		}

		this.guiSlot.actionPerformed(var0);
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		this.guiSlot.drawScreen(I1, I2, I3);

		this.drawCenteredString(this.fontRenderer, "Blocks manager [" + String.valueOf(this.guiSlot.getSelected() + 1) + "/" + this.mc.pm.sett.blockArray.size() + "]", this.width / 2, 10, 0xFFFFFFFF);

		super.drawScreen(I1, I2, I3);
	}
	
	@Override
	public void confirmClicked(boolean flag, int i1) {
		if(this.deletePressed) {
			this.deletePressed = false;

			if(flag) {
				this.mc.pm.sett.removeBlock(i1);
				this.renderBlocks();
			}

			this.mc.displayGuiScreen(this);
		}
	}
	
	private void renderBlocks() {
		int var0 = (int)this.mc.pm.wrapper.getPlayer().posX;
		int var1 = (int)this.mc.pm.wrapper.getPlayer().posY;
		int var2 = (int)this.mc.pm.wrapper.getPlayer().posZ;
		this.mc.pm.wrapper.getMinecraft().renderGlobal.markBlockRangeForRenderUpdate(var0 - 200, var1 - 200, var2 - 200, var0 + 200, var1 + 200, var2 + 200);
	}
}