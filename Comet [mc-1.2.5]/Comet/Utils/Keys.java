package Comet.Utils;

import Comet.*;
import Comet.Gui.Menu.*;
import Comet.Mods.*;
import net.minecraft.client.*;

public class Keys {

	public static Minecraft mc = Minecraft.theMinecraft;
	public static Settings settings = new Settings();
	public static Utils utils = new Utils();

	public static m_Sneak sneak = new m_Sneak();
	public static m_NoFall nofall = new m_NoFall();
	public static m_ClickTp clicktp = new m_ClickTp();
	public static m_Step step = new m_Step();
	public static m_KillAura killaura = new m_KillAura();
	public static m_Miner miner = new m_Miner();
	public static m_InstantMine instant = new m_InstantMine();
	public static m_Fly fly = new m_Fly();
	public static m_ServerSpeed serverspeed = new m_ServerSpeed();
	public static m_Bright bright = new m_Bright();
	public static m_xRay xray = new m_xRay();

	public static void checkKeys() {
		if(settings.keySneak.pressed) {sneak.toggle(); playSound(sneak.isRunning());}
		if(settings.keyNoFall.pressed) {nofall.toggle(); playSound(nofall.isRunning());}
		if(settings.keyKillAura.pressed) {killaura.toggle(); playSound(killaura.isRunning());}
		if(settings.keyMiner.pressed) {miner.toggle(); playSound(miner.isRunning());}
		if(settings.keyInstantMine.pressed) {instant.toggle(); playSound(instant.isRunning());}
		if(settings.keyFly.pressed) {fly.toggle(); playSound(fly.isRunning());}
		if(settings.keyBright.pressed) {bright.toggle(); playSound(bright.isRunning());}
		if(settings.keyxRay.pressed) {xray.toggle(); playSound(xray.isRunning());}
		if(settings.keyMenu.pressed) {mc.displayGuiScreen(new Clickable());}
		
		if(settings.macro1.pressed) {utils.sendChat(settings.macroMsg[0]);}
		if(settings.macro2.pressed) {utils.sendChat(settings.macroMsg[1]);}
		if(settings.macro3.pressed) {utils.sendChat(settings.macroMsg[2]);}
		if(settings.macro4.pressed) {utils.sendChat(settings.macroMsg[3]);}
		if(settings.macro5.pressed) {utils.sendChat(settings.macroMsg[4]);}
		if(settings.macro6.pressed) {utils.sendChat(settings.macroMsg[5]);}
		if(settings.macro7.pressed) {utils.sendChat(settings.macroMsg[6]);}
		if(settings.macro8.pressed) {utils.sendChat(settings.macroMsg[7]);}
		if(settings.macro9.pressed) {utils.sendChat(settings.macroMsg[8]);}
		if(settings.macro10.pressed) {utils.sendChat(settings.macroMsg[9]);}
		if(settings.macro11.pressed) {utils.sendChat(settings.macroMsg[10]);}
		if(settings.macro12.pressed) {utils.sendChat(settings.macroMsg[11]);}
		if(settings.macro13.pressed) {utils.sendChat(settings.macroMsg[12]);}
		if(settings.macro14.pressed) {utils.sendChat(settings.macroMsg[13]);}
	}

	public static void playSound(boolean f) {
		mc.sndManager.playSoundFX("random.click", 1.0F, f ? 0.7F : 1.1F);
	}

}
