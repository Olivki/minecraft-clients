package se.proxus.rori.events.list.server;

import net.minecraft.network.Packet;
import se.proxus.rori.events.EventCancellable;

/**
 * 
 * @see net.minecraft.client.network.NetHandlerPlayClient#addToSendQueue(Packet)
 * 
 **/
public class EventPacketSent extends EventCancellable {

	private Packet packet;

	public EventPacketSent(final Packet packet) {
		super("Packet Sent");
		setPacket(packet);
	}

	public Packet getPacket() {
		return packet;
	}

	public void setPacket(final Packet packet) {
		this.packet = packet;
	}
}