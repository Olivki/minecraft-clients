package se.proxus.DreamPvP.Gui.Screens;

import java.awt.Desktop;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ChatAllowedCharacters;
import net.minecraft.src.ChatLine;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import se.proxus.DreamPvP.Gui.Methods;
import se.proxus.DreamPvP.Utils.ChatLineOld;

public class Base_IRCChat extends GuiScreen {

	protected String message, name;
	private int updateCounter;
	private static final String allowedCharacters;
	public static List msgList = new ArrayList();
	public static boolean first = true;

	public Base_IRCChat(String name) {
		this.name = name;
		message = "";
		updateCounter = 0;
	}

	public void initGui() {
		Keyboard.enableRepeatEvents(true);

		controlList.add(new GuiButton(0, 2, 2, 48, 20, "Back."));
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	public void updateScreen() {
		updateCounter++;
	}

	protected void keyTyped(char c, int i) {
		if (i == 1) {
			mc.displayGuiScreen(null);
			return;
		}

		if(c == '\026') {
			String cb = GuiScreen.getClipboardString();

			if(cb != null) {
				message += cb;
			}
		}

		if (i == 28) {
			String s = message.trim();

			if (s.length() > 0) {
				String s1 = message.trim();
				addMsg("> You : " + message);
				mc.dp.main.bot.sendMessage(name, message);
			}

			message = "";
			return;
		}

		if (i == 14 && message.length() > 0) {
			message = message.substring(0, message.length() - 1);
		}

		if ((allowedCharacters.indexOf(c) >= 0 || c > ' ') && message.length() < (38)) {
			message += c;
		}
	}

	public void isModEnabled(boolean mod, String name) {
		addMsg(name + (mod ? " : \247aEnabled." : " : \247cDisabled."));
	}

	public void addMsg(String msg) {
		if(name.equalsIgnoreCase(mc.dp.settings.curIRCCHatName)) {
			int i;

			for(; mc.fontRenderer.getStringWidth(msg) > 230; msg = msg.substring(i)) {
				for(i = 1; i < msg.length() && mc.fontRenderer.getStringWidth(msg.substring(0, i + 1)) <= 230; i++) { }
				addMsg(msg.substring(0, i));
			}


			msgList.add(0, new ChatLineOld(msg, name));
			for(; msgList.size() > 50; msgList.remove(msgList.size() - 1)) { }
		}
	}

	public void drawScreen(int i, int j, float f) {
		drawBackground(0);
		Methods gui = new Methods();
		int x = (width / 2) - 123;
		int y = (height / 2) - 77;
		int w = (width / 2) + 126;
		int h = (height / 2) + 77;
		String thing = updateCounter / 6 % 2 != 0 ? "" : "_";
		gui.drawBorderedRect(x, y, w, h + 1, 0xFFA0A0A0, 0xFF000000);
		gui.drawBorderedRect(x, y, w, h - 142, 0xFFA0A0A0, 0xFF000000);
		fontRenderer.drawString(name, x + 2, y + 2, 0xFFFFFFFF);
		gui.drawBorderedRect(x + 2, h - 13, w - 2, h - 1, 0xFFA0A0A0, 0xFF000000);
		fontRenderer.drawString("> " + message + thing, x + 4, h - 11, 0xFFFFFFFF);
		byte maxMsgs = 14;

		for(int msgTotal = 0; msgTotal < msgList.size() && msgTotal < maxMsgs; msgTotal++) {
			int msgHeight = -msgTotal * 9;
			String s2 = ((ChatLineOld)msgList.get(msgTotal)).message;
			fontRenderer.drawString(s2, x + 4, (h - 23) + msgHeight, 0xFFFFFFFF);
		}

		super.drawScreen(i, j, f);
	}
	
	public void actionPerformed(GuiButton button) {
		switch(button.id) {
		case 0 :
			mc.displayGuiScreen(new IRCList());
			break;
		}
	}

	protected void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);

		if (k != 0) {
			return;
		}
		byte byte0 = 100;

		if (message.length() > byte0) {
			message = message.substring(0, byte0);
		}
	}

	static {
		allowedCharacters = ChatAllowedCharacters.allowedCharacters;
	}
}