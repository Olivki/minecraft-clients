package se.proxus.tools;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;

public class Tools {

    public static float ticksToSeconds(final int ticks) {
	return ticks / 20;
    }

    public static float secondsToTicks(final float seconds) {
	return seconds * 20;
    }

    public static void playSound(final String name, final float volume,
	    final float pitch) {
	Minecraft.getMinecraft().sndManager.playSoundFX(name, volume, pitch);
    }

    public static Block getBlockByName(final String name) {
	for (Block block : Block.blocksList)
	    if (block != null) {
		String blockName = block.getUnlocalizedName().toLowerCase()
			.replaceAll("tile.", "");
		if (blockName != null) {
		    if (blockName.contains("ore"))
			blockName = blockName.replaceAll("ore", "");
		    if (blockName.contains("block"))
			blockName = blockName.replaceAll("block", "");
		    if (name.equalsIgnoreCase(blockName))
			return block;
		}
	    }
	return Block.stone;
    }

    public static Block getBlockById(final int id) {
	for (Block block : Block.blocksList)
	    if (block.blockID == id)
		return block;
	return Block.stone;
    }
}