package ab.Bytes.Utils;

import java.util.Random;

import ab.Bytes.*;

public class InsultGenerator {
	
	public static Bytes bs = new Bytes();
	
	public static String 
	insultNames[] = {"fag", "dick", "gay", "cunt", "bitch", "whore", "so butthurt", "mad"},
	insultPhrases[] = {"lol", "stfu", "fuck", "gtfo"};
	
	public static Random
	rand1 = new Random();
	
	public static void onChat(String s) {
		if(s.contains("hi")) {
			bs.utils.sendChat(insultPhrases[rand1.nextInt(insultPhrases.length)] + " u " + insultNames[rand1.nextInt(insultNames.length)]);
		}
	}
}