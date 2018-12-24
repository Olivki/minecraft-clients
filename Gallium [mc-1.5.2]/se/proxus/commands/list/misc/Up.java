package se.proxus.commands.list.misc;

import se.proxus.Gallium;
import se.proxus.commands.Command;
import se.proxus.tools.Colours;

public class Up extends Command {

    public Up(final Gallium client) {
	super("up", ".up [blocks]",
		"Teleports you up the desired amount of blocks", client);
    }

    @Override
    public void onCommand(final String message, final String... args) {
	int amount = Integer.parseInt(args[0]);
	for (int blocks = 0; blocks < amount; blocks++)
	    getClient().getPlayer().setPosition(getClient().getPlayer().getX(),
		    getClient().getPlayer().getY() + blocks,
		    getClient().getPlayer().getZ());
	getClient().getPlayer().addMessage(
		Colours.GREY + "Tried to teleport you up "
			+ Colours.CLIENT_COLOUR + amount + Colours.GREY
			+ " blocks.");
    }
}