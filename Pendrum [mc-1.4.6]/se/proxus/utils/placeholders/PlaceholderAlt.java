package se.proxus.utils.placeholders;

import se.proxus.Pendrum;

public class PlaceholderAlt {

	public String username = "";
	
	public String password = "";
	
	public boolean offline = false;

	public PlaceholderAlt(String var0, String var1) {
		this.username = var0;
		this.password = var1;
		this.offline = var1.isEmpty();
		Pendrum.utils.log("Info", "Added the alt: " + this.username + ":" + Pendrum.utils.appendText(this.password, "*") + ":" + this.offline);
	}
}