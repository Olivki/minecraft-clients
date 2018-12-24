package Comet.Commands;

import Comet.Mods.*;

public class c_AutoTool extends Base_Command {

	public static m_AutoTool autotool = new m_AutoTool();

	public void run(String as[]) throws Exception {autotool.toggle();}

	public String getHelp() throws Exception {return "Makes your character switch to the best tool.";}

	public String getUsage() throws Exception {return ", can be used to switch.";}

}
