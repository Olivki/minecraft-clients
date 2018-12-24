package ab.Bytes.Render;

import java.util.ArrayList;

public final class Base_RenderList {
	
	public static ArrayList<Base_Render> renderArray = new ArrayList<Base_Render>();
	
	public static r_Waypoint waypoint = new r_Waypoint();
	
	public Base_RenderList() {
		checkRenderArray();
	}
	
	public static void checkRenderArray() {
		renderArray.clear();
		renderArray.add(waypoint);
	}
	
	public static void onRenderGlobal() {
		for(Base_Render render : renderArray) {
			render.onRenderGlobal();
		}
	}
}