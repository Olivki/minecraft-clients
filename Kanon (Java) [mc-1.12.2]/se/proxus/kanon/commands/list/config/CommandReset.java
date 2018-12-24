package se.proxus.kanon.commands.list.config;

import se.proxus.kanon.Kanon;
import se.proxus.kanon.commands.Command;
import se.proxus.kanon.commands.CommandSignature;
import se.proxus.kanon.config.Configuration;
import se.proxus.kanon.config.Entry;
import se.proxus.kanon.utils.minecraft.client.Colourz;
import se.proxus.kanon.utils.lang.Stringz;

// Shelved for now.
@CommandSignature(author = "Oliver Berg", date = "2018/09/12 (16:56)")
public final class CommandReset extends Command {

    public CommandReset() {
        super("reset");
    }

    @Override
    public void initialize() {
        registerArgument(new Argument(this, "all") {
            @Override
            public void onCommand(final String input, final String[] args) {
                if (args.length > 0)
                    return;
            
                printf("%s%s%sThis will reset the entries for !!ALL!! configs",
                       Colourz.RED,
                       Colourz.BOLD,
                       Colourz.UNDERLINE,
                       Colourz.RESET);
                printf("If you want to continue, type in \"%s.reset all y%s\", otherwise nothing will happen.",
                       Colourz.CLIENT_COLOUR,
                       Colourz.GREY);
            }
        });
    
        registerArgument(new Argument(this, "all", "y") {
            @Override
            public void onCommand(final String input, final String[] args) {
                if (args.length <= 0)
                    return;
            
                for (final Configuration config : Kanon.CONFIGS.getConfigs()) {
                    for (final Entry entry : config.getEntries()) {
                        config.setValue(entry, entry.getDefaultValue(), true);
                        printcf("%s[%s] contains been reset.", config.getName().replace(" ", ""));
                    }
                    
                    printcf("Config[%s] contains been reset.",config.getName().replace(" ", ""));
                }
            }
        }).setVisible(false);
    }
    
    @Override
    public String getDescription() {
        return "Resets all entries for a specific parent, or does it for all of them.";
    }
    
    public void printValueUpdate(final Configuration config, final Entry entry, final Object output) {
        if (output.getClass().isArray()) {
            final Object[] array = entry.getArray();
            
            printf("%s[%s%s%s] contains been set to: [%s]",
                   config.getName().replace(" ", ""),
                   Colourz.CLIENT_COLOUR,
                   entry.getKey(),
                   Colourz.GREY,
                   Stringz.arrayToStringFancy(array, ", "));
        } else {
            printf("%s[%s%s%s] contains been set to [%s%s%s].",
                   config.getName().replace(" ", ""),
                   Colourz.CLIENT_COLOUR,
                   entry.getKey(),
                   Colourz.GREY,
                   Colourz.CLIENT_COLOUR,
                   output,
                   Colourz.GREY);
        }
    }
}