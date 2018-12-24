package se.proxus.mods.list;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.*;
import se.proxus.events.Event;
import se.proxus.events.render.EventRender;
import se.proxus.mods.*;
import se.proxus.utils.placeholders.*;

public class ModWaypoint extends BaseMod {

	public int waypointColour = 0xFFFFFFFF;

	public ModWaypoint() {
		super("Waypoint", new ModInfo("Renders a box around waypoints.", "Oliver", "NONE", true), ModType.RENDER, true);
		this.getInfo().setMod(this);
		this.setState(true, false);
		this.getConfig().loadConfig();
	}
	
	@Override
	public void registerEvents() {
		this.getEvent().registerEvent(EventRender.class);
	}

	@Override
	public void onEnabled() {

	}

	@Override
	public void onDisabled() {

	}
	
	@Override
	public void onEvent(Event var0) {
		if(var0 instanceof EventRender) {
			EventRender var1 = (EventRender)var0;
			
			if(var1.getType() == EventRender.Type.ThreeDimensional) {
				this.renderWaypoints();
			}
		}
	}
	
	public void renderWaypoints() {
		if(this.getState()) {
			for(PlaceholderWaypoint var0 : this.sett.waypointArray) {
				if(this.sett.curIP.equalsIgnoreCase(var0.ip)) {
					if(var0.name.equalsIgnoreCase("Home") || var0.name.equalsIgnoreCase("House")) {
						this.waypointColour = 0xFF4F94CD;
					} else if(var0.name.startsWith("Death")) {
						this.waypointColour = 0xFFEE6363;
					} else {
						this.waypointColour = 0xFFFFA500;
					}
					
					/*int var1 = (int)this.utils3d.getFixedPos(this.wrapper.getPlayer())[0] - var0.x;					
					int var2 = (int)this.utils3d.getFixedPos(this.wrapper.getPlayer())[1] - var0.y;
					int var3 = (int)this.utils3d.getFixedPos(this.wrapper.getPlayer())[2] - var0.z;*/
					
					double playerX = this.wrapper.getPlayer().lastTickPosX + (this.wrapper.getPlayer().posX - this.wrapper.getPlayer().lastTickPosX) * (double)this.wrapper.getMinecraft().getTimer().renderPartialTicks;
					double playerY = this.wrapper.getPlayer().lastTickPosY + (this.wrapper.getPlayer().posY - this.wrapper.getPlayer().lastTickPosY) * (double)this.wrapper.getMinecraft().getTimer().renderPartialTicks;
					double playerZ = this.wrapper.getPlayer().lastTickPosZ + (this.wrapper.getPlayer().posZ - this.wrapper.getPlayer().lastTickPosZ) * (double)this.wrapper.getMinecraft().getTimer().renderPartialTicks;
					double var1 = var0.x - playerX;
					double var2 = var0.y - playerY;
					double var3 = var0.z - playerZ;
					
					this.utils3d.enableDefaults();
					//GL11.glColor4f(this.utils.getRGBA(this.waypointColour)[0], this.utils.getRGBA(this.waypointColour)[1], this.utils.getRGBA(this.waypointColour)[2], this.utils.getRGBA(this.waypointColour)[3]);
					this.utils3d.glColor4Hex(this.waypointColour);
					GL11.glLineWidth(1.5F);
					this.utils3d.renderOutlinedBox(AxisAlignedBB.getBoundingBox(var1, var2, var3, var1 + 1.0D, var2 + 2.0D, var3 + 1.0D));
					this.utils3d.renderCrossedBox(AxisAlignedBB.getBoundingBox(var1, var2, var3, var1 + 1.0D, var2 + 2.0D, var3 + 1.0D));
					this.utils3d.drawTracerTo(var0.x, var0.y + 1.5D, var0.z, this.utils.getRGBA(this.waypointColour)[0], this.utils.getRGBA(this.waypointColour)[1], this.utils.getRGBA(this.waypointColour)[2], 1.5F, this.wrapper.getPlayer());
					
			        this.wrapper.getMinecraft().entityRenderer.disableLightmap(0.0D);
					this.utils3d.renderLabel(var1 + 0.5D, var2 + 0.5D, var3 + 0.5D, var0.name, 0xFFFFFFFF);
			        this.wrapper.getMinecraft().entityRenderer.enableLightmap(0.0D);
					
					this.utils3d.disableDefaults();
				}
			}
		}
	}
}