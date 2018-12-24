package se.proxus.mods;

import java.awt.Window.Type;
import java.util.*;

import net.minecraft.src.*;

import se.proxus.*;
import se.proxus.events.*;
import se.proxus.events.player.*;
import se.proxus.events.render.*;
import se.proxus.events.server.*;
import se.proxus.events.world.*;
import se.proxus.mods.list.*;

public class ModHandler extends Pendrum {

	public static ArrayList<BaseMod> modArray = new ArrayList<BaseMod>();

	public static ModxRay xray = new ModxRay();

	public static ModNoCheat nocheat = new ModNoCheat();

	public static ModFlight flight = new ModFlight();

	public static ModSprint sprint = new ModSprint();

	public static ModBrightness brightness = new ModBrightness();

	public static ModForcefield forcefield = new ModForcefield();

	public static ModMiner miner = new ModMiner();

	public static ModGui gui = new ModGui();

	public static ModInstant instant = new ModInstant();

	public static ModStep step = new ModStep();

	public static ModESP esp = new ModESP();

	public static ModTracer tracer = new ModTracer();

	public static ModAntiVelocity antiVelocity = new ModAntiVelocity();

	public static ModWaypoint waypoints = new ModWaypoint();

	public static ModPanels panels = new ModPanels();

	public static ModFreecam freecam = new ModFreecam();

	public static ModNoFall nofall = new ModNoFall();

	public static ModHUD hud = new ModHUD();

	public static ModEffects effects = new ModEffects();

	public static ModStatus status = new ModStatus();

	public static ModTTF ttf = new ModTTF();

	public static ModAutoTool autoTool = new ModAutoTool();

	public static ModAutoSoup autoSoup = new ModAutoSoup();

	public static ModPlayerInfo playerInfo = new ModPlayerInfo();

	public static ModChestESP chestESP = new ModChestESP();

	public static ModJesus jesus = new ModJesus();

	public void initModHandler() {
		this.addMod(this.gui);
		this.addMod(this.instant);
		this.addMod(this.forcefield);
		this.addMod(this.flight);
		this.addMod(this.nocheat);
		this.addMod(this.sprint);
		this.addMod(this.xray);
		this.addMod(this.brightness);
		this.addMod(this.miner);
		this.addMod(this.step);
		this.addMod(this.esp);
		this.addMod(this.tracer);
		this.addMod(this.antiVelocity);
		this.addMod(this.waypoints);
		this.addMod(this.panels);
		this.addMod(this.freecam);
		this.addMod(this.nofall);
		this.addMod(this.hud);
		this.addMod(this.effects);
		this.addMod(this.status);
		this.addMod(this.ttf);
		this.addMod(this.autoTool);
		this.addMod(this.autoSoup);
		this.addMod(this.playerInfo);
		this.addMod(this.chestESP);
		this.addMod(this.jesus);
	}

	public void handleKeybinds(String var0) {
		try {
			for(BaseMod var1 : this.modArray) {
				if(var1.getInfo().getKey().equalsIgnoreCase(var0) && !(var1.getInfo().getKey().equalsIgnoreCase("NONE"))) {
					var1.toggle();
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void handleUpdate() {
		try {	
			this.handleEvent(new EventUpdate());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void handlePostMotionUpdate() {
		try {	
			this.handleEvent(new EventPostUpdate());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void handle2DRendering() {
		try {	
			this.handleEvent(new EventRender(EventRender.Type.TwoDimensional));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void handle3DRendering() {
		try {	
			this.handleEvent(new EventRender(EventRender.Type.ThreeDimensional));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void handleChat(Packet3Chat var0) {
		try {	
			this.handleEvent(new EventChat(var0.message, EventChat.Type.RECEIVE));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void handeBlockClicked(int var0, int var1, int var2, int var3) {
		try {
			this.handleEvent(new EventBlockClicked(var0, var1, var2, var3));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void handleEvent(Event var0) {
		for(BaseMod var1 : this.modArray) {
			if(var1.getEvent().getEventClassArray().contains(var0.getClass())) {
				var1.onEvent(var0);
			}
		}
	}

	public void addMod(BaseMod var0) {
		if(!(this.modArray.contains(var0))) {
			this.modArray.add(var0);
			this.utils.log("Mod", "Added the mod: " + var0.getName());
		}
	}

	public BaseMod getMod(String var0) {
		BaseMod var1 = null;

		try {
			for(BaseMod var2 : this.modArray) {
				if(var2.getName().equalsIgnoreCase(var0.replace(" ", "_"))) {
					var1 = var2;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			var1 = null;
		}

		return var1;
	}
}