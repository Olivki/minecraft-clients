package se.proxus.DreamPvP.Utils.Placeholders;

public class u_Block {
	
	public int id, x, y, z;
	
	public u_Block(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public u_Block(int id, int x, int y, int z) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public u_Block(int id) {
		this.id = id;
	}
}