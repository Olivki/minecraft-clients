package se.proxus.rori.commands.list.listhandler;

import se.proxus.rori.commands.Command;
import se.proxus.rori.commands.CommandManager;
import se.proxus.rori.commands.CommandType;
import se.proxus.rori.tools.Colours;
import se.proxus.rori.tools.StringTools;
import se.proxus.rori.tools.Tools;

public class Help extends Command {

	public Help() {
		super("help", ".help <command|page>",
				"Gives you a list of commands, or help on a certain command.",
				CommandType.LISTHANDLER);
	}

	@Override
	public void onCommand(final String message, final String... args) {
		CommandManager commandManager = CommandManager.getInstance();

		if (message.length() == getCommand().length()) {
			int commandsPerPage = 9;
			Command[] commands = commandManager.getRegisteredCommands();
			commandManager.sortCommands(commands);
			int pages = commands.length / commandsPerPage
					+ (commands.length % commandsPerPage > 0 ? 1 : 0);
			Tools.addChatMessage(Colours.CLIENT_COLOUR + "------------[" + Colours.GREY + "Help (1"
					+ "/" + pages + ")" + Colours.CLIENT_COLOUR + "]------------");
			for (int i = 0; i < commandsPerPage; i++) {
				if (i + 1 > commands.length) {
					break;
				}
				Tools.addChatMessage(Colours.CLIENT_COLOUR + commands[i].getCommand() + ": "
						+ Colours.GREY + commands[i].getSyntax());
			}
		} else if (message.length() > getCommand().length()) {
			if (StringTools.isInteger(args[0])) {
				int page = Integer.parseInt(args[0]);
				int commandsPerPage = 9;
				Command[] commands = commandManager.getRegisteredCommands();
				commandManager.sortCommands(commands);
				int pages = commands.length / commandsPerPage
						+ (commands.length % commandsPerPage > 0 ? 1 : 0);
				if (page < 1 || page > pages) {
					Tools.addChatMessage("Page not found.");
					return;
				}
				Tools.addChatMessage(Colours.CLIENT_COLOUR + "------------[" + Colours.GREY
						+ "Help (" + page + "/" + pages + ")" + Colours.CLIENT_COLOUR
						+ "]------------");
				for (int i = 0; i < commandsPerPage; i++) {
					if (i + (page - 1) * commandsPerPage + 1 > commands.length) {
						break;
					}
					Tools.addChatMessage(Colours.CLIENT_COLOUR
							+ commands[i + (page - 1) * commandsPerPage].getCommand() + ": "
							+ Colours.GREY + commands[i + (page - 1) * commandsPerPage].getSyntax());
				}
			} else {
				Command command = commandManager.getCommandByName(args[0]);
				Tools.addChatMessage(Colours.CLIENT_COLOUR + command.getSyntax() + ": "
						+ Colours.GREY + command.getDescription());
			}
		}
	}
}