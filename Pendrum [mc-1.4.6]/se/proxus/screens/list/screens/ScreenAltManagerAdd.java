package se.proxus.screens.list.screens;

import java.io.File;

import org.lwjgl.input.Keyboard;

import se.proxus.Pendrum;
import se.proxus.screens.utils.GuiPassword;
import se.proxus.utils.placeholders.PlaceholderAlt;
import net.minecraft.src.*;

public class ScreenAltManagerAdd extends GuiScreen {

	protected GuiTextField textField1;

	protected GuiPassword textField2;

	public GuiScreen parentScreen = null;

	public ScreenAltManagerAdd(GuiScreen parentScreen) {
		this.parentScreen = parentScreen;
	}

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);

		this.textField1 = new GuiTextField(fontRenderer, width / 2 - 100, 76, 200, 20);
		
		this.textField2 = new GuiPassword(fontRenderer, width / 2 - 100, 116, 200, 20);

		this.controlList.add(new GuiButton(2, width / 2 - 100, height - 58, "Add"));
		
		this.controlList.add(new GuiButton(3, width / 2 - 100, height - 36, "Cancel."));
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	public void updateScreen() {
		this.textField1.updateCursorCounter();
		
		this.textField2.updateCursorCounter();
	}

	@Override
	protected void actionPerformed(GuiButton var0) {
		switch(var0.id) {
		case 2:
			if(!(this.textField1.getText().isEmpty())) {
				Pendrum.sett.altArray.add(new PlaceholderAlt(textField1.getText(), textField2.getText()));
				Pendrum.wrapper.getMinecraft().displayGuiScreen(new ScreenAltManager(this.parentScreen));
				Pendrum.sett.saveAlts(new File(this.mc.pm.mainFolder, "Alts.cfg"));
			}
			break;

		case 3:
			Pendrum.wrapper.getMinecraft().displayGuiScreen(new ScreenAltManager(this.parentScreen));
			Pendrum.sett.saveAlts(new File(this.mc.pm.mainFolder, "Alts.cfg"));
			break;
		}
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		this.drawBackground(0);
		
		this.textField1.drawTextBox();
		
		this.textField2.drawTextBox();

		this.drawCenteredString(fontRenderer, "Ad alt.", this.width / 2, 10, 0xFFFFFFFF);
		this.drawString(this.fontRenderer, "Username.", this.width / 2 - 100, 63, 0xFFFFFFFF);
		this.drawString(this.fontRenderer, "Password.", this.width / 2 - 100, 104, 0xFFFFFFFF);
		super.drawScreen(I1, I2, I3);
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		
		this.textField1.mouseClicked(par1, par2, par3);
		
		this.textField2.mouseClicked(par1, par2, par3);
	}

	@Override
	protected void keyTyped(char par1, int par2) {	
		this.textField1.textboxKeyTyped(par1, par2);
		
		this.textField2.textboxKeyTyped(par1, par2);

		if(par1 == '\t') {
			if(this.textField1.isFocused()) {
				this.textField1.setFocused(false);
				this.textField2.setFocused(true);
			} else {
				this.textField1.setFocused(true);
				this.textField2.setFocused(false);
			}
		}
	}
}