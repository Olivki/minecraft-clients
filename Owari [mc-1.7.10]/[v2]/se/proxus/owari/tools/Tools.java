package se.proxus.owari.tools;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Timer;
import se.proxus.owari.Client;

public class Tools {

	public static Location getPlayerLocation() {
		return new Location(getPlayer().posX, getPlayer().posY, getPlayer().posZ);
	}

	public static ScaledResolution getResolution() {
		return new ScaledResolution(getMinecraft(), getMinecraft().displayWidth,
				getMinecraft().displayHeight);
	}

	public static int getScaledHeight() {
		return getResolution().getScaledHeight();
	}

	public static int getScaledWidth() {
		return getResolution().getScaledWidth();
	}

	public static void addChatMessage(final String message) {
		getMinecraft().thePlayer.addChatMessage(new ChatComponentText(Colours.GREY + message));
	}

	public static void sendChatMessage(final String message) {
		sendPacket(new C01PacketChatMessage(message));
	}

	public static String stringArrayToString(final String[] array) {
		String tempResult = "";
		for (String result : array) {
			tempResult += result + ", ";
		}
		return tempResult.substring(0, tempResult.length() - 2);
	}

	public static float ticksToSeconds(final int ticks) {
		return ticks / 20;
	}

	public static float secondsToTicks(final float seconds) {
		return seconds * 20;
	}

	public static void sendPacket(final Packet packet) {
		getMinecraft().thePlayer.sendQueue.addToSendQueue(packet);
	}

	public static boolean isNumber(final Object value) {
		return value instanceof Short || value instanceof Long || value instanceof Byte
				|| value instanceof Integer || value instanceof Double || value instanceof Float
				|| value instanceof BigInteger || value instanceof BigDecimal;
	}

	public static void playSound(final String location, final float volume) {
		getMinecraft().getSoundHandler().playSound(
				PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation(location),
						volume));
	}

	public static int getFps() throws Exception {
		Field fieldFps = getPrivateField(getMinecraft().getClass(), "debugFPS");
		Integer fps = (Integer) fieldFps.get(getMinecraft());
		return fps;
	}

	public static Timer getTimer() throws Exception {
		Field fieldTimer = getPrivateField(getMinecraft().getClass(), "timer");
		Timer timer = (Timer) fieldTimer.get(getMinecraft());
		return timer;
	}

	public static Field getPrivateField(final Class<?> mainClass, final String fieldName)
			throws NoSuchFieldException, SecurityException {
		Field field = mainClass.getDeclaredField(fieldName);
		field.setAccessible(true);
		return field;
	}

	public static EntityPlayerSP getPlayer() {
		return getMinecraft().thePlayer;
	}

	public static Minecraft getMinecraft() {
		return getClient().getMinecraft();
	}

	private static Client getClient() {
		return Client.getInstance();
	}
}