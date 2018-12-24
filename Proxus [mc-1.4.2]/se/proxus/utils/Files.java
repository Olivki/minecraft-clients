package se.proxus.utils;

import java.io.*;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import se.proxus.*;
import se.proxus.hacks.Base_Hack;
import se.proxus.hacks.Base_Options;

public class Files extends Main {

	public File
	baseFolder = null,
	accounts = null,
	proxies = null,
	keyBinds = null,
	settings = null,
	hackStates = null,
	config = null,
	hackConfig = null;

	public void initFiles() {
		baseFolder = new File(this.mc.getMinecraftDir(), "Proxus");
		baseFolder.mkdirs();

		keyBinds = new File(this.baseFolder, "Keybinds.txt");
		
		settings = new File(this.baseFolder, "Settings.txt");

		hackStates = new File(this.baseFolder, "Hack states.txt");
		
		config = new File(this.baseFolder, "Config.txt");
		
		hackConfig = new File(this.baseFolder, "Hack config.txt");
		
		loadBaseFile(new File(this.baseFolder, "xRay.txt"), this.vars.XRAY_ARRAY, false);

		loadKeys();
		loadStates();
		loadConfig();
		loadHackConfig();
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
			/*vars.addError("Error on loading the \"" + file.getName() + "\" file. [" + e.getMessage() + "]");
			files.saveBaseFile(new File(files.baseFolder, "Errors.txt"), vars.errorArray, true);*/
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
			/*vars.addError("Error on saving the \"" + file.getName() + "\" file. [" + e.getMessage() + "]");
			files.saveBaseFile(new File(files.baseFolder, "Errors.txt"), vars.errorArray, true);*/
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

						for(Base_Hack bHack : this.hacks.hackArray) {
							if(name.equalsIgnoreCase(bHack.getName())) {
								bHack.setKey(s[1], false);
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
			/*vars.addError("Error on saving the \"" + keyBinds.getName() + "\" file. [" + e.getMessage() + "]");
			files.saveBaseFile(new File(files.baseFolder, "Errors.txt"), vars.errorArray, true);*/
		}
	}

	public void saveKeys() {
		try {
			PrintWriter printWriter = new PrintWriter(new FileWriter(keyBinds));

			for(int i1 = 0; i1 < this.hacks.hackArray.size(); i1++) {
				Base_Hack bHack = (Base_Hack)this.hacks.hackArray.get(i1);

				printWriter.println("Key_" + bHack.getName() + " : " + bHack.getKey().toUpperCase());
			}

			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
			/*vars.addError("Error on saving the \"" + keyBinds.getName() + "\" file. [" + e.getMessage() + "]");
			files.saveBaseFile(new File(files.baseFolder, "Errors.txt"), vars.errorArray, true);*/
		}
	}

	/** The hack states. **/
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
		}
	}
	
	/** The hack config. **/
	public void loadHackConfig() {
		try {
			if(hackConfig.exists()) {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(hackConfig));

				for(String s1 = ""; (s1 = bufferedReader.readLine()) != null;) {
					try {
						String[] s = s1.split(" : ");

						for(Base_Hack bHack : this.hacks.hackArray) {
							if(s[0].equalsIgnoreCase(bHack.getName())) {
								bHack.setColor(s[1].charAt(0));
								bHack.setState(Boolean.parseBoolean(s[2]));
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
		}
	}

	public void saveHackConfig() {
		try {
			PrintWriter printWriter = new PrintWriter(new FileWriter(hackConfig));

			for(int i1 = 0; i1 < this.hacks.hackArray.size(); i1++) {
				Base_Hack bHack = (Base_Hack)this.hacks.hackArray.get(i1);

				printWriter.println(bHack.getName() + ", " + bHack.getCol() + ", " + bHack.getState() + ", " + bHack.getType() + ", " + bHack.getDescription());
			}

			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** The config. **/
	public void loadConfig() {
		try {
			if(config.exists()) {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(config));

				for(String s1 = ""; (s1 = bufferedReader.readLine()) != null;) {
					try {
						String[] var1 = s1.split(" : ");
						
						if(var1[0].equalsIgnoreCase("Name")) {
							this.vars.CLIENT_NAME = var1[1];
						}  if(var1[0].equalsIgnoreCase("Version")) {
							this.vars.CLIENT_VERSION = var1[1];
						}  if(var1[0].equalsIgnoreCase("Mine speed")) {
							this.hacks.HACK_MINER.setOption(Base_Options.MINE_SPEED, Float.valueOf(var1[1]));
						}  if(var1[0].equalsIgnoreCase("Mine delay")) {
							this.hacks.HACK_MINER.setOption(Base_Options.MINE_DELAY, Integer.valueOf(var1[1]));
						}  if(var1[0].equalsIgnoreCase("Brightness level")) {
							this.hacks.HACK_BRIGHTNESS.setOption(Base_Options.BRIGHTNESS_LEVEL, Float.valueOf(var1[1]));
						}  if(var1[0].equalsIgnoreCase("xRay brightness")) {
							this.hacks.HACK_XRAY.setOption(Base_Options.XRAY_BRIGHTNESS, Float.valueOf(var1[1]));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				saveConfig();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveConfig() {
		try {
			PrintWriter printWriter = new PrintWriter(new FileWriter(config));

			printWriter.println("Name : " + this.vars.CLIENT_NAME);
			printWriter.println("Version : " + this.vars.CLIENT_VERSION);
			printWriter.println("Mine speed : " + ((Float)this.hacks.HACK_MINER.getOption(Base_Options.MINE_SPEED)).floatValue());
			printWriter.println("Mine delay : " + ((Integer)this.hacks.HACK_MINER.getOption(Base_Options.MINE_DELAY)).intValue());
			printWriter.println("Brightness level : " + ((Float)this.hacks.HACK_BRIGHTNESS.getOption(Base_Options.BRIGHTNESS_LEVEL)).floatValue());
			printWriter.println("xRay brightness : " + ((Float)this.hacks.HACK_XRAY.getOption(Base_Options.XRAY_BRIGHTNESS)).floatValue());

			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}