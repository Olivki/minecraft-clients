package ab.Bytes.Gui.Menus;

import java.util.ArrayList;

public class Base_MenuList extends Base_Menu {

	public static ArrayList<Base_Menu> menuArray;

	public static ms_Player player = new ms_Player();
	public static ms_World world = new ms_World();
	public static ms_Nuker nuker = new ms_Nuker();

	public static Base_MenuList instance;
	
	public Base_MenuList() {
		instance = this;
		menuArray = new ArrayList<Base_Menu>();
		checkMenus();
	}

	public static void checkMenus() {
		menuArray.clear();
		menuArray.add(player);
		menuArray.add(world);
		menuArray.add(nuker);
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		drawDefaultBackground();
		for(Base_Menu menu : menuArray) {
			menu.drawScreen(I1, I2, I3);
		}
		bs.ingame.drawMouse(I1 + 1, I2);
	}

	@Override
	public void mouseClicked(int I1, int I2, int I3) {
		for(Base_Menu menu : menuArray) {
			menu.mouseClicked(I1, I2, I3);
		}
	}

	@Override
	public void mouseMovedOrUp(int I1, int I2, int I3) {
		for(Base_Menu menu : menuArray) {
			menu.mouseMovedOrUp(I1, I2, I3);
		}
	}

	@Override
	public void mouseDragged(int I1, int I2) {
		for(Base_Menu menu : menuArray) {
			menu.mouseDragged(I1, I2);
		}
	}

	@Override
	public boolean enabled() {
		Base_Menu bMenu = null;
		for(Base_Menu menu : menuArray) {
			bMenu = menu;
		}
		return bMenu.enabled();
	}

	@Override
	public boolean pinned() {
		Base_Menu bMenu = null;
		for(Base_Menu menu : menuArray) {
			bMenu = menu;
		}
		return bMenu.pinned();
	}
}