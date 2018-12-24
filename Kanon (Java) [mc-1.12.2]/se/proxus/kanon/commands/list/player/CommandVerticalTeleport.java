package se.proxus.kanon.commands.list.player;

import se.proxus.kanon.commands.Command;
import se.proxus.kanon.commands.CommandSignature;
import se.proxus.kanon.utils.minecraft.client.Colourz;
import se.proxus.kanon.utils.templates.Location;
import se.proxus.kanon.wrapper.minecraft.Player;

@CommandSignature(author = "Oliver Berg", date = "2018/09/10 (17:13)")
public final class CommandVerticalTeleport extends Command {

    public CommandVerticalTeleport() {
        super("vtp", "verticaltp", "verticalteleport", "vteleport");
    }

    @Override
    public void initialize() {
        registerArgument(new Argument(this, "up") {
            @Override
            public void onCommand(final String input, final String[] args) {
                if (isInteger(args[0])) {
                    performTeleport(Integer.parseInt(args[0]), (byte) 0);
                }
            }
        });

        registerArgument(new Argument(this, "down") {
            @Override
            public void onCommand(final String input, final String[] args) {
                if (isInteger(args[0])) {
                    performTeleport(Integer.parseInt(args[0]), (byte) 1);
                }
            }
        });
    }

    @Override
    public String getSyntax() {
        return getDefaultSyntax() + " [(1-10)]";
    }

    @Override
    public String getDescription() {
        return "Allows you to teleport vertically.";
    }

    private void performTeleport(final int distance, final byte type) {
        if (isInRange(distance, 1, 10)) {
            final Location location = Player.getLocation();

            switch (type) {
                case 0:
                    location.increaseY(distance);
                    printf("Tried to teleport you up %s%s%s blocks.",
                           Colourz.CLIENT_COLOUR,
                           distance,
                           Colourz.GREY);
                    break;

                case 1:
                    location.decreaseY(distance);
                    printf("Tried to teleport you down %s%s%s blocks.",
                           Colourz.CLIENT_COLOUR,
                           distance,
                           Colourz.GREY);
                    break;

                default:
                    error("Unknown type [" + type + "].");
                    return;
            }

            location.locationToPlayer();
        }
    }
}