package ab.Bytes.Utils;

import java.util.ArrayList;

import net.minecraft.src.*;
import ab.Bytes.*;
import ab.Bytes.Interfaces.*;
import ab.Bytes.Mods.*;

public class Utils {
	
	public static ArrayList<Updateable> updates = new ArrayList<Updateable>();
	public static ArrayList<EntityUpdate> entityUpdates = new ArrayList<EntityUpdate>();
	public static ArrayList<EventKey> onEventKeys = new ArrayList<EventKey>();
	public static ArrayList<RightClick> onRightClick = new ArrayList<RightClick>();
	public static ArrayList<ClickBlock> onClickBlock = new ArrayList<ClickBlock>();
	
	public static Bytes bs = new Bytes();

	public static void log(String s) { //This prints out the String you entered.
		String msg = "[Bytes] " + s;

		System.out.print("\n");
		System.out.print(msg);
		System.out.print("\n");
	}
	
	public static void sendPacket(Packet packet) {
		if(bs.mc.isMultiplayerWorld()) {
			bs.mc.getSendQueue().addToSendQueue(packet);
			return;
		}
	}
	
	public static void addChat(String s) {
		String msg = "§6[Bytes] §f" + s;
		
		bs.mc.thePlayer.addChatMessage(msg);
	}
	
	public static void sendChat(String s) {
		sendPacket(new Packet3Chat(s));
	}
	
	public static void refreshWorld() {
		bs.mc.renderGlobal.loadRenderers();
	}
	
	public static void addBlock(ArrayList blockList, int blockID) {
		m_xRay xray = new m_xRay();
		
		if(!blockList.contains(blockID)) {
			blockList.add(blockID);
			addChat("'" + blockID + "' has been added to your xRay list!");
			if(xray.isEnabled()) {refreshWorld();}
		} else {
			addChat("'" + blockID + "' is already in your xRay list!");
		}
	}
	
	public static void removeBlock(ArrayList blockList, int blockID) {
		m_xRay xray = new m_xRay();
		
		if(blockList.contains(blockID)) {
			blockList.remove(blockList.indexOf(blockID));
			addChat("'" + blockID + "' has been removed from your xRay list!");
			if(xray.isEnabled()) {refreshWorld();}
		} else {
			addChat("'" + blockID + "' is not in your xRay list!");
		}
	}
	
	
	/** For the interfaces & hooks. **/
	public static void onUpdate() {
		for(Updateable update : updates) {
			update.onUpdate();
		}
	}
	
	public static void onEntityUpdate(double I1, double I2, double I3) {
		for(EntityUpdate entityUpdate : entityUpdates) {
			entityUpdate.onEntityUpdate(I1, I2, I3);
		}
	}
	
	public static void onEventKey(int I1) {
		for(EventKey eventKeys : onEventKeys) {
			eventKeys.onEventKey(I1);
		}
	}
	
	public static void onRightClick(EntityPlayer I1, World I2, ItemStack I3, int I4, int I5, int I6, int I7) {
		for(RightClick rightclick : onRightClick) {
			rightclick.onRightClick(I1, I2, I3, I4, I5, I6, I7);
		}
	}
	
	public static void onClickBlock(int I1, int I2, int I3, int I4) {
		for(ClickBlock clickBlock : onClickBlock) {
			clickBlock.onClickBlock(I1, I2, I3, I4);
		}
	}
}