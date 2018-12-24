package Comet.Gui.Menu;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;

import org.lwjgl.opengl.GL11;

import Comet.Gui.Methods;

public class Slida extends GuiButton
{
    protected int width;
    protected int height;
    public int xPosition;
    public int yPosition;
    public String displayString;
    public int id;
    public boolean enabled;
    public boolean drawButton;

    public Slida(int i, int j, int k, String s)
    {
        this(i, j, k, 200, 22, s);
    }

    public Slida(int i, int j, int k, int l, int i1, String s)
    {
    	super(i, j, k, l, i1, s);
        width = 200;
        height = 22;
        enabled = true;
        drawButton = true;
        id = i;
        xPosition = j;
        yPosition = k;
        width = l;
        height = i1;
        displayString = s;
    }

    protected int getHoverState(boolean flag)
    {
        byte byte0 = 1;
        if (!enabled)
        {
            byte0 = 0;
        }
        else if (flag)
        {
            byte0 = 2;
        }
        return byte0;
    }

    public void drawButton(Minecraft minecraft, int i, int j)
    {
        if (!drawButton)
        {
            return;
        }
        FontRenderer fontrenderer = minecraft.fontRenderer;
        GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, minecraft.renderEngine.getTexture("/gui/gui.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        boolean flag = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
        int k = getHoverState(flag);
        Methods.drawSmallWBorderedRect(xPosition, yPosition - 2, xPosition + width / 2 + 40, yPosition + 2, 0xff000000, 0xff333333);
        mouseDragged(minecraft, i, j);
    }

    protected void mouseDragged(Minecraft minecraft, int i, int j)
    {
    }

    public void mouseReleased(int i, int j)
    {
    }
    
    public boolean mousePressed(Minecraft minecraft, int i, int j)
    {
        return enabled && drawButton && i >= xPosition  && j >= yPosition - 4 && i < xPosition + width && j < yPosition + 4;
    }
}
