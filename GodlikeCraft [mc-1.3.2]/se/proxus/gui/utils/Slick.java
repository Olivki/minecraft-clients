package se.proxus.gui.utils;

import java.awt.Font;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import se.proxus.GodlikeCraft;

public class Slick extends GodlikeCraft {

	public Slick() {
		vars.font.addAsciiGlyphs();
		vars.font.getEffects().add(new ColorEffect(java.awt.Color.white));

		try {
			vars.font.loadGlyphs();
		} catch(SlickException e) {
			e.printStackTrace();
		}
	}

	public static void drawCenteredString(String text, UnicodeFont font, float x, float y) {
		drawStringWithShadow(font, x - (font.getWidth(text) / 2), y, text);
	}

	public static void drawStringWithOutline(UnicodeFont unicodefont, float f, float f1, String s, int col)
	{
		f *= 2;
		f1 *= 2;
		int i = 0;
		int j = 0;
		boolean flag = false;
		GL11.glPushMatrix();
		GL11.glEnable(3042);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glHint(GL11.GL_POINT_SMOOTH_HINT, GL11.GL_NICEST);
		GL11.glDepthMask(false);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		if(s.contains("\247"))
		{
			String as[] = s.split("\247");
			if(!s.startsWith("\247")) {
				unicodefont.drawString(f, f1-1, as[0], Color.black);
				unicodefont.drawString(f, f1+1, as[0], Color.black);
				unicodefont.drawString(f - 1, f1, as[0], Color.black);
				unicodefont.drawString(f + 1, f1, as[0], Color.black);
				unicodefont.drawString(f, f1, as[0], Color.white);
				i += unicodefont.getWidth(as[0]);
				flag = true;
			}
			do
			{
				if(j == as.length)
				{
					break;
				}
				if(j != 0 || !flag)
				{
					String s1 = as[j];
					if(s1.length() != 0)
					{
						char c = 'z';
						try
						{
							c = s1.charAt(0);
						}
						catch(Exception exception)
						{
							exception.printStackTrace();
						}
						int k = "0123456789abcdefk".indexOf((new StringBuilder()).append("").append(c).toString().toLowerCase());
						if(k == -1)
						{
							break;
						}
						int l = mc.fontRenderer.getColorCode()[k];
						//System.out.println(l);
						as[j] = removeCharAt(as[j], 0);
						unicodefont.drawString(f + (float)i + 1, f1, as[j], Color.black);
						unicodefont.drawString(f + (float)i - 1, f1, as[j], Color.black);
						unicodefont.drawString(f + (float)i, f1 + 1, as[j], Color.black);
						unicodefont.drawString(f + (float)i  ,f1 - 1, as[j], Color.black);
						unicodefont.drawString(f + (float)i, f1, as[j], new Color(getRedFromHex(col), getGreenFromHex(col), getBlueFromHex(col), getAlphaFromHex(col)));
						i += unicodefont.getWidth(as[j]);
					}
				}
				j++;
			} while(true);
		} else
		{
			unicodefont.drawString(f+1, f1, s, Color.black);
			unicodefont.drawString(f-1, f1, s, Color.black);
			unicodefont.drawString(f, f1+1, s, Color.black);
			unicodefont.drawString(f, f1-1, s, Color.black);
			unicodefont.drawString(f, f1, s, new Color(getRedFromHex(col), getGreenFromHex(col), getBlueFromHex(col), getAlphaFromHex(col) * 16));
		}
		GL11.glScalef(2.0F, 2.0F, 2.0F);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_POINT_SMOOTH);
		GL11.glDepthMask(true);
		GL11.glPopMatrix();
	}

	public static void drawStringWithOutline(UnicodeFont unicodefont, float f, float f1, String s)
	{

		f *= 2;
		f1 *= 2;
		int i = 0;
		int j = 0;
		boolean flag = false;
		GL11.glPushMatrix();
		GL11.glEnable(3042);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glHint(GL11.GL_POINT_SMOOTH_HINT, GL11.GL_NICEST);
		GL11.glDepthMask(false);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		if(s.contains("\247"))
		{
			String as[] = s.split("\247");
			if(!s.startsWith("\247"))
			{
				unicodefont.drawString(f, f1-1, as[0], Color.black);
				unicodefont.drawString(f, f1+1, as[0], Color.black);
				unicodefont.drawString(f - 1, f1, as[0], Color.black);
				unicodefont.drawString(f + 1, f1, as[0], Color.black);
				unicodefont.drawString(f, f1, as[0], Color.white);
				i += unicodefont.getWidth(as[0]);
				flag = true;
			}
			do
			{
				if(j == as.length)
				{
					break;
				}
				if(j != 0 || !flag)
				{
					String s1 = as[j];
					if(s1.length() != 0)
					{
						char c = 'z';
						try
						{
							c = s1.charAt(0);
						}
						catch(Exception exception)
						{
							exception.printStackTrace();
						}
						int k = "0123456789abcdefk".indexOf((new StringBuilder()).append("").append(c).toString().toLowerCase());
						if(k == -1)
						{
							break;
						}
						int l = mc.fontRenderer.getColorCode()[k];
						//System.out.println(l);
						as[j] = removeCharAt(as[j], 0);
						unicodefont.drawString(f + (float)i + 1, f1, as[j], Color.black);
						unicodefont.drawString(f + (float)i - 1, f1, as[j], Color.black);
						unicodefont.drawString(f + (float)i, f1 + 1, as[j], Color.black);
						unicodefont.drawString(f + (float)i  ,f1 - 1, as[j], Color.black);
						unicodefont.drawString(f + (float)i, f1, as[j], new Color(getRedFromHex(l), getGreenFromHex(l), getBlueFromHex(l)));
						i += unicodefont.getWidth(as[j]);
					}
				}
				j++;
			} while(true);
		} else
		{
			unicodefont.drawString(f+1, f1, s, Color.black);
			unicodefont.drawString(f-1, f1, s, Color.black);
			unicodefont.drawString(f, f1+1, s, Color.black);
			unicodefont.drawString(f, f1-1, s, Color.black);
			unicodefont.drawString(f, f1, s, Color.white);
		}
		GL11.glScalef(2.0F, 2.0F, 2.0F);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_POINT_SMOOTH);
		GL11.glDepthMask(true);
		GL11.glPopMatrix();
	}

	public static void drawString(UnicodeFont unicodefont, float f, float f1, String s)
	{
		f *= 2;
		f1 *= 2;
		int i = 0;
		int j = 0;
		boolean flag = false;
		GL11.glPushMatrix();
		GL11.glEnable(3042);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glHint(GL11.GL_POINT_SMOOTH_HINT, GL11.GL_NICEST);
		GL11.glDepthMask(false);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		if(s.contains("\247"))
		{
			String as[] = s.split("\247");
			if(!s.startsWith("\247"))
			{
				unicodefont.drawString(f, f1, as[0], Color.white);
				i += unicodefont.getWidth(as[0]);
				flag = true;
			}
			do
			{
				if(j == as.length)
				{
					break;
				}
				if(j != 0 || !flag)
				{
					String s1 = as[j];
					if(s1.length() != 0)
					{
						char c = 'z';
						try
						{
							c = s1.charAt(0);
						}
						catch(Exception exception)
						{
							exception.printStackTrace();
						}
						int k = "0123456789abcdefk".indexOf((new StringBuilder()).append("").append(c).toString().toLowerCase());
						if(k == -1)
						{
							break;
						}
						int l = mc.fontRenderer.getColorCode()[k];
						as[j] = removeCharAt(as[j], 0);
						unicodefont.drawString(f + (float)i, f1, as[j], new Color(getRedFromHex(l), getGreenFromHex(l), getBlueFromHex(l)));
						i += unicodefont.getWidth(as[j]);
					}
				}
				j++;
			} while(true);
		} else
		{
			unicodefont.drawString(f, f1, s, Color.white);
		}
		GL11.glScalef(2.0F, 2.0F, 2.0F);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_POINT_SMOOTH);
		GL11.glDepthMask(true);
		GL11.glPopMatrix();
	}

	public static void drawStringWithShadow(UnicodeFont unicodefont, float f, float f1, String s) {
		f *= 2;
		f1 *= 2;
		int i = 0;
		int j = 0;
		boolean flag = false;
		GL11.glPushMatrix();
		GL11.glEnable(3042);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
		GL11.glHint(GL11.GL_POINT_SMOOTH_HINT, GL11.GL_NICEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDepthMask(false);
		GL11.glScalef(0.5F, 0.5F, 0.5F);


		if(s.contains("\247")) {
			String as[] = s.split("\247");
			if(!s.startsWith("\247")) {
				unicodefont.drawString(f + 1.0F, f1 + 1.0F, as[0], Color.black);
				unicodefont.drawString(f, f1, as[0], Color.white);
				i += unicodefont.getWidth(as[0]);
				flag = true;
			}
			do
			{
				if(j == as.length)
				{
					break;
				}
				if(j != 0 || !flag)
				{
					String s1 = as[j];
					if(s1.length() != 0)
					{
						char c = 'z';
						try
						{
							c = s1.charAt(0);
						} catch(Exception exception) {
							exception.printStackTrace();
						}
						int k = "0123456789abcdefk".indexOf((new StringBuilder()).append("").append(c).toString().toLowerCase());

						if(k == -1) {
							break;
						}
						
						int l = mc.fontRenderer.getColorCode()[k];
						as[j] = removeCharAt(as[j], 0);

						unicodefont.drawString(f + (float)i + 1.0F, f1 + 1.0F, as[j], Color.black);
						unicodefont.drawString(f + (float)i, f1, as[j], new Color(getRedFromHex(l), getGreenFromHex(l), getBlueFromHex(l)));
						i += unicodefont.getWidth(as[j]);
					}
				}
				j++;
			} while(true);
		} else {
			unicodefont.drawString(f + 1.0F, f1 + 1.0F, s, Color.black);
			unicodefont.drawString(f, f1, s, Color.white);
		}

		GL11.glScalef(2.0F, 2.0F, 2.0F);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_POINT_SMOOTH);
		GL11.glDepthMask(true);
		GL11.glPopMatrix();
	}

	public static String removeCharAt(String s, int i)
	{
		return (new StringBuilder()).append(s.substring(0, i)).append(s.substring(i + 1)).toString();
	}

	public static float getAlphaFromHex(int i)
	{
		return (float) (i >> 24 & 0xff) / 255F;
	}

	public static float getRedFromHex(int i)
	{
		return (float)(double)((float)(i >> 16 & 0xff) / 255F);
	}

	public static float getGreenFromHex(int i)
	{
		return (float)(double)((float)(i >> 8 & 0xff) / 255F);
	}

	public static float getBlueFromHex(int i)
	{
		return (float)(double)((float)(i & 0xff) / 255F);
	}

}