package se.proxus.kanon.commands;

import se.proxus.kanon.Kanon;
import se.proxus.kanon.event4j.EventPriority;
import se.proxus.kanon.event4j.EventSubscribe;
import se.proxus.kanon.event4j.events.client.EventClientReload;
import se.proxus.kanon.event4j.events.client.init.EventPreInitialization;
import se.proxus.kanon.utils.lang.Stringz;
import se.proxus.kanon.utils.system.Filez;
import se.proxus.kanon.utils.system.Reflectionz;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public final class CommandFactory {

    private static final Map<String, Command> LOADED_COMMANDS = new LinkedHashMap<>();
    public final static File DIRECTORY = Filez.getDirectory(Kanon.DIRECTORY, "commands");

    @EventSubscribe(priority = EventPriority.HIGHEST)
    public final void onPreInitialization(final EventPreInitialization event) {
        Kanon.LOGGER.info("Scanning for commands..");

        try {
            scanForCommands();
        } catch (final IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    
        organizeCommands();
    }

    @EventSubscribe(priority = EventPriority.HIGHEST)
    public final void onClientReload(final EventClientReload event) {
        LOADED_COMMANDS.clear();
        onPreInitialization(null);
    }

    private void scanForCommands()
    throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        final String packageName = getClass().getPackage().getName() + ".events";

        for (final Class<?> commandClass : Reflectionz.getClasses(packageName)) {
            if (!Command.class.isAssignableFrom(commandClass)) {
                continue;
            }
    
            final Command command = (Command) commandClass.newInstance();

            registerCommand(command, true);
        }
    }

    public void organizeCommands() {
        final Map<String, Command> tempMap = new LinkedHashMap<>(LOADED_COMMANDS);
        final Command[] tempArray = tempMap.values().toArray(new Command[0]);
        sortCommands(tempArray);
        LOADED_COMMANDS.clear();

        for (final Command command : tempArray) {
            registerCommand(command, false);
        }

        tempMap.clear();
        System.gc();
    }

    public final Command registerCommand(String name, final String description, final boolean shouldLog) {
        name = Stringz.toCamelCase(name.trim(), false);
    
        final lombok.val command = new Command(name) {
            @Override
            public void initialize() {}

            @Override
            public String getDescription() {
                return description;
            }
        };

        return registerCommand(command, shouldLog);
    }

    public final <T extends Command> T registerCommand(final T command, final boolean shouldLog) {
        if (!LOADED_COMMANDS.containsKey(command.getName())) {
            if (shouldLog) {
                Kanon.LOGGER.info(command.toString().replace("Command[",
                                                            "CommandRegister["));
            }

            LOADED_COMMANDS.put(command.getName(), command);
            
            return command;
        } else {
            throw new NullPointerException(String.format("Command[%s] is already registered.",
                                                         command.getName()));
        }
    }

    public final Command unregisterCommand(final Command command, final boolean shouldLog) {
        return unregisterCommand(command.getName(), shouldLog);
    }

    public final Command unregisterCommand(final String name, final boolean shouldLog) {
        if (LOADED_COMMANDS.containsKey(name)) {
            final lombok.val command = LOADED_COMMANDS.get(name);
            
            if (shouldLog) {
                Kanon.LOGGER.info(command.toString().replace("Command[", "CommandUnregister["));
            }

            LOADED_COMMANDS.remove(name);
            
            return command;
        } else {
            throw new NullPointerException(String.format("Command[%s] does not exist.",
                                                         name));
        }
    }

    public final Command[] getCommands() {
        return LOADED_COMMANDS.values().toArray(new Command[0]);
    }

    private Command[] sortCommands(final Command... commands) {
        Arrays.sort(commands, (command1, command2) -> {
            final String commandName1 = command1.getName();
            final String commandName2 = command2.getName();
            return commandName1.compareTo(commandName2);
        });

        return commands;
    }

    public final Command getCommand(final String name) {
        return getCommand(name, Command.class);
    }
    
    public final <K extends Command> K getCommand(final String name, final Class<K> cls) {
        final Command matchCommand = LOADED_COMMANDS.get(name);
        
        if (matchCommand == null) {
            for (final Command command : getCommands()) {
                if (command.nameMatch(name)) {
                    return (K) command;
                }
            }
            
            return null;
        } else {
            return (K) matchCommand;
        }
    }

    public final boolean contains(final String command) {
        return getCommand(command) != null;
    }

    private Kanon getKanon() {
        return Kanon.getInstance();
    }
}