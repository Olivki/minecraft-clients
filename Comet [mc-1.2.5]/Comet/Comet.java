package Comet;

import Comet.Commands.*;
import Comet.Gui.*;
import Comet.Render.*;
import Comet.Utils.*;
import Comet.Utils.Hooks.*;
import net.minecraft.client.*;
import net.minecraft.src.*;

public class Comet {

	public static Minecraft mc = Minecraft.theMinecraft;
	public static NetClientHandler netclienthandler = PlayerControllerMP.netClientHandler;
	public static InGame ingame = new InGame();
	public static Utils utils = new Utils();
	public static Settings settings = new Settings();
	public static Keys keys = new Keys();
	public static vBools bools = new vBools();
	public static Location location = new Location();
	public static Teleport teleport = new Teleport();
	public static Base_List baselist = new Base_List();
	public static Base_Render baserender = new Base_Render();
	public static PlayerControllerMP playercontrollermp = new PlayerControllerMP(mc, netclienthandler);
	public static HookPlayerContMP hookplayercontmp = new HookPlayerContMP(mc, netclienthandler);
	
	public Comet() {
		init();
	}
	
	public static void init() {
		keys.nofall.toggle();
	}
	
}
