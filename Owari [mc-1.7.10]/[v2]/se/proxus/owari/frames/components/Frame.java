package se.proxus.owari.frames.components;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import se.proxus.owari.Client;
import se.proxus.owari.frames.Component;
import se.proxus.owari.frames.DraggableComponent;
import se.proxus.owari.frames.FrameManager;
import se.proxus.owari.mods.Mod;
import se.proxus.owari.mods.ModManager;
import se.proxus.owari.tools.Tools;

public class Frame extends Gui implements Component, DraggableComponent {

	private String text;
	private int x;
	private int y;
	private int width;
	private int height;
	private int dragX;
	private int dragY;
	private int ticksHovered;
	private int componentsSize;
	private boolean state;
	private boolean pinned;
	private boolean dragging;
	private Button pin;
	private final List<Component> LOADED_COMPONENTS = new LinkedList<Component>();
	private int scroll;

	public Frame(final String text, final int x, final int y, final int width, final int height) {
		setText(text);
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		getLoadedComponents().clear();
		setPin(new Button("", getX() + getWidth() - 10, getY() + 2, 10, 10, this) {
			@Override
			public void mouseClicked(final int x, final int y, final int type) {
				if (isHovering(x, y) && type == 0) {
					Tools.playSound("random.click", 1.0F);
					setPinned(!getState());
					setState(isPinned());
				}
			}
		});
		init();
	}

	public Frame(final String text, final int x, final int y) {
		this(text, x, y, 120, 14);
	}

	public void init() {
		for (Mod mod : ModManager.getInstance().getMods()) {
			if (mod.getCategory().getName().equalsIgnoreCase(getText())) {
				addComponent(new Toggle(-999, -999, getWidth() - 6, 12, mod, this));
			}
		}
	}

	public void handleMouseInput() {

	}

	@Override
	public void draw(final FontRenderer font, final int x, final int y, final float ticks) {
		mouseDragged(x, y);
		drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), 0xF0297ACC);
		drawRect(getX(), getY() + getHeight(), getX() + getWidth(), getY() + getHeight()
				+ getComponentsSize(), 0x40111111);
		if (getState()) {
			drawRect(getX(), getY() + getHeight(), getX() + getWidth(), getY() + getHeight(),
					0xFF1F1F1F);
		}
		font.drawString(getText(), getX() + getWidth() / 2 - font.getStringWidth(getText()) / 2,
				getY() + getHeight() / 4, 0xFFFFFFFF);
		if (getClient().getMinecraft().currentScreen instanceof FrameManager) {
			getPin().setX(getX() + getWidth() - 13);
			getPin().setY(getY() + 2);
			getPin().draw(font, x, y, ticks);
		}
		if (getPin().isHovering(x, y) && !isObstructed(x, y)) {
			String pinned = getPin().getState() ? "Unpin" : "Pin";
			drawRect(x + 6, y + 1, x + 6 + font.getStringWidth(pinned) + 2, y + 9, 0x60000000);
			font.drawString(pinned, x + 7, y + 1, 0xFFFFFFFF);
		}
		if (getState()) {
			setComponentsSize(getLoadedComponents().size() * 14 + 4);
			GL11.glPushMatrix();
			startCut(getX(), getY() + getHeight(), getX() + getWidth(), getY() + getHeight()
					+ getComponentsSize());
			for (int var0 = 0; var0 < getLoadedComponents().size(); var0++) {
				Component component = getLoadedComponents().get(var0);
				component.setX(getX() + 3);
				component.setY(getY() + getHeight() + 3 + var0 * 14 - scroll);
				component.draw(font, x, y, ticks);
			}
			stopCut();
			GL11.glPopMatrix();
		} else {
			setComponentsSize(0);
		}
	}

	@Override
	public void mouseDragged(final int x, final int y) {
		if (Mouse.isButtonDown(0) && isDragging()) {
			setX(x - getDragX());
			setY(y - getDragY());
		} else {
			setDragging(false);
		}
		if (getX() <= 0) {
			setX(0);
		}
		if (getY() <= 0) {
			setY(0);
		}
		if (getY() >= Tools.getScaledHeight() - getHeight() && !getState()) {
			setY(Tools.getScaledHeight() - getHeight());
		}
		if (getY() >= Tools.getScaledHeight() - getHeight() - getComponentsSize() && getState()) {
			setY(Tools.getScaledHeight() - getHeight() - getComponentsSize());
		}
		if (getX() >= Tools.getScaledWidth() - getWidth()) {
			setX(Tools.getScaledWidth() - getWidth());
		}
	}

	@Override
	public void mouseClicked(final int x, final int y, final int type) {
		if (isHovering(x, y) && type == 0 && !isObstructed(x, y)) {
			Tools.playSound("random.click", 1.0F);
			setDragX(x - getX());
			setDragY(y - getY());
			setDragging(true);
			FrameManager.getInstance().setCurrentFrame(this, true);
		}
		if (isHovering(x, y) && type == 1 && !isObstructed(x, y)) {
			Tools.playSound("random.click", 1.0F);
			setState(!getState());
			FrameManager.getInstance().setCurrentFrame(this, true);
		}
		if (!isObstructed(x, y)) {
			getPin().mouseClicked(x, y, type);
		}
		if (getState()) {
			for (Component component : getLoadedComponents()) {
				if (!isObstructed(x, y)) {
					component.mouseClicked(x, y, type);
				}
			}
		}
	}

	@Override
	public void keyTyped(final String keyName, final char keyChar) {
		for (Component component : getLoadedComponents()) {
			component.keyTyped(keyName, keyChar);
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

	public int getComponentsSize() {
		return componentsSize;
	}

	public void setComponentsSize(final int componentsSize) {
		this.componentsSize = componentsSize;
	}

	@Override
	public int getDragY() {
		return dragY;
	}

	public int getTicksHovered() {
		return ticksHovered;
	}

	public void setTicksHovered(final int ticksHovered) {
		this.ticksHovered = ticksHovered;
	}

	@Override
	public void setDragging(final boolean dragging) {
		this.dragging = dragging;
	}

	@Override
	public boolean isDragging() {
		return dragging;
	}

	@Override
	public void setState(final boolean state) {
		this.state = state;
	}

	@Override
	public boolean getState() {
		return state;
	}

	public boolean isPinned() {
		return pinned;
	}

	public void setPinned(final boolean pinned) {
		this.pinned = pinned;
	}

	public Button getPin() {
		return pin;
	}

	public void setPin(final Button pin) {
		this.pin = pin;
	}

	public Client getClient() {
		return Client.getInstance();
	}

	public Component addComponent(final Component component) {
		if (!getLoadedComponents().contains(component)) {
			getLoadedComponents().add(component);
			getClient().getLogger().info(
					"Added the component '" + component.getText() + "' to the frame '" + getText()
							+ "'");
		}
		return component;
	}

	public List<Component> getLoadedComponents() {
		return LOADED_COMPONENTS;
	}

	@Override
	public boolean isHovering(final int x, final int y) {
		return x >= getX() && y >= getY() && x <= getX() + getWidth() - 14
				&& y <= getY() + getHeight();
	}

	/**
	 * @author WTG
	 * @param x
	 *            The X coordinate for the mouse.
	 * @param y
	 *            The Y coordinate for the mouse.
	 * @return If the Frame is obstructed.
	 */
	public boolean isObstructed(final int x, final int y) {
		for (Frame frame : FrameManager.getInstance().getLoadedFrames()) {
			if (frame == this) {
				continue;
			}

			if (!frame.getState()) {
				if (x >= frame.getX() && x <= frame.getX() + frame.getWidth() && y >= frame.getY()
						&& y <= frame.getY() + 14
						&& getIndex(frame.getText()) > getIndex(getText())) {
					return true;
				}
			} else if (x >= frame.getX() && x <= frame.getX() + frame.getWidth()
					&& y >= frame.getY()
					&& y <= frame.getY() + frame.getHeight() + frame.getComponentsSize()
					&& getIndex(frame.getText()) > getIndex(getText())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @author WTG
	 * @param name
	 *            The name of the Frame
	 * @return the index of the Frame.
	 */
	public int getIndex(final String name) {
		for (int index = 0; index < FrameManager.getInstance().getLoadedFrames().size(); index++) {
			Frame frame = FrameManager.getInstance().getLoadedFrames().get(index);

			if (name.equalsIgnoreCase(frame.getText())) {
				return index;
			}
		}
		return -1;
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

	public void startCut(final int x, final int y, final int width, final int height) {
		GL11.glScissor(x * Tools.getResolution().getScaleFactor(), (Tools.getResolution()
				.getScaledHeight() - height) * Tools.getResolution().getScaleFactor(), (width - x)
				* Tools.getResolution().getScaleFactor(), (height - y)
				* Tools.getResolution().getScaleFactor());
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
	}

	public void stopCut() {
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}
}