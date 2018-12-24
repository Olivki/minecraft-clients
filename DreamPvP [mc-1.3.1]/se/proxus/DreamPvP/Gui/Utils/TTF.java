package se.proxus.DreamPvP.Gui.Utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Gui;

/**
 * 
 * @author TheObliterator
 *
 * A class to create and draw true type
 * fonts onto the Minecraft game engine.
 */
public class TTF {

	private int texID;
	private int[] xPos;
	private int[] yPos;
	private int startChar;
	private int endChar;
	private FontMetrics metrics;
	private int scale;
	private float scaleDown, scaleUp;

	/**
	 * Instantiates the font, filling in default start
	 * and end character parameters.
	 * 
	 * 'new CustomFont(ModLoader.getMinecraftInstance(),
	 *                              "Arial", 12);
	 * 
	 * @param mc The Minecraft instance for the font to be bound to.
	 * @param fontName The name of the font to be drawn.
	 * @param size The size of the font to be drawn.
	 */
	public TTF(Minecraft mc, String fontName, int size, int scale, float scaleDown, float scaleUp) {
		this(mc, fontName, size, 32, 400, scale, scaleDown, scaleUp); //32, 126
	}

	/**
	 * Instantiates the font, pre-rendering a sprite
	 * font image by using a true type font on a
	 * bitmap. Then allocating that bitmap to the
	 * Minecraft rendering engine for later use.
	 * 
	 * 'new CustomFont(ModLoader.getMinecraftInstance(),
	 *                              "Arial", 12, 32, 126);'
	 * 
	 * @param mc The Minecraft instance for the font to be bound to.
	 * @param fontName The name of the font to be drawn.
	 * @param size The size of the font to be drawn.
	 * @param startChar The starting ASCII character id to be drawable. (Default 32)
	 * @param endChar The ending ASCII character id to be drawable. (Default 126)
	 */
	public TTF(Minecraft mc, String fontName, int size, int startChar, int endChar, int scale, float scaleDown, float scaleUp)
	{
		this.scale = scale;
		this.scaleDown = scaleDown;
		this.scaleUp = scaleUp;
		this.startChar = startChar;
		this.endChar = endChar;
		xPos = new int[endChar-startChar];
		yPos = new int[endChar-startChar];

		// Create a bitmap and fill it with a transparent color as well
		// as obtain a Graphics instance which can be drawn on.
		// NOTE: It is CRUICIAL that the size of the image is 256x256, if
		// it is not the Minecraft engine will not draw it properly.
		BufferedImage img = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		g.setFont(new Font(fontName, 0, size));
		g.setColor(new Color(255, 255, 255, 0));
		g.fillRect(0, 0, 256, 256);
		g.setColor(Color.white);
		metrics = g.getFontMetrics();

		// Draw the specified range of characters onto
		// the new bitmap, spacing according to the font
		// widths. Also allocating positions of characters
		// on the bitmap to two arrays which will be used
		// later when drawing.
		int x = 2;
		int y = 2;
		for (int i=startChar; i<endChar; i++)
		{
			g.drawString(""+((char)i), x, y + g.getFontMetrics().getAscent());
			xPos[i-startChar] = x;
			yPos[i-startChar] = y;
			x += metrics.stringWidth(""+(char)i) + 2;
			if (x >= 250-metrics.getMaxAdvance())
			{
				x = 2;
				y += metrics.getMaxAscent() + metrics.getMaxDescent() + 2;
			}
		}

		// Render the finished bitmap into the Minecraft
		// graphics engine.
		texID = mc.renderEngine.allocateAndSetupTexture(img);
	}

	/**
	 * Draws a given string with an automatically
	 * calculated shadow below it.
	 * @param gui The gui/subclass to be drawn on
	 * @param text The string to be drawn
	 * @param x The x position to start drawing
	 * @param y The y position to start drawing
	 * @param color The color of the non-shadowed text (Hex)
	 */
	public void drawStringS(Gui gui, String text, int x, int y, int color) {
		int l = (color & 16579836) >> 2 | color & -16777216;
		
		drawString(gui, text, x+1, y+1, l);
		drawString(gui, text, x, y, color);
	}

	/**
	 * Draws a given string onto a gui/subclass.
	 * @param gui The gui/subclass to be drawn on
	 * @param text The string to be drawn
	 * @param x The x position to start drawing
	 * @param y The y position to start drawing
	 * @param color The color of the non-shadowed text (Hex)
	 */
	public void drawString(Gui gui, String text, int x, int y, int color) {
		GL11.glScalef(scaleDown, scaleDown, scaleDown);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
		GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, texID);
		float red = (float)(color >> 16 & 0xff) / 255F;
		float green = (float)(color >> 8 & 0xff) / 255F;
		float blue = (float)(color & 0xff) / 255F;
		float alpha = (float)(color >> 24 & 0xff) / 255F;
		GL11.glColor4f(red, green, blue, alpha);
		int startX = x;
		for (int i=0; i<text.length(); i++)
		{
			char c = text.charAt(i);
			/*if (c == '\\') {
					try {
						char type = text.charAt(i + 1);
						if (type == 'n')
						{
							y += metrics.getAscent() + 2;
							x = startX;
						}
						i++;
						continue;
					} catch (Exception e) {
					}
				} else */
			if(c == '§') {
				try {
					char color1 = (i != text.length() - 1 ? text.charAt(i + 1) : 'f');
					tryColor(color1);
					++i;
					continue;
				} catch (Exception e) {
					++i;
					continue;
				}
			}
			
			drawChar(gui, c, x*2, y*2);
			x += metrics.getStringBounds(""+c, null).getWidth() / scale;
		}
		
		GL11.glScalef(scaleUp, scaleUp, scaleUp);
	}

	/**
	 * @author PigBacon
	 * Tries to glColor4f based on the given char.
	 * Should be called if the section sign is in chat, on the char at index + 1.
	 */
	public void tryColor(char c) {
		if(c == '0') {	
			GL11.glColor4f(0/255F,0/255F,0/255F,255/255F);
		} else if(c == '1') {
			GL11.glColor4f(0/255F,0/255F,170/255F,255/255F);
		} else if(c == '2') {
			GL11.glColor4f(0/255F,170/255F,0/255F,255/255F);
		} else if(c == '3') {
			GL11.glColor4f(0/255F,170/255F,170/255F,255/255F);
		} else if(c == '4') {
			GL11.glColor4f(170/255F,0/255F,0/255F,255/255F);
		} else if(c == '5') {
			GL11.glColor4f(170/255F,0/255F,170/255F,255/255F);
		} else if(c == '6') {
			GL11.glColor4f(255/255F,170/255F,0/255F,255/255F);
		} else if(c == '7') {
			GL11.glColor4f(170/255F,170/255F,170/255F,255/255F);
		} else if(c == '8') {
			GL11.glColor4f(85/255F,85/255F,85/255F,255/255F);
		} else if(c == '9') {
			GL11.glColor4f(85/255F,85/255F,255/255F,255/255F);
		} else if(c == 'A' || c == 'a') {
			GL11.glColor4f(85/255F,255/255F,85/255F,255/255F);
		} else if(c == 'B' || c == 'b') {
			GL11.glColor4f(85/255F,255/255F,255/255F,255/255F);
		} else if(c == 'C' || c == 'c') {
			GL11.glColor4f(255/255F,85/255F,85/255F,255/255F);
		} else if(c == 'D' || c == 'd') {
			GL11.glColor4f(255/255F,85/255F,255/255F,255/255F);
		} else if(c == 'E' || c == 'e') {
			GL11.glColor4f(255/255F,255/255F,85/255F,255/255F);
		} else if(c == 'F' || c == 'f') {
			GL11.glColor4f(255/255F,255/255F,255/255F,255/255F);
		} else if(c == 'K' || c == 'k') {
			GL11.glColor4f(0/255F,0/255F,0/255F,255/255F);
		}
	}

	/**
	 * Returns the created FontMetrics
	 * which is used to retrive various
	 * information about the True Type Font
	 * @return FontMetrics of the created font.
	 */
	public FontMetrics getMetrics()
	{
		return metrics;
	}

	/**
	 * Gets the drawing width of a given
	 * string of string.
	 * @param text The string to be measured
	 * @return The width of the given string.
	 */
	public int getStringWidth(String text)
	{
		return (int)getBounds(text).getWidth() / scale;
	}

	/**
	 * Gets the drawing height of a given
	 * string of string.
	 * @param text The string to be measured
	 * @return The height of the given string.
	 */
	public int getStringHeight(String text)
	{
		return (int)getBounds(text).getHeight() / scale;
	}

	/**
	 * A method that returns a Rectangle that
	 * contains the width and height demensions
	 * of the given string.
	 * @param text The string to be measured
	 * @return Rectangle containing width and height that
	 * the text will consume when drawn.
	 */
	private Rectangle getBounds(String text)
	{
		int w = 0;
		int h = 0;
		int tw = 0;
		for (int i=0; i<text.length(); i++)
		{
			char c = text.charAt(i);
			/*if (c == '\\')
                        {
							char type = text.charAt(i + 1);
							if (type == 'n')
							{
								h += metrics.getAscent() + 2;
								if (tw > w)
								w = tw;
								tw = 0;
							}
							i++;
							continue;
						}*/
			tw += metrics.stringWidth(""+c);
		}
		if (tw > w)
			w = tw;
		h += metrics.getAscent();
		return new Rectangle(0, 0, w, h);
	}

	/**
	 * Private drawing method used within other
	 * drawing methods.
	 */
	private void drawChar(Gui gui, char c, int x, int y)
	{
		try {
			Rectangle2D bounds = metrics.getStringBounds(""+c, null);
			gui.drawTexturedModalRect(x, y, xPos[(byte)c-startChar], yPos[(byte)c-startChar] + metrics.getDescent(), (int)bounds.getWidth(), (int)bounds.getHeight());
		} catch(Exception e) { 
			// Since no-one really cares, bad characters will just not appear.
			//System.out.println("!BADCHAR!: " + c);
		}
	}
}