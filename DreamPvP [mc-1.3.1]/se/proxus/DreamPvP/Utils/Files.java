package se.proxus.DreamPvP.Utils;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.lwjgl.input.Keyboard;

import se.proxus.DreamPvP.Mods.Base_Mod;
import se.proxus.DreamPvP.Utils.Placeholders.*;

public class Files extends Settings {

	public static File
	baseFolder,
	waypoints,
	macros,
	keys,
	modStates,
	alts,
	settings;

	public void init() {
		baseFolder = new File(dp.mc.getMinecraftDir(), "Dream PvP");
		baseFolder.mkdirs();

		waypoints = new File(baseFolder, "Waypoints.txt");
		macros = new File(baseFolder, "Macros.txt");
		keys = new File(baseFolder, "Keys.txt");
		modStates = new File(baseFolder, "Mod states.txt");
		alts = new File(baseFolder, "Alts.txt");
		settings = new File(baseFolder, "Settings.txt");

		loadBaseFile(new File(baseFolder, "Friends.txt"), friendArray, true);
		loadBaseFile(new File(baseFolder, "Enemys.txt"), enemyArray, true);
		loadBaseFile(new File(baseFolder, "xRay.txt"), xrayArray, false);

		loadWaypoint();
		loadMacros();
		loadKeys();
		//loadModStates();
		loadAlts();
		loadSettings();
	}

	/** Base loading and saving methods. **/
	public void loadBaseFile(File file, ArrayList array, boolean normal) {
		try {
			if(file.exists()) {
				array.clear();
				BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

				for(String s1 = ""; (s1 = bufferedReader.readLine()) != null;) {
					try {
						array.add(normal ? s1 : Integer.parseInt(s1));

						if(!file.getName().equalsIgnoreCase("Logs.txt")) {
							dp.utils.log("[File] Name \"" + file.getName() + "\", String \"" + s1 + "\".");
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

	public void saveBaseFile(File file, ArrayList array, boolean normal) {
		try {
			PrintWriter printWriter = new PrintWriter(new FileWriter(file));

			for(int i1 = 0; i1 < array.size(); i1++) {
				printWriter.println(array.get(i1));

				if(!file.getName().equalsIgnoreCase("Logs.txt")) {
					dp.utils.log("[File] Name \"" + file.getName() +  "\", String \"" + array.get(i1) + "\".");
				}
			}

			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/** More 'hardcoded' methods of saving and loading. **/
	/** Waypoints. **/
	public void loadWaypoint() {
		try {
			if(waypoints.exists()) {
				waypointArray.clear();
				BufferedReader bufferedReader = new BufferedReader(new FileReader(waypoints));

				for(String s1 = ""; (s1 = bufferedReader.readLine()) != null;) {
					try {
						String[] s = s1.split(", ");
						double x = Double.parseDouble(s[0]), y = Double.parseDouble(s[1]), z = Double.parseDouble(s[2]);
						float r = Float.parseFloat(s[3]), g = Float.parseFloat(s[4]), b = Float.parseFloat(s[5]);
						String ip = s[6], name = s[7];

						waypointArray.add(new u_Waypoint(x, y, z, r, g, b, ip, name));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveWaypoints() {
		try {
			PrintWriter printWriter = new PrintWriter(new FileWriter(waypoints));

			for(int i1 = 0; i1 < waypointArray.size(); i1++) {
				u_Waypoint wt = (u_Waypoint)waypointArray.get(i1);

				printWriter.println(wt.x + ", " + wt.y + ", " + wt.z + ", " + wt.r + ", " + wt.g + ", " + wt.b + ", " + wt.ip + ", " + wt.name);
			}

			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Macros. **/
	public void loadMacros() {
		try {
			if(macros.exists()) {
				macroArray.clear();
				BufferedReader bufferedReader = new BufferedReader(new FileReader(macros));

				for(String s1 = ""; (s1 = bufferedReader.readLine()) != null;) {
					try {
						String[] s = s1.split(", ");
						String name = s[0], command = s[1];
						int key = Keyboard.getKeyIndex(s[2]);

						macroArray.add(new Key(name, command, key));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveMacros() {
		try {
			PrintWriter printWriter = new PrintWriter(new FileWriter(macros));

			for(int i1 = 0; i1 < macroArray.size(); i1++) {
				Key key = (Key)macroArray.get(i1);

				printWriter.println(key.getName() + ", " + key.getCommand() + ", " + Keyboard.getKeyName(key.getKey()).toUpperCase());
			}

			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Keybinds for the mods. **/
	public void loadKeys() {
		try {
			if(keys.exists()) {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(keys));

				for(String s1 = ""; (s1 = bufferedReader.readLine()) != null;) {
					try {
						String[] s = s1.split(" : ");
						String name = s[0].replace("Key_", "");
						int key = Keyboard.getKeyIndex(s[1]);

						for(Base_Mod bMod : dp.bModList.modArray) {
							if(name.equalsIgnoreCase(bMod.getName())) {
								bMod.setKey(key);
							}
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

	public void saveKeys() {
		try {
			PrintWriter printWriter = new PrintWriter(new FileWriter(keys));

			for(int i1 = 0; i1 < dp.bModList.modArray.size(); i1++) {
				Base_Mod bMod = (Base_Mod)dp.bModList.modArray.get(i1);

				printWriter.println("Key_" + bMod.getName() + " : " + Keyboard.getKeyName(bMod.getKey()).toUpperCase());
			}

			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** The states of the mods. **/
	public void loadModStates() {
		try {
			if(modStates.exists()) {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(modStates));

				for(String s1 = ""; (s1 = bufferedReader.readLine()) != null;) {
					try {
						String[] split = s1.split(" : ");
						String name = split[0];
						boolean state = Boolean.parseBoolean(split[1]);

						for(Base_Mod bMod : dp.bModList.modArray) {
							if(name.equalsIgnoreCase(bMod.getName())) {
								bMod.setState(state);
							}
						}

						dp.utils.log("[File] " + s1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveModStates() {
		try {
			PrintWriter printWriter = new PrintWriter(new FileWriter(modStates));

			for(int i1 = 0; i1 < dp.bModList.modArray.size(); i1++) {
				Base_Mod bMod = (Base_Mod)dp.bModList.modArray.get(i1);

				printWriter.println(bMod.getName() + " : " + bMod.getState());
			}

			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Alts. **/
	public void loadAlts() {
		try {
			if(alts.exists()) {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(alts));

				for(String s1 = ""; (s1 = bufferedReader.readLine()) != null;) {
					try {
						String[] split = s1.split(":");
						String name = split[0], password = split[1];

						if(!altArray.contains(new u_Alt(name, password))) {
							altArray.add(new u_Alt(name, password));
						}

						dp.utils.log("[File] " + s1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveAlts() {
		try {
			PrintWriter printWriter = new PrintWriter(new FileWriter(alts));

			for(int i1 = 0; i1 < altArray.size(); i1++) {
				u_Alt alt = (u_Alt)altArray.get(i1);

				printWriter.println(alt.name + ":" + alt.password);
			}

			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Settings. **/
	public void loadSettings() {
		try {
			if(settings.exists()) {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(settings));

				for(String s1 = ""; (s1 = bufferedReader.readLine()) != null;) {
					try {
						String[] split = s1.split(" : ");

						if(split[0].equalsIgnoreCase("ESP Red")) {
							bC1 = Float.parseFloat(split[1]);
						} if(split[0].equalsIgnoreCase("ESP Green")) {
							bC2 = Float.parseFloat(split[1]);
						} if(split[0].equalsIgnoreCase("ESP Blue")) {
							bC3 = Float.parseFloat(split[1]);
						} if(split[0].equalsIgnoreCase("ESP Check size")) {
							espCheckSize = Float.parseFloat(split[1]);
						} if(split[0].equalsIgnoreCase("ESP Mode")) {
							espMode = Integer.parseInt(split[1]);
						} if(split[0].equalsIgnoreCase("Aura delay")) {
							dp.bModList.playerAura.auraDelay = Float.parseFloat(split[1]);
						} if(split[0].equalsIgnoreCase("Minespeed")) {
							dp.bModList.miner.mineSpeed = Float.parseFloat(split[1]);
						} if(split[0].equalsIgnoreCase("Flyspeed")) {
							flySpeed = Double.parseDouble(split[1]);
						} if(split[0].equalsIgnoreCase("Cham Red 1")) {
							cR1 = Float.parseFloat(split[1]);
						} if(split[0].equalsIgnoreCase("Cham Green 1")) {
							cG1 = Float.parseFloat(split[1]);
						} if(split[0].equalsIgnoreCase("Cham Blue 1")) {
							cB1 = Float.parseFloat(split[1]);
						} if(split[0].equalsIgnoreCase("Cham Red 2")) {
							cR2 = Float.parseFloat(split[1]);
						} if(split[0].equalsIgnoreCase("Cham Green 2")) {
							cG2 = Float.parseFloat(split[1]);
						} if(split[0].equalsIgnoreCase("Cham Blue 2")) {
							cB2 = Float.parseFloat(split[1]);
						} if(split[0].equalsIgnoreCase("Debug chat")) {
							debugChat = Boolean.parseBoolean(split[1]);
						} if(split[0].equalsIgnoreCase("Chat mode")) {
							chatMode = Integer.parseInt(split[1]);
						} if(split[0].equalsIgnoreCase("Radar mode")) {
							radarMode = Integer.parseInt(split[1]);
						} if(split[0].equalsIgnoreCase("Aimbot mode")) {
							aimbotMode = Integer.parseInt(split[1]);
						} if(split[0].equalsIgnoreCase("Button mode")) {
							buttonMode = Integer.parseInt(split[1]);
						} if(split[0].equalsIgnoreCase("Button Red")) {
							bR = Float.parseFloat(split[1]);
						} if(split[0].equalsIgnoreCase("Button Green")) {
							bG = Float.parseFloat(split[1]);
						} if(split[0].equalsIgnoreCase("Button Blue")) {
							bB = Float.parseFloat(split[1]);
						}

						dp.utils.log("[File] " + s1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveSettings() {
		try {
			DateFormat DTEformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			PrintWriter printWriter = new PrintWriter(new FileWriter(settings));

			printWriter.println("/* Dream PvP Settings file, last changed on : " + DTEformat.format(cal.getTime()) + " */");
			printWriter.println("ESP Red : " + bC1);
			printWriter.println("ESP Green : " + bC2);
			printWriter.println("ESP Blue : " + bC3);
			printWriter.println("ESP Check size : " + espCheckSize);
			printWriter.println("ESP Mode : " + espMode);
			printWriter.println("Aura delay : " + dp.bModList.playerAura.auraDelay);
			printWriter.println("Minespeed : " + dp.bModList.miner.mineSpeed);
			printWriter.println("Flyspeed : " + flySpeed);
			printWriter.println("Cham Red 1 : " + cR1);
			printWriter.println("Cham Green 1 : " + cG1);
			printWriter.println("Cham Blue 1 : " + cB1);
			printWriter.println("Cham Red 2 : " + cR2);
			printWriter.println("Cham Green 2 : " + cG2);
			printWriter.println("Cham Blue 2 : " + cB2);
			printWriter.println("Debug chat : " + debugChat);
			printWriter.println("Chat mode : " + chatMode);
			printWriter.println("Radar mode : " + radarMode);
			printWriter.println("Aimbot mode : " + aimbotMode);
			printWriter.println("Button mode : " + buttonMode);
			printWriter.println("Button Red : " + bR);
			printWriter.println("Button Green : " + bG);
			printWriter.println("Button Blue : " + bB);
			
			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}