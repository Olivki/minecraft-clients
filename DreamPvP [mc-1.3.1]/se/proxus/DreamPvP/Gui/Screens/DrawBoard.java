package se.proxus.DreamPvP.Gui.Screens;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import se.proxus.DreamPvP.Utils.Placeholders.*;
import net.minecraft.src.*;

public class DrawBoard extends GuiScreen {

	public static ArrayList<u_DrawerFont> fontArray = new ArrayList<u_DrawerFont>();

	protected GuiTextField textField1;

	public static String text = "";

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		textField1 = new GuiTextField(fontRenderer, 4, height - 26, 200, 20);

		controlList.add(new GuiButton(2, width / 2 - 100, height - 26, "Done."));
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	public void drawScreen(int var1, int var2, float var3) {
		drawBackground(0);
		textField1.drawTextBox();
		mc.dp.ingame.drawBorderedRect(2, 2, width - 4, height - 40, 0xFF000000, 0xFFFFFFFF);

		if(mouseInRec(var1, var2)) {
			fontRenderer.drawStringWithShadow(text, var1, var2, 0x80FFFFFF);

			if(Mouse.isButtonDown(0)) {
				mc.dp.settings.drawBoardArray.add(new u_Drawer(var1, var2, var1 + 2, var2 + 2, 1.0F, 1.0F, 1.0F));

				if(!text.isEmpty()) {
					fontArray.add(new u_DrawerFont(text, var1, var2));
				}
			}
		}

		for(u_Drawer bDrawer : mc.dp.settings.drawBoardArray) {
			drawRect(bDrawer.x, bDrawer.y, bDrawer.x2, bDrawer.y2, 0xFF000000);
		}

		for(u_DrawerFont bDrawerFont : fontArray) {
			fontRenderer.drawStringWithShadow(bDrawerFont.text, bDrawerFont.x, bDrawerFont.y, 0xFFFFFFFF);
		}

		super.drawScreen(var1, var2, var3);
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		textField1.mouseClicked(par1, par2, par3);
	}

	@Override
	public void keyTyped(char var1, int var2) {
		super.keyTyped(var1, var2);

		textField1.textboxKeyTyped(var1, var2);
		text = textField1.getText();
	}

	@Override
	public void actionPerformed(GuiButton var1) {
		switch(var1.id) {
		case 2 :
			mc.displayGuiScreen(new Main());
			break;
		}
	}

	public boolean mouseInRec(int var1, int var2) {
		return var1 >= 2 && var2 >= 2 && var1 < width - 4 && var2 < height - 40;
	}
}