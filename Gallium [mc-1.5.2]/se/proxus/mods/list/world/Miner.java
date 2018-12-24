package se.proxus.mods.list.world;

import net.minecraft.src.Potion;
import net.minecraft.src.PotionEffect;
import se.proxus.Gallium;
import se.proxus.mods.Mod;
import se.proxus.mods.ModCategory;

public class Miner extends Mod {

    public Miner(final Gallium client) {
	super("Miner", ModCategory.WORLD, false, client);
    }

    @Override
    public void init() {
	setDescription("Makes you mine faster. (Buggy)");
    }

    @Override
    public void onEnable() {
	getClient().getMinecraft().thePlayer.addPotionEffect(new PotionEffect(
		Potion.digSpeed.id, -1200000000));
    }

    @Override
    public void onDisable() {
	getClient().getMinecraft().thePlayer
		.removePotionEffect(Potion.digSpeed.id);
    }
}