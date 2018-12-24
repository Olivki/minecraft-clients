package se.proxus.owari.frames.components;

import java.io.IOException;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import se.proxus.owari.frames.Component;
import se.proxus.owari.frames.DraggableComponent;
import se.proxus.owari.mods.Mod;
import se.proxus.owari.tools.Tools;

public class Slider extends Gui implements Component, DraggableComponent {

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
	private Mod mod;
	private Frame frame;

	public Slider(final String text, final int x, final int y, final int width, final int height,
			final Mod mod, final double max, final Frame frame) {
		setText(text);
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setMod(mod);
		setMax(max);
		setFrame(frame);
		init();
	}

	public void init() {

	}

	@Override
	public void draw(final FontRenderer font, final int x, final int y, final float ticks) {
		boolean hovering = isHovering(x, y) && !getFrame().isObstructed(x, y);
		handleValues();
		drawBorderedGradientRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(),
				getState() ? 0xFF00152F : 0xFF111111, getState() ? 0xFF286EB9 : 0xFF262626,
				getState() ? 0xFF185EA9 : 0xFF222222);
		drawRect(getX(), getY(), getX() + getWidth(), getY(), getState() ? 0xFF5695D8 : 0xFF333333);
		drawRect(getX(), getY() + getHeight(), getX() + getWidth(), getY() + getHeight(),
				getState() ? 0xFF19548E : 0xFF1C1C1C);
		if (getAmount() > 0) {
			int sliderX = (int) (getAmount() * getWidth());
			drawBorderedGradientRect(getX(), getY(), getX() + sliderX, getY() + getHeight(),
					0xFF111111, 0xFF286EB9, 0xFF185EA9);
			drawRect(getX(), getY(), getX() + sliderX, getY(), 0xFF5695D8);
			drawRect(getX(), getY() + getHeight(), getX() + sliderX, getY() + getHeight(),
					0xFF19548E);
		}
		if (hovering) {
			drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), 0x30000000);
		}
		font.drawStringWithShadow(getText() + ": " + getValue(),
				getX() + getWidth() / 2 - font.getStringWidth(getText() + ": " + getValue()) / 2,
				getY() + 2, hovering ? 0xFFFFFF55 : 0xFFFFFFFF);
		mouseDragged(x, y);
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
			setAmtInOptions(Double.valueOf(getAmount() * getMax()));
			try {
				getMod().saveConfig();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			setDragging(false);
		}
	}

	@Override
	public void mouseClicked(final int x, final int y, final int type) {
		if (isHovering(x, y) && type == 0) {
			Tools.playSound("random.click", 1.0F);
			setAmount((float) (((double) x - (double) getX()) / getWidth()));
			setAmtInOptions(Double.valueOf(getAmount() * getMax()));
			setDragging(true);
		} else {
			setDragging(false);
		}
	}

	@Override
	public void keyTyped(final String keyName, final char keyChar) {

	}

	public void handleValues() {
		mod.setAmtInValue(getValue(), getText());
	}

	public void setAmtInOptions(final Object obj) {
		mod.setAmtInValue(obj, getText());
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
		this.value = value;
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

	public Mod getMod() {
		return mod;
	}

	public void setMod(final Mod mod) {
		this.mod = mod;
	}
}