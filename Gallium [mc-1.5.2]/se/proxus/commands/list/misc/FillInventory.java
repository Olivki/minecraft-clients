package se.proxus.commands.list.misc;

import se.proxus.Gallium;
import se.proxus.commands.Command;

public class FillInventory extends Command {

    public FillInventory(final Gallium client) {
	super("fillinventory", ".fillinventory [id] [size]",
		"Fills up your inventory with the given ID.", client);
    }

    @Override
    public void onCommand(final String message, final String... args) {
	int id = Integer.parseInt(args[0]);
	int size = Integer.parseInt(args[1]);
	for (int inventorySize = 44; inventorySize >= 9; inventorySize--)
	    getClient().getPlayer().sendMessage(
		    "/give " + getClient().getMinecraft().session.username
			    + " " + id + " " + size);
    }
}