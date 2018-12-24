package se.proxus;

import java.awt.Font;
import java.io.File;

import se.proxus.commands.*;
import se.proxus.gui.*;
import se.proxus.mods.*;
import se.proxus.panels.*;
import se.proxus.screens.list.screens.*;
import se.proxus.threads.*;
import se.proxus.utils.*;

public class Pendrum {

	public static File mainFolder = null;

	public static File modFolder = null;
	
	public static File panelFolder = null;

	public static Methods methods = new Methods();

	public static Wrapper wrapper = new Wrapper();

	public static Settings sett = new Settings();

	public static Utils utils = new Utils();

	public static Utils3D utils3d = new Utils3D();

	public static ModHandler mods = new ModHandler();

	public static CommandHandler cmds = new CommandHandler();

	public static ScreenManager screens = new ScreenManager();
	
	public static TrueTypeFont ttf = null;

	public static PanelHandler panels = new PanelHandler();
	
	public static RenderMap map = null;

	public Pendrum(String var0) {
		ThreadUpdate var1 = new ThreadUpdate(this);
		var1.run();

		sett.updateAvaible = var1.updateAvaible;
	}

	public Pendrum() {

	}

	public void initPendrum() {		
		this.mainFolder = new File(this.wrapper.getMinecraft().getMinecraftDir(), "Pendrum");
		this.mainFolder.mkdirs();

		this.modFolder = new File(this.mainFolder, "Mods");
		this.modFolder.mkdirs();
		
		this.panelFolder = new File(this.mainFolder, "Panels");
		this.panelFolder.mkdirs();

		this.mods.initModHandler();
		this.utils.log("Info", "Amount of mods added: " + this.mods.modArray.size());

		this.cmds.initCommandHandler();
		this.utils.log("Info", "Amount of commands added: " + this.cmds.cmdHash.size());

		this.sett.initSettings();
		this.utils.log("Info", "Amount of blocks added: " + this.sett.blockArray.size());

		this.screens.initScreenManager();
		this.utils.log("Info", "Amount of screens added: " + this.screens.screenArray.size());

		this.panels.initPanelHandler();
		this.utils.log("Info", "Amount of panels added: " + this.panels.panelArray.size());
	}
	
	public void initNullStuff() {
		if(this.ttf == null) {
			this.ttf = new TrueTypeFont(this.wrapper.getMinecraft(), new Font("Tahoma", Font.PLAIN, 17), 17);
		} if(this.map == null) {
			this.map = new RenderMap();
		}
	}
}