package se.proxus.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Gui;
import net.minecraft.src.StringUtils;

import org.lwjgl.opengl.GL11;

import se.proxus.Pendrum;

public class TrueTypeFont extends Pendrum {

	private int texID;
	private int[] xPos;
	private int[] yPos;
	private int startChar;
	private int endChar;
	private  FontMetrics metrics;
	private Graphics graphics;

	public TrueTypeFont(Minecraft mc, Font font, int size) {
		this(mc, font, 32, 126);
	}

	public TrueTypeFont(Minecraft mc, Font font, int startChar, int endChar) {
		this.startChar = startChar;
		this.endChar = endChar;
		this.xPos = new int[endChar - startChar];
		this.yPos = new int[endChar - startChar];

		BufferedImage img = new BufferedImage(256, 256, 2);
		Graphics2D g1 = (Graphics2D)img.getGraphics();
		g1.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g1.setFont(font);
		int size = font.getSize();
		g1.setColor(new Color(255, 255, 255, 0));
		g1.fillRect(0, 0, 256, 256);
		g1.setColor(Color.white);
		this.metrics = g1.getFontMetrics(font);

		int x = 2;
		int y = 2;
		for (int i = startChar; i < endChar; i++) {
			g1.drawString(String.valueOf((char)i), x, y + this.metrics.getAscent());
			this.xPos[(i - startChar)] = x;
			this.yPos[(i - startChar)] = (y - this.metrics.getMaxDescent() + 2);
			x += this.metrics.stringWidth(new StringBuilder().append("").append((char)i).toString()) + 2;
			if (x >= 250 - this.metrics.getMaxAdvance()) {
				x = 2;
				y += this.metrics.getMaxAscent() + this.metrics.getMaxDescent() + size / 2;
			}

		}

		try {
			this.texID = Pendrum.wrapper.getMinecraft().renderEngine.allocateAndSetupTexture(img);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		this.graphics = g1;
	}

	public void renderString(String s, float f, float g, int color, boolean flag) {
		if (s == null) return;

		s = s.replace("§r", "§f");
		
		f *= 4;
		g = g * 2 - 7;

		GL11.glPushMatrix();
		GL11.glScaled(0.5D, 0.5D, 0.5D);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(3553);
		GL11.glBindTexture(3553, this.texID);

		float red = this.utils.getRGBA(color)[0];
		float green = this.utils.getRGBA(color)[1];
		float blue = this.utils.getRGBA(color)[2];
		float alpha = flag ? 1.0F : this.utils.getRGBA(color)[3];

		GL11.glColor4f(red, green, blue, alpha);

		float startX = f;
		int size = s.length();

		for(int i = 0; i < size; i++) {
			char c = s.charAt(i);

			if((c == '§') && (i + 1 < s.length())) {
				char type = s.charAt(i + 1);

				if(type == 'n') {
					g += this.metrics.getAscent() + 2;
					f = startX;
				}

				int j = "0123456789abcdefklmnor".indexOf(s.toLowerCase().charAt(i + 1));

				if(j < 16) {
					try {
						int l = Pendrum.wrapper.getFontRenderer().getColorCode()[j];
						GL11.glColor3f((l >> 16) / 255.0F, (l >> 8 & 0xFF) / 255.0F, (l & 0xFF) / 255.0F);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				i++;
			} else {
				try {
					drawChar( c, f, g);
					f = (int)(f + this.metrics.getStringBounds(Character.toString(c), null).getWidth());
				} catch (ArrayIndexOutOfBoundsException e) {

				}
			}
		}

		GL11.glScaled(2.0D, 2.0D, 2.0D);
		GL11.glPopMatrix();
	}

	public void drawCenteredString(String s, float x, float y, int color, boolean shadow) {
		if(shadow) {
			this.drawStringWithShadow(s, x - this.getStringWidth(s) / 2, y, color);
		} else {
			this.drawString(s, x - this.getStringWidth(s) / 2, y, color);
		}
	}

	public void drawStringWithShadow(String s, float x, float f, int color) {
		x/=2;
		int l = color & 0x0;
		int shade = (color & 0x0) >> 2;
		shade += l;

		GL11.glPushMatrix();
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(3042);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		//GL11.glColor4f(this.utils.getRGBA(color)[0], this.utils.getRGBA(color)[1], this.utils.getRGBA(color)[2], this.utils.getRGBA(color)[3]);
		this.renderString(StringUtils.stripControlCodes(s), x + 0.5F, f + 0.5F, shade, true);
		this.renderString(s, x, f, color, false);
		GL11.glEnable(2929);
		GL11.glDisable(3042);
		GL11.glDepthMask(true);
		GL11.glPopMatrix();
	}

	public void drawString(String s, float f, float g, int color) {
		f/=2;
		int l = color & 0x0;
		int shade = (color & 0x0) >> 2;
		shade += l;

		GL11.glPushMatrix();
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(3042);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		this.renderString(s, f, g, color, false);
		GL11.glEnable(2929);
		GL11.glDisable(3042);
		GL11.glDepthMask(true);
		GL11.glPopMatrix();
	}

	public FontMetrics getMetrics() {
		return this.metrics;
	}

	public int getStringWidth(String s) {
		return (int)getBounds(s).getWidth() / 2;
	}

	public int getCharWidth(char c) {
		return (int)getBounds(Character.toString(c)).getWidth() / 2;
	}

	public int getStringHeight(String s) {
		return (int)getBounds(s).getHeight() / 2;
	}

	private Rectangle getBounds(String s) {
		if (s == null) return null;
		int w = 0;
		int h = 0;
		int tw = 0;
		int size = s.length();
		for (int i = 0; i < size; i++) {
			char c = s.charAt(i);
			if (c == '\\') {
				char type;
				try { type = s.charAt(i + 1);
				} catch (StringIndexOutOfBoundsException e) {
					break;
				}

				if (type == 'n') {
					h += this.metrics.getAscent() + 2;
					if (tw > w) w = tw;
					tw = 0;
				}
				i++;
			}
			else {
				tw += this.metrics.stringWidth(Character.toString(c));
			}
		}
		if (tw > w) w = tw;
		h += this.metrics.getAscent();
		return new Rectangle(0, 0, w, h);
	}

	private void drawChar(char c, float f, float g) throws ArrayIndexOutOfBoundsException {
		Gui gui =  Pendrum.wrapper.getIngameGui();
		Rectangle2D bounds = this.metrics.getStringBounds(Character.toString(c), this.graphics);
		gui.drawTexturedModalRect(f, g, this.xPos[((byte)c - this.startChar)], this.yPos[((byte)c - this.startChar)], (int)bounds.getWidth(), (int)bounds.getHeight() + this.metrics.getMaxDescent());
	}

	public List listFormattedStringToWidth(String par1Str, int par2) {
		return Arrays.asList(wrapFormattedStringToWidth(par1Str, par2).split("\n"));
	}

	String wrapFormattedStringToWidth(String par1Str, int par2) {
		int var3 = sizeStringToWidth(par1Str, par2);

		if(par1Str.length() <= var3) {
			return par1Str;
		}

		String var4 = par1Str.substring(0, var3);
		String var5 = new StringBuilder().append(getFormatFromString(var4)).append(par1Str.substring(var3 + (par1Str.charAt(var3) == ' ' ? 1 : 0))).toString();
		return new StringBuilder().append(var4).append("\n").append(wrapFormattedStringToWidth(var5, par2)).toString();
	}

	private int sizeStringToWidth(String par1Str, int par2) {
		int var3 = par1Str.length();
		int var4 = 0;
		int var5 = 0;
		int var6 = -1;

		for(boolean var7 = false; var5 < var3; var5++) {
			char var8 = par1Str.charAt(var5);

			switch(var8) {
			case '§':
				if (var5 >= var3 - 1) break;
				var5++;
				char var9 = par1Str.charAt(var5);

				if((var9 != 'l') && (var9 != 'L')) {
					if((var9 == 'r') || (var9 == 'R'))
						var7 = false;
				} else {
					var7 = true;
				}

				break;
			case ' ':
				var6 = var5;
			default:
				var4 += getCharWidth(var8);

				if(!var7) break;
				var4++;
			}

			if(var8 == '\n') {
				var5++;
				var6 = var5;
				break;
			}

			if(var4 > par2) {
				break;
			}
		}
		return (var5 != var3) && (var6 != -1) && (var6 < var5) ? var6 : var5;
	}

	private String getFormatFromString(String par0Str) {
		String var1 = "";
		int var2 = -1;
		int var3 = par0Str.length();

		while((var2 = par0Str.indexOf('§', var2 + 1)) != -1) {
			if(var2 < var3 - 1) {
				char var4 = par0Str.charAt(var2 + 1);

				if(isFormatColor(var4))
					var1 = new StringBuilder().append("§").append(var4).toString();
				else if (isFormatSpecial(var4)) {
					var1 = new StringBuilder().append(var1).append("§").append(var4).toString();
				}
			}
		}

		return var1;
	}

	private boolean isFormatColor(char par0) {
		return ((par0 >= '0') && (par0 <= '9')) || ((par0 >= 'a') && (par0 <= 'f')) || ((par0 >= 'A') && (par0 <= 'F'));
	}

	private boolean isFormatSpecial(char par0) {
		return ((par0 >= 'k') && (par0 <= 'o')) || ((par0 >= 'K') && (par0 <= 'O')) || (par0 == 'r') || (par0 == 'R');
	}
}