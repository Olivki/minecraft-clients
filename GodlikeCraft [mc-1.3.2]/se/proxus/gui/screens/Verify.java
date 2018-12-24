package se.proxus.gui.screens;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.StringTranslate;

import org.lwjgl.input.Keyboard;

import se.proxus.gui.screens.utils.GuiPassword;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.io.*;
import java.net.URLConnection;
import java.util.*;
import java.security.PublicKey;
import java.security.cert.Certificate;
import javax.net.ssl.HttpsURLConnection;

public class Verify extends GuiScreen {

	private GuiTextField fieldKey;
	private int validCounter = 0;
	private boolean isValid = false;
	private String username = "";

	public void updateScreen() {
		fieldKey.updateCursorCounter();
		
		if(isValid) {
			validCounter++;
			
			if(validCounter == 20) {
				mc.displayGuiScreen(new GuiMainMenu());
			}
		}
	}

	public void initGui() {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		Keyboard.enableRepeatEvents(true);
		controlList.clear();
		controlList.add(new GuiButton(0, width / 2 - 100, (height / 4 - 10) + 75, "Login."));
		controlList.add(new GuiButton(1, width / 2 - 100, (height / 4 - 10) + 100, "Quit."));
		String s = mc.session.username;
		((GuiButton)controlList.get(0)).enabled = s.length() > 0;

		fieldKey = new GuiTextField(fontRenderer, width / 2 - 100, (height / 4 - 10) + 50, 200, 20);

		fieldKey.setMaxStringLength(400);
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	protected void actionPerformed(GuiButton guibutton) {
		if(!guibutton.enabled) {
			return;
		} if(guibutton.id == 1) {
			mc.shutdown();
		} if(guibutton.id == 0) {
			try {
				URL url = new URL(this.mc.dm.crypt.decryptText("-m;(&KuB#I#&:BHd7mS*4n-mtB;7P%(`>D}Dfm") + this.fieldKey.getText());
				BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
				String s = reader.readLine();
				
				this.mc.dm.utils.log(s);
				
				if(s.equalsIgnoreCase(this.mc.dm.crypt.decryptText(".g=xX&K")) || !(s.length() > 7)) {
					mc.shutdown();
				} if(s.startsWith(this.mc.dm.crypt.decryptText(";Z3!P"))) { 
					String[] var1 = s.split(" : ");
					isValid = true;
					username = var1[1];
					//
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected void keyTyped(char c, int i) {
		fieldKey.textboxKeyTyped(c, i);
		if(c == '\r') {
			actionPerformed((GuiButton)controlList.get(0));
		}
		((GuiButton)controlList.get(0)).enabled = fieldKey.getText().length() > 0;
	}

	protected void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		fieldKey.mouseClicked(i, j, k);
	}

	public void drawScreen(int i, int j, float f) {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		drawDefaultBackground();
		drawCenteredString(fontRenderer, "Login to GodlikeCraft.", width / 2, (height / 4 - 60) + 20, 0xFFFFFFFF);
		
		if(isValid) {
			drawCenteredString(fontRenderer, "Welcome back, " + username + ".", width / 2, (height / 4 - 50) + 20, 0xFFFFFFFF);
		}
		
		fontRenderer.drawString("Key : ", width / 2 - 98, (height / 4 - 10)+40, 0xFFFFFFFF);
		fieldKey.drawTextBox();
		super.drawScreen(i, j, f);
	}
}