package se.proxus.eien.commands.list.misc;

import se.proxus.eien.commands.Command;
import se.proxus.eien.tools.Tools;

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