package se.proxus.commands.list.setting;

import se.proxus.Gallium;
import se.proxus.commands.Command;
import se.proxus.commands.CommandType;
import se.proxus.mods.Mod.ModController;
import se.proxus.tools.Colours;

public class Toggle extends Command {

    public Toggle(final Gallium client) {
	super("toggle", ".toggle [mod name]", "Toggles the selected mod.",
		client, CommandType.SETTING);
    }

    @Override
    public void onCommand(final String message, final String... args) {
	if (getClient().getMods().getMod(args[0]).getController()
		.equals(ModController.TOGGLE)) {
	    getClient().getMods().getMod(args[0]).toggle();
	    getClient().getPlayer()
		    .addMessage(
			    Colours.GREY
				    + getClient().getMods().getMod(args[0])
					    .getName()
				    + " is now "
				    + (getClient().getMods().getMod(args[0])
					    .getState() ? "enabled."
					    : "disabled."));
	} else
	    getClient().getPlayer().addMessage(
		    "The mod doesn't have a valid controller.");
    }
}