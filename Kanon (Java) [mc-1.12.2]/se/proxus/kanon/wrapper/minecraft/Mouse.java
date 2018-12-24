package se.proxus.kanon.wrapper.minecraft;

import lombok.experimental.UtilityClass;

import java.awt.*;

@UtilityClass
public final class Mouse {
    
    public static boolean isOver(final int x, final int y, final int compareX, final int compareY) {
        return (getX() >= x && getX() <= compareX && getY() >= y && getY() <= compareY);
    }
    
    public static boolean isOver(final Rectangle rectangle) {
        return (getX() >= rectangle.x
                && getX() <= rectangle.y
                && getY() >= rectangle.y + rectangle.height
                && getY() <= rectangle.width + rectangle.x);
    }
    
    private static int getX() {
        return getMouse().x;
    }
    
    private static int getY() {
        return getMouse().y;
    }
    
    /**
     * @author DarkStorm
     * @return The current coordinates of the mouse in the form of a Point class.
     */
    public static Point getMouse() {
        final int width = Screen.getDisplayWidth();
        final int height = Screen.getDisplayHeight();
        int scale = Minekraft.getSettings().guiScale;
        
        if (scale == 0) {
            scale = 1000;
        }
        
        int scaleFactor = 0;
        
        while ((scaleFactor < scale)
               && ((width / (scaleFactor + 1)) >= 320)
               && ((height / (scaleFactor + 1)) >= 240)) {
            scaleFactor++;
        }
        
        return new Point(org.lwjgl.input.Mouse.getX() / scaleFactor,
                         (height / scaleFactor) - (org.lwjgl.input.Mouse.getY() / scaleFactor) - 1);
    }
}
