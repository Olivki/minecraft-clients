package se.proxus.kanon.utils.math;

import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.Range;

@Getter
@ToString
public final class NumberRange {
    
    private final Number minimum;
    private final Number maximum;
    private Range $range;
    
    private NumberRange(final Number minimum, final Number maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    
        setRange(minimum, maximum);
    }
    
    public static NumberRange is(final Number element) {
        return new NumberRange(element, element);
    }
    
    public static NumberRange between(final Number minimum, final Number maximum) {
        return new NumberRange(minimum, maximum);
    }
    
    /**
     * <p>Checks whether the specified element occurs within this range.</p>
     *
     * @param element  the element to check for, null returns false
     * @return true if the specified element occurs within this range
     */
    @SuppressWarnings("unchecked")
    public boolean contains(final Number element) {
        if (minimum instanceof Byte) {
            return $range.contains(element.byteValue());
        } else if (minimum instanceof Short) {
            return $range.contains(element.shortValue());
        } else if (minimum instanceof Integer) {
            return $range.contains(element.intValue());
        } else if (minimum instanceof Long) {
            return $range.contains(element.longValue());
        } else if (minimum instanceof Float) {
            return $range.contains(element.floatValue());
        } else if (minimum instanceof Double) {
            return $range.contains(element.doubleValue());
        }
        
        return false;
    }
    
    /**
     * <p>Checks whether this range is after the specified element.</p>
     *
     * @param element  the element to check for, null returns false
     * @return true if this range is entirely after the specified element
     */
    @SuppressWarnings("unchecked")
    public boolean isAfter(final Number element) {
        if (minimum instanceof Byte) {
            return $range.isAfter(element.byteValue());
        } else if (minimum instanceof Short) {
            return $range.isAfter(element.shortValue());
        } else if (minimum instanceof Integer) {
            return $range.isAfter(element.intValue());
        } else if (minimum instanceof Long) {
            return $range.isAfter(element.longValue());
        } else if (minimum instanceof Float) {
            return $range.isAfter(element.floatValue());
        } else if (minimum instanceof Double) {
            return $range.isAfter(element.doubleValue());
        }
        
        return false;
    }
    
    /**
     * <p>Checks whether this range starts with the specified element.</p>
     *
     * @param element  the element to check for, null returns false
     * @return true if the specified element occurs within this range
     */
    @SuppressWarnings("unchecked")
    public boolean isStartedBy(final Number element) {
        if (minimum instanceof Byte) {
            return $range.isStartedBy(element.byteValue());
        } else if (minimum instanceof Short) {
            return $range.isStartedBy(element.shortValue());
        } else if (minimum instanceof Integer) {
            return $range.isStartedBy(element.intValue());
        } else if (minimum instanceof Long) {
            return $range.isStartedBy(element.longValue());
        } else if (minimum instanceof Float) {
            return $range.isStartedBy(element.floatValue());
        } else if (minimum instanceof Double) {
            return $range.isStartedBy(element.doubleValue());
        }
    
        return false;
    }
    
    /**
     * <p>Checks whether this range ends with the specified element.</p>
     *
     * @param element  the element to check for, null returns false
     * @return true if the specified element occurs within this range
     */
    @SuppressWarnings("unchecked")
    public boolean isEndedBy(final Number element) {
        if (minimum instanceof Byte) {
            return $range.isEndedBy(element.byteValue());
        } else if (minimum instanceof Short) {
            return $range.isEndedBy(element.shortValue());
        } else if (minimum instanceof Integer) {
            return $range.isEndedBy(element.intValue());
        } else if (minimum instanceof Long) {
            return $range.isEndedBy(element.longValue());
        } else if (minimum instanceof Float) {
            return $range.isEndedBy(element.floatValue());
        } else if (minimum instanceof Double) {
            return $range.isEndedBy(element.doubleValue());
        }
    
        return false;
    }
    
    /**
     * <p>Checks whether this range is before the specified element.</p>
     *
     * @param element  the element to check for, null returns false
     * @return true if this range is entirely before the specified element
     */
    @SuppressWarnings("unchecked")
    public boolean isBefore(final Number element) {
        if (minimum instanceof Byte) {
            return $range.isBefore(element.byteValue());
        } else if (minimum instanceof Short) {
            return $range.isBefore(element.shortValue());
        } else if (minimum instanceof Integer) {
            return $range.isBefore(element.intValue());
        } else if (minimum instanceof Long) {
            return $range.isBefore(element.longValue());
        } else if (minimum instanceof Float) {
            return $range.isBefore(element.floatValue());
        } else if (minimum instanceof Double) {
            return $range.isBefore(element.doubleValue());
        }
    
        return false;
    }
    
    /**
     * <p>Checks where the specified element occurs relative to this range.</p>
     *
     * <p>The API is reminiscent of the Comparable interface returning {@code -1} if
     * the element is before the range, {@code 0} if contained within the range and
     * {@code 1} if the element is after the range. </p>
     *
     * @param element  the element to check for, not null
     * @return -1, 0 or +1 depending on the element's location relative to the range
     */
    @SuppressWarnings("unchecked")
    public int elementCompareTo(final Number element) {
        if (minimum instanceof Byte) {
            return $range.elementCompareTo(element.byteValue());
        } else if (minimum instanceof Short) {
            return $range.elementCompareTo(element.shortValue());
        } else if (minimum instanceof Integer) {
            return $range.elementCompareTo(element.intValue());
        } else if (minimum instanceof Long) {
            return $range.elementCompareTo(element.longValue());
        } else if (minimum instanceof Float) {
            return $range.elementCompareTo(element.floatValue());
        } else if (minimum instanceof Double) {
            return $range.elementCompareTo(element.doubleValue());
        }
    
        return 0;
    }
    
    // Range tests
    //--------------------------------------------------------------------
    
    /**
     * <p>Checks whether this range contains all the elements of the specified range.</p>
     *
     * <p>This method may fail if the ranges have two different comparators or element types.</p>
     *
     * @param otherRange  the range to check, null returns false
     * @return true if this range contains the specified range
     * @throws RuntimeException if ranges cannot be compared
     */
    @SuppressWarnings("unchecked")
    public boolean containsRange(final NumberRange otherRange) {
        if (otherRange == null)
            return false;
        
        return contains(otherRange.minimum) && contains(otherRange.maximum);
    }
    
    /**
     * <p>Checks whether this range is completely after the specified range.</p>
     *
     * <p>This method may fail if the ranges have two different comparators or element types.</p>
     *
     * @param otherRange  the range to check, null returns false
     * @return true if this range is completely after the specified range
     * @throws RuntimeException if ranges cannot be compared
     */
    @SuppressWarnings("unchecked")
    public boolean isAfterRange(final NumberRange otherRange) {
        if (otherRange == null)
            return false;
        
        return isAfter(otherRange.maximum);
    }
    
    /**
     * <p>Checks whether this range is overlapped by the specified range.</p>
     *
     * <p>Two ranges overlap if there is at least one element in common.</p>
     *
     * <p>This method may fail if the ranges have two different comparators or element types.</p>
     *
     * @param otherRange  the range to test, null returns false
     * @return true if the specified range overlaps with this
     *  range; otherwise, {@code false}
     * @throws RuntimeException if ranges cannot be compared
     */
    @SuppressWarnings("unchecked")
    public boolean isOverlappedBy(final NumberRange otherRange) {
        if (otherRange == null)
            return false;
        
        return otherRange.contains(minimum)
               || otherRange.contains(maximum)
               || contains(otherRange.minimum);
    }
    
    /**
     * <p>Checks whether this range is completely before the specified range.</p>
     *
     * <p>This method may fail if the ranges have two different comparators or element types.</p>
     *
     * @param otherRange  the range to check, null returns false
     * @return true if this range is completely before the specified range
     * @throws RuntimeException if ranges cannot be compared
     */
    @SuppressWarnings("unchecked")
    public boolean isBeforeRange(final NumberRange otherRange) {
        if (otherRange == null)
            return false;
        
        return isBefore(otherRange.minimum);
    }
    
    private void setRange(final Number minimum, final Number maximum) {
        if (minimum instanceof Byte) {
            $range = Range.between(minimum.byteValue(), maximum.byteValue());
        } else if (minimum instanceof Short) {
            $range = Range.between(minimum.shortValue(), maximum.shortValue());
        } else if (minimum instanceof Integer) {
            $range = Range.between(minimum.intValue(), maximum.intValue());
        } else if (minimum instanceof Long) {
            $range = Range.between(minimum.longValue(), maximum.longValue());
        } else if (minimum instanceof Float) {
            $range = Range.between(minimum.floatValue(), maximum.floatValue());
        } else if (minimum instanceof Double) {
            $range = Range.between(minimum.doubleValue(), maximum.doubleValue());
        }
    }
}
