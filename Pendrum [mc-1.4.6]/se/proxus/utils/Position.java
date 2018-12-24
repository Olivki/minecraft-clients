package se.proxus.utils;

public class Position {
	
	public double x = 0;
	
	public double y = 0;
	
	public double z = 0;
	
	public int side = 0;
	
	public boolean canChange = false;
	
	public Position(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Position(double x, double y, double z, boolean canChange) {
		this(x, y, z);
		this.canChange = canChange;
	}
	
	public Position(double x, double y, double z, int side) {
		this(x, y, z);
		this.side = side;
	}
	
	public Position(double x, double y, double z, int side, boolean canChange) {
		this(x, y, z, side);
		this.canChange = canChange;
	}
}