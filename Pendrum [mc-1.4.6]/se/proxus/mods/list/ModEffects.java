package se.proxus.mods.list;

import java.util.Collection;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.*;
import net.minecraft.src.*;
import se.proxus.events.*;
import se.proxus.events.render.*;
import se.proxus.events.world.*;
import se.proxus.mods.*;

public class ModEffects extends BaseMod {

	public ModEffects() {
		super("Effects", new ModInfo("Renders all the current effects.", "Oliver", "NONE", true), ModType.GUI, true);
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
		if(this.getState()) {
			if(var0 instanceof EventRender) {
				EventRender var1 = (EventRender)var0;

				if(var1.getType() == EventRender.Type.TwoDimensional) {
					if(this.mods.gui.getState()) {
						this.renderEffects();
					}
				}
			}
		}
	}

	public void renderEffects() {
		ScaledResolution var13 = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);

		int var1 = 1;
		int var2 = 1;
		Collection var4 = this.wrapper.getPlayer().getActivePotionEffects();

		if(!var4.isEmpty()) {
			int var5 = this.wrapper.getRenderEngine().getTexture("/gui/inventory.png");
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			int var6 = -16;

			for(Iterator var7 = this.wrapper.getPlayer().getActivePotionEffects().iterator(); var7.hasNext(); var2 += var6) {
				PotionEffect var8 = (PotionEffect)var7.next();
				Potion var9 = Potion.potionTypes[var8.getPotionID()];
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				this.wrapper.getRenderEngine().bindTexture(var5);

				if(var9.hasStatusIcon()) {
					int var10 = var9.getStatusIconIndex();
					this.methods.drawTexturedModalRect(var1 + var13.getScaledWidth() - 20, var2 + var13.getScaledHeight() - 20, 0 + var10 % 8 * 18, 198 + var10 / 8 * 18, 18, 18);
				}

				String var12 = StatCollector.translateToLocal(var9.getName());

				if(var8.getAmplifier() == 1) {
					var12 = var12 + " II";
				} else if (var8.getAmplifier() == 2) {
					var12 = var12 + " III";
				} else if (var8.getAmplifier() == 3) {
					var12 = var12 + " IV";
				}

				int var14 = var13.getScaledWidth() - this.wrapper.getFontRenderer().getStringWidth(var12) / 2 - 21;

				this.methods.drawTinyString(var12, var1 + var14, var2 + var13.getScaledHeight() - 14, 16777215);
				String var11 = Potion.getDurationString(var8);
				var14 = var13.getScaledWidth() - this.wrapper.getFontRenderer().getStringWidth(var11) / 2 - 21;
				this.methods.drawTinyString(var11, var1 + var14, var2 + var13.getScaledHeight() - 8, 8355711);
			}
		}
	}
}