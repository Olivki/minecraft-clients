package Comet.Utils;

import java.util.ArrayList;

import Comet.*;
import Comet.Mods.*;
import Comet.Utils.Hooks.*;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;


public class Utils {

	private static ArrayList<Tickable> tickables = new ArrayList<Tickable>();
	public static ArrayList<ClickBlock> clickblocks = new ArrayList<ClickBlock>();
	
	public static Minecraft mc = Minecraft.theMinecraft;
	
	public static void sendPacket(Packet packet) {
		if(Comet.mc.isMultiplayerWorld()){
			Comet.mc.getSendQueue().addToSendQueue(packet);
			return;
		} else {return;}
	}
	
    public static EntityPlayer getPlayer() {
    	EntityPlayer player = null;
    	
    	for(int i = 0; i < mc.theWorld.playerEntities.size(); i++){player = (EntityPlayer)mc.theWorld.playerEntities.get(i);}
    	
    	return player;
    }

	public static void addChat(String message) {
		String msg = Color.AQUA + "[Comet] " + Color.WHITE + message;
		Comet.mc.thePlayer.addChatMessage(msg);
	}

	public static void sendChat(String msg) {
		sendPacket(new Packet3Chat(msg));
	}

	public static void addChatToggle(String name, boolean fl) {
		String msg = name + " : " + (fl ? Color.GREEN + "Enabled." : Color.RED + "Disabled.");
		addChat(msg);
	}

	public static void reverseString(String s) {
		StringBuffer stringbuff = new StringBuffer(s);
		stringbuff.reverse();
	}

	public static void rerender(){
		Comet.mc.renderGlobal.loadRenderers();
	}

	public static void onUpdate() {
		for (Tickable tick : tickables) {tick.onTick();}
	}
	
	public static void onClickBlock(int I1, int I2, int I3, int I4) {
		for (ClickBlock clickblock : clickblocks) {clickblock.onClickBlock(I1, I2, I3, I4);}
	}

	public static void addToTick(Tickable tickable) {
		if (!tickables.contains(tickable)) {tickables.add(tickable);}
	}
	
	public static void addToClickBlock(ClickBlock clickblock) {
		if (!clickblocks.contains(clickblock)) {clickblocks.add(clickblock);}
	}
	
	public static void removeFromTick(Tickable tickable) {
		if (tickables.contains(tickable)) {tickables.remove(tickable);}
	}

	public static void removeFromClickBlock(ClickBlock clickblock) {
		if (clickblocks.contains(clickblock)) {clickblocks.remove(clickblock);}
	}
	
	public static void addFriend(String name) {
		mc.comet.settings.friends.add(name);
	}
	
	public static void addEnemy(String name) {
		mc.comet.settings.enemys.add(name);
	}
	
	public static void addLwcName(String name) {
		mc.comet.settings.lwcList.add(name);
	}
	
	public static void removeFriend(String name) {
		if(mc.comet.settings.friends.contains(name)) {mc.comet.settings.friends.remove(name);}
	}
	
	public static void removeEnemy(String name) {
		if(mc.comet.settings.enemys.contains(name)) {mc.comet.settings.enemys.remove(name);}
	}
	
	public static void removeLwcName(String name) {
		if(mc.comet.settings.lwcList.contains(name)) {mc.comet.settings.lwcList.remove(name);}
	}
	
    public boolean isFriend(String name) {
        return mc.comet.settings.friends.contains(name);
    }
    
    public boolean isEnemy(String name) {
        return mc.comet.settings.enemys.contains(name);
    }
    
    public boolean isLwcName(String name) {
        return mc.comet.settings.lwcList.contains(name);
    }
}
