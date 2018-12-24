package se.proxus.eien.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventSender {

	private final Map<EventListener, List<Method>> handlers = new HashMap<EventListener, List<Method>>();

	private final Class<? extends Event> eventListenerClass;

	public EventSender(final Class<? extends Event> eventListenerClass) {
		this.eventListenerClass = eventListenerClass;
	}

	public synchronized void addHandler(final EventListener listener,
			final Method method) {
		List<Method> methods = handlers.get(listener);
		if (methods == null) {
			methods = new ArrayList<Method>();
			handlers.put(listener, methods);
		}
		methods.add(method);
	}

	public synchronized void unregisterListener(
			final EventListener eventListener) {
		handlers.remove(eventListener);
	}

	public synchronized List<EventListener> getListeners() {
		return new ArrayList<EventListener>(handlers.keySet());
	}

	public synchronized void sendEvent(final Event event) {
		Class<?> eventClass = event.getClass();
		if (!eventClass.isAssignableFrom(eventListenerClass)) {
			return;
		}
		for (EventListener listener : new ArrayList<EventListener>(
				handlers.keySet())) {
			List<Method> methods = handlers.get(listener);
			if (methods == null) {
				continue;
			}
			for (Method method : methods) {
				try {
					method.invoke(listener, event);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}

	public synchronized Class<? extends Event> getListenerEventClass() {
		return eventListenerClass;
	}
}