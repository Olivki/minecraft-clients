package se.proxus.kanon.event4j.events.server;

import net.minecraft.network.Packet;
import se.proxus.kanon.event4j.events.EventCancellable;

public class EventPacketSent extends EventCancellable {

    private final Packet packet;

    public EventPacketSent(final Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }
}