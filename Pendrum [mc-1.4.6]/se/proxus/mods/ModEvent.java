package se.proxus.mods;

import java.util.ArrayList;

import se.proxus.events.*;

public class ModEvent {
	
	protected BaseMod mod;
	
	protected ArrayList<Class<? extends Event>> eventClassArray;
	
	protected ArrayList<Event> eventArray;
	
	public ModEvent(BaseMod mod) {
		this.setMod(mod);
		this.setEventClassArray(new ArrayList<Class<? extends Event>>());
		this.setEventArray(new ArrayList<Event>());
	}
	
	public void registerEvent(Class<? extends Event> var0) {
		if(!(this.getEventClassArray().contains(var0))) {
			this.getEventClassArray().add(var0);
		}
	}

	public ArrayList<Class<? extends Event>> getEventClassArray() {
		return this.eventClassArray;
	}
	
	public ArrayList<Event> getEventArray() {
		return this.eventArray;
	}

	public void setEventClassArray(ArrayList<Class<? extends Event>> eventClassArray) {
		this.eventClassArray = eventClassArray;
	}
	
	public void setEventArray(ArrayList<Event> eventArray) {
		this.eventArray = eventArray;
	}

	public BaseMod getMod() {
		return this.mod;
	}

	public void setMod(BaseMod mod) {
		this.mod = mod;
	}
}