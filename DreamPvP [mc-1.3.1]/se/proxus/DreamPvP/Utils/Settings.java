package se.proxus.DreamPvP.Utils;

import java.io.File;
import java.text.*;
import java.util.*;

import net.minecraft.src.Block;
import net.minecraft.src.Item;

import org.lwjgl.input.Keyboard;

import se.proxus.DreamPvP.*;
import se.proxus.DreamPvP.Gui.Utils.TTF;
import se.proxus.DreamPvP.Utils.Placeholders.*;

public class Settings {

	public static DreamPvP dp = new DreamPvP();

	public static ArrayList<Key> macroArray = new ArrayList<Key>();
	public static ArrayList<String> friendArray = new ArrayList<String>();
	public static ArrayList<String> enemyArray = new ArrayList<String>();
	public static ArrayList<u_Block> blockArray = new ArrayList<u_Block>();
	public static ArrayList<Integer> xrayArray = new ArrayList<Integer>();
	public static ArrayList<u_Waypoint> waypointArray = new ArrayList<u_Waypoint>();
	public static ArrayList<Object> logArray = new ArrayList<Object>();
	public static ArrayList<String> ircArray = new ArrayList<String>();
	public static ArrayList<String> donatorArray = new ArrayList<String>();
	public static ArrayList<String> modIRCArray = new ArrayList<String>();
	public static ArrayList<u_IRCMessage> ircMessageArray = new ArrayList<u_IRCMessage>();
	public static ArrayList<String> friendIRCArray = new ArrayList<String>();
	public static ArrayList<u_Alt> altArray = new ArrayList<u_Alt>();
	public static ArrayList<Block> blocksArray = new ArrayList<Block>();
	public static ArrayList<String> ircPlaceholderArray = new ArrayList<String>();
	public static ArrayList<u_Drawer> drawBoardArray = new ArrayList<u_Drawer>();
	public static ArrayList<Object> itemArray = new ArrayList<Object>();

	public static float bC1 = 1.0F, bC2 = 1.0F, bC3 = 0.6F, update = 0.0F, reach = 5.0F, wR = 1.0F, wG = 1.0F, wB = 1.0F, espCheckSize = 34, placeDelay = 4, curBlockDamage = 0.0F;
	public static float cR1 = 1.0F, cG1 = 0.0F, cB1 = 0.0F, cR2 = 0.0F, cG2 = 1.0F, cB2 = 0.0F, bR = 1.0F, bG = 0.0F, bB = 0.0F;

	public static double flySpeed = 2.5D;

	public static String lockUsername = "", curVersion = "2.0b", newVersion = "", curIRCCHatName = "", bannedReason = "LOL", name = "", lastChatMessage = "", lastIRCMessage = "";
	public String curServerIP = "";

	public static int curPort = 0, espMode = 1, chatMode = 4, radarMode = 1, aimbotMode = 1, buttonMode = 1, saidTimes = 0, saidTimesIRC = 0, TimeIRC = 3000;

	public static long curTime = 0L;
	
	public static boolean debugChat = false;

	public void init() {

		for(int var1 = 0; var1 < Block.blocksList.length; var1++) {
			Block block = Block.blocksList[var1];

			if(!blocksArray.contains(block) && !itemArray.contains(block) && block != null) {
				blocksArray.add(block);
				itemArray.add(block);
			}
		}

		for(int var2 = 0; var2 < Item.itemsList.length; var2++) {
			Item item = Item.itemsList[var2];
			
			if(!itemArray.contains(item) && item != null) {
				itemArray.add(item);
			}
		}

		if(xrayArray.isEmpty()) {
			xrayArray.add(8);
			xrayArray.add(9);
			xrayArray.add(10);
			xrayArray.add(11);
			xrayArray.add(5);
			xrayArray.add(129);
			xrayArray.add(56);
			xrayArray.add(57);
			xrayArray.add(15);
			xrayArray.add(42);
			xrayArray.add(16);
			xrayArray.add(61);
			xrayArray.add(62);
			dp.files.saveBaseFile(new File(dp.files.baseFolder, "xRay.txt"), xrayArray, true);
		}
	}

	public static void checkMacros() {
		DateFormat DTEformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();

		for(Key key : macroArray) {
			if(dp.utils.getEventKey(key.getKey())) {
				String cmd = key.getCommand();
				String keys = Keyboard.getKeyName(key.getKey());

				dp.utils.sendChat(cmd);
				dp.utils.log("[Macro] Key : " + keys + ", command : " + cmd + ", issued on : " + DTEformat.format(cal.getTime()));
			}
		}
	}
}