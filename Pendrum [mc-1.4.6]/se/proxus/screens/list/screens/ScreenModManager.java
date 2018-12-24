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

import org.lwjgl.input.Keyboard;

import se.proxus.Pendrum;
import se.proxus.screens.list.slots.*;
import se.proxus.threads.*;
import se.proxus.utils.placeholders.PlaceholderScreen;

import net.minecraft.src.*;

public class ScreenModManager extends GuiScreen {

	private ScreenSlotMod guiSlot = null;

	public GuiScreen parentScreen = null;

	public GuiButton buttonKeybind = null;

	public GuiButton buttonToggle = null;

	public boolean bindPressed = false;
	
	public boolean secondEscapeTry = false;

	public ScreenModManager(GuiScreen parentScreen) {
		this.parentScreen = parentScreen;
	}

	@Override
	public void initGui() {
		this.guiSlot = new ScreenSlotMod(this);
		this.guiSlot.registerScrollButtons(this.controlList, 7, 8);

		this.buttonKeybind = new GuiButton(0, this.width / 2 - 100, this.height - 48, 98, 20, "Key: " + this.mc.pm.mods.modArray.get(this.guiSlot.getSelected()).getInfo().getKey());
		this.buttonToggle = new GuiButton(1, this.width / 2 + 2, this.height - 48, 98, 20, "State: " + this.mc.pm.mods.modArray.get(this.guiSlot.getSelected()).getState());

		this.buttonToggle.enabled = this.mc.thePlayer != null;

		this.controlList.add(this.buttonKeybind);
		this.controlList.add(this.buttonToggle);
		this.controlList.add(new GuiButton(2, this.width / 2 + 2, this.height - 26, 98, 20, "Done"));
		this.controlList.add(new GuiButton(3, this.width / 2 - 100, this.height - 26, 98, 20, "Unbind key"));
	}

	@Override
	public void updateScreen() {
		this.controlList.clear();
		
		this.buttonKeybind = new GuiButton(0, this.width / 2 - 100, this.height - 48, 98, 20, "Key: " + (this.bindPressed ? "--" : this.mc.pm.mods.modArray.get(this.guiSlot.getSelected()).getInfo().getKey()));
		this.buttonToggle = new GuiButton(1, this.width / 2 + 2, this.height - 48, 98, 20, "State: " + this.mc.pm.mods.modArray.get(this.guiSlot.getSelected()).getState());

		this.buttonToggle.enabled = this.mc.thePlayer != null;

		this.controlList.add(this.buttonKeybind);
		this.controlList.add(this.buttonToggle);
		this.controlList.add(new GuiButton(2, this.width / 2 + 2, this.height - 26, 98, 20, "Done"));
		this.controlList.add(new GuiButton(3, this.width / 2 - 100, this.height - 26, 98, 20, "Unbind key"));
	}

	@Override
	protected void actionPerformed(GuiButton var0) {
		if(var0.enabled) {
			switch(var0.id) {
			case 0:
				this.bindPressed =! this.bindPressed;
				break;

			case 1:
				this.mc.pm.mods.modArray.get(this.guiSlot.getSelected()).toggle();
				break;

			case 2:
				this.mc.displayGuiScreen(this.parentScreen);
				break;
				
			case 3:
				this.mc.pm.mods.modArray.get(this.guiSlot.getSelected()).getInfo().setKey("NONE", true);
				break;
			}
		}

		this.guiSlot.actionPerformed(var0);
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		this.guiSlot.drawScreen(I1, I2, I3);

		this.drawCenteredString(this.fontRenderer, "Mod manager [" + String.valueOf(this.guiSlot.getSelected() + 1) + "/" + this.mc.pm.mods.modArray.size() + "]", this.width / 2, 10, 0xFFFFFFFF);

		super.drawScreen(I1, I2, I3);
	}

	@Override
	public void keyTyped(char var0, int var1) {
		try {
			if(this.bindPressed && !(Keyboard.getKeyName(var1).equalsIgnoreCase("ESCAPE"))) {
				this.mc.pm.mods.modArray.get(this.guiSlot.getSelected()).getInfo().setKey(Keyboard.getKeyName(var1), true);
				this.bindPressed = false;
			} else if(Keyboard.getKeyName(var1).equalsIgnoreCase("ESCAPE") && this.bindPressed) {
				this.bindPressed = false;
			} else if(!(this.bindPressed) && Keyboard.getKeyName(var1).equalsIgnoreCase("ESCAPE")) {
				this.secondEscapeTry = true;
				
				if(this.secondEscapeTry) {
					this.secondEscapeTry = false;
					this.mc.displayGuiScreen(this.parentScreen);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}