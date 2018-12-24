package se.proxus.utils;

import java.util.*;

public class Variables {
	
	public static ArrayList<String> 
	GUI_ARRAY = new ArrayList<String>();
	
	public static ArrayList<Integer>
	XRAY_ARRAY = new ArrayList<Integer>();
	
	public static String
	CLIENT_NAME = "Proxus",
	CLIENT_VERSION = "1.4.2";
	
	public static int getGuiArrayIndex(String var1) {
		int var2 = 0;
		
		for(int var3 = 0; var3 < GUI_ARRAY.size(); var3++) {
			if(GUI_ARRAY.get(var3).equalsIgnoreCase(var1)) {
				var2 = var3;
				break;
			}
		}
		
		return var2;
	}
}