package se.proxus.rori.frames;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

import se.proxus.rori.Rori;
import se.proxus.rori.frames.components.Frame;
import se.proxus.rori.tools.Tools;

public class FrameManager extends GuiScreen {

	private static final List<Frame> LOADED_FRAMES = new LinkedList<Frame>();
	private static FrameManager instance;
	private Frame currentFrame;

	public void init() {
		getLoadedFrames().clear();
		addFrame(new Frames());
		// addFrame(new Blocks(getClient()));
	}

	public Frame addFrame(final Frame frame) {
		if (getFrameByName(frame.getText()) == null) {
			getLoadedFrames().add(frame);
		}
		return frame;
	}

	public Frame getFrameByName(final String name) {
		Frame tempFrame = null;
		for (Frame frame : getLoadedFrames()) {
			if (frame.getText().equalsIgnoreCase(name)) {
				tempFrame = frame;
				break;
			}
		}
		return tempFrame;
	}

	public Frame removeFrame(final Frame frame) {
		if (getLoadedFrames().contains(frame)) {
			getLoadedFrames().remove(getLoadedFrames().indexOf(frame));
		}
		return frame;
	}

	public void removeFrameByName(final String name) {
		for (Frame frame : getLoadedFrames()) {
			if (frame.getText().equalsIgnoreCase(name)) {
				removeFrame(frame);
			}
		}
	}

	public boolean containsByName(final String name) {
		for (Frame frame : getLoadedFrames()) {
			if (frame.getText().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void drawScreen(final int x, final int y, final float ticks) {
		super.drawScreen(x, y, ticks);
		for (Frame frame : getLoadedFrames()) {
			frame.draw(Tools.getMinecraft().fontRendererObj, x, y, ticks);
		}
		/*
		 * FontRenderer fontRenderer = Tools.getMinecraft().fontRendererObj;
		 * fontRenderer.drawStringWithShadow("X: " + x, 2, 22, 0xFFFFFFFF);
		 * fontRenderer.drawStringWithShadow("Y: " + y, 2, 32, 0xFFFFFFFF);
		 * Frame frame = getCurrentFrame();
		 * fontRenderer.drawStringWithShadow("Frame: " + frame.getText(), 2, 42,
		 * 0xFFFFFFFF); fontRenderer.drawStringWithShadow("Frame Index: " +
		 * frame.getIndex(frame.getText()), 2, 52, 0xFFFFFFFF);
		 * fontRenderer.drawStringWithShadow("Frame X: " + frame.getX(), 2, 62,
		 * 0xFFFFFFFF); fontRenderer.drawStringWithShadow("Frame Y: " +
		 * frame.getY(), 2, 72, 0xFFFFFFFF);
		 */
	}

	@Override
	public void mouseClicked(final int x, final int y, final int type) {
		try {
			super.mouseClicked(x, y, type);
			for (Frame frame : getLoadedFrames()) {
				frame.mouseClicked(x, y, type);
			}
		} catch (Exception exception) {

		}
	}

	@Override
	public void keyTyped(final char keyChar, final int keyID) {
		try {
			super.keyTyped(keyChar, keyID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Frame frame : getLoadedFrames()) {
			frame.keyTyped(Keyboard.getKeyName(keyID), keyChar);
		}
	}

	@Override
	public void handleMouseInput() {
		try {
			super.handleMouseInput();
			for (Frame frame : getLoadedFrames()) {
				frame.handleMouseInput();
			}
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

	public void setCurrentFrame(final Frame currentFrame, final boolean shouldSendToTop) {
		if (shouldSendToTop) {
			removeFrame(currentFrame);
			addFrame(currentFrame);
			// getLoadedFrames().set(0, currentFrame);
		}
		this.currentFrame = currentFrame;
	}

	public Rori getClient() {
		return Rori.getInstance();
	}

	public static FrameManager getInstance() {
		if (instance == null) {
			instance = new FrameManager();
		}
		return instance;
	}

	public List<Frame> getLoadedFrames() {
		return LOADED_FRAMES;
	}
}