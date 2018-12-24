package se.proxus.panels.list.buttons;

import se.proxus.gui.*;
import se.proxus.mods.*;
import se.proxus.panels.*;

public class ButtonMod extends Button {
	
	protected BaseMod mod;

	public ButtonMod(ButtonInfo info, BaseMod mod) {
		super("", info);
		this.setMod(mod);
	}

	@Override
	public void draw(int var0, int var1, TrueTypeFont var2) {
		this.setName(this.getMod().getName());
		this.getInfo().setState(this.getMod().getState());
		super.draw(var0, var1, var2);
	}

	@Override
	public void mouseClicked(int var0, int var1, int var2) {
		super.mouseClicked(var0, var1, var2);
	}
	
	public void onClicked() {
		this.getMod().toggle();
		this.getMod().getConfig().saveConfig();
	}
	
	public BaseMod getMod() {
		return this.mod;
	}

	public void setMod(BaseMod mod) {
		this.mod = mod;
	}
}