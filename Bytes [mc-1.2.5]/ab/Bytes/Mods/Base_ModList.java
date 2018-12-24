package ab.Bytes.Mods;

import java.util.ArrayList;

import ab.Bytes.Utils.Utils;

public final class Base_ModList {
	
	public static ArrayList<Base_Mod> modArray = new ArrayList<Base_Mod>();
	
	public static m_Sneak sneak = new m_Sneak();
	public static m_Aura aura = new m_Aura();
	public static m_Fly fly = new m_Fly();
	public static m_xRay xray = new m_xRay();
	public static m_Bright bright = new m_Bright();
	public static m_Miner miner = new m_Miner();
	public static m_AutoMiner autominer = new m_AutoMiner();
	public static m_AutoEat autoeat = new m_AutoEat();
	public static m_NoFall nofall = new m_NoFall();
	public static m_SurvivalNuker survivalnuker = new m_SurvivalNuker();
	public static m_CreativeNuker creativenuker = new m_CreativeNuker();
	public static m_Smasher smasher = new m_Smasher();
	
	public Base_ModList() {
		checkMods();
	}
	
	public static void checkMods() {
		modArray.clear();
		modArray.add(sneak);
		modArray.add(aura);
		modArray.add(fly);
		modArray.add(xray);
		modArray.add(bright);
		modArray.add(miner);
		modArray.add(autominer);
		modArray.add(autoeat);
		modArray.add(nofall);
		modArray.add(survivalnuker);
		modArray.add(creativenuker);
		modArray.add(smasher);
	}
	
	public static void onModKeyEvent() {
		for(Base_Mod bMod : modArray) {
			bMod.onKeyEvent();
		}
	}
}