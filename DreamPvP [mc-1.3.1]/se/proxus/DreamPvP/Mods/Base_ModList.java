package se.proxus.DreamPvP.Mods;

import java.util.ArrayList;

public final class Base_ModList {

	public static ArrayList<Base_Mod> modArray = new ArrayList<Base_Mod>();

	/** Objects for the mod classes. **/
	public static m_Sneak sneak = new m_Sneak();
	public static m_PlayerAura playerAura = new m_PlayerAura();
	public static m_ClickAimbot clickAimbot = new m_ClickAimbot();
	public static m_ESP esp = new m_ESP();
	public static m_BlockESP blockESP = new m_BlockESP();
	public static m_ChestESP chestESP = new m_ChestESP();
	public static m_xRay xray = new m_xRay();
	public static m_Bright bright = new m_Bright();
	public static m_Fly fly = new m_Fly();
	public static m_NoFall nofall = new m_NoFall();
	public static m_Miner miner = new m_Miner();
	public static m_AutoTool autoTool = new m_AutoTool();
	public static m_MobAura mobAura = new m_MobAura();
	public static m_Waypoint waypoint = new m_Waypoint();
	public static m_Instant instant = new m_Instant();
	public static m_TripWireESP tripWireESP = new m_TripWireESP();
	public static m_Nametags nameTags = new m_Nametags();
	public static m_Freecam freeCam = new m_Freecam();
	public static m_Info info = new m_Info();
	public static m_Minimap miniMap = new m_Minimap();
	public static m_Derp_Normal derpNormal = new m_Derp_Normal();
	public static m_Derp_Headless derpHeadless = new m_Derp_Headless();
	public static m_Derp_Reverse derpReverse = new m_Derp_Reverse();
	public static m_AutoBlocker autoBlocker = new m_AutoBlocker();
	public static m_Nuker nuker = new m_Nuker();
	public static m_Sprint sprint = new m_Sprint();
	public static m_BowAimbot bowAimbot = new m_BowAimbot();
	public static m_BlockOutline blockOutline = new m_BlockOutline();
	public static m_Chams chams = new m_Chams();
	public static m_ThermalVision thermalVision = new m_ThermalVision();
	public static m_AntiSpam antiSpam = new m_AntiSpam();

	public Base_ModList() {
		checkMods();
	}

	public static void checkMods() {
		modArray.clear();
		modArray.add(sneak);
		modArray.add(playerAura);
		modArray.add(clickAimbot);
		modArray.add(esp);
		modArray.add(blockESP);
		modArray.add(chestESP);
		modArray.add(xray);
		modArray.add(bright);
		modArray.add(fly);
		modArray.add(nofall);
		modArray.add(miner);
		modArray.add(autoTool);
		modArray.add(mobAura);
		modArray.add(waypoint);
		modArray.add(instant);
		modArray.add(tripWireESP);
		modArray.add(nameTags);
		modArray.add(freeCam);
		modArray.add(info);
		modArray.add(miniMap);
		modArray.add(derpNormal);
		modArray.add(derpHeadless);
		modArray.add(derpReverse);
		modArray.add(autoBlocker);
		modArray.add(nuker);
		modArray.add(sprint);
		modArray.add(bowAimbot);
		modArray.add(blockOutline);
		modArray.add(chams);
		modArray.add(thermalVision);
		modArray.add(antiSpam);
	}

	public static void checkModKeys() {
		for(Base_Mod bMod : modArray) {
			bMod.onKeyPressed();
		}
	}
}