package se.proxus.kanon.commands.list.lists;

import org.apache.commons.lang3.text.WordUtils;
import se.proxus.kanon.Kanon;
import se.proxus.kanon.commands.Command;
import se.proxus.kanon.commands.CommandSignature;
import se.proxus.kanon.commands.CommandUtils;
import se.proxus.kanon.commands.IterableListCommand;
import se.proxus.kanon.utils.minecraft.client.Colourz;
import se.proxus.kanon.utils.lang.Stringz;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@CommandSignature(author = "Oliver Berg", date = "2018/09/10 (17:12)")
public final class CommandHelp extends IterableListCommand {

    private final List<String> entryList = new LinkedList<>();
    private String syntax = "";

    public CommandHelp() {
        super("help", "?");
    }

    @Override
    public void initialize() {
        super.initialize();

        registerArgument(new Argument(this) {
            @Override
            public void onCommand(final String input, final String... args) {
                if (args.length <= 0)
                    return;

                if (!isInteger(input,true, true)) {
                    if (Kanon.COMMANDS.contains(args[0])) {
                        final Command command = Kanon.COMMANDS.getCommand(args[0]);

                        CommandUtils.printHeadLine(getParent(),
                                                   WordUtils.capitalize(Objects.requireNonNull(command)
                                                                                .getName()));

                        printf("%s-%s %s",
                               Colourz.CLIENT_COLOUR,
                               Colourz.GREY,
                               command.getDescription());
                        printf("%s-%s %s",
                               Colourz.CLIENT_COLOUR,
                               Colourz.GREY,
                               command.getSyntax());

                        if (command.getAliases().length > 1) {
                            printf("%s-%s [%s]",
                                   Colourz.CLIENT_COLOUR,
                                   Colourz.GREY,
                                   Stringz.arrayToString(command.getAliases(), ", "));
                        }
                    } else {
                        errorf("Command[%s] does not exist.", args[0]);
                    }
                }
            }
        });
    }

    @Override
    public String getSyntax() {
        return syntax;
    }

    @Override
    public String getDescription() {
        return "Displays a events of all available commands, and lets you get more information about specific" +
                " commands.";
    }

    @Override
    public String getListName() {
        return "Help";
    }

    @Override
    public String[] getEntries() {
        return entryList.toArray(new String[0]);
    }

    @Override
    public int getEntriesPerPage() {
        return 8;
    }

    @Override
    public int getMaxPages() {
        final int entriesLength = getEntries().length;
        return entriesLength / getEntriesPerPage() + (entriesLength % getEntriesPerPage() > 0 ? 1 : 0);
    }

    @Override
    public void populateEntries() {
        entryList.clear();

        for (final Command command : Kanon.COMMANDS.getCommands()) {
            if (command instanceof IterableListCommand && !(command.equals(this))) {
                ((IterableListCommand) command).populateEntries();
            }
            
            final String entry = String.format("%s%s: %s%s",
                                               Colourz.CLIENT_COLOUR,
                                               command.getName(),
                                               Colourz.GREY,
                                               command.getSyntax());
            entryList.add(entry);
        }

        if (getMaxPages() > 1) {
            syntax = getDefaultSyntax() + String.format(" {(%s)/command}",
                                                        "1-" + getMaxPages());
        } else {
            syntax = getDefaultSyntax() + " {command}";
        }
    }
}