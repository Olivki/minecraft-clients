package se.proxus.frames.list.frames;

import net.minecraft.client.Minecraft;
import se.proxus.Gallium;
import se.proxus.frames.list.components.Button;
import se.proxus.frames.list.components.Frame;
import se.proxus.mods.ModCategory;

public class Frames extends Frame {

    public Frames(final Gallium client) {
	super("Frames", 2, 2, client);
    }

    @Override
    public void init() {
	for (final ModCategory category : ModCategory.values()) {
	    if (category.getName().equalsIgnoreCase("None"))
		continue;
	    addComponent(new Button(category.getName(), -999, -999,
		    getWidth() - 6, 12) {
		@Override
		public void mouseClicked(final int x, final int y,
			final int type) {
		    if (isHovering(x, y) && type == 0) {
			getClient().getMinecraft().sndManager.playSoundFX(
				"random.click", 0.5F, 1.0F);
			if (!Minecraft.dp.getFrames().containsByName(
				category.getName()))
			    Minecraft.dp.getFrames().addFrame(
				    new Frame(category.getName(), 2, 2,
					    getClient()));
			else if (Minecraft.dp.getFrames().containsByName(
				category.getName()))
			    Minecraft.dp.getFrames().removeFrameByName(
				    category.getName());
			setState(Minecraft.dp.getFrames().containsByName(
				category.getName()));
		    }
		}
	    });
	}
    }
}