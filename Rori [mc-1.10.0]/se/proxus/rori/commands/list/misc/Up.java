package se.proxus.rori.commands.list.misc;

import se.proxus.rori.commands.Command;
import se.proxus.rori.tools.Colours;
import se.proxus.rori.tools.Location;
import se.proxus.rori.tools.StringTools;
import se.proxus.rori.tools.Tools;

public class Up extends Command {

	public Up() {
		super("up", ".up [blocks]", "Tries to teleport you up the desired amount of blocks.");
	}

	@Override
	public void onCommand(final String message, final String... args) {
		if (!StringTools.isInteger(args[0])) {
			Tools.addChatMessage("Please enter a integer.");
			return;
		}

		int amount = Integer.parseInt(args[0]);

		if (amount < 1) {
			Tools.addChatMessage("Please enter a number greater than " + Colours.CLIENT_COLOUR
					+ "0" + Colours.GREY + ".");
			Tools.addChatMessage(Colours.ITALIC
					+ "If you wanted to teleport down instead of up, use the "
					+ Colours.CLIENT_COLOUR + ".down " + Colours.GREY + "command.");
			return;
		}

		Location playerLocation = Tools.getPlayerLocation();
		playerLocation.setY(playerLocation.getY() + amount);
		playerLocation.locationToEntity(Tools.getPlayer());

		Tools.addChatMessage("Tried to teleport you up " + Colours.CLIENT_COLOUR + amount
				+ Colours.GREY + " blocks.");
	}
}