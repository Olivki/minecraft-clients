package se.proxus.kanon.commands.list.config;

import org.lwjgl.input.Keyboard;
import se.proxus.kanon.Kanon;
import se.proxus.kanon.commands.Command;
import se.proxus.kanon.commands.CommandSignature;
import se.proxus.kanon.config.Configuration;
import se.proxus.kanon.config.Entry;
import se.proxus.kanon.modules.Module;
import se.proxus.kanon.utils.minecraft.client.Colourz;

@CommandSignature(author = "Oliver Berg", date = "2018/09/10 (17:12)")
public final class CommandKeybind extends Command {

    public CommandKeybind() {
        super("keybind", "keybinds", "key", "keys");
    }

    @Override
    public void initialize() {
        registerArgument(new Argument(this, "set") {
            @Override
            protected Argument initialize() {
                addCosmeticArgument("module");
                addCosmeticArgument("key");
                return this;
            }

            @Override
            public void onCommand(final String input, final String... args) {
                if (args.length <= 0)
                    return;
                

                if (Kanon.MODULES.contains(args[0])) {
                    final Module module = Kanon.MODULES.getByCommandName(args[0]);

                    final Configuration config = module.getConfig();
                    final Entry entry = config.getEntry("Keybind");

                    final int keyIndex = Keyboard.getKeyIndex(args[1]);

                    if (keyIndex <= 0) {
                        errorf("[%s%s%s] is not a valid key!", Colourz.CLIENT_COLOUR, args[1].toUpperCase(),
                               Colourz.GREY);
                        return;
                    }

                    printValueUpdate(config, entry, args[1].toUpperCase());
                    config.setValue(entry.getKey(), args[1].toUpperCase());
                } else {
                    errorf("'%s%s%s' is not a known module.", Colourz.CLIENT_COLOUR, args[0], Colourz.GREY);
                }
            }
        });

        registerArgument(new Argument(this, "null") {
            @Override
            protected Argument initialize() {
                addCosmeticArgument("module");
                return this;
            }

            @Override
            public void onCommand(final String input, final String... args) {
                if (args.length <= 0)
                    return;

                if (Kanon.MODULES.contains(args[0])) {
                    final Module module = Kanon.MODULES.getByCommandName(args[0]);

                    final Configuration config = module.getConfig();
                    final Entry entry = config.getEntry("Keybind");

                    printValueUpdate(config, entry, "NONE");
                    config.setValue(entry.getKey(), "NONE");
                } else {
                    errorf("'%s%s%s' is not a known module.", Colourz.CLIENT_COLOUR, args[0], Colourz.GREY);
                }
            }
        });
    }

    @Override
    public String getDescription() {
        return "Sets or nulls the keybind for the given module.";
    }

    private void printValueUpdate(final Configuration config, final Entry entry,
            final Object output) {
        printf("%s[%s%s%s] contains been set to [%s%s%s].", config.getName().replace(" ", ""),
               Colourz.CLIENT_COLOUR, entry.getKey(), Colourz.GREY, Colourz.CLIENT_COLOUR, output,
               Colourz.GREY);
    }
}