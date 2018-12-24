package se.proxus.DreamPvP.Mods;

import static org.lwjgl.input.Keyboard.*;
import net.minecraft.src.*;
import se.proxus.DreamPvP.Interfaces.*;

public class m_ESP extends Base_Mod implements Renderer {

	public m_ESP() {
		super('7', "ESP", "Makes players have a sphere around them.", KEY_G, "OpenGL", "[§eP§r] ");
	}

	@Override
	public void onEnabled() {
		dp.interfaces.renderArray.add(this);
	}

	@Override
	public void onDisabled() {
		dp.interfaces.renderArray.remove(this);
	}

	@Override
	public void onRendered() {
		for(Object o : dp.mc.theWorld.playerEntities) {
			EntityPlayer e = (EntityPlayer)o;
			float col1 = (float)dp.utils.getDistanceToEntity(e, dp.mc.thePlayer) / 100;
			float col2 = (float)dp.utils.getDistanceToEntity(e, dp.mc.thePlayer);
			float col3 = (float)dp.utils.getDistanceToEntity(e, dp.mc.thePlayer) * 100;

			if(e != dp.mc.thePlayer) {
				dp.render.drawTracerTo(e.posX, e.posY + e.getEyeHeight(), e.posZ, col1, col2, col3, 2.0F);
			}
		}
	}
}