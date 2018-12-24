package se.proxus.DreamPvP.Utils;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import org.lwjgl.input.Keyboard;

import net.minecraft.src.*;
import se.proxus.DreamPvP.*;
import se.proxus.DreamPvP.Gui.Utils.TTF;
import se.proxus.DreamPvP.Utils.Placeholders.u_Waypoint;

public class Utils {

	public static DreamPvP dp = new DreamPvP();

	/** Essential methods. **/
	public static void log(Object o) {
		String msg = "[DreamPvP] " + o;

		System.out.println(msg);
		dp.settings.logArray.add(msg);
		dp.files.saveBaseFile(new File(dp.files.baseFolder, "Logs.txt"), dp.settings.logArray, true);

		if(dp.settings.debugChat && dp.mc.inGameHasFocus) {
			addChat("[§dDebug chat§r] " + o);
		}
	}

	public static void addChat(String s) {
		String msg = "[§dDream PvP§r] " + s;

		dp.mc.thePlayer.addChatMessage(msg);
	}

	public static void addIRC(String s, String sender, boolean flag, boolean isPM) {
		boolean isOlivki = sender.equalsIgnoreCase("Olivki");
		String don = donatorCol(sender);
		String name = flag ? "[" + don + " -> §eYou§r] " : "[§eYou§r -> " + don + "]";
		String s1 = isPM ? "[§eIRC§r] " + name + " : " + s : "[§eIRC§r] [" + don + "] : " + s;
		String msg = isOlivki ? "[§eIRC§r] [§eCreator§r] [" + don + "] : " + s : s1;

		String var1 = msg;
		boolean var2 = false;

		if(var1.equalsIgnoreCase(dp.settings.lastIRCMessage)) {
			dp.settings.saidTimesIRC++;
			var2 = true;
		} else {
			dp.settings.saidTimesIRC = 0;
		}

		if(dp.settings.lastIRCMessage.equalsIgnoreCase("")) {
			dp.settings.lastIRCMessage = var1;
		}

		dp.settings.lastIRCMessage = var1;

		if(var2) {
			var1 = var1 + " [§e" + dp.settings.saidTimesIRC + "§r]";

			if(dp.mc.ingameGUI.getChatGUI().ChatLines.size() > 0) {
				dp.mc.ingameGUI.getChatGUI().ChatLines.set(0, new ChatLine(dp.mc.ingameGUI.getUpdateCounter(), var1, 0));
			}

			return;
		}

		dp.mc.thePlayer.addChatMessage(msg);
	}

	public static void addIRCPM(String s, String sender) {
		String msg = "[§eIRC§r] [§ePM§r] [" + donatorCol(sender) + "] : " + s;

		dp.mc.thePlayer.addChatMessage(msg);
	}

	public static void sendPacket(Packet packet) {
		if(dp.mc.isMultiplayerWorld()) {
			dp.mc.getSendQueue().addToSendQueue(packet);
			return;
		}
	}

	public static void sendChat(String s) {
		if(s.charAt(0) == '.' || dp.settings.chatMode == 2 && !(dp.settings.chatMode == 1)) {
			return;
		} else {
			sendPacket(new Packet3Chat(s.replace("%|%|%", ".")));
			return;
		}
	}

	public static double getDistanceToEntity(Entity e, EntityPlayerSP ep) {
		return Math.sqrt((ep.posX - e.posX) * (ep.posX - e.posX) + (ep.posZ - e.posZ) * (ep.posZ - e.posZ));
	}

	public static double getDistanceMath(EntityPlayerSP ep, double posX, double posZ) {
		return Math.sqrt((ep.posX - posX) * (ep.posX - posX) + (ep.posZ - posZ) * (ep.posZ - posZ));
	}

	public static String getEntityString(Entity e) {
		return EntityList.getEntityString(e);
	}

	public static String getDistance(Entity e, EntityPlayerSP ep) {
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(2);
		df.setMaximumFractionDigits(2);

		return getEntityString(e) + " : " + df.format(getDistanceToEntity(e, ep));
	}

	public static String getDistance(String text, Entity e, EntityPlayerSP ep) {
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(2);
		df.setMaximumFractionDigits(2);

		return text + " : " + df.format(getDistanceToEntity(e, ep));
	}

	public static String getDistance(String text, EntityPlayerSP ep, double posX, double posZ) {
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(2);
		df.setMaximumFractionDigits(2);

		return text + " : " + df.format(getDistanceMath(ep, posX, posZ));
	}

	public static EntityMob getNearestEntityMob() {
		EntityMob nearest = null;
		if(dp.mc.theWorld == null || !dp.mc.isMultiplayerWorld()) {
			return null;
		} else {
			for(Object o : dp.mc.theWorld.loadedEntityList) {
				if(o != null && !(o instanceof EntityPlayerSP) && o instanceof EntityMob) {
					EntityMob e = (EntityMob)o;
					if(!e.isDead) {
						if(nearest == null) {
							nearest = e;
						} else if(nearest.getDistanceToEntity(dp.mc.thePlayer) > e.getDistanceToEntity(dp.mc.thePlayer)) {
							nearest = e;
						}
					}
				}
			}
			return nearest;
		}
	}

	public static EntityPlayer getNearestEntityPlayer() {
		EntityPlayer nearest = null;
		if(dp.mc.theWorld == null || !dp.mc.isMultiplayerWorld()) {
			return null;
		} else {
			for(Object o : dp.mc.theWorld.playerEntities) {
				if(o != null && !(o instanceof EntityPlayerSP)) {
					EntityPlayer e = (EntityPlayer)o;
					if(!e.isDead) {
						if(nearest == null) {
							nearest = e;
						} else if(nearest.getDistanceToEntity(dp.mc.thePlayer) > e.getDistanceToEntity(dp.mc.thePlayer)) {
							nearest = e;
						}
					}
				}
			}
			return nearest;
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

	public static String replaceStar(String s) {
		StringBuffer stringBuffer = new StringBuffer();

		for(int i1 = 0; i1 < s.length(); i1++) {
			stringBuffer.append("*");
		}

		return stringBuffer.toString();
	}

	public static String getIRCMessage(String username, String message) {
		boolean isOlivki = username.equalsIgnoreCase("Olivki");
		String msg = (isOlivki ? "[§eCreator§r] " : "") + "[" + Utils.donatorCol(username) + "] : " + message;

		return msg;
	}

	public static String getBiomeName() {
		int x = MathHelper.floor_double(dp.mc.thePlayer.posX), y = MathHelper.floor_double(dp.mc.thePlayer.posY), z = MathHelper.floor_double(dp.mc.thePlayer.posZ);
		String biome = "";

		if(dp.mc.theWorld != null && dp.mc.theWorld.blockExists(x, y, z)) {
			Chunk chunk = dp.mc.theWorld.getChunkFromBlockCoords(x, z);
			biome = chunk.getBiomeGenForWorldCoords(x & 15, z & 15, dp.mc.theWorld.getWorldChunkManager()).biomeName;
		}

		return biome;
	}

	public static String getWorldTime() {
		StringBuilder sB = new StringBuilder();
		long curTime = dp.settings.curTime;

		sB
		.append(getChar(curTime, 0))
		.append(getChar(curTime, 1))
		.append(':')
		.append(getChar(curTime, 3))
		.append(getChar(curTime, 4))
		.append(':')
		.append(getChar(curTime, 5))
		.append(getChar(curTime, 6));

		return sB.toString();
	}

	public static String getPos() {
		return "X:" + (int)dp.mc.thePlayer.posX + ", Y:" + (int)dp.mc.thePlayer.posY + ", Z:" + (int)dp.mc.thePlayer.posZ;
	}

	public static String getESPModeNames(int mode) {
		if(mode == 1) {
			return "Sphere ESP";
		} if(mode == 2) {
			return "Box ESP";
		}

		return "N/A";
	}

	public static String getChatModeNames(int mode) {
		if(mode == 1) {
			return "Chat";
		} if(mode == 2) {
			return "Console";
		} if(mode == 3) {
			return "IRC";
		} if(mode == 4) {
			return "Everything";
		}

		return "N/A";
	}

	public static String getRadarModeNames(int mode) {
		if(mode == 1) {
			return "Minimap";
		} if(mode == 2) {
			return "Text radar";
		}

		return "N/A";
	}

	public static String getAimbotModeNames(int mode) {
		if(mode == 1) {
			return "Server sided";
		} if(mode == 2) {
			return "Client sided";
		}

		return "N/A";
	}
	
	public static String getButtonModeNames(int mode) {
		if(mode == 1) {
			return "Notch";
		} if(mode == 2) {
			return "Rectangle";
		}

		return "N/A";
	}

	public static char getChar(long var1, int var2) {
		return Long.toString(var1).charAt(var2);
	}

	public static boolean ircCanSendInfo(String sender) {
		return dp.bools.getState("canBeDisturbed") && isIRCFriend(sender) || isMod(sender);
	}

	public static boolean isDonator(String username) {
		return dp.settings.donatorArray.contains(username);
	}

	public static boolean isFriend(String username) {
		return dp.settings.friendArray.contains(username);
	}

	public static boolean isMod(String username) {
		return dp.settings.modIRCArray.contains(username);
	}

	public static boolean isIRCFriend(String username) {
		return dp.settings.friendIRCArray.contains(username);
	}

	public static String donatorCol(String s) {
		return (isDonator(s) ? "§e" : "§r") + s + (isDonator(s) ? "§r" : "§r");
	}

	public static String stripColorCodes(String s) {
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

	/** Misc methods. **/
	public static void onUpdate() {
		dp.interfaces.onUpdate();
	}

	public static void checkKeys() {
		dp.bModList.checkModKeys();
		dp.settings.checkMacros();
	}

	public static boolean getEventKey(int key) {
		return Keyboard.getEventKey() == key;
	}

	public static void onDeath() {
		String ip = dp.settings.curServerIP;

		if(!dp.settings.waypointArray.contains(new u_Waypoint(dp.mc.thePlayer.posX, dp.mc.thePlayer.posY, dp.mc.thePlayer.posZ, 1.0F, 0.0F, 0.0F, ip, "Death waypoint."))) {
			dp.settings.waypointArray.add(new u_Waypoint(dp.mc.thePlayer.posX, dp.mc.thePlayer.posY, dp.mc.thePlayer.posZ, 1.0F, 0.0F, 0.0F, ip, "Death waypoint."));
		}

		addChat("Death coords : X:" + (int)dp.mc.thePlayer.posX + " Y:" + (int)dp.mc.thePlayer.posY + " Z:" + (int)dp.mc.thePlayer.posZ + ".");
		log("Death coords : X:" + (int)dp.mc.thePlayer.posX + " Y:" + (int)dp.mc.thePlayer.posY + " Z:" + (int)dp.mc.thePlayer.posZ + ".");
	}
}