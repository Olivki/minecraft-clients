package se.proxus.kanon.wrapper.minecraft;

import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.util.Objects;

@UtilityClass
public final class Screen {
    
    /**
     * Gets the Clients ScaledResolution for getting the bounds of the screen.
     *
     * @return The Clients ScaledResolution for getting the bounds of the screen.
     */
    public static ScaledResolution getResolution() {
        return new ScaledResolution(getMinecraft());
    }
    
    public static int getDisplayWidth() {
        return getMinecraft().displayWidth;
    }
    
    public static int getDisplayHeight() {
        return getMinecraft().displayHeight;
    }
    
    public static int getScaledWidth() {
        return getResolution().getScaledWidth();
    }
    
    public static int getScaledHeight() {
        return getResolution().getScaledHeight();
    }
    
    public static int getWidthMiddle() {
        return getScaledWidth() / 2;
    }
    
    public static int getHeightMiddle() {
        return getScaledHeight() / 2;
    }
    
    public static GuiScreen getCurrent() {
        return getMinecraft().currentScreen;
    }
    
    public static boolean isCurrent(final GuiScreen screen) {
        if (Objects.isNull(getCurrent()))
            return false;
        else
            return getCurrent().getClass().isAssignableFrom(screen.getClass());
    }
    
    public static boolean isCurrent(final Class<?> cls) {
        if (Objects.isNull(getCurrent()))
            return false;
        else
            return getCurrent().getClass().isAssignableFrom(cls);
    }
    
    public static void display(final GuiScreen screen) {
        getMinecraft().displayGuiScreen(screen);
    }
    
    private static Minecraft getMinecraft() {
        return Minekraft.instance();
    }
    
}
