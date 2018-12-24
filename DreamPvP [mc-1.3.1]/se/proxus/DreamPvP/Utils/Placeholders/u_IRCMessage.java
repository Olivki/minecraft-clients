package se.proxus.DreamPvP.Utils.Placeholders;

import se.proxus.DreamPvP.Utils.Utils;

public class u_IRCMessage {
	
	public String username, message, wholeMessage;
	
	public u_IRCMessage(String username, String message) {
		this.username = username;
		this.message = message;
	}
	
	public u_IRCMessage(String message) {
		this.wholeMessage = message;
	}
	
	public String getMessage() {
		boolean isOlivki = username.equalsIgnoreCase("Olivki");
		String msg = (isOlivki ? "[§eCreator§r] " : "") + "[" + Utils.donatorCol(username) + "] : " + message;
		
		return msg;
	}
}