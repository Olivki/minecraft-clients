package se.proxus.kanon.config;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import se.proxus.kanon.Kanon;
import se.proxus.kanon.utils.math.Mathz;
import se.proxus.kanon.utils.math.NumberRange;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@Accessors(chain = true)
@ToString
public final class Entry {

    private final Configuration parent;
    private final String key;
    private String description;
    private final Object defaultValue;
    private Object $value;
    private boolean mutable;
    private boolean commandMutable;
    private final Class[] genericType = new Class[4];
    @Nullable private NumberRange $range;
    
    public Entry(final Configuration parent, final String key, final Object value) {
        this.parent = parent;
        this.key = key;
        this.$value = value;
        this.defaultValue = value;
        this.description = "EMPTY";
        this.mutable = true;
        this.commandMutable = true;
    }
    
    final Number decrease(final Number amount) {
        if ($value instanceof Double) {
            setValue(getValue(Double.class) - amount.doubleValue());
        } else if ($value instanceof Float) {
            setValue(getValue(Float.class) - amount.floatValue());
        } else if ($value instanceof Integer) {
            setValue(getValue(Integer.class) - amount.intValue());
        } else if ($value instanceof Long) {
            setValue(getValue(Long.class) - amount.longValue());
        }
        
        return -1;
    }
    
    final Number increase(final Number amount) {
        if ($value instanceof Double) {
            setValue(getValue(Double.class) + amount.doubleValue());
        } else if ($value instanceof Float) {
            setValue(getValue(Float.class) + amount.floatValue());
        } else if ($value instanceof Integer) {
            setValue(getValue(Integer.class) + amount.intValue());
        } else if ($value instanceof Long) {
            setValue(getValue(Long.class) + amount.longValue());
        }
        
        return -1;
    }
    
    public final Entry setGeneric(@NotNull final Class<?>... generics) {
        System.arraycopy(generics, 0, genericType, 0, generics.length);
        
        return this;
    }
    
    public final boolean toggle(final boolean... log) {
        return setValue(!getValue(Boolean.class), log);
    }
    
    @SuppressWarnings("unchecked")
    public final <V> V getValue(final Class<V> valueClass) {
        return (V) $value;
    }
    
    public final Object getValue() {
        return $value;
    }
    
    @SuppressWarnings("unchecked")
    public final <V> V[] getArray(final Class<V> cls) {
        if ($value instanceof List)
            return (V[]) getList(cls).toArray();
        else if ($value instanceof Map)
            return (V[]) getValue(Map.class).values().toArray();
        else
            return (V[]) $value;
    }
    
    public final Object[] getArray() {
        if ($value instanceof List)
            return getValue(List.class).toArray();
        else if ($value instanceof Map)
            return getValue(Map.class).values().toArray();
        else
            return (Object[]) $value;
    }
    
    @SuppressWarnings("unchecked")
    public final <V> List<V> getList(final Class<V> cls) {
        return (List<V>) $value;
    }
    
    @SuppressWarnings("unchecked")
    public final <K, V> Map<K, V> getMap(final Class<K> keyClass, final Class<V> valueClass) {
        return (Map<K, V>) $value;
    }
    
    public final boolean isEmpty() {
        if ($value instanceof List) {
            return getValue(List.class).isEmpty();
        } else if ($value instanceof Map) {
            return getValue(Map.class).isEmpty();
        } else if ($value.getClass().isArray()) {
            return getArray().length <= 0;
        }
        
        return true;
    }
    
    public final boolean hasGenerics() {
        return Objects.nonNull(genericType[0]);
    }
    
    public final boolean setValue(final Object value, final boolean... log) {
        if (value.equals(this.$value)) {
            return false;
        }
        
        final Object truncatedValue = Mathz.truncate(this, value);
        
        if (Objects.isNull(truncatedValue)) {
            if ((log.length <= 0 || log[0])) {
                if (this.$value instanceof String) {
                    Kanon.LOGGER.infof("EntryChange[{%s[{%s[\"%s\", \"%s\"]}]}]",
                                                          parent.getName(), key, this.$value, value);
                } else {
                    Kanon.LOGGER.infof("EntryChange[{%s[{%s[%s, %s]}]}]",
                                                          parent.getName(), key, this.$value, value);
                }
            }
            
            this.$value = value;
            
            return true;
        } else {
            if ((log.length <= 0 || log[0])) {
                if (this.$value instanceof String) {
                    Kanon.LOGGER.infof("EntryChange[{%s[{%s[\"%s\", \"%s\"]}]}]",
                                                          parent.getName(), key, this.$value, truncatedValue);
                } else {
                    Kanon.LOGGER.infof("EntryChange[{%s[{%s[%s, %s]}]}]",
                                                          parent.getName(), key, this.$value, truncatedValue);
                }
            }
            
            this.$value = truncatedValue;
            
            return true;
        }
    }
    
    @SuppressWarnings("unchecked")
    public final Entry setRange(final Number floor, final Number ceiling) {
        if ($value instanceof Number) {
            $range = NumberRange.between(floor, ceiling);
        } else if ($value instanceof String
                   || $value instanceof List
                   || $value instanceof Map) {
            $range = NumberRange.between(floor.intValue(), ceiling.intValue());
        }
        
        if ($value.getClass().isArray())
            $range = NumberRange.between(floor.intValue(), ceiling.intValue());
        
        return this;
    }
    
    public final NumberRange getRange() {
        return $range;
    }
    
    // Backwards compatibility
    @Deprecated
    @SuppressWarnings("ConstantConditions")
    public final Number getMin() {
        if (Objects.nonNull($range)) {
            return getRange().getMinimum();
        } else {
            return -1;
        }
    }
    
    @Deprecated
    @SuppressWarnings("ConstantConditions")
    public final Number getMax() {
        if (Objects.nonNull($range)) {
            return getRange().getMaximum();
        } else {
            return -1;
        }
    }
}
