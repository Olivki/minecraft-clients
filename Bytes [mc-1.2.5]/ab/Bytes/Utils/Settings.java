package ab.Bytes.Utils;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import ab.Bytes.*;
import ab.Bytes.Gui.Menus.*;

import net.minecraft.src.*;

public class Settings {
	
	public static Bytes bt = new Bytes();
	
	public static String 
	name = "§6Bytes§f",
	version = " §60.2a§f",
	info = name + version;
	
	public static ArrayList 
	blockList = new ArrayList();
	
	public static float
	speed = 1.0F,
	autoMineBreak;
	
	public static boolean
	canAutoMine = false;
	
	public Settings() {
		checkxRay();
	}
	
	public static void checkxRay() {
		blockList.clear();
		blockList.add(Block.lavaMoving.blockID);
		blockList.add(Block.lavaStill.blockID);
		blockList.add(Block.waterMoving.blockID);
		blockList.add(Block.waterStill.blockID);
		blockList.add(Block.oreCoal.blockID);
		blockList.add(Block.oreIron.blockID);
		blockList.add(Block.oreDiamond.blockID);
	}
	
	public static void onKeyEvent() {
		if(onKeyPressed(Keyboard.KEY_RSHIFT)) {
			bt.mc.displayGuiScreen(new Base_MenuList());
		}
	}
	
	public static boolean onKeyPressed(int key) {
		return Keyboard.getEventKey() == key;
	}
	
    public static void setOptionFloatValue(EnumSettings I1, float I2) {
        if (I1 == EnumSettings.SPEED) {
        	speed = I2;
        }
    }
    
    public static void setOptionValue(EnumSettings I1, int I2) {
    	
    }

    public static float getOptionFloatValue(EnumSettings I1) {
        if (I1 == EnumSettings.SPEED) {
            return speed;
        } else {
            return 0.0F;
        }
    }
    
    public static String getKeyBinding(EnumSettings I1) {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        String s = (new StringBuilder()).append(stringtranslate.translateKey(I1.getEnumString())).append(" : ").toString();

        if (I1.getEnumFloat()) {
            float f = getOptionFloatValue(I1);

            if (I1 == EnumSettings.SPEED) {
                if (f == 0.0F)
                {
                    return (new StringBuilder()).append(s).append(stringtranslate.translateKey("0")).toString();
                }

                if (f == 1.0F)
                {
                    return (new StringBuilder()).append(s).append(stringtranslate.translateKey("10")).toString();
                }
                else
                {
                    return (new StringBuilder()).append(s).append((int)(f * 200F)).append("%").toString();
                }
            }
        } else {
            return s;
        }
		return s;
    }
}