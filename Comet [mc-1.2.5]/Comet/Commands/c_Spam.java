package Comet.Commands;

import java.util.ArrayList;

import Comet.Mods.*;
import Comet.Utils.Color;
import Comet.Utils.Settings;
import Comet.Utils.Utils;

public class c_Spam extends Base_Command {

	public void run(String as[]) throws Exception {
		try {
			String S1 = as[0];
			String S2 = as[1];
			int amount = Integer.parseInt(S2);

			for(int spam = 0; spam < amount; spam++) {
				Utils.sendChat(S1);
			}

		} catch (Exception e) {
			mc.comet.utils.addChat("Syntax : -spam [text] [amount]");
			e.printStackTrace();
		}
	}

	public String getHelp() throws Exception {return "Spams a message a certain amount of times.";}

	public String getUsage() throws Exception {return ", can be used to annoy people, or just for the lulz.";}

}
