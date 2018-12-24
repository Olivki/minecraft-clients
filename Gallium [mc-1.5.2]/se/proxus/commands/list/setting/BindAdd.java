package se.proxus.commands.list.setting;

import se.proxus.Gallium;
import se.proxus.commands.Command;
import se.proxus.commands.CommandType;
import se.proxus.tools.Colours;

public class BindAdd extends Command {

    public BindAdd(final Gallium client) {
	super("bind", ".bind <add> [mod name] <key>",
		"Binds the key of the selected mod.", client,
		CommandType.SETTING);
    }

    @Override
    public void onCommand(final String message, final String... args) {
	if (args[0].equalsIgnoreCase("add")) {
	    getClient().getMods().getMod(args[1])
		    .setKey(args[2].toUpperCase(), true);
	    getClient().getPlayer().addMessage(
		    Colours.GREY + "Bound '" + Colours.CLIENT_COLOUR
			    + getClient().getMods().getMod(args[1]).getName()
			    + Colours.GREY + "' to '" + Colours.CLIENT_COLOUR
			    + args[2].toUpperCase() + Colours.GREY + "'");
	}
    }
}