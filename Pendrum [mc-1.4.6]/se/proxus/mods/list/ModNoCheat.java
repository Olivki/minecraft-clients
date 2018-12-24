package se.proxus.mods.list;

import se.proxus.events.*;
import se.proxus.events.player.*;
import se.proxus.mods.*;

public class ModNoCheat extends BaseMod {

	public ModNoCheat() {
		super("NoCheat", new ModInfo("Enables special behavior.", "Oliver", "NONE", true), ModType.SERVER, true);
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
				for(BaseMod var1 : this.mods.modArray) {
					if(!(var1.getInfo().getNoCheat()) && var1.getState()) {
						var1.setState(false, true);
					}
				}
			}
		}
	}
}