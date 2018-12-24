package Comet.Commands;

import net.minecraft.src.*;
import Comet.Mods.*;
import Comet.Utils.Location;

public class c_Teleport extends Base_Command {

	public void run(String as[]) throws Exception {
		try {
			double TP1 = Float.parseFloat(as[0]);
			double TP2 = Float.parseFloat(as[1]) + mc.thePlayer.yOffset;
			double TP3 = Float.parseFloat(as[2]);
			mc.comet.teleport.TeleportTo(TP1, TP2, TP3);
			mc.comet.utils.addChat("Teleporting to : " + (int)TP1 + ", " + (int)TP2 + ", " + (int)TP3 + ".");
			System.out.print("\n" + "[COMET] Teleporting to : " + (int)TP1 + ", " + (int)TP2 + ", " + (int)TP3 + "." + "\n");
		} catch(Exception e) {mc.comet.utils.addChat("Syntax : -teleport [X] [Y] [Z]");}
	}

	public String getHelp() throws Exception {return "Teleports your character to the cordinates you wrote.";}

	public String getUsage() throws Exception {return ", can be used to get away quickly, or for the lulz.";}

}
