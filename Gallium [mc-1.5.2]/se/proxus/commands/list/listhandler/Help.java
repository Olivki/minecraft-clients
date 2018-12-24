package se.proxus.commands.list.listhandler;

import se.proxus.Gallium;
import se.proxus.commands.Command;
import se.proxus.commands.CommandType;
import se.proxus.tools.Colours;
import se.proxus.tools.StringTools;

public class Help extends Command {

    public Help(final Gallium client) {
	super("help", ".help <command|page>", "Gives you a list of command.",
		client, CommandType.LISTHANDLER);
    }

    @Override
    public void onCommand(final String message, final String... args) {
	if (message.length() == getCommand().length()) {
	    int commandsPerPage = 6;
	    Command[] commands = getClient().getCommands()
		    .getRegisteredCommands();
	    getClient().getCommands().sortCommands(commands);
	    int pages = commands.length / commandsPerPage
		    + (commands.length % commandsPerPage > 0 ? 1 : 0);
	    getClient().getPlayer().addMessage(
		    Colours.CLIENT_COLOUR + "------------[" + Colours.GREY
			    + "Help (1" + "/" + pages + ")"
			    + Colours.CLIENT_COLOUR + "]------------");
	    for (int i = 0; i < commandsPerPage; i++) {
		if (i + 1 > commands.length)
		    break;
		getClient().getPlayer().addMessage(
			Colours.CLIENT_COLOUR + commands[i].getCommand() + ": "
				+ Colours.GREY + commands[i].getUsage());
	    }
	} else if (message.length() > getCommand().length())
	    if (StringTools.isInteger(args[0])) {
		int page = Integer.parseInt(args[0]);
		int commandsPerPage = 6;
		Command[] commands = getClient().getCommands()
			.getRegisteredCommands();
		getClient().getCommands().sortCommands(commands);
		int pages = commands.length / commandsPerPage
			+ (commands.length % commandsPerPage > 0 ? 1 : 0);
		if (page < 1 || page > pages) {
		    getClient().getPlayer().addMessage(
			    Colours.GREY + "Page not found.");
		    return;
		}
		getClient().getPlayer().addMessage(
			Colours.CLIENT_COLOUR + "------------[" + Colours.GREY
				+ "Help (" + page + "/" + pages + ")"
				+ Colours.CLIENT_COLOUR + "]------------");
		for (int i = 0; i < commandsPerPage; i++) {
		    if (i + (page - 1) * commandsPerPage + 1 > commands.length)
			break;
		    getClient().getPlayer()
			    .addMessage(
				    Colours.CLIENT_COLOUR
					    + commands[i + (page - 1)
						    * commandsPerPage]
						    .getCommand()
					    + ": "
					    + Colours.GREY
					    + commands[i + (page - 1)
						    * commandsPerPage]
						    .getUsage());
		}
	    } else {
		Command command = getClient().getCommands().getCommandByName(
			args[0]);
		getClient().getPlayer().addMessage(
			Colours.CLIENT_COLOUR + command.getUsage() + ": "
				+ Colours.GREY + command.getDescription());
	    }
    }
}