package se.proxus.kanon.gui.framesold;


import se.proxus.kanon.gui.framesold.components.Button;
import se.proxus.kanon.gui.framesold.components.Frame;
import se.proxus.kanon.modules.Module;
import se.proxus.kanon.wrapper.minecraft.Minekraft;

public class Frames extends Frame {

	public Frames() {
		super("Frames", 2, 2);
	}

	@Override
	public void init() {
		for (final Module.Category category : Module.Category.values()) {
			if (category.getName().equalsIgnoreCase("None")) {
				continue;
			}
			addComponent(new Button(category.getName(), -999, -999, getWidth() - 6, 12, this) {
				@Override
				public void mouseClicked(final int x, final int y, final int type) {
					if (isHovering(x, y) && type == 0) {
                        Minekraft.playSound("random.click", 0.5F);
						if (!FrameManager.getInstance().containsByName(category.getName())) {
							FrameManager.getInstance()
									.addFrame(new Frame(category.getName(), 2, 2));
						} else if (FrameManager.getInstance().containsByName(category.getName())) {
							FrameManager.getInstance().removeFrameByName(category.getName());
						}
						setState(FrameManager.getInstance().containsByName(category.getName()));
					}
				}
			});
		}
	}
}