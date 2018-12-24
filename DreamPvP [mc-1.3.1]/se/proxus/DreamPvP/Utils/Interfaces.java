package se.proxus.DreamPvP.Utils;

import java.util.ArrayList;

import se.proxus.DreamPvP.Interfaces.*;

public class Interfaces extends Utils {
	
	public static ArrayList<Updates> updateArray = new ArrayList<Updates>();
	public static ArrayList<Renderer> renderArray = new ArrayList<Renderer>();
	public static ArrayList<ClickBlock> clickBlockArray = new ArrayList<ClickBlock>();
	
	public static void onUpdate() {
		for(Updates updates : updateArray) {
			updates.onUpdate();
		}
	}
	
	public static void onRendered() {
		for(Renderer render : renderArray) {
			render.onRendered();
		}
	}
	
	public static void onClickBlock(int i1, int i2, int i3, int i4) {
		for(ClickBlock clickBlock : clickBlockArray) {
			clickBlock.onClickBlock(i1, i2, i3, i4);
		}
	}
}