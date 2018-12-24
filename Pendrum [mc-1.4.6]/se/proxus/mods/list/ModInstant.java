package se.proxus.mods.list;

import net.minecraft.src.*;
import se.proxus.events.*;
import se.proxus.events.player.*;
import se.proxus.events.world.*;
import se.proxus.mods.*;

public class ModInstant extends BaseMod {

	public ModInstant() {
		super("Instant", new ModInfo("Makes you mine blocks \"instantly\".", "Oliver", "NONE", true), ModType.WORLD, false);
		this.setOption("Instant_mode", Integer.valueOf(0), false);
		this.getInfo().setMod(this);
		this.getConfig().loadConfig();
	}
	
	@Override
	public void registerEvents() {
		this.getEvent().registerEvent(EventBlockClicked.class);
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
			if(var0 instanceof EventBlockClicked) {
				EventBlockClicked var1 = (EventBlockClicked)var0;
				
				if(((Integer)this.getOption("Instant_mode")).intValue() == 0) {
					this.utils.sendPacket(new Packet14BlockDig(2, var1.getX(), var1.getY(), var1.getZ(), var1.getSide()));
				}
			} if(var0 instanceof EventUpdate) {
				if(((Integer)this.getOption("Instant_mode")).intValue() == 0) {
					if(this.wrapper.getMouseOver() != null) {
						int var1 = this.wrapper.getMouseOver().blockX;
						int var2 = this.wrapper.getMouseOver().blockY;
						int var3 = this.wrapper.getMouseOver().blockZ;
						int var4 = this.wrapper.getMouseOver().sideHit;

						this.utils.sendPacket(new Packet14BlockDig(0, var1, var2, var3, var4));
						this.utils.sendPacket(new Packet18Animation(this.wrapper.getPlayer(), 1));
					}
				}
			}
		}
	}
}