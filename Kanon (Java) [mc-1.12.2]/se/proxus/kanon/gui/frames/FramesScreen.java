package se.proxus.kanon.gui.frames;

import net.minecraft.client.gui.GuiScreen;
import org.darkstorm.minecraft.gui.GuiManager;
import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.component.Frame;

import java.awt.*;
import java.io.IOException;

public class FramesScreen extends GuiScreen {

    private final GuiManager guiManager;

    public FramesScreen() {
        guiManager = new FramesManager();
    }

    @Override
    protected void mouseClicked(final int x, final int y, final int button) throws IOException {
        super.mouseClicked(x, y, button);

        for (final Frame frame : guiManager.getFrames()) {
            if (!frame.isVisible()) {
                continue;
            }

            if (!frame.isMinimized() && !frame.getArea().contains(x, y)) {
                for (final Component component : frame.getChildren()) {
                    for (final Rectangle area : component.getTheme().getUIForComponent(component)
                            .getInteractableRegions(component)) {
                        if (area.contains(x - frame.getX() - component.getX(), y - frame.getY() - component.getY())) {
                            frame.onMousePress(x - frame.getX(), y - frame.getY(), button);
                            guiManager.bringForward(frame);
                            return;
                        }
                    }
                }
            }
        }

        for (final Frame frame : guiManager.getFrames()) {
            if (!frame.isVisible()) {
                continue;
            }

            if (!frame.isMinimized() && frame.getArea().contains(x, y)) {
                frame.onMousePress(x - frame.getX(), y - frame.getY(), button);
                guiManager.bringForward(frame);
                break;
            } else if (frame.isMinimized()) {
                for (final Rectangle area : frame.getTheme().getUIForComponent(frame)
                        .getInteractableRegions(frame)) {
                    if (area.contains(x - frame.getX(), y - frame.getY())) {
                        frame.onMousePress(x - frame.getX(), y - frame.getY(), button);
                        guiManager.bringForward(frame);
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void mouseReleased(final int x, final int y, final int button) {
        super.mouseReleased(x, y, button);
        for (final Frame frame : guiManager.getFrames()) {
            if (!frame.isVisible()) { continue; }
            if (!frame.isMinimized() && !frame.getArea().contains(x, y)) {
                for (final Component component : frame.getChildren()) {
                    for (final Rectangle area : component.getTheme().getUIForComponent(component)
                            .getInteractableRegions(component)) {
                        if (area.contains(x - frame.getX() - component.getX(),
                                y - frame.getY() - component.getY())) {
                            frame.onMouseRelease(x - frame.getX(), y - frame.getY(), button);
                            guiManager.bringForward(frame);
                            return;
                        }
                    }
                }
            }
        }
        for (final Frame frame : guiManager.getFrames()) {
            if (!frame.isVisible()) { continue; }
            if (!frame.isMinimized() && frame.getArea().contains(x, y)) {
                frame.onMouseRelease(x - frame.getX(), y - frame.getY(), button);
                guiManager.bringForward(frame);
                break;
            } else if (frame.isMinimized()) {
                for (final Rectangle area : frame.getTheme().getUIForComponent(frame)
                        .getInteractableRegions(frame)) {
                    if (area.contains(x - frame.getX(), y - frame.getY())) {
                        frame.onMouseRelease(x - frame.getX(), y - frame.getY(), button);
                        guiManager.bringForward(frame);
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void drawScreen(final int par2, final int par3, final float par4) {
        guiManager.render();
        super.drawScreen(par2, par3, par4);
    }

    public GuiManager getManager() {
        return guiManager;
    }
}