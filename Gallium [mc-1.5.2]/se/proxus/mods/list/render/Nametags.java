package se.proxus.mods.list.render;

import se.proxus.Gallium;
import se.proxus.mods.Mod;
import se.proxus.mods.ModCategory;

public class Nametags extends Mod {

    public Nametags(final Gallium client) {
	super("Nametags", ModCategory.RENDER, false, client);
    }

    @Override
    public void init() {
	setDescription("Renders larger nametags.");
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}