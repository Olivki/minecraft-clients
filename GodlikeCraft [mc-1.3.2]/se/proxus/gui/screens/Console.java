package se.proxus.gui.screens;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import se.proxus.commands.Base_Command;
import se.proxus.gui.screens.utils.ConsoleButton;
import se.proxus.panels.Base_Panels;
import se.proxus.utils.StringUtil;

import net.minecraft.src.*;

public class Console extends GuiScreen {

	public static ArrayList<ConsoleButton> buttonArray = new ArrayList<ConsoleButton>();
	public static ArrayList<String> consoleArray = new ArrayList<String>();
	public static ArrayList<Base_Command> preArray = new ArrayList<Base_Command>();
	public static ArrayList<String> activePrecmds = new ArrayList<String>();
	public static int index = 0;

	public static String text = "";

	public static int updateCounter = 0;

	public Console() {
		text = "";
	}

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		text = "";
	}

	@Override
	public void updateScreen() {
		updateCounter++;
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	public void drawScreen(int var1, int var2, float var3) {
		ScaledResolution var4 = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);

		for(ConsoleButton var6 : buttonArray) {
			var6.draw(var1, var2);
		}

		mc.dm.ingame.drawBorderedRect(0, 0, var4.getScaledWidth(), 96, 0xFF000000, 0x40000000);
		mc.dm.ingame.drawBorderedRect(0, 84, var4.getScaledWidth(), 96, 0xFF000000, 0x40000000);

		for(int var5 = index; var5 < index + consoleArray.size() && var5 < 9; var5++) {
			Base_Panels.ttf.drawStringS("§f" + (String)consoleArray.get(var5), 2, 72 + -var5 * 9);
		}

		if(text.isEmpty()) {
			buttonArray.clear();
		}

		Set s = mc.dm.cmds.precmds.entrySet();
		Iterator i1 = s.iterator();

		Base_Panels.ttf.drawStringS("§7" + mc.dm.cmds.getPrediction(text), 2, 84);
		Base_Panels.ttf.drawStringS("§f" + text.toLowerCase() + ((updateCounter / 6) % 2 != 0 ? "" : "_"), 2, 84);
	}

	@Override
	public void keyTyped(char var1, int var2) {
		super.keyTyped(var1, var2);

		if(var2 == 28) {
			String s = text.trim();

			if (s.length() > 0) {
				String s1 = text.trim();
				addMessage("> " + s1.toLowerCase());
				mc.dm.cmds.runCommands(text.toLowerCase());
			}

			text = "";
			return;
		}

		if(var2 == 14 && text.length() > 0) {
			text = text.substring(0, text.length() - 1);
		}

		if((ChatAllowedCharacters.allowedCharacters.indexOf(var1) >= 0 || var1 > ' ') && text.length() < 55) {
			text += var1;
		}
	}

	@Override
	public void mouseClicked(int var1, int var2, int var3) {
		for(ConsoleButton var4 : this.buttonArray) {
			if(var4.mouseInRec(var1, var2)) {
				this.mc.dm.utils.playSound();
				this.buttonPerformed(var4);
			}
		}
	}

	public void handleMouseInput() {
		try {
			super.handleMouseInput();
			int i = Mouse.getEventDWheel();

			if (i != 0) {
				if (i > 1) {
					i = 1;
				}

				if (i < -1) {
					i = -1;
				}

				if (!isShiftKeyDown()) {
					i *= 7;
				}


				index -= i;
				if(index <= 0) {
					index = 0;
				} if(index > consoleArray.size() - 16) {
					index = consoleArray.size() - 16;
				}
			}
		} catch (Exception e) {

		}
	}

	public void buttonPerformed(ConsoleButton var1) {
		for(int var6 = 0; var6 < mc.dm.cmds.cmdObjectArray.size(); var6++) {
			if(var1.id == var6) {

			}
		}
	}

	public void addMessage(String var1) {
		consoleArray.add(0, var1);
	}
}