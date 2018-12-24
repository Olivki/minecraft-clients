package se.proxus.commands.list.setting;

import se.proxus.Gallium;
import se.proxus.commands.Command;
import se.proxus.commands.CommandType;
import se.proxus.tools.Colours;

public class BindRemove extends Command {

    public BindRemove(final Gallium client) {
	super("bind", ".bind <remove> [mod name]",
		"Removes the key of the selected mod.", client,
		CommandType.SETTING);
    }

    @Override
    public void onCommand(final String message, final String... args) {
	if (args[0].equalsIgnoreCase("remove")) {
	    getClient().getMods().getMod(args[1]).setKey("NONE", true);
	    getClient().getPlayer().addMessage(
		    Colours.GREY + "Bound '"
			    + getClient().getMods().getMod(args[1]).getName()
			    + "' to '" + Colours.CLIENT_COLOUR + "NONE"
			    + Colours.GREY + "'");
	}
    }
}