package se.proxus.kanon.utils.lang;

import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

@UtilityClass
public final class Stringz {

    /**
     * <p>Converts all the delimiter separated words in a String into camelCase, that is each word is made up
     * of a titlecase character and then a series of lowercase characters.</p>
     * <p>
     * <p>The delimiters represent a set of characters understood to separate words. The first non-delimiter
     * character after a delimiter will be capitalized. The first String character may or may not be
     * capitalized and it's determined by the user input for capitalizeFirstLetter variable.</p>
     * <p>
     * <p>A <code>null</code> input String returns <code>null</code>. Capitalization uses the Unicode title
     * case, normally equivalent to upper case and cannot perform locale-sensitive mappings.</p>
     * <p>
     * <pre>
     * CaseUtils.toCamelCase(null, false)                                 = null
     * CaseUtils.toCamelCase("", false, *)                                = ""
     * CaseUtils.toCamelCase(*, false, null)                              = *
     * CaseUtils.toCamelCase(*, true, new char[0])                        = *
     * CaseUtils.toCamelCase("To.Camel.Case", false, new char[]{'.'})     = "toCamelCase"
     * CaseUtils.toCamelCase(" to @ Camel case", true, new char[]{'@'})   = "ToCamelCase"
     * CaseUtils.toCamelCase(" @to @ Camel case", false, new char[]{'@'}) = "toCamelCase"
     * </pre>
     *
     * @param str
     *         the String to be converted to camelCase, may be null
     * @param capitalizeFirstLetter
     *         boolean that determines if the first character of first word should be title case.
     * @param delimiters
     *         set of characters to determine capitalization, null and/or empty array means whitespace
     * @return camelCase of String, <code>null</code> if null String input
     */
    public static String toCamelCase(String str, final boolean capitalizeFirstLetter,
            final char... delimiters) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(str)) {
            return str;
        }
        str = str.toLowerCase();
        final int strLen = str.length();
        final int[] newCodePoints = new int[strLen];
        int outOffset = 0;
        final Set<Integer> delimiterSet = generateDelimiterSet(delimiters);
        boolean capitalizeNext = false;
        if (capitalizeFirstLetter) {
            capitalizeNext = true;
        }
        for (int index = 0; index < strLen; ) {
            final int codePoint = str.codePointAt(index);

            if (delimiterSet.contains(codePoint)) {
                capitalizeNext = true;
                if (outOffset == 0) {
                    capitalizeNext = false;
                }
                index += Character.charCount(codePoint);
            } else if (capitalizeNext || outOffset == 0 && capitalizeFirstLetter) {
                final int titleCaseCodePoint = Character.toTitleCase(codePoint);
                newCodePoints[outOffset++] = titleCaseCodePoint;
                index += Character.charCount(titleCaseCodePoint);
                capitalizeNext = false;
            } else {
                newCodePoints[outOffset++] = codePoint;
                index += Character.charCount(codePoint);
            }
        }
        if (outOffset != 0) {
            return new String(newCodePoints, 0, outOffset);
        }
        return str;
    }

    /**
     * <p>Converts an array of delimiters to a hash set of code points. Code point of space(32) is added as
     * the default value. The generated hash set provides O(1) lookup time.</p>
     *
     * @param delimiters
     *         set of characters to determine capitalization, null means whitespace
     * @return Set<Integer>
     */
    private static Set<Integer> generateDelimiterSet(final char[] delimiters) {
        final Set<Integer> delimiterHashSet = new HashSet<>();
        delimiterHashSet.add(Character.codePointAt(new char[]{' '}, 0));
        if (delimiters == null || delimiters.length == 0) {
            return delimiterHashSet;
        }

        for (int index = 0; index < delimiters.length; index++) {
            delimiterHashSet.add(Character.codePointAt(delimiters, index));
        }
        return delimiterHashSet;
    }

    public static String[] wrapText(final String text, final int len) {
        if (text == null) {
            return new String[]{};
        }

        if (len <= 0) {
            return new String[]{text};
        }

        if (text.length() <= len) {
            return new String[]{text};
        }

        final char[] chars = text.toCharArray();

        final Vector lines = new Vector();

        final StringBuffer line = new StringBuffer();

        final StringBuffer word = new StringBuffer();

        for (int i = 0; i < chars.length; i++) {
            word.append(chars[i]);

            if (chars[i] == ' ') {
                if ((line.length() + word.length()) > len) {
                    lines.add(line.toString());
                    line.delete(0, line.length());
                }

                line.append(word);
                word.delete(0, word.length());
            }
        }

        if (word.length() > 0) {
            if ((line.length() + word.length()) > len) {
                lines.add(line.toString());
                line.delete(0, line.length());
            }

            line.append(word);
        }

        if (line.length() > 0) {
            lines.add(line.toString());
        }

        final String[] ret = new String[lines.size()];

        int c = 0;

        for (final Enumeration e = lines.elements(); e.hasMoreElements(); c++) {
            ret[c] = (String) e.nextElement();
        }

        return ret;
    }

    public static String arrayToStringList(final Object[] array) {
        final String stringBuilder = Arrays.stream(array)
                .map(object -> object + System.lineSeparator())
                .collect(Collectors.joining());
    
        return org.apache.commons.lang3.StringUtils
                .stripEnd(stringBuilder, System.lineSeparator());
    }

    public static String listToStringList(final List<?> array) {
        final String stringBuilder = array.stream()
                .map(object -> object + System.lineSeparator())
                .collect(Collectors.joining());
    
        return org.apache.commons.lang3.StringUtils
                .stripEnd(stringBuilder, System.lineSeparator());
    }

    public static String listToString(final List<?> list, @Nullable final String delimiter) {
        final String result = list.stream()
                .map(object -> object + (delimiter == null
                                         ? " "
                                         : delimiter))
                .collect(Collectors.joining());
    
        return org.apache.commons.lang3.StringUtils.stripEnd(result,
                                                             (delimiter == null
                                                              ? " "
                                                              : delimiter));
    }
    
    
    public static String listToStringFancy(final List<?> list, @Nullable final String delimiter) {
        final StringBuilder stringBuilder = new StringBuilder();
        
        for (final Object object : list) {
            if (object instanceof String) {
                stringBuilder
                        .append("\"")
                        .append(object)
                        .append("\"")
                        .append((delimiter == null
                                 ? " "
                                 : delimiter));
            } else {
                stringBuilder
                        .append(object)
                        .append((delimiter == null
                                 ? " "
                                 : delimiter));
            }
        }
        
        return org.apache.commons.lang3.StringUtils.stripEnd(stringBuilder.toString(),
                                                             (delimiter == null
                                                              ? " "
                                                              : delimiter));
    }
    

    public static String collectionToString(final Collection<?> list, @Nullable final String delimiter) {
        final String result = list.stream()
                .map(object -> object + (delimiter == null
                                         ? " "
                                         : delimiter))
                .collect(Collectors.joining());
    
        return org.apache.commons.lang3.StringUtils.stripEnd(result,
                                                             (delimiter == null
                                                              ? " "
                                                              : delimiter));
    }
    
    public static String collectionToStringFancy(final Collection<?> list, @Nullable final String delimiter) {
        final StringBuilder stringBuilder = new StringBuilder();
        
        for (final Object object : list) {
            if (object instanceof String) {
                stringBuilder
                        .append("\"")
                        .append(object)
                        .append("\"")
                        .append((delimiter == null
                                 ? " "
                                 : delimiter));
            } else {
                stringBuilder
                        .append(object)
                        .append((delimiter == null
                                 ? " "
                                 : delimiter));
            }
        }
        
        return org.apache.commons.lang3.StringUtils.stripEnd(stringBuilder.toString(),
                                                             (delimiter == null
                                                              ? " "
                                                              : delimiter));
    }
    
    public static String arrayToStringFancy(final Object[] array, @Nullable final String delimiter) {
        final StringBuilder stringBuilder = new StringBuilder();
        
        for (final Object object : array) {
            if (object instanceof String) {
                stringBuilder
                        .append("\"")
                        .append(object)
                        .append("\"")
                        .append((delimiter == null
                                 ? " "
                                 : delimiter));
            } else {
                stringBuilder
                        .append(object)
                        .append((delimiter == null
                                 ? " "
                                 : delimiter));
            }
        }
        
        return org.apache.commons.lang3.StringUtils.stripEnd(stringBuilder.toString(),
                                                             (delimiter == null
                                                              ? " "
                                                              : delimiter));
    }

    public static String arrayToString(final Object[] array, @Nullable final String delimiter) {
        final String result = Arrays.stream(array)
                .map(object -> object + (delimiter == null
                                         ? " "
                                         : delimiter))
                .collect(Collectors.joining());
    
        return org.apache.commons.lang3.StringUtils.stripEnd(result,
                                                             (delimiter == null
                                                              ? " "
                                                              : delimiter));
    }
    
    public static String toMeme(final String string) {
        return string.toLowerCase();
    }
}
