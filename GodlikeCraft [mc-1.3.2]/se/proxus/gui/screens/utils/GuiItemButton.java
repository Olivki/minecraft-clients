package se.proxus.gui.screens.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

import org.lwjgl.opengl.GL11;

import se.proxus.GodlikeCraft;

public class GuiItemButton extends GuiButton {
	
	private static RenderItem itemRenderer = new RenderItem();	
	
	/** Button width in pixels */
	protected int width;

	/** Button height in pixels */
	protected int height;

	/** The x position of this control. */
	public int xPosition;

	/** The y position of this control. */
	public int yPosition;

	/** The string displayed on this control. */
	public String displayString;

	/** ID for this control. */
	public int id, itemID;

	/** True if this control is enabled, false to disable. */
	public boolean enabled;

	/** Hides the button completely if false. */
	public boolean drawButton;

	/** Stuff. **/
	public float Rez1, Rez2, Rez3, Rez4;

	public GuiItemButton(int par1, int par2, int par3, String par4Str, int itemID)
	{
		this(par1, par2, par3, 30, 20, par4Str, itemID);
	}

	public GuiItemButton(int par1, int par2, int par3, int par4, int par5, String par6Str, int itemID) {
		super(par1, par2, par3, par4, par5, par6Str);
		this.width = 30;
		this.height = 20;
		this.enabled = true;
		this.drawButton = true;
		this.id = par1;
		this.xPosition = par2;
		this.yPosition = par3;
		this.width = par4;
		this.height = par5;
		this.displayString = par6Str;
		this.itemID = itemID;
		//this.Rez1 = par2;
	}

	/**
	 * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
	 * this button.
	 */
	protected int getHoverState(boolean par1)
	{
		byte var2 = 1;

		if (!this.enabled)
		{
			var2 = 0;
		}
		else if (par1)
		{
			var2 = 2;
		}

		return var2;
	}

	/**
	 * Draws this button to the screen.
	 */
	public void drawButton(Minecraft par1Minecraft, int par2, int par3)
	{
		if (this.drawButton)
		{
			boolean isRez = false, canSound = false;
			FontRenderer var4 = par1Minecraft.fontRenderer;
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, par1Minecraft.renderEngine.getTexture("/gui/gui.png"));
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			boolean var5 = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
			int var6 = this.getHoverState(var5);
			/*this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + var6 * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + var6 * 20, this.width / 2, this.height);*/

			if(var5) {
				isRez = true;
			
				if(isRez && !(Rez1 < -2)) {
					this.Rez1 -= 0.15F;
				} if(isRez && !(Rez2 < -2)) {
					this.Rez2 -= 0.15F;
				} if(isRez && !(Rez3 < -2)) {
					this.Rez3 -= 0.15F;
				} if(isRez && !(Rez4 < -2)) {
					this.Rez4 -= 0.15F;
				}
			} else {
				if(!isRez && Rez1 < 1) {
					this.Rez1 += 0.15F;
				} if(!isRez && Rez2 < 1) {
					this.Rez2 += 0.15F;
				} if(!isRez && Rez3 < 1) {
					this.Rez3 += 0.15F;
				} if(!isRez && Rez4 < 1) {
					this.Rez4 += 0.15F;
				}
			}

			//this.drawBorderedRect(this.xPosition + this.Rez1, this.yPosition + this.Rez2, this.xPosition + this.width - this.Rez3, this.yPosition + this.height - this.Rez4, 0xFF666666, 0x60336699);
			this.drawButtonRect(this.xPosition + this.Rez1, this.yPosition + this.Rez2, this.xPosition + this.width - this.Rez3, this.yPosition + this.height - this.Rez4, enabled);
			this.drawItem(this.xPosition, this.yPosition, this.itemID);

			this.mouseDragged(par1Minecraft, par2, par3);
			int var7 = 14737632;

			if (!this.enabled)
			{
				var7 = -6250336;
			}
			else if (var5)
			{
				var7 = 16777120;
			}

			this.drawCenteredString(var4, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, var7);
		}
	}

	/**
	 * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
	 */
	protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3) {}

	/**
	 * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
	 */
	public void mouseReleased(int par1, int par2) {}

	/**
	 * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
	 * e).
	 */
	public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3)
	{
		return this.enabled && this.drawButton && par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
	}

	private void drawBaseBorderedRect(int x, int y, int x2, int y2, int col1, int col2) {
		drawRect(x + 1, y + 1, x2 - 1, y2 - 1, col2);
		drawVerticalLine(x, y, y2 - 1, col1);
		drawVerticalLine(x2 - 1, y, y2 - 1, col1);
		drawHorizontalLine(x + 1, x2 - 2, y, col1);
		drawHorizontalLine(x + 1, x2 - 2, y2 - 1, col1);
	}

	protected void drawBorderedRect(int x, int y, int x2, int y2, int col1, int col2) {
		GL11.glPushMatrix();
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		drawBaseBorderedRect(x * 2, y * 2, x2 * 2, y2 * 2, col1, col2);
		GL11.glPopMatrix();
	}
	
	protected void drawButtonRect(float x, float y, float x2, float y2, boolean var1) {
	    GodlikeCraft.ingame.drawHollowBorderedRect(x, y, x2, y2, 0xFF13161D);
	    GodlikeCraft.ingame.drawHollowBorderedRectCustom(x + 0.5F, y + 0.5F, x2 - 1, y2 - 1, var1 ? 0xFF1A587E : 0xFF4C4C4C, false);
	    GodlikeCraft.ingame.drawHollowBorderedRectCustom(x + 1, y + 1, x2 - 0.5F, y2 - 0.5F, var1 ? 0xFF093B5B : 0xFF2E2E2E, true);
		//this.drawRect(x + 1F, y2 - 1, x + 1.5F, y2 - 2F, 0xFF400000);
	    GodlikeCraft.ingame.drawGradientRect(x + 1, y + 1, x2 - 1, y2 - 1, var1 ? 0xFF014C7D : 0xFF3F3F3F, var1 ? 0xFF013150 : 0xFF292929);
	}
	
	public void playSound() {
		Minecraft.theMinecraft.sndManager.playSoundFX("random.click", 1.0F, 0.6F);
	}
	
	private void drawItem(int x, int y, int id) {
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		itemRenderer.renderItemIntoGUI(Minecraft.theMinecraft.fontRenderer, Minecraft.theMinecraft.renderEngine, new ItemStack(Block.chest), 999, 999);
		itemRenderer.drawItemIntoGui(Minecraft.theMinecraft.fontRenderer, Minecraft.theMinecraft.renderEngine, id, 0, Item.itemsList[id].getIconFromDamage(0), x + 1, y + 1);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
	}
}
