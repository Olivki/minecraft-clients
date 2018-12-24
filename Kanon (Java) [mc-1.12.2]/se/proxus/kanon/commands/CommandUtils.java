package se.proxus.kanon.commands;

import se.proxus.kanon.utils.minecraft.client.Colourz;

public final class CommandUtils {

    public static void printPageHeadLine(final Command command, final String name, final int page,
            final int pages) {
        printHeadLine(command, String.format("%s (%s/%s)", name, page, pages));
    }

    public static void printHeadLine(final Command command, final String name) {
        command.printf("%s%s[%s%s%s]%s", Colourz.CLIENT_COLOUR, CommandConstants.LINE, Colourz.GREY, name,
                       Colourz.CLIENT_COLOUR, CommandConstants.LINE);
    }
    
}