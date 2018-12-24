package se.proxus.mods.list.misc;

import net.minecraft.src.Packet3Chat;
import se.proxus.Gallium;
import se.proxus.events.EventHandler;
import se.proxus.events.list.server.EventPacketReceived;
import se.proxus.mods.Mod;
import se.proxus.mods.ModCategory;
import se.proxus.tools.Colours;

public class ChatDate extends Mod {

    public ChatDate(final Gallium client) {
	super("Chat Date", ModCategory.MISC, true, client);
    }

    @Override
    public void init() {
	setDescription("Adds a date before chat messages.");
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
    public void onEventChat(final EventPacketReceived event) {
	if (!(event.getPacket() instanceof Packet3Chat))
	    return;
	Packet3Chat chatPacket = (Packet3Chat) event.getPacket();
	chatPacket.message = Colours.GOLD + "[" + getClient().getDate() + "]: "
		+ Colours.RESET + chatPacket.message;
    }
}