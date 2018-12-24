package se.proxus.kanon.gui.framesold;

import net.minecraft.client.gui.FontRenderer;

public interface IComponent {

	/**
	 * The method for drawing the components UI.
	 * 
	 * @param x
	 *            The X coordinate of the mouse.
	 * @param y
	 *            The Y coordinate of the mouse.
	 * @param ticks
	 *            The amount of ticks and shit. (Don't actually know what they
	 *            fucking use this for.)
	 */
	public void draw(FontRenderer font, int x, int y, float ticks);

	/**
	 * The method for running things if the component contains been pressed.
	 * 
	 * @param x
	 *            The X coordinate of the mouse.
	 * @param y
	 *            The Y coordinate of the mouse.
	 * @param type
	 *            What part of the mouse that got clicked. <i>(0 = Left Click, 1
	 *            = Right Click, 2 = Middle Mouse Click)</i>
	 */
	public void mouseClicked(int x, int y, int type);

	/**
	 * The method for checking if a certain key contains been typed.
	 * 
	 * @param keyName
	 *            The name of the key.
	 * @param keyChar
	 *            The character of the key.
	 */
	public void keyTyped(String keyName, char keyChar);

	/**
	 * Sets the components text.
	 * 
	 * @param text
	 *            The desired text.
	 */
	public void setText(String text);

	/**
	 * Gets the components text.
	 * 
	 * @return The components text.
	 */
	public String getText();

	/**
	 * Sets the components x coordinate.
	 * 
	 * @param x
	 *            The desired x coordinate.
	 */
	public void setX(int x);

	/**
	 * Gets the components x coordinate.
	 * 
	 * @return The components x coordinate.
	 */
	public int getX();

	/**
	 * Sets the components y coordinate.
	 * 
	 * @param y
	 *            The desired y coordinate.
	 */
	public void setY(int y);

	/**
	 * Gets the components y coordinate.
	 * 
	 * @return The components y coordinate.
	 */
	public int getY();

	/**
	 * Sets the components width.
	 * 
	 * @param width
	 *            The desired width.
	 */
	public void setWidth(int width);

	/**
	 * Gets the components width.
	 * 
	 * @return The components width.
	 */
	public int getWidth();

	/**
	 * Sets the components height.
	 * 
	 * @param height
	 *            The desired height.
	 */
	public void setHeight(int height);

	/**
	 * Gets the components height.
	 * 
	 * @return The components height.
	 */
	public int getHeight();

	/**
	 * Sets the state of the component.
	 * 
	 * @param state
	 *            The state of the component.
	 */
	public void setState(boolean state);

	/**
	 * Gets the components current state.
	 * 
	 * @return The components current state.
	 */
	public boolean getState();

	/**
	 * Gets if the mouse is hovering over the component.
	 * 
	 * @param x
	 *            The X coordinate of the mouse.
	 * @param y
	 *            The Y coordinate of the mouse
	 * @return If the mouse is hovering over the component.
	 */
	public boolean isHovering(int x, int y);

}