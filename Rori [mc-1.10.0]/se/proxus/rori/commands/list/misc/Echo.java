package se.proxus.rori.commands.list.misc;

import se.proxus.rori.commands.Command;
import se.proxus.rori.tools.Tools;

public class Echo extends Command {

	public Echo() {
		super("echo", ".echo [message]", "Sends the desired message to the client.");
	}

	@Override
	public void onCommand(final String message, final String... args) {
		Tools.addChatMessage(message.substring(getCommand().length() + 1));
	}
}