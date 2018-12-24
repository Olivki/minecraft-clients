package ab.Bytes;

import net.minecraft.client.*;
import net.minecraft.src.*;
import ab.Bytes.Commands.*;
import ab.Bytes.Gui.*;
import ab.Bytes.Inheritance.*;
import ab.Bytes.Mods.*;
import ab.Bytes.Render.*;
import ab.Bytes.Utils.*;

public class Bytes {

	/**
	 ** Note to people reading this, I always put //Bytes if I do a change to a class that's not in the Bytes package.
	 **/

	public static Minecraft mc = Minecraft.theMinecraft; //This is a instance / object.
	public static vBools bools = new vBools(); //This is a instance / object.
	public static Utils utils = new Utils(); //This is a 1nstance / object.
	public static InGame ingame = new InGame(); //This is a 1nstance / object.
	public static Settings settings = new Settings(); //This is a 1nstance / object.
	public static Base_ModList basemodlist = new Base_ModList(); //This is a 1nstance / object.
	public static Base_CmdList basecmdlist = new Base_CmdList(); //This is a 1nstance / object.
	public static ChatListener chatlistener = new ChatListener(); //This is a 1nstance / object.
	public static InsultGenerator insultgenerator = new InsultGenerator(); //This is a 1nstance / object.
	public static Base_RenderList baserenderlist = new Base_RenderList(); //This is a 1nstance / object.

	public Bytes() {
		onLaunch();
	}

	public static void onLaunch() {
		
	}
}