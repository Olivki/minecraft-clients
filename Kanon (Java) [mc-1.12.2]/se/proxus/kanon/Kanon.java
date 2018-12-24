package se.proxus.kanon;

import se.proxus.kanon.commands.CommandFactory;
import se.proxus.kanon.config.ConfigurationFactory;
import se.proxus.kanon.event4j.EventPriority;
import se.proxus.kanon.event4j.EventSubscribe;
import se.proxus.kanon.event4j.EventWrapper;
import se.proxus.kanon.event4j.events.client.init.EventPostGameStart;
import se.proxus.kanon.event4j.events.client.init.EventPostInitialization;
import se.proxus.kanon.event4j.events.client.init.EventPreGameStart;
import se.proxus.kanon.event4j.events.client.init.EventPreInitialization;
import se.proxus.kanon.gui.frames.FramesManager;
import se.proxus.kanon.gui.frames.FramesScreen;
import se.proxus.kanon.keybinds.KeybindFactory;
import se.proxus.kanon.modules.ModuleFactory;
import se.proxus.kanon.utils.system.Filez;
import se.proxus.kanon.utils.system.Reflectionz;
import se.proxus.kanon.wrapper.LoggerWrapper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Random;

public final class Kanon {
    
    /**
     * CREDITS:
     *  - Techcable
     *      - For the Event4J system. (https://github.com/Techcable/Event4J)
     *  - Wurst Client
     *      - For a lot of math related code.
     *  - DarkStorm
     *      - For various things.
     */
    
    /**
     * CHANGELOG:
     *  - 0.0 - 0.3
     *      - Literally everything.
     *  - 0.35
     *      - Remade how the Entries in the configuration system works. (Adding entries now looks cleaner and makes
     *          more sense. (I hope at least.))
     *      - Reimplemented the ASM based way of invoking the event4j for the event system. (It's supposedly a lot
     *          faster than just pure Java reflection.)
     *      - Removed Manifold Systems because it was just throwing errors all over the place.
     *          Might be useful when the plugin gets out of early alpha and it's more stable.
     *      - Changed the class name of "EventHandler" to "EventSubscribe".
     */
    
    /**
     * TODO:
     *  - Move the commands and modules out of the parent "list" package, because that's not proper Java conventions.
     *  - Make something similar to the PrintStream class (System.out) to reduce boilerplate for printing.
     *  - Make a standalone keybind (macros) system. (Have the modules adapt it.)
     *      - Implement macros for sending chat messages. (Could be used for sending multiple client & server commands.)
     *  - Make the new frames system.
     *      - The saved files will be similar to how HTML looks, and saved in XML.
     *      - The themes will be using locally saved files that will imitate the syntax of CSS.
     *      - You'll be able to resize the components and create custom frames on the fly.
     *      - Implement some sort of layout manager.
     *  - Implement JAR loading for external modules.
     *  - Implement own scripting language?
     *      - Or maybe just implement some library for JavaScript or Lua?
     *  - Make chat server and client. (Probably based on IRC or something simple.)
     *  - Module Ideas
     *      - Chest buttons (Store all, Steal all, etc).
     */

    /** We back once more, with a weeb name again. * */
    private static Kanon instance;
    private final static Random RNG = new Random();
    
    public final static LoggerWrapper LOGGER = LoggerWrapper.getInstance(getName());
    public final static File DIRECTORY = Filez.getAppDir("." + getName().toLowerCase());
    public final static ConfigurationFactory CONFIGS = new ConfigurationFactory();
    public final static ModuleFactory MODULES = new ModuleFactory();
    public final static CommandFactory COMMANDS = new CommandFactory();
    public final static KeybindFactory KEYBINDS = new KeybindFactory();
    public final static FramesScreen FRAMES_SCREEN = new FramesScreen();

    /**
     * Runs the method scanForListeners at the start of the game.
     *
     * @return The getInstance() method. (This is a singleton.)
     */
    public final Kanon initialize() {
        try {
            LOGGER.info("Scanning for event listeners..");
            scanForListeners();
        } catch (final ClassNotFoundException | IOException | IllegalAccessException |
                InstantiationException e) {
            e.printStackTrace();
        }

        return getInstance();
    }

    /**
     * Scans through all classes contained within the se.proxus.kanon package and checks if they are a
     * registered EventListener and registers them as such. It skips the modules.events package because that
     * requires real-time registration and unregistration of EventListeners to work properly.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void scanForListeners()
    throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        for (final Class<?> tempClass : Reflectionz.getClasses(getClass().getPackage().getName())) {
            if (tempClass.getPackage().getName().contains("modules.list")) {
                continue;
            }

            if (tempClass.equals(getClass())) {
                LOGGER.info("FoundListener [" + tempClass.getName() + "]");
                EventWrapper.registerListener(this);
                continue;
            }

            int matches = 0;

            for (final Method method : tempClass.getDeclaredMethods()) {
                if (!EventWrapper.getEventBus().isMarked(method)) {
                    continue;
                }

                matches++;
                LOGGER.info("FoundListener [" + tempClass.getName() + ", " + method.getName() + "]");
                continue;
            }

            if (matches >= 1) {
                final Object listener = tempClass.newInstance();

                EventWrapper.registerListener(listener);
            }
        }
    }

    @EventSubscribe(priority = EventPriority.HIGHEST)
    public final void onPreGameStart(final EventPreGameStart event) {
        LOGGER.info("Pre initialization..");
        EventWrapper.fire(new EventPreInitialization());
    
        LOGGER.info("Post initialization..");
        EventWrapper.fire(new EventPostInitialization());
    }

    @EventSubscribe(priority = EventPriority.HIGHEST)
    public final void onPostGameStart(final EventPostGameStart event) {
        getFramesManager().setup();
    }
    
    public static String getName() {
        return "Kanon";
    }
    
    public static double getVersion() {
        return 0.35D;
    }

    public final Random getRNG() {
        return RNG;
    }

    public final FramesManager getFramesManager() {
        return (FramesManager) FRAMES_SCREEN.getManager();
    }

    @Override
    public String toString() {
        return getName() + " " + getVersion();
    }

    public static Kanon getInstance() {
        if (instance == null) {
            instance = new Kanon();
        }
        return instance;
    }
}
