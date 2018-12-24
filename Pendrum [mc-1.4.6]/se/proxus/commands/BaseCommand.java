package se.proxus.commands;

import se.proxus.*;

public abstract class BaseCommand extends Pendrum {

	protected String name = "";

	protected String description = "";

	protected String syntax = "";

	protected String author = "";

	public BaseCommand(String name, String description, String syntax, String author) {
		this.name = name;
		this.description = description;
		this.syntax = syntax;
		this.author = author;
	}

	public abstract void onCommand(String[] var0, String var1);

	public void addChat(char var0, String var1) {
		if(this.cmds.state == 0) {
			this.utils.addMessage("§" + var0 + var1);
		}
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public String getSyntax() {
		return this.syntax;
	}

	public String getAuthor() {
		return this.author;
	}
}