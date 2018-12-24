package se.proxus.tools;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;

public class ScreenTools {

    public static int getColourAtPoint(final int x, final int y) {
	try {
	    Robot robot = new Robot();
	    Color colour = robot.getPixelColor(x, y);
	    return colour.getAlpha() + colour.getRGB();
	} catch (AWTException e) {
	    e.printStackTrace();
	    return 0;
	}
    }

}