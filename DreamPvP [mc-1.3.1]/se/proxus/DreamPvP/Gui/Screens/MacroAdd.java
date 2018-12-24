package se.proxus.DreamPvP.Gui.Screens;

import org.lwjgl.input.Keyboard;

import se.proxus.DreamPvP.DreamPvP;
import se.proxus.DreamPvP.Utils.Placeholders.Key;
import net.minecraft.src.*;

public class MacroAdd extends GuiScreen {
	
	protected GuiTextField textField1, textField2;
	public static int key;
	public static boolean focused;

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		
		textField1 = new GuiTextField(fontRenderer, width / 2 - 100, 76, 200, 20);
		textField2 = new GuiTextField(fontRenderer, width / 2 - 100, 116, 200, 20);
		
		controlList.add(new GuiButton(1, width / 2 - 100, 156, focused ? ">> ? <<" : "Key : " + Keyboard.getKeyName(key)));
		controlList.add(new GuiButton(2, width / 2 - 100, height - 36, "Done."));
	}
	
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		key = Keyboard.KEY_NONE;
	}
	
	@Override
	public void updateScreen() {
		textField1.updateCursorCounter();
		textField2.updateCursorCounter();
		
		controlList.clear();
		controlList.add(new GuiButton(1, width / 2 - 100, 156, focused ? ">> ? <<" : "Key : " + Keyboard.getKeyName(key)));
		controlList.add(new GuiButton(2, width / 2 - 100, height - 36, "Done."));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if(button.enabled) {
			if(button.id == 2 && !textField1.getText().isEmpty() && !textField2.getText().isEmpty()) {
				DreamPvP.settings.macroArray.add(new Key(textField1.getText(), textField2.getText(), key));
				DreamPvP.mc.displayGuiScreen(new MacroList());
				DreamPvP.files.saveMacros();
			} if(button.id == 1) {
				focused = true;
			} else if(focused) {
				focused = false;
			}
		}
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		drawBackground(0);
		textField1.drawTextBox();
		textField2.drawTextBox();
		
		drawCenteredString(fontRenderer, "Macro add.", width / 2, 10, 0xFFFFFFFF);
        drawString(fontRenderer, "Macro name.", width / 2 - 100, 63, 0xFFFFFFFF);
        drawString(fontRenderer, "Macro command.", width / 2 - 100, 104, 0xFFFFFFFF);
        drawString(fontRenderer, "Macro key.", width / 2 - 100, 144, 0xFFFFFFFF);
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
		if(focused && par2 != Keyboard.KEY_ESCAPE) {
			key = par2;
			focused = false;
		}
		
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