package ab.Bytes.Utils;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.src.*;

public class Keys {
	
	public static ArrayList<Keys> keyArray = new ArrayList<Keys>();
    public static IntHashMap keyHash = new IntHashMap();
    public static String keyDescription;
    public static int keyCode, keyPressTime;
    public static boolean keyPressed;
    
    public Keys(String I1, int I2) {
    	keyPressTime = 0;
        keyDescription = I1;
        keyCode = I2;
        keyArray.add(this);
        keyHash.addKey(I2, this);
    }
    
    public static void onTick(int I1) {
        Keys keys = (Keys)keyHash.lookup(I1);

        if (keys != null) {
        	keys.keyPressTime++;
        }
    }
    
    public static void setKeyState(int I1, boolean I2) {
    	Keys keys = (Keys)keyHash.lookup(I1);

        if (keys != null) {
        	keys.keyPressed = I2;
        }
    }
    
    public static void releaseAllKeys() {
    	Keys keys;
    	
        for (Keys key : keyArray) {
        	key.releaseAllKeys();
        }
    }
    
    public static void resetKeyArrayAndHash() {
        keyHash.clearMap();
        Keys keys;

        for (Iterator iterator = keyArray.iterator(); iterator.hasNext(); keyHash.addKey(keys.keyCode, keys)) {
        	keys = (Keys)iterator.next();
        }
    }
    
    public static boolean isPressed() {
        if (keyPressTime == 0) {
            return false;
        } else {
        	keyPressTime--;
            return true;
        }
    }
    
    private static void unpressKey() {
    	keyPressTime = 0;
        keyPressed = false;
    }
}