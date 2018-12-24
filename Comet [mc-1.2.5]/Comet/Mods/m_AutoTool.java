package Comet.Mods;

import net.minecraft.src.*;
import Comet.Gui.InGame;
import Comet.Utils.Utils;
import Comet.Utils.Hooks.ClickBlock;
import Comet.Utils.Hooks.Tickable;

public class m_AutoTool extends Base_Mod implements ClickBlock {

	public m_AutoTool() {
		super(Base_Enum.AutoTool);
	}

	@Override
	public void onStart() {
		enabled = true;
		InGame.array.add("Nuker");
		Utils.addToClickBlock(this);
	}

	@Override
	public void onStop() {
		enabled = false;
		InGame.array.remove("Nuker");
		Utils.removeFromClickBlock(this);
	}

	@Override
	public void onClickBlock(int I1, int I2, int I3, int I4) {
		autoTool(I1, I2, I3);
	}

	public void autoTool(int x, int y, int z) {
		//Credits to Squish000
		int l = mc.theWorld.getBlockId(x, y, z);
		if(l == 0)
		{
			return;
		}
		float f = 1.0F;
		int i1 = -1;
		for(int j1 = 0; j1 < 45; j1++)
		{
			ItemStack itemstack = mc.thePlayer.inventorySlots.getSlot(j1).getStack();
			if(itemstack == null)
			{
				continue;
			}
			float f2 = itemstack.getStrVsBlock(Block.blocksList[l]);
			if(f2 > f)
			{
				i1 = j1;
				f = f2;
			}
		}
		if(i1 == 36 || i1 == 37 || i1 == 38 || i1 == 39 || i1 == 40 || i1 == 41 || i1 == 42 || i1 == 43 || i1 == 44)
		{
			i1 = i1 - 36;
			mc.thePlayer.inventory.currentItem = i1;
			return;
		}
		if(i1 == 2 || i1 == -1)
		{
			return;
		}else
		{
			mc.playerController.windowClick(0, 38, 0, false, mc.thePlayer);
			mc.playerController.windowClick(0, i1, 0, true, mc.thePlayer);
			mc.playerController.windowClick(0, 38, 0, false, mc.thePlayer);
			mc.thePlayer.inventory.currentItem = 2;
			return;
		}
	}

}
