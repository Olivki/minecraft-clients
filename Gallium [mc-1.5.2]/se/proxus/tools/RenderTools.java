package se.proxus.tools;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.RenderManager;

import org.lwjgl.opengl.GL11;

public class RenderTools {

    public static void renderLineTo(final double x, final double y,
	    final double z, final float lWidth) {

	enableDefaults();
	GL11.glLineWidth(lWidth);
	GL11.glBegin(GL11.GL_LINES);
	{
	    GL11.glVertex2d(0.0D, 0.0D);
	    GL11.glVertex3d(x, y, z);
	}
	GL11.glEnd();
	disableDefaults();
    }

    public static void renderLine(final double minX, final double minY,
	    final double minZ, final double maxX, final double maxY,
	    final double maxZ, final float lWidth) {
	enableDefaults();
	GL11.glLineWidth(lWidth);
	GL11.glBegin(GL11.GL_LINES);
	{
	    GL11.glVertex3d(minX, minY, minZ);
	    GL11.glVertex3d(maxX, maxY, maxZ);
	}
	GL11.glEnd();
	disableDefaults();
    }

    public static void renderBox(final double minX, final double minY,
	    final double minZ, final double maxX, final double maxY,
	    final double maxZ) {
	enableDefaults();
	/** FRONT FACE **/
	GL11.glBegin(GL11.GL_QUADS);
	{
	    GL11.glVertex3d(minX, minY, minZ);
	    GL11.glVertex3d(minX, maxY, minZ);
	    GL11.glVertex3d(maxX, minY, minZ);
	    GL11.glVertex3d(maxX, maxY, minZ);
	    GL11.glVertex3d(maxX, minY, maxZ);
	    GL11.glVertex3d(maxX, maxY, maxZ);
	    GL11.glVertex3d(minX, minY, maxZ);
	    GL11.glVertex3d(minX, maxY, maxZ);
	}
	GL11.glEnd();

	GL11.glBegin(GL11.GL_QUADS);
	{
	    GL11.glVertex3d(maxX, maxY, minZ);
	    GL11.glVertex3d(maxX, minY, minZ);
	    GL11.glVertex3d(minX, maxY, minZ);
	    GL11.glVertex3d(minX, minY, minZ);
	    GL11.glVertex3d(minX, maxY, maxZ);
	    GL11.glVertex3d(minX, minY, maxZ);
	    GL11.glVertex3d(maxX, maxY, maxZ);
	    GL11.glVertex3d(maxX, minY, maxZ);
	}
	GL11.glEnd();

	GL11.glBegin(GL11.GL_QUADS);
	{
	    GL11.glVertex3d(minX, maxY, minZ);
	    GL11.glVertex3d(maxX, maxY, minZ);
	    GL11.glVertex3d(maxX, maxY, maxZ);
	    GL11.glVertex3d(minX, maxY, maxZ);
	    GL11.glVertex3d(minX, maxY, minZ);
	    GL11.glVertex3d(minX, maxY, maxZ);
	    GL11.glVertex3d(maxX, maxY, maxZ);
	    GL11.glVertex3d(maxX, maxY, minZ);
	}
	GL11.glEnd();

	GL11.glBegin(GL11.GL_QUADS);
	{
	    GL11.glVertex3d(minX, minY, minZ);
	    GL11.glVertex3d(maxX, minY, minZ);
	    GL11.glVertex3d(maxX, minY, maxZ);
	    GL11.glVertex3d(minX, minY, maxZ);
	    GL11.glVertex3d(minX, minY, minZ);
	    GL11.glVertex3d(minX, minY, maxZ);
	    GL11.glVertex3d(maxX, minY, maxZ);
	    GL11.glVertex3d(maxX, minY, minZ);
	}
	GL11.glEnd();

	GL11.glBegin(GL11.GL_QUADS);
	{
	    GL11.glVertex3d(minX, minY, minZ);
	    GL11.glVertex3d(minX, maxY, minZ);
	    GL11.glVertex3d(minX, minY, maxZ);
	    GL11.glVertex3d(minX, maxY, maxZ);
	    GL11.glVertex3d(maxX, minY, maxZ);
	    GL11.glVertex3d(maxX, maxY, maxZ);
	    GL11.glVertex3d(maxX, minY, minZ);
	    GL11.glVertex3d(maxX, maxY, minZ);
	}
	GL11.glEnd();

	GL11.glBegin(GL11.GL_QUADS);
	{
	    GL11.glVertex3d(minX, maxY, maxZ);
	    GL11.glVertex3d(minX, minY, maxZ);
	    GL11.glVertex3d(minX, maxY, minZ);
	    GL11.glVertex3d(minX, minY, minZ);
	    GL11.glVertex3d(maxX, maxY, minZ);
	    GL11.glVertex3d(maxX, minY, minZ);
	    GL11.glVertex3d(maxX, maxY, maxZ);
	    GL11.glVertex3d(maxX, minY, maxZ);
	}
	GL11.glEnd();
	disableDefaults();
    }

    public static void renderOutlinedBox(final double minX, final double minY,
	    final double minZ, final double maxX, final double maxY,
	    final double maxZ, final float width) {
	enableDefaults();
	GL11.glLineWidth(width);
	/** FRONT FACE **/
	GL11.glBegin(GL11.GL_LINES);
	{
	    GL11.glVertex3d(minX, minY, minZ);
	    GL11.glVertex3d(minX, maxY, minZ);
	    GL11.glVertex3d(maxX, minY, minZ);
	    GL11.glVertex3d(maxX, maxY, minZ);
	    GL11.glVertex3d(maxX, minY, maxZ);
	    GL11.glVertex3d(maxX, maxY, maxZ);
	    GL11.glVertex3d(minX, minY, maxZ);
	    GL11.glVertex3d(minX, maxY, maxZ);
	}
	GL11.glEnd();

	GL11.glBegin(GL11.GL_LINES);
	{
	    GL11.glVertex3d(maxX, maxY, minZ);
	    GL11.glVertex3d(maxX, minY, minZ);
	    GL11.glVertex3d(minX, maxY, minZ);
	    GL11.glVertex3d(minX, minY, minZ);
	    GL11.glVertex3d(minX, maxY, maxZ);
	    GL11.glVertex3d(minX, minY, maxZ);
	    GL11.glVertex3d(maxX, maxY, maxZ);
	    GL11.glVertex3d(maxX, minY, maxZ);
	}
	GL11.glEnd();

	GL11.glBegin(GL11.GL_LINES);
	{
	    GL11.glVertex3d(minX, maxY, minZ);
	    GL11.glVertex3d(maxX, maxY, minZ);
	    GL11.glVertex3d(maxX, maxY, maxZ);
	    GL11.glVertex3d(minX, maxY, maxZ);
	    GL11.glVertex3d(minX, maxY, minZ);
	    GL11.glVertex3d(minX, maxY, maxZ);
	    GL11.glVertex3d(maxX, maxY, maxZ);
	    GL11.glVertex3d(maxX, maxY, minZ);
	}
	GL11.glEnd();

	GL11.glBegin(GL11.GL_LINES);
	{
	    GL11.glVertex3d(minX, minY, minZ);
	    GL11.glVertex3d(maxX, minY, minZ);
	    GL11.glVertex3d(maxX, minY, maxZ);
	    GL11.glVertex3d(minX, minY, maxZ);
	    GL11.glVertex3d(minX, minY, minZ);
	    GL11.glVertex3d(minX, minY, maxZ);
	    GL11.glVertex3d(maxX, minY, maxZ);
	    GL11.glVertex3d(maxX, minY, minZ);
	}
	GL11.glEnd();

	GL11.glBegin(GL11.GL_LINES);
	{
	    GL11.glVertex3d(minX, minY, minZ);
	    GL11.glVertex3d(minX, maxY, minZ);
	    GL11.glVertex3d(minX, minY, maxZ);
	    GL11.glVertex3d(minX, maxY, maxZ);
	    GL11.glVertex3d(maxX, minY, maxZ);
	    GL11.glVertex3d(maxX, maxY, maxZ);
	    GL11.glVertex3d(maxX, minY, minZ);
	    GL11.glVertex3d(maxX, maxY, minZ);
	}
	GL11.glEnd();

	GL11.glBegin(GL11.GL_LINES);
	{
	    GL11.glVertex3d(minX, maxY, maxZ);
	    GL11.glVertex3d(minX, minY, maxZ);
	    GL11.glVertex3d(minX, maxY, minZ);
	    GL11.glVertex3d(minX, minY, minZ);
	    GL11.glVertex3d(maxX, maxY, minZ);
	    GL11.glVertex3d(maxX, minY, minZ);
	    GL11.glVertex3d(maxX, maxY, maxZ);
	    GL11.glVertex3d(maxX, minY, maxZ);
	}
	GL11.glEnd();
	disableDefaults();
    }

    public static void renderCrossedOutlinedBox(final double minX,
	    final double minY, final double minZ, final double maxX,
	    final double maxY, final double maxZ, final float width) {
	enableDefaults();
	GL11.glLineWidth(width);
	/** FRONT FACE **/
	GL11.glBegin(GL11.GL_LINES);
	{
	    /** 1ST LINE **/
	    GL11.glVertex3d(minX, minY, minZ);
	    GL11.glVertex3d(minX, maxY, maxZ);
	    /** 2ND LINE **/
	    GL11.glVertex3d(minX, minY, maxZ);
	    GL11.glVertex3d(minX, maxY, minZ);
	}
	GL11.glEnd();

	GL11.glBegin(GL11.GL_LINES);
	{
	    /** 1ST LINE **/
	    GL11.glVertex3d(maxX, minY, minZ);
	    GL11.glVertex3d(maxX, maxY, maxZ);
	    /** 2ND LINE **/
	    GL11.glVertex3d(maxX, minY, maxZ);
	    GL11.glVertex3d(maxX, maxY, minZ);
	}
	GL11.glEnd();

	GL11.glBegin(GL11.GL_LINES);
	{
	    /** 1ST LINE **/
	    GL11.glVertex3d(maxX, minY, minZ);
	    GL11.glVertex3d(minX, maxY, minZ);
	    /** 2ND LINE **/
	    GL11.glVertex3d(minX, minY, minZ);
	    GL11.glVertex3d(maxX, maxY, minZ);
	}
	GL11.glEnd();

	GL11.glBegin(GL11.GL_LINES);
	{
	    /** 1ST LINE **/
	    GL11.glVertex3d(maxX, minY, maxZ);
	    GL11.glVertex3d(minX, maxY, maxZ);
	    /** 2ND LINE **/
	    GL11.glVertex3d(minX, minY, maxZ);
	    GL11.glVertex3d(maxX, maxY, maxZ);
	}
	GL11.glEnd();
	disableDefaults();
    }

    public static void renderNameTag(final String name, final double x,
	    final double y, final double z, final double distance,
	    final boolean showDistance, final int hex) {
	Minecraft mc = Minecraft.getMinecraft();
	FontRenderer font = mc.fontRenderer;
	RenderManager renderManager = RenderManager.instance;
	float var0 = 1.6F;
	float scaleAmount = 0.01666667F * var0;
	enableDefaults();
	if (distance >= 6F && distance <= 60F)
	    scaleAmount = (float) (scaleAmount * (0.27999999999999999D * distance));
	if (distance >= 60F && distance <= 100F)
	    scaleAmount = (float) (scaleAmount * (0.33D * distance));
	if (distance >= 100F)
	    scaleAmount = (float) (scaleAmount * (0.40000000000000001D * distance));
	EntityPlayerSP entityplayersp = mc.thePlayer;
	GL11.glTranslatef((float) x, (float) y, (float) z);
	GL11.glNormal3f(0.0F, 1.0F, 0.0F);
	GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
	GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
	GL11.glScalef(-scaleAmount, -scaleAmount, scaleAmount);
	if (showDistance) {
	    mc.ingameGUI.drawRect(
		    -font.getStringWidth(name + " (" + Colours.CLIENT_COLOUR
			    + (int) distance + Colours.RESET + ")") / 2 - 2,
		    -12,
		    font.getStringWidth(name + " (" + Colours.CLIENT_COLOUR
			    + (int) distance + Colours.RESET + ")") / 2 + 2, 0,
		    0x70000000);
	    font.drawStringWithShadow(
		    name + " (" + (int) distance + ")",
		    -font.getStringWidth(name + " (" + Colours.CLIENT_COLOUR
			    + (int) distance + Colours.RESET + ")") / 2, -10,
		    hex);
	} else {
	    mc.ingameGUI.drawRect(-font.getStringWidth(name) / 2 - 2, -12,
		    font.getStringWidth(name) / 2 + 2, 0, 0x70000000);
	    font.drawStringWithShadow(name, -font.getStringWidth(name) / 2,
		    -10, hex);
	}
	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	disableDefaults();
    }

    public static void enableDefaults() {
	Minecraft.getMinecraft().entityRenderer.disableLightmap(1.0D);
	GL11.glEnable(3042 /* GL_BLEND */);
	GL11.glDisable(3553 /* GL_TEXTURE_2D */);
	GL11.glDisable(2896 /* GL_LIGHTING */);
	GL11.glDisable(2929 /* GL_DEPTH_TEST */);
	GL11.glDepthMask(false);
	GL11.glBlendFunc(770, 771);
	if (Minecraft.getMinecraft().gameSettings.fancyGraphics
		&& Minecraft.getMinecraft().debugFPS >= 40)
	    GL11.glEnable(2848 /* GL_LINE_SMOOTH */);
	GL11.glPushMatrix();
    }

    public static void disableDefaults() {
	GL11.glPopMatrix();
	if (Minecraft.getMinecraft().gameSettings.fancyGraphics
		&& Minecraft.getMinecraft().debugFPS >= 40)
	    GL11.glDisable(2848 /* GL_LINE_SMOOTH */);
	GL11.glDepthMask(true);
	GL11.glEnable(2929 /* GL_DEPTH_TEST */);
	GL11.glEnable(3553 /* GL_TEXTURE_2D */);
	GL11.glEnable(2896 /* GL_LIGHTING */);
	GL11.glDisable(3042 /* GL_BLEND */);
	Minecraft.getMinecraft().entityRenderer.enableLightmap(1.0D);
    }

    public static void glColor4Hex(final int hex) {
	GL11.glColor4f(Colours.getRGBA(hex)[0], Colours.getRGBA(hex)[1],
		Colours.getRGBA(hex)[2], Colours.getRGBA(hex)[3]);
    }
}