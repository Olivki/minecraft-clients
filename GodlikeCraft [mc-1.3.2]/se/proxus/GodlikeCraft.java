package se.proxus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import net.minecraft.client.*;
import se.proxus.ai.i_Follow;
import se.proxus.commands.*;
import se.proxus.gui.*;
import se.proxus.gui.screens.Console;
import se.proxus.gui.utils.Slick;
import se.proxus.hacks.*;
import se.proxus.panels.Base_Panels;
import se.proxus.utils.*;

public class GodlikeCraft {

	/** Notch classes. **/
	/** The instance of the class Minecraft.java **/
	public static Minecraft mc = Minecraft.theMinecraft;

	/** My classes. **/
	/** The instance of the class InGame.java **/
	public static InGame ingame = new InGame();

	/** The instance of the class Utils.java **/
	public static Utils utils = new Utils();

	/** The instance of the class Base_Hacks.java **/
	public static Base_Hacks hacks = new Base_Hacks();

	/** The instance of the class Variables.java **/
	public static Variables vars = new Variables();
	
	/** The instance of the class Files.java **/
	public static Files files = new Files();

	/** The instance of the class Base_Commands.java **/
	public static Base_Commands cmds = new Base_Commands();

	/** The instance of the class StringUtil.java **/
	public static StringUtil sUtil = new StringUtil();

	/** The instance of the class Base_Panels.java **/
	//public static Base_Panels panels = new Base_Panels();

	/** The instance of the class Crypter.java **/
	public static Crypter crypt = new Crypter();

	/** The instance of the class Key.java **/
	public static Key key = new Key();
	
	/** The instance of the class i_Follow.java **/
	public static i_Follow follow = new i_Follow();
	
	/** The instance of the class Renderers.java **/
	public static Renderers renders = new Renderers();
	
	/** The instance of the class Prediction.java **/
	public static Prediction pred = new Prediction();
	
	/** The instance of the class Console.java **/
	public static Console console = new Console();
	
	/** The instance of the class Slick.java **/
	public static Slick slick = new Slick();
	
	public GodlikeCraft(String var1) {
		this.checkVersion();
	}
	
	public GodlikeCraft() {
		
	}

	/** The methods. **/
	/** The initAll() method. **/
	public void initAll() {
		if(this.vars.firstTime) {
			this.files.initFiles();
			this.vars.addFriend("Olivki");
			this.vars.addFriend("GodlikeGuy");
			this.vars.addFriend("HakeSkid");
			this.checkKillSwitch();
			this.vars.firstTime = false;
		}
	}

	/** The method for checking if the kill switch is pulled. **/
	private void checkKillSwitch() {
		try {
			URL url = new URL(this.crypt.decryptText("-m;(&KuB#I#&:BHd7mS*4n-my;A9rk0l"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String s = reader.readLine();

			if(s.equalsIgnoreCase(this.crypt.decryptText("5n3$Q!"))) {
				mc.shutdown();
			}
		} catch (Exception e) {
			mc.shutdown();
			e.printStackTrace();
		}
	}
	
	private void checkVersion() {
		try {
			URL url = new URL("http://proxus.se/GodlikeCraft/checkVersion.php");
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String s = reader.readLine();

			if(!vars.curVersion.equalsIgnoreCase(s)) {
				vars.newVersion = s;
				vars.needsUpdate = true;
			}
		} catch (Exception e)  {
			e.printStackTrace();
		}
	}

	/**
	 * The method for encrypting a String to a MD5 encryption.
	 * @param var1 - The string to be encrypted.
	 * @return - The encrypted String.
	 */
	private String MD5(String var1) {
		MessageDigest var2 = null;

		try {
			var2 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {

		}

		byte[] var3 = var1.getBytes();
		byte[] var4 = var2.digest(var3);
		BigInteger var5 = new BigInteger(1, var4);

		return var5.toString(16);
	}

	/**
	 * The method for getting the users HWID.
	 * @return - The HWID of the user.
	 */
	public String generateHWID() {
		return MD5(System.getProperty(this.crypt.decryptText(":l,*y+H?u"))).toUpperCase();
	}
}