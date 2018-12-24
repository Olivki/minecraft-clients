package se.proxus.mods.list.gui;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.FontRenderer;
import se.proxus.Gallium;
import se.proxus.commands.Command;
import se.proxus.commands.CommandType;
import se.proxus.events.EventHandler;
import se.proxus.events.list.client.EventRendered2D;
import se.proxus.mods.Mod;
import se.proxus.mods.ModCategory;
import se.proxus.tools.Colours;
import se.proxus.tools.Location;
import se.proxus.tools.Tools;

public class Notifications extends Mod {

    private final static List<Notification> ACTIVE_NOTIFICATIONS = new LinkedList<Notification>();
    private final static List<Notification> STORED_NOTIFICATIONS = new LinkedList<Notification>();

    public Notifications(final Gallium client) {
	super("Notifications", ModCategory.GUI, true, client);
    }

    @Override
    public void init() {
	setDescription("Some notification stuff.");
	setState(true);
	getClient().getCommands().registerCommand(
		new Command("notifications",
			".notifications <add> [duration] [name]",
			"Adds a notification.", getClient(),
			CommandType.LISTHANDLER) {
		    @Override
		    public void onCommand(final String message,
			    final String... args) {
			if (args[0].equalsIgnoreCase("add")) {
			    String name = message.substring(getCommand()
				    .length()
				    + args[0].length()
				    + args[1].length() + 3);
			    addNotification(new Notification(name.replace("&",
				    "" + Colours.COLOUR_SYMBOL), name, Integer
				    .parseInt(args[1])));
			    getClient().getPlayer().addMessage(
				    Colours.GREY + "Added '"
					    + Colours.CLIENT_COLOUR + name
					    + Colours.GREY + "' Duration '"
					    + Colours.CLIENT_COLOUR
					    + Integer.parseInt(args[1])
					    + Colours.GREY + "'.");
			}
		    }
		});
    }

    @Override
    public void onEnable() {
	getClient().getEvents().registerListener(this);
    }

    @Override
    public void onDisable() {
	getClient().getEvents().unregisterListener(this);
	getActiveNotifications().clear();
    }

    @EventHandler
    public void onEventRendered2D(final EventRendered2D event) {
	FontRenderer font = event.getFont();
	for (int size = 0; size < getActiveNotifications().size(); size++) {
	    Notification notification = getActiveNotifications().get(size);
	    int x = getClient().getResolution().getScaledWidth()
		    - event.getFont().getStringWidth(notification.getName())
		    - 2;
	    int y = getClient().getResolution().getScaledHeight() - 10;

	    if (notification.getTicks() < Tools.secondsToTicks(notification
		    .getDuration())) {
		notification.setTicks(notification.getTicks() + 1);
		font.drawStringWithShadow(notification.getName(), x, y - size
			* 9, 0xFFFFFFFF);
	    } else if (notification.getTicks() >= Tools
		    .secondsToTicks(notification.getDuration())) {
		getActiveNotifications().remove(notification);
		Tools.playSound("random.click", 1.0F, 2.0F);
	    }
	}

	for (Object o : getClient().getMinecraft().theWorld.loadedEntityList)
	    if (o instanceof EntityLiving) {
		EntityLiving entity = (EntityLiving) o;
		double distance = getClient().getPlayer().getLocation()
			.distanceTo(Location.entityToLocation(entity));
		Notification entityNotification = new Notification(
			entity.getEntityName() + " has entered attack range.",
			entity.entityId, 4);

		if (distance <= 3.5F
			&& entity != getClient().getMinecraft().thePlayer)
		    addNotification(entityNotification);
	    }
    }

    public void addNotification(final Notification notification) {
	if (!isIdLoaded(notification.getId())) {
	    getActiveNotifications().add(notification);
	    getStoredNotifications().add(notification);
	    Tools.playSound("random.click", 1.0F, 1.0F);
	}
    }

    public boolean isIdLoaded(final Object id) {
	for (Notification notification : getActiveNotifications())
	    if (notification.getId().equals(id))
		return true;
	return false;
    }

    public static List<Notification> getActiveNotifications() {
	return ACTIVE_NOTIFICATIONS;
    }

    public static List<Notification> getStoredNotifications() {
	return STORED_NOTIFICATIONS;
    }

    class Notification {

	private String name;
	private int ticks;
	private int duration;
	private Object id;

	public Notification(final String name, final Object id,
		final int duration) {
	    setName(name);
	    setId(id);
	    setDuration(duration);
	}

	public String getName() {
	    return name;
	}

	public void setName(final String name) {
	    this.name = name;
	}

	public Object getId() {
	    return id;
	}

	public void setId(final Object id) {
	    this.id = id;
	}

	public int getTicks() {
	    return ticks;
	}

	public void setTicks(final int ticks) {
	    this.ticks = ticks;
	}

	public int getDuration() {
	    return duration;
	}

	public void setDuration(final int duration) {
	    this.duration = duration;
	}
    }
}