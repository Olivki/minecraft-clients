package se.proxus.gui;

import se.proxus.hacks.*;

public class InGame extends Methods {

	public void drawOverlay(int var1, int var2) {
		for(Base_Hack var3 : this.hacks.hackArray) {
			var3.onRendered2D(var1, var2);
		}
	}
}