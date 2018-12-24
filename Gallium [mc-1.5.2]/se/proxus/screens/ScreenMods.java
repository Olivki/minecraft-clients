package se.proxus.screens;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiScreen;

import org.lwjgl.input.Mouse;

import se.proxus.mods.Mod;

public class ScreenMods extends GuiScreen {

    private static final List<Button> LOADED_BUTTONS = new LinkedList<Button>();
    private static boolean firstTime = true;

    @Override
    public void initGui() {
	if (firstTime) {
	    for (int modCounter = 0; modCounter < mc.dp.getMods()
		    .getLoadedMods().size(); modCounter++) {
		Mod mod = mc.dp.getMods().getLoadedMods().get(modCounter);
		if (!mod.getName().equalsIgnoreCase("Mods"))
		    getLoadedButtons().add(
			    new Button(mc, mod, modCounter, mod.getName(), 2,
				    2, 120, 11));
	    }
	    for (int buttonCounter = 0; buttonCounter < getLoadedButtons()
		    .size(); buttonCounter++) {
		Button button = getLoadedButtons().get(buttonCounter);
		button.setY(2 + buttonCounter * 12);
	    }
	    firstTime = false;
	}
    }

    @Override
    public void drawScreen(final int x, final int y, final float ticks) {
	super.drawScreen(x, y, ticks);
	this.drawDefaultBackground();
	for (int buttonCounter = 0; buttonCounter < getLoadedButtons().size(); buttonCounter++) {
	    Button button = getLoadedButtons().get(buttonCounter);
	    button.draw(mc.fontRenderer, x, y, ticks);
	}
    }

    @Override
    public void mouseClicked(final int x, final int y, final int type) {
	super.mouseClicked(x, y, type);
	for (Button button : getLoadedButtons())
	    button.mouseClicked(x, y, type);
    }

    public static List<Button> getLoadedButtons() {
	return LOADED_BUTTONS;
    }

    class Button extends Gui {

	private int id;
	private int x;
	private int y;
	private int width;
	private int height;
	private int dragX;
	private int dragY;
	private boolean open;
	private boolean dragging;
	private String name;
	private Mod mod;
	private Minecraft minecraft;

	public Button(final Minecraft minecraft, final Mod mod, final int id,
		final String name, final int x, final int y, final int width,
		final int height) {
	    setMinecraft(minecraft);
	    setMod(mod);
	    setId(id);
	    setName(name);
	    setX(x);
	    setY(y);
	    setWidth(width);
	    setHeight(height);
	}

	public void draw(final FontRenderer font, final int x, final int y,
		final float ticks) {
	    mouseDragged(x, y);
	    String key = getMod().getKey().replace("NONE", "-").length() > 3 ? getMod()
		    .getKey().replace("NONE", "-").substring(0, 2)
		    + getMod()
			    .getKey()
			    .replace("NONE", "-")
			    .substring(
				    getMod().getKey().replace("NONE", "-")
					    .length() - 1)
		    : getMod().getKey().replace("NONE", "-");
	    setName(getMod().getName() + ": " + key);
	    setWidth(font.getStringWidth(getName()) + 3);
	    drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(),
		    0xFF7A7A7A);
	    /*
	     * drawRect(getX() + 1, getY() + 1, getX() + getWidth() - 1, getY()
	     * + getHeight() - 1, Mouse.isButtonDown(1) && isHovering(x, y) ?
	     * 0xFF0066FF : 0xFF5CADFF);
	     */
	    drawRect(getX() + 1, getY() + 1, getX() + getWidth() - 1, getY()
		    + getHeight() - 1, Mouse.isButtonDown(1)
		    && isHovering(x, y) ? 0xFF0066FF : getMod().getColour());
	    font.drawString(getName(), getX() + 2, getY() + 2, 0xFFFFFFFF);
	}

	public void mouseDragged(final int x, final int y) {
	    if (isDragging() && Mouse.isButtonDown(0)) {
		setX(x - getDragX());
		setY(y - getDragY());
	    } else
		setDragging(false);
	}

	public void mouseClicked(final int x, final int y, final int type) {
	    if (isHovering(x, y) && type == 0) {
		setDragX(x - getX());
		setDragY(y - getY());
		setDragging(true);
		getMinecraft().sndManager.playSoundFX("random.click", 1.0F,
			1.0F);
	    }
	    if (isHovering(x, y) && type == 1) {
		getMinecraft().sndManager.playSoundFX("random.click", 1.0F,
			0.5F);
		getMod().toggle();
	    }
	}

	public boolean isHovering(final int x, final int y) {
	    return x >= getX() && y >= getY() && x <= getX() + getWidth()
		    && y <= getY() + getHeight();
	}

	public int getId() {
	    return id;
	}

	public void setId(final int id) {
	    this.id = id;
	}

	public int getX() {
	    return x;
	}

	public void setX(final int x) {
	    this.x = x;
	}

	public int getY() {
	    return y;
	}

	public void setY(final int y) {
	    this.y = y;
	}

	public int getWidth() {
	    return width;
	}

	public void setWidth(final int width) {
	    this.width = width;
	}

	public int getHeight() {
	    return height;
	}

	public void setHeight(final int height) {
	    this.height = height;
	}

	public int getDragX() {
	    return dragX;
	}

	public void setDragX(final int dragX) {
	    this.dragX = dragX;
	}

	public int getDragY() {
	    return dragY;
	}

	public void setDragY(final int dragY) {
	    this.dragY = dragY;
	}

	public String getName() {
	    return name;
	}

	public void setName(final String name) {
	    this.name = name;
	}

	public boolean isOpen() {
	    return open;
	}

	public void setOpen(final boolean open) {
	    this.open = open;
	}

	public boolean isDragging() {
	    return dragging;
	}

	public void setDragging(final boolean dragging) {
	    this.dragging = dragging;
	}

	public Mod getMod() {
	    return mod;
	}

	public void setMod(final Mod mod) {
	    this.mod = mod;
	}

	public Minecraft getMinecraft() {
	    return minecraft;
	}

	public void setMinecraft(final Minecraft minecraft) {
	    this.minecraft = minecraft;
	}
    }
}