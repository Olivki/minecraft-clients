package se.proxus.mods.list.combat;

import net.minecraft.src.Packet7UseEntity;
import se.proxus.Gallium;
import se.proxus.events.EventHandler;
import se.proxus.events.list.server.EventPacketSent;
import se.proxus.mods.Mod;
import se.proxus.mods.ModCategory;

public class Criticals extends Mod {

    public Criticals(final Gallium client) {
	super("Criticals", ModCategory.COMBAT, false, client);
    }

    @Override
    public void init() {
	setDescription("Attempts to get a critical on a entity.");
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
	if (!(event.getPacket() instanceof Packet7UseEntity))
	    return;
	Packet7UseEntity packet = (Packet7UseEntity) event.getPacket();
	if (getClient().getMinecraft().thePlayer.onGround)
	    getClient().getPlayer().setMotionY(0.42D);
    }
}