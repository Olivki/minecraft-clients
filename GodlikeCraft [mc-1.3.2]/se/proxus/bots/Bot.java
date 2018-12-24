package se.proxus.bots;

import java.util.*;

import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import se.proxus.GodlikeCraft;

import net.minecraft.client.Minecraft;

public class Bot implements Runnable {

	public static boolean test = false;
	public static Minecraft mc;
	public Thread loginSpam;
	Random ran = new Random();
	public String user = "Sirenfal" + ran.nextInt(9999);
	DataOutputStream os = null;
	DataInputStream is = null;

	public Bot(Minecraft minecraft) {
		mc = minecraft;
		loginSpam = new Thread(this, "");
		loginSpam.start();
	}

	public void run() {
		try {
			try {
				System.out.println("Connecting...");
				System.out.println("Socket Attempting to connect.");
				Thread.sleep(5000);
				Socket socket = new Socket(mc.dm.vars.curIP, mc.dm.vars.curPort);
				os = new DataOutputStream(socket.getOutputStream());
				is = new DataInputStream(socket.getInputStream());

				Handshake();
				Login();
				System.out.println("Done.");

				while (true) {
					Spam();
					os.writeByte(0);
					Thread.sleep(700);
				}
			} catch (UnknownHostException e) {
			} catch (IOException e) {
			} catch (InterruptedException e) {
			}
			Thread.sleep(5000);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static void writeString2(String s, DataOutputStream dataoutputstream)
			throws IOException {
		if (s.length() > 32767) {
			throw new IOException("String too big");
		} else {
			dataoutputstream.writeShort(s.length());
			dataoutputstream.writeChars(s);
			return;
		}
	}

	public void Handshake() {

		try {
			os.writeByte(2);
			writeString2(user, os);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void Chat(String s) {

		try {
			os.writeByte(3);
			writeString2(s, os);
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}

	public void Spam() {
		try {
			while(true){Chat(GodlikeCraft.vars.botMessage + (new Random()).nextInt(100));}
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	public void Login() {

		try {
			os.writeByte(1);
			os.writeInt(39);
			writeString2(user, os);
			os.writeLong(0);
			os.writeShort(0);
			os.writeInt(0);
			os.writeByte(0);
			os.writeByte(0);
			os.writeByte(0);
			os.writeByte(0);		
			Chat("I'm a hurp."); 
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
}