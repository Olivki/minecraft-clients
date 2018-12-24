package se.proxus.panels;

public class PanelColors {

	public int PANEL_BORDER = 0xFF676767;

	public int PANEL_INNER = 0x9F373737;

	public int PANEL_TOP_INNER = 0xFF373737;

	public int PANEL_RECTANGLE = 0x40000000;

	public int PANEL_NAME = 0xFFFFFFFF;

	private PanelInfo info;

	private BasePanel panel;

	public PanelColors(PanelInfo info, BasePanel panel) {
		this.info = info;
		this.panel = panel;
	}

	public void setColors(int var0, int var1) {
		if(this.getPanel().getPendrum().wrapper.getMinecraft().currentScreen instanceof PanelHandler) {
			if(this.getInfo().isCurrentPanel(this.getPanel())) {
				this.PANEL_NAME = 0xFFFFFFFF;
			} else {
				this.PANEL_NAME = 0xFF909090;
			}
		}
	}

	public PanelInfo getInfo() {
		return this.info;
	}

	public void setInfo(PanelInfo info) {
		this.info = info;
	}

	public BasePanel getPanel() {
		return this.panel;
	}

	public void setPanel(BasePanel panel) {
		this.panel = panel;
	}
}