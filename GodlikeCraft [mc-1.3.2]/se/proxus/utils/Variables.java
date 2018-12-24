package se.proxus.utils;

import java.awt.Font;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.newdawn.slick.UnicodeFont;

import net.minecraft.src.EntityPlayer;

import se.proxus.*;
import se.proxus.utils.placeholders.p_Breadcrum;

public class Variables extends GodlikeCraft {

	public static int
	killCount = 0,
	aimbotMode = 2,
	botAmount = 2,
	curPort = 0,
	fontSize = 16,
	bookColourMode = 1,
	opacity = 200,
	fontMode = 1,
	fontStyle = Font.TRUETYPE_FONT;

	public static double
	trailX = 0.0D,
	trailY = 0.0D,
	trailZ = 0.0D;

	public static boolean 
	firstTime = true,
	dropItemsInAutoSoup = false,
	isAuraBlocking = false,
	canChangePos = false,
	needsUpdate = false;

	public static String
	kitPvPString = "/pvp",
	botMessage = "I got big hakes and big d0ng5.",
	curIP = "",
	fontName = "Ubuntu",
	lockedName = "Olivki",
	curVersion = "1.2a",
	newVersion = "";

	public static ArrayList<String> 
	checkedMessages = new ArrayList<String>(),
	friendArray = new ArrayList<String>(),
	logArray = new ArrayList<String>(),
	accountArray = new ArrayList<String>(),
	proxyArray = new ArrayList<String>(),
	spamArray = new ArrayList<String>(),
	errorArray = new ArrayList<String>();

	public static ArrayList<EntityPlayer> 
	playerArray = new ArrayList<EntityPlayer>();

	public static ArrayList<Integer> xrayArray = new ArrayList<Integer>();

	public static ArrayList<p_Breadcrum> trailArray = new ArrayList<p_Breadcrum>();

	public static UnicodeFont 
	font = new UnicodeFont(new Font(fontName, fontStyle, fontSize)),
	fontSmaller = new UnicodeFont(new Font(fontName, fontStyle, fontSize - 4));

	public void addFriend(String var1) {
		if(!(this.friendArray.contains(var1.toLowerCase()))) {
			this.friendArray.add(var1.toLowerCase());
			this.utils.log("[Friends] Added the friend \"" + var1.toLowerCase() + "\" to the friend array.");
		} else {
			this.utils.log("[Friends] The user \"" + var1 + "\" is already in the friend array.");
		}
	}

	public void delFriend(String var1) {
		if(this.friendArray.contains(var1.toLowerCase())) {
			this.friendArray.remove(this.friendArray.indexOf(var1.toLowerCase()));
			this.utils.log("[Friends] Removed the friend \"" + var1.toLowerCase() + "\" from the friend array.");
		} else {
			this.utils.log("[Friends] The user \"" + var1 + "\" is not in the friend array.");
		}
	}

	public void addError(String var1) {
		DateFormat var2 = new SimpleDateFormat("yy/mm/dd/hh:mm:ss");
		Calendar var3 = Calendar.getInstance();

		this.errorArray.add("[" + var2.format(var3.getTime()) + "] " + var1);
	}
}