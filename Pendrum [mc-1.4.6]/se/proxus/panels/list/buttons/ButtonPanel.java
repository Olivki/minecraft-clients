package se.proxus.panels.list.buttons;

import se.proxus.gui.*;
import se.proxus.mods.*;
import se.proxus.panels.*;

public class ButtonPanel extends Button {
	
	protected BasePanel panel;

	public ButtonPanel(ButtonInfo info, BasePanel panel) {
		super("", info);
		this.setPanel(panel);
	}

	@Override
	public void draw(int var0, int var1, TrueTypeFont var2) {
		this.setName(this.getPanel().getName());
		this.getInfo().setState(this.getPendrum().panels.panelArray.contains(this.getPanel()));
		super.draw(var0, var1, var2);
	}

	@Override
	public void mouseClicked(int var0, int var1, int var2) {
		super.mouseClicked(var0, var1, var2);
	}
	
	public void onClicked() {
		if(!(this.getPendrum().panels.panelArray.contains(this.getPanel()))) {
			this.getPendrum().panels.addPanel(this.getPanel());
			this.getPendrum().panels.setCurrentPanel(this.getPanel(), false);
		} else {
			this.getPendrum().panels.removePanel(this.getPanel());
			this.getPendrum().panels.setCurrentPanel(this.getPendrum().panels.getPanel("Panels"), false);
		}
	}
	
	public BasePanel getPanel() {
		return this.panel;
	}

	public void setPanel(BasePanel panel) {
		this.panel = panel;
	}
}