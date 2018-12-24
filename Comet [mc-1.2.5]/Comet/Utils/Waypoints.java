package Comet.Utils;

public class Waypoints {

	public double posX;
	public double posY;
	public double posZ;
	public String name;
	
	public Waypoints(String pointName, double x, double y, double z) {
		posX = x; posY = y; posZ = z; name = pointName;
	}		
	
	public String getName() {
		return name;
	}	
}
