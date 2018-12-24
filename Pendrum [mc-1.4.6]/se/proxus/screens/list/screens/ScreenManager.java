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
import se.proxus.utils.placeholders.PlaceholderScreen;

import net.minecraft.src.*;

public class ScreenManager extends GuiScreen {

	private ScreenSlotScreen guiSlot = null;

	public GuiScreen parentScreen = null;

	public GuiButton openScreenButton = null;

	public boolean deletePressed = false;
	
	public static ArrayList<PlaceholderScreen> screenArray = new ArrayList<PlaceholderScreen>();

	public ScreenManager(GuiScreen parentScreen) {
		this.parentScreen = parentScreen;
	}
	
	public ScreenManager() {
		
	}
	
	public void initScreenManager() {
		this.addScreen(new PlaceholderScreen("Blocks manager", "A screen for managing the blocks.", new ScreenBlocksManager(this), "Oliver"));
		this.addScreen(new PlaceholderScreen("Friend manager", "A screen for managing your friends.", new ScreenFriendManager(this), "Oliver"));
		this.addScreen(new PlaceholderScreen("Credits", "A screen for seeing all the credits.", new ScreenCredits(this), "Oliver"));
		this.addScreen(new PlaceholderScreen("Mod manager", "A screen for managing the mods.", new ScreenModManager(this), "Oliver"));
	}

	@Override
	public void initGui() {
		this.guiSlot = new ScreenSlotScreen(this);
		this.guiSlot.registerScrollButtons(this.controlList, 7, 8);
		
		this.openScreenButton = new GuiButton(1, this.width / 2 - 100, this.height - 48, "Open screen.");
		this.openScreenButton.enabled = this.guiSlot.getSelected() >= 0 && this.guiSlot.getSelected() < this.mc.pm.screens.screenArray.size();
		
		this.controlList.add(this.openScreenButton);
		this.controlList.add(new GuiButton(100, this.width / 2 - 100, this.height - 26, "Done"));
	}

	@Override
	protected void actionPerformed(GuiButton var0) {
		switch(var0.id) {
		case 1:
			this.mc.displayGuiScreen(this.screenArray.get(this.guiSlot.getSelected()).screen);
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

		this.drawCenteredString(this.fontRenderer, "Screen manager [" + String.valueOf(this.guiSlot.getSelected() + 1) + "/" + this.screenArray.size() + "]", this.width / 2, 10, 0xFFFFFFFF);

		super.drawScreen(I1, I2, I3);
	}
	
	public void addScreen(PlaceholderScreen var0) {
		if(!(this.screenArray.contains(var0))) {
			this.screenArray.add(var0);
			Pendrum.utils.log("Screen", "Added the screen: " + var0.name);
			Pendrum.utils.log("Screen", "Author: " + var0.author);
		}
	}
}