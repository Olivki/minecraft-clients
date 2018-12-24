package se.proxus.hacks.hacks;

import net.minecraft.src.*;
import se.proxus.hacks.*;

public class h_NoFall extends Base_Hack {

	private double
	FALL_DIST = 0.0D, 
	LAST_Y = 0.0D;

	private boolean
	PREV_ON_GROUND = false,
	IS_SAFE = true;

	public h_NoFall() {
		super('2', "NoFall", "Avoids fall damage.", "NONE", "Player", true);
		this.hacks.addHack(this);
	}

	@Override
	public void onEnabled() {
		this.setColor('2');
		this.LAST_Y = this.mc.thePlayer.posY;
	}

	@Override
	public void onDisabled() {
		if(!(this.IS_SAFE)) {
			this.setState(true);
			this.utils.addMessage("Please wait until the NoFall text has become green to toggle NoFall.");
		}
	}

	@Override
	public void onUpdate() {
		if(this.getState()) {
			if(this.isSafe()) {
				this.FALL_DIST = 0.0D;
				this.setColor('2');
			} if(this.FALL_DIST > 3.3D) {
				this.setColor('c');
	            this.IS_SAFE = false;
	        } else {
	        	this.IS_SAFE = true;
	        }
			
			if(this.mc.thePlayer.posY < this.LAST_Y && !this.isSafe()) {
				this.FALL_DIST += this.LAST_Y - this.mc.thePlayer.posY;
			}

			this.LAST_Y = this.mc.thePlayer.posY;
			
			this.PREV_ON_GROUND = mc.thePlayer.onGround;
			this.mc.thePlayer.onGround = false;
		}
	}

	@Override
	public void onPostMotion() {
		if(this.getState()) {
			this.mc.thePlayer.onGround = this.PREV_ON_GROUND;
		}
	}

	private boolean isSafe() {
		boolean var2 = this.mc.thePlayer.isInWater();
		var2 = var2 || this.mc.thePlayer.isInsideOfMaterial(Material.web);
		var2 = var2 || this.mc.thePlayer.isInsideOfMaterial(Material.lava);
		var2 = var2 || this.mc.thePlayer.isOnLadder();
		var2 = var2 || this.mc.thePlayer.capabilities.isCreativeMode;
		return var2;
	}
}