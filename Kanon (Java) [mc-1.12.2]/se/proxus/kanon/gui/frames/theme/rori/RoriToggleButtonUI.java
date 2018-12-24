package se.proxus.kanon.gui.frames.theme.rori;

import net.minecraft.client.gui.FontRenderer;
import org.darkstorm.minecraft.gui.component.CheckButton;
import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.component.Frame;
import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
import org.darkstorm.minecraft.gui.theme.Theme;
import org.darkstorm.minecraft.gui.util.RenderUtil;
import se.proxus.kanon.gui.frames.FramesScreen;
import se.proxus.kanon.wrapper.minecraft.Screen;

import java.awt.*;

import static se.proxus.kanon.render.Render2D.*;

public class RoriToggleButtonUI extends AbstractComponentUI<CheckButton> {

    private final RoriTheme theme;

    RoriToggleButtonUI(final RoriTheme theme) {
        super(CheckButton.class);
        this.theme = theme;

        foreground = Color.WHITE;
        background = new Color(128, 128, 128, 128);
    }

    @Override
    protected void renderComponent(final CheckButton button) {
        translateComponent(button, false);
        final FontRenderer font = theme.getFontRenderer();
        final Rectangle area = button.getArea();
        final int size = area.height - 4;

        if (button.isSelected()) {
            drawBorderedGradientRect(0, 0, area.width, area.height, 0xFF00152F, 0xFF286EB9, 0xFF185EA9);
            drawRect(0, 0.5F, area.width, 1.0F, 0xFF5695D8);
            drawRect(0, area.height - 0.5F, area.width, area.height - 1.0F, 0xFF19548E);
            drawGradientRect(0, 0.5F, 0.5F, area.height - 0.5F, 0xFF5695D8, 0xFF19548E);
            drawGradientRect(area.width - 0.5F, 0.5F, area.width, area.height - 0.5F, 0xFF5695D8, 0xFF19548E);
        } else {
            drawBorderedGradientRect(0, 0, area.width, area.height, 0xFF111111, 0xFF262626, 0xFF222222);
            drawRect(0, 0.5F, area.width, 1.0F, 0xFF333333);
            drawRect(0, area.height - 0.5F, area.width, area.height - 1.0F, 0xFF1C1C1C);
            drawGradientRect(0, 0.5F, 0.5F, area.height - 0.5F, 0xFF333333, 0xFF1C1C1C);
            drawGradientRect(area.width - 0.5F, 0.5F, area.width, area.height - 0.5F, 0xFF333333, 0xFF1C1C1C);
        }

        final Point mouse = RenderUtil.calculateMouseLocation();
        Component parent = button.getParent();
        boolean obstructed = false;

        while (parent != null) {
            mouse.x -= parent.getX();
            mouse.y -= parent.getY();

            if (button.getParent() instanceof Frame) {
                final Frame frame = (Frame) parent;
                final Theme theme = frame.getTheme();
                final RoriFrameUI frameUI = (RoriFrameUI) theme.getUIForComponent(frame);

                obstructed = frameUI.isObstructed();
            }

            parent = parent.getParent();

            //System.out.println(button.getParent().getClass().getKey());
        }


        //Try using the isObstructed method from the old system to check.

        final boolean hovering = area.contains(mouse) && Screen.isCurrent(FramesScreen.class) && !obstructed;

        if (hovering) {
            drawRect(0, 0, area.width, area.height, 0x30000000);
        }

        final String text = button.getText();
        final int fontX = area.width / 2 - font.getStringWidth(text) / 2;
        final float fontY = area.height / 2 - font.FONT_HEIGHT / 2;

        font.drawString(text, fontX, fontY + 1, hovering ? 0xFFFFFF55 : 0xFFFFFFFF, false);

        translateComponent(button, true);
    }

    @Override
    protected Dimension getDefaultComponentSize(final CheckButton component) {
        return new Dimension(theme.getFontRenderer().getStringWidth(component.getText()) +
                theme.getFontRenderer().FONT_HEIGHT + 6, theme.getFontRenderer().FONT_HEIGHT + 4);
    }

    @Override
    protected Rectangle[] getInteractableComponentRegions(final CheckButton component) {
        return new Rectangle[]{new Rectangle(0, 0, component.getWidth(),
                component.getHeight())};
    }

    @Override
    protected void handleComponentInteraction(final CheckButton component, final Point location,
            final int button) {
        if (location.x <= component.getWidth() && location.y <= component.getHeight()) {
            component.press(button);
        }
    }
}
