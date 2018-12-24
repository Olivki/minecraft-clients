package se.proxus.mods.list.movement;

import net.minecraft.src.Packet10Flying;
import se.proxus.Gallium;
import se.proxus.events.EventHandler;
import se.proxus.events.list.server.EventPacketSent;
import se.proxus.mods.Mod;
import se.proxus.mods.ModCategory;

public class NoFall extends Mod {

    public NoFall(final Gallium client) {
	super("NoFall", ModCategory.MOVEMENT, false, client);
    }

    @Override
    public void init() {
	setDescription("Tries to avoid fall damage.");
    }

    @Override
    public void onEnable() {
	getClient().getEvents().registerListener(this);
    }

    @Override
    public void onDisable() {
	getClient().getEvents().unregisterListener(this);
    }

    @EventHandler
    public void onEventPacketSent(final EventPacketSent event) {
	if (!(event.getPacket() instanceof Packet10Flying))
	    return;
	Packet10Flying packet = (Packet10Flying) event.getPacket();
	packet.onGround = true;
    }
}