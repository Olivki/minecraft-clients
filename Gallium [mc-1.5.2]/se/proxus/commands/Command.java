package se.proxus.commands;

import se.proxus.Gallium;

public class Command {

    private String command;
    private String usage;
    private String description;
    private Gallium client;
    private CommandType type;

    public Command(final String command, final String usage,
	    final String description) {
	setCommand(command);
	setUsage(usage);
	setDescription(description);
    }

    public Command(final String command, final String usage,
	    final String description, final Gallium client,
	    final CommandType type) {
	this(command, usage, description);
	setClient(client);
	setType(type);
    }

    public Command(final String command, final String usage,
	    final String description, final Gallium client) {
	this(command, usage, description, client, CommandType.MISC);
    }

    public void onCommand(final String message, final String... args) {
    }

    public boolean checkValues(final float value, final float min,
	    final double max) {
	return value >= min && value <= max;
    }

    public String getCommand() {
	return command;
    }

    public void setCommand(final String command) {
	this.command = command;
    }

    public String getUsage() {
	return usage;
    }

    public void setUsage(final String usage) {
	this.usage = usage;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(final String description) {
	this.description = description;
    }

    public void setClient(final Gallium client) {
	this.client = client;
    }

    public Gallium getClient() {
	return client;
    }

    public CommandType getType() {
	return type;
    }

    public void setType(final CommandType type) {
	this.type = type;
    }
}