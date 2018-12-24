package se.proxus.DreamPvP.Gui.Screens;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import se.proxus.DreamPvP.DreamPvP;
import se.proxus.DreamPvP.Gui.Methods;
import se.proxus.DreamPvP.Gui.Screens.Sliders.s_WaypointB;
import se.proxus.DreamPvP.Gui.Screens.Sliders.s_WaypointG;
import se.proxus.DreamPvP.Gui.Screens.Sliders.s_WaypointR;
import se.proxus.DreamPvP.Utils.Placeholders.Key;
import se.proxus.DreamPvP.Utils.Placeholders.u_Waypoint;
import net.minecraft.src.*;

public class WaypointAdd extends GuiScreen {
	
	protected GuiTextField textField1, textField2;
	public static int key;
	public static boolean focused;

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		
		textField1 = new GuiTextField(fontRenderer, width / 2 - 100, 40, 200, 20);
		textField2 = new GuiTextField(fontRenderer, width / 2 - 100, 76, 200, 20);
		
		textField2.setText((int)mc.thePlayer.posX + ", " + (int)mc.thePlayer.posY + ", " + (int)mc.thePlayer.posZ);
		
		controlList.add(new GuiButton(2, width / 2 - 100, height - 32, "Done."));
		
		controlList.add(new s_WaypointR(5, width / 2 - 100, 110, mc.dp.settings.wR / 1.0F));
		controlList.add(new s_WaypointG(6, width / 2 - 100, 134, mc.dp.settings.wG / 1.0F));
		controlList.add(new s_WaypointB(7, width / 2 - 100, 158, mc.dp.settings.wB / 1.0F));
	}
	
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
	
	public int getPosX(String s) {
		String[] split = s.split(", ");
		
		return Integer.parseInt(split[0]);
	}
	
	public int getPosY(String s) {
		String[] split = s.split(", ");
		
		return Integer.parseInt(split[1]);
	}
	
	public int getPosZ(String s) {
		String[] split = s.split(", ");
		
		return Integer.parseInt(split[2]);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		String text1 = textField1.getText();
		String text2 = textField2.getText();
		
		if(button.enabled) {
			if(button.id == 2 && !textField1.getText().isEmpty() && !textField2.getText().isEmpty()) {
				String ip = mc.dp.settings.curServerIP;
				DreamPvP.settings.waypointArray.add(new u_Waypoint(getPosX(text2), getPosY(text2), getPosZ(text2), mc.dp.settings.wR, mc.dp.settings.wG, mc.dp.settings.wB, ip, text1));
				DreamPvP.mc.displayGuiScreen(new WaypointList());
				
				mc.dp.settings.wR = 1.0F;
				mc.dp.settings.wG = 1.0F;
				mc.dp.settings.wB = 1.0F;
				mc.dp.files.saveWaypoints();
			}
		}
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		Methods m = mc.dp.ingame;
		float r = mc.dp.settings.wR, g = mc.dp.settings.wG, b = mc.dp.settings.wB;
		drawBackground(0);
		textField1.drawTextBox();
		textField2.drawTextBox();
		
		drawCenteredString(fontRenderer, "Waypoint add.", width / 2, 10, 0xFFFFFFFF);
        drawString(fontRenderer, "Waypoint name.", width / 2 - 100, 26, 0xFFFFFFFF);
        drawString(fontRenderer, "Waypoint position (Splitted by ', ').", width / 2 - 100, 64, 0xFFFFFFFF);
        drawString(fontRenderer, "Waypoint RGB settings.", width / 2 - 100, 100, 0xFFFFFFFF);
		m.drawCenteredString(fontRenderer, textField1.getText(), width / 2, 182, 0xFFFFFFFF);
		m.drawFilledCircleRGB(width / 2, 198, 6, r, g, b);
		m.drawCircleRGB(width / 2, 198, 6, r - 0.3F, g - 0.3F,  b - 0.3F);
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