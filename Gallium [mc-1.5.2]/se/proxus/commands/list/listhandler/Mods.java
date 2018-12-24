package se.proxus.commands.list.listhandler;

import se.proxus.Gallium;
import se.proxus.commands.Command;
import se.proxus.commands.CommandType;
import se.proxus.mods.Mod;
import se.proxus.tools.Colours;
import se.proxus.tools.StringTools;

public class Mods extends Command {

    public Mods(final Gallium client) {
	super("mods", ".mods <page>", "Gives you a list of mods.", client,
		CommandType.LISTHANDLER);
    }

    @Override
    public void onCommand(final String message, final String... args) {
	if (message.length() == getCommand().length()) {
	    int modsPerPage = 6;
	    Mod[] mods = getClient().getMods().getRegisteredMods();
	    getClient().getMods().sortMods(mods);
	    int pages = mods.length / modsPerPage
		    + (mods.length % modsPerPage > 0 ? 1 : 0);
	    getClient().getPlayer().addMessage(
		    Colours.CLIENT_COLOUR + "------------[" + Colours.GREY
			    + "Mods (1" + "/" + pages + ")"
			    + Colours.CLIENT_COLOUR + "]------------");
	    for (int i = 0; i < modsPerPage; i++) {
		if (i + 1 > mods.length)
		    break;
		getClient().getPlayer().addMessage(
			Colours.CLIENT_COLOUR + mods[i].getName() + ": "
				+ Colours.GREY + mods[i].getDescription());
	    }
	} else if (message.length() > getCommand().length())
	    if (StringTools.isInteger(args[0])) {
		int page = Integer.parseInt(args[0]);
		int modsPerPage = 6;
		Mod[] mods = getClient().getMods().getRegisteredMods();
		getClient().getMods().sortMods(mods);
		int pages = mods.length / modsPerPage
			+ (mods.length % modsPerPage > 0 ? 1 : 0);
		if (page < 1 || page > pages) {
		    getClient().getPlayer().addMessage(
			    Colours.GREY + "Page not found.");
		    return;
		}
		getClient().getPlayer().addMessage(
			Colours.CLIENT_COLOUR + "------------[" + Colours.GREY
				+ "Mods (" + page + "/" + pages + ")"
				+ Colours.CLIENT_COLOUR + "]------------");
		for (int i = 0; i < modsPerPage; i++) {
		    if (i + (page - 1) * modsPerPage + 1 > mods.length)
			break;
		    getClient().getPlayer().addMessage(
			    Colours.CLIENT_COLOUR
				    + mods[i + (page - 1) * modsPerPage]
					    .getName()
				    + ": "
				    + Colours.GREY
				    + mods[i + (page - 1) * modsPerPage]
					    .getDescription());
		}
	    }
    }
}