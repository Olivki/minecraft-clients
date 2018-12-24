package se.proxus.hacks;

import java.io.File;
import java.util.*;

import org.lwjgl.input.Keyboard;

import se.proxus.*;

public final class Base_Hacks extends GodlikeCraft {

	/** The variables. **/
	/** The ArrayList that holds the hacks. **/
	public static ArrayList<Base_Hack> hackArray = new ArrayList<Base_Hack>();

	/** The hacks. **/
	public Base_Hack 
	sneak = null,
	step = null,
	aura = null,
	antiKnockback = null,
	crits = null,
	sprint = null,
	autoSoup = null,
	kitPvPMode = null,
	autoTool = null,
	panels = null,
	fly = null,
	noCheat = null,
	emo = null,
	noFall = null,
	tracer = null,
	miner = null,
	flood = null,
	breadcrumb = null,
	brightness = null,
	xray = null,
	glide = null,
	chestESP = null,
	console = null,
	mobAura = null,
	bowAimbot = null;

	public Base_Hacks() {
		initHacks();
	}

	public void initHacks() {
		addHack(sneak = new h_Sneak());
		addHack(step = new h_Step());
		addHack(aura = new h_Aura());
		addHack(antiKnockback = new h_Anti_Knockback());
		addHack(crits = new h_Crits());
		addHack(sprint = new h_Sprint());
		addHack(autoSoup = new h_Auto_Soup());
		addHack(kitPvPMode = new h_KitPvP_Mode());
		addHack(autoTool = new h_Auto_Tool());
		addHack(panels = new h_Panel());
		addHack(fly = new h_Fly());
		addHack(noCheat = new h_NoCheat());
		addHack(emo = new h_Emo());
		addHack(noFall = new h_NoFall());
		addHack(tracer = new h_Tracer());
		addHack(miner = new h_Miner());
		addHack(flood = new h_Flood());
		addHack(breadcrumb = new h_Breadcrumb());
		addHack(brightness = new h_Brightness());
		addHack(xray = new h_xRay());
		addHack(glide = new h_Glide());
		addHack(chestESP = new h_Chest_ESP());
		addHack(console = new h_Console());
		addHack(mobAura = new h_Mob_Aura());
		addHack(bowAimbot = new h_Bow_Aimbot());
	}

	/**
	 * The method for adding hacks.
	 * @param var1 - The hack to be added to the hackArray.
	 **/
	public void addHack(Base_Hack var1) {
		if(!hackArray.contains(var1)) {
			hackArray.add(var1);
			utils.log("[Hacks] Added the hack \"" + var1.getName() + "\" to the hack array.");
		} else {
			utils.log("[Hacks] The hack array already contains the hack \"" + var1.getName() + "\".");
		}
	}

	/**
	 * The method for adding hacks.
	 * @param var1 - The hack to be added to the hackArray.
	 **/
	public void delHack(Base_Hack var1) {
		if(hackArray.contains(var1)) {
			hackArray.remove(hackArray.indexOf(var1));
			utils.log("[Hacks] Removed the hack \"" + var1.getName() + "\" from the hack array.");
		} else {
			utils.log("[Hacks] The hack array does not contain the hack \"" + var1.getName() + "\".");
		}
	}

	/**
	 * The method for checking the keybinds.
	 * @param var1 - The key being pressed.
	 **/
	public static void checkKeys(int var1) {
		try {
			for(Base_Hack var2 : hackArray) {
				if(var1 == var2.getKey()) {
					var2.toggle();
					var2.playSound(var2.getState());
				}
			}
		} catch (Exception e) {
			vars.addError("Error on checking keys. [" + e.getMessage() + "]");
			files.saveBaseFile(new File(files.baseFolder, "Errors.txt"), vars.errorArray, true);
		}
	}

	/**
	 * The method for activating the method onUpdate() inside Base_Hack.java on every tick.
	 **/
	public static void onUpdate() {
		try {
			for(Base_Hack var1 : hackArray) {
				var1.onUpdate();
			}
		} catch (Exception e) {
			vars.addError("Error on update method. [" + e.getMessage() + "]");
			files.saveBaseFile(new File(files.baseFolder, "Errors.txt"), vars.errorArray, true);
		}
	}

	/**
	 * The method for activating the method onRendered3D() inside Base_Hack.java on every render tick.
	 **/
	public static void onRendered3D() {
		try {
			for(Base_Hack var1 : hackArray) {
				var1.onRendered3D();
			}
		} catch (Exception e) {
			vars.addError("Error on rendering 3D objects. [" + e.getMessage() + "]");
			files.saveBaseFile(new File(files.baseFolder, "Errors.txt"), vars.errorArray, true);
		}
	}
}