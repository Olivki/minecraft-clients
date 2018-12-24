package se.proxus.mods.list.render;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import net.minecraft.src.StringUtils;

import org.lwjgl.opengl.GL11;

import se.proxus.Gallium;
import se.proxus.commands.Command;
import se.proxus.commands.CommandType;
import se.proxus.events.EventHandler;
import se.proxus.events.list.client.EventRendered3D;
import se.proxus.mods.Mod;
import se.proxus.mods.ModCategory;
import se.proxus.tools.ArrayHelper;
import se.proxus.tools.Colours;
import se.proxus.tools.Location;
import se.proxus.tools.RenderTools;
import se.proxus.tools.StringTools;

public class Waypoints extends Mod {

    private final static List<ArrayHelper<String, Location>> LOADED_WAYPOINTS = new LinkedList<ArrayHelper<String, Location>>();

    public Waypoints(final Gallium client) {
	super("Waypoints", ModCategory.RENDER, true, client);
    }

    @Override
    public void init() {
	setDescription("Renders a box around waypoints and stuff.");
	setState(true);
	getClient().getCommands().registerCommand(
		new Command("waypoints", ".waypoints <add> <x> <y> <z> [name]",
			"Adds a waypoint.", getClient(),
			CommandType.LISTHANDLER) {
		    @Override
		    public void onCommand(final String message,
			    final String... args) {
			if (args[0].equalsIgnoreCase("add")) {
			    String name = "";
			    if (StringTools.isInteger(args[1])
				    && StringTools.isInteger(args[2])
				    && StringTools.isInteger(args[3])) {
				name = message.substring(getCommand().length()
					+ args[0].length() + args[1].length()
					+ args[2].length() + args[3].length()
					+ 2)
					+ ":"
					+ (getClient().getMinecraft()
						.getServerData() == null ? "Singleplayer"
						: getClient().getMinecraft()
							.getServerData().serverIP);
				if (!isWaypoint(name)) {
				    getLoadedWaypoints()
					    .add(new ArrayHelper(
						    name,
						    new Location(
							    Integer.parseInt(args[1]),
							    Integer.parseInt(args[2]),
							    Integer.parseInt(args[3]))));
				    getClient().getPlayer().addMessage(
					    Colours.GREY + "Added '"
						    + Colours.CLIENT_COLOUR
						    + name.split(":")[0]
						    + Colours.GREY + "'.");
				    saveWaypoints();
				} else
				    getClient()
					    .getPlayer()
					    .addMessage(
						    Colours.GREY
							    + "You already have a waypoint with that name.");
			    } else {
				name = message.substring(getCommand().length()
					+ args[0].length() + 2)
					+ ":"
					+ (getClient().getMinecraft()
						.getServerData() == null ? "Singleplayer"
						: getClient().getMinecraft()
							.getServerData().serverIP);
				if (!isWaypoint(name)) {
				    getLoadedWaypoints()
					    .add(new ArrayHelper(
						    name,
						    new Location(
							    (int) getClient()
								    .getPlayer()
								    .getX(),
							    (int) getClient()
								    .getPlayer()
								    .getY() + 1,
							    (int) getClient()
								    .getPlayer()
								    .getZ())));
				    getClient().getPlayer().addMessage(
					    Colours.GREY + "Added '"
						    + Colours.CLIENT_COLOUR
						    + name.split(":")[0]
						    + Colours.GREY + "'.");
				    saveWaypoints();
				} else
				    getClient()
					    .getPlayer()
					    .addMessage(
						    Colours.GREY
							    + "You already have a waypoint with that name.");
			    }
			}
		    }
		});
	getClient().getCommands().registerCommand(
		new Command("waypoints", ".waypoints <remove> [name]",
			"Removes a waypoint.", getClient(),
			CommandType.LISTHANDLER) {
		    @Override
		    public void onCommand(final String message,
			    final String... args) {
			if (args[0].equalsIgnoreCase("remove")) {
			    String name = message.substring(getCommand()
				    .length() + args[0].length() + 2)
				    + ":"
				    + (getClient().getMinecraft()
					    .getServerData() == null ? "Singleplayer"
					    : getClient().getMinecraft()
						    .getServerData().serverIP);
			    if (isWaypoint(name)) {
				getClient().getPlayer().addMessage(
					Colours.GREY + "Removed '"
						+ Colours.CLIENT_COLOUR
						+ name.split(":")[0]
						+ Colours.GREY + "'.");
				removeWaypointByName(name);
				saveWaypoints();
			    } else
				getClient()
					.getPlayer()
					.addMessage(
						Colours.GREY
							+ "You don't have a waypoint with that name.");
			}
		    }
		});
	getClient().getCommands().registerCommand(
		new Command("waypoints", ".waypoints <clear>",
			"Clears all your waypoints.", getClient(),
			CommandType.LISTHANDLER) {
		    @Override
		    public void onCommand(final String message,
			    final String... args) {
			if (args[0].equalsIgnoreCase("clear")) {
			    getClient().getPlayer().addMessage(
				    Colours.GREY + "Removed '"
					    + Colours.CLIENT_COLOUR
					    + getLoadedWaypoints().size()
					    + Colours.GREY + "' waypoints.");
			    getLoadedWaypoints().clear();
			    saveWaypoints();
			}
		    }
		});
	getClient().getCommands().registerCommand(
		new Command("waypoints", ".waypoints <list>",
			"Lists all your waypoints.", getClient(),
			CommandType.LISTHANDLER) {
		    @Override
		    public void onCommand(final String message,
			    final String... args) {
			if (args[0].equalsIgnoreCase("list"))
			    if (getLoadedWaypoints().size() > 0)
				for (ArrayHelper<String, Location> waypoints : getLoadedWaypoints())
				    getClient().getPlayer().addMessage(
					    Colours.GREY
						    + "["
						    + Colours.CLIENT_COLOUR
						    + getLoadedWaypoints()
							    .indexOf(waypoints)
						    + Colours.GREY
						    + "] "
						    + Colours.CLIENT_COLOUR
						    + waypoints.getKey().split(
							    ":")[0]
						    + Colours.GREY + " "
						    + waypoints.getValue());
			    else
				getClient().getPlayer().addMessage(
					Colours.GREY
						+ "You have no waypoints..");
		    }
		});
	loadWaypoints();
	registerSetting(0, false, "Tracers", 0.0D, true, false, true);
	registerSetting(1, true, "Nametags", 0.0D, true, false, true);
	registerSetting(2, true, "Black Outline", 0.0D, true, false, true);
    }

    @Override
    public void onEnable() {
	getClient().getEvents().registerListener(this);
    }

    @Override
    public void onDisable() {
	getClient().getEvents().unregisterListener(this);
    }

    @EventHandler
    public void onEventRendered3D(final EventRendered3D event) {
	if (getClient().getMinecraft().theWorld == null
		|| getClient().getMinecraft().thePlayer == null)
	    return;
	for (ArrayHelper<String, Location> waypoints : getLoadedWaypoints()) {
	    if (!waypoints.getKey().split(":")[1].equalsIgnoreCase(getClient()
		    .getMinecraft().getServerData() == null ? "Singleplayer"
		    : getClient().getMinecraft().getServerData().serverIP))
		continue;
	    String name = waypoints.getKey();
	    Location location = waypoints.getValue();
	    RenderTools tools = new RenderTools();
	    double x = event.getRenderX(location.getX());
	    double y = event.getRenderY(location.getY());
	    double z = event.getRenderZ(location.getZ());
	    /*
	     * tools.glColor4Hex(0xFF000000); tools.renderLine(x + 0.0D, y -
	     * 0.0D, z - 0.0D, x + 1.0D, y - 1.95D, z - 1.0D, 3.5F);
	     * tools.glColor4Hex(0xFFFFAA00); tools.renderLine(x + 0.0D, y -
	     * 0.0D, z - 0.0D, x + 1.0D, y - 1.95D, z - 1.0D, 1.5F);
	     * 
	     * tools.glColor4Hex(0xFF000000); tools.renderLine(x + 1.0D, y -
	     * 0.0D, z - 0.0D, x + 0.0D, y - 1.95D, z - 1.0D, 3.5F);
	     * tools.glColor4Hex(0xFFFFAA00); tools.renderLine(x + 1.0D, y -
	     * 0.0D, z - 0.0D, x + 0.0D, y - 1.95D, z - 1.0D, 1.5F);
	     * 
	     * tools.glColor4Hex(0xFF000000); tools.renderLine(x + 0.0D, y -
	     * 0.0D, z - 1.0D, x + 1.0D, y - 1.95D, z - 0.0D, 3.5F);
	     * tools.glColor4Hex(0xFFFFAA00); tools.renderLine(x + 0.0D, y -
	     * 0.0D, z - 1.0D, x + 1.0D, y - 1.95D, z - 0.0D, 1.5F);
	     * 
	     * tools.glColor4Hex(0xFF000000); tools.renderLine(x + 1.0D, y -
	     * 0.0D, z - 1.0D, x + 0.0D, y - 1.95D, z - 0.0D, 3.5F);
	     * tools.glColor4Hex(0xFFFFAA00); tools.renderLine(x + 1.0D, y -
	     * 0.0D, z - 1.0D, x + 0.0D, y - 1.95D, z - 0.0D, 1.5F);
	     * 
	     * tools.glColor4Hex(0xFF000000); tools.renderLine(x + 0.0D, y, z -
	     * 1.0D, x + 1.0D, y, z - 0.0D, 3.5F);
	     * tools.glColor4Hex(0xFFFFAA00); tools.renderLine(x + 0.0D, y, z -
	     * 1.0D, x + 1.0D, y, z - 0.0D, 1.5F);
	     * 
	     * tools.glColor4Hex(0xFF000000); tools.renderLine(x + 1.0D, y, z -
	     * 1.0D, x + 0.0D, y, z - 0.0D, 3.5F);
	     * tools.glColor4Hex(0xFFFFAA00); tools.renderLine(x + 1.0D, y, z -
	     * 1.0D, x + 0.0D, y, z - 0.0D, 1.5F);
	     */

	    /*
	     * tools.glColor4Hex(0xFF000000); tools.renderLine(x + 0.0D, y -
	     * 2.0D, z - 1.0D, x + 1.0D, y - 2.0D, z - 0.0D, 3.5F);
	     * 
	     * tools.glColor4Hex(0xFF000000); tools.renderLine(x + 1.0D, y -
	     * 2.0D, z - 1.0D, x + 0.0D, y - 2.0D, z - 0.0D, 3.5F);
	     * 
	     * tools.glColor4Hex(0xFF000000); tools.renderLine(x + 0.5D, y -
	     * 2.0D, z - 0.5D, x + 0.5D, y, z - 0.5D, 3.5F);
	     * 
	     * tools.glColor4Hex(0xFFFFAA00); tools.renderLine(x + 0.0D, y -
	     * 2.0D, z - 1.0D, x + 1.0D, y - 2.0D, z - 0.0D, 1.5F);
	     * 
	     * tools.glColor4Hex(0xFFFFAA00); tools.renderLine(x + 1.0D, y -
	     * 2.0D, z - 1.0D, x + 0.0D, y - 2.0D, z - 0.0D, 1.5F);
	     * 
	     * tools.glColor4Hex(0xFFFFAA00); tools.renderLine(x + 0.5D, y -
	     * 2.0D, z - 0.5D, x + 0.5D, y, z - 0.5D, 1.5F);
	     */

	    if ((Boolean) getSetting(2)) {
		tools.glColor4Hex(0xFF000000);
		tools.renderLine(x + 0.0D, y - 2.0D, z - 1.0D, x + 1.0D,
			y - 1.0D, z - 0.0D, 3.5F);
		tools.glColor4Hex(0xFF000000);
		tools.renderLine(x + 1.0D, y - 2.0D, z - 1.0D, x + 0.0D,
			y - 1.0D, z - 0.0D, 3.5F);
		tools.glColor4Hex(0xFF000000);
		tools.renderLine(x + 0.0D, y - 2.0D, z - 0.0D, x + 1.0D,
			y - 1.0D, z - 1.0D, 3.5F);
		tools.glColor4Hex(0xFF000000);
		tools.renderLine(x + 1.0D, y - 2.0D, z - 0.0D, x + 0.0D,
			y - 1.0D, z - 1.0D, 3.5F);
	    }

	    tools.glColor4Hex(0xFFFFAA00);
	    tools.renderLine(x + 0.0D, y - 2.0D, z - 1.0D, x + 1.0D, y - 1.0D,
		    z - 0.0D, 1.5F);
	    tools.glColor4Hex(0xFFFFAA00);
	    tools.renderLine(x + 1.0D, y - 2.0D, z - 1.0D, x + 0.0D, y - 1.0D,
		    z - 0.0D, 1.5F);
	    tools.glColor4Hex(0xFFFFAA00);
	    tools.renderLine(x + 0.0D, y - 2.0D, z - 0.0D, x + 1.0D, y - 1.0D,
		    z - 1.0D, 1.5F);
	    tools.glColor4Hex(0xFFFFAA00);
	    tools.renderLine(x + 1.0D, y - 2.0D, z - 0.0D, x + 0.0D, y - 1.0D,
		    z - 1.0D, 1.5F);

	    if ((Boolean) getSetting(0)) {
		GL11.glPushMatrix();
		if ((Boolean) getSetting(2)) {
		    tools.glColor4Hex(0xFF000000);
		    tools.renderLineTo(x + 0.5D, y - 1.5D, z - 0.5D, 3.5F);
		}
		tools.glColor4Hex(0xFFFFAA00);
		tools.renderLineTo(x + 0.5D, y - 1.5D, z - 0.5D, 1.5F);
		GL11.glPopMatrix();
	    }

	    if ((Boolean) getSetting(1))
		tools.renderNameTag(name.split(":")[0], x + 0.5D, y - 1.0D,
			z - 0.5D, location.distanceTo(getClient().getPlayer()
				.getLocation()), true, 0xFFFFFFFF);
	}
    }

    public boolean isWaypoint(final String name) {
	for (ArrayHelper<String, Location> waypoints : getLoadedWaypoints())
	    if (waypoints.getKey().equalsIgnoreCase(
		    StringUtils.stripControlCodes(name)))
		return true;
	return false;
    }

    public void removeWaypointByName(final String name) {
	for (ArrayHelper<String, Location> waypoints : getLoadedWaypoints())
	    if (waypoints.getKey().equalsIgnoreCase(
		    StringUtils.stripControlCodes(name)))
		getLoadedWaypoints().remove(waypoints);
    }

    public ArrayHelper<String, Location> getWaypointByName(final String name) {
	for (ArrayHelper<String, Location> waypoints : getLoadedWaypoints())
	    if (waypoints.getKey().equalsIgnoreCase(
		    StringUtils.stripControlCodes(name)))
		return waypoints;
	return null;
    }

    public void loadWaypoints() {
	File file = new File(getClient().getDirectory(), "Waypoints.gcfg");
	String line = "";
	if (file.exists()) {
	    BufferedReader fileReader;
	    try {
		fileReader = new BufferedReader(new FileReader(file));
		try {
		    while ((line = fileReader.readLine()) != null) {
			String[] split = line.split(":");
			String name = split[0] + ":" + split[1];
			String location = split[2];
			getLoadedWaypoints().add(
				new ArrayHelper(name, Location
					.parseLocation(location)));
			getClient().getLogger().log(
				Level.INFO,
				"Loaded the waypoints '" + name + ":"
					+ location + "'.");
		    }
		    fileReader.close();
		} catch (Exception exception) {
		    exception.printStackTrace();
		}
	    } catch (FileNotFoundException e) {
		e.printStackTrace();
	    }
	} else
	    saveWaypoints();
    }

    public void saveWaypoints() {
	try {
	    File file = new File(getClient().getDirectory(), "Waypoints.gcfg");
	    PrintWriter fileWriter = new PrintWriter(file);
	    for (ArrayHelper<String, Location> waypoints : getLoadedWaypoints())
		fileWriter.println(waypoints.getKey() + ":"
			+ waypoints.getValue().toString());
	    fileWriter.close();
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
    }

    public static List<ArrayHelper<String, Location>> getLoadedWaypoints() {
	return LOADED_WAYPOINTS;
    }
}