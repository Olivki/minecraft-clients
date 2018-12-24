package Comet.Gui.Menu;

import java.util.ArrayList;

public class Clickable extends WindowBase {

	public static ArrayList menus;
	
	public Clickable() {
		menus = new ArrayList();
		
		menus.add(new m_Player());
		menus.add(new m_World());
		menus.add(new m_Settings());
		menus.add(new m_xRayGui());
	}

	public void drawScreen(int i, int j, float f) {			
		for(int l = 0; l < menus.size();l++) {
			WindowBase windows = (WindowBase)menus.get(l);
			windows.drawScreen(i, j, f);
		}
	}

	public void mouseClicked(int i, int j, int k) {
		for(int l = 0; l < menus.size();l++) {
			WindowBase windows = (WindowBase)menus.get(l);
			windows.mouseClicked(i, j, k);
		}
	}

	public void mouseMovedOrUp(int i, int j, int k) {
		for(int l = 0; l < menus.size();l++) {
			WindowBase windows = (WindowBase)menus.get(l);
			windows.mouseMovedOrUp(i, j, k);
		}
	}

	public boolean pinned() {
		WindowBase windows = null;
		for(int l = 0; l < menus.size();l++) {windows = (WindowBase)menus.get(l);}
		return windows.pinned();
	}
}
