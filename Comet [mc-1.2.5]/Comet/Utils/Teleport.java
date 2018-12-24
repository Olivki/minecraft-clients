package Comet.Utils;

import net.minecraft.src.*;
import Comet.Comet;

public class Teleport extends Utils {
	
	public static void SendFullUpdate(EntityPlayerSP entityplayersp, double d, double d1, double d2) {
		for(int i = 0; i < 2; i++) {
		entityplayersp.setPosition(d, d1, d2);
		sendPacket(new Packet13PlayerLookMove(entityplayersp.posX, entityplayersp.boundingBox.minY, entityplayersp.posY, entityplayersp.posZ, entityplayersp.rotationYaw, entityplayersp.rotationPitch, true));}
	}


	public static void moveGradually(EntityPlayerSP entityplayersp, double d, double d1, double d2) {
		double d3 = entityplayersp.posX;
		double d4 = entityplayersp.posY;
		double d5 = entityplayersp.posZ;
		double d6 = d - d3;
		double d7 = d1 - d4;
		double d8 = d2 - d5;
		double d9 = Math.sqrt(d6 * d6 + d7 * d7 + d8 * d8);
		if(d9 < 8D){SendFullUpdate(entityplayersp, d, d1, d2); return;}
		d9 /= 8D;
		d6 /= d9;
		d7 /= d9;
		d8 /= d9;
		int i = (int)Math.ceil(d9);
		for(int j = 0; j < i; j++) {
			d3 += d6;
			d4 += d7;
			d5 += d8;
			SendFullUpdate(entityplayersp, d3, d4, d5);}

		SendFullUpdate(entityplayersp, d, d1, d2);
	}

	public static void TeleportTo(double d, double d1, double d2) {
		EntityPlayerSP entityplayersp = Comet.mc.thePlayer;
		double d3 = Comet.mc.thePlayer.posX;
		double d4 = Comet.mc.thePlayer.posY;
		double d5 = Comet.mc.thePlayer.posZ;
		moveGradually(entityplayersp, Comet.mc.thePlayer.posX, 128D, Comet.mc.thePlayer.posZ);
		moveGradually(entityplayersp, d, 128D, d2);
		moveGradually(entityplayersp, d, d1, d2);
		SendFullUpdate(entityplayersp, Comet.mc.thePlayer.posX, Comet.mc.thePlayer.posY, Comet.mc.thePlayer.posZ);
	}

}
