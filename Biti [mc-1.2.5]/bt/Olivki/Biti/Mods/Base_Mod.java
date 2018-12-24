package bt.Olivki.Biti.Mods;

public abstract class Base_Mod { //I will be referring the methods and variables like it was a car, I think it makes it easier to understand.
	
	private Base_Enum bm; //Makes it so we can use our Base_Enum class for our mods / hacks.
	private boolean Running; //This won't really run or anything, but I just like to refer it like it was a car.
	
	public Base_Mod(Base_Enum base_enum){
		bm = base_enum;
	}
	
	public final void start() { //When the mod / hack is activated, this happens.
		Running = true; //When the method Start() has been activated / referred it will set the state of the boolean boolean Running to true.
		onStart(); //Refers the method onStart() which is what we will be using in our mod / hack class.
	}
	
	public final void stop() { //When the mod / hack is deactivated, this happens.
		Running = false; //When the method Stop() has been activated / referred it will set the state of the boolean boolean Running to false.
		onStop();  //Refers the method onStop() which is what we will be using in our mod / hack class.
	}
	
	public final void toggle() { //Toggles the mod / hack.
		Running = !Running; //Toggles the boolean Running.
		if(Running) { onStart(); } else { onStop();} //If the boolean Running is true, it runs onStart(), if it's false, it runs onStop().
	}
	
	public final boolean isRunning() { //Checks if the boolean Running is either true or false, pretty much like let's say if a car is on or off.
		return Running; //Returns the boolean Running.
	}
	
	public abstract void onStart(); //When the method Start() has been activated, this happens, we will be using this in our mod / hack class.
	
	public abstract void onStop(); //When the method Stop() has been activated, this happens, we will be using this in our mod / hack class.

}
