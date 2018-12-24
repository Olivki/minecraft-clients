package se.proxus.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import se.proxus.Gallium;

public class EventManager {

	private static final List<EventSender> EVENT_SENDERS = new LinkedList<EventSender>();
	private Gallium client;

	public EventManager(final Gallium client) {
		setClient(client);
	}

	public synchronized void registerListener(final EventListener eventListener) {
		Class<? extends EventListener> eventListenerClass = eventListener
				.getClass();

		for (Method method : eventListenerClass.getMethods()) {
			if (method.getAnnotation(EventHandler.class) == null)
				continue;
			if (!method.isAccessible())
				method.setAccessible(true);
			if (method.getParameterTypes().length != 1)
				throw new IllegalArgumentException("Method "
						+ method.toString() + " in class "
						+ eventListenerClass.getName()
						+ " does not have the proper amount of parameters.");
			Class<? extends Event> eventClass = method.getParameterTypes()[0]
					.asSubclass(Event.class);
			boolean senderExists = false;
			for (EventSender sender : EVENT_SENDERS)
				if (eventClass.isAssignableFrom(sender.getListenerEventClass())) {
					sender.addHandler(eventListener, method);
					getClient().getLogger().log(
							Level.INFO,
							"Method: " + method.getName()
							+ " contains an event.");
					senderExists = true;
				}

			if (!senderExists) {
				EventSender sender = new EventSender(eventClass);
				EVENT_SENDERS.add(sender);
				sender.addHandler(eventListener, method);
			}
		}
	}

	public synchronized void unregisterListener(
			final EventListener eventListener) {
		for (EventSender sender : EVENT_SENDERS)
			sender.unregisterListener(eventListener);
	}

	public synchronized void clearListeners() {
		EVENT_SENDERS.clear();
	}

	public synchronized Event sendEvent(final Event event) {
		try {
			for (EventSender sender : EVENT_SENDERS) {
				Class<? extends Event> eventClass = sender
						.getListenerEventClass();
				if (eventClass.isInstance(event)
						&& !sender.getListenerEventClass().equals(event))
					sender.sendEvent(event);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return event;
	}

	public synchronized List<EventListener> getListeners(
			final Class<? extends Event> eventClass) {
		for (EventSender sender : EVENT_SENDERS)
			if (eventClass.isAssignableFrom(sender.getListenerEventClass()))
				return sender.getListeners();
		return new ArrayList<EventListener>();
	}

	public Gallium getClient() {
		return client;
	}

	public void setClient(final Gallium client) {
		this.client = client;
	}
}