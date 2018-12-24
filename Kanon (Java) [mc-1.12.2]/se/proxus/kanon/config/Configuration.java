package se.proxus.kanon.config;

import lombok.Getter;
import se.proxus.kanon.Kanon;
import se.proxus.kanon.config.handler.Handler;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Configuration {
    
    private final Map<String, Entry> loadedEntries = new LinkedHashMap<>();
    @Getter private final String name;
    @Getter private final File directory;
    @Getter private final File file;
    @Getter private final Handler handler;
    
    public Configuration(final String name, final File directory) {
        this.name = name;
        this.directory = directory;
        
        if (!directory.exists())
            directory.mkdirs();
        
        file = new File(directory, name + ".xml");
        handler = new Handler(this, file);
    }
    
    /**
     * A method for adding a Entry with the given parameters to the Map.
     *
     * @param key
     *            The key to put it under in the name. (This will also be used
     *            as a sort of "name" for it.)
     * @param value
     *            The value that the entry should store.
     * @return The newly registered Entry instance. If there's is already a
     *         entry registered under the given key, it will return null.
     */
    public final Entry addEntry(String key, final Object value, final boolean... log) {
        key = key.replace("_", " ");
        
        if (!hasEntry(key)) {
            final Entry entry = new Entry(this, key, value);
            
            if ((log.length <= 0 || log[0])) {
            
            }
            
            loadedEntries.put(key, entry);
            
            return entry;
        } else {
            throw new NullPointerException(String.format("\"%s\" is already a registered key.", key));
        }
    }
    
    public final Number decrease(final Entry entry, final Number amount) {
        return entry.decrease(amount);
    }
    
    public final Number decrease(final String key, final Number amount) {
        return decrease(getEntry(key), amount);
    }
    
    public final Number increase(final Entry entry, final Number amount) {
        return entry.increase(amount);
    }
    
    public final Number increase(final String key, final Number amount) {
        return increase(getEntry(key), amount);
    }
    
    /**
     * A wrapper method to be used if you have the a instance of the Entry and
     * not just the name.
     *
     * @param entry
     *            The instance of the Entry to modify the value of.
     * @param parsedValue
     *            The new value to be parsed.
     */
    public final void parse(final Entry entry, final Object parsedValue) {
        final Object value = entry.getValue();
    
        if (value instanceof Double) {
            setValue(entry, Double.parseDouble((String) parsedValue));
        } else if (value instanceof Float) {
            setValue(entry, Float.parseFloat((String) parsedValue));
        } else if (value instanceof Integer) {
            setValue(entry, Integer.parseInt((String) parsedValue));
        } else if (value instanceof Long) {
            setValue(entry, Long.parseLong((String) parsedValue));
        } else if (value instanceof Byte) {
            setValue(entry, Byte.parseByte((String) parsedValue));
        } else if (value instanceof Short) {
            setValue(entry, Short.parseShort((String) parsedValue));
        }
    }
    
    /**
     * To be used when accepting user inputs via text, to make sure that the set
     * value will be the correct type.
     *
     * @param key
     *            The key of the wanted entry.
     * @param parsedValue
     *            The value to parse.
     */
    public final void parse(final String key, final Object parsedValue) {
        parse(getEntry(key), parsedValue);
    }
    
    /**
     * Attempts to remove the Entry stored under the given key.
     *
     * @param key
     *            The key to query with.
     * @return The instance of the removed Entry. If no entry exists under the
     *         given key, it will return null.
     */
    public final Entry removeEntry(final String key) {
        if (hasEntry(key)) {
            return loadedEntries.remove(key);
        } else {
            return null;
        }
    }
    
    public final boolean toggle(final Entry entry, final boolean... log) {
        final boolean toggle = entry.toggle(log);
    
        handler.notify(entry);
    
        return toggle;
    }
    
    public final boolean toggle(final String key, final boolean... log) {
        return toggle(getEntry(key), log);
    }
    
    public final Entry[] getEntries() {
        return loadedEntries.values().toArray(new Entry[0]);
    }
    
    /**
     * A wrapper method for the get method in the Map.
     *
     * @param key
     *            The key to query with.
     * @return The instance of the entry stored under the given key. If there is
     *         none, it will return null.
     */
    public final Entry getEntry(final String key) {
        if (loadedEntries.get(key) == null) {
            Kanon.LOGGER.fatalf("%s[%s] IS NOT A REGISTERED KEY!", name.toUpperCase(), key.toUpperCase());
        }
        
        return loadedEntries.get(key);
    }
    
    public final boolean isImmutable(final String key) {
        if (hasEntry(key)) {
            return getEntry(key).isMutable();
        } else {
            return false;
        }
    }
    
    /**
     * A wrapper method for the containsValue method in the Map.
     *
     * @param entry
     *            The value to query with.
     * @return Whether or not the Map contains an instance of the given Entry.
     */
    public final boolean hasEntry(final Entry entry) {
        return loadedEntries.containsValue(entry);
    }
    
    /**
     * A wrapper method for the containsKey method in the Map.
     *
     * @param key
     *            The key to query with.
     * @return Whether or not the Map contains a entry stored under the given
     *         key.
     */
    public final boolean hasEntry(final String key) {
        return loadedEntries.containsKey(key);
    }
    
    private Kanon getKanon() {
        return Kanon.getInstance();
    }
    
    /**
     * A wrapper method for the getValue method stored in the Entry instance
     * stored under the given key.
     *
     * @param key
     *            The key to query with.
     * @return The value stored within the Entry instance. If there is no Entry
     *         stored under the given key, it will return null.
     */
    public final Object getValue(final String key) {
        return getEntry(key).getValue();
    }
    
    public final void setValue(final Entry entry, final Object value, final boolean... log) {
        setValue(entry.getKey(), value, log);
    }
    
    /**
     * Attempts to set the value stored within the Entry instance under the
     * given key. It will also notify the Handler to check if it can auto-save.
     *
     * @param key
     *            The key to query with.
     * @param value
     *            The value to store.
     */
    public final boolean setValue(final String key, final Object value, final boolean... log) {
        final Entry entry = getEntry(key);
        
        final boolean success = entry.setValue(value, log);
        handler.notify(entry);
        return success;
    }
    
    // -- Numbers --
    public final byte getByte(final Entry entry) {
        return entry.getValue(Byte.class);
    }
    
    public final byte getByte(final String key) {
        return getByte(getEntry(key));
    }
    
    public final short getShort(final Entry entry) {
        return entry.getValue(Short.class);
    }
    
    public final short getShort(final String key) {
        return getShort(getEntry(key));
    }
    
    public final int getInteger(final Entry entry) {
        return entry.getValue(Integer.class);
    }
    
    public final int getInteger(final String key) {
        return getInteger(getEntry(key));
    }
    
    public final long getLong(final Entry entry) {
        return entry.getValue(Long.class);
    }
    
    public final long getLong(final String key) {
        return getLong(getEntry(key));
    }
    
    public final float getFloat(final Entry entry) {
        return entry.getValue(Float.class);
    }
    
    public final float getFloat(final String key) {
        return getFloat(getEntry(key));
    }
    
    public final double getDouble(final Entry entry) {
        return entry.getValue(Double.class);
    }
    
    public final double getDouble(final String key) {
        return getDouble(getEntry(key));
    }
    
    
    // -- Other Primitives --
    public final String getString(final Entry entry) {
        return entry.getValue(String.class);
    }
    
    public final String getString(final String key) {
        return getString(getEntry(key));
    }
    
    public final boolean getBoolean(final Entry entry) {
        return entry.getValue(Boolean.class);
    }
    
    public final boolean getBoolean(final String key) {
        return getBoolean(getEntry(key));
    }
    
    public final Enum getEnum(final Entry entry) {
        return entry.getValue(Enum.class);
    }
    
    public final Enum getEnum(final String key) {
        return getEnum(getEntry(key));
    }
    
    
    // -- Collections --
    public final <V> V[] getArray(final Entry entry, final Class<V> cls) {
        return entry.getArray(cls);
    }
    
    public final <V> V[] getArray(final String key, final Class<V> cls) {
        return getArray(getEntry(key), cls);
    }
    
    public final <V> List<V> getList(final Entry entry, final Class<V> valueClass) {
        return entry.getList(valueClass);
    }
    
    public final <V> List<V> getList(final String key, final Class<V> valueClass) {
        return getList(getEntry(key), valueClass);
    }
    
    public final <K, V> Map<K, V> getMap(final Entry entry, final Class<K> keyClass, final Class<V> valueClass) {
        return entry.getMap(keyClass, valueClass);
    }
    
    public final <K, V> Map<K, V> getMap(final String key, final Class<K> keyClass, final Class<V> valueClass) {
        return getMap(getEntry(key), keyClass, valueClass);
    }
    
    public final <T> T getEnum(final Entry entry, final Class<T> cls) {
        return entry.getValue(cls);
    }
    
    public final <T> T getEnum(final String key, final Class<T> cls) {
        return getEnum(getEntry(key), cls);
    }
    
    @Override
    public String toString() {
        return String.format("Configuration[%s, \"%s\"]", name, directory.getAbsolutePath());
    }
    
}
