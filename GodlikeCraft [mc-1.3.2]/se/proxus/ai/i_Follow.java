package se.proxus.ai;

import net.minecraft.src.EntityPlayer;

public class i_Follow extends Base_Moving_AI {
	
	@Override
	public void onUpdate() {
		for(Object o : this.mc.theWorld.playerEntities) {
			EntityPlayer var1 = (EntityPlayer)o;
			
			if(var1.username.equalsIgnoreCase(this.vars.lockedName) && var1 != this.mc.thePlayer) {
				face(var1);
				moveForward();
			}
		}
	}
}