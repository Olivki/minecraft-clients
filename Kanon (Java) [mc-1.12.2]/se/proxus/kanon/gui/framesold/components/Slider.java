package se.proxus.kanon.gui.framesold.components;

import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.input.Mouse;
import se.proxus.kanon.Kanon;
import se.proxus.kanon.config.Entry;
import se.proxus.kanon.gui.framesold.IComponent;
import se.proxus.kanon.gui.framesold.IDraggableComponent;
import se.proxus.kanon.modules.Module;
import se.proxus.kanon.wrapper.minecraft.Minekraft;

public class Slider implements IComponent, IDraggableComponent {

    private String text;
    private String value;
    private int x;
    private int y;
    private int width;
    private int height;
    private int dragX;
    private int dragY;
    private double max;
    private double amount;
    private boolean state;
    private boolean dragging;
    private Module module;
    private Frame frame;

    public Slider(final Entry value, final int x, final int y, final int width,
            final int height, final Module module, final double max, final Frame frame) {
        setText(value.getKey());
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        setModule(module);
        setMax(max);
        setFrame(frame);
        setValue("" + value.getValue());
        Kanon.LOGGER.info("#1 " + getAmount());

        try {
            //setAmount((float) (value.getDouble() / getMax()));
        } catch (final Exception e) {
            e.printStackTrace();
        }
    
        Kanon.LOGGER.info("#2 " + getAmount());
        init();
    }

    public void init() {
        setAmount((float) ((getAmount() - getX()) / getWidth()));
    }

    @Override
    public void draw(final FontRenderer font, final int x, final int y, final float ticks) {
        /*final boolean hovering = isHovering(x, y) && !getFrame().isObstructed(x, y);
        handleValues();
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
        // drawRect(getX(), getY(), getX() + getWidth(), getY(), getState() ?
        // 0xFF5695D8 : 0xFF333333);
        /*
         * drawRect(getX(), getY() + getHeight(), getX() + getWidth(), getY() +
		 * getHeight(), getState() ? 0xFF19548E : 0xFF1C1C1C);
		 */
        /*if (getAmount() > 0) {
            final int sliderX = (int) (getAmount() * getWidth());
            drawBorderedGradientRect(getX(), getY(), getX() + sliderX, getY() + getHeight(),
                    0xFF111111, 0xFF286EB9, 0xFF185EA9);
            drawRect(getX(), getY() + 0.5F, getX() + sliderX, getY() + 1.0F, 0xFF5695D8);
            drawRect(getX(), getY() + getHeight() - 0.5F, getX() + sliderX,
                    getY() + getHeight() - 1.0F, 0xFF19548E);
            drawGradientRect(getX(), getY() + 0.5F, getX() + 0.5F, getY() + getHeight() - 0.5F,
                    0xFF5695D8, 0xFF19548E);
            drawGradientRect(getX() + sliderX - 0.5F, getY() + 0.5F, getX() + sliderX,
                    getY() + getHeight() - 0.5F, 0xFF5695D8, 0xFF19548E);
            /*
             * drawRect(getX(), getY(), getX() + sliderX, getY(), 0xFF5695D8);
			 * drawRect(getX(), getY() + getHeight(), getX() + sliderX, getY() +
			 * getHeight(), 0xFF19548E);
			 */
        /*}
        if (hovering) {
            drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), 0x30000000);
        }
        font.drawString(getText() + ": " + getValue(),
                getX() + getWidth() / 2 - font.getStringWidth(getText() + ": " + getValue()) / 2,
                getY() + 2, hovering || isDragging() ? 0xFFFFFF55 : 0xFFFFFFFF);
        mouseDragged(x, y);*/
    }

    @Override
    public void mouseDragged(final int x, final int y) {
        if (Mouse.isButtonDown(0) && isDragging()) {
            setAmount((float) (((double) x - (double) getX()) / getWidth()));
            if (getAmount() < 0.0F) {
                setAmount(0.0F);
            }
            if (getAmount() > 1.0F) {
                setAmount(1.0F);
            }
            setAmtInOptions(getAmount() * getMax());

            /*try {
                getModule().getConfig().saveConfig();
            } catch (final IOException e) {
                e.printStackTrace();
            }*/
        } else {
            setDragging(false);
        }
    }

    @Override
    public void mouseClicked(final int x, final int y, final int type) {
        if (isHovering(x, y) && type == 0) {
            Minekraft.playSound("random.click", 1.0F);
            setAmount((float) (((double) x - (double) getX()) / getWidth()));
            setAmtInOptions(getAmount() * getMax());
            setDragging(true);
        } else {
            setDragging(false);
        }
    }

    @Override
    public void keyTyped(final String keyName, final char keyChar) {

    }

    public void handleValues() {
        // module.parseValue(getAmount(), getText());
        // setValue("" + getAmount());
    }

    public void setAmtInOptions(final Object obj) {
        getModule().getConfig().parse(getText(), "" + obj);
        setValue("" + obj);
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

    @Override
    public void setDragX(final int x) {
        dragX = x;
    }

    @Override
    public int getDragX() {
        return dragX;
    }

    @Override
    public void setDragY(final int y) {
        dragY = y;
    }

    @Override
    public int getDragY() {
        return dragY;
    }

    @Override
    public void setDragging(final boolean dragging) {
        this.dragging = dragging;
    }

    @Override
    public boolean isDragging() {
        return dragging;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        String tempValue = "";
        if (value.substring(3).equalsIgnoreCase("0")) {
            tempValue = value.substring(0, 2);
        } else {
            tempValue = value.substring(0, 3);
        }
        this.value = tempValue;
    }

    public double getMax() {
        return max;
    }

    public void setMax(final double max) {
        this.max = max;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(final double amount) {
        this.amount = amount;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(final Module module) {
        this.module = module;
    }
}