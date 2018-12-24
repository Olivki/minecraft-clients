package se.proxus.rori.commands.list.listhandler;

import se.proxus.rori.commands.Command;
import se.proxus.rori.commands.CommandType;
import se.proxus.rori.mods.Mod;
import se.proxus.rori.mods.ModManager;
import se.proxus.rori.tools.Colours;
import se.proxus.rori.tools.StringTools;
import se.proxus.rori.tools.Tools;

public class Mods extends Command {

	public Mods() {
		super("mods", ".mods <page>", "Gives you a list of mods.", CommandType.LISTHANDLER);
	}

	@Override
	public void onCommand(final String message, final String... args) {
		ModManager modManager = ModManager.getInstance();

		if (message.length() == getCommand().length()) {
			int modsPerPage = 9;
			Mod[] mods = modManager.getMods().toArray(new Mod[modManager.getMods().size()]);
			modManager.sortMods(mods);
			int pages = mods.length / modsPerPage + (mods.length % modsPerPage > 0 ? 1 : 0);
			Tools.addChatMessage(Colours.CLIENT_COLOUR + "------------[" + Colours.GREY + "Mods (1"
					+ "/" + pages + ")" + Colours.CLIENT_COLOUR + "]------------");
			for (int i = 0; i < modsPerPage; i++) {
				if (i + 1 > mods.length) {
					break;
				}
				Tools.addChatMessage(Colours.CLIENT_COLOUR + mods[i].getName() + ": "
						+ Colours.GREY + mods[i].getDescription());
			}
		} else if (message.length() > getCommand().length()) {
			if (StringTools.isInteger(args[0])) {
				int page = Integer.parseInt(args[0]);
				int modsPerPage = 9;
				Mod[] mods = modManager.getMods().toArray(new Mod[modManager.getMods().size()]);
				modManager.sortMods(mods);
				int pages = mods.length / modsPerPage + (mods.length % modsPerPage > 0 ? 1 : 0);
				if (page < 1 || page > pages) {
					Tools.addChatMessage("Page not found.");
					return;
				}
				Tools.addChatMessage(Colours.CLIENT_COLOUR + "------------[" + Colours.GREY
						+ "Mods (" + page + "/" + pages + ")" + Colours.CLIENT_COLOUR
						+ "]------------");
				for (int i = 0; i < modsPerPage; i++) {
					if (i + (page - 1) * modsPerPage + 1 > mods.length) {
						break;
					}
					Tools.addChatMessage(Colours.CLIENT_COLOUR
							+ mods[i + (page - 1) * modsPerPage].getName() + ": " + Colours.GREY
							+ mods[i + (page - 1) * modsPerPage].getDescription());
				}
			}
		}
	}
}