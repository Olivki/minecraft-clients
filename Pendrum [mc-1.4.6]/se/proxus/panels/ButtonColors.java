package se.proxus.panels;

public class ButtonColors {

	public int BUTTON_BORDER = 0xFF676767;

	public int BUTTON_INNER = 0xFF474747;

	public int BUTTON_NAME = 0xFFFFFFFF;

	private ButtonInfo info;

	private BaseButton button;

	public ButtonColors(ButtonInfo info, BaseButton button) {
		this.setInfo(info);
		this.setButton(button);
	}

	public void setColors(int var0, int var1) {
		if(!(this.getButton().getName().equalsIgnoreCase("X"))) {
			if(this.getButton().getInfo().getState()) {
				this.BUTTON_NAME = 0xFFFFFFFF;
			} else {
				this.BUTTON_NAME = 0xFFB3B3B3;
			} if(this.getButton().isHovering(var0, var1) && this.getButton().getPendrum().wrapper.getMinecraft().currentScreen instanceof PanelHandler) {
				this.BUTTON_NAME = 0xFFFFFF55;
			}
		}
	}

	public ButtonInfo getInfo() {
		return this.info;
	}

	public void setInfo(ButtonInfo info) {
		this.info = info;
	}

	public BaseButton getButton() {
		return this.button;
	}

	public void setButton(BaseButton button) {
		this.button = button;
	}
}