package Comet.Commands;

import Comet.Mods.*;

public class c_TextRadar extends Base_Command {

	public static m_TextRadar textradar = new m_TextRadar();

	public void run(String as[]) throws Exception {textradar.toggle();}

	public String getHelp() throws Exception {return "Shows up a text radar.";}

	public String getUsage() throws Exception {return ", can be used to not get detected when griefing.";}

}
