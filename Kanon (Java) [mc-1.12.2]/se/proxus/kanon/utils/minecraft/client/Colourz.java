package se.proxus.kanon.utils.minecraft.client;

import lombok.experimental.UtilityClass;
import net.minecraft.util.Util;

import java.awt.*;

@UtilityClass
public final class Colourz {

    /** FIX FOR MAC USERS * */
    public static final char COLOUR_SYMBOL = Util.getOSType() == Util.EnumOS.OSX ? '\u00A7' : '\247';

    /** Code: 0 * */
    public static final String BLACK = COLOUR_SYMBOL + "0";

    /** Code: 1 * */
    public static final String DARK_BLUE = COLOUR_SYMBOL + "1";

    /** Code: 2 * */
    public static final String DARK_GREEN = COLOUR_SYMBOL + "2";

    /** Code: 3 * */
    public static final String DARK_AQUA = COLOUR_SYMBOL + "3";

    /** Code: 4 * */
    public static final String DARK_RED = COLOUR_SYMBOL + "4";

    /** Code: 5 * */
    public static final String PURPLE = COLOUR_SYMBOL + "5";

    /** Code: 6 * */
    public static final String GOLD = COLOUR_SYMBOL + "6";

    /** Code: 7 * */
    public static final String GREY = COLOUR_SYMBOL + "7";

    /** Code: 8 * */
    public static final String DARK_GREY = COLOUR_SYMBOL + "8";

    /** Code: 9 * */
    public static final String INDIGO = COLOUR_SYMBOL + "9";

    /** Code: a * */
    public static final String BRIGHT_GREEN = COLOUR_SYMBOL + "a";

    /** Code: b * */
    public static final String AQUA = COLOUR_SYMBOL + "b";

    /** Code: c * */
    public static final String RED = COLOUR_SYMBOL + "c";

    /** Code: d * */
    public static final String PINK = COLOUR_SYMBOL + "d";

    /** Code: e * */
    public static final String YELLOW = COLOUR_SYMBOL + "e";

    /** Code: f * */
    public static final String WHITE = COLOUR_SYMBOL + "f";

    /** Code: k * */
    public static final String RANDOM = COLOUR_SYMBOL + "k";

    /** Code: l * */
    public static final String BOLD = COLOUR_SYMBOL + "l";

    /** Code: m * */
    public static final String STRIKE = COLOUR_SYMBOL + "m";

    /** Code: n * */
    public static final String UNDERLINE = COLOUR_SYMBOL + "n";

    /** Code: o * */
    public static final String ITALIC = COLOUR_SYMBOL + "o";

    /** Code: r * */
    public static final String RESET = COLOUR_SYMBOL + "r";

    /** Code: {@link Colourz#GOLD} * */
    public static final String CLIENT_COLOUR = GOLD;

    public static int getHex(final int red, final int green, final int blue,
            final int alpha) {
        return new Color(red, green, blue, alpha).getRGB()
                + new Color(red, green, blue, alpha).getAlpha();
    }

    public static int getHex(final float red, final float green,
            final float blue, final float alpha) {
        Color color = new Color(red / 255, green / 255, blue / 255, alpha);
        return color.getAlpha()
                + color.getRGB();
    }

    public static float[] getRgba(final int hex) {
        return new float[]{ (hex >> 16 & 255) / 255.0F,
                (hex >> 8 & 255) / 255.0F,
                (hex & 255) / 255.0F,
                (hex >> 24 & 255) / 255.0F,
                (hex >> 24 & 0xFF) / 255F };
    }

    public static String getCode(final int index) {
        return Integer.toHexString(index);
    }
    
    public static int interpolateColor(final int startRgba, final int endRgba, final float percent) {
        final int startHex = startRgba & 0xFF, g1 = startRgba >> 8 & 0xFF, b1 = startRgba >> 16 & 0xFF, a1 = startRgba >> 24 & 0xFF;
        final int r2 = endRgba & 0xFF, g2 = endRgba >> 8 & 0xFF, b2 = endRgba >> 16 & 0xFF, a2 = endRgba >> 24 & 0xFF;
        final int r = (int) (startHex < r2 ? startHex + (r2 - startHex) * percent : r2 + (startHex - r2) * percent);
        final int g = (int) (g1 < g2 ? g1 + (g2 - g1) * percent : g2 + (g1 - g2) * percent);
        final int b = (int) (b1 < b2 ? b1 + (b2 - b1) * percent : b2 + (b1 - b2) * percent);
        final int a = (int) (a1 < a2 ? a1 + (a2 - a1) * percent : a2 + (a1 - a2) * percent);
        return r | g << 8 | b << 16 | a << 24;
    }
}