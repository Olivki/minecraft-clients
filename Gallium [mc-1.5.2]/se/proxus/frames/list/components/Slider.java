package se.proxus.frames.list.components;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import se.proxus.frames.Component;
import se.proxus.frames.DraggableComponent;
import se.proxus.mods.Mod;
import se.proxus.tools.FontFactory;

public class Slider extends Gui implements Component, DraggableComponent {

	private String text;
	private String value;
	private int x;
	private int y;
	private int width;
	private int height;
	private int dragX;
	private int dragY;
	private int id;
	private double max;
	private double amount;
	private boolean state;
	private boolean dragging;
	private Mod mod;

	public Slider(final String text, final int x, final int y, final int width,
			final int height, final Mod mod, final double max, final int id) {
		setText(text);
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setMod(mod);
		setMax(max);
		setId(id);
		init();
	}

	public void init() {

	}

	@Override
	public void draw(final FontFactory font, final int x, final int y,
			final float ticks) {
		handleValues();
		/*
		 * drawRect(getX() - 0.5F, getY() - 0.5F, getX() + getWidth() + 0.5F,
		 * getY() + getHeight() + 0.5F, 0xFF464747);
		 * drawBorderedGradientRect(getX(), getY(), getX() + getWidth(), getY()
		 * + getHeight(), 0xFF000000, 0xFF676767, 0xFF4E4E4E); drawRect(getX() +
		 * 0.5F, getY() + 0.5F, getX() + getWidth() - 0.5F, getY() + 1.0F,
		 * 0xFF878787); drawRect(getX() + 0.5F, getY() + getHeight() - 0.5F,
		 * getX() + getWidth() - 0.5F, getY() + getHeight() - 1.0F, 0xFF565656);
		 * drawGradientRect(getX() + 0.5F, getY() + 0.5F, getX() + 1.0F, getY()
		 * + getHeight() - 0.5F, 0xFF878787, 0xFF565656);
		 * drawGradientRect(getX() + getWidth() - 1.0F, getY() + 0.5F, getX() +
		 * getWidth() - 0.5F, getY() + getHeight() - 0.5F, 0xFF878787,
		 * 0xFF565656);
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
		if (getAmount() > 0) {
			float sliderX = (int) (getAmount() * getWidth());
			/*
			 * drawBorderedGradientRect(getX(), getY(), getX() + sliderX, getY()
			 * + getHeight(), 0xFF000000, 0xFF6270DC, 0xFF3E4593);
			 * drawRect(getX() + 0.5F, getY() + 0.5F, getX() + sliderX - 0.5F,
			 * getY() + 1.0F, 0xFF8497F0); drawRect(getX() + 0.5F, getY() +
			 * getHeight() - 0.5F, getX() + sliderX - 0.5F, getY() + getHeight()
			 * - 1.0F, 0xFF4E59A8); drawGradientRect(getX() + 0.5F, getY() +
			 * 0.5F, getX() + 1.0F, getY() + getHeight() - 0.5F, 0xFF8497F0,
			 * 0xFF4E59A8); drawGradientRect(getX() + sliderX - 1.0F, getY() +
			 * 0.5F, getX() + sliderX - 0.5F, getY() + getHeight() - 0.5F,
			 * 0xFF8497F0, 0xFF4E59A8);
			 */
			drawBorderedGradientRect(getX(), getY(), getX() + sliderX, getY()
					+ getHeight(), 0xFF111111, 0xFF286EB9, 0xFF185EA9);
			drawRect(getX(), getY() + 0.5F, getX() + sliderX, getY() + 1.0F,
					0xFF5695D8);
			drawRect(getX(), getY() + getHeight() - 0.5F, getX() + sliderX,
					getY() + getHeight() - 1.0F, 0xFF19548E);
			drawGradientRect(getX(), getY() + 0.5F, getX() + 0.5F, getY()
					+ getHeight() - 0.5F, 0xFF5695D8, 0xFF19548E);
			drawGradientRect(getX() + sliderX - 0.5F, getY() + 0.5F, getX()
					+ sliderX, getY() + getHeight() - 0.5F, 0xFF5695D8,
					0xFF19548E);
		}
		if (isHovering(x, y))
			drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(),
					0x30000000);
		font.drawStringWithShadow(
				getText() + ": " + getValue(),
				getX() + getWidth() / 2
				- font.getStringWidth(getText() + ": " + getValue(), 1)
				/ 2, getY() + 2F, isHovering(x, y) ? 0xFFFFFF55
						: 0xFFFFFFFF, 1);
		mouseDragged(x, y);
	}

	@Override
	public void mouseDragged(final int x, final int y) {
		if (Mouse.isButtonDown(0) && isDragging()) {
			setAmount((float) (((double) x - (double) getX()) / getWidth()));
			if (getAmount() < 0.0F)
				setAmount(0.0F);
			if (getAmount() > 1.0F)
				setAmount(1.0F);
			setAmtInOptions(Double.valueOf(getAmount() * getMax()));
			getMod().saveSettings();
		} else
			setDragging(false);
	}

	@Override
	public void mouseClicked(final int x, final int y, final int type) {
		if (isHovering(x, y) && type == 0) {
			Minecraft.getMinecraft().sndManager.playSoundFX("random.click",
					1.0F, 1.0F);
			setAmount((float) (((double) x - (double) getX()) / getWidth()));
			setAmtInOptions(Double.valueOf(getAmount() * getMax()));
			setDragging(true);
		} else
			setDragging(false);
	}

	@Override
	public void keyTyped(final String keyName, final char keyChar) {

	}

	public void handleValues() {
		if (mod.getSetting(getId()) instanceof Double)
			setValue("" + (int) Math.floor(getDoubleFromOptions() * 10D) / 10D);
		else if (mod.getSetting(getId()) instanceof Float)
			setValue("" + (int) Math.floor(getFloatFromOptions() * 10F) / 10D);
		else if (mod.getSetting(getId()) instanceof Integer)
			setValue("" + (int) Math.floor(getIntegerFromOptions() * 10) / 10);
		else if (mod.getSetting(getId()) instanceof Long)
			setValue("" + (int) Math.floor(getLongFromOptions() * 10L) / 10L);

		if (mod.getSetting(getId()) instanceof Double)
			setAmount((float) (getDoubleFromOptions() / getMax()));
		else if (this.mod.getSetting(getId()) instanceof Float)
			setAmount((float) (getFloatFromOptions() / getMax()));
		else if (this.mod.getSetting(getId()) instanceof Integer)
			setAmount((float) (getIntegerFromOptions() / getMax()));
		else if (this.mod.getSetting(getId()) instanceof Long)
			setAmount((float) (getLongFromOptions() / getMax()));
	}

	public double getDoubleFromOptions() {
		try {
			return ((Double) mod.getSetting(getId())).doubleValue();
		} catch (Exception exception) {
			return 1.0D;
		}
	}

	public float getFloatFromOptions() {
		try {
			return ((Float) mod.getSetting(getId())).floatValue();
		} catch (Exception exception) {
			return 1.0F;
		}
	}

	public int getIntegerFromOptions() {
		try {
			return ((Integer) mod.getSetting(getId())).intValue();
		} catch (Exception exception) {
			return 1;
		}
	}

	public long getLongFromOptions() {
		try {
			return ((Long) mod.getSetting(getId())).longValue();
		} catch (Exception exception) {
			return 1L;
		}
	}

	public void setAmtInOptions(final Object obj) {
		try {
			if (mod.getSetting(getId()) instanceof Double)
				mod.registerSetting(getId(), obj, getText(), max, true, true,
						false);
			else if (mod.getSetting(getId()) instanceof Float)
				mod.registerSetting(getId(),
						Float.valueOf(((Double) obj).floatValue()), getText(),
						max, true, true, false);
			else if (mod.getSetting(getId()) instanceof Integer)
				mod.registerSetting(getId(),
						Integer.valueOf(((Double) obj).intValue()), getText(),
						max, true, true, false);
			else if (mod.getSetting(getId()) instanceof Long)
				mod.registerSetting(getId(),
						Long.valueOf(((Double) obj).longValue()), getText(),
						max, true, true, false);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
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

	@Override
	public void setDragX(final int x) {
		this.dragX = x;
	}

	@Override
	public int getDragX() {
		return dragX;
	}

	@Override
	public void setDragY(final int y) {
		this.dragY = y;
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

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
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