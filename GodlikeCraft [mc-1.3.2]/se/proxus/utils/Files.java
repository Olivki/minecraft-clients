package se.proxus.utils;

import java.io.*;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import se.proxus.*;
import se.proxus.hacks.Base_Hack;
import se.proxus.panels.Base_Panel;
import se.proxus.panels.Base_Panels;

public class Files extends GodlikeCraft {

	public File
	baseFolder = null,
	botFolder = null,
	accounts = null,
	proxies = null,
	keyBinds = null,
	auraSettings = null,
	panelSettings = null,
	settings = null,
	hackStates = null;

	public void initFiles() {
		baseFolder = new File(this.mc.getMinecraftDir(), "GodlikeCraft");
		baseFolder.mkdirs();

		botFolder = new File(this.baseFolder, "Bots");
		botFolder.mkdirs();

		keyBinds = new File(this.baseFolder, "Keybinds.txt");

		auraSettings = new File(this.baseFolder, "Aura settings.txt");

		panelSettings = new File(this.baseFolder, "Panel settings.txt");

		settings = new File(this.baseFolder, "Settings.txt");

		hackStates = new File(this.baseFolder, "Hack states.txt");

		loadBaseFile(new File(this.baseFolder, "Friends.txt"), this.vars.friendArray, true);
		loadBaseFile(new File(this.baseFolder, "xRay.txt"), this.vars.xrayArray, false);

		loadKeys();
		loadAuraSettings();
		loadStates();
	}

	/** Base loading and saving methods. **/
	public void loadBaseFile(File file, ArrayList array, boolean normal) {
		try {
			if(file.exists()) {
				array.clear();
				BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

				for(String s1 = ""; (s1 = bufferedReader.readLine()) != null;) {
					try {
						array.add(normal ? s1.trim() : Integer.parseInt(s1));

						if(!file.getName().equalsIgnoreCase("Logs.txt")) {
							this.utils.log("[File] Name \"" + file.getName() + "\", String \"" + s1 + "\".");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			vars.addError("Error on loading the \"" + file.getName() + "\" file. [" + e.getMessage() + "]");
			files.saveBaseFile(new File(files.baseFolder, "Errors.txt"), vars.errorArray, true);
		}
	}

	public void saveBaseFile(File file, ArrayList array, boolean normal) {
		try {
			PrintWriter printWriter = new PrintWriter(new FileWriter(file));

			for(int i1 = 0; i1 < array.size(); i1++) {
				printWriter.println(array.get(i1));

				if(!file.getName().equalsIgnoreCase("Logs.txt")) {
					this.utils.log("[File] Name \"" + file.getName() +  "\", String \"" + array.get(i1) + "\".");
				}
			}

			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
			vars.addError("Error on saving the \"" + file.getName() + "\" file. [" + e.getMessage() + "]");
			files.saveBaseFile(new File(files.baseFolder, "Errors.txt"), vars.errorArray, true);
		}
	}

	/** The keybinds. **/
	public void loadKeys() {
		try {
			if(keyBinds.exists()) {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(keyBinds));

				for(String s1 = ""; (s1 = bufferedReader.readLine()) != null;) {
					try {
						String[] s = s1.split(" : ");
						String name = s[0].replace("Key_", "");
						int key = Keyboard.getKeyIndex(s[1]);

						for(Base_Hack bHack : this.hacks.hackArray) {
							if(name.equalsIgnoreCase(bHack.getName())) {
								bHack.setKey(key, false);
							}
						}	
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				saveKeys();
			}
		} catch (Exception e) {
			e.printStackTrace();
			vars.addError("Error on saving the \"" + keyBinds.getName() + "\" file. [" + e.getMessage() + "]");
			files.saveBaseFile(new File(files.baseFolder, "Errors.txt"), vars.errorArray, true);
		}
	}

	public void saveKeys() {
		try {
			PrintWriter printWriter = new PrintWriter(new FileWriter(keyBinds));

			for(int i1 = 0; i1 < this.hacks.hackArray.size(); i1++) {
				Base_Hack bHack = (Base_Hack)this.hacks.hackArray.get(i1);

				printWriter.println("Key_" + bHack.getName() + " : " + Keyboard.getKeyName(bHack.getKey()).toUpperCase());
			}

			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
			vars.addError("Error on saving the \"" + keyBinds.getName() + "\" file. [" + e.getMessage() + "]");
			files.saveBaseFile(new File(files.baseFolder, "Errors.txt"), vars.errorArray, true);
		}
	}

	/** The aura settings. **/
	public void loadAuraSettings() {
		try {
			if(auraSettings.exists()) {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(auraSettings));

				for(String s1 = ""; (s1 = bufferedReader.readLine()) != null;) {
					try {
						String[] s = s1.split(" : ");

						if(s[0].equalsIgnoreCase("Aimbot mode")) {
							this.vars.aimbotMode = Integer.parseInt(s[1]);
						} if(s[0].equalsIgnoreCase("KitPvP String")) {
							this.vars.kitPvPString = s[1];
						} if(s[0].equalsIgnoreCase("Soup item drop")) {
							this.vars.dropItemsInAutoSoup = Boolean.parseBoolean(s[1]);
						} if(s[0].equalsIgnoreCase("Aura blocking")) {
							this.vars.isAuraBlocking = Boolean.parseBoolean(s[1]);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				saveAuraSettings();
			}
		} catch (Exception e) {
			e.printStackTrace();
			vars.addError("Error on loading the \"" + auraSettings.getName() + "\" file. [" + e.getMessage() + "]");
			files.saveBaseFile(new File(files.baseFolder, "Errors.txt"), vars.errorArray, true);
		}
	}

	public void saveAuraSettings() {
		try {
			PrintWriter printWriter = new PrintWriter(new FileWriter(auraSettings));

			printWriter.println("Aimbot mode : " + this.vars.aimbotMode);
			printWriter.println("KitPvP String : " + this.vars.kitPvPString);
			printWriter.println("Soup item drop : " + this.vars.dropItemsInAutoSoup);
			printWriter.println("Aura blocking : " + this.vars.isAuraBlocking);

			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
			vars.addError("Error on saving the \"" + auraSettings.getName() + "\" file. [" + e.getMessage() + "]");
			files.saveBaseFile(new File(files.baseFolder, "Errors.txt"), vars.errorArray, true);
		}
	}

	/** The panel settings. **/
	public void loadPanelSettings() {
		try {
			if(panelSettings.exists()) {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(panelSettings));

				for(String s1 = ""; (s1 = bufferedReader.readLine()) != null;) {
					try {
						String[] s = s1.split(", ");

						for(Base_Panel var1 : Base_Panels.panelBeforeArray) {
							if(var1.name.equalsIgnoreCase(s[0])) {
								var1.x = Integer.parseInt(s[1]);
								var1.y = Integer.parseInt(s[2]);
								var1.x2 = Integer.parseInt(s[3]);
								var1.y2 = Integer.parseInt(s[4]);
								var1.open = Boolean.parseBoolean(s[5]);

								if(Boolean.parseBoolean(s[6])) {
									if(!(Base_Panels.panelArray.contains(var1))) {
										Base_Panels.panelArray.add(var1);
									}
								}

								this.utils.log("[Panels] " + s1);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				savePanelSettings();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void savePanelSettings() {
		try {
			PrintWriter printWriter = new PrintWriter(new FileWriter(panelSettings));

			for(int var1 = 0; var1 < Base_Panels.panelBeforeArray.size(); var1++) {
				Base_Panel var2 = (Base_Panel)Base_Panels.panelBeforeArray.get(var1);

				printWriter.println(var2.name + ", " + var2.x + ", " + var2.y + ", " + var2.x2 + ", " + var2.y2 + ", " + var2.open + ", " + Base_Panels.panelArray.contains(var2));
			}

			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** The keybinds. **/
	public void loadStates() {
		try {
			if(hackStates.exists()) {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(hackStates));

				for(String s1 = ""; (s1 = bufferedReader.readLine()) != null;) {
					try {
						String[] s = s1.split(" : ");

						for(Base_Hack bHack : this.hacks.hackArray) {
							if(s[0].equalsIgnoreCase(bHack.getName())) {
								bHack.setState(Boolean.parseBoolean(s[1]));
							}
						}	
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				saveStates();
			}
		} catch (Exception e) {
			e.printStackTrace();
			vars.addError("Error on loading the \"" + hackStates.getName() + "\" file. [" + e.getMessage() + "]");
			files.saveBaseFile(new File(files.baseFolder, "Errors.txt"), vars.errorArray, true);
		}
	}

	public void saveStates() {
		try {
			PrintWriter printWriter = new PrintWriter(new FileWriter(hackStates));

			for(int i1 = 0; i1 < this.hacks.hackArray.size(); i1++) {
				Base_Hack bHack = (Base_Hack)this.hacks.hackArray.get(i1);

				printWriter.println(bHack.getName() + " : " + bHack.getState());
			}

			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
			vars.addError("Error on saving the \"" + hackStates.getName() + "\" file. [" + e.getMessage() + "]");
			files.saveBaseFile(new File(files.baseFolder, "Errors.txt"), vars.errorArray, true);
		}
	}
}