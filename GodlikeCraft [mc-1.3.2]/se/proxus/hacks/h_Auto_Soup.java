package se.proxus.hacks;

import se.proxus.inheritance.cEntityClientPlayerMP;
import se.proxus.inheritance.cPlayerControllerMP;
import net.minecraft.src.*;

public class h_Auto_Soup extends Base_Hack {

	public h_Auto_Soup() {
		super('2', "Auto_soup", "Makes you automatically eat soup.", "Aura", "NONE");
	}

	@Override
	public void onEnabled() {
		
	}

	@Override
	public void onDisabled() {

	}

	@Override
	public void onToggled() {

	}

	@Override
	public void onUpdate() {
		if(this.getState()) {
			for(int var1 = 44; var1 >= 9; var1--) {
				ItemStack var2 = this.mc.thePlayer.inventorySlots.getSlot(var1).getStack(), var3 = this.mc.thePlayer.getCurrentEquippedItem();
				cEntityClientPlayerMP var4 = this.mc.thePlayer;
				cPlayerControllerMP var5 = this.mc.playerController;

				if(var2 != null && var3 != null) {
					if(var1 >= 36 && var1 <= 44) {
						if(var2.itemID == Item.bowlSoup.shiftedIndex && var4.getHealth() <= 14) {
							//var4.inventory.currentItem = var1 - 36;
							this.utils.sendPacket(new Packet16BlockItemSwitch(var1 - 36));
							this.utils.sendPacket(new Packet15Place(-1, -1, -1, -1, var3, 0, 0, 0));
							return;
						} else if(getModBoolean()[0] && var2.itemID != Item.bowlSoup.shiftedIndex && var2.itemID != Item.swordDiamond.shiftedIndex) {
							var4.inventory.currentItem = var1 - 36;
							this.utils.sendPacket(new Packet16BlockItemSwitch(var4.inventory.currentItem));
							this.utils.sendPacket(new Packet15Place(-1, -1, -1, -1, var3, 0, 0, 0));
							this.utils.sendPacket(new Packet14BlockDig(4, 0, 0, 0, 0));
							return;
						}
					} else {
						if(var2.itemID == Item.bowlSoup.shiftedIndex && var4.getHealth() <= 14) {
							var5.windowClick(0, var1, 0, true, var4);
						}
					}
				}
			}
		}
	}

	@Override
	public String[] getModString() {
		return (new String[] {});
	}

	@Override
	public int[] getModInteger() {
		return (new int[] {});
	}

	@Override
	public float[] getModFloat() {
		return (new float[] {});
	}

	@Override
	public long[] getModLong() {
		return (new long[] {});
	}

	@Override
	public boolean[] getModBoolean() {
		return (new boolean[] {this.vars.dropItemsInAutoSoup});
	}
}