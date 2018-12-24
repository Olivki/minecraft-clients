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

public class ScreenAltManager extends GuiScreen {

	private ScreenSlotAlt guiSlot = null;

	public GuiScreen parentScreen = null;

	public GuiButton deleteAltButton = null;
	
	public GuiButton loginAltButton = null;

	public boolean deletePressed = false;

	public ScreenAltManager(GuiScreen parentScreen) {
		this.parentScreen = parentScreen;
	}

	@Override
	public void initGui() {
		this.guiSlot = new ScreenSlotAlt(this);
		this.guiSlot.registerScrollButtons(this.controlList, 7, 8);
		
		this.deleteAltButton = new GuiButton(1, this.width / 2 - 42, this.height - 48, 56, 20, "Remove");
		this.deleteAltButton.enabled = this.guiSlot.getSelected() >= 0 && this.guiSlot.getSelected() < this.mc.pm.sett.altArray.size();
		
		this.loginAltButton = new GuiButton(2, this.width / 2 + 16, this.height - 48, 84, 20, "Login.");
		this.loginAltButton.enabled = this.guiSlot.getSelected() >= 0 && this.guiSlot.getSelected() < this.mc.pm.sett.altArray.size();
		
		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height - 48, 56, 20, "Add"));
		this.controlList.add(this.deleteAltButton);
		this.controlList.add(this.loginAltButton);
		this.controlList.add(new GuiButton(100, this.width / 2 - 100, this.height - 26, "Done"));
	}

	@Override
	protected void actionPerformed(GuiButton var0) {
		switch(var0.id) {
		case 0:
			this.mc.displayGuiScreen(new ScreenAltManagerAdd(this.parentScreen));
			break;

		case 1:
			String var1 = this.mc.pm.sett.altArray.get(guiSlot.getSelected()).username;

			if(var1 != null) {
				this.deletePressed = true;

				String var2 = "Are you sure you want to delete this alt?";

				String var3 = "\"" + var1 + "\" will be lost forever! (A long time!)";

				String var4 = "Delete.";

				String var5 = "Cancel.";

				GuiYesNo var6 = new GuiYesNo(this, var2, var3, var4, var5, this.guiSlot.getSelected());

				this.mc.displayGuiScreen(var6);

				this.mc.pm.sett.saveAlts(new File(this.mc.pm.mainFolder, "Alts.cfg"));
			}
			break;

		case 2:
			try {
				ThreadLogin var7 = new ThreadLogin(this.guiSlot.getName(), this.guiSlot.getPass());
				var7.run();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case 100:
			this.mc.displayGuiScreen(this.parentScreen);
			this.mc.pm.sett.saveAlts(new File(this.mc.pm.mainFolder, "Alts.cfg"));
			break;
		}

		this.guiSlot.actionPerformed(var0);
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		this.guiSlot.drawScreen(I1, I2, I3);

		this.drawCenteredString(this.fontRenderer, "Alt manager [" + String.valueOf(this.guiSlot.getSelected() + 1) + "/" + this.mc.pm.sett.altArray.size() + "]", this.width / 2, 10, 0xFFFFFFFF);

		super.drawScreen(I1, I2, I3);
	}

	@Override
	public void confirmClicked(boolean flag, int i1) {
		if(this.deletePressed) {
			this.deletePressed = false;

			if(flag) {
				this.mc.pm.sett.altArray.remove(i1);
				this.mc.pm.sett.saveAlts(new File(this.mc.pm.mainFolder, "Alts.cfg"));
			}

			this.mc.displayGuiScreen(this);
		}
	}
}