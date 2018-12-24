package Comet.Gui;

import java.util.List;

import Comet.Utils.Settings;
import net.minecraft.client.*;
import net.minecraft.src.*;

public class Keybinds extends GuiScreen {
	/**
	 * A reference to the screen object that created this. Used for navigating between screens.
	 */
	private GuiScreen parentScreen;

	/** The title string that is displayed in the top-center of the screen. */
	protected String screenTitle;

	/** Reference to the GameSettings object. */
	private Settings options;

	/** The ID of the  button that has been pressed. */
	private int buttonId;

	public Keybinds(GuiScreen par1GuiScreen, Settings par2GameSettings)
	{
		screenTitle = "Keybinds";
		buttonId = -1;
		parentScreen = par1GuiScreen;
		options = par2GameSettings;
	}

	private int func_20080_j()
	{
		return width / 2 - 155;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	public void initGui() {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		int i = func_20080_j();

		for (int j = 0; j < options.keys.length; j++)
		{
			controlList.add(new GuiSmallButton(j, i + (j % 2) * 160, height / 6 + 24 * (j >> 1), 70, 20, options.getKeysOptionDisplayString(j)));
		}

		controlList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168, stringtranslate.translateKey("gui.done")));
		screenTitle = stringtranslate.translateKey("Keybinds");
	}

	/**
	 * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
	 */
	protected void actionPerformed(GuiButton par1GuiButton) {
		for (int i = 0; i < options.keys.length; i++)
		{
			((GuiButton)controlList.get(i)).displayString = options.getKeysOptionDisplayString(i);
		}

		if (par1GuiButton.id == 200)
		{
			mc.displayGuiScreen(parentScreen);
			options.saveKeys();
		}
		else
		{
			buttonId = par1GuiButton.id;
			par1GuiButton.displayString = (new StringBuilder()).append("> ").append(options.getKeysOptionDisplayString(par1GuiButton.id)).append(" <").toString();
			options.saveKeys();
		}
	}

	/**
	 * Called when the mouse is clicked.
	 */
	protected void mouseClicked(int par1, int par2, int par3)
	{
		if (buttonId >= 0)
		{
			options.setKeys(buttonId, -100 + par3);
			((GuiButton)controlList.get(buttonId)).displayString = options.getKeysOptionDisplayString(buttonId);
			buttonId = -1;
			KeyBinding.resetKeyBindingArrayAndHash();
		}
		else
		{
			super.mouseClicked(par1, par2, par3);
		}
	}

	/**
	 * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
	 */
	protected void keyTyped(char par1, int par2)
	{
		if (buttonId >= 0)
		{
			options.setKeys(buttonId, par2);
			((GuiButton)controlList.get(buttonId)).displayString = options.getKeysOptionDisplayString(buttonId);
			buttonId = -1;
			KeyBinding.resetKeyBindingArrayAndHash();
		}
		else
		{
			super.keyTyped(par1, par2);
		}
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, screenTitle, width / 2, 20, 0xffffff);
		int i = func_20080_j();

		for (int j = 0; j < options.keys.length; j++)
		{
			boolean flag = false;
			int k = 0;

			do
			{
				if (k >= options.keys.length)
				{
					break;
				}

				if (k != j && options.keys[j].keyCode == options.keys[k].keyCode)
				{
					flag = true;
					break;
				}

				k++;
			}
			while (true);

			k = j;

			if (buttonId == j)
			{
				((GuiButton)controlList.get(k)).displayString = "\247f> \247e? \247f<";
			}
			else if (flag)
			{
				((GuiButton)controlList.get(k)).displayString = (new StringBuilder()).append("\247c").append(options.getKeysOptionDisplayString(k)).toString();
			}
			else
			{
				((GuiButton)controlList.get(k)).displayString = options.getKeysOptionDisplayString(k);
			}

			drawString(fontRenderer, options.getKeysDescription(j), i + (j % 2) * 160 + 70 + 6, height / 6 + 24 * (j >> 1) + 7, -1);
		}

		super.drawScreen(par1, par2, par3);
	}
}
