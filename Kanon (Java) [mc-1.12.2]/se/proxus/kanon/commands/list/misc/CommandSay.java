package se.proxus.kanon.commands.list.misc;

import se.proxus.kanon.commands.Command;
import se.proxus.kanon.commands.CommandSignature;

import javax.annotation.Nullable;

@CommandSignature(author = "Oliver Berg", date = "2018/09/10 (17:13)")
public final class CommandSay extends Command {
    
    public CommandSay() {
        super("say");
    }

    @Override
    public void initialize() {
        registerArgument(new Argument(this) {
            @Override
            protected Argument initialize() {
                addCosmeticArgument("\"message\"");
                return this;
            }

            @Override
            public void onCommand(final String input, @Nullable final String... args) {
                if (args.length > 0) {
                    send((input.charAt(0) == '.' ? "." : "") + input);
                } else {
                    error("Can't send an empty message!");
                }
            }
        });
    }

    @Override
    public String getDescription() {
        return "Sends the given input to the chat publicly.";
    }
}