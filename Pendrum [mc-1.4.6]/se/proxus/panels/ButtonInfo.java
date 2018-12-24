package se.proxus.panels;

public class ButtonInfo {
	
	protected int[] position = new int[4];
	
	protected boolean[] states = new boolean[1];
	
	public ButtonInfo(int x, int y, int width, int height) {
		this.setX(x);
		this.setX(y);
		this.setWidth(width);
		this.setHeight(height);
	}
	
	public int getX() {
		return this.position[0];
	}
	
	public int getY() {
		return this.position[1];
	}
	
	public int getWidth() {
		return this.position[2];
	}
	
	public int getHeight() {
		return this.position[3];
	}
	
	public boolean getState() {
		return this.states[0];
	}
	
	public boolean isEnabled() {
		return this.states[1];
	}
	
	public void setX(int var0) {
		this.position[0] = var0;
	}
	
	public void setY(int var0) {
		this.position[1] = var0;
	}
	
	public void setWidth(int var0) {
		this.position[2] = var0;
	}
	
	public void setHeight(int var0) {
		this.position[3] = var0;
	}
	
	public void setState(boolean var0) {
		this.states[0] = var0;
	}
	
	public void setEnabled(boolean var0) {
		this.states[1] = var0;
	}
}