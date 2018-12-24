package se.proxus.kanon.modules;

import se.proxus.kanon.Kanon;
import se.proxus.kanon.event4j.EventSubscribe;
import se.proxus.kanon.event4j.events.client.EventClientReload;
import se.proxus.kanon.event4j.events.client.init.EventPreInitialization;
import se.proxus.kanon.utils.lang.Stringz;
import se.proxus.kanon.utils.system.Filez;
import se.proxus.kanon.utils.system.Reflectionz;

import java.io.File;
import java.io.IOException;
import java.util.*;

public final class ModuleFactory {

	private final static Map<String, Module> REGISTERED_MODULES = new LinkedHashMap<>();
	
    public final static File DIRECTORY = Filez.getDirectory(Kanon.DIRECTORY, "modules");

	@EventSubscribe
	public void onPreInitialization(final EventPreInitialization event) {
        Kanon.LOGGER.info("Scanning for modules..");

		try {
			scanForModules();
		} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		organizeModules();
		Kanon.COMMANDS.organizeCommands();
	}

	@EventSubscribe
	public void onClientReload(final EventClientReload event) {
		REGISTERED_MODULES.clear();
		onPreInitialization(null);
	}

	private void scanForModules()
    throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
		final String packageName = getClass().getPackage().getName() + ".list";

		for (final Class<?> moduleClass : Reflectionz.getClasses(packageName)) {
			if (!Module.class.isAssignableFrom(moduleClass))
				continue;

			final Module module = (Module) moduleClass.newInstance();

			registerModule(module, true);
		}
	}

	private void organizeModules() {
        final LinkedHashMap<String, Module> cloneMap = new LinkedHashMap<>(REGISTERED_MODULES);
		final Module[] tempArray = cloneMap.values().toArray(new Module[0]);
		sortModules(tempArray);
		REGISTERED_MODULES.clear();

		for (final Module module : tempArray) {
			registerModule(module, false);
		}

		cloneMap.clear();
		System.gc();
	}

	public void registerModule(final Module module, final boolean shouldLog) {
		registerModule(module.getName(), module, shouldLog);
	}

	public void registerModule(final String name, final Module module, final boolean shouldLog) {
		if (!contains(module.getName())) {
			if (shouldLog)
				Kanon.LOGGER.info(module.toString().replace("Module[", "ModuleRegister["));

			REGISTERED_MODULES.put(name, module);
		}
	}

	public void unregisterModule(final Module module, final boolean shouldLog) {
		unregisterModule(module.getName(), shouldLog);
	}

	public void unregisterModule(final String name, final boolean shouldLog) {
		if (contains(name)) {
			lombok.val module = REGISTERED_MODULES.get(name);

			if (shouldLog)
                Kanon.LOGGER.info(module.toString().replace("Module[",
																	  "ModuleUnregister["));

			REGISTERED_MODULES.remove(name);
		}
	}

	public final boolean contains(final String name) {
		return REGISTERED_MODULES.containsKey(name) || getByCommandName(name) != null;

	}

	public Module getModule(final String name) {
		return REGISTERED_MODULES.get(name);
	}

	public Module getByCommandName(final String query) {
		for (lombok.val module : getModules()) {
			if (Stringz.toCamelCase(module.getName(), false).equalsIgnoreCase(query)) {
				return module;
			}
		}

		return null;
	}

	public Module[] getByCategory(final Module.Category category) {
        return REGISTERED_MODULES.values()
                .stream()
                .filter(module -> module.getCategory()
                        .equals(category))
                .toArray(Module[]::new);
	}

	// These two methods are probably not very efficient ways to do this.
	private Module[] getActiveModules() {
		final List<Module> activeModules = new LinkedList<>();

		for (final Module module : getModules()) {
			if (module.getController().equals(Module.Controller.TOGGLE) && module.getState()) {
				activeModules.add(module);
			}
		}

		return sortModules(activeModules.toArray(new Module[0]));
	}

	public Module[] getModulesToDisplay() {
        final List<Module> modulesToDisplay = new LinkedList<>();

		for (final Module module : getActiveModules()) {
			if (module.getController().equals(Module.Controller.TOGGLE)) {
				modulesToDisplay.add(module);
			}
		}

		return sortModules(modulesToDisplay.toArray(new Module[0]));
	}

	private Module[] sortModules(final Module[] modules) {
		Arrays.sort(modules, (module1, module2) -> {
			lombok.val modName1 = module1.getName();
			lombok.val modName2 = module2.getName();
			return modName1.compareTo(modName2);
		});

		return modules;
	}

	public Module[] getModules() {
		return REGISTERED_MODULES.values().toArray(new Module[0]);
	}

	public Kanon getKanon() {
		return Kanon.getInstance();
	}
}