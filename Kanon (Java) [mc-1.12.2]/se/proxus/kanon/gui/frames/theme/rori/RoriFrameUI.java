package se.proxus.kanon.gui.frames.theme.rori;

import net.minecraft.client.gui.FontRenderer;
import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.component.Frame;
import org.darkstorm.minecraft.gui.layout.Constraint;
import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
import org.darkstorm.minecraft.gui.util.RenderUtil;
import org.lwjgl.input.Mouse;
import se.proxus.kanon.Kanon;
import se.proxus.kanon.gui.frames.FramesScreen;
import se.proxus.kanon.render.Render2D;
import se.proxus.kanon.wrapper.minecraft.Screen;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static se.proxus.kanon.render.Render2D.*;

public class RoriFrameUI extends AbstractComponentUI<Frame> {

    private final RoriTheme theme;
    private Frame component;

    RoriFrameUI(final RoriTheme theme) {
        super(Frame.class);
        this.theme = theme;

        foreground = Color.WHITE;
        background = new Color(128, 128, 128, 128);
    }

    @Override
    protected void renderComponent(final Frame component) {
        this.component = component;
        final FontRenderer font = theme.getFontRenderer();
        final Rectangle area = new Rectangle(component.getArea());
        final int fontHeight = theme.getFontRenderer().FONT_HEIGHT;
        translateComponent(component, false);
        glEnable(GL_BLEND);
        glDisable(GL_CULL_FACE);
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        // Draw frame background
        if (component.isMinimized()) { area.height = fontHeight + 4; }
        final int fontY = fontHeight + 3;
        final boolean open = !component.isMinimized();
        /*Render2DUtils
                .drawBorderedRect(0, 0, area.width, area.height + (open ? 0.5F : 0), 0xFF111111, 0xFF2A2A2A);
        Render2DUtils.drawBorderedRect(0, 0, area.width, meme, 0xFF111111, 0xFF313131);*/
        if (open) {
            Render2D.drawBorderedRect(0, 0, area.width, area.height + 0.5F, 0xFF111111, 0xFF313131);
        }
        
        Render2D.drawBorderedRect(0, 0, area.width, fontY + 1, 0xFF111111, 0xFF2A2A2A);

        if (open) {
            Render2D.drawRect(0, fontY, area.width, fontY + 0.5F, 0xFF1F1F1F);
            Render2D.drawRect(0, fontY + 0.5F, area.width, fontY + 1.0F, 0xFF363636);
        }

        // Draw controls
        int offset = component.getWidth() - 2;
        final Point mouse = RenderUtil.calculateMouseLocation();
        Component parent = component;

        while (parent != null) {
            mouse.x -= parent.getX();
            mouse.y -= parent.getY();
            parent = parent.getParent();
        }

        if (Screen.isCurrent(FramesScreen.class)) {
            final boolean[] checks =
                    new boolean[]{component.isClosable(), component.isPinnable()};
            final boolean[] overlays = new boolean[]{false, component.isPinned()};

            for (int i = 0; i < checks.length; i++) {
                if (!checks[i]) {
                    continue;
                }

                final int posX = offset - fontHeight;
                final int posY = 2;
                final int posWidth = offset;
                final float posHeight = fontHeight + 1.5F;

                if (overlays[i]) {
                    drawBorderedGradientRect(posX, posY, posWidth, posHeight, 0xFF00152F, 0xFF286EB9,
                            0xFF185EA9);
                    drawRect(posX, posY + 0.5F, posWidth, posY + 1.0F, 0xFF5695D8);
                    drawRect(posX, posHeight - 0.5F, posWidth, posHeight - 1.0F, 0xFF19548E);
                    drawGradientRect(posX, posY + 0.5F, posX + 0.5F, posHeight - 0.5F, 0xFF5695D8,
                            0xFF19548E);
                    drawGradientRect(posWidth - 0.5F, posY + 0.5F, posWidth, posHeight - 0.5F, 0xFF5695D8,
                            0xFF19548E);
                } else {
                    drawBorderedGradientRect(posX, posY, posWidth, posHeight, 0xFF111111, 0xFF262626,
                            0xFF222222);
                    drawRect(posX, posY + 0.5F, posWidth, posY + 1.0F, 0xFF333333);
                    drawRect(posX, posHeight - 0.5F, posWidth, posHeight - 1.0F, 0xFF1C1C1C);
                    drawGradientRect(posX, posY + 0.5F, posX + 0.5F, posHeight - 0.5F, 0xFF333333,
                            0xFF1C1C1C);
                    drawGradientRect(posWidth - 0.5F, posY + 0.5F, posWidth, posHeight - 0.5F, 0xFF333333,
                            0xFF1C1C1C);
                }

                if (mouse.x >= offset - fontHeight && mouse.x <= offset
                        && mouse.y >= 2 && mouse.y <= fontHeight + 2) {
                    drawRect(posX, posY, posWidth, posHeight, 0x30000000);
                }

                offset -= fontHeight + 3;
            }
        }

        font.drawString(component.getTitle(), area.width / 2 - font.getStringWidth(component.getTitle()) / 2,
                fontY / 4, 0xFFFFFFFF);

        translateComponent(component, true);
    }

    @Override
    protected Rectangle getContainerChildRenderArea(final Frame container) {
        final Rectangle area = new Rectangle(container.getArea());
        area.x = 2;
        area.y = theme.getFontRenderer().FONT_HEIGHT + 5;
        area.width -= 4;
        area.height -= theme.getFontRenderer().FONT_HEIGHT + 8;
        return area;
    }

    @Override
    protected Dimension getDefaultComponentSize(final Frame component) {
        final Component[] children = component.getChildren();
        final Rectangle[] areas = new Rectangle[children.length];
        final Constraint[][] constraints = new Constraint[children.length][];

        for (int i = 0; i < children.length; i++) {
            final Component child = children[i];
            final Dimension size = child.getTheme().getUIForComponent(child).getDefaultSize(child);
            areas[i] = new Rectangle(0, 0, size.width, size.height);
            constraints[i] = component.getConstraints(child);
        }

        final Dimension size = component.getLayoutManager().getOptimalPositionedSize(areas, constraints);
        size.width = 114;
        size.height += theme.getFontRenderer().FONT_HEIGHT + 7.5;
        return size;
    }

    @Override
    protected Rectangle[] getInteractableComponentRegions(final Frame component) {
        return new Rectangle[]{new Rectangle(0, 0, component.getWidth(),
                theme.getFontRenderer().FONT_HEIGHT + 4)};
    }

    @Override
    protected void handleComponentInteraction(final Frame component, final Point location, final int button) {
        int offset = component.getWidth() - 2;
        final int textHeight = theme.getFontRenderer().FONT_HEIGHT;

        if (component.isClosable() && button == 0) {
            if (location.x >= offset - textHeight && location.x <= offset
                    && location.y >= 2 && location.y <= textHeight + 2) {
                component.close();
                return;
            }

            offset -= textHeight + 2;
        }

        if (component.isPinnable() && button == 0) {
            if (location.x >= offset - textHeight && location.x <= offset
                    && location.y >= 2 && location.y <= textHeight + 2) {
                component.setPinned(!component.isPinned());
                return;
            }

            offset -= textHeight + 2;
        }

        if (component.isMinimizable()) {
            if (location.x >= 0 && location.x <= offset && location.y >= 0 && location.y <= textHeight + 4 &&
                    Mouse.getEventButton() == 1) {
                component.setMinimized(!component.isMinimized());
                return;
            }
        }

        if (location.x >= 0 && location.x <= offset && location.y >= 0 && location.y <= textHeight + 4 &&
                button == 0) {
            component.setDragging(true);
            return;
        }
    }

    public boolean isObstructed() {
        final Point rawMouse = RenderUtil.calculateMouseLocation();
        final int x = rawMouse.x;
        final int y = rawMouse.y;

        if (getIntersectingFrames().isEmpty()) {
            return false;
        } else {
            for (final Frame frame : getIntersectingFrames()) {
                final int fontHeight = theme.getFontRenderer().FONT_HEIGHT;

                final Rectangle area = new Rectangle(frame.getArea());

                if (frame.isMinimized()) {
                    area.height = fontHeight + 4;
                }

                final Rectangle compareArea = new Rectangle(getComponent().getArea());

                if (getComponent().isMinimized()) {
                    compareArea.height = fontHeight + 4;
                }

                final Rectangle intersection = compareArea.intersection(area);
                final boolean higherIndex = getIndex(frame.getTitle()) < getIndex(getComponent().getTitle());

                if (intersection.contains(x, y) && higherIndex) {
                    return true;
                }
            }
        }

        return false;
    }

    public List<Frame> getIntersectingFrames() {
        final List<Frame> intersectingFrames = new LinkedList<>();

        for (final Frame frame : Kanon.getInstance().getFramesManager().getFrames()) {
            if (frame == getComponent()) {
                continue;
            }

            if (frame.getArea().intersects(getComponent().getArea())) {
                intersectingFrames.add(frame);
            }
        }

        return intersectingFrames;
    }

    /**
     * @param title
     *         The name of the Frame
     * @return the index of the Frame.
     *
     * @author WTG
     */
    public int getIndex(final String title) {
        for (int index = 0; index < Kanon.getInstance().getFramesManager().getFrames().length; index++) {
            final Frame frame = Kanon.getInstance().getFramesManager().getFrames()[index];

            if (title.equalsIgnoreCase(frame.getTitle())) {
                return index;
            }
        }
        return -1;
    }

    public Frame getComponent() {
        return component;
    }
}
