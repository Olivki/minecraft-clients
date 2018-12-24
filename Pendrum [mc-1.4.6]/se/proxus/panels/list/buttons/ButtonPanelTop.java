package se.proxus.panels.list.buttons;

import se.proxus.*;
import se.proxus.gui.*;
import se.proxus.panels.*;
import se.proxus.panels.list.panels.*;

public class ButtonPanelTop extends BaseButton {

	protected int BUTTON_REMOVE = 0xFFCD0000;

	protected BasePanel panel;

	protected boolean newState;

	public ButtonPanelTop(String name, ButtonInfo info, BasePanel panel, boolean newState) {
		super(name, info);
		this.setPanel(panel);
	}

	@Override
	public void draw(int var0, int var1, TrueTypeFont var2) {
		/** COLORS START **/
		this.getColors().setColors(var0, var1);
		/** COLORS STOP **/

		/** DRAWING START **/
		if(this.getName().equalsIgnoreCase("X")) {
			if(this.getPanel() instanceof Panels) {
				var2.drawStringWithShadow(this.getName(), this.getInfo().getX(), this.getInfo().getY(), 0xFF808080);
			} else {
				var2.drawStringWithShadow(this.getName(), this.getInfo().getX(), this.getInfo().getY(), this.BUTTON_REMOVE);
			}
		} else {
			var2.drawStringWithShadow(this.getName(), this.getInfo().getX(), this.getInfo().getY(), this.getColors().BUTTON_NAME);
		}
		/** DRAWING STOP **/
	}

	@Override
	public void mouseClicked(int var0, int var1, int var2) {
		if(this.isHovering(var0, var1) && var2 == 0) {
			if(this.getName().equalsIgnoreCase("X") && !(this.getPanel() instanceof Panels)) {
				Pendrum.utils.playSound();
				this.getInfo().setState(!(this.getInfo().getState()));
				this.getPendrum().panels.removePanel(this.getPanel());
				this.getPendrum().panels.setCurrentPanel(this.getPendrum().panels.getPanel("Panels"), true);
			} else if(!(this.getName().equalsIgnoreCase("X"))) {
				if(this.getName().equalsIgnoreCase("_")) {
					Pendrum.utils.playSound();
					this.getInfo().setState(!(this.getInfo().getState()));
					this.getPanel().getInfo().setOpened(this.getInfo().getState());
					this.getPendrum().panels.setCurrentPanel(this.getPanel(), true);
				} if(this.getName().equalsIgnoreCase("*")) {
					Pendrum.utils.playSound();
					this.getInfo().setState(!(this.getInfo().getState()));
					this.getPanel().getInfo().setPinned(this.getInfo().getState());
					this.getPendrum().panels.setCurrentPanel(this.getPanel(), true);
				}
			}
		}
	}

	public BasePanel getPanel() {
		return this.panel;
	}

	public boolean getNewState() {
		return newState;
	}

	public void setPanel(BasePanel panel) {
		this.panel = panel;
	}

	public void setNewState(boolean newState) {
		this.newState = newState;
	}

	public boolean isHovering(int var0, int var1) {
		return var0 >= this.getInfo().getX() - 1 && var1 >= this.getInfo().getY() - 2 && var0 <= this.getInfo().getX() + this.getInfo().getWidth() + 2 && var1 <= this.getInfo().getY() + this.getInfo().getHeight() + 1;
	}
}