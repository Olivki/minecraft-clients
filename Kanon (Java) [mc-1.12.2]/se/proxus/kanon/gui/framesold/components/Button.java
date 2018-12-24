package se.proxus.kanon.gui.framesold.components;

import net.minecraft.client.gui.FontRenderer;
import se.proxus.kanon.gui.framesold.IComponent;

import static se.proxus.kanon.render.Render2D.*;

public class Button implements IComponent {

    private String text;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean state;
    private Frame frame;

    public Button(final String text, final int x, final int y, final int width, final int height,
            final Frame frame) {
        setText(text);
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        setFrame(frame);
        init();
    }

    public void init() {

    }

    @Override
    public void draw(final FontRenderer font, final int x, final int y, final float ticks) {
        final boolean hovering = isHovering(x, y) && !getFrame().isObstructed(x, y);
        // boolean hovering = isHovering(x, y);
        drawBorderedGradientRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(),
                getState() ? 0xFF00152F : 0xFF111111, getState() ? 0xFF286EB9 : 0xFF262626,
                getState() ? 0xFF185EA9 : 0xFF222222);
        drawRect(getX(), getY() + 0.5F, getX() + getWidth(), getY() + 1.0F,
                getState() ? 0xFF5695D8 : 0xFF333333);
        drawRect(getX(), getY() + getHeight() - 0.5F, getX() + getWidth(),
                getY() + getHeight() - 1.0F, getState() ? 0xFF19548E : 0xFF1C1C1C);
        drawGradientRect(getX(), getY() + 0.5F, getX() + 0.5F, getY() + getHeight() - 0.5F,
                getState() ? 0xFF5695D8 : 0xFF333333, getState() ? 0xFF19548E : 0xFF1C1C1C);
        drawGradientRect(getX() + getWidth() - 0.5F, getY() + 0.5F, getX() + getWidth(),
                getY() + getHeight() - 0.5F, getState() ? 0xFF5695D8 : 0xFF333333,
                getState() ? 0xFF19548E : 0xFF1C1C1C);
        if (hovering) {
            // getFrame().getKanon().getLogger().info("Hovering: " +
            // getText());
            drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), 0x30000000);
        }
        font.drawString(getText(), getX() + getWidth() / 2 - font.getStringWidth(getText()) / 2,
                getY() + 2, hovering ? 0xFFFFFF55 : 0xFFFFFFFF);
    }

    @Override
    public void mouseClicked(final int x, final int y, final int type) {

    }

    @Override
    public void keyTyped(final String keyName, final char keyChar) {

    }

    @Override
    public void setText(final String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setX(final int x) {
        this.x = x;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setY(final int y) {
        this.y = y;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setWidth(final int width) {
        this.width = width;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setHeight(final int height) {
        this.height = height;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setState(final boolean state) {
        this.state = state;
    }

    @Override
    public boolean getState() {
        return state;
    }

    @Override
    public boolean isHovering(final int x, final int y) {
        return x >= getX() && y >= getY() && x <= getX() + getWidth() && y <= getY() + getHeight();
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(final Frame frame) {
        this.frame = frame;
    }
}