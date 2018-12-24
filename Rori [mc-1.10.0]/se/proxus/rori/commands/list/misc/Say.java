package se.proxus.rori.commands.list.misc;

import se.proxus.rori.commands.Command;
import se.proxus.rori.tools.Tools;

public class Say extends Command {

	public Say() {
		super("say", ".say [message]", "Sends the desired message to the chat.");
	}

	@Override
	public void onCommand(final String message, final String... args) {
		if (message.substring(getCommand().length() + 1).isEmpty()) {
			return;
		}

		Tools.sendChatMessage(message.substring(getCommand().length() + 1));
	}
}