package se.proxus.kanon.utils.math;

public final class Numberz {
    
    // Most of these methods are not good practice.
    // try & catching should never be used to control the flow of anything.
    // They're pretty much legacy code at this point.
    // (Even though this is pretty the same thing that Apache does with their commons library.)
    
    public static int toInteger(final Object object) {
        if (object instanceof Short) {
            return ((Short) object).intValue();
        } else if (object instanceof Byte) {
            return ((Byte) object).intValue();
        } else if (object instanceof Long) {
            return ((Long) object).intValue();
        } else if (object instanceof Float) {
            return ((Float) object).intValue();
        } else if (object instanceof Double) {
            return ((Double) object).intValue();
        }
        
        return (int) object;
    }

    public static boolean isNumber(final String text) {
        return isInteger(text) || isLong(text) || isFloat(text) || isDouble(text) || isShort(text)
                || isByte(text);
    }

    public static boolean isDecimalInstance(final Object value) {
        return value instanceof Double || value instanceof Float;
    }
    
    public static boolean isDecimalClass(final Class<?> cls) {
        return Double.class.isAssignableFrom(cls) || Float.class.isAssignableFrom(cls);
    }

    public static boolean isDecimal(final String input) {
        return !(isInteger(input) || isByte(input) || isShort(input) || isLong(input));
    }

    public static boolean isBoolean(final String text) {
        try {
            Boolean.parseBoolean(text);
            return true;
        } catch (final Exception exception) {
            return false;
        }
    }

    public static boolean isInteger(final String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (final NumberFormatException exception) {
            return false;
        }
    }

    public static boolean isFloat(final String text) {
        try {
            Float.parseFloat(text);
            return true;
        } catch (final NumberFormatException exception) {
            return false;
        }
    }

    public static boolean isLong(final String text) {
        try {
            Long.parseLong(text);
            return true;
        } catch (final NumberFormatException exception) {
            return false;
        }
    }

    public static boolean isDouble(final String text) {
        try {
            Double.parseDouble(text);
            return true;
        } catch (final NumberFormatException exception) {
            return false;
        }
    }

    public static boolean isShort(final String text) {
        try {
            Short.parseShort(text);
            return true;
        } catch (final NumberFormatException exception) {
            return false;
        }
    }

    public static boolean isByte(final String text) {
        try {
            Byte.parseByte(text);
            return true;
        } catch (final NumberFormatException exception) {
            return false;
        }
    }
}