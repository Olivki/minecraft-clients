package ab.Bytes.Gui.Menus;

import java.util.ArrayList;

import ab.Bytes.Mods.*;

import net.minecraft.src.*;

public class ms_Player extends Base_Menu {

	protected ArrayList<Base_Button> buttons;

	protected Base_ModList mList = new Base_ModList();

	protected static boolean
	enabled = false,
	pinned = false,
	dragged = false;

	public static int
	dragX = 2,
	dragY = 0,
	startX,
	startY;	

	protected Base_Button curButton;

	public ms_Player() {
		buttons = new ArrayList<Base_Button>();
		checkButtons();
	}

	protected void checkButtons() {
		buttons.clear();

		buttons.add(new Base_Label(0, "Player", dragX, dragY, enabled, buttons, 22));
		buttons.add(new Base_SmallButton(1, dragX + 80, dragY + 2, enabled));
		buttons.add(new Base_SmallButton(2, dragX + 69, dragY + 2, pinned));

		if(enabled) {
			buttons.add(new Base_Button(3, "Sneak", dragX, dragY + 15, mList.sneak.isEnabled()));
			buttons.add(new Base_Button(4, "Fly", dragX, dragY + 30, mList.fly.isEnabled()));
			buttons.add(new Base_Button(5, "NoFall", dragX, dragY + 45, mList.nofall.isEnabled()));
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
		case 3 : {mList.sneak.toggle(); {break;}}
		case 4 : {mList.fly.toggle(); {break;}}
		case 5 : {mList.nofall.toggle(); {break;}}
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