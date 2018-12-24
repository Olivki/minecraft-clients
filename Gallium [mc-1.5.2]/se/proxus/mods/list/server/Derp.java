package se.proxus.mods.list.server;

import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet18Animation;
import se.proxus.Gallium;
import se.proxus.events.EventHandler;
import se.proxus.events.list.server.EventPacketSent;
import se.proxus.mods.Mod;
import se.proxus.mods.ModCategory;

public class Derp extends Mod {

    public Derp(final Gallium client) {
	super("Derp", ModCategory.SERVER, false, client);
    }

    @Override
    public void init() {
	setDescription("Herp derp. 0:Normal 1:Head in body 2:Backwards 3:Normal with head in body 4:Hump");
	registerSetting(0, 0, "Mode", 6.0D, true, false, true);
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
	switch ((Integer) getSetting(0)) {
	case 0:
	    packet.yaw = getClient().getRNG().nextInt(360);
	    packet.pitch = getClient().getRNG().nextInt(360);
	    getClient().sendPacket(
		    new Packet18Animation(getClient().getMinecraft().thePlayer,
			    1));
	    break;
	case 1:
	    packet.pitch = -180;
	    getClient().sendPacket(
		    new Packet18Animation(getClient().getMinecraft().thePlayer,
			    1));
	    break;
	case 2:
	    packet.yaw = -180;
	    break;
	case 3:
	    packet.pitch = -180;
	    packet.yaw = getClient().getRNG().nextInt(360);
	    getClient().sendPacket(
		    new Packet18Animation(getClient().getMinecraft().thePlayer,
			    1));
	    break;
	case 4:
	    getClient().sendPacket(
		    new Packet18Animation(getClient().getMinecraft().thePlayer,
			    2));
	    try {
		Thread.sleep(2);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	    getClient().sendPacket(
		    new Packet18Animation(getClient().getMinecraft().thePlayer,
			    3));
	    break;
	default:
	    packet.yaw = getClient().getRNG().nextInt(360);
	    packet.pitch = getClient().getRNG().nextInt(360);
	    getClient().sendPacket(
		    new Packet18Animation(getClient().getMinecraft().thePlayer,
			    1));
	    break;
	}
    }
}