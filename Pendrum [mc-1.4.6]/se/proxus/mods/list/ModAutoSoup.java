package se.proxus.mods.list;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import se.proxus.events.Event;
import se.proxus.events.player.EventPostUpdate;
import se.proxus.events.player.EventUpdate;
import se.proxus.mods.*;

public class ModAutoSoup extends BaseMod {

	public ModAutoSoup() {
		super("Auto_Soup", new ModInfo("Automagically selects soup and stuff.", "Oliver", "NONE", true), ModType.COMBAT, false);
		this.getInfo().setMod(this);
		this.getConfig().loadConfig();
	}

	@Override
	public void registerEvents() {
		this.getEvent().registerEvent(EventUpdate.class);
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
			if(var0 instanceof EventUpdate) {
				if(this.wrapper.getPlayer().getHealth() <= 14) {
					this.setCurrentItemSoup();
				}
			}
		}
	}

	public void setCurrentItemSoup() {	
		for(int var0 = 0; var0 < 9; var0++) {
			ItemStack var1 = this.wrapper.getPlayer().inventory.mainInventory[var0];

			if(var1 != null) {
				if(var1.getItem() == Item.bowlSoup) {
					this.wrapper.getPlayer().inventory.currentItem = var0;
					this.utils.sendPacket(new Packet15Place(-1, -1, -1, -1, this.wrapper.getPlayer().inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
				} 
			} else {
				this.dragSoupFromInventory();
			}
		}
	}

	public void dragSoupFromInventory() {
		for(int var0 = 0; var0 < 36; var0++) {
			if(var0 > 9) {
				ItemStack var1 = this.wrapper.getPlayer().inventory.mainInventory[var0];

				if(var1 != null) {
					if(var1.getItem() == Item.bowlSoup && !(var1.getItem() instanceof ItemSword)) {
						this.wrapper.getController().windowClick(0, var0, 0, 1, this.wrapper.getPlayer());
					}
				}
			}
		}
	}
}