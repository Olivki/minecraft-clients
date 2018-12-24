package se.proxus.eien.mods.list.gui;

import java.util.ArrayList;

import net.minecraft.client.gui.FontRenderer;
import se.proxus.eien.events.EventHandler;
import se.proxus.eien.events.EventManager;
import se.proxus.eien.events.list.client.EventRendered2D;
import se.proxus.eien.frames.FrameManager;
import se.proxus.eien.frames.components.Frame;
import se.proxus.eien.mods.Mod;
import se.proxus.eien.mods.ModCategory;
import se.proxus.eien.mods.ModManager;
import se.proxus.eien.tools.Colours;
import se.proxus.eien.tools.Tools;

public class Overlay extends Mod {

	public Overlay() {
		super("Overlay", ModCategory.GUI, true);
	}

	@Override
	public void init() {
		addValue("Bland Arraylist", false,
				"If the arraylist should be rendered without any colours.", true, true);
		setValue("Description", "Renders the ingame overlay.", false);
		setValue("State", true, false);
		checkState();
	}

	@Override
	public void onEnable() {
		EventManager.registerListener(this);
	}

	@Override
	public void onDisable() {
		EventManager.unregisterListener(this);
	}

	@EventHandler
	public void onRendered2D(final EventRendered2D event) {
		FontRenderer font = event.getFont();

		font.drawStringWithShadow(getClient().toString(), 2, 2, 0xFFFFFFFF);
		font.drawStringWithShadow(Tools.getPlayerLocation().toString(), 2, 12, 0xFFFFFFFF);

		ModManager mods = ModManager.getInstance();
		ArrayList<Mod> activeMods = mods.getActiveMods();
		for (int modLine = 0; modLine < activeMods.size(); modLine++) {
			Mod mod = activeMods.get(modLine);
			String name = (getValue("Bland Arraylist").getBoolean() ? Colours.RESET : mod
					.getCategory().getColour()) + mod.getName();
			font.drawStringWithShadow(name, Tools.getScaledWidth() - font.getStringWidth(name) - 2,
					3 + modLine * 10, 0xFFFFFFFF);
		}

		for (Frame frame : FrameManager.getInstance().getLoadedFrames()) {
			if (frame.isPinned()
					&& getClient().getMinecraft().currentScreen != FrameManager.getInstance()) {
				frame.draw(getClient().getMinecraft().fontRendererObj, -900, -900, 1.0F);
			}
		}

	}
}