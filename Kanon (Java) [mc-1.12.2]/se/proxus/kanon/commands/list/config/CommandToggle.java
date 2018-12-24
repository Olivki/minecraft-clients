package se.proxus.kanon.commands.list.config;

import se.proxus.kanon.Kanon;
import se.proxus.kanon.commands.Command;
import se.proxus.kanon.commands.CommandSignature;
import se.proxus.kanon.config.Configuration;
import se.proxus.kanon.config.Entry;
import se.proxus.kanon.modules.Module;
import se.proxus.kanon.utils.minecraft.client.Colourz;

@CommandSignature(author = "Oliver Berg", date = "2018/09/10 (17:12)")
public final class CommandToggle extends Command {

    public CommandToggle() {
        super("toggle");
    }

    @Override
    public void initialize() {
        registerArgument(new Argument(this) {
            @Override
            protected Argument initialize() {
                addCosmeticArgument("module");
                return this;
            }

            @Override
            @SuppressWarnings("ConstantConditions")
            public void onCommand(final String input, final String... args) {
                if (args.length <= 0)
                    return;

                if (Kanon.MODULES.contains(args[0])) {
                    final Module module = Kanon.MODULES.getByCommandName(args[0]);

                    if (module.isToggleable()) {
                        final Configuration config = module.getConfig();
                        final Entry entry = config.getEntry("State");

                        printValueUpdate(config, entry, config.toggle(entry));
                        module.checkState();
                    } else {
                        errorf("%sModule[%s%s%s] can't be toggled.", Colourz.GREY, Colourz.CLIENT_COLOUR,
                               module.getName(), Colourz.GREY);
                    }
                } else {
                    errorf("'%s%s%s' is not a known module.", Colourz.CLIENT_COLOUR, args[0], Colourz.GREY);
                }
            }
        });
    }

    @Override
    public String getDescription() {
        return "Toggles the state of the given module.";
    }

    private void printValueUpdate(final Configuration config, final Entry entry, final Object output) {
        printf("%s[%s%s%s] contains been set to [%s%s%s].", config.getName().replace(" ", ""),
               Colourz.CLIENT_COLOUR, entry.getKey(), Colourz.GREY, Colourz.CLIENT_COLOUR, output,
               Colourz.GREY);
    }
}