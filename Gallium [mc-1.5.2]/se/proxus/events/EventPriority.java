package se.proxus.events;

public enum EventPriority {

    LOWEST(0), LOW(1), MEDIUM(2), HIGH(3), HIGHEST(4);

    private int level;

    private EventPriority(final int level) {
	setLevel(level);
    }

    public int getLevel() {
	return level;
    }

    public void setLevel(final int level) {
	this.level = level;
    }
}