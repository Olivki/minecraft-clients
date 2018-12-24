package se.proxus.rori.commands.list.misc;

import se.proxus.rori.commands.Command;
import se.proxus.rori.tools.Colours;
import se.proxus.rori.tools.Location;
import se.proxus.rori.tools.StringTools;
import se.proxus.rori.tools.Tools;

public class Down extends Command {

	public Down() {
		super("down", ".down [blocks]", "Tries to teleport you down the desired amount of blocks.");
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
					+ "If you wanted to teleport up instead of down, use the "
					+ Colours.CLIENT_COLOUR + ".up " + Colours.GREY + "command.");
			return;
		}

		Location playerLocation = Tools.getPlayerLocation();
		playerLocation.setY(playerLocation.getY() - amount);
		playerLocation.locationToEntity(Tools.getPlayer());

		Tools.addChatMessage("Tried to teleport you down " + Colours.CLIENT_COLOUR + amount
				+ Colours.GREY + " blocks.");
	}
}