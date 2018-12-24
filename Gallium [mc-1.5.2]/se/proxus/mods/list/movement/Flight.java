package se.proxus.mods.list.movement;

import net.minecraft.src.Packet10Flying;

import org.lwjgl.input.Keyboard;

import se.proxus.Gallium;
import se.proxus.events.EventHandler;
import se.proxus.events.list.player.EventUpdate;
import se.proxus.events.list.server.EventPacketSent;
import se.proxus.mods.Mod;
import se.proxus.mods.ModCategory;

public class Flight extends Mod {

    public Flight(final Gallium client) {
	super("Flight", ModCategory.MOVEMENT, false, client);
    }

    @Override
    public void init() {
	setDescription("Makes you fly like a blackman.");
	registerSetting(0, 1.0F, "Speed", 10.0D, true, false, false);
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
	((Packet10Flying) event.getPacket()).onGround = true;
    }

    @EventHandler
    public void onEventUpdate(final EventUpdate event) {
	getClient().getPlayer().setMotionX(0.0D);
	getClient().getPlayer().setMotionY(0.0D);
	getClient().getPlayer().setMotionZ(0.0D);
	getClient().getMinecraft().thePlayer.jumpMovementFactor = (Float) getSetting(0) / 2;
	if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)
		&& getClient().getMinecraft().inGameHasFocus)
	    getClient().getPlayer().setMotionY(
		    getClient().getPlayer().getMotionY()
			    + (Float) getSetting(0) / 2 + 0.2F);
	if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)
		&& getClient().getMinecraft().inGameHasFocus)
	    getClient().getPlayer().setMotionY(
		    getClient().getPlayer().getMotionY()
			    - (Float) getSetting(0));
    }
}