package se.proxus.panels.list.panels;

import se.proxus.panels.*;
import se.proxus.panels.list.buttons.*;

public class Panels extends BasePanel {

	public Panels(PanelInfo info) {
		super("Panels", info);
	}
	
	@Override
	public void setupButtons() {
		for(BasePanel var0 : this.getPendrum().panels.panelQueueArray) {
			this.addButton(new ButtonPanel(new ButtonInfo(0, 0, this.getInfo().getWidth() - 6, 12), var0));
		}
	}
}