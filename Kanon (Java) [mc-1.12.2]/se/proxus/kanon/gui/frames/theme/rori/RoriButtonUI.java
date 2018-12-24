package se.proxus.kanon.gui.frames.theme.rori;

import net.minecraft.client.gui.FontRenderer;
import org.darkstorm.minecraft.gui.component.Button;
import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
import org.darkstorm.minecraft.gui.util.RenderUtil;
import org.lwjgl.input.Mouse;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class RoriButtonUI extends AbstractComponentUI<Button> {

    private final RoriTheme theme;

    RoriButtonUI(final RoriTheme theme) {
        super(Button.class);
        this.theme = theme;

        foreground = Color.WHITE;
        background = new Color(128, 128, 128, 128 + 128 / 2);
    }

    @Override
    protected void renderComponent(final Button button) {
        final FontRenderer font = theme.getFontRenderer();
        translateComponent(button, false);
        final Rectangle area = button.getArea();

        final Point mouse = RenderUtil.calculateMouseLocation();
        Component parent = button.getParent();
        while (parent != null) {
            mouse.x -= parent.getX();
            mouse.y -= parent.getY();
            parent = parent.getParent();
        }

        if (area.contains(mouse)) {
            glColor4f(0.0f, 0.0f, 0.0f, Mouse.isButtonDown(0) ? 0.5f : 0.3f);
            glBegin(GL_QUADS);
            {
                glVertex2d(0, 0);
                glVertex2d(area.width, 0);
                glVertex2d(area.width, area.height);
                glVertex2d(0, area.height);
            }
            glEnd();
        }

        glEnable(GL_TEXTURE_2D);

        final String text = button.getText();
        font.drawString(text, area.width / 2 - font.getStringWidth(text) / 2,
                area.height / 2 - font.FONT_HEIGHT / 2, RenderUtil.toRGBA(button.getForegroundColor()));

        translateComponent(button, true);
    }

    @Override
    protected Dimension getDefaultComponentSize(final Button component) {
        return new Dimension(theme.getFontRenderer().getStringWidth(component.getText()) + 4,
                theme.getFontRenderer().FONT_HEIGHT + 4);
    }

    @Override
    protected Rectangle[] getInteractableComponentRegions(final Button component) {
        return new Rectangle[]{new Rectangle(0, 0, component.getWidth(),
                component.getHeight())};
    }

    @Override
    protected void handleComponentInteraction(final Button component, final Point location,
            final int button) {
        if (location.x <= component.getWidth()
                && location.y <= component.getHeight() && button == 0) {
            component.press(button);
        }
    }
}