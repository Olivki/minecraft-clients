package bt.Olivki.Biti;

import bt.Olivki.Biti.Gui.*;
import bt.Olivki.Biti.Hooks.*;
import bt.Olivki.Biti.Render.*;
import bt.Olivki.Biti.Utils.*;
import net.minecraft.client.*;

public class Biti implements Runnable {

	public static Minecraft mc;

	public static Keys keys = new Keys(); //Creates a instance for the class Keys.java to refer it easier.
	public static Variables vars = new Variables(); //Creates a instance for the class Variables.java to refer it easier.
	public static InGame ingame; //Creates a instance for the class InGame.java to refer it easier.
	public static Biti_Renderer render; //Creates a instance for the class Bitit_Renderer.java to refer it easier.

	public Biti(Minecraft minecraft) {
		mc = minecraft; //Makes it so the class knows that it's the Minecraft.java class, otherwise it might crash.
		ingame = new InGame(mc); //It needs to be put here, otherwise it will crash, because mc gets defined here.

		run(); //Runs the method run() once Biti.java is refereed.
	}

	@Override
	public void run() {
		System.out.print("Biti has been started."); //Outprints to the system that Biti has been start, you can change the text.
	}

}
