package se.proxus.hacks.hacks;

import net.minecraft.src.*;
import se.proxus.hacks.*;

public class h_Kill_aura extends Base_Hack {

	public h_Kill_aura() {
		super('c', "Kill_aura", "Makes you hit people.", "NONE", "Aura", true);
		this.hacks.addHack(this);
	}

	@Override
	public void onEnabled() {
		
	}

	@Override
	public void onDisabled() {
		
	}

	@Override
	public void onUpdate() {
		if(this.getState()) {
			for(Object var1 : this.mc.theWorld.playerEntities) {
				EntityPlayer var2 = (EntityPlayer)var1;
				
				if(var2 != this.mc.thePlayer) {
					this.mc.playerController.attackEntity(this.mc.thePlayer, var2);
					break;
				}
			}
		}
	}
}