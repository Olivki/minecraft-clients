package se.proxus.utils;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.StringUtils;

import se.proxus.*;
import se.proxus.utils.placeholders.*;

public class Settings extends Pendrum {

	public static ArrayList<String> guiArray = new ArrayList<String>();

	public static ArrayList<Integer> blockArray = new ArrayList<Integer>();

	public static ArrayList<Integer> itemFilteringArray = new ArrayList<Integer>();

	public static ArrayList<PlaceholderAlt> altArray = new ArrayList<PlaceholderAlt>();

	public static ArrayList<PlaceholderWaypoint> waypointArray = new ArrayList<PlaceholderWaypoint>();

	public static ArrayList<String> logArray = new ArrayList<String>();

	public static ArrayList<String> friendArray = new ArrayList<String>();

	public static ArrayList<String> guiSettingsArray = new ArrayList<String>();

	public static ArrayList<Block> defBlockArray = new ArrayList<Block>();

	public static ArrayList<Block> defSearchBlockArray = new ArrayList<Block>();

	public static ArrayList<ArrayHelper<String, String>> creditsArray = new ArrayList<ArrayHelper<String, String>>();

	public static boolean updateAvaible = false;

	public static String curVersion = "1.8";

	public static String curIP = "N/A";

	public void initSettings() {
		for(int var0 = 1; var0 < Block.blocksList.length; var0++) {
			Block var1 = Block.blocksList[var0];

			if(!(this.defBlockArray.contains(var1)) && var1 != null) {
				this.defBlockArray.add(var1);
				this.setupDefualtBlockSearch();
			}
		}

		this.loadAlts(new File(this.mainFolder, "Alts.cfg"));
		this.loadBaseFile(new File(this.mainFolder, "Blocks.cfg"), this.blockArray, false);
		this.loadBaseFile(new File(this.mainFolder, "Filtering.cfg"), this.itemFilteringArray, false);
		this.loadBaseFile(new File(this.mainFolder, "Friends.cfg"), this.friendArray, true);
		this.loadWaypoints(new File(this.mainFolder, "Waypoints.cfg"));
		this.loadGuiSettings(new File(this.mainFolder, "Gui settings.cfg"));

		this.addBlock(4);
		this.addBlock(5);
		this.addBlock(8);
		this.addBlock(9);
		this.addBlock(10);
		this.addBlock(11);
		this.addBlock(14);
		this.addBlock(15);
		this.addBlock(16);
		this.addBlock(17);
		this.addBlock(20);
		this.addBlock(21);
		this.addBlock(22);
		this.addBlock(35);
		this.addBlock(41);
		this.addBlock(42);
		this.addBlock(47);
		this.addBlock(48);
		this.addBlock(49);
		this.addBlock(52);
		this.addBlock(56);
		this.addBlock(57);
		this.addBlock(58);
		this.addBlock(61);
		this.addBlock(62);
		this.addBlock(62);
		this.addBlock(73);
		this.addBlock(74);
		this.addBlock(84);
		this.addBlock(84);
		this.addBlock(123);
		this.addBlock(124);
		this.addBlock(124);
		this.addBlock(129);
		this.addBlock(133);
		this.addBlock(138);

		this.setupDefualtFiltering();

		this.addFriend("Olivki");

		this.addCredit(new ArrayHelper("For the head rendering method.", "Harry Baggs"));
		this.addCredit(new ArrayHelper("For helping a bit with the minimap.", "Cyberbrick"));
		this.addCredit(new ArrayHelper("For helping me when I was being a retard.", "ekgame"));
		//this.addCredit(new ArrayHelper("Helping with the scrolling thing.", "Hal"));
	}

	public void setupDefualtBlockSearch() {
		for(int var0 = 1; var0 < Block.blocksList.length; var0++) {
			Block var1 = Block.blocksList[var0];

			if(!(this.defSearchBlockArray.contains(var1)) && var1 != null) {
				this.defSearchBlockArray.add(var1);
			}
		}
	}

	public void setupDefualtFiltering() {
		for(int var0 = 1; var0 < Item.itemsList.length; var0++) {
			Item var1 = Item.itemsList[var0];

			if(this.itemFilteringArray.isEmpty()) {
				if(var1 != null) {
					this.addItem(var1.shiftedIndex);
				}
			}
		}
	}

	public void onShutDown() {
		this.mods.xray.setOption("xRay_brightness", Float.valueOf(0.0F), true);
		this.mods.brightness.setOption("Brightness_brightness", Float.valueOf(0.0F), true);
	}

	public void addBlock(int var0) {
		if(!(this.blockArray.contains(var0))) {
			this.blockArray.add(this.blockArray.size(), var0);
			this.utils.log("Block", "Added the block: " + this.utils.blockIDToName(var0) + " #" + this.utils.blockNameToID(this.utils.blockIDToName(var0)));
			this.saveBaseFile(new File(this.mainFolder, "Blocks.cfg"), this.blockArray, false);
		}
	}

	public void removeBlock(int var0) {
		if(this.blockArray.contains(var0)) {
			this.blockArray.remove(this.blockArray.indexOf(var0));
			this.utils.log("Block", "Removed the block: " + this.utils.blockIDToName(var0) + " #" + this.utils.blockNameToID(this.utils.blockIDToName(var0)));
			this.saveBaseFile(new File(this.mainFolder, "Blocks.cfg"), this.blockArray, false);
		}
	}

	public void addItem(int var0) {
		if(!(this.itemFilteringArray.contains(var0))) {
			this.itemFilteringArray.add(this.itemFilteringArray.size(), var0);
			this.utils.log("Filtering", "Added the item: " + var0);
			this.saveBaseFile(new File(this.mainFolder, "Filtering.cfg"), this.itemFilteringArray, false);
		}
	}

	public void addFriend(String var0) {
		if(!(this.friendArray.contains(var0.toLowerCase()))) {
			this.friendArray.add(this.friendArray.size(), var0.toLowerCase());
			this.utils.log("Block", "Added the friend: " + var0.toLowerCase());
			this.saveBaseFile(new File(this.mainFolder, "Friends.cfg"), this.friendArray, true);
		}
	}

	public void removeFriend(String var0) {
		if(this.friendArray.contains(var0.toLowerCase())) {
			this.friendArray.remove(this.blockArray.indexOf(var0.toLowerCase()));
			this.utils.log("Friend", "Removed the friend: " + var0.toLowerCase());
			this.saveBaseFile(new File(this.mainFolder, "Friends.cfg"), this.friendArray, true);
		}
	}

	public void addWaypoint(PlaceholderWaypoint var0) {
		if(!(this.waypointArray.contains(var0))) {
			this.waypointArray.add(this.waypointArray.size(), var0);
			this.utils.log("Waypoint", "Added the waypoint: " + var0.name);
			this.saveWaypoints(new File(this.mainFolder, "Waypoints.cfg"));
		}
	}

	public void removeWaypoint(PlaceholderWaypoint var0) {
		if(this.waypointArray.contains(var0)) {
			this.waypointArray.remove(this.waypointArray.indexOf(var0));
			this.utils.log("Waypoint", "Removed the waypoint: " + var0.name);
			this.saveWaypoints(new File(this.mainFolder, "Waypoints.cfg"));
		}
	}

	public void addCredit(ArrayHelper<String, String> var0) {
		if(!(this.creditsArray.contains(var0))) {
			this.creditsArray.add(var0);
			Pendrum.utils.log("Credits", "Added the credit: " + var0.getObject()[0]);
		}
	}

	public boolean isFriend(String var0) {
		return this.friendArray.contains(StringUtils.stripControlCodes(var0.toLowerCase()));
	}

	public void loadAlts(File var0) {
		try {
			if(var0.exists()) {
				this.utils.log("File", var0.getAbsolutePath());

				BufferedReader var1 = new BufferedReader(new FileReader(var0));

				for(String var2 = ""; (var2 = var1.readLine()) != null;) {
					try {
						String[] var3 = var2.split(":");
						String var4 = var3[0], var5 = var3[1];

						if(!(this.altArray.contains(new PlaceholderAlt(var4, var5)))) {
							this.altArray.add(new PlaceholderAlt(var4, var5));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveGuiSettings(File var0) {
		try {
			PrintWriter var1 = new PrintWriter(new FileWriter(var0));

			var1.println("#This is the file for changing everything that you see in the ingame gui,");
			var1.println("#except for the ArrayList and the client name.");
			var1.println("#If you start a line with a # it will be ignored as a comment.");
			var1.println("#If you want to add special things, like the players name, or current position");
			var1.println("#just type out the words that are listed down below, do note that they are case sensitive.");
			var1.println("#{POS-X} | {POS-Y} | {POS-Z} | {FPS} | {ITEM} | {USERNAME} | {BIOME}");
			var1.println("#{HEALTH} | {ARMOUR} | {HUNGER} | {LEVEL} | {TIME} | {LIGHT}");
			var1.println("#And if you want special colours, just use the normal Minecraft colour codes, but instead of using the § character");
			var1.println("#just put & infront of it.");
			var1.println("#Link to the colour codes: http://www.minecraftwiki.net/wiki/Formatting_codes");
			var1.println("#Example:");
			var1.println("#Name: &e{USERNAME}");
			var1.println("#Health: &c{HEALTH}");
			var1.println("#Level: &2{LEVEL}");
			var1.println("");

			var1.println("&fX: {POS-X}");
			var1.println("&fY: {POS-Y}");
			var1.println("&fZ: {POS-Z}");
			var1.println("&fFPS: {FPS}");

			var1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadGuiSettings(File var0) {
		try {
			if(var0.exists()) {
				this.utils.log("File", var0.getAbsolutePath());

				BufferedReader var1 = new BufferedReader(new FileReader(var0));

				for(String var2 = ""; (var2 = var1.readLine()) != null;) {
					try {
						if(!(this.guiSettingsArray.contains(var2.replace("&", "§"))) && !(var2.startsWith("#")) && !(var2.isEmpty())) {
							this.guiSettingsArray.add(var2.replace("&", "§"));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				this.saveGuiSettings(var0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveAlts(File var0) {
		try {
			PrintWriter printWriter = new PrintWriter(new FileWriter(var0));

			for(int i1 = 0; i1 < this.altArray.size(); i1++) {
				PlaceholderAlt alt = (PlaceholderAlt)this.altArray.get(i1);

				printWriter.println(alt.username + ":" + alt.password);
			}

			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadWaypoints(File var0) {
		try {
			if(var0.exists()) {
				this.utils.log("File", var0.getAbsolutePath());

				BufferedReader var1 = new BufferedReader(new FileReader(var0));

				for(String var2 = ""; (var2 = var1.readLine()) != null;) {
					try {
						String[] var3 = var2.split(":");

						if(!(this.waypointArray.contains(new PlaceholderWaypoint(var3[0], 
								Integer.parseInt(var3[1]), 
								Integer.parseInt(var3[2]), 
								Integer.parseInt(var3[3]),
								Integer.parseInt(var3[4]),
								var3[5])))) {
							this.waypointArray.add(new PlaceholderWaypoint(var3[0], 
									Integer.parseInt(var3[1]), 
									Integer.parseInt(var3[2]), 
									Integer.parseInt(var3[3]),
									Integer.parseInt(var3[4]),
									var3[5]));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveWaypoints(File var0) {
		try {
			PrintWriter var1 = new PrintWriter(new FileWriter(var0));

			for(int var2 = 0; var2 < this.waypointArray.size(); var2++) {
				PlaceholderWaypoint var3 = (PlaceholderWaypoint)this.waypointArray.get(var2);

				var1.println(var3.name + ":" + var3.x + ":" + var3.y + ":" + var3.z + ":" + var3.color + ":" + var3.ip);
			}

			var1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadBaseFile(File var0, ArrayList var1, boolean var2) {
		try {
			if(var0.exists()) {
				this.utils.log("File", var0.getAbsolutePath());

				BufferedReader var3 = new BufferedReader(new FileReader(var0));

				for(String var4 = ""; (var4 = var3.readLine()) != null;) {
					try {
						if(!(var1.contains(var2 ? var4 : Integer.parseInt(var4)))) {
							var1.add(var2 ? var4 : Integer.parseInt(var4));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveBaseFile(File var0, ArrayList var1, boolean var2) {
		try {
			PrintWriter var3 = new PrintWriter(new FileWriter(var0));

			for(int var4 = 0; var4 < var1.size(); var4++) {
				var3.println(var1.get(var4));
			}

			var3.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}