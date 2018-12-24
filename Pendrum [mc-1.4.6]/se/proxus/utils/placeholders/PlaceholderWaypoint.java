package se.proxus.utils.placeholders;

public class PlaceholderWaypoint {
	
	public String name = "";
	
	public String ip = "";
	
	public int x = 0;
	
	public int y = 0;
	
	public int z = 0;
	
	public int color = 0;
	
	public PlaceholderWaypoint(String name, int x, int y, int z, int color, String ip) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
		this.color = color;
		this.ip = ip;
	}
}