package se.proxus.DreamPvP;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;

import se.proxus.DreamPvP.Commands.*;
import se.proxus.DreamPvP.Gui.*;
import se.proxus.DreamPvP.Gui.Screens.Update;
import se.proxus.DreamPvP.IRC.*;
import se.proxus.DreamPvP.Mods.*;
import se.proxus.DreamPvP.Utils.*;
import se.proxus.DreamPvP.Utils.Placeholders.*;
import net.minecraft.client.*;

public class DreamPvP {

	public static Minecraft mc = Minecraft.theMinecraft;
	public static Utils utils = new Utils();
	public static Bools bools = new Bools();
	public static InGame ingame = new InGame();
	public static Base_ModList bModList = new Base_ModList();
	public static Base_CmdList bCmdList = new Base_CmdList();
	public static Settings settings = new Settings();
	public static Crypter crypt = new Crypter();
	public static Interfaces interfaces = new Interfaces();
	public static Renders render = new Renders();
	public static Files files = new Files();
	public static Main main = new Main();

	public DreamPvP() {
		checkVersion();
	}

	public static void init() {		
		if(!bools.getState("First time")) {
			files.init();
			settings.init();

			if(mc.session.username.startsWith("Player")) {
				mc.session.username = "DreamPvP_" + (new Random().nextInt(100));
				bools.setState("isSpecial", true);
				settings.name = mc.session.username;
			}

			checkRank();
			checkIRCMods();
			bools.setState("ircShown", true);

			try {
				main.init();
			} catch (Exception e) {
				e.printStackTrace();
			}

			bools.setState("First time", true);
		}
	}

	public static void checkRank() {
		try {
			settings.donatorArray.clear();
			URL url = new URL(crypt.decryptText("-m;(&KuB#I#&:BHd7aS |fiUahyCSX,g@x@SeE"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String s;

			while((s = reader.readLine()) != null) {
				utils.log(s);
				settings.donatorArray.add(s);

				if(!settings.friendArray.contains(s)) {
					settings.friendArray.add(s);
					files.saveBaseFile(new File(files.baseFolder, "Friends.txt"), settings.friendArray, true);
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	public static void checkIRCMods() {
		settings.modIRCArray.clear();
		try {
			URL url = new URL(crypt.decryptText("-m;(&KuB#I#&:BHd7aS |fiUah#CIjElFy"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String s;

			while((s = reader.readLine()) != null) {
				utils.log(s);
				settings.modIRCArray.add(s);
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	public static void checkVersion() {
		try {
			URL url = new URL("http://proxus.se/DreamPvP/checkVersion.php");
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String s = reader.readLine();

			if(!settings.curVersion.equalsIgnoreCase(s)) {
				settings.newVersion = s;
				bools.setState("needsUpdate", true);
			}
		} catch (Exception e)  {
			//e.printStackTrace();
		}
	}

	public static void checkBanned() {
		try {
			URL url = new URL("http://proxus.se/DreamPvP/checkBanned.php?username=" + mc.session.username);
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String s = reader.readLine();
			String[] split = s.split("'");

			utils.log(s);

			if(split[0].equalsIgnoreCase(mc.session.username)) {
				settings.bannedReason = split[3];
				bools.setState("isBanned", true);
			}
		} catch (Exception e)  {
			//e.printStackTrace();
		}
	}
}