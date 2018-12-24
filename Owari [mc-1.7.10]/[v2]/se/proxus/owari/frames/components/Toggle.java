package se.proxus.owari.frames.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

import org.lwjgl.opengl.GL11;

import se.proxus.owari.config.Value;
import se.proxus.owari.frames.Component;
import se.proxus.owari.frames.FrameManager;
import se.proxus.owari.mods.Mod;
import se.proxus.owari.tools.Tools;

public class Toggle extends Gui implements Component {

	private String text;
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean state;
	private Mod mod;
	private Frame frame;

	public Toggle(final int x, final int y, final int width, final int height, final Mod mod,
			final Frame frame) {
		setText(mod.getName());
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setMod(mod);
		setFrame(frame);
	}

	@Override
	public void draw(final FontRenderer font, final int x, final int y, final float ticks) {
		setState(getMod().getState());
		boolean hovering = isHovering(x, y) && !getFrame().isObstructed(x, y);
		drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), getState() ? 0x90FF5600
				: 0x50111111);
		drawRect(getX(), getY() + getHeight(), getX() + getWidth(), getY() + getHeight(),
				getState() ? 0xFF19548E : 0xFF1C1C1C);
		if (hovering) {
			drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), 0x30000000);
		}
		font.drawString(getText(), getX() + getWidth() / 2 - font.getStringWidth(getText()) / 2,
				getY() + 2, hovering ? 0xFFFFFF55 : 0xFFFFFFFF);
	}

	@Override
	public void mouseClicked(final int x, final int y, final int type) {
		boolean hovering = isHovering(x, y) && !getFrame().isObstructed(x, y);
		if (hovering && type == 0) {
			if (Minecraft.getMinecraft().thePlayer == null) {
				return;
			}
			Tools.playSound("random.click", 1.0F);
			getMod().toggle();
		}
		if (hovering && type == 1) {
			Tools.playSound("random.click", 1.0F);
			Frame frame = new Frame(getMod().getName() + " Config", 0, 2) {
				@Override
				public void init() {
					for (final Value value : getMod().getValues()) {
						if (!value.isEditable() || value.getName().equalsIgnoreCase("State")) {
							continue;
						}
						if (Tools.isNumber(value)) {
							addComponent(new Slider(value.getName(), 0, 0, getWidth() - 6, 12,
									getMod(), value.getMax(), getFrame()));
						}
						if (value.getValue() instanceof Boolean) {
							addComponent(new Button(value.getName().replace(
									getMod().getName() + " ", ""), 0, 0, getWidth() - 6, 12,
									getFrame()) {
								@Override
								public void init() {
									setState(value.getBoolean());
								}

								@Override
								public void mouseClicked(final int x, final int y, final int type) {
									if (isHovering(x, y) && type == 0) {
										Tools.playSound("random.click", 0.5F);
										setState(!value.getBoolean());
										getMod().setValue(value.getName(), getState(), true);
									}
								}
							});
						}
					}
					addComponent(new Button("Keybind: " + getMod().getKeybind(), 0, 0,
							getWidth() - 6, 12, getFrame()) {
						private boolean keyTyped = false;

						@Override
						public void mouseClicked(final int x, final int y, final int type) {
							if (isHovering(x, y) && type == 0) {
								Tools.playSound("random.click", 1.0F);
								setText("Keybind: *");
								setKeyTyped(!isKeyTyped());
							}
							if (isHovering(x, y) && type == 2) {
								Tools.playSound("random.click", 1.0F);
								getMod().setValue("Keybind", "NONE", true);
								setText("Keybind: " + getMod().getKeybind());
								setKeyTyped(false);
							}
						}

						@Override
						public void keyTyped(final String keyName, final char keyChar) {
							if (isKeyTyped() && !keyName.equalsIgnoreCase("ESCAPE")) {
								Tools.playSound("random.click", 1.0F);
								getMod().setValue("Keybind", keyName, true);
								setText("Keybind: " + getMod().getKeybind());
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
			};

			if (!FrameManager.getInstance().containsByName(frame.getText())) {
				FrameManager.getInstance().addFrame(frame);
			} else if (FrameManager.getInstance().containsByName(frame.getText())) {
				FrameManager.getInstance().removeFrameByName(frame.getText());
			}
		}
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

	public Mod getMod() {
		return mod;
	}

	public void setMod(final Mod mod) {
		this.mod = mod;
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

	public void drawBaseBorderedRect(final int x, final int y, final int width, final int height,
			final int hex1, final int hex2) {
		drawRect(x, y + 1, width, height - 1, hex2);
		drawVerticalLine(x - 1, y, height - 1, hex1);
		drawVerticalLine(width, y, height - 1, hex1);
		drawHorizontalLine(x, width - 1, y, hex1);
		drawHorizontalLine(x, width - 1, height - 1, hex1);
	}

	public void drawBorderedRect(final int x, final int y, final int width, final int height,
			final int hex1, final int hex2) {
		GL11.glPushMatrix();
		GL11.glScaled(0.5D, 0.5D, 0.5D);
		drawBaseBorderedRect(x * 2, y * 2, width * 2, height * 2, hex1, hex2);
		GL11.glPopMatrix();
	}

	public void drawBaseBorderedGradientRect(final int x, final int y, final int width,
			final int height, final int hex1, final int hex2, final int hex3) {
		drawGradientRect(x, y + 1, width, height - 1, hex2, hex3);
		drawVerticalLine(x - 1, y, height - 1, hex1);
		drawVerticalLine(width, y, height - 1, hex1);
		drawHorizontalLine(x, width - 1, y, hex1);
		drawHorizontalLine(x, width - 1, height - 1, hex1);
	}

	public void drawBorderedGradientRect(final int x, final int y, final int width,
			final int height, final int hex1, final int hex2, final int hex3) {
		GL11.glPushMatrix();
		GL11.glScaled(0.5D, 0.5D, 0.5D);
		drawBaseBorderedGradientRect(x * 2, y * 2, width * 2, height * 2, hex1, hex2, hex3);
		GL11.glPopMatrix();
	}
}