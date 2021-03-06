package se.proxus.owari.mods;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import se.proxus.owari.Client;

public class ModManager {

	private static ModManager instance;
	private static final List<Mod> LOADED_MODS = new LinkedList<Mod>();
	private File directory;
	private File configs;

	public void init() throws IOException {
		setDirectory(new File(getClient().getDirectory(), "mods"));
		setConfigs(new File(getDirectory(), "configs"));
		FileUtils.forceMkdir(getDirectory());
		FileUtils.forceMkdir(getConfigs());
	}

	public void loadMods() {
		/** Re-organizing the mods after the alphabet. **/
		getClient().getLogger().info("Re-organizing the mods after the alphabet.");
		List<Mod> tempList = new LinkedList<Mod>();
		tempList.addAll(getMods());
		Mod[] tempArray = tempList.toArray(new Mod[tempList.size()]);
		sortMods(tempArray);
		getMods().clear();

		for (Mod mod : tempArray) {
			addMod(mod);
		}

		tempArray = null;
		tempList.clear();
		tempList = null;
		System.gc();
	}

	public void onKeyPressed(final String keyName) {
		for (Mod mod : getMods()) { // Make it so that you can't have the same
									// bind as any other mod, k
			if (keyName.equalsIgnoreCase(mod.getKeybind()) && !keyName.equalsIgnoreCase("NONE")
					&& getClient().getMinecraft().inGameHasFocus) {
				mod.toggle();
				break;
			}
		}
	}

	public void addMod(final Mod mod) {
		if (getMod(mod.getName()) == null) {
			getClient().getLogger().info("Added the mod '" + mod.getName() + "'");
			getMods().add(mod);
		}
	}

	public void removeMod(final Mod mod) {
		if (getMod(mod.getName()) != null) {
			getClient().getLogger().info("Removed the mod '" + mod.getName() + "'");
			getMods().remove(mod);
		}
	}

	public Mod getMod(final String name) {
		Mod tempMod = null;
		for (Mod mod : getMods()) {
			if (mod.getName().equalsIgnoreCase(name)) {
				tempMod = mod;
				break;
			}
		}
		return tempMod;
	}

	public Mod getModByShortenedName(final String name) {
		Mod tempMod = null;
		for (Mod mod : getMods()) {
			if (mod.getName().replace(" ", "").equalsIgnoreCase(name)) {
				tempMod = mod;
				break;
			}
		}
		return tempMod;
	}

	public Mod[] getRegisteredMods() {
		return getMods().toArray(new Mod[getMods().size()]);
	}

	public ArrayList<Mod> getActiveMods() {
		ArrayList<Mod> activeMods = new ArrayList<Mod>();
		for (Mod mod : getMods()) {
			if (mod.getState() && !mod.isHidden()) {
				if (!activeMods.contains(mod)) {
					activeMods.add(mod);
				}
			}
		}
		return activeMods;
	}

	public Mod[] sortMods(final Mod[] mods) {
		Arrays.sort(mods, new Comparator<Mod>() {
			@Override
			public int compare(final Mod mod1, final Mod mod2) {
				String modName1 = mod1.getName();
				String modName2 = mod2.getName();
				return modName1.compareTo(modName2);
			}
		});
		return mods;
	}

	public Client getClient() {
		return Client.getInstance();
	}

	public List<Mod> getMods() {
		return LOADED_MODS;
	}

	public static ModManager getInstance() {
		if (instance == null) {
			instance = new ModManager();
		}
		return instance;
	}

	public File getDirectory() {
		return directory;
	}

	public void setDirectory(final File directory) {
		this.directory = directory;
	}

	public File getConfigs() {
		return configs;
	}

	public void setConfigs(final File configs) {
		this.configs = configs;
	}
}