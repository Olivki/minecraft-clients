package se.proxus.gui.utils;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.UnicodeFont;

import se.proxus.GodlikeCraft;

import net.minecraft.src.*;

public class TTF extends GodlikeCraft {

	private int texID;
	private int xPos[];
	private int yPos[];
	private int startChar;
	private int endChar;
	private FontMetrics metrics;
	public Object objes = null;
	public int fontSize = 0;

	public static TTF ttfFont;
	public Gui eGui;

	public TTF(Gui eGui, Minecraft minecraft, Object obj, int i)
	{
		this(eGui,
				minecraft,
				obj,
				i,
				31,
				127);
		this.objes = obj;
		this.fontSize = i;
	}

	public TTF(Gui eGui, Minecraft minecraft, Object obj, int i, int j, int k) {
		this.eGui = eGui;
		this.objes = obj;
		this.fontSize = i;

		startChar = j;
		endChar = k;
		xPos = new int[k - j];
		yPos = new int[k - j];
		BufferedImage bufferedimage = new BufferedImage(256, 256, 2);
		Graphics g = bufferedimage.getGraphics();

		try {
			Graphics2D g1 = (Graphics2D)g;
			g1.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			if (obj instanceof String)
			{
				String s = (String)obj;

				if (s.contains("/"))
				{
					g.setFont(Font.createFont(0, new File(s)).deriveFont(i));
				}
				else
				{
					g.setFont(new Font(s, 0, i));
				}
			}
			else if (obj instanceof InputStream)
			{
				g.setFont(Font.createFont(0, (InputStream)obj).deriveFont(i));
			}
			else if (obj instanceof File)
			{
				g.setFont(Font.createFont(0, (File)obj).deriveFont(i));
			}
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}

		g.setColor(new Color(255, 255, 255, 0));
		g.fillRect(0, 0, 256, 256);
		g.setColor(Color.white);
		metrics = g.getFontMetrics();
		int l = 2;
		int i1 = 2;

		for (int j1 = j; j1 < k; j1++)
		{
			g.drawString((new StringBuilder()).append("").append((char)j1).toString(), l, i1 + g.getFontMetrics().getAscent());
			xPos[j1 - j] = l;
			yPos[j1 - j] = i1 - metrics.getMaxDescent();
			l += metrics.stringWidth((new StringBuilder()).append("").append((char)j1).toString()) + 2;

			if (l >= 250 - metrics.getMaxAdvance())
			{
				l = 2;
				i1 += metrics.getMaxAscent() + metrics.getMaxDescent() + i / 2;
			}
		}

		texID = minecraft.renderEngine.allocateAndSetupTexture(bufferedimage);
	}

	public void drawStringS(String s, float g, float f) {
		/*g=g*2;
		f=f*2;

		if(s != null) {
			if(GodlikeCraft.utils.isFriend(s)) {
				s = "§3" + s + "§r";
			}
		}

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
		GL11.glHint(GL11.GL_POINT_SMOOTH_HINT, GL11.GL_NICEST);
		GL11.glDepthMask(false);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		/*renderString(s, g - 1, f - 1, true);
		renderString(s, g + 1, f + 1, true);
		renderString(s, g + 1, f - 1, true);
		renderString(s, g - 1, f + 1, true);*/
		/*renderString(s, g - 1, f - 1, true);
		renderString(s, g, f, false);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_POINT_SMOOTH);
		GL11.glDepthMask(true);
		GL11.glPopMatrix();*/

		if(vars.fontMode == 1) {
			slick.drawStringWithShadow(vars.font, g, f, s);
		} if(vars.fontMode == 2) {
			slick.drawStringWithOutline(vars.font, g, f, s);
		} if(vars.fontMode == 3) {
			slick.drawString(vars.font, g, f, s);
		}
	}

	public void drawString(String s, int i, float f) {	
		/*if(s != null) {
			if(GodlikeCraft.utils.isFriend(s)) {
				s = "§3" + s + "§r";
			}
		}

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
		GL11.glHint(GL11.GL_POINT_SMOOTH_HINT, GL11.GL_NICEST);
		GL11.glDepthMask(false);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		renderString(s, i * 2, f * 2, false);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_POINT_SMOOTH);
		GL11.glDepthMask(true);
		GL11.glPopMatrix();*/

		slick.drawString(vars.font, i, f, s);
	}

	public void drawCenteredString(String var1, int var2, int var3) {
		drawStringS(var1, var2 - getStringWidth(var1) / 2, var3);
	}

	public void renderString(String s, float g, float f, boolean flag) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texID);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		if (flag) {
			GL11.glColor4f(0.4F, 0.4F, 0.4F, 1F);
		}

		float k = g;

		for (int l = 0; l < s.length(); l++)
		{
			char c1 = s.charAt(l);

			if (c1 == '\n')
			{
				f += metrics.getAscent() + 4;
				g = k;
				l++;
				continue;
			}

			if (c1 == '\341' || c1 == '\340' || c1 == '\342' || c1 == '\344' || c1 == '\301' || c1 == '\300' || c1 == '\302' || c1 == '\304')
			{
				c1 = 'a';
			}

			if (c1 == '\351' || c1 == '\350' || c1 == '\352' || c1 == '\353' || c1 == '\311' || c1 == '\310' || c1 == '\312' || c1 == '\313')
			{
				c1 = 'e';
			}

			if (c1 == '\355' || c1 == '\354' || c1 == '\356' || c1 == '\357' || c1 == '\315' || c1 == '\314' || c1 == '\316' || c1 == '\317')
			{
				c1 = 'i';
			}

			if (c1 == '\363' || c1 == '\362' || c1 == '\364' || c1 == '\366' || c1 == '\323' || c1 == '\322' || c1 == '\324' || c1 == '\326')
			{
				c1 = 'o';
			}

			if (c1 == '\372' || c1 == '\371' || c1 == '\373' || c1 == '\374' || c1 == '\332' || c1 == '\331' || c1 == '\333' || c1 == '\334')
			{
				c1 = 'u';
			}

			if (c1 == '\247' || c1 == '&')
			{
				l++;
				char c = s.charAt(l);

				if (c == 'a' || c == 'A')
				{
					if (!flag)
					{
						GL11.glColor4f(0.25F, 1.0F, 0.25F, 1.0F);
					}
					else
					{
						GL11.glColor4f(0.05F, 0.2F, 0.05F, 1.0F);
					}
				}

				if (c == 'b' || c == 'B')
				{
					if (!flag)
					{
						GL11.glColor4f(0.25F, 1.0F, 1.0F, 1.0F);
					}
					else
					{
						GL11.glColor4f(0.05F, 0.2F, 0.2F, 1.0F);
					}
				}

				if (c == 'c' || c == 'C')
				{
					if (!flag)
					{
						GL11.glColor4f(1.0F, 0.25F, 0.25F, 1.0F);
					}
					else
					{
						GL11.glColor4f(0.2F, 0.05F, 0.05F, 1.0F);
					}
				}

				if (c == 'd' || c == 'D')
				{
					if (!flag)
					{
						GL11.glColor4f(1.0F, 0.25F, 1.0F, 1.0F);
					}
					else
					{
						GL11.glColor4f(0.2F, 0.05F, 0.2F, 1.0F);
					}
				}

				if (c == 'e' || c == 'E')
				{
					if (!flag)
					{
						GL11.glColor4f(1.0F, 1.0F, 0.25F, 1.0F);
					}
					else
					{
						GL11.glColor4f(0.2F, 0.2F, 0.05F, 1.0F);
					}
				}

				if (c == 'f' || c == 'F')
				{
					if (!flag)
					{
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					}
					else
					{
						GL11.glColor4f(0.2F, 0.2F, 0.2F, 1.0F);
					}
				}

				if (c == 'r' || c == 'R')
				{
					if (!flag)
					{
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					}
					else
					{
						GL11.glColor4f(0.2F, 0.2F, 0.2F, 1.0F);
					}
				}

				if (c == '0')
				{
					GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
				}

				if (c == '1')
				{
					if (!flag)
					{
						GL11.glColor4f(0.0F, 0.0F, 0.75F, 1.0F);
					}
					else
					{
						GL11.glColor4f(0.0F, 0.0F, 0.1F, 1.0F);
					}
				}

				if (c == '2')
				{
					if (!flag)
					{
						GL11.glColor4f(0.0F, 0.75F, 0.0F, 1.0F);
					}
					else
					{
						GL11.glColor4f(0.0F, 0.1F, 0.0F, 1.0F);
					}
				}

				if (c == '3')
				{
					if (!flag)
					{
						GL11.glColor4f(0.0F, 0.75F, 0.75F, 1.0F);
					}
					else
					{
						GL11.glColor4f(0.0F, 0.1F, 0.1F, 1.0F);
					}
				}

				if (c == '4')
				{
					if (!flag)
					{
						GL11.glColor4f(0.75F, 0.0F, 0.0F, 1.0F);
					}
					else
					{
						GL11.glColor4f(0.1F, 0.0F, 0.0F, 1.0F);
					}
				}

				if (c == '5')
				{
					if (!flag)
					{
						GL11.glColor4f(0.75F, 0.0F, 0.75F, 1.0F);
					}
					else
					{
						GL11.glColor4f(0.1F, 0.0F, 0.1F, 1.0F);
					}
				}

				if (c == '6')
				{
					if (!flag)
					{
						GL11.glColor4f(1.0F, 0.75F, 0.0F, 1.0F);
					}
					else
					{
						GL11.glColor4f(0.1F, 0.1F, 0.0F, 1.0F);
					}
				}

				if (c == '7')
				{
					if (!flag)
					{
						GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
					}
					else
					{
						GL11.glColor4f(0.1F, 0.1F, 0.1F, 1.0F);
					}
				}

				if (c == '8')
				{
					if (!flag)
					{
						GL11.glColor4f(0.25F, 0.25F, 0.25F, 1.0F);
					}
					else
					{
						GL11.glColor4f(0.05F, 0.05F, 0.05F, 1.0F);
					}
				}

				if (c != '9')
				{
					continue;
				}
				else {

				}

				if (!flag)
				{
					GL11.glColor4f(0.25F, 0.25F, 1.0F, 1.0F);
				}
				else
				{
					GL11.glColor4f(0.05F, 0.05F, 0.2F, 1.0F);
				}

				continue;
			}

			if (c1 <= '~')
			{
				drawChar(c1, g, f);
				g = (int)((double)g + metrics.getStringBounds((new StringBuilder()).append("").append(c1).toString(), null).getWidth());
			}
		}
	}

	public FontMetrics getMetrics()
	{
		return metrics;
	}

	public int getStringWidth(String s)
	{
		return (int)getBounds(s).getWidth();
	}

	public int getStringHeight(String s)
	{
		return (int)getBounds(s).getHeight();
	}

	private Rectangle getBounds(String s)
	{
		int i = 0;
		int j = 0;
		int k = 0;

		for (int l = 0; l < s.length(); l++) {
			char c = s.charAt(l);

			if (c == '\\')
			{
				char c1 = s.charAt(l + 1);

				if (c1 == 'n')
				{
					j += metrics.getAscent() + 2;

					if (k > i)
					{
						i = k;
					}

					k = 0;
				}

				l++;
			}
			else
			{
				k += metrics.stringWidth((new StringBuilder()).append("").append(c).toString());
			}
		}

		if (k > i)
		{
			i = k;
		}

		j += metrics.getAscent();
		return new Rectangle(0, 0, i, j);
	}

	private void drawChar(char c, float g, float f) {
		Rectangle2D rectangle2d = metrics.getStringBounds((new StringBuilder()).append("").append(c).toString(), null);
		eGui.drawTexturedModalRect(g, f - 2, xPos[(byte)c - startChar], yPos[(byte)c - startChar], (int)rectangle2d.getWidth(), (int)rectangle2d.getHeight() + metrics.getMaxDescent() + 2);
	}
}