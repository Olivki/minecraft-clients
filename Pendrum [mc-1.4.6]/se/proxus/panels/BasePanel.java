package se.proxus.panels;

import java.util.ArrayList;

import net.minecraft.src.*;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import se.proxus.gui.*;
import se.proxus.mods.*;
import se.proxus.panels.list.buttons.*;
import se.proxus.utils.Colors;

public abstract class BasePanel extends Methods {

	protected String name;

	protected PanelInfo info;

	protected PanelColors colors;

	protected ArrayList<BaseButton> buttonArray;

	protected ButtonPanelTop[] buttons = new ButtonPanelTop[3];
	
	protected PanelConfig config;

	public BasePanel(String name, PanelInfo info) {
		this.setName(name);
		this.setInfo(info);
		this.setConfig(new PanelConfig(this, this.getPendrum()));
		this.setColors(new PanelColors(this.getInfo(), this));
		this.setButtonArray(new ArrayList<BaseButton>());
		this.setButtonOpen(new ButtonPanelTop("_", new ButtonInfo(this.getInfo().getX() + this.getInfo().getWidth() - 25, this.getInfo().getY() + 4, 6, 4), this, this.getInfo().isOpen()));
		this.setButtonPin(new ButtonPanelTop("*", new ButtonInfo(this.getInfo().getX() + this.getInfo().getWidth() - 15, this.getInfo().getY() + 5, 6, 4), this, this.getInfo().isPinned()));
		this.setButtonRemove(new ButtonPanelTop("X", new ButtonInfo(this.getInfo().getX() + this.getInfo().getWidth() - 5, this.getInfo().getY() + 4, 6, 4), this, false));
		this.getConfig().loadConfig();
		this.setupButtons();
	}

	public void setupButtons() {
		for(BaseMod var0 : this.getPendrum().mods.modArray) {
			if(var0.getType().getName().equalsIgnoreCase(this.getName()) && var0 != null) {
				this.addButton(new ButtonMod(new ButtonInfo(0, 0, this.getInfo().getWidth() - 6, 12), var0));
			}
		}
	}

	public void draw(int var0, int var1, TrueTypeFont var2) {
		/** DRAGGING START **/
		this.mouseDragged(var0, var1);
		/** DRAGGING STOP **/

		/** COLORS START **/
		this.getColors().setColors(var0, var1);
		/** COLORS STOP **/

		/** DRAWING START **/
		this.drawBorderedRect(this.getInfo().getX(), this.getInfo().getY(), this.getInfo().getX() + this.getInfo().getWidth(), this.getInfo().getY() + this.getInfo().getHeight() + this.getInfo().getRectHeight(), 1.5F, this.getColors().PANEL_BORDER, this.getColors().PANEL_INNER);
		this.drawBorderedRect(this.getInfo().getX(), this.getInfo().getY(), this.getInfo().getX() + this.getInfo().getWidth(), this.getInfo().getY() + this.getInfo().getHeight(), 1.5F, this.getColors().PANEL_BORDER, this.getColors().PANEL_TOP_INNER);

		var2.drawStringWithShadow(this.getName() + " [" + Colors.YELLOW + this.getButtonArray().size(), this.getInfo().getX() + 3, this.getInfo().getY() + 4, this.getColors().PANEL_NAME);
		var2.drawStringWithShadow("]", this.getInfo().getX() + 3 + var2.getStringWidth(this.getName() + " [" + this.getButtonArray().size()), this.getInfo().getY() + 4, this.getColors().PANEL_NAME);
		/** DRAWING STOP **/

		/** BUTTONS START **/
		this.handleButtons(var0, var1, var2);
		/** BUTTONS STOP **/
	}

	public void mouseClicked(int var0, int var1, int var2) {
		/** DRAGGING START **/
		if(this.isHovering(var0, var1) && !(this.isObstructed(var0, var1)) && var2 == 0) {
			this.getPendrum().utils.playSound();
			this.getInfo().setDragX(var0 - this.getInfo().getX());
			this.getInfo().setDragY(var1 - this.getInfo().getY());
			this.getInfo().setDragging(true);
			this.getPendrum().panels.setCurrentPanel(this, true);
			this.getConfig().saveConfig();
		}
		/** DRAGGING STOP **/

		/** BUTTONS START **/
		if(this.getInfo().isOpen()) {
			for(BaseButton var3 : this.getButtonArray()) {
				var3.mouseClicked(var0, var1, var2);
			}
		}

		for(ButtonPanelTop var4 : this.buttons) {
			var4.mouseClicked(var0, var1, var2);
		}
		/** BUTTONS STOP **/
	}

	public void mouseDragged(int var0, int var1) {
		/** DRAGGING START **/
		if(Mouse.isButtonDown(0) && this.getInfo().isDragging()) {
			this.getInfo().setX(var0 - this.getInfo().getDragX());
			this.getInfo().setY(var1 - this.getInfo().getDragY());
			this.getConfig().saveConfig();
		} else {
			this.getInfo().setDragging(false);
		}
		/** DRAGGING STOP **/

		/** LOGIC START **/
		this.handleLogic(var0, var1);
		/** LOGIC STOP **/
	}

	public void handleLogic(int var0, int var1) {
		ScaledResolution var2 = this.getPendrum().wrapper.getScaledResolution();

		if(this.getInfo().getX() <= 1) {
			this.getInfo().setX(1);
		} if(this.getInfo().getY() <= 1) {
			this.getInfo().setY(1);
		} if(this.getInfo().getY() >= (var2.getScaledHeight() - this.getInfo().getHeight() - 1) && !(this.getInfo().isOpen())) {
			this.getInfo().setY(var2.getScaledHeight() - this.getInfo().getHeight() - 1);
		} if(this.getInfo().getY() >= (var2.getScaledHeight() - this.getInfo().getHeight() - this.getInfo().getRectHeight() - 2) && this.getInfo().isOpen()) {
			this.getInfo().setY(var2.getScaledHeight() - this.getInfo().getHeight() - this.getInfo().getRectHeight() - 2);
		} if(this.getInfo().getX() >= (var2.getScaledWidth() - this.getInfo().getWidth() - 1)) {
			this.getInfo().setX(var2.getScaledWidth() - this.getInfo().getWidth() - 1);
		}
	}

	public void handleButtons(int var0, int var1, TrueTypeFont var2) {
		if(this.getInfo().isOpen()) {
			this.getInfo().setRectHeight(this.getButtonArray().size() * 12 + 2);

			for(int var3 = 0; var3 < this.getButtonArray().size(); var3++) {
				BaseButton var4 = (BaseButton)this.getButtonArray().get(var3);

				var4.getInfo().setX(this.getInfo().getX() + 4);
				var4.getInfo().setY(this.getInfo().getY() + this.getInfo().getHeight() + var3 * 12 + 1);
				var4.draw(var0, var1, var2);
			}
		} else {
			this.getInfo().setRectHeight(0);
		}

		for(ButtonPanelTop var4 : this.buttons) {
			if(var4 == this.getButtonOpen()) {
				var4.getInfo().setX(this.getInfo().getX() + this.getInfo().getWidth() - 29);
				var4.getInfo().setY(this.getInfo().getY() + 5);
			} if(var4 == this.getButtonPin()) {
				var4.getInfo().setX(this.getInfo().getX() + this.getInfo().getWidth() - 19);
				var4.getInfo().setY(this.getInfo().getY() + 4);
				var4.getInfo().setState(this.getInfo().isPinned());
			} if(var4 == this.getButtonRemove()) {
				var4.getInfo().setX(this.getInfo().getX() + this.getInfo().getWidth() - 9);
				var4.getInfo().setY(this.getInfo().getY() + 4);
			}
			
			var4.draw(var0, var1, var2);
		}
	}

	public void handleButtonPressed(BaseButton var0) {
		
	}

	public void addButton(BaseButton var0) {
		if(!(this.getButtonArray().contains(var0))) {
			this.getButtonArray().add(this.getButtonArray().size(), var0);
		}
	}

	/***
	 * @author WTG
	 ***/
	public boolean isObstructed(int var0, int var1) {
		for(BasePanel var2 : this.getPendrum().panels.panelArray) {
			if(var2 == this) continue;

			if(!(var2.getInfo().isOpen())){
				if(var0 >= var2.getInfo().getX() && var0 <= var2.getInfo().getX() + var2.getInfo().getWidth() && var1 >= var2.getInfo().getY() && var1 <= var2.getInfo().getY() + 12 && getIndex(var2.getName()) > getIndex(getName())) {
					return true;
				}
			} else {
				if(var0 >= var2.getInfo().getX() && var0 <= var2.getInfo().getX() + var2.getInfo().getWidth() && var1 >= var2.getInfo().getY() && var1 <= var2.getInfo().getY() + var2.getInfo().getHeight() && getIndex(var2.getName()) > getIndex(getName())) {
					return true;
				}
			}
		}

		return false;
	}

	/***
	 * @author WTG
	 ***/
	public int getIndex(String var0) {
		for(int var1 = 0 ; var1 < this.getPendrum().panels.panelArray.size(); var1++){
			BasePanel var2 = this.getPendrum().panels.panelArray.get(var1);

			if(var0.equalsIgnoreCase(var2.getName())) {
				return var1;
			}
		}
		return -1;
	}

	public boolean isHovering(int var0, int var1) {
		return var0 >= this.getInfo().getX() && var1 >= this.getInfo().getY() && var0 <= this.getInfo().getX() + this.getInfo().getWidth() - 30 && var1 <= this.getInfo().getY() + this.getInfo().getHeight();
	}

	public String getName() {
		return this.name;
	}

	public PanelInfo getInfo() {
		return this.info;
	}

	public PanelColors getColors() {
		return this.colors;
	}

	public ArrayList<BaseButton> getButtonArray() {
		return buttonArray;
	}

	public ButtonPanelTop getButtonOpen() {
		return this.buttons[0];
	}

	public ButtonPanelTop getButtonPin() {
		return this.buttons[1];
	}

	public ButtonPanelTop getButtonRemove() {
		return this.buttons[2];
	}

	public PanelConfig getConfig() {
		return this.config;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setInfo(PanelInfo info) {
		this.info = info;
	}

	public void setColors(PanelColors colors) {
		this.colors = colors;
	}

	public void setButtonArray(ArrayList<BaseButton> buttonArray) {
		this.buttonArray = buttonArray;
	}

	public void setButtonOpen(ButtonPanelTop buttonpaneltop) {
		this.buttons[0] = buttonpaneltop;
	}

	public void setButtonPin(ButtonPanelTop buttonpaneltop) {
		this.buttons[1] = buttonpaneltop;
	}

	public void setButtonRemove(ButtonPanelTop buttonpaneltop) {
		this.buttons[2] = buttonpaneltop;
	}

	public void setConfig(PanelConfig config) {
		this.config = config;
	}
}