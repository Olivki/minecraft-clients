package se.proxus;

import se.proxus.commands.*;
import se.proxus.gui.*;
import se.proxus.hacks.*;
import se.proxus.utils.*;
import net.minecraft.client.*;

public class Main {
	
	public static Minecraft 
	mc = Minecraft.getMinecraft();
	
	public static Utils 
	utils = new Utils();
	
	public static Variables
	vars = new Variables();
	
	public static Base_Hacks
	hacks = new Base_Hacks();
	
	public static InGame
	ingame = new InGame();
	
	public static StringUtil
	sUtil = new StringUtil();
	
	public static Base_Commands
	cmds = new Base_Commands();
	
	public static Files
	files = new Files();
	
	public static void init() {
		files.initFiles();
		hacks.HACK_GUI.setState(true);
	}
}