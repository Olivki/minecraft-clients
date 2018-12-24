package bt.Olivki.Biti.Utils;

import bt.Olivki.Biti.Biti;
import net.minecraft.src.*;

public class Utils {
	
	public static void sendPacket(Packet packet) { //A method for sending packets, it looks better..
		if(Biti.mc.isMultiplayerWorld()){ //Checks if the world is multiplayer, and if it return true, then it send the packet, if it returns false, then it does nothing.
		Biti.mc.getSendQueue().addToSendQueue(packet); //Send the Packet.
		return; //Returns so it won't do anything more.
		} else { return;} //If it doesn't return true, then it doesn't send the packet.
	}
	
	public static void addChat(String MESSAGE) { //A method for adding chat messages with a prefix, it looks better.
		String MSG = Colours.AQUA + "[Biti] " + Colours.WHITE + MESSAGE; //Makes it so that the String MSG first adds the [Biti] part, then it adds the message.
		Biti.mc.thePlayer.addChatMessage(MSG); //Adds the message to the chat.
	}
	
	public static void sendChat(String MSG) { //A method for sending chat messages, it looks better. :D
		sendPacket(new Packet3Chat(MSG)); //Sends the packet for sending a chat message using our sendPacket method. ^^
	}
}
