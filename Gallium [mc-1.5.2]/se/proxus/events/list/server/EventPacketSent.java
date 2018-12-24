package se.proxus.events.list.server;

import net.minecraft.src.Packet;
import se.proxus.events.EventCancellable;
import se.proxus.events.EventPriority;

public class EventPacketSent extends EventCancellable {

    private Packet packet;

    public EventPacketSent(final Packet packet) {
	super("Packet Sent", EventPriority.MEDIUM);
	setPacket(packet);
    }

    public Packet getPacket() {
	return packet;
    }

    public void setPacket(final Packet packet) {
	this.packet = packet;
    }
}