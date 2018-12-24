package se.proxus.mods.list;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import se.proxus.events.Event;
import se.proxus.events.world.EventBlockClicked;
import se.proxus.mods.*;

public class ModAutoTool extends BaseMod {

	public ModAutoTool() {
		super("Auto_Tool", new ModInfo("Selects the best tool.", "Oliver", "NONE", true), ModType.WORLD, false);
		this.getInfo().setMod(this);
		this.getConfig().loadConfig();
	}
	
	@Override
	public void registerEvents() {
		this.getEvent().registerEvent(EventBlockClicked.class);
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
			if(var0 instanceof EventBlockClicked) {
				EventBlockClicked var1 = (EventBlockClicked)var0;
				
				this.autoToolBlock(var1.getX(), var1.getY(), var1.getZ());
			}
		}
	}

	public void autoToolBlock(int var0, int var1, int var2) {
		if(!(this.wrapper.getController().isInCreativeMode())) {
			Block var4 = Block.blocksList[this.wrapper.getWorld().getBlockId(var0, var1, var2)];

			for(int var5 = 0; var5 < 9; var5++) {
				ItemStack var6 = this.wrapper.getPlayer().inventory.mainInventory[var5];
				ItemStack var7 = this.wrapper.getPlayer().getCurrentEquippedItem();

				if(var6 != null && !(var6.getItem() instanceof ItemSword) && var4 != null) {
					if(var6.getStrVsBlock(var4) > (this.wrapper.getPlayer().getCurrentEquippedItem() == null ? 0.5F : var7.getStrVsBlock(var4))) {
						this.wrapper.getPlayer().inventory.currentItem = var5;
					}
				}
			}
		}
	}
	
	public void autoToolEntity(EntityLiving var0) {
		if(var0 != null) {
			for(int var1 = 0; var1 < 9; var1++) {
				ItemStack var2 = this.wrapper.getPlayer().inventory.mainInventory[var1];
				ItemStack var3 = this.wrapper.getPlayer().getCurrentEquippedItem();

				if(var2 != null && var2.getItem() instanceof ItemSword) {
					if(var2.getDamageVsEntity(var0) > (this.wrapper.getPlayer().getCurrentEquippedItem() == null ? 0.5F : var3.getDamageVsEntity(var0))) {
						this.wrapper.getPlayer().inventory.currentItem = var1;
					}
				}
			}
		}
	}
}