package se.proxus.DreamPvP.Gui.Screens;

import org.lwjgl.input.Keyboard;

import se.proxus.DreamPvP.DreamPvP;
import se.proxus.DreamPvP.Gui.Screens.Utils.PassField;
import se.proxus.DreamPvP.Utils.Placeholders.Key;
import se.proxus.DreamPvP.Utils.Placeholders.u_Alt;
import net.minecraft.src.*;

public class AltAdd extends GuiScreen {

	protected GuiTextField textField1;
	protected PassField textField2;
	public static int key;
	public static boolean focused;

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);

		textField1 = new GuiTextField(fontRenderer, width / 2 - 100, 76, 200, 20);
		textField2 = new PassField(fontRenderer, width / 2 - 100, 116, 200, 20);

		controlList.add(new GuiButton(2, width / 2 - 100, height - 58, "Done."));
		controlList.add(new GuiButton(3, width / 2 - 100, height - 36, "Cancel."));
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	public void updateScreen() {
		textField1.updateCursorCounter();
		textField2.updateCursorCounter();
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if(button.enabled) {
			if(button.id == 2 && !textField1.getText().isEmpty() && !textField2.getText().isEmpty()) {
				DreamPvP.settings.altArray.add(new u_Alt(textField1.getText(), textField2.getText()));
				DreamPvP.mc.displayGuiScreen(new AltList(new Main()));
				DreamPvP.files.saveAlts();
			} if(button.id == 3) {
				DreamPvP.mc.displayGuiScreen(new AltList(new Main()));
				DreamPvP.files.saveAlts();
			}
		}
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		drawBackground(0);
		textField1.drawTextBox();
		textField2.drawTextBox();

		drawCenteredString(fontRenderer, "Alt list add.", width / 2, 10, 0xFFFFFFFF);
		drawString(fontRenderer, "Username.", width / 2 - 100, 63, 0xFFFFFFFF);
		drawString(fontRenderer, "Password.", width / 2 - 100, 104, 0xFFFFFFFF);
		super.drawScreen(I1, I2, I3);
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		textField1.mouseClicked(par1, par2, par3);
		textField2.mouseClicked(par1, par2, par3);
	}

	@Override
	protected void keyTyped(char par1, int par2) {	
		textField1.textboxKeyTyped(par1, par2);
		textField2.textboxKeyTyped(par1, par2);

		if (par1 == '\t') {
			if (textField1.isFocused()) {
				textField1.setFocused(false);
				textField2.setFocused(true);
			} else {
				textField1.setFocused(true);
				textField2.setFocused(false);
			}
		}
	}
}