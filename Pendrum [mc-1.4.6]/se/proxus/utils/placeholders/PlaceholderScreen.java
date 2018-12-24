package se.proxus.utils.placeholders;

import net.minecraft.src.*;

public class PlaceholderScreen {
	
	public String name = "";
	
	public String description = "";
	
	public String author = "";
	
	public GuiScreen screen = null;

	public PlaceholderScreen(String var0, String var1, GuiScreen var2, String var3) {
		this.name = var0;
		this.description = var1;
		this.screen = var2;
		this.author = var3;
	}
}