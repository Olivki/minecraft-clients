package se.proxus.kanon.gui.framesold.components;

import net.minecraft.client.gui.FontRenderer;
import se.proxus.kanon.gui.framesold.IComponent;
import se.proxus.kanon.modules.Module;

import static se.proxus.kanon.render.Render2D.*;

public class Toggle implements IComponent {

    private String text;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean state;
    private Module module;
    private Frame frame;

    public Toggle(final int x, final int y, final int width, final int height, final Module module,
            final Frame frame) {
        setText(module.getName());
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        setModule(module);
        setFrame(frame);
    }

    @Override
    public void draw(final FontRenderer font, final int x, final int y, final float ticks) {
        setState(getModule().getState());
        final boolean hovering = isHovering(x, y) && !getFrame().isObstructed(x, y);
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
            // getModule().getKanon().getLogger().info("Hovering: " + getText());
            drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), 0x30000000);
        }
        font.drawString(getText(), getX() + getWidth() / 2 - font.getStringWidth(getText()) / 2,
                getY() + 2, hovering ? 0xFFFFFF55 : 0xFFFFFFFF);
    }

    @Override
    public void mouseClicked(final int x, final int y, final int type) {
        /*final boolean hovering = isHovering(x, y) && !getFrame().isObstructed(x, y);
        if (hovering && type == 0) {
            if (PlayerUtils.getPlayer() == null) {
                return;
            }

            if (!getModule().isToggleable()) {
                return;
            }

            MinecraftUtils.playSound("random.click", 1.0F);
            getModule().toggle();
        }
        if (hovering && type == 1) {
            MinecraftUtils.playSound("random.click", 1.0F);
            final String frameName = getModule().getKey() + " Config";
            if (!FrameManager.getInstance().containsByName(frameName)) {
                FrameManager.getInstance().addFrame(new Frame(frameName, 2, 2) {
                    @Override
                    public void init() {
                        for (final ConfigValue value : getModule().getConfig().getValues()) {
                            if (!value.isEditableViaCommand() || value.getKey().equalsIgnoreCase("State")) {
                                continue;
                            }

                            if (value.getValue() instanceof Number) {
                                addComponent(new Slider(value, -999, -999, getWidth() - 6, 12,
                                        getModule(), value.getMax(), this));
                                getKanon().getLogger().info("Found number: " + value.getKey()
                                        + " Max: " + value.getMax());
                            }

                            if (value.getValue() instanceof Boolean) {
                                addComponent(new Button(
                                        value.getKey().replace(getModule().getKey() + " ", ""), -999,
                                        -999, getWidth() - 6, 12, this) {
                                    @Override
                                    public void init() {
                                        setState(value.getBoolean());
                                    }

                                    @Override
                                    public void mouseClicked(final int x, final int y,
                                            final int type) {
                                        if (isHovering(x, y) && type == 0) {
                                            MinecraftUtils.playSound("random.click", 0.5F);
                                            setState(!value.getBoolean());
                                            getModule().getConfig().setValue(value.getKey(), getState(),
                                                    true);
                                        }
                                    }
                                });
                            }
                        }

                        addComponent(new Button("Keybind: " + getModule().getKeybind(), -999, -999,
                                getWidth() - 6, 12, this) {
                            private boolean keyTyped = false;

                            @Override
                            public void mouseClicked(final int x, final int y, final int type) {
                                if (isHovering(x, y) && type == 0) {
                                    MinecraftUtils.playSound("random.click", 1.0F);
                                    setText("Keybind: *");
                                    setKeyTyped(!isKeyTyped());
                                }
                                if (isHovering(x, y) && type == 2) {
                                    MinecraftUtils.playSound("random.click", 1.0F);
                                    getModule().getConfig().setValue("Keybind", "NONE", true);
                                    setText("Keybind: " + getModule().getKeybind());
                                    setKeyTyped(false);
                                }
                            }

                            @Override
                            public void keyTyped(final String keyName, final char keyChar) {
                                if (isKeyTyped() && !keyName.equalsIgnoreCase("ESCAPE")) {
                                    MinecraftUtils.playSound("random.click", 1.0F);
                                    getModule().getConfig().setValue("Keybind", keyName, true);
                                    setText("Keybind: " + getModule().getKeybind());
                                    setKeyTyped(false);
                                }
                            }

                            public boolean isKeyTyped() {
                                return keyTyped;
                            }

                            public void setKeyTyped(final boolean keyTyped) {
                                this.keyTyped = keyTyped;
                            }
                        });
                    }
                });
            } else if (FrameManager.getInstance().containsByName(frameName)) {
                FrameManager.getInstance().removeFrameByName(frameName);
            }
        }*/
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

    public Module getModule() {
        return module;
    }

    public void setModule(final Module module) {
        this.module = module;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(final Frame frame) {
        this.frame = frame;
    }

    @Override
    public boolean isHovering(final int x, final int y) {
        return x >= getX() && y >= getY() && x <= getX() + getWidth() && y <= getY() + getHeight();
    }
}