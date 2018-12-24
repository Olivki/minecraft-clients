package bt.Olivki.Biti.Hooks;

import bt.Olivki.Biti.Biti;
import bt.Olivki.Biti.Utils.Utils;
import net.minecraft.src.*;

public class EntityClient {

	public static int FlyNocheat;
	
	public static void onUpdate(){
		FlyNocheat ++; if(FlyNocheat == 7){FlyNocheat = 0;}
		if(Biti.vars.Test && FlyNocheat == 6)
		for(Object target: Biti.mc.theWorld.playerEntities)
		{
			EntityPlayer targetEntity = (EntityPlayer) target;
			if(targetEntity.entityId == Biti.mc.thePlayer.entityId)
			{
				Biti.mc.getSendQueue().addToSendQueue(new Packet7UseEntity(Biti.mc.thePlayer.entityId, targetEntity.entityId, 1));
				Biti.mc.thePlayer.swingItem();
				break;
			}
		}
	}
	
}
