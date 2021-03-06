package se.proxus.owari.commands;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.network.play.client.C01PacketChatMessage;
import se.proxus.owari.Client;
import se.proxus.owari.events.EventHandler;
import se.proxus.owari.events.EventListener;
import se.proxus.owari.events.EventManager;
import se.proxus.owari.events.list.server.EventPacketSent;
import se.proxus.owari.tools.Colours;
import se.proxus.owari.tools.Tools;

public class CommandManager implements EventListener {

	private static final List<Command> LOADED_COMMANDS = new LinkedList<Command>();
	private static CommandManager instance;

	public void init() {
		EventManager.registerListener(this);
	}

	public void loadCommands() {
		/** Re-organizing the commands after the alphabet. **/
		getClient().getLogger().info("Re-organizing the commands after the alphabet.");
		List<Command> tempList = new LinkedList<Command>();
		tempList.addAll(getCommands());
		Command[] tempArray = tempList.toArray(new Command[tempList.size()]);
		sortCommands(tempArray);
		getCommands().clear();

		for (Command command : tempArray) {
			registerCommand(command);
		}

		tempArray = null;
		tempList.clear();
		tempList = null;
		System.gc();
	}

	@EventHandler
	public void onEventChatSent(final EventPacketSent event) {
		try {
			if (!(event.getPacket() instanceof C01PacketChatMessage)) {
				return;
			}
			C01PacketChatMessage packet = (C01PacketChatMessage) event.getPacket();
			String message = packet.getMessage();
			if (message.charAt(0) == '.' && !message.startsWith("..")) {
				event.setCancelled(true);
				String[] split;
				if (message.indexOf(' ') < 0) {
					split = new String[0];
				} else {
					split = message.substring(1).split(" ");
				}
				if (getCommandByName(message.indexOf(' ') < 0 ? message.substring(1) : split[0]) == null
						&& !message.equalsIgnoreCase(".")) {
					Tools.addChatMessage("Unknown command, do " + Colours.CLIENT_COLOUR + ".help"
							+ Colours.GREY + " for a list of all the available " + Colours.GREY
							+ "commands.");
				}
				for (Command command : getCommands()) {
					try {
						if (command.getCommand().equalsIgnoreCase(
								message.indexOf(' ') < 0 ? message.substring(1) : split[0])) {
							String[] args;
							if (message.indexOf(' ') < 0) {
								args = new String[0];
							} else {
								args = message.substring(1).substring(message.indexOf(' '))
										.split(" ");
							}
							command.onCommand(message.substring(1), args);
						}
					} catch (Exception exception) {
						Tools.addChatMessage(Colours.CLIENT_COLOUR + "Syntax: " + Colours.GREY
								+ command.getSyntax());
					}
				}
			} else if (message.startsWith("..")) {
				Field fieldMessage = Tools.getPrivateField(packet.getClass(), "message");
				String messageString = (String) fieldMessage.get(packet);
				messageString = message.substring(1);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public Command registerCommand(final Command command) {
		if (getCommandBySyntax(command.getSyntax()) == null) {
			getCommands().add(command);
			getClient().getLogger().info("Registered the command '" + command.getCommand() + "'");
		}
		return command;
	}

	public Command unregisterCommand(final Command command) {
		if (getCommandBySyntax(command.getSyntax()) != null) {
			getCommands().remove(command);
			getClient().getLogger().info("Unregistered the command '" + command.getCommand() + "'");
		}
		return command;
	}

	/**
	 * @author DarkStorm_
	 * @return
	 */
	public Command[] getRegisteredCommands() {
		return getCommands().toArray(new Command[getCommands().size()]);
	}

	/**
	 * @author DarkStorm_
	 * @param commands
	 * @return
	 */
	public Command[] sortCommands(final Command[] commands) {
		Arrays.sort(commands, new Comparator<Command>() {
			@Override
			public int compare(final Command command1, final Command command2) {
				String commandName1 = command1.getCommand();
				String commandName2 = command2.getCommand();
				return commandName1.compareTo(commandName2);
			}
		});
		return commands;
	}

	public Command getCommandByName(final String name) {
		Command tempCommand = null;
		for (Command command : getCommands()) {
			if (command.getCommand().equalsIgnoreCase(name)) {
				tempCommand = command;
				break;
			}
		}
		return tempCommand;
	}

	public Command getCommandBySyntax(final String syntax) {
		Command tempCommand = null;
		for (Command command : getCommands()) {
			if (command.getSyntax().equalsIgnoreCase(syntax)) {
				tempCommand = command;
				break;
			}
		}
		return tempCommand;
	}

	public List<Command> getCommands() {
		return LOADED_COMMANDS;
	}

	public Client getClient() {
		return Client.getInstance();
	}

	public static CommandManager getInstance() {
		if (instance == null) {
			instance = new CommandManager();
		}
		return instance;
	}
}