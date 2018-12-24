package se.proxus.eien.commands;

import se.proxus.eien.Eien;

public class Command {

	private String command;
	private String syntax;
	private String description;
	private CommandType type;

	public Command(final String command, final String syntax, final String description,
			final CommandType type) {
		setCommand(command);
		setSyntax(syntax);
		setDescription(description);
		setType(type);
	}

	public Command(final String command, final String usage, final String description) {
		this(command, usage, description, CommandType.MISC);
	}

	public void onCommand(final String message, final String... args) {
	}

	public boolean checkValues(final float value, final float min, final double max) {
		return value >= min && value <= max;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(final String command) {
		this.command = command;
	}

	public String getSyntax() {
		return syntax;
	}

	public void setSyntax(final String syntax) {
		this.syntax = syntax;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Eien getClient() {
		return Eien.getInstance();
	}

	public CommandType getType() {
		return type;
	}

	public void setType(final CommandType type) {
		this.type = type;
	}
}