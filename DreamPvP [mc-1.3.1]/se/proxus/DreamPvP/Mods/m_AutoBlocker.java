package se.proxus.DreamPvP.Mods;

import static org.lwjgl.input.Keyboard.*;

import java.util.Random;

import net.minecraft.src.*;
import se.proxus.DreamPvP.Interfaces.*;

public class m_AutoBlocker extends Base_Mod implements Updates {
	
	public boolean blocking;

	public m_AutoBlocker() {
		super('7', "Auto_blocker", "Makes you block serversided.", KEY_NONE, "Aura", "[§eA§r] ");
	}

	@Override
	public void onEnabled() {
		dp.interfaces.updateArray.add(this);
		checkBlock();
	}

	@Override
	public void onDisabled() {
		dp.interfaces.updateArray.remove(this);
		checkBlock();
	}

	@Override
	public void onUpdate() {
		if(!blocking && holdingSword()) {
            dp.utils.sendPacket(new Packet15Place(0, 0, 0, 255, null, 0, 0, 0));
        }
		
        blocking = holdingSword();
	}
	
    public boolean holdingSword() {
        ItemStack itemstack;
        return (itemstack = dp.mc.thePlayer.getCurrentEquippedItem()) != null && (itemstack.getItem() instanceof ItemSword);
    }
    
    public void checkBlock() {
        if (!getState() && holdingSword()) {
            dp.utils.sendPacket(new Packet14BlockDig(5, 0, 0, 0, 255));
        }
    }
}