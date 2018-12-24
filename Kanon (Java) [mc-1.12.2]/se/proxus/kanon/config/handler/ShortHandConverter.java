package se.proxus.kanon.config.handler;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public final class ShortHandConverter {

    private final static BiMap<String, Class<?>> REGISTERED_SHORT_HANDS = HashBiMap.create();

    static {
        // Numbers
        REGISTERED_SHORT_HANDS.put("integer", Integer.class);
        REGISTERED_SHORT_HANDS.put("byte", Byte.class);
        REGISTERED_SHORT_HANDS.put("short", Short.class);
        REGISTERED_SHORT_HANDS.put("long", Long.class);
        REGISTERED_SHORT_HANDS.put("double", Double.class);
        REGISTERED_SHORT_HANDS.put("float", Float.class);

        // Collections
        REGISTERED_SHORT_HANDS.put("collection", Collection.class);
        REGISTERED_SHORT_HANDS.put("set", Set.class);
        // Lists
        REGISTERED_SHORT_HANDS.put("events", List.class);
        REGISTERED_SHORT_HANDS.put("arrayList", ArrayList.class);
        REGISTERED_SHORT_HANDS.put("linkedList", LinkedList.class);
        REGISTERED_SHORT_HANDS.put("vector", Vector.class);
        //Maps
        REGISTERED_SHORT_HANDS.put("map", Map.class);
        REGISTERED_SHORT_HANDS.put("hashMap", HashMap.class);
        REGISTERED_SHORT_HANDS.put("linkedHashMap", LinkedHashMap.class);
        REGISTERED_SHORT_HANDS.put("treeMap", TreeMap.class);

        // Misc
        REGISTERED_SHORT_HANDS.put("enum", Enum.class);
        REGISTERED_SHORT_HANDS.put("boolean", Boolean.class);
        REGISTERED_SHORT_HANDS.put("string", String.class);

        //Arrays
        //Numbers
        REGISTERED_SHORT_HANDS.put("integerArray", Integer[].class);
        REGISTERED_SHORT_HANDS.put("longArray", Long[].class);
        REGISTERED_SHORT_HANDS.put("shortArray", Short[].class);
        REGISTERED_SHORT_HANDS.put("byteArray", Byte[].class);
        REGISTERED_SHORT_HANDS.put("doubleArray", Double[].class);
        REGISTERED_SHORT_HANDS.put("floatArray", Float[].class);

        //Misc
        REGISTERED_SHORT_HANDS.put("array", Object[].class);
        REGISTERED_SHORT_HANDS.put("stringArray", String[].class);
        REGISTERED_SHORT_HANDS.put("booleanArray", Boolean[].class);
    }

    public static Class<?> parse(final String shortHand) throws ClassNotFoundException {
        if (StringUtils.countMatches(shortHand, '.') < 2)
            return REGISTERED_SHORT_HANDS.get(shortHand);
        
        return Class.forName(shortHand);
    }

    public static String get(final Class<?> cls) {
        if (cls.isArray() || List.class.isAssignableFrom(cls) || Map.class.isAssignableFrom(cls))
            return REGISTERED_SHORT_HANDS.inverse().get(cls);
        
        return cls.getName();
    }

    public static class ShortHandParseException extends RuntimeException {

        public ShortHandParseException() {
            super();
        }

        public ShortHandParseException(final String message) {
            super(message);
        }

        public ShortHandParseException(final String message, final Throwable cause) {
            super(message, cause);
        }

        public ShortHandParseException(final Throwable cause) {
            super(cause);
        }
    }
}