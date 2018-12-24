package se.proxus.panels;

import java.util.ArrayList;

import se.proxus.mods.*;
import se.proxus.panels.list.panels.*;

import net.minecraft.src.*;

public class PanelHandler extends GuiScreen {

	public static ArrayList<BasePanel> panelArray = new ArrayList<BasePanel>();

	public static ArrayList<BasePanel> panelQueueArray = new ArrayList<BasePanel>();

	public static BasePanel currentPanel;

	public void initPanelHandler() {
		for(ModType var0 : ModType.values()) {
			if(!(var0.getType()[6]) && !(var0.getType()[7])) {
				this.addQueuePanel(new PanelPlaceholder(var0.getName(), new PanelInfo(1, 1, 120, 14)));
			}
		}

		this.addPanel(this.setCurrentPanel(new Panels(new PanelInfo(1, 1, 120, 14)), false));
	}

	@Override
	public void drawScreen(int var0, int var1, float var2) {
		for(BasePanel var3 : this.panelArray) {
			var3.draw(var0, var1, this.mc.pm.ttf);
		}
	}

	@Override
	public void mouseClicked(int var0, int var1, int var2) {
		try {
			for(BasePanel var3 : this.panelArray) {
				var3.mouseClicked(var0, var1, var2);
			}
		} catch(Exception var3) {

		}
	}

	public void addPanel(BasePanel var0) {
		if(!(this.panelArray.contains(var0))) {
			this.panelArray.add(var0);
			this.mc.pm.utils.log("Panel", "Added the panel: " + var0.getName());
		}
	}
	
	public void addPanel(BasePanel var0, int var1) {
		if(!(this.panelArray.contains(var0))) {
			this.panelArray.add(var1, var0);
			this.mc.pm.utils.log("Panel", "Added the panel: " + var0.getName());
		}
	}

	public void addQueuePanel(BasePanel var0) {
		if(!(this.panelQueueArray.contains(var0))) {
			this.panelQueueArray.add(var0);
			this.mc.pm.utils.log("Panel", "Added the panel: " + var0.getName() + " (Queue)");
		}
	}

	public void removePanel(BasePanel var0) {
		if(this.panelArray.contains(var0)) {
			var0.getConfig().saveConfig();
			this.panelArray.remove(this.panelArray.indexOf(var0));
			this.mc.pm.utils.log("Panel", "Removed the panel: " + var0.getName());
		}
	}
	
	public BasePanel getPanel(String var0) {
		BasePanel var1 = null;
		
		try {
			for(BasePanel var2 : this.panelArray) {
				if(var2.getName().equalsIgnoreCase(var0)) {
					var1 = var2;
				}
			}
		} catch(Exception var3) {
			
		}
		
		return var1;
	}

	public BasePanel getCurrentPanel() {
		return this.currentPanel;
	}

	public BasePanel setCurrentPanel(BasePanel currentPanel, boolean var0) {
		if(currentPanel != null) {
			if(var0) {
				this.panelArray.remove(this.panelArray.indexOf(currentPanel));
				this.panelArray.add(this.panelArray.size(), currentPanel);
			}

			this.currentPanel = currentPanel;
		}

		return currentPanel;
	}
}