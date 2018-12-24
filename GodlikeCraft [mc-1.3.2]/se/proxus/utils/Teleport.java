package se.proxus.utils;

import net.minecraft.src.*;

public class Teleport extends Utils {

	public static void sendFullUpdate(EntityPlayerSP entityplayersp, double d, double d1, double d2) {
		for(int i = 0; i < 2; i++) {
			entityplayersp.setPosition(d, d1, d2);
			sendPacket(new Packet13PlayerLookMove(mc.thePlayer.posX, mc.thePlayer.boundingBox.minY, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
		}
	}

	public static void moveGradually(EntityPlayerSP entityplayersp, double d, double d1, double d2) {
		double d3 = entityplayersp.posX;
		double d4 = entityplayersp.posY;
		double d5 = entityplayersp.posZ;
		double d6 = d - d3;
		double d7 = d1 - d4;
		double d8 = d2 - d5;
		double d9 = Math.sqrt(d6 * d6 + d7 * d7 + d8 * d8);

		if(d9 < 8D){
			sendFullUpdate(entityplayersp, d, d1, d2); 
			return;
		}

		d9 /= 8D;
		d6 /= d9;
		d7 /= d9;
		d8 /= d9;
		int i = (int)Math.ceil(d9);

		for(int j = 0; j < i; j++) {
			d3 += d6;
			d4 += d7;
			d5 += d8;
			sendFullUpdate(entityplayersp, d3, d4, d5);
		}

		sendFullUpdate(entityplayersp, d, d1, d2);
	}

	public static void teleportTo(double d, double d1, double d2) {
		EntityPlayerSP entityplayersp = mc.thePlayer;
		double d3 = mc.thePlayer.posX;
		double d4 = mc.thePlayer.posY;
		double d5 = mc.thePlayer.posZ;
		moveGradually(entityplayersp, mc.thePlayer.posX, 128D, mc.thePlayer.posZ);
		moveGradually(entityplayersp, d, 128D, d2);
		moveGradually(entityplayersp, d, d1, d2);
		sendFullUpdate(entityplayersp, mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
	}
}