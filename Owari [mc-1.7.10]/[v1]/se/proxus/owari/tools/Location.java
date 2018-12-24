package se.proxus.owari.tools;

import net.minecraft.entity.Entity;

public class Location {

	private double x;
	private double y;
	private double z;

	public Location(final double x, final double y, final double z) {
		setX(x);
		setY(y);
		setZ(z);
	}

	public double getX() {
		return x;
	}

	public void setX(final double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(final double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(final double z) {
		this.z = z;
	}

	@Override
	public String toString() {
		return "(" + (int) getX() + ", " + (int) getY() + ", " + (int) getZ() + ")";
	}

	public double distanceTo(final double x, final double y, final double z) {
		return Math.sqrt((getX() - x) * (getX() - x) + (getZ() - z) * (getZ() - z));
	}

	public double distanceTo(final Location location) {
		return distanceTo(location.getX(), location.getY(), location.getZ());
	}

	public Location entityToLocation(final Entity entity) {
		return new Location(entity.posX, entity.posY, entity.posZ);
	}

	public void locationToEntity(final Entity entity) {
		entity.setPosition(getX(), getY(), getZ());
	}

	public Location parseLocation(final String location) {
		try {
			String[] split = location.replace("(", "").replace(")", "").split(", ");
			double x = Double.parseDouble(split[0]);
			double y = Double.parseDouble(split[1]);
			double z = Double.parseDouble(split[2]);
			return new Location(x, y, z);
		} catch (Exception exception) {
			exception.printStackTrace();
			return new Location(0, 0, 0);
		}
	}
}