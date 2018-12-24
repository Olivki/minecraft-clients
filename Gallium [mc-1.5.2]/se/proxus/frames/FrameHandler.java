package se.proxus.frames;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;

import org.lwjgl.input.Keyboard;

import se.proxus.Gallium;
import se.proxus.frames.list.components.Frame;
import se.proxus.frames.list.frames.Frames;

public class FrameHandler extends GuiScreen {

    private static final List<Frame> LOADED_FRAMES = new LinkedList<Frame>();
    private Frame currentFrame;
    private Gallium client;

    public FrameHandler(final Gallium client) {
	setClient(client);
    }

    @Override
    public void initGui() {
	getButtonList().clear();
	// buttonList.add(new ButtonCheckbox(0, 10, 20, "Remember PIN Code."));
    }

    public void init() {
	getLoadedFrames().clear();
	addFrame(new Frames(getClient()));
	// addFrame(new Blocks(getClient()));
    }

    public Frame addFrame(final Frame frame) {
	if (!getLoadedFrames().contains(frame))
	    getLoadedFrames().add(frame);
	return frame;
    }

    public Frame removeFrame(final Frame frame) {
	if (getLoadedFrames().contains(frame))
	    getLoadedFrames().remove(getLoadedFrames().indexOf(frame));
	return frame;
    }

    public void removeFrameByName(final String name) {
	for (Frame frame : getLoadedFrames())
	    if (frame.getText().equalsIgnoreCase(name))
		removeFrame(frame);
    }

    public boolean containsByName(final String name) {
	for (Frame frame : getLoadedFrames())
	    if (frame.getText().equalsIgnoreCase(name))
		return true;
	return false;
    }

    @Override
    public void drawScreen(final int x, final int y, final float ticks) {
	super.drawScreen(x, y, ticks);
	drawDefaultBackground();
	for (Frame frame : getLoadedFrames())
	    frame.draw(mc.dp.getFontFactory(), x, y, ticks);
    }

    @Override
    public void mouseClicked(final int x, final int y, final int type) {
	super.mouseClicked(x, y, type);
	try {
	    for (Frame frame : getLoadedFrames())
		frame.mouseClicked(x, y, type);
	} catch (Exception exception) {

	}
    }

    @Override
    public void keyTyped(final char keyChar, final int keyID) {
	super.keyTyped(keyChar, keyID);
	for (Frame frame : getLoadedFrames())
	    frame.keyTyped(Keyboard.getKeyName(keyID), keyChar);
    }

    @Override
    public void handleMouseInput() {
	super.handleMouseInput();
	try {
	    for (Frame frame : getLoadedFrames())
		frame.handleMouseInput();
	} catch (Exception exception) {

	}
    }

    @Override
    public void actionPerformed(final GuiButton button) {
	switch (button.id) {
	case 0:
	    button.enabled = !button.enabled;
	    break;
	}
    }

    public Frame getCurrentFrame() {
	return currentFrame;
    }

    public void setCurrentFrame(final Frame currentFrame,
	    final boolean shouldSendToTop) {
	if (shouldSendToTop) {
	    removeFrame(currentFrame);
	    addFrame(currentFrame);
	}
	this.currentFrame = currentFrame;
    }

    public Gallium getClient() {
	return client;
    }

    public void setClient(final Gallium client) {
	this.client = client;
    }

    public static List<Frame> getLoadedFrames() {
	return LOADED_FRAMES;
    }
}