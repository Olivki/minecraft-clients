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

public class ScreenCredits extends GuiScreen {

	private ScreenSlotCredits guiSlot = null;

	public GuiScreen parentScreen = null;

	public boolean deletePressed = false;

	public ScreenCredits(GuiScreen parentScreen) {
		this.parentScreen = parentScreen;
	}

	@Override
	public void initGui() {
		this.guiSlot = new ScreenSlotCredits(this);
		this.guiSlot.registerScrollButtons(this.controlList, 7, 8);
		
		this.controlList.add(new GuiButton(100, this.width / 2 - 100, this.height - 26, "Done"));
	}

	@Override
	protected void actionPerformed(GuiButton var0) {
		switch(var0.id) {
		case 100:
			this.mc.displayGuiScreen(this.parentScreen);
			break;
		}

		this.guiSlot.actionPerformed(var0);
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		this.guiSlot.drawScreen(I1, I2, I3);

		this.drawCenteredString(this.fontRenderer, "Credits [" + String.valueOf(this.guiSlot.getSelected() + 1) + "/" + this.mc.pm.sett.creditsArray.size() + "]", this.width / 2, 10, 0xFFFFFFFF);

		super.drawScreen(I1, I2, I3);
	}
}