package bt.Olivki.Biti.Gui;

import bt.Olivki.Biti.*;
import net.minecraft.client.*;
import net.minecraft.src.*;

public class InGame extends Methods { //We make it extend methods so we can use all the GUI methods inside of it, without needing to change them.

	public static Minecraft mc; //Makes it so we can use all the stuff inside Minecraft.java without needing to change them.
	public static FontRenderer fr; //Makes it so we can use FontRenderer without needing to type useless stuff infront of it.
	public static ScaledResolution sc; //Makes it so we can use the W & H int's.

	public static int W; //The int for the width.
	public static int H; //The int for the height.

	public InGame(Minecraft minecraft) { //Here we will define everything.
		mc = minecraft; //Makes it so the class knows that it's the Minecraft.java class, otherwise it might crash.

		fr = mc.fontRenderer; //fr will now act like a shortcut for mc.fontRenderer, it looks better, and it helps my OCD.
		sc = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight); //Just like fr, it acts like a shortened down version of the ScaledResolution class.

		W = sc.getScaledWidth(); //Same as fr, a shortened down version, it look's better and helps my OCD.
		H = sc.getScaledHeight(); //Same as above.
	}

	public static void renderGameOverlay() { //This is the method where we will be drawing / rendering all our GUI related stuff.
		fr.drawStringWithShadow("Biti (Tutorial client)", 1, 1, 0xFFFFFFFF); //Draws a string with shadow, pretty self explanatory.
		arrayList(); //Calls the method ArrayList() which will draw the ArrayList.
		// I will go more indepth about GUI making in another part, this is just to show you guys / girls how to create the base for the client.
	}

	public static void arrayList() {
		int Gui = 1; //The int which will change the position for the ArrayList.
		int Colour = 0xFFFFFFFF; //The colour for the text in the ArrayList.
		int Pos = 12 * Gui; //Determines the position of the text.
		if(Biti.vars.Sneak){fr.drawStringWithShadow("Sneak", 1, Pos, Colour); Gui += 1;} //If the boolean Sneak is true, then it draws this, if it's false, it won't draw it.
	}

}
