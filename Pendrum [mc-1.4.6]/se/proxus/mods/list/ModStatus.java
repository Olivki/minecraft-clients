package se.proxus.mods.list;

import java.util.Collection;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiChat;
import net.minecraft.src.GuiMultiplayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Potion;
import net.minecraft.src.PotionEffect;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.ServerData;
import net.minecraft.src.StatCollector;
import se.proxus.events.Event;
import se.proxus.events.render.EventRender;
import se.proxus.mods.*;

public class ModStatus extends BaseMod {

	public int posY = 0;

	public ModStatus() {
		super("Status", new ModInfo("Draws your current equipped armour and item.", "Oliver", "NONE", true), ModType.GUI, true);
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
		if(this.getState() && this.mods.gui.getState()) {
			if(var0 instanceof EventRender) {
				EventRender var1 = (EventRender)var0;

				if(var1.getType() == EventRender.Type.TwoDimensional) {
					if(!(this.wrapper.getMinecraft().currentScreen instanceof GuiChat)) {
						ScaledResolution var2 = this.wrapper.getScaledResolution();

						this.posY = var2.getScaledHeight() - 18;

						this.drawStatusItem(2, this.posY, this.wrapper.getPlayer().getCurrentEquippedItem());
						this.drawStatusItem(2, this.posY, this.wrapper.getPlayer().inventory.armorInventory[0]);
						this.drawStatusItem(2, this.posY, this.wrapper.getPlayer().inventory.armorInventory[1]);
						this.drawStatusItem(2, this.posY, this.wrapper.getPlayer().inventory.armorInventory[2]);
						this.drawStatusItem(2, this.posY, this.wrapper.getPlayer().inventory.armorInventory[3]);
					}
				}
			}
		}
	}

	public void drawStatusItem(int x, int y, ItemStack var0) {
		if(var0 != null) {
			this.methods.drawItemTag(x, y, var0);

			if(var0.getItem().isItemTool(var0)) {
				this.methods.drawTinyString(var0.getDisplayName(), x + 16, y + 6, 16777215);
				this.methods.drawTinyString((var0.getMaxDamage() - var0.getItemDamageForDisplay()) + "/" + var0.getMaxDamage(), x + 16, y + 11, 8355711);
			}

			this.posY -= 16;
		}
	}
}