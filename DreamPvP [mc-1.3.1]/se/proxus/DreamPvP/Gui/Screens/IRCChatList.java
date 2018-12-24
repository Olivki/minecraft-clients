package se.proxus.DreamPvP.Gui.Screens;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import org.lwjgl.input.Keyboard;

import se.proxus.DreamPvP.DreamPvP;
import net.minecraft.src.*;

public class IRCChatList extends GuiScreen {

	private IRCChatListSlot mSlot;
	protected GuiTextField textField1;

	@Override
	public void initGui() {
		controlList.add(new GuiButton(100, width / 2 - 100, height - 26, "Done."));
		textField1 = new GuiTextField(fontRenderer, width / 2 - 100, height - 48, 200, 20);

		mSlot = new IRCChatListSlot(this);
		mSlot.registerScrollButtons(controlList, 7, 8);
	}
	
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override
	public void updateScreen() {
		textField1.updateCursorCounter();
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if(button.enabled) {
			if(button.id == 0) {
				
			} if(button.id == 1) {
				
			} if(button.id == 2) {
               
			} if(button.id == 100) {
				mc.displayGuiScreen(new Main());
			} else {
				mSlot.actionPerformed(button);
			}
		}
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		mSlot.drawScreen(I1, I2, I3);
		textField1.drawTextBox();

		drawCenteredString(fontRenderer, "Alt list. [§e" + mc.dp.settings.altArray.size() + "§r]", width / 2, 10, 0xFFFFFFFF);
		fontRenderer.drawStringWithShadow("Username : " + mc.session.username, 4, 4, 0xFFFFFFFF);
		super.drawScreen(I1, I2, I3);
	}
	
	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		textField1.mouseClicked(par1, par2, par3);
	}
	
	@Override
	protected void keyTyped(char par1, int par2) {	
		textField1.textboxKeyTyped(par1, par2);
	}
}