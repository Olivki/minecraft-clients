package se.proxus.kanon.gui.framesold;

public interface IDraggableComponent {

	/**
	 * The method for checking when the mouse is dragged.
	 * 
	 * @param x
	 *            The X coordinate of the mouse.
	 * @param y
	 *            The Y coordinate of the mouse.
	 */
	public void mouseDragged(int x, int y);

	/**
	 * Sets the components drag x coordinate.
	 * 
	 * @param x
	 *            The desired drag x coordinate.
	 */
	public void setDragX(int x);

	/**
	 * Gets the components drag x coordinate.
	 * 
	 * @return The components drag x coordinate.
	 */
	public int getDragX();

	/**
	 * Sets the components drag y coordinate.
	 * 
	 * @param y
	 *            The desired drag y coordinate.
	 */
	public void setDragY(int y);

	/**
	 * Gets the components drag y coordinate.
	 * 
	 * @return The components drag y coordinate.
	 */
	public int getDragY();

	/**
	 * Sets the dragging state of the component.
	 * 
	 * @param state
	 *            The state of the dragging.
	 */
	public void setDragging(boolean dragging);

	/**
	 * Gets if the component is being dragged or not.
	 * 
	 * @return If the component is being dragged or not.
	 */
	public boolean isDragging();

}