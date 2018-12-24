package bt.Olivki.Biti.Utils;

import bt.Olivki.Biti.Biti;
import net.minecraft.src.Packet3Chat;

public class Commands extends Utils {

	public static String CMDS[] = {"."};
	public static String CMD;

	public static void CMD(String s){
		CMD = s;

		if(CMD.equalsIgnoreCase(CMDS[0] + CMDS[0])){sendPacket(new Packet3Chat(CMDS[0]));}
	}
	

}
