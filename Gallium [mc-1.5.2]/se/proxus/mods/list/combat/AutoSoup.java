package se.proxus.mods.list.combat;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet16BlockItemSwitch;
import se.proxus.Gallium;
import se.proxus.events.EventHandler;
import se.proxus.events.list.player.EventUpdate;
import se.proxus.mods.Mod;
import se.proxus.mods.ModCategory;
import se.proxus.tools.Colours;

public class AutoSoup extends Mod {

    public AutoSoup(final Gallium client) {
	super("Auto Soup", ModCategory.COMBAT, false, client);
    }

    @Override
    public void init() {
	setDescription("Automagically eats soup and stacks it in your inventory. "
		+ Colours.ITALIC
		+ "(You have to have at least one empty spot in your inventory to begin with.)"
		+ Colours.GREY);
	registerSetting(1, 100L, false, false);
	registerSetting(2, -1L, false, false);
	registerSetting(4, -1L, false, false);
	registerSetting(5, 14, "Health Threshhold", 20.0D, true, true, false);
	registerSetting(6, true, "Stack Soup", 0.0D, true, true, false);
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
	if (getClient().getPlayer().getHealth() > (Integer) getSetting(5))
	    return;
	registerSetting(0, event.getCurrentMilliseconds(), false, false);
	if ((Long) getSetting(0) - (Long) getSetting(2) >= (Long) getSetting(1)
		|| (Long) getSetting(2) == -1L) {
	    for (int inventorySize = 44; inventorySize >= 9; inventorySize--) {
		ItemStack item = getClient().getMinecraft().thePlayer.inventoryContainer
			.getSlot(inventorySize).getStack();
		if (item == null)
		    continue;
		if (inventorySize >= 36 && inventorySize <= 44) {
		    if (item.getItem().itemID == Item.bowlSoup.itemID) {
			getClient().getPlayer().getInventory().currentItem = inventorySize - 36;
			getClient()
				.sendPacket(
					new Packet16BlockItemSwitch(
						inventorySize - 36));
			getClient().sendPacket(
				new Packet15Place(-1, -1, -1, -1, getClient()
					.getPlayer().getInventory()
					.getCurrentItem(), 0.0F, 0.0F, 0.0F));
			return;
		    }
		    if (item.getItem().itemID == Item.bowlEmpty.itemID) {
			if (!(Boolean) getSetting(6))
			    return;
			getClient().getMinecraft().playerController
				.windowClick(0, inventorySize, 0, 1,
					getClient().getMinecraft().thePlayer);
			return;
		    }
		} else if (item.getItem().itemID == Item.bowlSoup.itemID) {
		    registerSetting(3, event.getCurrentMilliseconds(), false,
			    false);
		    if ((Long) getSetting(3) - (Long) getSetting(4) >= (Long) getSetting(1)
			    || (Long) getSetting(4) == -1L) {
			getClient().getMinecraft().playerController
				.windowClick(0, inventorySize, 0, 1,
					getClient().getMinecraft().thePlayer);
			getClient().getMinecraft().playerController
				.updateController();
			registerSetting(4, event.getCurrentMilliseconds(),
				false, false);
		    }
		}
	    }
	    registerSetting(2, event.getCurrentMilliseconds(), false, false);
	}
    }
}