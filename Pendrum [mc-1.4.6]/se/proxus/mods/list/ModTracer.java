package se.proxus.mods.list;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.*;
import se.proxus.events.Event;
import se.proxus.events.render.EventRender;
import se.proxus.mods.*;

public class ModTracer extends BaseMod {

	public int tracerColor = 0xFFFFFFFF;

	public ModTracer() {
		super("Tracer", new ModInfo("Renders a line to the players.", "Oliver", "NONE", true), ModType.RENDER, false);
		this.getInfo().setMod(this);
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
				this.renderTracers();
			}
		}
	}
	
	public void renderTracers() {
		if(this.getState()) {
			for(Object var0 : this.wrapper.getWorld().playerEntities) {
				EntityPlayer var1 = (EntityPlayer)var0;
				
				if(var1 != this.wrapper.getPlayer()) {
					if(this.mods.forcefield.current instanceof EntityPlayer && this.mods.forcefield.current == var1) {
						this.tracerColor = 0xFFEE6363;
					} else if(this.mods.forcefield.current != var1) {
						this.tracerColor = 0xFFFFFFFF;
					} if(this.sett.isFriend(var1.username)) {
						this.tracerColor = 0xFF4F94CD;
					} if(var1.hurtTime > 0) {
						this.tracerColor = 0xFFFFA500;
					}
					
					this.utils3d.drawTracerTo(var1.posX, var1.posY + var1.getEyeHeight() - 0.5F, var1.posZ, this.utils.getRGBA(this.tracerColor)[0], this.utils.getRGBA(this.tracerColor)[1], this.utils.getRGBA(this.tracerColor)[2], 1.5F, var1);
				}
			}
		}
	}
}