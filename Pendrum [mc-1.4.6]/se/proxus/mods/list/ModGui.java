package se.proxus.mods.list;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.*;
import net.minecraft.src.*;
import se.proxus.events.*;
import se.proxus.events.render.EventRender;
import se.proxus.events.world.*;
import se.proxus.mods.*;

public class ModGui extends BaseMod {

	public ModGui() {
		super("Gui", new ModInfo("Renders the gui.", "Oliver", "NONE", true), ModType.GUI, true);
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
	public void onEvent(Event event) {
		if(this.getState()) {
			if(event instanceof EventRender) {
				EventRender eventRender = (EventRender)event;

				if(eventRender.getType() == EventRender.Type.TwoDimensional) {
					int var0 = (int)this.wrapper.getPlayer().posX;
					int var1 = (int)this.wrapper.getPlayer().posY;
					int var2 = (int)this.wrapper.getPlayer().posZ;
					String var6 = "";

					if(this.mods.forcefield.current != null) {
						var6 = this.mods.forcefield.current.getEntityName();
					} else if(this.mods.forcefield.current == null) {
						var6 = "";
					}

					this.methods.drawMain(this.sett.guiArray, this.getMain(var0, var1, var2), this.wrapper.getFontRenderer(), var6);

					if(this.wrapper.getMinecraft().getServerData() != null) {
						ServerData var7 = this.wrapper.getMinecraft().getServerData();

						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						this.wrapper.getRenderEngine().bindTexture(this.wrapper.getRenderEngine().getTexture("/gui/icons.png"));
						int var8 = 0;
						int var9 = this.wrapper.getFontRenderer().getStringWidth(this.getMain(var0, var1, var2)[0]);
						byte var10 = 0;

						if(var7.field_78841_f && var7.pingToServer != -2L) {
							if(var7.pingToServer < 0L) {
								var8 = 5;
							} else if(var7.pingToServer < 150L) {
								var8 = 0;
							} else if(var7.pingToServer < 300L) {
								var8 = 1;
							} else if(var7.pingToServer < 600L) {
								var8 = 2;
							} else if(var7.pingToServer < 1000L) {
								var8 = 3;
							} else {
								var8 = 4;
							}
						} else {
							var10 = 1;
						}

						this.methods.drawTexturedModalRect(var9 + 1, 1, 0 + 0 * 10, 176 + var8 * 8, 10, 8);
						this.methods.drawTinyString(var8 + "ms", var9 + 12, 5, 0xFFFFFFFF);
					} else {
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						this.wrapper.getRenderEngine().bindTexture(this.wrapper.getRenderEngine().getTexture("/gui/icons.png"));
						int var8 = 0;
						int var9 = this.wrapper.getFontRenderer().getStringWidth(this.getMain(var0, var1, var2)[0]);
						byte var10 = 0;

						var8 = 0;
						var10 = 1;

						this.methods.drawTexturedModalRect(var9 + 1, 1, 0 + 0 * 10, 176 + var8 * 8, 10, 8);
						this.methods.drawTinyString(var8 + "ms", var9 + 12, 5, 0xFFFFFFFF);
						//this.methods.drawTexturedModalRect(var9 + 1, 3, 0 + var10 * 10, 176 + var8 * 8, 10, 8);
					}
				}
			}
		}
	}

	public String[] getMain(int var0, int var1, int var2) {
		return (new String[] {
				"§fPendrum (v" + this.sett.curVersion + ") ",
				"§fX: " + var0,
				"§fY: " + var1,
				"§fZ: " + var2,
				"§fFPS: " + Minecraft.getDebugFPS()
		});
	}
}