package Comet.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import net.minecraft.client.*;
import net.minecraft.src.*;

public class Settings {

	public static Minecraft mc = Minecraft.theMinecraft;

	public static ArrayList waypoints = new ArrayList();
	public static ArrayList friends = new ArrayList();
	public static ArrayList enemys = new ArrayList();
	public static ArrayList lwcList = new ArrayList();
	public static ArrayList nameProtectOrgName = new ArrayList();
	public static ArrayList nameProtectNewName = new ArrayList();
	public static ArrayList blockList = new ArrayList();

	public static String lastChatMessage;
	public static String macroMsg[] = {
		"Macro 1", "Macro 2", "Macro 3", "Macro 4", "Macro 5", "Macro 6", "Macro 7", "Macro 8", "Macro 9", "Macro 10", "Macro 11", "Macro 12", "Macro 13", "Macro 14",
	};

	public static int amountMsg;

	public static float opacity = 155F;
	public static float speed = Timer.timerSpeed;
	public static float jump;
	public static float step = 0.5F;
	public static float zoom;
	public static float reach = 4.5F;
	public static float flyspeed;
	public static float auraRange = 4.5F;
	public static byte chatList = 10;
	
	public static int instantX;
	public static int instantY;
	public static int instantZ;

	public static double D_X;
	public static double D_Y;
	public static double D_Z;

	public static File Text;
	public static File Text2;
	public static File Text3;
	public static String Text4;
	public static String Text5;
	public static String Text6;
	public static String Text7;
	public static File Folder;

	public static KeyBinding keySneak;
	public static KeyBinding keyMenu;
	public static KeyBinding keyNoFall;
	public static KeyBinding keyKillAura;
	public static KeyBinding keyMiner;
	public static KeyBinding keyInstantMine;
	public static KeyBinding keyFly;
	public static KeyBinding keyBright;
	public static KeyBinding keyxRay;
	public static KeyBinding keys[];

	public static KeyBinding macro1;
	public static KeyBinding macro2;
	public static KeyBinding macro3;
	public static KeyBinding macro4;
	public static KeyBinding macro5;
	public static KeyBinding macro6;
	public static KeyBinding macro7;
	public static KeyBinding macro8;
	public static KeyBinding macro9;
	public static KeyBinding macro10;
	public static KeyBinding macro11;
	public static KeyBinding macro12;
	public static KeyBinding macro13;
	public static KeyBinding macro14;
	public static KeyBinding macros[];

	public Settings() {
		Folder = new File(mc.getMinecraftDir(), "Comet");
		Folder.mkdirs();

		Text = new File(Folder, "Keybinds.txt");
		Text2 = new File(Folder, "Macros.txt");
		Text3 = new File(Folder, "Settings.txt");
		Text4 = "Friends.txt";
		Text5 = "Enemys.txt";
		Text6 = "LWC crack list.txt";
		Text7 = "xRay.txt";

		keySneak = new KeyBinding("Sneak", Keyboard.KEY_Z);
		keyMenu = new KeyBinding("Menu", Keyboard.KEY_RSHIFT);
		keyNoFall = new KeyBinding("NoFall", Keyboard.KEY_N);
		keyKillAura = new KeyBinding("Kill aura", Keyboard.KEY_K);
		keyMiner = new KeyBinding("Miner", Keyboard.KEY_R);
		keyInstantMine = new KeyBinding("Instant mine", Keyboard.KEY_I);
		keyFly = new KeyBinding("Fly", Keyboard.KEY_F);
		keyBright = new KeyBinding("Bright", Keyboard.KEY_C);
		keyxRay = new KeyBinding("xRay", Keyboard.KEY_X);

		keys = (new KeyBinding[] {
				keySneak, keyMenu, keyNoFall, keyKillAura, keyMiner, keyInstantMine, keyFly, keyBright, keyxRay
		});

		macro1 = new KeyBinding("Macro 1", Keyboard.KEY_UP);
		macro2 = new KeyBinding("Macro 2", Keyboard.KEY_DOWN);
		macro3 = new KeyBinding("Macro 3", Keyboard.KEY_RIGHT);
		macro4 = new KeyBinding("Macro 4", Keyboard.KEY_LEFT);
		macro5 = new KeyBinding("Macro 5", Keyboard.KEY_NUMPAD0);
		macro6 = new KeyBinding("Macro 6", Keyboard.KEY_NUMPAD1);
		macro7 = new KeyBinding("Macro 7", Keyboard.KEY_NUMPAD2);
		macro8 = new KeyBinding("Macro 8", Keyboard.KEY_NUMPAD3);
		macro9 = new KeyBinding("Macro 9", Keyboard.KEY_NUMPAD4);
		macro10 = new KeyBinding("Macro 10", Keyboard.KEY_NUMPAD5);
		macro11 = new KeyBinding("Macro 11", Keyboard.KEY_NUMPAD6);
		macro12 = new KeyBinding("Macro 12", Keyboard.KEY_NUMPAD7);
		macro13 = new KeyBinding("Macro 13", Keyboard.KEY_NUMPAD8);
		macro14 = new KeyBinding("Macro 14", Keyboard.KEY_NUMPAD9);

		macros = (new KeyBinding[] {
				macro1, macro2, macro3, macro4, macro5, macro6, macro7, macro8, macro9, macro10, macro11, macro12, macro13, macro14
		});
		
		loadKeys();
		loadMacros();
		loadFriends();
		loadEnemys();
		loadLwcList();
		loadxRay();
		
		blockList.add(Block.lavaStill.blockID);
		blockList.add(Block.lavaMoving.blockID);
		blockList.add(Block.waterStill.blockID);
		blockList.add(Block.waterMoving.blockID);
	}

	public static String getKeysDescription(int I1) {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		return stringtranslate.translateKey(keys[I1].keyDescription);
	}

	public static String getKeysOptionDisplayString(int I1) {
		int i = keys[I1].keyCode;
		return getKeysDisplayString(i);
	}

	public static String getKeysDisplayString(int I1) {
		if (I1 < 0) {
			return StatCollector.translateToLocalFormatted("key.mouseButton", new Object[]
					{
					Integer.valueOf(I1 + 101)
					});
		} else {return Keyboard.getKeyName(I1);}
	}

	public static void setKeys(int I1, int I2) {
		keys[I1].keyCode = I2;
		saveKeys();
	}

	public static String getMacrosDescription(int I1) {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		return stringtranslate.translateKey(macros[I1].keyDescription);
	}

	public static String getMacrosOptionDisplayString(int I1) {
		int i = macros[I1].keyCode;
		return getMacrosDisplayString(i);
	}

	public static String getMacrosDisplayString(int I1) {
		if (I1 < 0) {
			return StatCollector.translateToLocalFormatted("key.mouseButton", new Object[]
					{
					Integer.valueOf(I1 + 101)
					});
		} else {return Keyboard.getKeyName(I1);}
	}

	public static void setMacros(int I1, int I2) {
		macros[I1].keyCode = I2;
		saveMacros();
	}

	public static void setOptionFloatValue(Enum_Options enumoptions, float I1) {
		if (enumoptions == Enum_Options.Opacity) {opacity = I1;}
		if (enumoptions == Enum_Options.MoveSpeed) {Timer.timerSpeed = I1;}
	}

	public static void setOptionValue(Enum_Options enumoptions, int I1) {
		if (enumoptions == Enum_Options.Opacity) {opacity = opacity + I1;}
		if (enumoptions == Enum_Options.MoveSpeed) {Timer.timerSpeed = Timer.timerSpeed + I1;}
	}

	public static float getOptionFloatValue(Enum_Options enumoptions) {
		if (enumoptions == Enum_Options.Opacity){return opacity;}
		if (enumoptions == Enum_Options.MoveSpeed){return Timer.timerSpeed;}
		else{return 0.0F;}
	}

	public static String getKeys(Enum_Options enumoptions) {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		String s = stringtranslate.translateKey(enumoptions.getEnumString());

		if (s == null) {s = enumoptions.getEnumString();}

		String s1 = (new StringBuilder()).append(s).append(" : ").toString();

		if (enumoptions.getEnumFloat()) {
			float I1 = getOptionFloatValue(enumoptions);

			if (enumoptions == Enum_Options.MoveSpeed) {
				if (I1 == 0.0F){return (new StringBuilder()).append(s1).append(stringtranslate.translateKey("0")).toString();}

				if (I1 == 1.0F){return (new StringBuilder()).append(s1).append(stringtranslate.translateKey("10")).toString();}

				else{return (new StringBuilder()).append(s1).append("+").append((int)(I1 * 10F)).append("%").toString();}}

			if (enumoptions == Enum_Options.Opacity) {
				if (I1 == 0.0F){return (new StringBuilder()).append(s1).append(stringtranslate.translateKey("0")).toString();}

				if (I1 == 1.0F){return (new StringBuilder()).append(s1).append(stringtranslate.translateKey("255")).toString();}

				else{return (new StringBuilder()).append(s1).append("+").append((int)(I1 * 10F)).append("%").toString();}

			} else {
				return s1;}}
		return s1;
	}

	public static void loadKeys() {
		try {
			if(!Text.exists()) {return;}
			BufferedReader bufferedreader = new BufferedReader(new FileReader(Text));
			for(String s = ""; (s = bufferedreader.readLine()) != null;) {
				try {
					String as[] = s.split(" : ");
					int i = 0;
					int j = 0;
					while(i < keys.length) 
					{if(as[0].equals((new StringBuilder()).append("Key_").append(keys[i].keyDescription).toString())){
						keys[i].keyCode = Integer.parseInt(as[1]);
					}i++;}
				}catch(Exception exception1){
					System.out.println((new StringBuilder()).append("Skipping bad option: ").append(s).toString());}}

			KeyBinding.resetKeyBindingArrayAndHash();
			bufferedreader.close();
		}catch(Exception exception){
			System.out.println("Failed to load options");
			exception.printStackTrace();}
	}

	public static void saveKeys() {
		try {
			PrintWriter printwriter = new PrintWriter(new FileWriter(Text));
			for(int i = 0; i < keys.length; i++){
				printwriter.println((new StringBuilder()).append("Key_").append(keys[i].keyDescription).append(" : ").append(keys[i].keyCode).toString());}
			printwriter.close();
		}catch(Exception exception){
			System.out.println("Failed to save keybinds");
			exception.printStackTrace();}
	}

	public static void loadMacros() {
		try {
			if(!Text2.exists()) {return;}
			BufferedReader bufferedreader = new BufferedReader(new FileReader(Text2));
			for(String s = ""; (s = bufferedreader.readLine()) != null;) {
				try {
					String as[] = s.split(" : ");
					int i = 0;
					int j = 0;
					while(i < macros.length) 
					{if(as[0].equals((new StringBuilder()).append("Macro_").append(macros[i].keyDescription).toString())){
						macros[i].keyCode = Integer.parseInt(as[1]);
					}i++;}
				}catch(Exception exception1){
					System.out.println((new StringBuilder()).append("Skipping bad option: ").append(s).toString());}}

			KeyBinding.resetKeyBindingArrayAndHash();
			bufferedreader.close();
		}catch(Exception exception){
			System.out.println("Failed to load options");
			exception.printStackTrace();}
	}

	public static void saveMacros() {
		try {
			PrintWriter printwriter = new PrintWriter(new FileWriter(Text2));
			for(int i = 0; i < macros.length; i++){
				printwriter.println((new StringBuilder()).append("Macro_").append(macros[i].keyDescription).append(" : ").append(macros[i].keyCode).toString());}
			printwriter.close();
		}catch(Exception exception){
			System.out.println("Failed to save keybinds");
			exception.printStackTrace();}
	}

	public static void loadFriends() {
		try {
			File file = new File(Folder, Text4);
			if (file.exists()) {
				BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
				String s;
				for (int i = 0; (s = bufferedreader.readLine()) != null; i++) {friends.add(s);}
			}
		} catch (Exception exception) {
			System.err.print(exception.toString());
		}
	}

	public static void loadEnemys() {
		try {
			File file = new File(Folder, Text5);
			if (file.exists()) {
				BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
				String s;
				for (int i = 0; (s = bufferedreader.readLine()) != null; i++) {enemys.add(s);}
			}
		} catch (Exception exception) {
			System.err.print(exception.toString());
		}
	}

	public static void loadLwcList() {
		try {
			File file = new File(Folder, Text6);
			if (file.exists()) {
				BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
				String s;
				for (int i = 0; (s = bufferedreader.readLine()) != null; i++) {lwcList.add(s);}
			}
		} catch (Exception exception) {
			System.err.print(exception.toString());
		}
	}

	public static void loadxRay() {
		try {
			File file = new File(Folder, Text7);
			if (file.exists()) {
				BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
				String s;
				for (int i = 0; (s = bufferedreader.readLine()) != null; i++) {blockList.add(s);}
			}
		} catch (Exception exception) {
			System.err.print(exception.toString());
		}
	}

	public static void saveFriends() {
		try {
			File file = new File(Folder, Text4);
			BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < friends.size(); i++) {bufferedwriter.write((new StringBuilder()).append((String) friends.get(i)).append("\r\n").toString());}
			bufferedwriter.close();
		} catch (Exception exception) {
			System.err.print(exception.toString());
		}
	}

	public static void saveEnemys() {
		try {
			File file = new File(Folder, Text5);
			BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < enemys.size(); i++) {bufferedwriter.write((new StringBuilder()).append((String) enemys.get(i)).append("\r\n").toString());}
			bufferedwriter.close();
		} catch (Exception exception) {
			System.err.print(exception.toString());
		}
	}

	public static void saveLwcList() {
		try {
			File file = new File(Folder, Text6);
			BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < lwcList.size(); i++) {bufferedwriter.write((new StringBuilder()).append((String) lwcList.get(i)).append("\r\n").toString());}
			bufferedwriter.close();
		} catch (Exception exception) {
			System.err.print(exception.toString());
		}
	}

	public static void savexRay() {
		try {
			File file = new File(Folder, Text7);
			BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < blockList.size(); i++) {bufferedwriter.write((new StringBuilder()).append((String) blockList.get(i)).append("\r\n").toString());}
			bufferedwriter.close();
		} catch (Exception exception) {
			System.err.print(exception.toString());
		}
	}

}
