package Comet.Mods;

import net.minecraft.client.Minecraft;

public abstract class Base_Mod {
	
	public static Minecraft mc = Minecraft.theMinecraft;
	
	private Base_Enum bm;
	protected boolean enabled;

	public Base_Mod(Base_Enum base_enum){
		bm = base_enum;
	}

	public final void start() {
		enabled = true;
		onStart();
	}

	public final void stop() {
		enabled = false;
		onStop();
	}

	public final void toggle() {
		enabled = !enabled;
		if(enabled) {onStart();} else {onStop();}
	}

	public final boolean isRunning() {
		return enabled;
	}

	public abstract void onStart();

	public abstract void onStop();

}
