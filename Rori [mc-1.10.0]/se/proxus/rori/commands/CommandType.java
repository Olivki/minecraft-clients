package se.proxus.rori.commands;

public enum CommandType {

	MISC("Misc", 0), CONFIG("Config", 1), LISTHANDLER("List Handler", 2);

	private String name;
	private int id;

	private CommandType(final String name, final int id) {
		setName(name);
		setId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}
}