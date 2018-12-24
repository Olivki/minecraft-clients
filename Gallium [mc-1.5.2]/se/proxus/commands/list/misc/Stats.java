package se.proxus.commands.list.misc;

import java.util.ArrayList;

import net.minecraft.src.EntityPlayer;
import se.proxus.Gallium;
import se.proxus.commands.Command;

public class Stats extends Command {

    /**
     * 
     * TODO: Finish up this command.
     * 
     **/

    public Stats(final Gallium client) {
	super("stats", ".stats <username>",
		"Gives the stats of yourself or a user.", client);
    }

    @Override
    public void onCommand(final String message, final String... args) {
	if (message.length() == getCommand().length()) {

	} else {

	}
    }

    private ArrayList<String> getStats(final EntityPlayer player) {
	final ArrayList<String> stats = new ArrayList<String>();
	stats.add(player.username + "'s Stats");
	stats.add("Health: " + player.getHealth());
	// stats.add("Armour: " + player.);
	return stats;
    }
}