package se.proxus.mods.list.render;

import net.minecraft.src.EntityPlayer;
import se.proxus.Gallium;
import se.proxus.events.EventHandler;
import se.proxus.events.list.client.EventRendered3D;
import se.proxus.mods.Mod;
import se.proxus.mods.ModCategory;
import se.proxus.mods.list.none.Friends;
import se.proxus.tools.RenderTools;

public class LineESP extends Mod {

    public LineESP(final Gallium client) {
	super("Line ESP", ModCategory.RENDER, false, client);
    }

    @Override
    public void init() {
	setDescription("Renders a line through the players body.");
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
	    RenderTools tools = new RenderTools();
	    double x = event.getRenderX(player.posX);
	    double y = event.getRenderY(player.posY);
	    double z = event.getRenderZ(player.posZ);
	    if ((Boolean) getSetting(1)) {
		tools.glColor4Hex(0xFF000000);
		tools.renderLine(x, y + player.getEyeHeight() - 0.25D, z, x, y,
			z, 3.5F);
	    }
	    tools.glColor4Hex((Integer) getSetting(0));
	    tools.renderLine(x, y + player.getEyeHeight() - 0.25D, z, x, y, z,
		    1.5F);
	}
    }
}