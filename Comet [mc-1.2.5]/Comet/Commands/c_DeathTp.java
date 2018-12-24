package Comet.Commands;

import net.minecraft.src.*;
import Comet.Mods.*;
import Comet.Utils.Location;

public class c_DeathTp extends Base_Command {

	public void run(String as[]) throws Exception {
		double TP1 = mc.comet.settings.D_X;
		double TP2 = mc.comet.settings.D_Y;
		double TP3 = mc.comet.settings.D_Z;
		mc.comet.teleport.TeleportTo(TP1, TP2, TP3);
		mc.comet.utils.addChat("Teleporting to : " + (int)TP1 + ", " + (int)TP2 + ", " + (int)TP3 + ".");
		System.out.print("\n" + "[COMET] Teleporting to : " + (int)TP1 + ", " + (int)TP2 + ", " + (int)TP3 + "." + "\n");}

	public String getHelp() throws Exception {return "Teleports your character back to where you died.";}

	public String getUsage() throws Exception {return ", can be used to get your items back quickly if you died.";}

}
