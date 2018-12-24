package se.proxus.frames.list.components;

import net.minecraft.src.Gui;

import org.lwjgl.opengl.GL11;

import se.proxus.frames.Component;
import se.proxus.tools.FontFactory;

public class Button extends Gui implements Component {

	private String text;
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean state;

	public Button(final String text, final int x, final int y, final int width,
			final int height) {
		setText(text);
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		init();
	}

	public void init() {

	}

	@Override
	public void draw(final FontFactory font, final int x, final int y,
			final float ticks) {
		/*
		 * drawRect(getX() - 0.5F, getY() - 0.5F, getX() + getWidth() + 0.5F,
		 * getY() + getHeight() + 0.5F, 0xFF464747);
		 * drawBorderedGradientRect(getX(), getY(), getX() + getWidth(), getY()
		 * + getHeight(), 0xFF000000, getState() ? 0xFF2E74BF : 0xFF676767,
		 * getState() ? 0xFF155BA6 : 0xFF4E4E4E); drawRect(getX() + 0.5F, getY()
		 * + 0.5F, getX() + getWidth() - 0.5F, getY() + 1.0F, getState() ?
		 * 0xFF4E94DF : 0xFF878787); drawRect(getX() + 0.5F, getY() +
		 * getHeight() - 0.5F, getX() + getWidth() - 0.5F, getY() + getHeight()
		 * - 1.0F, getState() ? 0xFF1D63AE : 0xFF565656);
		 * drawGradientRect(getX() + 0.5F, getY() + 0.5F, getX() + 1.0F, getY()
		 * + getHeight() - 0.5F, getState() ? 0xFF4E94DF : 0xFF878787,
		 * getState() ? 0xFF1D63AE : 0xFF565656); drawGradientRect(getX() +
		 * getWidth() - 1.0F, getY() + 0.5F, getX() + getWidth() - 0.5F, getY()
		 * + getHeight() - 0.5F, getState() ? 0xFF4E94DF : 0xFF878787,
		 * getState() ? 0xFF1D63AE : 0xFF565656);
		 */
		/*
		 * drawBorderedRect(getX(), getY(), getX() + getWidth(), getY() +
		 * getHeight(), 0xFF111111, 0xFF222222);
		 */
		drawBorderedGradientRect(getX(), getY(), getX() + getWidth(), getY()
				+ getHeight(), getState() ? 0xFF00152F : 0xFF111111,
						getState() ? 0xFF286EB9 : 0xFF262626, getState() ? 0xFF185EA9
								: 0xFF222222);
		drawRect(getX(), getY() + 0.5F, getX() + getWidth(), getY() + 1.0F,
				getState() ? 0xFF5695D8 : 0xFF333333);
		drawRect(getX(), getY() + getHeight() - 0.5F, getX() + getWidth(),
				getY() + getHeight() - 1.0F, getState() ? 0xFF19548E
						: 0xFF1C1C1C);
		drawGradientRect(getX(), getY() + 0.5F, getX() + 0.5F, getY()
				+ getHeight() - 0.5F, getState() ? 0xFF5695D8 : 0xFF333333,
						getState() ? 0xFF19548E : 0xFF1C1C1C);
		drawGradientRect(getX() + getWidth() - 0.5F, getY() + 0.5F, getX()
				+ getWidth(), getY() + getHeight() - 0.5F,
				getState() ? 0xFF5695D8 : 0xFF333333, getState() ? 0xFF19548E
						: 0xFF1C1C1C);
		if (isHovering(x, y))
			drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(),
					0x30000000);
		font.drawStringWithShadow(
				getText(),
				getX() + getWidth() / 2 - font.getStringWidth(getText(), 1) / 2,
				getY() + 2F, isHovering(x, y) ? 0xFFFFFF55 : 0xFFFFFFFF, 1);
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
		return x >= getX() && y >= getY() && x <= getX() + getWidth()
		&& y <= getY() + getHeight();
	}

	public void drawBaseBorderedRect(final float x, final float y,
			final float width, final float height, final int hex1,
			final int hex2) {
		drawRect(x, y + 1, width, height - 1, hex2);
		drawVerticalLine(x - 1, y, height - 1, hex1);
		drawVerticalLine(width, y, height - 1, hex1);
		drawHorizontalLine(x, width - 1, y, hex1);
		drawHorizontalLine(x, width - 1, height - 1, hex1);
	}

	public void drawBorderedRect(final float x, final float y,
			final float width, final float height, final int hex1,
			final int hex2) {
		GL11.glPushMatrix();
		GL11.glScaled(0.5D, 0.5D, 0.5D);
		drawBaseBorderedRect(x * 2, y * 2, width * 2, height * 2, hex1, hex2);
		GL11.glPopMatrix();
	}

	public void drawBaseBorderedGradientRect(final float x, final float y,
			final float width, final float height, final int hex1,
			final int hex2, final int hex3) {
		drawGradientRect(x, y + 1, width, height - 1, hex2, hex3);
		drawVerticalLine(x - 1, y, height - 1, hex1);
		drawVerticalLine(width, y, height - 1, hex1);
		drawHorizontalLine(x, width - 1, y, hex1);
		drawHorizontalLine(x, width - 1, height - 1, hex1);
	}

	public void drawBorderedGradientRect(final float x, final float y,
			final float width, final float height, final int hex1,
			final int hex2, final int hex3) {
		GL11.glPushMatrix();
		GL11.glScaled(0.5D, 0.5D, 0.5D);
		drawBaseBorderedGradientRect(x * 2, y * 2, width * 2, height * 2, hex1,
				hex2, hex3);
		GL11.glPopMatrix();
	}
}