package se.proxus.utils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.*;
import java.util.*;

import org.lwjgl.Sys;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

import se.proxus.*;
import se.proxus.hacks.Base_Hack;

public class Utils extends Main {

	public static void log(Object var1) {
		DateFormat var2 = new SimpleDateFormat("yy/mm/dd/hh:mm:ss");
		Calendar var3 = Calendar.getInstance();
		String var4 = "[" + var2.format(var3.getTime()) + "] [" + vars.CLIENT_NAME +"] " + var1;

		System.out.println(var4);
	}

	public static void addMessage(String var1) {
		mc.thePlayer.addChatMessage("<§e" + vars.CLIENT_NAME + "§f> " + var1);
	}

	public static void sendPacket(Packet var1) {
		mc.getSendQueue().addToSendQueue(var1);
	}

	public static void sendMessage(String var1) {
		sendPacket(new Packet3Chat(var1));
	}

	public static void playSound() {
		mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
	}

	public static float[] getRGB(int var1) {
		return (new float[] {
				(float)(var1 >> 16 & 255) / 255.0F,
				(float)(var1 >> 8 & 255) / 255.0F,
				(float)(var1 & 255) / 255.0F,
				(float)(var1 >> 24 & 255) / 255.0F
		});
	}

	public static boolean onCommand(String var1) {
		boolean var2 = false;

		for(Base_Hack var3 : hacks.hackArray) {
			var2 = var3.onCommand(var1.substring(1));
		}

		/*if(var1.charAt(0) == '-') {
			try {
				String var2 = var1.substring(1);

				for(Base_Hack var3 : hacks.hackArray) {
					if(var2.equalsIgnoreCase(var3.getName())) {
						var3.toggle();
						utils.addMessage(var3.getName() + " : " + (var3.getState() ? "§2Enabled" : "§cDisabled") + "§f.");
						return true;
					} if(sUtil.startsWithIgnoreCase(var2, "info")) {
						if(var2.split(" ")[1].equalsIgnoreCase(var3.getName())) {
							utils.addMessage(var3.getDescription());
						} else {
							utils.addMessage("Syntax : -info [name]");
						}

						return true;
					}

					if(var3.onCommand(var2)) {
						return true;
					}
				} if(var2.equalsIgnoreCase("hacks")) {
					StringBuilder var4 = new StringBuilder();

					for(Base_Hack var5 : hacks.hackArray) {
						var4.append(var5.getName() + ", ");
					}

					utils.addMessage(var4.toString());
					return true;
				}
			} catch (Exception e) {
				utils.addMessage("Do -help for a list of commands.");
			}
		} else {
			canSend = false;
		}*/

		if(var1.charAt(0) == '-') {
			cmds.runCommands(var1.substring(1));
			return true;
		}

		if(var2) {
			return true;
		}

		return false;
	}

	public static void openFile(String location) {
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
}