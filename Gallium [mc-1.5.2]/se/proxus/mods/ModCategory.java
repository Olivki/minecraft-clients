package se.proxus.mods;

import se.proxus.tools.Colours;

public enum ModCategory {

    COMBAT("Combat", Colours.RED), WORLD("World", Colours.GREY), MOVEMENT(
	    "Movement", Colours.GOLD), RENDER("Render", Colours.YELLOW), SERVER(
	    "Server", Colours.DARK_AQUA), GUI("Gui", Colours.WHITE), MISC(
	    "Misc", Colours.GREY), NONE("None", Colours.BLACK);

    private String name;
    private String colour;

    private ModCategory(final String name, final String colour) {
	setName(name);
	setColour(colour);
    }

    public String getName() {
	return name;
    }

    public void setName(final String name) {
	this.name = name;
    }

    public String getColour() {
	return colour;
    }

    public void setColour(final String colour) {
	this.colour = colour;
    }
}