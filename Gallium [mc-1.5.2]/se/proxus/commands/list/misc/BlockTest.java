package se.proxus.commands.list.misc;

import se.proxus.Gallium;
import se.proxus.commands.Command;
import se.proxus.tools.Colours;
import se.proxus.tools.Tools;

public class BlockTest extends Command {

    public BlockTest(final Gallium client) {
	super("blocktest", ".blocktest [blockname]",
		"Gives you the ID for the block.", client);
    }

    @Override
    public void onCommand(final String message, final String... args) {
	getClient().getPlayer().addMessage(
		Colours.GREY + "Block ID: "
			+ Tools.getBlockByName(args[0]).blockID);
    }
}