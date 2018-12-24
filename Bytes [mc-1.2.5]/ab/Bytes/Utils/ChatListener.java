package ab.Bytes.Utils;

import java.util.ArrayList;

import ab.Bytes.*;

public class ChatListener {

	public static ArrayList chatArray = new ArrayList();

	public static Bytes bs = new Bytes();

	public static void convertString(String s) {
		s = s.replaceAll("§0", ""); //Black.
		s = s.replaceAll("§1", ""); //Dark blue.
		s = s.replaceAll("§2", ""); //Dark green.
		s = s.replaceAll("§3", ""); //Dark aqua.
		s = s.replaceAll("§4", ""); //Dark red.
		s = s.replaceAll("§5", ""); //Dark purple.
		s = s.replaceAll("§6", ""); //Gold.
		s = s.replaceAll("§7", ""); //Grey.
		s = s.replaceAll("§8", ""); //Dark grey.
		s = s.replaceAll("§9", ""); //Blue.

		s = s.replaceAll("§a", ""); //Green.
		s = s.replaceAll("§b", ""); //Aqua.
		s = s.replaceAll("§c", ""); //Red.
		s = s.replaceAll("§d", ""); //Light purple.
		s = s.replaceAll("§e", ""); //Yellow.
		s = s.replaceAll("§f", ""); //White.
		s = s.replaceAll("§k", ""); //Random.
		s = s.replaceAll("§l", ""); //Bold.
		s = s.replaceAll("§m", ""); //Strikethrough.
		s = s.replaceAll("§n", ""); //Underlined.
		s = s.replaceAll("§o", ""); //Italic.
		s = s.replaceAll("§r", ""); //Reset.

		chatArray.add(s);
		Utils.log(s);
	}
	
	public static void preventSpam(String s) {
		
	}

	public static void onChat(String s) {
		convertString(s);
		preventSpam(s);
		//bs.insultgenerator.onChat(s);
	}
}
