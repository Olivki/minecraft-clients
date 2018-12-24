package se.proxus.utils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.*;
import java.util.*;

import org.lwjgl.Sys;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

import se.proxus.*;
import se.proxus.mods.*;

public class Utils extends Pendrum {

	private static String key = "asd";

	public void log(String var0, Object var1) {
		DateFormat var2 = new SimpleDateFormat("yy/mm/dd/hh:mm:ss");
		Calendar var3 = Calendar.getInstance();
		String var4 = "[" + var2.format(var3.getTime()) + "] [Pendrum] [" + var0 + "] " + var1;

		System.out.println(var4);
		this.sett.logArray.add(var4);
		this.sett.saveBaseFile(new File(this.mainFolder, "Logs.cfg"), this.sett.logArray, true);
	}

	public void addMessage(String var1) {
		this.wrapper.getPlayer().addChatMessage("[§ePendrum§f]: " + var1);
	}

	public void sendPacket(Packet var1) {
		this.wrapper.getSendQueue().addToSendQueue(var1);
	}

	public void sendMessage(String var1) {
		this.sendPacket(new Packet3Chat(var1));
	}

	public void playSound() {
		this.wrapper.getMinecraft().sndManager.playSoundFX("random.click", 1.0F, 1.0F);
	}

	public float[] getRGBA(int var1) {
		return (new float[] {
				(float)(var1 >> 16 & 255) / 255.0F,
				(float)(var1 >> 8 & 255) / 255.0F,
				(float)(var1 & 255) / 255.0F,
				(float)(var1 >> 24 & 255) / 255.0F,
				(float)(var1 >> 24 & 0xFF) / 255F
		});
	}
	
	public String getSyntaxedString(String var0) {
		String var1 = var0;
		
		try {
			var0 = var0.replace("{POS-X}", "" + (int)this.wrapper.getPlayer().posX);
			var0 = var0.replace("{POS-Y}", "" + (int)this.wrapper.getPlayer().posY);
			var0 = var0.replace("{POS-Z}", "" + (int)this.wrapper.getPlayer().posZ);
			var0 = var0.replace("{FPS}", "" + this.wrapper.getMinecraft().getDebugFPS());
			var0 = var0.replace("{ITEM}", "" + (this.wrapper.getPlayer().getCurrentEquippedItem() == null ? "N/A" : this.wrapper.getPlayer().getCurrentEquippedItem().getDisplayName()));
			var0 = var0.replace("{USERNAME}", this.wrapper.getMinecraft().session.username);
			var0 = var0.replace("{BIOME}", this.getBiomeName());
			var0 = var0.replace("{HEALTH}", "" + this.getFixedNonZeroString("" + this.wrapper.getPlayer().getHealth()));
			var0 = var0.replace("{ARMOUR}", "" + this.getFixedNonZeroString("" + this.wrapper.getPlayer().getTotalArmorValue()));
			var0 = var0.replace("{LEVEL}", "" + this.wrapper.getPlayer().experienceLevel);
			var0 = var0.replace("{TIME}", "N/A");
			var0 = var0.replace("{LIGHT}", "N/A");
		} catch(Exception e) {
			e.printStackTrace();
			var1 = var0;
		}
		
		return var0;
	}

	public String getFixedNonZeroString(String var0) {
		String var1 = "";

		try {
			if(var0.length() <= 1) {
				var1 = "0" +  var0;
			} else {
				var1 = var0;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		return var1;
	}

	public boolean isPlayerOnSameServer(String var0) {
		boolean var1 = false;

		try {
			for(int var2 = 0; var2 < this.wrapper.getSendQueue().playerInfoList.size(); var2++) {
				GuiPlayerInfo var3 = (GuiPlayerInfo)this.wrapper.getSendQueue().playerInfoList.get(var2);

				if(var3.name.equalsIgnoreCase(var0)) {
					var1 = true;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			var1 = false;
		}

		return var1;
	}

	public double entityDistance(Entity var0, Entity var1) {
		double var2 = var1.posX - var0.posX;
		double var4 = var1.posY - var0.posY;
		double var6 = var1.posZ - var0.posZ;
		double var8 = Math.sqrt(var2 * var2 + var4 * var4 + var6 * var6);
		return var8;
	}

	public String useCommandAlgorithm(String var0) {
		String var1 = "";

		try {
			var1 = var0.toLowerCase().substring(0, 2) + var0.toLowerCase().charAt(var0.length() - 1);
		} catch(Exception e) {
			e.printStackTrace();
		}

		return var1;
	}

	public String encryptText(String text) {
		long finalKey = 0;

		for(int i = 0; i < key.length(); i++) {
			long tempKey = key.charAt(i);
			tempKey *= 128;
			finalKey += tempKey;
		}

		Random generator = new Random(finalKey);

		String returnString = "";

		for(int i = 0; i < text.length(); i++) {
			int temp = (int)text.charAt(i);
			temp += generator.nextInt(95);

			if(temp > 126) {
				temp -= 95;
			}

			returnString += (char)temp;
		}

		return returnString;
	}

	public String decryptText(String text) {
		long finalKey = 0;

		for(int i = 0; i < key.length(); i++) {
			long tempKey = key.charAt(i);
			tempKey *= 128;
			finalKey += tempKey;
		}

		Random generator = new Random(finalKey);
		String returnString = "";

		for(int i = 0; i < text.length(); i++) {
			int temp = (int)text.charAt(i);

			temp -= generator.nextInt(95);

			if(temp < 36) {
				temp+= 95;
			} else if(temp > 126) {
				temp -= 95;
			}

			returnString += (char)temp;
		}

		return returnString;
	}

	public Object getFixedObject(String var0) {
		Object var1 = null;

		try {
			if(var0.endsWith("F")) {
				var1 = Float.parseFloat(var0.replace("F", ""));
			} if(var0.endsWith("I")) {
				var1 = Integer.parseInt(var0.replace("I", ""));
			} if(var0.endsWith("B")) {
				var1 = Boolean.parseBoolean(var0.replace("B", ""));
			} if(var0.endsWith("L")) {
				var1 = Long.parseLong(var0.replace("L", ""));
			} if(var0.endsWith("D")) {
				var1 = Double.parseDouble(var0.replace("D", ""));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		return var1;
	}

	public String setFixedObject(Object var0) {
		String var1 = null;

		try {
			if(var0 instanceof Float) {
				var1 = var0 + "F";
			} if(var0 instanceof Integer) {
				var1 = var0 + "I";
			} if(var0 instanceof Boolean) {
				var1 = var0 + "B";
			} if(var0 instanceof Long) {
				var1 = var0 + "L";
			} if(var0 instanceof Double) {
				var1 = var0 + "D";
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		return var1;
	}

	public String getBiomeName() {
		int x = MathHelper.floor_double(this.wrapper.getPlayer().posX);
		int y = MathHelper.floor_double(this.wrapper.getPlayer().posY);
		int z = MathHelper.floor_double(this.wrapper.getPlayer().posZ);

		String biome = "";

		if(this.wrapper.getWorld() != null && this.wrapper.getWorld().blockExists(x, y, z)) {
			Chunk chunk = this.wrapper.getWorld().getChunkFromBlockCoords(x, z);

			biome = chunk.getBiomeGenForWorldCoords(x & 15, z & 15, this.wrapper.getWorld().getWorldChunkManager()).biomeName;
		}

		return biome;
	}

	public String [] wrapText(String text, int len) {
		if(text == null) {
			return new String [] {};
		}

		if(len <= 0) {
			return new String [] {text};
		}

		if(text.length() <= len) {
			return new String [] {text};
		}

		char[] chars = text.toCharArray();

		Vector lines = new Vector();

		StringBuffer line = new StringBuffer();

		StringBuffer word = new StringBuffer();

		for(int i = 0; i < chars.length; i++) {
			word.append(chars[i]);

			if(chars[i] == ' ') {
				if((line.length() + word.length()) > len) {
					lines.add(line.toString());
					line.delete(0, line.length());
				}

				line.append(word);
				word.delete(0, word.length());
			}
		}

		if(word.length() > 0) {
			if((line.length() + word.length()) > len) {
				lines.add(line.toString());
				line.delete(0, line.length());
			}

			line.append(word);
		}

		if(line.length() > 0) {
			lines.add(line.toString());
		}

		String[] ret = new String[lines.size()];

		int c = 0;

		for(Enumeration e = lines.elements(); e.hasMoreElements(); c++) {
			ret[c] = (String) e.nextElement();
		}

		return ret;
	}

	public int blockNameToID(String var0) {
		var0 = var0.toLowerCase();
		Block[] var1 = Block.blocksList;
		int var2 = var1.length;

		for(int var3 = 0; var3 < var2; var3++) {
			Block var4 = var1[var3];

			if(var4 != null) {
				String var5 = var4.getBlockName();

				if(var5 != null) {
					var5 = var5.toLowerCase().replaceAll("tile.", "");

					if(var5.contains("ore")) {
						var5 = var5.toLowerCase().replaceAll("ore", "");
						var5 = var5 + "-ore";
					}

					if(var5.contains("block")) {
						var5 = var5.toLowerCase().replaceAll("block", "");
						var5 = var5 + "-block";
					}

					if(var5.equalsIgnoreCase(var0)) {
						return var4.blockID;
					}
				}
			}
		}

		return -1;
	}

	public String blockIDToName(int var0) {
		Block[] var1 = Block.blocksList;
		int var2 = var1.length;

		for(int var3 = 0; var3 < var2; var3++) {
			Block var4 = var1[var3];

			if (var4 != null) {
				String var5 = var4.getBlockName();

				if(var5 != null) {
					var5 = var5.toLowerCase().replaceAll("tile.", "");

					if(var5.contains("ore")) {
						var5 = var5.toLowerCase().replaceAll("ore", "");
						var5 = var5 + "-ore";
					}

					if(var5.contains("block")) {
						var5 = var5.toLowerCase().replaceAll("block", "");
						var5 = var5 + "-block";
					}

					if(var4.blockID == var0) {
						return var5.substring(0, 1).toUpperCase() + var5.substring(1).toLowerCase();
					}
				}
			}
		}

		return null;
	}

	public String stripColorCodes(String s) {
		String newString = s;	

		try {
			newString = newString
					.replace("§0", "") /* BLACK */
					.replace("§1", "") /* DARK_BLUE */
					.replace("§2", "") /* DARK_GREEN */
					.replace("§3", "") /* DARK_AQUA */
					.replace("§4", "") /* DARK_RED */
					.replace("§5", "") /* DARK_PURPLE */
					.replace("§6", "") /* GOLD */
					.replace("§7", "") /* GRAY */
					.replace("§8", "") /* DARK_GRAY */
					.replace("§9", "") /* BLUE */
					.replace("§A", "") /* GREEN */
					.replace("§B", "") /* AQUA */
					.replace("§C", "") /* RED */
					.replace("§D", "") /* LIGHT_PURPLE */
					.replace("§E", "") /* YELLOW */
					.replace("§F", "") /* WHITE */
					.replace("§K", "") /* MAGIC */
					.replace("§L", "") /* BOLD */
					.replace("§N", "") /* STRIKETHROUGH */
					.replace("§N", "") /* UNDERLINE */
					.replace("§O", "") /* ITALIC */
					.replace("§R", "") /* RESET */
					.replace("§a", "") /* GREEN */
					.replace("§b", "") /* AQUA */
					.replace("§c", "") /* RED */
					.replace("§d", "") /* LIGHT_PURPLE */
					.replace("§e", "") /* YELLOW */
					.replace("§f", "") /* WHITE */
					.replace("§k", "") /* MAGIC */
					.replace("§l", "") /* BOLD */
					.replace("§m", "") /* STRIKETHROUGH */
					.replace("§n", "") /* UNDERLINE */
					.replace("§o", "") /* ITALIC */
					.replace("§r", ""); /* RESET */
		} catch(Exception e) {}

		return newString;
	}

	/**
	 * The method for making stars.
	 * @param var1 - The String to add the text onto.
	 * @param var2 - The String that should be added onto the text.
	 **/
	public String appendText(String var1, String var2) {
		StringBuilder var3 = new StringBuilder();

		for(int var4 = 0; var4 < var1.length(); var4++) {
			var3.append(var2);
		}

		return var3.toString();
	}

	public void openFile(String location) {
		if(Minecraft.getOs() == EnumOS.MACOS) {
			try {
				System.out.println(location);
				Runtime.getRuntime().exec(new String[] {"/usr/bin/open", location});
				return;
			} catch (IOException var7) {
				var7.printStackTrace();
			}
		} else if (Minecraft.getOs() == EnumOS.WINDOWS) {
			String var2 = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] {location});

			try {
				Runtime.getRuntime().exec(var2);
				return;
			} catch (IOException var6) {
				var6.printStackTrace();
			}
		}

		boolean var8 = false;

		try {
			Class var3 = Class.forName("java.awt.Desktop");
			Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
			var3.getMethod("browse", new Class[] {URI.class}).invoke(var4, new Object[] {(new File(Minecraft.getMinecraftDir(), "texturepacks")).toURI()});
		} catch (Throwable var5) {
			var5.printStackTrace();
			var8 = true;
		}

		if (var8) {
			System.out.println("Opening via system class!");
			Sys.openURL("file://" + location);
		}
	}

	public static void openSite(String url) {
		Desktop desktop = Desktop.getDesktop();

		try {
			URI uri = new URI(url);
			desktop.browse(uri);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}