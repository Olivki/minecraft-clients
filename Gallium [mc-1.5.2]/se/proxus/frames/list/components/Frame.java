package se.proxus.frames.list.components;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import se.proxus.Gallium;
import se.proxus.frames.Component;
import se.proxus.frames.DraggableComponent;
import se.proxus.frames.FrameHandler;
import se.proxus.mods.Mod;
import se.proxus.tools.Colours;
import se.proxus.tools.FontFactory;
import se.proxus.tools.Tools;

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
	private Gallium client;
	private final List<Component> LOADED_COMPONENTS = new LinkedList<Component>();
	private int scroll;

	public Frame(final String text, final int x, final int y, final int width,
			final int height, final Gallium client) {
		setClient(client);
		setText(text);
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		getLoadedComponents().clear();
		setPin(new Button("", getX() + getWidth() - 10, getY() + 2, 10, 10) {
			@Override
			public void mouseClicked(final int x, final int y, final int type) {
				if (isHovering(x, y) && type == 0) {
					Minecraft.getMinecraft().sndManager.playSoundFX(
							"random.click", 1.0F, 1.0F);
					setPinned(!getState());
					setState(isPinned());
				}
			}
		});
		init();
	}

	public Frame(final String text, final int x, final int y,
			final Gallium client) {
		this(text, x, y, 120, 14, client);
	}

	public void init() {
		for (Mod mod : getClient().getMods().getLoadedMods())
			if (mod.getCategory().getName().equalsIgnoreCase(getText()))
				addComponent(new Toggle(-999, -999, getWidth() - 6, 12, mod));
	}

	public void handleMouseInput() {

	}

	@Override
	public void draw(final FontFactory font, final int x, final int y,
			final float ticks) {
		mouseDragged(x, y);
		if (isHovering(x, y))
			setTicksHovered(getTicksHovered() + 1);
		if (!isHovering(x, y) || getState())
			setTicksHovered(0);
		/*
		 * drawBorderedRect(getX(), getY(), getX() + getWidth(), getY() +
		 * getHeight() + getComponentsSize(), 0xFF262626, 0xFF2B2B2B);
		 */
		drawBorderedRect(getX(), getY(), getX() + getWidth(), getY()
				+ getHeight() + getComponentsSize()
				+ (getLoadedComponents().size() > 5 && getState() ? 0.5F : 0),
				0xFF111111, 0xFF313131);
		drawBorderedRect(getX(), getY(), getX() + getWidth(), getY()
				+ getHeight() + (getState() ? 1 : 0), 0xFF111111, 0xFF2A2A2A);
		if (getState()) {
			/*
			 * drawBorderedRect(getX() + 0.5F, getY() + getHeight() - 0.5F,
			 * getX() + getWidth() - 0.5F, getY() + getHeight() +
			 * getComponentsSize() - 0.5F, 0xFF262626, 0xFF313132);
			 * drawBorderedRect(getX() + 0.5F, getY() + getHeight(), getX() +
			 * getWidth() - 0.5F, getY() + getHeight() + getComponentsSize() -
			 * 0.5F, 0xFF262626, 0xFF313132); drawRect(getX() + 1, getY() +
			 * getHeight() + 0.5F, getX() + getWidth() - 1, getY() + getHeight()
			 * + 1.0F, 0xFF373737);
			 */
			drawRect(getX(), getY() + getHeight(), getX() + getWidth(), getY()
					+ getHeight() + 0.5F, 0xFF1F1F1F);
			drawRect(getX(), getY() + getHeight() + 0.5F, getX() + getWidth(),
					getY() + getHeight() + 1.0F, 0xFF363636);
		}
		/*
		 * drawBorderedGradientRect( getX() + getWidth() / 2 -
		 * font.getStringWidth(getText(), 1) / 2 - 2, getY() + getHeight() / 4 -
		 * 1, getX() + getWidth() / 2 - font.getStringWidth(getText(), 1) / 2 +
		 * font.getStringWidth(getText(), 1) + 2, getY() + getHeight() / 4 + 9,
		 * 0xFF111111, 0xFF262626, 0xFF222222);
		 */
		font.drawString(
				getText(),
				getX() + getWidth() / 2 - font.getStringWidth(getText(), 1) / 2,
				getY() + getHeight() / 4 + (getState() ? 0.5F : 0), 0xFFFFFFFF,
				1);
		if (getClient().getMinecraft().currentScreen instanceof FrameHandler) {
			getPin().setX(getX() + getWidth() - 13);
			getPin().setY(getY() + 2);
			getPin().draw(font, x, y, ticks);
		}
		if (getPin().isHovering(x, y)) {
			String pinned = getPin().getState() ? "Unpin" : "Pin";
			drawRect(x + 6, y + 1, x + 6 + font.getStringWidth(pinned, 1) + 2,
					y + 9, 0x60000000);
			font.drawString(pinned, x + 7, y + 1, 0xFFFFFFFF, 1);
		}
		if (Tools.ticksToSeconds(getTicksHovered()) >= 5.0F && !getState()) {
			String help = "Press your " + Colours.GOLD + "Right Mouse Button"
					+ Colours.RESET + " to <open|close> <up> the frame.";
			drawRect(x + 6, y + 1, x + 6 + font.getStringWidth(help, 1) + 2,
					y + 9, 0x60000000);
			font.drawString(help, x + 7, y + 1, 0xFFFFFFFF, 1);
		}
		if (getState()) {
			setComponentsSize(getLoadedComponents().size() * 14 + 4);
			GL11.glPushMatrix();
			startCut(getX(), getY() + getHeight(), getX() + getWidth(), getY()
					+ getHeight() + getComponentsSize());
			for (int var0 = 0; var0 < getLoadedComponents().size(); var0++) {
				Component component = getLoadedComponents().get(var0);
				component.setX(getX() + 3);
				component.setY(getY() + getHeight() + 3 + var0 * 14 - scroll);
				component.draw(font, x, y, ticks);
			}
			stopCut();
			GL11.glPopMatrix();
			/*
			 * for (Component component : getLoadedComponents()) if (component
			 * instanceof Toggle) if (component.isHovering(x, y)) { Toggle
			 * toggle = (Toggle) component; String hidden =
			 * toggle.getMod().isHidden() ? "Hidden" : "Shown"; drawRect(x + 4,
			 * y + 2, x + 4 + font.getStringWidth(hidden, 1) + 2, y + 10,
			 * 0x60000000); font.drawString(hidden, x + 5, y + 2, 0xFFFFFFFF,
			 * 1); }
			 */
		} else
			setComponentsSize(0);
	}

	@Override
	public void mouseDragged(final int x, final int y) {
		if (Mouse.isButtonDown(0) && isDragging()) {
			setX(x - getDragX());
			setY(y - getDragY());
		} else
			setDragging(false);
		if (getX() <= 0)
			setX(0);
		if (getY() <= 0)
			setY(0);
		if (getY() >= Minecraft.dp.getResolution().getScaledHeight()
				- getHeight()
				&& !getState())
			setY(Minecraft.dp.getResolution().getScaledHeight() - getHeight());
		if (getY() >= Minecraft.dp.getResolution().getScaledHeight()
				- getHeight() - getComponentsSize()
				&& getState())
			setY(Minecraft.dp.getResolution().getScaledHeight() - getHeight()
					- getComponentsSize());
		if (getX() >= Minecraft.dp.getResolution().getScaledWidth()
				- getWidth())
			setX(Minecraft.dp.getResolution().getScaledWidth() - getWidth());
	}

	@Override
	public void mouseClicked(final int x, final int y, final int type) {
		if (isHovering(x, y) && type == 0 && !isObstructed(x, y)) {
			Minecraft.getMinecraft().sndManager.playSoundFX("random.click",
					1.0F, 1.0F);
			setDragX(x - getX());
			setDragY(y - getY());
			setDragging(true);
			getClient().getFrames().setCurrentFrame(this, true);
		}
		if (isHovering(x, y) && type == 1 && !isObstructed(x, y)) {
			Minecraft.getMinecraft().sndManager.playSoundFX("random.click",
					1.0F, 1.0F);
			setState(!getState());
			getClient().getFrames().setCurrentFrame(this, true);
		}
		getPin().mouseClicked(x, y, type);
		if (getState())
			for (Component component : getLoadedComponents())
				component.mouseClicked(x, y, type);
	}

	@Override
	public void keyTyped(final String keyName, final char keyChar) {
		for (Component component : getLoadedComponents())
			component.keyTyped(keyName, keyChar);
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

	public Gallium getClient() {
		return client;
	}

	public void setClient(final Gallium client) {
		this.client = client;
	}

	public Component addComponent(final Component component) {
		if (!getLoadedComponents().contains(component)) {
			getLoadedComponents().add(component);
			getClient().getLogger().log(
					Level.INFO,
					"Added the component '" + component.getText()
					+ "' to the frame '" + getText() + "'");
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
		for (Frame frame : getClient().getFrames().getLoadedFrames()) {
			if (frame == this)
				continue;

			if (!frame.getState()) {
				if (x >= frame.getX() && x <= frame.getX() + frame.getWidth()
				&& y >= frame.getY() && y <= frame.getY() + 14
				&& getIndex(frame.getText()) > getIndex(getText()))
					return true;
			} else if (x >= frame.getX()
					&& x <= frame.getX() + frame.getWidth()
					&& y >= frame.getY()
					&& y <= frame.getY() + frame.getHeight()
					&& getIndex(frame.getText()) > getIndex(getText()))
				return true;
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
		for (int index = 0; index < getClient().getFrames().getLoadedFrames()
				.size(); index++) {
			Frame frame = getClient().getFrames().getLoadedFrames().get(index);

			if (name.equalsIgnoreCase(frame.getText()))
				return index;
		}
		return -1;
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

	public void startCut(final int x, final int y, final int width,
			final int height) {
		GL11.glScissor(x * getClient().getResolution().getScaleFactor(),
				(getClient().getResolution().getScaledHeight() - height)
				* getClient().getResolution().getScaleFactor(),
				(width - x) * getClient().getResolution().getScaleFactor(),
				(height - y) * getClient().getResolution().getScaleFactor());
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
	}

	public void stopCut() {
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}
}