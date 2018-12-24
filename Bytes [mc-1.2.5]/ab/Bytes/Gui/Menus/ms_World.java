package ab.Bytes.Gui.Menus;

import java.util.ArrayList;

import ab.Bytes.Mods.*;

import net.minecraft.src.*;

public class ms_World extends Base_Menu {

	protected ArrayList<Base_Button> buttons;

	protected Base_ModList mList = new Base_ModList();

	protected static boolean
	enabled = false,
	pinned = false,
	dragged = false;

	protected static int
	dragX = 97,
	dragY = 0,
	startX,
	startY;	

	protected Base_Button curButton;

	public ms_World() {
		buttons = new ArrayList<Base_Button>();
		checkButtons();
	}

	protected void checkButtons() {
		buttons.clear();

		buttons.add(new Base_Label(0, "World", dragX, dragY, enabled, buttons, 19));
		buttons.add(new Base_SmallButton(1, dragX + 80, dragY + 2, enabled));
		buttons.add(new Base_SmallButton(2, dragX + 69, dragY + 2, pinned));

		if(enabled) {
			buttons.add(new Base_Button(3, "Miner", dragX, dragY + 15, mList.miner.isEnabled()));
			buttons.add(new Base_Button(4, "Auto miner", dragX, dragY + 30, mList.autominer.isEnabled()));
			buttons.add(new Base_Button(5, "Bright", dragX, dragY + 45, mList.bright.isEnabled()));
			buttons.add(new Base_Button(6, "xRay", dragX, dragY + 60, mList.xray.isEnabled()));
		}
	}

	protected void buttonClicked(Base_Button button, int I1, int I2) {
		switch(button.id) {
		case 0 : {
			dragged = true;
			startX = (I1) - dragX;
			startY = (I2) - dragY;
			{break;}
		}
		case 1 : {enabled =! enabled; {break;}}
		case 2 : {pinned =! pinned; {break;}}
		case 3 : {mList.miner.toggle(); {break;}}
		case 4 : {mList.autominer.toggle(); {break;}}
		case 5 : {mList.bright.toggle(); {break;}}
		case 6 : {mList.xray.toggle(); {break;}}
		}
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		FontRenderer fr = bs.mc.fontRenderer;
		checkButtons();
		mouseDragged(I1, I2);

		for(Base_Button button : buttons) {
			button.drawButton(I1, I2);
		}
	}

	@Override
	public void mouseClicked(int I1, int I2, int I3) {		
		for(Base_Button button : buttons) {
			if(button.mousePressed(I1, I2)) {
				buttonClicked(button, I1, I2);
				playSound();
				curButton = button;
				Base_MenuList mList = new Base_MenuList();
			}
		}
	}

	@Override
	public void mouseMovedOrUp(int I1, int I2, int I3) {
		if(I3 == 0) {
			dragged = false;
		}
	}

	@Override
	public void mouseDragged(int I1, int I2) {
		if(dragged) {
			dragX = (I1 - startX);
			dragY = (I2 - startY);
		}
	}

	@Override
	public boolean enabled() {
		return enabled;
	}

	@Override
	public boolean pinned() {
		return pinned;
	}
}