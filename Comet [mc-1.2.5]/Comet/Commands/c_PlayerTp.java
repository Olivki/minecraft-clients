package Comet.Commands;

import net.minecraft.src.*;
import Comet.Mods.*;
import Comet.Utils.Color;
import Comet.Utils.Location;

public class c_PlayerTp extends Base_Command {

	public void run(String as[]) throws Exception {
		try {
			String username = as[0];

			EntityPlayer player = mc.comet.utils.getPlayer();

			if(player.username.equals(username)) {
				mc.comet.teleport.TeleportTo(player.posX, player.posY, player.posZ);
				mc.comet.utils.addChat("Teleporting to : " + player.username);
				System.out.print("\n" + "[COMET] Teleporting to : " + player.username + "\n");
			} else {
				mc.comet.utils.addChat(Color.RED + "Error" + Color.WHITE + " : Could not find that player!");
			}

		} catch(Exception e) {mc.comet.utils.addChat("Syntax : -tpplayer [Username]");}
	}

	public String getHelp() throws Exception {return "Teleports your character to the cordinates you wrote.";}

	public String getUsage() throws Exception {return ", can be used to get away quickly, or for the lulz.";}

}
