package se.proxus.mods.list.render;

import net.minecraft.src.TileEntityChest;

import org.lwjgl.opengl.GL11;

import se.proxus.Gallium;
import se.proxus.events.EventHandler;
import se.proxus.events.list.client.EventRendered3D;
import se.proxus.mods.Mod;
import se.proxus.mods.ModCategory;
import se.proxus.tools.RenderTools;

public class ChestESP extends Mod {

    public ChestESP(final Gallium client) {
	super("Chest ESP", ModCategory.RENDER, false, client);
    }

    @Override
    public void init() {
	setDescription("Renders a box around chests.");
	registerSetting(0, true, "Black Outline", 0.0D, true, false, true);
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
	for (Object o : getClient().getMinecraft().theWorld.loadedTileEntityList) {
	    if (!(o instanceof TileEntityChest))
		continue;
	    TileEntityChest chest = (TileEntityChest) o;
	    RenderTools tools = new RenderTools();
	    double x = event.getRenderX(chest.xCoord);
	    double y = event.getRenderY(chest.yCoord);
	    double z = event.getRenderZ(chest.zCoord);
	    GL11.glPushMatrix();
	    GL11.glTranslated(x, y, z);
	    if ((Boolean) getSetting(0)) {
		tools.glColor4Hex(0xFF000000);
		tools.renderOutlinedBox(0, 0, 0, 1, 1, 1, 3.5F);
	    }
	    tools.glColor4Hex(chest.func_98041_l() == 1 ? 0xFFFF5555
		    : 0xFFFFFFFF);
	    tools.renderOutlinedBox(0, 0, 0, 1, 1, 1, 1.5F);
	    GL11.glPopMatrix();
	}
    }
}