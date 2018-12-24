package se.proxus.utils;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import net.minecraft.src.*;
import se.proxus.*;

public class Utils extends GodlikeCraft {

	/** The essential methods. **/
	/**
	 * The method for logging stuff to the console.
	 * @param var1 - The object to be printed out.
	 **/
	public static void log(Object var1) {
		DateFormat var2 = new SimpleDateFormat("yy/mm/dd/hh:mm:ss");
		Calendar var3 = Calendar.getInstance();
		String var4 = "[" + var2.format(var3.getTime()) + "] [GodlikeCraft] " + var1;

		System.out.println(var4);

		/** The saving of the log file and adding onto the log array. **/
		try {
			vars.logArray.add(var4);
			
			if(files.baseFolder != null) {
				files.saveBaseFile(new File(files.baseFolder, "Logs.txt"), vars.logArray, true);
			}
		} catch (NullPointerException e) {}
	}

	/**
	 * The method for adding chat messages to the client.
	 * @param var1 - The String to add to the chat.
	 **/
	public static void addChat(String var1) {
		String var2 = "[§cGodlikeCraft§f] " + var1;

		mc.thePlayer.addChatMessage(var2);
	}

	/**
	 * The method for sending packets to the server.
	 * @param var1 - The packet to be sent.
	 **/
	public static void sendPacket(Packet var1) {
		mc.getSendQueue().addToSendQueue(var1);
	}

	/**
	 * The method for sending chat messages to the server.
	 * @param var1 - The String to be sent.
	 **/
	public static void sendChat(String var1) {
		sendPacket(new Packet3Chat(var1));
	}

	/**
	 * The method for checking if someone is your friend.
	 * @param var1 - The players username.
	 **/
	public static boolean isFriend(String var1) {
		return vars.friendArray.contains(var1.toLowerCase());
	}

	/**
	 * The method for getting the distance of something using math.
	 * @param ep - The player.
	 * @param posX - The position x of the object.
	 * @param posZ - The position z of the object.
	 **/
	public static double getDistanceMath(EntityPlayerSP ep, double posX, double posZ) {
		return Math.sqrt((ep.posX - posX) * (ep.posX - posX) + (ep.posZ - posZ) * (ep.posZ - posZ));
	}

	/**
	 * The method for making stars.
	 * @param var1 - The String to add the text onto.
	 * @param var2 - The String that should be added onto the text.
	 **/
	public static String appendText(String var1, String var2) {
		StringBuilder var3 = new StringBuilder();

		for(int var4 = 0; var4 < var1.length(); var4++) {
			var3.append(var2);
		}

		return var3.toString();
	}

	/** The misc methods. **/
	/** 
	 * Checks in chat for messages.
	 * @param var1 - The chat String. 
	 **/
	public static void checkChatForGreen(String var1) {
		if(var1.startsWith("§aYou are rewarded") && !vars.checkedMessages.contains(var1)) {
			vars.killCount++;
			vars.checkedMessages.add(var1);
			log("Kill count has been increased.");
		}
	}

	/**
	 * The method for the Entity auto tool.
	 * @param e - The entity.
	 **/
	public void entityAutoTool(Entity e) {
		if(this.mc.objectMouseOver == null || this.mc.currentScreen != null) {return;}

		try {
			int bestTool = 0;
			float bestStr = 0;

			if (this.mc.objectMouseOver.typeOfHit == EnumMovingObjectType.ENTITY) {
				for (int g = 0; g < 9; g++) {
					if (this.mc.thePlayer.inventory.mainInventory[g] != null && this.mc.thePlayer.inventory.getStackInSlot(g).getDamageVsEntity(e) > bestStr) {
						bestStr = this.mc.thePlayer.inventory.getStackInSlot(g).getDamageVsEntity(e);
						bestTool = g;
					}
				}

				this.mc.thePlayer.inventory.currentItem = bestTool;
				this.sendPacket(new Packet16BlockItemSwitch(bestTool));
			}
		} catch (Exception er) {
			this.log("Prevented a auto tool crash.");
		}
	}

	public void blockAutoTool() {
		if(this.mc.objectMouseOver == null || this.mc.currentScreen != null) {return;}

		try {
			int bestTool = 0;
			float bestStr = 0;

			if(this.mc.objectMouseOver.typeOfHit == EnumMovingObjectType.TILE) {
				int blockid = this.mc.theWorld.getBlockId(this.mc.objectMouseOver.blockX, this.mc.objectMouseOver.blockY, this.mc.objectMouseOver.blockZ);
				Block block = Block.blocksList[blockid];

				for (int g = 0; g < 9; g++) {
					if (this.mc.thePlayer.inventory.mainInventory[g]!=null && this.mc.thePlayer.inventory.getStackInSlot(g).getStrVsBlock(block) > bestStr) {
						bestStr = this.mc.thePlayer.inventory.getStackInSlot(g).getStrVsBlock(block);
						bestTool = g;
					}
				}

				this.mc.thePlayer.inventory.currentItem = bestTool;
				this.sendPacket(new Packet16BlockItemSwitch(bestTool));
			}
		} catch (Exception e) {
			this.utils.log("Prevented a auto tool crash.");
		}
	} 

	/**
	 * Plays the button click sound.
	 **/
	public static void playSound() {
		mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
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

	public static float[] getRGB(int var1) {
		return (new float[] {
				(float)(var1 >> 16 & 255) / 255.0F,
				(float)(var1 >> 8 & 255) / 255.0F,
				(float)(var1 & 255) / 255.0F,
				(float)(var1 >> 24 & 255) / 255.0F
		});
	}
}