package se.proxus.ai;

import net.minecraft.src.Entity;
import se.proxus.GodlikeCraft;

public class Base_Moving_AI extends GodlikeCraft {

	public void moveForward() {
		this.mc.thePlayer.movementInput.moveForward++;
	}
	
	public void moveBackward() {
		this.mc.thePlayer.movementInput.moveForward--;
	}
	
	public void moveLeft() {
		this.mc.thePlayer.movementInput.moveStrafe--;
	}
	
	public void moveRight() {
		this.mc.thePlayer.movementInput.moveStrafe++;
	}
	
	public void stopMovement() {
		this.mc.thePlayer.movementInput.moveStrafe = 0.0F;
		this.mc.thePlayer.movementInput.moveForward = 0.0F;
		this.mc.thePlayer.movementInput.jump = false;
	}
	
	public void face(Entity var1) {
		this.mc.thePlayer.faceEntity(var1, 360, 360);
	}
	
	public  void onUpdate() {};
}