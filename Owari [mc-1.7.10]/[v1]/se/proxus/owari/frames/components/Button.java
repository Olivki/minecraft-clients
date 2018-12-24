package se.proxus.owari.frames.components;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

import org.lwjgl.opengl.GL11;

import se.proxus.owari.frames.Component;

public class Button extends Gui implements Component {

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
		boolean hovering = isHovering(x, y) && getFrame().isObstructed(x, y);
		drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), getState() ? 0x90FF5600
				: 0x50111111);
		if (hovering) {
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