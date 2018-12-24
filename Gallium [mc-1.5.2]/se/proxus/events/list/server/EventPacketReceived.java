package se.proxus.events.list.server;

import net.minecraft.src.Packet;
import se.proxus.events.EventCancellable;
import se.proxus.events.EventPriority;

public class EventPacketReceived extends EventCancellable {

    private Packet packet;

    public EventPacketReceived(final Packet packet) {
	super("Packet Received", EventPriority.MEDIUM);
	setPacket(packet);
    }

    public Packet getPacket() {
	return packet;
    }

    public void setPacket(final Packet packet) {
	this.packet = packet;
    }
}