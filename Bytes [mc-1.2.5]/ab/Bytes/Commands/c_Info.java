package ab.Bytes.Commands;

import java.util.List;

import net.minecraft.src.*;

public class c_Info extends Base_Cmd {

	public c_Info() {
		super(".info");
	}

	@Override
	public void command(String[] s) {
		if(s.length < 1) {
			EntityPlayerSP tp = bs.mc.thePlayer;
			List itemEq = tp.getCurrentEquippedItem().getItemNameandInformation();
			int itemSt = tp.getCurrentEquippedItem().stackSize;
			boolean itemEqZero = tp.getCurrentEquippedItem().itemID == 0;
			String name = "Username : " + tp.username;
			String posX = "X : " + (int)tp.posX;
			String posY = "Y : " + (int)tp.posY;
			String posZ = "Z : " + (int)tp.posZ;
			String item = "Item : " + itemEq + ", " + itemSt + ".";

			bs.utils.addChat(name);
			bs.utils.addChat(posX);
			bs.utils.addChat(posY);
			bs.utils.addChat(posZ);
			bs.utils.addChat(item);
		} else {
			String s1 = s[0];
			try {
				for(Object o : bs.mc.theWorld.playerEntities) {
					EntityPlayer e = (EntityPlayer)o;
					if(s1.equalsIgnoreCase(e.username)) {
						List itemEq = e.getCurrentEquippedItem().getItemNameandInformation();
						boolean itemEqZero = e.getCurrentEquippedItem().itemID == 0;
						String name = "Username : " + e.username;
						String posX = "X : " + (int)e.posX;
						String posY = "Y : " + (int)e.posY;
						String posZ = "Z : " + (int)e.posZ;
						String item = "Item : " + itemEq + ".";
						
						bs.utils.addChat(name);
						bs.utils.addChat(posX);
						bs.utils.addChat(posY);
						bs.utils.addChat(posZ);
						bs.utils.addChat(item);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				bs.utils.addChat("Syntax : .info [username]");
			}
		}
	}

	@Override
	public String getUsage() {
		return ".info [username]";
	}

	@Override
	public String getHelp() {
		return "Can be used to get info about another play our yourself.";
	}

}
