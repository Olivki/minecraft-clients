package se.proxus.DreamPvP.Utils.Placeholders;

public class u_Waypoint {
	
	public String name, ip;
	public double x, y, z;
	public float r, g, b;
	
	public u_Waypoint(double x, double y, double z, float r, float g, float b, String ip, String name) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.r = r;
		this.g = g;
		this.b = b;
		this.ip = ip;
		this.name = name;
	}
	
	public String getRGB() {
		return "" + r + g + b;
	}
	
	public String getPos() {
		return (int)x + ", " + (int)y + ", " + (int)z + ".";
	}
}