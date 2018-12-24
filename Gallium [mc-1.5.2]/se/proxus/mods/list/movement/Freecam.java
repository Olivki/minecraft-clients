package se.proxus.mods.list.movement;

import net.minecraft.src.EntityOtherPlayerMP;

import org.lwjgl.input.Keyboard;

import se.proxus.Gallium;
import se.proxus.events.EventHandler;
import se.proxus.events.list.player.EventUpdate;
import se.proxus.hooks.Player;
import se.proxus.mods.Mod;
import se.proxus.mods.ModCategory;
import se.proxus.tools.Colours;
import se.proxus.tools.Location;

public class Freecam extends Mod {

    private EntityOtherPlayerMP player;

    public Freecam(final Gallium client) {
	super("Freecam", ModCategory.MOVEMENT, false, client);
    }

    @Override
    public void init() {
	setDescription("Makes you sneaky, like a asian.");
	registerSetting(0, 1.0F, "Speed", 10.0D, true, false, false);
	registerSetting(1, true, "TP Back", 0.0D, true, false, false);
	registerSetting(2, true, "Distance TP", 0.0D, true, false, false);
    }

    @Override
    public void onEnable() {
	getClient().getEvents().registerListener(this);
	Player player = getClient().getPlayer();
	setPlayer(new EntityOtherPlayerMP(getClient().getMinecraft().theWorld,
		getClient().getPlayer().getUsername()));
	getPlayer().setPositionAndRotation(player.getX(), player.getY() - 1.6D,
		player.getZ(), player.getYaw(), player.getPitch());
	getPlayer().inventory = player.getInventory();
	getClient().getMinecraft().theWorld.addEntityToWorld(-1, getPlayer());
	getClient().getMinecraft().thePlayer.noClip = true;
    }

    @Override
    public void onDisable() {
	getClient().getEvents().unregisterListener(this);
	if (getPlayer() == null)
	    return;
	Player player = getClient().getPlayer();
	if ((Boolean) getSetting(1)) {
	    player.setPositionAndAngles(getPlayer().posX,
		    getPlayer().posY + 1.6D, getPlayer().posZ,
		    getPlayer().rotationYaw, getPlayer().rotationPitch);
	    player.addMessage(Colours.GREY
		    + "TP Back Setting is on, teleporting back.");
	}
	if ((Boolean) getSetting(2)
		&& Location.entityToLocation(getPlayer()).distanceTo(
			player.getLocation()) > 9) {
	    player.setPositionAndAngles(getPlayer().posX,
		    getPlayer().posY + 1.6D, getPlayer().posZ,
		    getPlayer().rotationYaw, getPlayer().rotationPitch);
	    player.addMessage(Colours.GREY
		    + "You're to far away from the freecam entity, teleporting you back.");
	}
	getClient().getMinecraft().theWorld.removeEntityFromWorld(-1);
	setPlayer(null);
	getClient().getMinecraft().thePlayer.noClip = false;
    }

    @EventHandler
    public void onEventUpdate(final EventUpdate event) {
	getClient().getPlayer().setMotionX(0.0D);
	getClient().getPlayer().setMotionY(0.0D);
	getClient().getPlayer().setMotionZ(0.0D);
	getClient().getMinecraft().thePlayer.onGround = false;
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
	event.setCancelled(true);
    }

    public EntityOtherPlayerMP getPlayer() {
	return player;
    }

    public void setPlayer(final EntityOtherPlayerMP player) {
	this.player = player;
    }
}