package se.proxus.mods;

public class ModValue {

    private String name;
    private double max;
    private Object value;
    private int id;
    private Mod mod;

    public ModValue(final String name, final Object value, final double max) {
	setName(name);
	setValue(value);
	setMax(max);
    }

    public String getName() {
	return name;
    }

    public void setName(final String name) {
	this.name = name;
    }

    public double getMax() {
	return max;
    }

    public void setMax(final double max) {
	this.max = max;
    }

    public Object getValue() {
	return value;
    }

    public void setValue(final Object value) {
	this.value = value;
    }

    public int getId() {
	return id;
    }

    public ModValue setId(final int id) {
	this.id = id;
	return this;
    }

    public Mod getMod() {
	return mod;
    }

    public ModValue setMod(final Mod mod) {
	this.mod = mod;
	return this;
    }
}