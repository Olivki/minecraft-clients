package se.proxus.mods.list.server;

import net.minecraft.src.EntityFishHook;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet16BlockItemSwitch;
import se.proxus.Gallium;
import se.proxus.events.EventHandler;
import se.proxus.events.list.player.EventUpdate;
import se.proxus.mods.Mod;
import se.proxus.mods.ModCategory;

public class AutoFish extends Mod {

    public AutoFish(final Gallium client) {
	super("Auto Fish", ModCategory.SERVER, false, client);
    }

    @Override
    public void init() {
	setDescription("Automagically catches fish for you.");
	registerSetting(0, true, "Auto Rod", 0.0D, true, true, false);
	registerSetting(1, 100L, false, false);
	registerSetting(2, -1L, false, false);
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
    public void onEventUpdate(final EventUpdate event) {
	if ((Boolean) getSetting(0))
	    sortFishingRods(event);
	performAutoFishing();
    }

    public void sortFishingRods(final EventUpdate event) {
	for (int inventorySize = 44; inventorySize >= 9; inventorySize--) {
	    ItemStack item = getClient().getMinecraft().thePlayer.inventoryContainer
		    .getSlot(inventorySize).getStack();
	    if (item == null)
		continue;
	    if (inventorySize >= 36 && inventorySize <= 44) {
		if (item.getItem().itemID == Item.fishingRod.itemID) {
		    getClient().getPlayer().getInventory().currentItem = inventorySize - 36;
		    getClient().sendPacket(
			    new Packet16BlockItemSwitch(getClient().getPlayer()
				    .getInventory().currentItem));
		    return;
		}
	    } else if (item.getItem().itemID == Item.fishingRod.itemID) {
		registerSetting(3, event.getCurrentMilliseconds(), false, false);
		if ((Long) getSetting(3) - (Long) getSetting(2) >= (Long) getSetting(1)
			|| (Long) getSetting(2) == -1L) {
		    getClient().getMinecraft().playerController.windowClick(0,
			    inventorySize, 0, 1,
			    getClient().getMinecraft().thePlayer);
		    getClient().getMinecraft().playerController
			    .updateController();
		    registerSetting(2, event.getCurrentMilliseconds(), false,
			    false);
		}
	    }
	}
	registerSetting(4, event.getCurrentMilliseconds(), false, false);
    }

    public void performAutoFishing() {
	if (getClient().getPlayer().getInventory().getCurrentItem() == null)
	    return;
	ItemStack currentItem = getClient().getPlayer().getInventory()
		.getCurrentItem();
	if (currentItem.getItem() != Item.fishingRod)
	    return;
	for (Object entity : getClient().getMinecraft().theWorld.loadedEntityList)
	    if (entity instanceof EntityFishHook) {
		EntityFishHook hook = (EntityFishHook) entity;
		if (hook.motionX == 0 && hook.motionY != 0 && hook.motionZ == 0) {
		    /*
		     * getClient().sendPacket( new Packet15Place(-1, -1, -1, -1,
		     * currentItem, 0.0F, 0.0F, 0.0F));
		     */
		    getClient().getMinecraft().clickMouse(1);
		    getClient().getMinecraft().clickMouse(1);
		    break;
		}
	    }
    }
}