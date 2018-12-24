package se.proxus.kanon.render;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import se.proxus.kanon.wrapper.minecraft.Minekraft;
import se.proxus.kanon.wrapper.minecraft.Screen;

import static org.lwjgl.opengl.GL11.*;

//Todo: remove final and remove static and make stuff inherit this.
public final class Render2D {

    private static final float zLevel = 0.0F;

    public static void startCut(final int x, final int y, final int xEnd, final int yEnd) {
        glPushMatrix();
        final int width = xEnd - x;
        final int height = yEnd - y;
        final ScaledResolution scaledRes = Screen.getResolution();
        final int factor = scaledRes.getScaleFactor();
        final int bottomY = scaledRes.getScaledHeight() - yEnd;
        glScissor(x * factor, bottomY * factor, width * factor, height * factor);
    }

    public static void stopCut() {
        glDisable(GL_SCISSOR_TEST);
        glPopMatrix();
    }

    /**
     * Draws a thin horizontal line between two points.
     */
    public static void drawHorizontalLine(float startX, float endX, final float y, final int color) {
        if (endX < startX) {
            final float i = startX;
            startX = endX;
            endX = i;
        }

        drawRect(startX, y, endX + 1, y + 1, color);
    }

    /**
     * Draw a 1 pixel wide vertical line. Args : x, y1, y2, color
     */
    public static void drawVerticalLine(final float x, float startY, float endY, final int color) {
        if (endY < startY) {
            final float i = startY;
            startY = endY;
            endY = i;
        }

        drawRect(x, startY + 1, x + 1, endY, color);
    }

    /**
     * Draws a solid color rectangle with the specified coordinates and color.
     */
    public static void drawRect(float x, float y, float width, float height, final int color) {
        if (x < width) {
            final float i = x;
            x = width;
            width = i;
        }

        if (y < height) {
            final float j = y;
            y = height;
            height = j;
        }

        final float f3 = (float) (color >> 24 & 255) / 255.0F;
        final float f = (float) (color >> 16 & 255) / 255.0F;
        final float f1 = (float) (color >> 8 & 255) / 255.0F;
        final float f2 = (float) (color & 255) / 255.0F;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f, f1, f2, f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos((double) x, (double) height, 0.0D).endVertex();
        bufferbuilder.pos((double) width, (double) height, 0.0D).endVertex();
        bufferbuilder.pos((double) width, (double) y, 0.0D).endVertex();
        bufferbuilder.pos((double) x, (double) y, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    /**
     * Draws a rectangle with a vertical gradient between the specified colors (ARGB format). Args : x1, y1,
     * x2, y2, topColor, bottomColor
     */
    public static void drawGradientRect(final float x, final float y, final float width, final float height,
            final int startColor, final int endColor) {
        final float f = (float) (startColor >> 24 & 255) / 255.0F;
        final float f1 = (float) (startColor >> 16 & 255) / 255.0F;
        final float f2 = (float) (startColor >> 8 & 255) / 255.0F;
        final float f3 = (float) (startColor & 255) / 255.0F;
        final float f4 = (float) (endColor >> 24 & 255) / 255.0F;
        final float f5 = (float) (endColor >> 16 & 255) / 255.0F;
        final float f6 = (float) (endColor >> 8 & 255) / 255.0F;
        final float f7 = (float) (endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double) width, (double) y, (double) zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double) x, (double) y, (double) zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double) x, (double) height, (double) zLevel).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos((double) width, (double) height, (double) zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawBaseBorderedRect(final float x, final float y, final float width,
            final float height,
            final int hex1, final int hex2) {
        drawRect(x, y + 1, width, height - 1, hex2);
        drawVerticalLine(x - 1, y, height - 1, hex1);
        drawVerticalLine(width, y, height - 1, hex1);
        drawHorizontalLine(x, width - 1, y, hex1);
        drawHorizontalLine(x, width - 1, height - 1, hex1);
    }

    public static void drawBorderedRect(final float x, final float y, final float width, final float height,
            final int hex1, final int hex2) {
        GL11.glPushMatrix();
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        drawBaseBorderedRect(x * 2, y * 2, width * 2, height * 2, hex1, hex2);
        GL11.glPopMatrix();
    }

    public static void drawBaseBorderedGradientRect(final float x, final float y, final float width,
            final float height, final int hex1, final int hex2, final int hex3) {
        drawGradientRect(x, y + 1, width, height - 1, hex2, hex3);
        drawVerticalLine(x - 1, y, height - 1, hex1);
        drawVerticalLine(width, y, height - 1, hex1);
        drawHorizontalLine(x, width - 1, y, hex1);
        drawHorizontalLine(x, width - 1, height - 1, hex1);
    }

    public static void drawBorderedGradientRect(final float x, final float y, final float width,
            final float height, final int hex1, final int hex2, final int hex3) {
        GL11.glPushMatrix();
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        drawBaseBorderedGradientRect(x * 2, y * 2, width * 2, height * 2, hex1, hex2, hex3);
        GL11.glPopMatrix();
    }

    public static void drawToolTip(final String text, final float mouseX, final float mouseY) {
        final FontRenderer font = Minekraft.getFont();
        final float x = mouseX + 6;
        final float y = mouseY + 1;
        final float width = x + font.getStringWidth(text) + 2;
        final float height = y + 8;

        drawRect(x, y, width, height, 0x60000000);
        font.drawString(text, x + 1, y, 0xFFFFFFFF, false);
    }
}