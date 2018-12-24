package se.proxus.mods.list;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiMultiplayer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ServerData;
import se.proxus.events.Event;
import se.proxus.events.player.EventUpdate;
import se.proxus.mods.*;

public class ModNoFall extends BaseMod {

	public ModNoFall() {
		super("NoFall", new ModInfo("Avoids fall damage.", "Oliver", "NONE", false), ModType.PLAYER, false);
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
				this.wrapper.getPlayer().onGround = true;
			}
		}
	}
}