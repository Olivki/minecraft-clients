package se.proxus.events.server;

import net.minecraft.src.*;
import se.proxus.events.*;

public class EventChat extends Event {
	
	protected String message;
	
	protected Type type;

	public EventChat(String message, Type type) {
		this.setMessage(message);
		this.setType(type);
	}

	public enum Type {
		RECEIVE,
		SEND
	}
	
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Type getType() {
		return this.type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}