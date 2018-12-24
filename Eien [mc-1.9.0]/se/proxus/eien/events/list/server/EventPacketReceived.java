package se.proxus.eien.events.list.server;

import net.minecraft.network.Packet;
import se.proxus.eien.events.EventCancellable;

/**
 * 
 * Can be found in the MessageDeserializer class.
 * 
 */
public class EventPacketReceived extends EventCancellable {

	private Packet packet;

	public EventPacketReceived(final Packet packet) {
		super("Packet Received");
		setPacket(packet);
	}

	public Packet getPacket() {
		return packet;
	}

	public void setPacket(final Packet packet) {
		this.packet = packet;
	}
}