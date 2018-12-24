package se.proxus.kanon.wrapper.minecraft;

import lombok.experimental.UtilityClass;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

@UtilityClass
public final class Inventory {

    public static InventoryPlayer getInventory() {
        return Player.getInventory();
    }

    public static void windowClick(final int slot, final int mouseButton, final ClickType clickType) {
        Player.getController()
                .windowClick(getContainer().windowId, slot, mouseButton, clickType,
                             Player.getPlayer());
    }

    public static void clickSlot(final int slot, final int mouseButton, final boolean shiftClick) {
        windowClick(slot, mouseButton, shiftClick ? ClickType.SWAP : ClickType.QUICK_MOVE);
    }

    public static ItemStack getHotbarItem(final String name) {
        for (int index = 0; index < 9; index++) {
            final ItemStack item = getInventory().getStackInSlot(index);

            if (item.getUnlocalizedName().equalsIgnoreCase(name)) {
                return item;
            }
        }

        return null;
    }

    /**
     * @author Vazkii
     */
    public static void switchItems(final int slot1, final int slot2) {
        final int inventorySize = getInventory().mainInventory.size();

        if (slot1 >= inventorySize || slot2 >= inventorySize) {
            return;
        }

        final ItemStack stackAtSlot1 = getInventory().getStackInSlot(slot1);
        final ItemStack stackAtSlot2 = getInventory().getStackInSlot(slot2);

        getInventory().setInventorySlotContents(slot2, stackAtSlot1);
        getInventory().setInventorySlotContents(slot1, stackAtSlot2);
    }

    public static boolean doesHotbarContainItem(final String name) {
        return getHotbarItem(name) != null &
                getHotbarItem(name).getUnlocalizedName().equalsIgnoreCase("Air");
    }

    public static ItemStack getInventoryItem(final String name) {
        for (int index = 9; index < 36; index++) {
            final ItemStack item = Player.getInventory().getStackInSlot(index);

            if (item.getUnlocalizedName().equalsIgnoreCase(name)) {
                return item;
            }
        }

        return null;
    }

    public static boolean doesInventoryContainItem(final String name) {
        return getInventoryItem(name) != null &
                getInventoryItem(name).getItem().getUnlocalizedName().equalsIgnoreCase("Air");
    }

    public int getAvailableSlots(final String[] itemNames) {
        for (int index = 36; index < 45; index++) {
            final ItemStack item = getContainer().getSlot(index).getStack();

            for (final String name : itemNames) {
                if (item.getUnlocalizedName().equalsIgnoreCase(name)) {
                    return index;
                }
            }
        }

        return -1;
    }

    public int getItemCountHotbar(final String name) {
        int count = 0;

        for (int hotbarIndex = 0; hotbarIndex < 9; hotbarIndex++) {
            final ItemStack item = Player.getInventory().getStackInSlot(hotbarIndex);

            if (item.getUnlocalizedName().equalsIgnoreCase(name)) {
                count += item.getStackSize();
            }
        }

        return count;
    }

    public int getItemCountInventory(final String name) {
        int count = 0;

        for (int inventoryIndex = 9; inventoryIndex < 36; inventoryIndex++) {
            final ItemStack item = getContainer().getSlot(inventoryIndex).getStack();

            if (item.getUnlocalizedName().equalsIgnoreCase(name)) {
                count += item.getStackSize();
            }
        }

        return count;
    }
    
    public static Container getContainer() {
        return Player.getPlayer().inventoryContainer;
    }
}