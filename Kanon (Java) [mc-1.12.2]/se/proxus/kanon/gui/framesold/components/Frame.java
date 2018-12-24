package se.proxus.kanon.gui.framesold.components;

import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.input.Mouse;
import se.proxus.kanon.Kanon;
import se.proxus.kanon.gui.framesold.FrameManager;
import se.proxus.kanon.gui.framesold.IComponent;
import se.proxus.kanon.gui.framesold.IDraggableComponent;
import se.proxus.kanon.modules.Module;
import se.proxus.kanon.wrapper.minecraft.Minekraft;
import se.proxus.kanon.wrapper.minecraft.Screen;

import java.util.LinkedList;
import java.util.List;

public class Frame implements IComponent, IDraggableComponent {

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
	private final List<IComponent> LOADED_COMPONENTS = new LinkedList<IComponent>();
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
				if (isHovering(x, y) && type == 0 && !isObstructed(x, y)) {
                    Minekraft.playSound("random.click", 1.0F);
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
		for (Module mod : Kanon.MODULES.getModules()) {
			if (mod.getCategory().getName().equalsIgnoreCase(getText())) {
				addComponent(new Toggle(-999, -999, getWidth() - 6, 12, mod, this));
			}
		}
	}

	public void handleMouseInput() {

	}

	@Override
	public void draw(final FontRenderer font, final int x, final int y, final float ticks) {
		/*mouseDragged(x, y);
		drawBorderedRect(getX(), getY(), getX() + getWidth(),
				getY() + getHeight() + getComponentsSize()
						+ (getLoadedComponents().size() > 5 && getState() ? 0.5F : 0),
				0xFF111111, 0xFF313131);
		drawBorderedRect(getX(), getY(), getX() + getWidth(),
				getY() + getHeight() + (getState() ? 1 : 0), 0xFF111111, 0xFF2A2A2A);

		if (getState()) {
			drawRect(getX(), getY() + getHeight(), getX() + getWidth(), getY() + getHeight() + 0.5F,
					0xFF1F1F1F);
			drawRect(getX(), getY() + getHeight() + 0.5F, getX() + getWidth(),
					getY() + getHeight() + 1.0F, 0xFF363636);
		}

		font.drawString(getText(), getX() + getWidth() / 2 - font.getStringWidth(getText()) / 2,
				getY() + getHeight() / 4, 0xFFFFFFFF);
		if (getKanon().getMinecraft().currentScreen instanceof FrameManager) {
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
			startCut(getX(), getY() + getHeight(), getX() + getWidth(),
					getY() + getHeight() + getComponentsSize());
			for (int var0 = 0; var0 < getLoadedComponents().size(); var0++) {
				IComponent component = getLoadedComponents().get(var0);
				component.setX(getX() + 3);
				component.setY(getY() + getHeight() + 3 + var0 * 14 - scroll);
				component.draw(font, x, y, ticks);
			}
			stopCut();
			GL11.glPopMatrix();
		} else {
			setComponentsSize(0);
		}*/
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
		if (getY() >= Screen.getScaledHeight() - getHeight() && !getState()) {
			setY(Screen.getScaledHeight() - getHeight());
		}
		if (getY() >= Screen.getScaledHeight() - getHeight() - getComponentsSize() && getState()) {
			setY(Screen.getScaledHeight() - getHeight() - getComponentsSize());
		}
		if (getX() >= Screen.getScaledWidth() - getWidth()) {
			setX(Screen.getScaledWidth() - getWidth());
		}
	}

	@Override
	public void mouseClicked(final int x, final int y, final int type) {
		if (isHovering(x, y) && type == 0 && !isObstructed(x, y)) {
            Minekraft.playSound("random.click", 1.0F);
			setDragX(x - getX());
			setDragY(y - getY());
			setDragging(true);
			FrameManager.getInstance().setCurrentFrame(this, true);
		}
		if (isHovering(x, y) && type == 1 && !isObstructed(x, y)) {
            Minekraft.playSound("random.click", 1.0F);
			setState(!getState());
			FrameManager.getInstance().setCurrentFrame(this, true);
		}
		if (!isObstructed(x, y)) {
			getPin().mouseClicked(x, y, type);
		}
		if (getState()) {
			for (IComponent component : getLoadedComponents()) {
				if (!isObstructed(x, y)) {
					component.mouseClicked(x, y, type);
				}
			}
		}
	}

	@Override
	public void keyTyped(final String keyName, final char keyChar) {
		for (IComponent component : getLoadedComponents()) {
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

	public Kanon getKanon() {
		return Kanon.getInstance();
	}

	public IComponent addComponent(final IComponent component) {
		if (!getLoadedComponents().contains(component)) {
			getLoadedComponents().add(component);
			Kanon.LOGGER.info("Added the component '" + component.getText()
					+ "' to the frame '" + getText() + "'");
		}
		return component;
	}

	public List<IComponent> getLoadedComponents() {
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
					/*
					 * getKanon().getLogger().info( "#1 " + frame.getText() +
					 * " Obstructed by: " + getText());
					 */
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
}