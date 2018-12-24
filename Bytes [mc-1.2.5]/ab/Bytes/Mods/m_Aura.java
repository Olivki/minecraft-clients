package ab.Bytes.Mods;

import net.minecraft.src.*;

import org.lwjgl.input.Keyboard;

import ab.Bytes.Interfaces.*;

public class m_Aura extends Base_Mod implements Updateable {

	public m_Aura() {
		super('4', "Aura", Keyboard.KEY_K);
	}

	@Override
	public void onEnabled() {
		bs.utils.updates.add(this);
	}

	@Override
	public void onDisabled() {
		bs.utils.updates.remove(this);
	}

	@Override
	public void onUpdate() {
		for(Object o : bs.mc.theWorld.playerEntities) {
			EntityPlayer e = (EntityPlayer)o;
			EntityPlayerSP tp = bs.mc.thePlayer;
			String s = e.username;
			
			boolean I1 = tp != e && tp.getDistanceToEntity(e) <= 6 && tp.canEntityBeSeen(e) && !e.isDead;
			
			if(I1) {
				bs.utils.sendPacket(new Packet7UseEntity(tp.entityId, e.entityId, 1));
				tp.swingItem();
				break;
			}
		}
	}
}