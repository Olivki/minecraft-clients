package se.proxus.hacks;

import java.util.*;

import org.lwjgl.input.Keyboard;

import se.proxus.*;
import se.proxus.hacks.hacks.*;

public class Base_Hacks extends Main {

	public static ArrayList<Base_Hack> hackArray = new ArrayList<Base_Hack>();

	public static h_Gui 
	HACK_GUI = new h_Gui();
	
	public static h_NoFall
	HACK_NOFALL = new h_NoFall();
	
	public static h_Miner
	HACK_MINER = new h_Miner();
	
	public static h_xRay
	HACK_XRAY = new h_xRay();
	
	public static h_Brightness
	HACK_BRIGHTNESS = new h_Brightness();
	
	public static h_Panels
	HACK_PANELS = new h_Panels();
	
	public static h_Kill_aura
	HACK_KILL_AURA = new h_Kill_aura();

	public static void addHack(Base_Hack var1) {
		if(!(hackArray.contains(var1))) {
			hackArray.add(var1);
			utils.log("[Hacks] Added the hack \"" + var1.getName() + "\" to the hack array.");
		} else {
			utils.log("[Hacks] The hack array already contains the hack \"" + var1.getName() + "\".");
		}
	}

	public static void delHack(Base_Hack var1) {
		if((hackArray.contains(var1))) {
			hackArray.remove(var1);
			utils.log("[Hacks] Removed the hack \"" + var1.getName() + "\" from the hack array.");
		} else {
			utils.log("[Hacks] The hack array does not contain the hack \"" + var1.getName() + "\".");
		}
	}

	public static Base_Hack getHack(String var1) {
		Base_Hack var2 = null;

		for(Base_Hack var3 : hackArray) {
			if(var3.getName().equalsIgnoreCase(var1)) {
				var2 = var3;
				utils.log("[Hacks] Found hack, String : \"" + var1 + "\", Class \"" + var2.getClass().getName() + "\".");
			}
		}

		return var2;
	}

	public static void onUpdate() {
		for(Base_Hack var1 : hackArray) {
			var1.onUpdate();
		}
	}
	
	public static void onPostMotion() {
		for(Base_Hack var1 : hackArray) {
			var1.onPostMotion();
		}
	}

	public static void onRendered3D() {
		for(Base_Hack var1 : hackArray) {
			var1.onRendered3D();
		}
	}
	
	public static void onRendered2D(int var1, int var2) {
		for(Base_Hack var3 : hackArray) {
			var3.onRendered2D(var1, var2);
		}
	}

	public static void checkKeys() {
		for(Base_Hack var1 : hackArray) {
			if(Keyboard.getEventKey() == Keyboard.getKeyIndex(var1.getKey())) {
				var1.toggle();
				playSound(var1.getState());
			}
		}
	}

	public static void playSound(boolean var1) {
		mc.sndManager.playSoundFX("random.click", 1.0F, var1 ? 0.6F : 1.1F);
	}
}