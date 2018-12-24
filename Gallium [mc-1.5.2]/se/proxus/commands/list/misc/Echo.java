package se.proxus.commands.list.misc;

import se.proxus.Gallium;
import se.proxus.commands.Command;
import se.proxus.tools.Colours;

public class Echo extends Command {

    public Echo(final Gallium client) {
	super("echo", ".echo [message]",
		"Sends the desired message to the client.", client);
    }

    @Override
    public void onCommand(final String message, final String... args) {
	getClient().getPlayer().addMessage(Colours.GREY + message.substring(5));
    }
}