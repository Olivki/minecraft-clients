package se.proxus.kanon.commands;

import net.minecraft.network.play.client.CPacketChatMessage;
import se.proxus.kanon.Kanon;
import se.proxus.kanon.event4j.EventSubscribe;
import se.proxus.kanon.event4j.events.server.EventPacketSent;
import se.proxus.kanon.utils.minecraft.client.Colourz;
import se.proxus.kanon.utils.system.Reflectionz;
import se.proxus.kanon.wrapper.minecraft.Player;

import java.lang.reflect.Field;

public final class CommandHandler {

    @EventSubscribe
    public final void onEventChatSent(final EventPacketSent event) {
        try {
            if (!(event.getPacket() instanceof CPacketChatMessage)) {
                return;
            }

            final CPacketChatMessage packet = (CPacketChatMessage) event.getPacket();

            final Field fieldMessage = Reflectionz.getNonAccessibleField(packet.getClass(), "message");
            final String message = (String) fieldMessage.get(packet);

            if (message.charAt(0) == '.' && !message.startsWith("..")) {
                event.setCancelled(true);
                final String[] split;

                if (message.indexOf(' ') < 0) {
                    split = new String[0];
                } else {
                    split = message.substring(1).split(" ");
                }

                if (Kanon.COMMANDS.getCommand(message.indexOf(' ') < 0
                                              ? message.substring(1)
                                              : split[0]) == null
                    && !message.equalsIgnoreCase(".")) {
                    addMessage("Unknown command, do " + Colourz.CLIENT_COLOUR + ".help" + Colourz.GREY +
                               " for a events of all the available " + Colourz.GREY + "commands.");
                }

                for (final Command command : Kanon.COMMANDS.getCommands()) {
                    try {
                        if (command.nameMatch(message.indexOf(' ') < 0 ? message.substring(1) : split[0])) {
                            final String[] args;

                            if (message.indexOf(' ') < 0) {
                                args = new String[0];
                            } else {
                                args = message.substring(1).substring(message.indexOf(' ')).split(" ");
                            }

                            if (command.hasMatch(args)) {
                                command.parseCommand(message.substring(1), args, Command.Sender.CHAT);
                            }
                        }
                    } catch (final ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException exception) {
                        command.throwSyntaxError();
                    } catch (final Exception exception) {
                        exception.printStackTrace();
                    }
                }
            } else if (message.startsWith("..")) {
                fieldMessage.set(packet, message.substring(1));
            }
        } catch (final Exception exception) {
            exception.printStackTrace();
        }
    }

    public void addMessage(final String message) {
        Player.addMessage(message);
    }

    public Kanon getClient() {
        return Kanon.getInstance();
    }

}
