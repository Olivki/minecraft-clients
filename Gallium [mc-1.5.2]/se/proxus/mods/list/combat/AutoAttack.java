package se.proxus.mods.list.combat;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ItemSword;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet16BlockItemSwitch;
import se.proxus.Gallium;
import se.proxus.events.EventHandler;
import se.proxus.events.list.player.EventUpdate;
import se.proxus.events.list.server.EventPacketSent;
import se.proxus.mods.Mod;
import se.proxus.mods.ModCategory;
import se.proxus.mods.list.none.Friends;
import se.proxus.tools.Location;

public class AutoAttack extends Mod {

    public AutoAttack(final Gallium client) {
	super("Auto Attack", ModCategory.COMBAT, false, client);
    }

    @Override
    public void init() {
	setDescription("Attacks people that are close to you.");
	registerSetting(0, 0L, false, false);
	registerSetting(1, 15L, "APS", 15D, true, false, false);
	registerSetting(2, -1L, false, false);
	registerSetting(3, 0.0F, false, false);
	registerSetting(4, 0.0F, false, false);
	registerSetting(5, true, "Silent Aimbot", 0.0D, true, false, false);
    }

    @Override
    public void onEnable() {
	getClient().getEvents().registerListener(this);
    }

    @Override
    public void onDisable() {
	getClient().getEvents().unregisterListener(this);
	registerSetting(0, 0L, false, false);
	registerSetting(2, -1L, false, false);
	registerSetting(3, 0.0F, false, false);
	registerSetting(4, 0.0F, false, false);
	getClient().getPlayer().setTarget(null);
    }

    @EventHandler
    public void onEventUpdate(final EventUpdate event) {
	if (getClient().getPlayer().getTarget() == null)
	    getClient().getPlayer().setTarget(getNearestPlayer());
	else if (!getClient().getMinecraft().theWorld.playerEntities
		.contains(getClient().getPlayer().getTarget()))
	    getClient().getPlayer().setTarget(null);
	else if (getClient()
		.getPlayer()
		.getLocation()
		.distanceTo(
			Location.entityToLocation(getClient().getPlayer()
				.getTarget())) > 3.5F)
	    getClient().getPlayer().setTarget(null);
	else if (((Friends) getClient().getMods().getMod("Friends"))
		.isFriend(getClient().getPlayer().getTarget().getEntityName()))
	    getClient().getPlayer().setTarget(null);
	if (getClient().getPlayer().getTarget() != null
		&& getTargetChecks(getClient().getPlayer().getTarget(), 3.5F)) {
	    registerSetting(0, event.getCurrentMilliseconds(), false, false);
	    getClient().getPlayer().setPitch(
		    getClient().getPlayer().getAttackPitch(
			    getClient().getPlayer().getTarget()));
	    getClient().getPlayer().setYaw(
		    getClient().getPlayer().getAttackYaw(
			    getClient().getPlayer().getTarget()));
	    if ((Long) getSetting(0) - (Long) getSetting(2) >= 1000 / (Long) getSetting(1)
		    || (Long) getSetting(2) == -1L) {
		getClient().getPlayer().setSprinting(false);
		getBestTool(getClient().getPlayer().getTarget());
		if (!(Boolean) getSetting(5)) {
		    getClient().getPlayer().setPitch(
			    getClient().getPlayer().getAttackPitch(
				    getClient().getPlayer().getTarget()));
		    getClient().getPlayer().setYaw(
			    getClient().getPlayer().getAttackYaw(
				    getClient().getPlayer().getTarget()));
		}
		getClient().getMinecraft().thePlayer.swingItem();
		getClient().getMinecraft().playerController.attackEntity(
			getClient().getMinecraft().thePlayer, getClient()
				.getPlayer().getTarget());
		registerSetting(2, event.getCurrentMilliseconds(), false, false);
	    }
	} else
	    getClient().getPlayer().setTarget(null);
    }

    @EventHandler
    public void onEventPacketSent(final EventPacketSent event) {
	if (!(Boolean) getSetting(5))
	    return;
	if (!(event.getPacket() instanceof Packet10Flying)
		|| getClient().getPlayer().getTarget() == null)
	    return;
	Packet10Flying packet = (Packet10Flying) event.getPacket();
	packet.pitch = getClient().getPlayer().getAttackPitch(
		getClient().getPlayer().getTarget());
	packet.yaw = getClient().getPlayer().getAttackYaw(
		getClient().getPlayer().getTarget());
    }

    /**
     * @author Ownage
     * @return The nearest entity.
     */
    public EntityPlayer getNearestPlayer() {
	EntityPlayer nearest = null;
	for (Object entity : getClient().getMinecraft().theWorld.playerEntities)
	    if (entity != null
		    && entity != getClient().getMinecraft().thePlayer) {
		EntityPlayer e = (EntityPlayer) entity;
		if (!e.isDead)
		    if (nearest == null)
			nearest = e;
		    else if (Location.entityToLocation(nearest).distanceTo(
			    getClient().getPlayer().getLocation()) > Location
			    .entityToLocation(e).distanceTo(
				    getClient().getPlayer().getLocation()))
			nearest = e;
	    }

	return nearest;
    }

    public boolean getTargetChecks(final EntityLiving entity, final float reach) {
	return !entity.isDead
		&& getClient().getMinecraft().thePlayer.canEntityBeSeen(entity)
		&& getClient()
			.getPlayer()
			.getLocation()
			.distanceTo(
				Location.entityToLocation(getClient()
					.getPlayer().getTarget())) < reach;
    }

    public double getDistanceToEntity(final EntityLiving e) {
	return Math.sqrt((getClient().getPlayer().getX() - e.posX)
		* (getClient().getPlayer().getX() - e.posX)
		+ (getClient().getPlayer().getZ() - e.posZ)
		* (getClient().getPlayer().getZ() - e.posZ));
    }

    public void getBestTool(final EntityLiving target) {
	ItemStack currentItem = getClient().getPlayer().getInventory()
		.getCurrentItem();
	ItemStack newItem = null;

	for (int hotbar = 0; hotbar < 9; hotbar++) {
	    newItem = getClient().getPlayer().getInventory()
		    .getStackInSlot(hotbar);

	    if (newItem != null && newItem.getItem() instanceof ItemSword)
		if (newItem.getDamageVsEntity(target) > (currentItem == null ? 1
			: currentItem.getDamageVsEntity(target))) {
		    getClient().getPlayer().getInventory().currentItem = hotbar;
		    getClient().sendPacket(new Packet16BlockItemSwitch(hotbar));
		}
	}
    }
}