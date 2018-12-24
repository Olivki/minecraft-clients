package se.proxus.kanon.modules;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.lwjgl.input.Keyboard;
import se.proxus.kanon.Kanon;
import se.proxus.kanon.commands.ConfigCommand;
import se.proxus.kanon.config.Configuration;
import se.proxus.kanon.config.Entry;
import se.proxus.kanon.config.handler.Handler;
import se.proxus.kanon.event4j.EventWrapper;
import se.proxus.kanon.keybinds.KeybindModule;
import se.proxus.kanon.utils.minecraft.client.Colourz;
import se.proxus.kanon.utils.system.Filez;
import se.proxus.kanon.wrapper.minecraft.Player;

import java.io.File;
import java.util.Objects;

import static org.lwjgl.input.Keyboard.getKeyName;

@Getter
@Setter
public abstract class Module {
   
   @NonNull private final Configuration config;
   
   private final Controller controller;
   
   private final Category category;
   
   private KeybindModule keybind;
   
   private ConfigCommand configCommand;
   
   public Module(final String name, final String description) {
      this(name, getKeyName(Keyboard.KEY_NONE), description, Controller.TRIGGER, Category.NONE);
   }
   
   public Module(final String name, final String description, final Category category) {
      this(name, getKeyName(Keyboard.KEY_NONE), description, Controller.TOGGLE, category);
   }
   
   public Module(final String name, final String description, final Controller controller) {
      this(name, getKeyName(Keyboard.KEY_NONE), description, controller, Category.NONE);
   }
   
   public Module(final String name, final String keybind, final String description) {
      this(name, keybind, description, Controller.TRIGGER, Category.NONE);
   }
   
   public Module(final String name, final String description, final Controller controller,
           final Category category) {
      this(name, getKeyName(Keyboard.KEY_NONE), description, controller, category);
   }
   
   public Module(final String name, final String keybind, final String description, final Category category) {
      this(name, keybind, description, Controller.TOGGLE, category);
   }
   
   public Module(final String name, final String keybind, final String description,
                 final Controller controller) {
      this(name, keybind, description, controller, Category.NONE);
   }
   
   public Module(final String name, final String keybind, final String description,
           final Controller controller, final Category category) {
      this.controller = controller;
      this.category = category;
      
      final File directory = Filez.getDirectory(Kanon.DIRECTORY, "modules/configurations/"
                                                              + category.getName().toLowerCase());
      
      config = Objects.requireNonNull(Kanon.CONFIGS.registerConfig(name, directory));
    
      populateEntries(name, description, keybind);
      
      initialize();
      config.getHandler().load();
      checkState();
      config.getHandler().setState(Handler.State.STANDBY);
      generateCommands();
   }
   
   private void populateEntries(final String name, final String description, final String keybind) {
       config.addEntry("Name", name,false)
               .setDescription("The name of the module.")
               .setMutable(false);
    
       config.addEntry("Description", description,false)
               .setDescription("The description of the module.")
               .setMutable(false);
    
       if (!Objects.equals(controller, Controller.MONITOR)) {
           config.addEntry("State", false,false)
                   .setDescription("The active state of the module.")
                   .setCommandMutable(false);
        
           //config.addEntry("Keybind", keybind.toUpperCase(),false)
           //        .setDescription("The keybind used to trigger to module.")
           //        .setCommandMutable(false);
           this.keybind = Kanon.KEYBINDS.addModuleKeybind(keybind, this);
        
           config.addEntry("Global", false,false)
                   .setDescription("Whether or not the mod can be triggered anywhere in the client.")
                   .setMutable(false);
       }
   }
   
   public final void checkState() {
      if (isToggleable()) {
         if (getState()) {
            onEnable();
         } else {
            onDisable();
         }
      }
   }
   
   protected final void error(final String message) {
      print(Colourz.RED + message);
   }
   
   protected final void errorf(final String message, final Object... args) {
      error(String.format(message, args));
   }
   
   private void generateCommands() {
      boolean shouldRegisterCommand = false;
      
      for (final Entry value : getConfig().getEntries()) {
         if (!value.isCommandMutable() || !value.isMutable()) {
            continue;
         }
         
         shouldRegisterCommand = true;
         
         break;
      }
      
      if (shouldRegisterCommand) {
         setConfigCommand(Kanon.COMMANDS.registerCommand(new ConfigCommand(getConfig()), true));
         registerCommands();
      }
   }
   
   public void registerCommands() {}
   
   public void initialize() {}
   
   public void onEnable() {
        EventWrapper.registerListener(this);
    }
   
   public void onDisable() {
      EventWrapper.unregisterListener(this);
   }
   
   public void onTrigger() {}
   
   public void onToggle() {}
   
   protected final void print(final String message) {
      Player.addMessage(message);
   }
   
   protected final void printf(final String message, final Object... args) {
      print(String.format(message, args));
   }
    
    protected final void printc(String message) {
        if (message.contains("[") && message.contains("]")) {
            message = message.replace("[", "[" + Colourz.CLIENT_COLOUR);
            message = message.replace("]", Colourz.GREY + "]");
        }
        
        print(message);
    }
    
    protected final void printcf(final String message, final Object... args) {
        printc(String.format(message, args));
    }
   
   protected final void send(final String message) {
      Player.sendMessage(message);
   }
   
   protected final void sendf(final String message, final Object... args) {
      send(String.format(message, args));
   }
   
   @Override
   public String toString() {
      final String format;
      
      if (getController().equals(Controller.MONITOR)) {
         format = String.format("Module[%s, \"%s\", %s, %s]",
                                getName(),
                                getDescription(),
                                controller.name(),
                                category.name());
      } else {
         format = String.format("Module[%s, %s (%s), \"%s\", %s, %s, %s]",
                                getName(),
                                getKeybind(),
                                keybind.getId(),
                                getDescription(),
                                getState(),
                                controller.name(),
                                category.name());
      }
      
      return format;
   }
   
   public final void toggle() {
      if (isToggleable()) {
         getConfig().toggle("State", false);
         checkState();
         onToggle();
      }
   }
   
   public final String getDescription() {
      return getConfig().getString("Description");
   }
   
   public final boolean isGlobal() {
      return getConfig().getBoolean("Global");
   }
   
   public final boolean isHidden() {
      return Objects.equals(controller, Controller.TOGGLE_HIDDEN);
   }
   
   protected final Kanon getKanon() {
      return Kanon.getInstance();
   }
   
   public final String getName() {
      return getConfig().getString("Name");
   }
   
   public final boolean getState() {
      return getConfig().getBoolean("State");
   }
   
   public final boolean isToggleable() {
      return controller.isToggleable();
   }
   
   @RequiredArgsConstructor
   public enum Controller {
       TOGGLE(true),
       TOGGLE_HIDDEN(true),
       TRIGGER(false),
       MONITOR(false);
    
       @Getter private final boolean toggleable;
   }
    
    public enum Category {
        COMBAT("Combat", Colourz.RED),
        WORLD("World", Colourz.GREY),
        MOVEMENT("Movement", Colourz.GOLD),
        RENDER("Render", Colourz.YELLOW),
        SERVER("Server", Colourz.DARK_AQUA),
        GUI("Gui", Colourz.WHITE),
        MISC("Misc", Colourz.GREY),
        NONE("None", Colourz.BLACK, false);
        
        @Getter private final String name;
        @Getter private final String colour;
        @Getter private final boolean display;
        
        Category(final String name, final String colour) {
            this(name, colour, true);
        }
        
        Category(final String name, final String colour, final boolean display) {
            this.name = name;
            this.colour = colour;
            this.display = display;
        }
    }
}