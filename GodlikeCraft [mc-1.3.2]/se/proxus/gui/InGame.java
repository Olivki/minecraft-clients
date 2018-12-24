package se.proxus.gui;

import java.util.ArrayList;

import se.proxus.utils.Popup;

public class InGame extends Methods {
	
	/** The variables. **/
	/** The ArrayList that all the hacks add onto. **/
	public static ArrayList<String> guiArray = new ArrayList<String>();
	
	/** Main methods. **/
	/** Handles the drawing of the most common stuff. **/
	public static void drawOverlay(float var1, int var2, int var3) {
		drawMain("§f" + crypt.decryptText("kh+$U(Lt#;p%") + " " + vars.curVersion, guiArray, var1, var2, var3);
	}
}