package se.proxus.events.render;

import se.proxus.events.*;

public class EventRender extends Event {
	
	protected Type type;
	
	public EventRender(Type type) {
		this.setType(type);
	}

	public enum Type {
		TwoDimensional,
		ThreeDimensional
	}
	
	public Type getType() {
		return this.type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}