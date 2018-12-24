package se.proxus.events.world;

import se.proxus.events.*;

public class EventBlockClicked extends Event {

	protected int[] position = new int[5];

	public EventBlockClicked(int x, int y, int z, int side) {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
		this.setSide(side);
	}

	public int getX() {
		return this.position[0];
	}

	public int getY() {
		return this.position[1];
	}

	public int getZ() {
		return this.position[2];
	}

	public int getSide() {
		return this.position[3];
	}

	public void setX(int var0) {
		this.position[0] = var0;
	}

	public void setY(int var0) {
		this.position[1] = var0;
	}

	public void setZ(int var0) {
		this.position[2] = var0;
	}

	public void setSide(int var0) {
		this.position[3] = var0;
	}
}