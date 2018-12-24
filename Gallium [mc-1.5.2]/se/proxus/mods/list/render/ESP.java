package se.proxus.mods.list.render;

import net.minecraft.src.EntityPlayer;

import org.lwjgl.opengl.GL11;

import se.proxus.Gallium;
import se.proxus.events.EventHandler;
import se.proxus.events.list.client.EventRendered3D;
import se.proxus.mods.Mod;
import se.proxus.mods.ModCategory;
import se.proxus.mods.list.none.Friends;
import se.proxus.tools.RenderTools;

public class ESP extends Mod {

    public ESP(final Gallium client) {
	super("ESP", ModCategory.RENDER, false, client);
    }

    @Override
    public void init() {
	setDescription("Renders a box around players.");
	registerSetting(0, 0xFFFFFFFF, false, false);
	registerSetting(1, true, "Black Outline", 0.0D, true, false, true);
    }

    @Override
    public void onEnable() {
	getClient().getEvents().registerListener(this);
    }

    @Override
    public void onDisable() {
	getClient().getEvents().unregisterListener(this);
    }

    @EventHandler
    public void onEventRendered3D(final EventRendered3D event) {
	for (Object o : getClient().getMinecraft().theWorld.playerEntities) {
	    EntityPlayer player = (EntityPlayer) o;
	    if (player == getClient().getMinecraft().thePlayer)
		continue;
	    if (getClient().getPlayer().getTarget() instanceof EntityPlayer
		    && getClient().getPlayer().getTarget() == player)
		registerSetting(0, 0xFFCC33FF, false, false);
	    else if (getClient().getPlayer().getTarget() != player)
		registerSetting(0, 0xFFFFFFFF, false, false);
	    if (((Friends) getClient().getMods().getMod("Friends"))
		    .isFriend(player.username))
		registerSetting(0, 0xFF4F94CD, false, false);
	    if (player.hurtTime > 0)
		registerSetting(0, 0xFFFFA500, false, false);
	    GL11.glPushMatrix();
	    RenderTools tools = new RenderTools();
	    double x = event.getRenderX(player.posX);
	    double y = event.getRenderY(player.posY);
	    double z = event.getRenderZ(player.posZ);
	    GL11.glTranslated(x, y, z);
	    GL11.glRotatef(-player.rotationYaw, 0, 1, 0);
	    GL11.glTranslated(-x, -y, -z);
	    GL11.glTranslated(x, y, z);
	    if ((Boolean) getSetting(1)) {
		tools.glColor4Hex(0xFF000000);
		tools.renderOutlinedBox(0 - 0.5D, 0, 0 - 0.5D, 0 + 0.5D,
			0 + 2.0D, 0 + 0.5D, 3.5F);
		tools.glColor4Hex(0xFF000000);
		tools.renderCrossedOutlinedBox(0 - 0.5D, 0, 0 - 0.5D, 0 + 0.5D,
			0 + 2.0D, 0 + 0.5D, 3.5F);
	    }
	    tools.glColor4Hex((Integer) getSetting(0));
	    tools.renderOutlinedBox(0 - 0.5D, 0, 0 - 0.5D, 0 + 0.5D, 0 + 2.0D,
		    0 + 0.5D, 1.5F);
	    tools.glColor4Hex((Integer) getSetting(0));
	    tools.renderCrossedOutlinedBox(0 - 0.5D, 0, 0 - 0.5D, 0 + 0.5D,
		    0 + 2.0D, 0 + 0.5D, 1.5F);
	    GL11.glPopMatrix();
	}
    }
}