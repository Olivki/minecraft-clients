package se.proxus;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiConnecting;
import net.minecraft.src.Packet;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.Session;
import se.proxus.betterfonts.StringCache;
import se.proxus.commands.CommandManager;
import se.proxus.events.EventManager;
import se.proxus.frames.FrameHandler;
import se.proxus.hooks.Player;
import se.proxus.hooks.PlayerImpl;
import se.proxus.mods.ModHandler;
import se.proxus.mods.list.none.Friends;
import se.proxus.screens.ScreenMain;
import se.proxus.tools.ArrayHelper;
import se.proxus.tools.FileTools;
import se.proxus.tools.FontFactory;
import se.proxus.tools.IOTools;
import se.proxus.tools.Location;

public class Gallium {

    private boolean needsUpdate;
    private File directory;
    private PlayerImpl player;
    private final static Random RNG = new Random();
    private final EventManager events = new EventManager(this);
    private final ModHandler mods = new ModHandler(this);
    private final FileTools fileUtils = new FileTools();
    private final CommandManager commands = new CommandManager(this);
    private final FontFactory fontFactory = new FontFactory(this);
    private final ScreenMain main = new ScreenMain(this, null);
    private final FrameHandler frames = new FrameHandler(this);
    private final static List VIP_LIST = new LinkedList<String>();
    private final static List MOD_LIST = new LinkedList<String>();
    private final static int USER_ID = RNG.nextInt(6);
    private static int protocolVersion = 61;
    private double newVersion;
    private StringCache font;
    private StringCache fontPanel;
    private StringCache fontChat;

    /**
     * @author Oliver
     * @param type
     *            Where the method gets ran from. <i>(Example; 0 (First
     *            Minecraft startup.), 1 (Each time the Main menu gets
     *            opened.))</i>
     */
    public void init(final int type) {
	try {
	    switch (type) {
	    case 0:
		getLogger().info(toString() + " " + getDate());
		getLogger().info("User ID: " + USER_ID);
		if (getMinecraft().session.username.startsWith("Player"))
		    getMinecraft().session.username = "Olivki";
		player = new PlayerImpl(this);
		directory = new File(getMinecraft().getMinecraftDir(),
			getName().toLowerCase());
		getDirectory().mkdirs();
		getCommands().init();
		getMods().init();
		getMain().init();
		getFrames().init();
		break;
	    case 1:
		if (getFont() == null) {
		    setFont(new StringCache(
			    getMinecraft().fontRenderer.getColorCode()));
		    getFont().setDefaultFont("Lucida Console", 18, true);
		}
		if (getFontPanel() == null) {
		    setFontPanel(new StringCache(
			    getMinecraft().fontRenderer.getColorCode()));
		    getFontPanel().setDefaultFont("Tahoma", 17, true);
		}
		if (getFontChat() == null) {
		    setFontChat(new StringCache(
			    getMinecraft().fontRenderer.getColorCode()));
		    getFontChat().setDefaultFont("Verdana Bold", 17, true);
		}
		break;
	    }
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
    }

    /**
     * @author Ownage
     * @param hostname
     *            The hostname for the server.
     * @param port
     *            The port for the server.
     */
    public void connectToServer(final String hostname, final int port) {
	if (getMinecraft().theWorld != null) {
	    getMinecraft().theWorld.sendQuittingDisconnectingPacket();
	    try {
		Thread.sleep(2000L);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	getMinecraft().displayGuiScreen(
		new GuiConnecting(null, getMinecraft(), hostname, port));
    }

    /**
     * @return The Clients name. <i>(Example: "Gallium")</i>
     */
    public String getName() {
	return "Gallium";
    }

    /**
     * @return The Clients current version. <i>(Example: 1.53D)</i>
     */
    public double getVersion() {
	return 1.5D;
    }

    /**
     * @return Returns the Clients font factory.
     */
    public FontFactory getFontFactory() {
	return fontFactory;
    }

    /**
     * All the download links that the clients need when updating.
     * 
     * @return The download links that the clients need when updating.
     */
    public String[] getDownloadLinks() {
	IOTools tools = new IOTools("http://proxus.se/Gallium/Links.txt");
	ArrayList<String> loadedLinks = new ArrayList<String>();
	String input = "";
	while ((input = tools.getContent()) != null)
	    loadedLinks.add(input);
	return loadedLinks.toArray(new String[loadedLinks.size()]);
    }

    /**
     * @author Oliver
     * @return Minecrafts current version
     */
    public String getMinecraftVersion() {
	return "1.5.2";
    }

    /**
     * @return The Clients Logger. <i>(Example: Logger.getLogger("Gallium"))</i>
     */
    public Logger getLogger() {
	return Logger.getLogger(toString());
    }

    /**
     * @author Oliver
     * @return Returns the Clients current file. <i>(Example:
     *         Client.getMinecraft().getMinecraftDir() + "/Gallium/")</i>
     */
    public File getDirectory() {
	return directory;
    }

    /**
     * @return Returns the hooked player class.
     */
    public Player getPlayer() {
	return player;
    }

    /**
     * Gets the Clients FrameHandler.
     * 
     * @return The Clients FrameHandler.
     */
    public FrameHandler getFrames() {
	return frames;
    }

    /**
     * @return Returns a instance of Minecraft.java
     */
    public Minecraft getMinecraft() {
	return Minecraft.getMinecraft();
    }

    /**
     * @return Returns the Clients mod handler.
     */
    public ModHandler getMods() {
	return mods;
    }

    /**
     * @author Ramisme
     * @return Returns the Clients event manager.
     */
    public EventManager getEvents() {
	return events;
    }

    /**
     * @return Returns the Clients file utils.
     */
    public FileTools getFileUtils() {
	return fileUtils;
    }

    /**
     * @author Oliver
     * @return Returns the Clients command manager.
     */
    public CommandManager getCommands() {
	return commands;
    }

    /**
     * Gets the Clients RNG. <i>(Random Number Generator)</i>
     * 
     * @return The Clients RNG. <i>(Random Number Generator)</i>
     */
    public Random getRNG() {
	return RNG;
    }

    /**
     * Gets the Clients ScaledResolution for getting the bounds of the screen.
     * 
     * @return The Clients ScaledResolution for getting the bounds of the
     *         screen.
     */
    public ScaledResolution getResolution() {
	return new ScaledResolution(getMinecraft().gameSettings,
		getMinecraft().displayWidth, getMinecraft().displayHeight);
    }

    /**
     * @param packet
     *            The Packet to be sent to the server.
     */
    public void sendPacket(final Packet packet) {
	getMinecraft().thePlayer.sendQueue.addToSendQueue(packet);
    }

    /**
     * A method for getting the distance between 2 locations.
     * 
     * @param location1
     *            Location one.
     * @param location2
     *            Location two.
     * @return The distance between to locations in doubles.
     */
    public double distanceTo(final Location location1, final Location location2) {
	return location1.distanceTo(location2.getX(), location2.getY(),
		location2.getZ());
    }

    /**
     * Attempts to log you into the specified account.
     * 
     * @author JordinJM
     * @param username
     *            The Minecraft username/email.
     * @param password
     *            The password.
     * @return If the login was successful or not.
     */
    public boolean loginToAccount(final String username, final String password) {
	try {
	    URL url = new URL(
		    String.format(
			    "http://login.minecraft.net/?user=%s&password=%s&version=69",
			    username, password));
	    BufferedReader in = new BufferedReader(new InputStreamReader(url
		    .openConnection().getInputStream()));
	    String as[] = in.readLine().split(":");
	    getMinecraft().session = new Session(as[2], as[3]);
	    return true;
	} catch (Exception e) {
	    return false;
	}
    }

    /**
     * The default font for the Client. <i>(Might be used in global TTF later
     * on.)</i>
     * 
     * @return The Clients default font.
     */
    public StringCache getFont() {
	return font;
    }

    /**
     * Sets the Clients default font.
     * 
     * @param font
     *            The font to be set.
     */
    public void setFont(final StringCache font) {
	this.font = font;
    }

    /**
     * The font for the 'panels' in the client.
     * 
     * @return The font for the 'panels' in the client.
     */
    public StringCache getFontPanel() {
	return fontPanel;
    }

    /**
     * Sets the clients panel font.
     * 
     * @param font
     *            The font to be set.
     */
    public void setFontPanel(final StringCache fontPanel) {
	this.fontPanel = fontPanel;
    }

    /**
     * The font for the chat in the client.
     * 
     * @return The font for the chat in the client.
     */
    public StringCache getFontChat() {
	return fontChat;
    }

    /**
     * Sets the Clients chat font.
     * 
     * @param font
     *            The font to be set.
     */
    public void setFontChat(final StringCache fontChat) {
	this.fontChat = fontChat;
    }

    /**
     * A method for getting the computers current date, without a default
     * format.
     * 
     * @param format
     *            The format for the SimpleDateFormat. <i>(Example:
     *            yyyy/mm/dd/hh:mm)</i>
     * @see Gallium#getDate()
     * @return Returns computers current date.
     */
    public String getDate(final String format) {
	DateFormat date = new SimpleDateFormat(format);
	Calendar calendar = Calendar.getInstance();
	return date.format(calendar.getTime());
    }

    /**
     * A method for getting the computers current date, with a default format.
     * 
     * @see Gallium#getDate(String)
     * @return Returns computers current date.
     */
    public String getDate() {
	return getDate("yyyy/mm/dd/hh:mm");
    }

    @Override
    public String toString() {
	return getName() + " " + getVersion() + " (" + getMinecraftVersion()
		+ ")";
    }

    public void checkVersion() {
	try {
	    URL url = new URL("http://proxus.se/DreamPvP/checkVersion.php");
	    BufferedReader reader = new BufferedReader(new InputStreamReader(
		    url.openStream()));
	    String s = reader.readLine();

	    if (Double.parseDouble(s) != getVersion()) {
		setNeedsUpdate(true);
		setNewVersion(Double.parseDouble(s));
		getLogger().log(Level.INFO,
			"Client is outdated. v" + getNewVersion());
	    } else
		getLogger().log(Level.INFO, "Client is up to date.");
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public void readVipList() {
	try {
	    getVipList().clear();
	    URL url = new URL("http://proxus.se/DreamPvP/vips.txt");
	    BufferedReader reader = new BufferedReader(new InputStreamReader(
		    url.openStream()));
	    String s;

	    while ((s = reader.readLine()) != null) {
		getLogger().log(Level.INFO, s);
		getVipList().add(s.toLowerCase());
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public void readModsList() {
	try {
	    getModList().clear();
	    URL url = new URL("http://proxus.se/DreamPvP/Mods.txt");
	    BufferedReader reader = new BufferedReader(new InputStreamReader(
		    url.openStream()));
	    String s;

	    while ((s = reader.readLine()) != null) {
		getLogger().log(Level.INFO, s);
		if (!((Friends) getMods().getMod("Friends")).isFriend(s)) {
		    ((Friends) getMods().getMod("Friends")).getLoadedFriends()
			    .add(new ArrayHelper(s, s));
		    ((Friends) getMods().getMod("Friends")).saveFriends();
		}
		getModList().add(s.toLowerCase());
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public boolean getNeedsUpdate() {
	return needsUpdate;
    }

    public void setNeedsUpdate(final boolean needsUpdate) {
	this.needsUpdate = needsUpdate;
    }

    public static List<String> getVipList() {
	return VIP_LIST;
    }

    public static List<String> getModList() {
	return MOD_LIST;
    }

    public ScreenMain getMain() {
	return main;
    }

    public double getNewVersion() {
	return newVersion;
    }

    public void setNewVersion(final double newVersion) {
	this.newVersion = newVersion;
    }

    public static int getUserId() {
	return USER_ID;
    }

    public static int getProtocolVersion() {
	return protocolVersion;
    }

    public static void setProtocolVersion(final int protocolVersion) {
	Gallium.protocolVersion = protocolVersion;
    }
}