package ab.Bytes.Utils;

public class Waypoint {
	
	public double posX, posY, posZ, colR, colG, colB;
	public String name;
	
	public Waypoint(String names, double posXs, double posYs, double posZs, double colRs, double colGs, double colBs) {
		posX = posXs;
		posY = posYs;
		posZ = posZs;
		colR = colRs;
		colG = colGs;
		colB = colBs;
	}
}