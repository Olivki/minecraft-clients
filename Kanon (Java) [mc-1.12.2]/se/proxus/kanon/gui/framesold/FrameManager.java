package se.proxus.kanon.gui.framesold;


import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import se.proxus.kanon.Kanon;
import se.proxus.kanon.event4j.EventSubscribe;
import se.proxus.kanon.event4j.events.client.init.EventPostInitialization;
import se.proxus.kanon.gui.framesold.components.Frame;
import se.proxus.kanon.wrapper.minecraft.Minekraft;

import java.util.LinkedList;
import java.util.List;


public class FrameManager extends GuiScreen {

    private static final List<Frame> LOADED_FRAMES = new LinkedList<Frame>();
    private static FrameManager instance;
    private Frame currentFrame;

    @EventSubscribe
    public void onPostInitialization(final EventPostInitialization event) {
        getLoadedFrames().clear();
        addFrame(new Frames());
        // addFrame(new Blocks(getKanon()));
    }

    public Frame addFrame(final Frame frame) {
        if (getFrameByName(frame.getText()) == null) {
            getLoadedFrames().add(frame);
        }

        return frame;
    }

    public Frame getFrameByName(final String name) {
        Frame tempFrame = null;
        for (final Frame frame : getLoadedFrames()) {
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
        for (final Frame frame : getLoadedFrames()) {
            if (frame.getText().equalsIgnoreCase(name)) {
                removeFrame(frame);
            }
        }
    }

    public boolean containsByName(final String name) {
        for (final Frame frame : getLoadedFrames()) {
            if (frame.getText().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void drawScreen(final int x, final int y, final float ticks) {
        super.drawScreen(x, y, ticks);
        for (final Frame frame : getLoadedFrames()) {
            frame.draw(Minekraft.getFont(), x, y, ticks);
        }
    }

    @Override
    public void mouseClicked(final int x, final int y, final int type) {
        try {
            super.mouseClicked(x, y, type);
            for (final Frame frame : getLoadedFrames()) {
                frame.mouseClicked(x, y, type);
            }
        } catch (final Exception exception) {

        }
    }

    @Override
    public void keyTyped(final char keyChar, final int keyID) {
        try {
            super.keyTyped(keyChar, keyID);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        for (final Frame frame : getLoadedFrames()) {
            frame.keyTyped(Keyboard.getKeyName(keyID), keyChar);
        }
    }

    @Override
    public void handleMouseInput() {
        try {
            super.handleMouseInput();
            for (final Frame frame : getLoadedFrames()) {
                frame.handleMouseInput();
            }
        } catch (final Exception exception) {

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

    public Kanon getClient() {
        return Kanon.getInstance();
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