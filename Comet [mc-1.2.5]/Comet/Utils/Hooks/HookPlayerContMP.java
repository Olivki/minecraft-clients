package Comet.Utils.Hooks;

import Comet.Utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

public class HookPlayerContMP extends PlayerControllerMP {

	public HookPlayerContMP(Minecraft par1Minecraft, NetClientHandler par2NetClientHandler) {
		super(par1Minecraft, par2NetClientHandler);
	}

	@Override
	public boolean onPlayerDestroyBlock(int par1, int par2, int par3, int par4) {
		return super.onPlayerDestroyBlock(par1, par2, par3, par4);
	}

	@Override
	public void clickBlock(int par1, int par2, int par3, int par4) {
		super.clickBlock(par1, par2, par3, par4);
		mc.comet.utils.onClickBlock(par1, par2, par3, par4);
	}

	@Override
	public void onPlayerDamageBlock(int par1, int par2, int par3, int par4) {
		super.onPlayerDamageBlock(par1, par2, par3, par4);
        int i = mc.theWorld.getBlockId(par1, par2, par3);

        if (i == 0)
        {
            setHittingBlock(false);
            return;
        }

        Block block = Block.blocksList[i];
		if(mc.comet.keys.miner.isRunning()) {setCurBlockDamageMP(getCurBlockDamageMP() + block.blockStrength(mc.thePlayer) * 0.2F);}
		if ((getCurBlockDamageMP() >= (mc.comet.keys.miner.isRunning() ? (1.0F - 0.3F) : 1.0F)) || mc.comet.keys.instant.isRunning()) {
			setHittingBlock(false);
			netClientHandler.addToSendQueue(new Packet14BlockDig(2, par1, par2, par3, par4));
			if(!mc.comet.keys.instant.isRunning()) {onPlayerDestroyBlock(par1, par2, par3, par4);}
			setCurBlockDamageMP(0.0F);
			setPrevBlockDamageMP(0.0F);
			setStepSoundTickCounter(0.0F);
			setBlockHitDelay((mc.comet.keys.miner.isRunning() || mc.comet.keys.instant.isRunning()) ? 0 : 5);
		}
	}

}
