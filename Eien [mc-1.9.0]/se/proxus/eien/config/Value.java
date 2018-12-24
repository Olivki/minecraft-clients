package se.proxus.eien.config;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Value {

	private Object value;
	private String name;
	private String description;
	private double max;
	private boolean editable;

	public Value(final String name, final Object value, final String description, final double max,
			final boolean editable) {
		setName(name);
		setValue(value);
		setDescription(description);
		setMax(max);
		setEditable(editable);
	}

	public Object getValue() {
		return value;
	}

	public void setValue(final Object value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public double getMax() {
		return max;
	}

	public void setMax(final double max) {
		this.max = max;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(final boolean editable) {
		this.editable = editable;
	}

	/** Bunch of getters to make the code look nicer. **/

	public String getString() {
		return (String) getValue();
	}

	public boolean getBoolean() {
		return (Boolean) getValue();
	}

	public int getInteger() {
		return (Integer) getValue();
	}

	public long getLong() {
		return (Long) getValue();
	}

	public short getShort() {
		return (Short) getValue();
	}

	public byte getByte() {
		return (Byte) getValue();
	}

	public double getDouble() {
		return (Double) getValue();
	}

	public float getFloat() {
		return (Float) getValue();
	}

	public BigInteger getBigInteger() {
		return (BigInteger) getValue();
	}

	public BigDecimal getBigDecimal() {
		return (BigDecimal) getValue();
	}

	public Date getDate() {
		return (Date) getValue();
	}

	public Calendar getCalendar() {
		return (Calendar) getValue();
	}

	public URL getURL() {
		return (URL) getValue();
	}

	public Color getColor() {
		return (Color) getValue();
	}

	public List<?> getList() {
		return (List<?>) getValue();
	}
}