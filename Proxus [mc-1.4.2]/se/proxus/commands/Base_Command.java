package se.proxus.commands;

import se.proxus.*;

public abstract class Base_Command extends Main {

	/**
	 * The method for running commands.
	 * @param var1 - The string to be exuceted.
	 **/
	public abstract void runCommand(String[] var1, String var2);

	/** The method for getting the syntax. **/
	public abstract String getSyntax();

	/** The method for getting the usage. **/
	public abstract String getUsage();

	/** 
	 * The method for getting the name. 
	 * @param var1 - The boolean to get if it's for prediction.
	 **/
	public abstract String getName(boolean var1);

	/**
	 * The method for adding syntax messages.
	 * @param var1 - The syntax String.
	 **/
	protected void addSyntax(String var1) {
		this.utils.addMessage("Syntax : " + var1);
	}
	
	/**
	 * The method for adding mod toggles messages.
	 * @param var1 - The mod name String.
	 **/
	protected void addToggle(String var1, boolean var2) {
		this.utils.addMessage(var1 + " : " + (var2 ? "§aEnabled§f" : "§cDisabled§f") + ".");
	}

	/**
	 * The method for adding chat messages to the client.
	 * @param var1 - The String to add to the chat.
	 **/
	protected void addChat(String var1) {
		this.utils.addMessage(var1);
	}
}