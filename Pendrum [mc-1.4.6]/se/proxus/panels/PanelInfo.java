package se.proxus.panels;

import se.proxus.*;

public class PanelInfo {
	
	protected int[] position = new int[7];
	
	protected boolean[] states = new boolean[3];
	
	public PanelInfo(int x, int y, int width, int height) {
		this.position[0] = x;
		this.position[1] = y;
		this.position[2] = width;
		this.position[3] = height;
		this.position[4] = 0;
		this.position[5] = 0;
		this.position[6] = 0;
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
	
	public int getDragX() {
		return this.position[4];
	}
	
	public int getDragY() {
		return this.position[5];
	}
	
	public int getRectHeight() {
		return this.position[6];
	}
	
	public boolean isOpen() {
		return this.states[0];
	}
	
	public boolean isDragging() {
		return this.states[1];
	}
	
	public boolean isPinned() {
		return this.states[2];
	}
	
	public boolean isCurrentPanel(BasePanel var0) {
		return Pendrum.panels.getCurrentPanel() == var0;
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
	
	public void setDragX(int var0) {
		this.position[4] = var0;
	}
	
	public void setDragY(int var0) {
		this.position[5] = var0;
	}
	
	public void setRectHeight(int var0) {
		this.position[6] = var0;
	}
	
	public void setOpened(boolean var0) {
		this.states[0] = var0;
	}
	
	public void setDragging(boolean var0) {
		this.states[1] = var0;
	}
	
	public void setPinned(boolean var0) {
		this.states[2] = var0;
	}
}