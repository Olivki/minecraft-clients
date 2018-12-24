package se.proxus.kanon.commands.list.lists;

import org.apache.commons.lang3.text.WordUtils;
import se.proxus.kanon.Kanon;
import se.proxus.kanon.commands.CommandSignature;
import se.proxus.kanon.commands.CommandUtils;
import se.proxus.kanon.commands.IterableListCommand;
import se.proxus.kanon.config.Entry;
import se.proxus.kanon.modules.Module;
import se.proxus.kanon.utils.minecraft.client.Colourz;
import se.proxus.kanon.utils.lang.Stringz;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@CommandSignature(author = "Oliver Berg", date = "2018/09/10 (17:07)")
public final class CommandModules extends IterableListCommand {

    private final List<String> entryList = new LinkedList<>();

    public CommandModules() {
        super("modules", "mods", "hacks");
    }

    @Override
    public void initialize() {
        registerArgument(new Argument(this, "events") {
            @Override
            public void onCommand(final String input, final String[] args) {
                if (args.length > 0)
                    return;

                displayPage(1);
            }
        });

        registerArgument(new Argument(this, "events") {
            @Override
            public void onCommand(final String input, final String[] args) {
                if (getMaxPages() > 1) {
                    addCosmeticArgument("(1-" + getMaxPages() + ")");
                }

                if (args.length <= 0)
                    return;

                if (!isInteger(args[0], true))
                    return;

                displayPage(Integer.parseInt(args[0]));
            }
        });

        registerArgument(new Argument(this, "get") {
            @Override
            protected Argument initialize() {
                addCosmeticArgument("module");
                return this;
            }

            @Override
            public void onCommand(final String input, final String... args) {
                if (args.length <= 0) {
                    throwSyntaxError();
                    return;
                }

                if (!isInteger(args[0], true, true)) {
                    if (Kanon.MODULES.contains(args[0])) {
                        populateEntries();
                        final Module module = Kanon.MODULES.getByCommandName(args[0]);

                        CommandUtils.printHeadLine(getParent(), WordUtils.capitalize(module.getName()) + " Dump");
                        
                        for (final Entry entry : module.getConfig().getEntries()) {
                            printf("%s%s%s",
                                   Colourz.CLIENT_COLOUR,
                                   entry.getKey(),
                                   Colourz.GREY);
                            printf("  %s-%s %s",
                                   Colourz.CLIENT_COLOUR,
                                   Colourz.GREY,
                                   entry.getDescription());
                            
                            if (entry.getValue().getClass().isArray()) {
                                if (Objects.nonNull(entry.getRange())) {
                                    printf("  %s-%s [%s] (%s, %s)",
                                           Colourz.CLIENT_COLOUR,
                                           Colourz.GREY,
                                           Stringz.arrayToStringFancy(entry.getArray(), ", "),
                                           entry.getMin(),
                                           entry.getMax());
                                } else {
                                    printf("  %s-%s [%s]",
                                           Colourz.CLIENT_COLOUR,
                                           Colourz.GREY,
                                           Stringz.arrayToStringFancy(entry.getArray(), ", "));
                                }
                            } else {
                                if (Objects.nonNull(entry.getRange())) {
                                    printf("  %s-%s %s (%s, %s)",
                                           Colourz.CLIENT_COLOUR,
                                           Colourz.GREY,
                                           entry.getValue(),
                                           entry.getMin(),
                                           entry.getMax());
                                } else {
                                    printf("  %s-%s %s",
                                           Colourz.CLIENT_COLOUR,
                                           Colourz.GREY,
                                           entry.getValue());
                                }
                            }
                        }
                    } else {
                        errorf("Module[%s] does not exist.", args[0]);
                    }
                }
            }
        });
    }

    @Override
    public String getSyntax() {
        return getDefaultSyntax();
    }

    @Override
    public String getDescription() {
        return "Displays a events of all available modules, and let's you configure some things about them.";
    }

    @Override
    public String getListName() {
        return "Modules";
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
    public void populateEntries() {
        entryList.clear();

        for (final Module module : Kanon.MODULES.getModules()) {
            final String entry = String.format("%s%s: %s%s",
                                               Colourz.CLIENT_COLOUR,
                                               module.getName(),
                                               Colourz.GREY,
                                               module.getDescription());
            entryList.add(entry);
        }
    }
}