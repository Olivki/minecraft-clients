package se.proxus.commands.list.misc;

import se.proxus.Gallium;
import se.proxus.commands.Command;

public class Say extends Command {

    public Say(final Gallium client) {
	super("say", ".say [message]",
		"Sends the desired message to the chat.", client);
    }

    @Override
    public void onCommand(final String message, final String... args) {
	if (message.substring(3).isEmpty())
	    return;
	getClient().getPlayer().sendMessage(message.substring(3));
    }
}