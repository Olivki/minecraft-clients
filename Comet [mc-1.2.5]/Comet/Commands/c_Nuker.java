package Comet.Commands;

import Comet.Mods.*;

public class c_Nuker extends Base_Command {

	public static m_Nuker nuker = new m_Nuker();

	public void run(String as[]) throws Exception {nuker.toggle();}

	public String getHelp() throws Exception {return "Makes your character nuke shit.";}

	public String getUsage() throws Exception {return ", can be used to grief.";}

}
