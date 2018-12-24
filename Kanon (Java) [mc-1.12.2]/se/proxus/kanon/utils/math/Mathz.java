package se.proxus.kanon.utils.math;

import com.google.common.base.Defaults;
import org.apache.commons.lang3.StringUtils;
import se.proxus.kanon.config.Entry;

import java.util.Objects;

public final class Mathz {

    public static boolean isInRange(final Number value, final Number floor, final Number ceiling) {
        if (Numberz.isDecimalInstance(value)) {
            return isInRange(value.doubleValue(), floor.doubleValue(), ceiling.doubleValue()); // float value og
        } else {
            return isInRange(value.intValue(), floor.intValue(), ceiling.intValue());
        }
    }

    public static boolean isInRange(final double value, final double floor, final double ceiling) {
        return floor <= value && ceiling >= value;
    }

    public static boolean isInRange(final int value, final int floor, final int ceiling) {
        return floor <= value && ceiling >= value;
    }
    
    public static Object truncate(final Object rawValue, final Number floor, final Number ceiling) {
        if (rawValue instanceof String) {
            final String value = (String) rawValue;
            final int min = floor.intValue();
            final int max = ceiling.intValue();
            
            if (max > 0 && value.length() > max) {
                return StringUtils.substring(value, min, max);
            } else {
                return value;
            }
        } else if (rawValue instanceof Number) {
            if (Numberz.isDecimalInstance(rawValue)) {
                final Double value = (Double) rawValue;
                final double min = floor.doubleValue();
                final double max = ceiling.doubleValue();
                
                if (max > 0 && value > max) {
                    return max;
                }
                
                if (value < min) {
                    return min;
                }
            } else {
                final int value = (Integer) rawValue;
                final int min = floor.intValue();
                final int max = ceiling.intValue();
                
                if (max > 0 && value > max) {
                    return max;
                }
                
                if (value < min) {
                    return min;
                }
            }
        }
        
        return null;
    }
    
    @SuppressWarnings("unchecked")
    public static Object truncate(final Entry entry, final Object newValue) {
        if (Objects.isNull(entry.getRange()))
            return Defaults.defaultValue(newValue.getClass()); // Maybe make this return a null instead?
        
        final NumberRange range = entry.getRange();
        
        if (entry.getValue() instanceof String) {
            final String value = (String) newValue;
            
            if (range.isAfter(value.length()))
                return entry.getDefaultValue();
            
            if (range.isBefore(value.length())) {
                return value.substring(range.getMinimum().intValue(), range.getMaximum().intValue());
            } else {
                return value;
            }
        } else if (entry.getValue() instanceof Number) {
            final Number originalNumber = entry.getValue(Number.class);
            final Number number = (Number) newValue;
            
            if (originalNumber instanceof Byte) {
                final byte value = number.byteValue();
    
                if (range.isBefore(value))
                    return range.getMaximum().byteValue();
    
                if (range.isAfter(value))
                    return range.getMinimum().byteValue();
                
                if (range.contains(value))
                    return value;
            } else if (originalNumber instanceof Short) {
                final short value = number.shortValue();
    
                if (range.isBefore(value))
                    return range.getMaximum().shortValue();
    
                if (range.isAfter(value))
                    return range.getMinimum().shortValue();
    
                if (range.contains(value))
                    return value;
            } else if (originalNumber instanceof Integer) {
                final int value = number.intValue();
        
                if (range.isBefore(value))
                    return range.getMaximum().intValue();
        
                if (range.isAfter(value))
                    return range.getMinimum().intValue();
    
                if (range.contains(value))
                    return value;
            } else if (originalNumber instanceof Long) {
                final long value = number.longValue();
    
                if (range.isBefore(value))
                    return range.getMaximum().longValue();
    
                if (range.isAfter(value))
                    return range.getMinimum().longValue();
                
                if (range.contains(value))
                    return value;
            } else if (originalNumber instanceof Float) {
                final float value = number.floatValue();
    
                if (range.isBefore(value))
                    return range.getMaximum().floatValue();
    
                if (range.isAfter(value))
                    return range.getMinimum().floatValue();
                
                if (range.contains(value))
                    return value;
            } else if (originalNumber instanceof Double) {
                final double value = number.doubleValue();
    
                if (range.isBefore(value))
                    return range.getMaximum().doubleValue();
    
                if (range.isAfter(value))
                    return range.getMinimum().doubleValue();
    
                if (range.contains(value))
                    return value;
            }
        }
        
        return null;
    }
    
}
