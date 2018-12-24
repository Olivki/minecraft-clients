package Comet.Gui;

import java.util.List;

import Comet.Utils.Settings;
import net.minecraft.client.*;
import net.minecraft.src.*;

public class Macros extends GuiScreen {
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

	public Macros(GuiScreen par1GuiScreen, Settings par2GameSettings)
	{
		screenTitle = "Macros";
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

		for (int j = 0; j < options.macros.length; j++)
		{
			controlList.add(new GuiSmallButton(j, i + (j % 2) * 160, height / 6 + 24 * (j >> 1), 70, 20, options.getMacrosOptionDisplayString(j)));
		}

		controlList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168, stringtranslate.translateKey("gui.done")));
		screenTitle = stringtranslate.translateKey("Macros");
	}

	/**
	 * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
	 */
	protected void actionPerformed(GuiButton par1GuiButton) {
		for (int i = 0; i < options.macros.length; i++)
		{
			((GuiButton)controlList.get(i)).displayString = options.getMacrosOptionDisplayString(i);
		}

		if (par1GuiButton.id == 200)
		{
			mc.displayGuiScreen(parentScreen);
			options.saveMacros();
		}
		else
		{
			buttonId = par1GuiButton.id;
			par1GuiButton.displayString = (new StringBuilder()).append("> ").append(options.getMacrosOptionDisplayString(par1GuiButton.id)).append(" <").toString();
			options.saveMacros();
		}
	}

	/**
	 * Called when the mouse is clicked.
	 */
	protected void mouseClicked(int par1, int par2, int par3)
	{
		if (buttonId >= 0)
		{
			options.setMacros(buttonId, -100 + par3);
			((GuiButton)controlList.get(buttonId)).displayString = options.getMacrosOptionDisplayString(buttonId);
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
			options.setMacros(buttonId, par2);
			((GuiButton)controlList.get(buttonId)).displayString = options.getMacrosOptionDisplayString(buttonId);
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

		for (int j = 0; j < options.macros.length; j++)
		{
			boolean flag = false;
			int k = 0;

			do
			{
				if (k >= options.macros.length)
				{
					break;
				}

				if (k != j && options.macros[j].keyCode == options.macros[k].keyCode)
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
				((GuiButton)controlList.get(k)).displayString = (new StringBuilder()).append("\247c").append(options.getMacrosOptionDisplayString(k)).toString();
			}
			else
			{
				((GuiButton)controlList.get(k)).displayString = options.getMacrosOptionDisplayString(k);
			}

			drawString(fontRenderer, options.getMacrosDescription(j), i + (j % 2) * 160 + 70 + 6, height / 6 + 24 * (j >> 1) + 7, -1);
		}

		super.drawScreen(par1, par2, par3);
	}
}
