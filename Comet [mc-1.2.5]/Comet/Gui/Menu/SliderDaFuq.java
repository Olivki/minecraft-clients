package Comet.Gui.Menu;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;

import org.lwjgl.opengl.GL11;

import Comet.Utils.Enum_Options;
import Comet.Utils.Settings;

public class SliderDaFuq extends GuiButton
{
    /** The value of this slider control. */
    public float sliderValue;

    /** Is this slider control being dragged. */
    public boolean dragging;

    /** Additional ID for this slider control. */
    private Enum_Options idFloat;

    public SliderDaFuq(int par1, int par2, int par3, Enum_Options par4EnumOptions, String par5Str, float par6)
    {
        super(par1, par2, par3, 150, 20, par5Str);
        sliderValue = 1.0F;
        dragging = false;
        idFloat = null;
        idFloat = par4EnumOptions;
        sliderValue = par6;
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    protected int getHoverState(boolean par1)
    {
        return 0;
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3)
    {
        if (!drawButton)
        {
            return;
        }

        if (dragging)
        {
            sliderValue = (float)(par2 - (xPosition + 4)) / (float)(field_52008_a - 8);

            if (sliderValue < 0.0F)
            {
                sliderValue = 0.0F;
            }

            if (sliderValue > 1.0F)
            {
                sliderValue = 1.0F;
            }

            Settings.setOptionFloatValue(idFloat, sliderValue);
            displayString = Settings.getKeys(idFloat);
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(xPosition + (int)(sliderValue * (float)(field_52008_a - 8)), yPosition, 0, 66, 4, 20);
        drawTexturedModalRect(xPosition + (int)(sliderValue * (float)(field_52008_a - 8)) + 4, yPosition, 196, 66, 4, 20);
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3)
    {
        if (super.mousePressed(par1Minecraft, par2, par3))
        {
            sliderValue = (float)(par2 - (xPosition + 4)) / (float)(field_52008_a - 8);

            if (sliderValue < 0.0F)
            {
                sliderValue = 0.0F;
            }

            if (sliderValue > 1.0F)
            {
                sliderValue = 1.0F;
            }

            Settings.setOptionFloatValue(idFloat, sliderValue);
            displayString = Settings.getKeys(idFloat);
            dragging = true;
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int par1, int par2)
    {
        dragging = false;
    }
}
