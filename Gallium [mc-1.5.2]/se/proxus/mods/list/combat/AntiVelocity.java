package se.proxus.mods.list.combat;

import net.minecraft.src.Packet28EntityVelocity;
import se.proxus.Gallium;
import se.proxus.events.EventHandler;
import se.proxus.events.list.server.EventPacketReceived;
import se.proxus.mods.Mod;
import se.proxus.mods.ModCategory;

public class AntiVelocity extends Mod {

    public AntiVelocity(final Gallium client) {
	super("Anti Velocity", ModCategory.COMBAT, false, client);
    }

    @Override
    public void init() {
	setDescription("Prevents you from getting knocked back.");
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
    public void onEventPacketSent(final EventPacketReceived event) {
	if (!(event.getPacket() instanceof Packet28EntityVelocity))
	    return;
	Packet28EntityVelocity packet = (Packet28EntityVelocity) event
		.getPacket();
	if (packet.entityId == getClient().getMinecraft().thePlayer.entityId) {
	    packet.motionX = 0;
	    packet.motionY = 0;
	    packet.motionZ = 0;
	}
    }
}