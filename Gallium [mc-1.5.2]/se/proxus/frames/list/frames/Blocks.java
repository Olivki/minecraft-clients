package se.proxus.frames.list.frames;

import se.proxus.Gallium;
import se.proxus.frames.list.components.Frame;
import se.proxus.frames.list.components.Selection;

public class Blocks extends Frame {

    public Blocks(final Gallium client) {
	super("Blocks", 2, 2, client);
    }

    @Override
    public void init() {
	addComponent(new Selection("Test1", -999, -999, getWidth() - 6, 12, 1));
    }
}