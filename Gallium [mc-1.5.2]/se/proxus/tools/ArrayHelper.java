package se.proxus.tools;

/**
 * Acts kind of like a HashMap, but simpler.
 * 
 * @author Oliver
 * @param <K>
 *            The key object.
 * @param <V>
 *            The value object.
 */
public class ArrayHelper<K, V> {

    private K key;
    private V value;

    public ArrayHelper(final K key, final V value) {
	setKey(key);
	setValue(value);
    }

    public K getKey() {
	return key;
    }

    public void setKey(final K key) {
	this.key = key;
    }

    public V getValue() {
	return value;
    }

    public void setValue(final V value) {
	this.value = value;
    }
}