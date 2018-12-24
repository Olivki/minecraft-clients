package se.proxus.kanon.event4j;

public final class EventWrapper {

    private final static EventBus<Object, Object> EVENT_BUS = EventBus.build();

    public static void registerListener(final Object listener) {
        getEventBus().register(listener);
    }

    public static void unregisterListener(final Object listener) {
        getEventBus().unregister(listener);
    }

    public static void fire(final Object event) {
        getEventBus().fire(event);
    }

    public static EventBus<Object, Object> getEventBus() {
        return EVENT_BUS;
    }
}