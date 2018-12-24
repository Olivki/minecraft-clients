package se.proxus.kanon.commands.list.misc;

import se.proxus.kanon.commands.Command;
import se.proxus.kanon.commands.CommandSignature;
import se.proxus.kanon.event4j.EventWrapper;
import se.proxus.kanon.event4j.events.client.EventClientReload;

import javax.annotation.Nullable;

@CommandSignature(author = "Oliver Berg", date = "2018/09/10 (17:12)")
public final class CommandReload extends Command {

    public CommandReload() {
        super("reload");
    }

    @Override
    public void initialize() {
        registerArgument(new Argument(this) {
            @Override
            public void onCommand(final String input, @Nullable final String... args) {
                print("Trying to reload client system..");
                EventWrapper.fire(new EventClientReload());
            }
        });
    }

    @Override
    public String getDescription() {
        return "Reloads various parts of the system, only to be used for debug.";
    }
}