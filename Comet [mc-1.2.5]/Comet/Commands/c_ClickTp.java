package Comet.Commands;

import Comet.Mods.*;

public class c_ClickTp extends Base_Command {

	public static m_ClickTp clicktp = new m_ClickTp();

	public void run(String as[]) throws Exception {clicktp.toggle(); mc.comet.utils.addChatToggle("Click tp", clicktp.isRunning());}

	public String getHelp() throws Exception {return "Teleports your character to wherever you click.";}

	public String getUsage() throws Exception {return ", can be used to get away quickly, or for the lulz.";}

}
