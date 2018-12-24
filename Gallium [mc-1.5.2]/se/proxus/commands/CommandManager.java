package se.proxus.commands;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import net.minecraft.src.Packet3Chat;
import se.proxus.Gallium;
import se.proxus.commands.list.listhandler.Help;
import se.proxus.commands.list.listhandler.Mods;
import se.proxus.commands.list.misc.BlockTest;
import se.proxus.commands.list.misc.Down;
import se.proxus.commands.list.misc.Echo;
import se.proxus.commands.list.misc.FillInventory;
import se.proxus.commands.list.misc.Reload;
import se.proxus.commands.list.misc.Say;
import se.proxus.commands.list.misc.Stats;
import se.proxus.commands.list.misc.Up;
import se.proxus.commands.list.setting.BindAdd;
import se.proxus.commands.list.setting.BindRemove;
import se.proxus.commands.list.setting.Toggle;
import se.proxus.events.EventHandler;
import se.proxus.events.EventListener;
import se.proxus.events.list.server.EventPacketSent;
import se.proxus.tools.Colours;

public class CommandManager implements EventListener {

    private static final List<Command> LOADED_COMMANDS = new LinkedList<Command>();
    private Gallium client;

    public CommandManager(final Gallium client) {
	setClient(client);
    }

    public void init() {
	getLoadedCommands().clear();
	getClient().getEvents().registerListener(this);
	registerCommand(new Say(getClient()));
	registerCommand(new Echo(getClient()));
	registerCommand(new BindAdd(getClient()));
	registerCommand(new BindRemove(getClient()));
	registerCommand(new Toggle(getClient()));
	registerCommand(new Reload(getClient()));
	registerCommand(new Up(getClient()));
	registerCommand(new Down(getClient()));
	registerCommand(new FillInventory(getClient()));
	registerCommand(new Help(getClient()));
	registerCommand(new Mods(getClient()));
	registerCommand(new Stats(getClient()));
	registerCommand(new BlockTest(getClient()));
    }

    @EventHandler
    public void onEventChatSent(final EventPacketSent event) {
	try {
	    if (!(event.getPacket() instanceof Packet3Chat))
		return;
	    Packet3Chat packet = (Packet3Chat) event.getPacket();
	    String message = packet.message;
	    if (message.charAt(0) == '.' && !message.startsWith("..")) {
		event.setCancelled(true);
		String[] split;
		if (message.indexOf(' ') < 0)
		    split = new String[0];
		else
		    split = message.substring(1).split(" ");
		for (Command command : getLoadedCommands())
		    try {
			if (command.getCommand().equalsIgnoreCase(
				message.indexOf(' ') < 0 ? message.substring(1)
					: split[0])) {
			    String[] args;
			    if (message.indexOf(' ') < 0)
				args = new String[0];
			    else
				args = message.substring(1)
					.substring(message.indexOf(' '))
					.split(" ");
			    command.onCommand(message.substring(1), args);
			}
		    } catch (Exception exception) {
			exception.printStackTrace();
			getClient().getPlayer().addMessage(
				Colours.CLIENT_COLOUR + "Usage: "
					+ Colours.GREY + command.getUsage());
		    }
	    } else if (message.startsWith(".."))
		packet.message = message.substring(1);
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
    }

    public Command registerCommand(final Command command) {
	if (!getLoadedCommands().contains(command)) {
	    getLoadedCommands().add(command);
	    getClient().getLogger().log(Level.INFO,
		    "Registered the command '" + command.getCommand() + "'");
	}
	return command;
    }

    /**
     * @author DarkStorm_
     * @return
     */
    public Command[] getRegisteredCommands() {
	return getLoadedCommands().toArray(
		new Command[getLoadedCommands().size()]);
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
	for (Command command : getLoadedCommands())
	    if (command.getCommand().equalsIgnoreCase(name))
		return command;
	return null;
    }

    public static List<Command> getLoadedCommands() {
	return LOADED_COMMANDS;
    }

    public Gallium getClient() {
	return client;
    }

    public void setClient(final Gallium client) {
	this.client = client;
    }
}