package se.proxus.mods;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import se.proxus.Gallium;
import se.proxus.mods.list.combat.AntiVelocity;
import se.proxus.mods.list.combat.AutoAttack;
import se.proxus.mods.list.combat.AutoSoup;
import se.proxus.mods.list.combat.Criticals;
import se.proxus.mods.list.gui.Chat;
import se.proxus.mods.list.gui.Gui;
import se.proxus.mods.list.gui.Notifications;
import se.proxus.mods.list.misc.ChatDate;
import se.proxus.mods.list.movement.AutoSwim;
import se.proxus.mods.list.movement.Flight;
import se.proxus.mods.list.movement.Freecam;
import se.proxus.mods.list.movement.NoFall;
import se.proxus.mods.list.movement.Sprint;
import se.proxus.mods.list.none.Friends;
import se.proxus.mods.list.none.Panels;
import se.proxus.mods.list.render.ChestESP;
import se.proxus.mods.list.render.ESP;
import se.proxus.mods.list.render.LineESP;
import se.proxus.mods.list.render.Nametags;
import se.proxus.mods.list.render.Tracer;
import se.proxus.mods.list.render.Waypoints;
import se.proxus.mods.list.server.AutoEquip;
import se.proxus.mods.list.server.AutoFish;
import se.proxus.mods.list.server.AutoRespawn;
import se.proxus.mods.list.server.Derp;
import se.proxus.mods.list.world.Brightness;
import se.proxus.mods.list.world.Miner;

public class ModHandler {

    private File directory;
    private Gallium client;
    private static final List<Mod> LOADED_MODS = new LinkedList<Mod>();
    private static final HashMap<String, Mod> LOADED_MODS_HASH = new HashMap<String, Mod>();

    public ModHandler(final Gallium client) {
	setClient(client);
    }

    /**
     * @author Oliver The method where you should add all your mods in.
     *         <i>(Example: addMod(new Flight())</i>
     */
    public void init() {
	directory = new File(getClient().getDirectory(), File.separator
		+ "mods" + File.separator);
	getDirectory().mkdirs();
	getLoadedMods().clear();
	getLoadedModsHash().clear();
	addMod(new Gui(getClient()));
	addMod(new NoFall(getClient()));
	addMod(new AutoAttack(getClient()));
	addMod(new AntiVelocity(getClient()));
	addMod(new ChatDate(getClient()));
	addMod(new Criticals(getClient()));
	addMod(new Brightness(getClient()));
	addMod(new Miner(getClient()));
	addMod(new Sprint(getClient()));
	addMod(new Derp(getClient()));
	addMod(new AutoSoup(getClient()));
	addMod(new Flight(getClient()));
	addMod(new AutoRespawn(getClient()));
	addMod(new AutoEquip(getClient()));
	addMod(new AutoSwim(getClient()));
	addMod(new Tracer(getClient()));
	addMod(new ESP(getClient()));
	addMod(new Friends(getClient()));
	addMod(new AutoFish(getClient()));
	addMod(new Freecam(getClient()));
	addMod(new LineESP(getClient()));
	addMod(new Chat(getClient()));
	addMod(new Panels(getClient()));
	addMod(new ChestESP(getClient()));
	addMod(new Waypoints(getClient()));
	addMod(new Nametags(getClient()));
	addMod(new Notifications(getClient()));
	/** Resort them after the alphabet. **/
	getClient().getLogger().info(
		"Reorganizing the Mod list after the alphabet.");
	List<Mod> tempList = new LinkedList<Mod>();
	tempList.addAll(getLoadedMods());
	Mod[] tempArray = tempList.toArray(new Mod[tempList.size()]);
	sortMods(tempArray);
	getLoadedMods().clear();
	for (Mod mod : tempArray)
	    addMod(mod);
	tempArray = null;
	tempList.clear();
	tempList = null;
	System.gc();
    }

    /**
     * @author Oliver
     * @param key
     *            The name of the key that got pressed, you can toggle your mods
     *            in here.
     */
    public void onKeyPressed(final String key) {
	for (Mod mod : getLoadedMods())
	    if (key.equalsIgnoreCase(mod.getKey())
		    && !key.equalsIgnoreCase("NONE"))
		mod.toggle();
    }

    /**
     * @author Oliver
     * @param mod
     *            The Mod that you want to add.
     */
    public void addMod(final Mod mod) {
	if (!getLoadedModsHash().containsKey(mod.getName())) {
	    getLoadedMods().add(mod);
	    getLoadedModsHash().put(
		    mod.getName().replace(" ", "").toLowerCase(), mod);
	    getClient().getLogger().log(Level.INFO,
		    "Added the mod: " + mod.getName());
	} else
	    throw new IllegalArgumentException("The mod is already added!");
    }

    /**
     * @author Oliver
     * @param name
     *            The Mod that you want to remove.
     */
    public void removeMod(final Mod mod) {
	getLoadedMods().remove(getLoadedMods().indexOf(mod));
	getLoadedModsHash().remove(mod.getName().toLowerCase());
    }

    /**
     * @author Oliver
     * @param name
     *            The name of the Mod that you wan to get.
     * @return Returns the Mod with the same name as the one entered.
     */
    public Mod getMod(final String name) {
	return getLoadedModsHash().get(name.replace(" ", "").toLowerCase());
    }

    /**
     * @author Oliver
     * @param text
     *            The text typen in.
     * @return The mods that contains any part of the text.
     */
    public ArrayList<Mod> getMods(final String text) {
	ArrayList<Mod> toAdd = new ArrayList<Mod>();
	if (text.isEmpty())
	    toAdd.clear();
	else
	    for (Mod mod : getLoadedMods())
		if (mod.getName().toLowerCase().startsWith(text.toLowerCase()))
		    toAdd.add(mod);
	return toAdd;
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

    /**
     * @author Oliver
     * @return Returns the loaded mods.
     */
    public List<Mod> getLoadedMods() {
	return LOADED_MODS;
    }

    /**
     * @author Oliver
     * @return Returns the loaded mods.
     */
    public HashMap<String, Mod> getLoadedModsHash() {
	return LOADED_MODS_HASH;
    }

    /**
     * A method for getting all the current active mods. <i>(Ignores all the
     * hidden ones.)</i>
     * 
     * @return Returns a ArrayList with all the active mods.
     */
    public ArrayList<Mod> getActiveMods() {
	ArrayList<Mod> activeMods = new ArrayList<Mod>();
	for (Mod mod : getLoadedMods())
	    if (mod.getState() && !mod.isHidden())
		if (!activeMods.contains(mod))
		    activeMods.add(mod);
	return activeMods;
    }

    public Mod[] getRegisteredMods() {
	return getLoadedMods().toArray(new Mod[getLoadedMods().size()]);
    }

    /**
     * @author Oliver
     * @return Returns the mods directory.
     */
    public File getDirectory() {
	return directory;
    }

    public Gallium getClient() {
	return client;
    }

    public void setClient(final Gallium client) {
	this.client = client;
    }
}