package se.proxus.commands.list.misc;

import se.proxus.Gallium;
import se.proxus.commands.Command;
import se.proxus.tools.Colours;

public class Reload extends Command {

    public Reload(final Gallium client) {
	super("reload", ".reload",
		"Tries to reload most of the stuff in the client, might cause crashes, etc. "
			+ Colours.DARK_RED + "(DEBUG)" + Colours.RESET, client);
    }

    @Override
    public void onCommand(final String message, final String... args) {
	getClient().getPlayer().addMessage(
		Colours.GREY + "Trying to reload the client..");
	getClient().getCommands().getLoadedCommands().clear();
	getClient().init(0);
	getClient().init(1);
	getClient().getPlayer().addMessage(
		Colours.GREY + "The client has been reloaded!");
    }
}