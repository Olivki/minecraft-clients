package Comet.Commands;

import Comet.Mods.*;

public class c_Sneak extends Base_Command {

	public static m_Sneak sneak = new m_Sneak();

	public void run(String as[]) throws Exception {sneak.toggle();}

	public String getHelp() throws Exception {return "Makes your character sneak.";}

	public String getUsage() throws Exception {return ", can be used to not get detected when griefing.";}

}
