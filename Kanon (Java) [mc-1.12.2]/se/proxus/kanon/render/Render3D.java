package se.proxus.kanon.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import se.proxus.kanon.Kanon;
import se.proxus.kanon.utils.minecraft.client.Colourz;
import se.proxus.kanon.utils.templates.Location;
import se.proxus.kanon.wrapper.minecraft.Minekraft;

import static org.lwjgl.opengl.GL11.*;

/**
 * X: Left and Right.
 * Y: Up and Down.
 * Z: Forwards and Backwards.
 */
public class Render3D {
    
    public final void renderLineTo(final Location location, final float lineWidth) {
        renderLineTo(location.getX(), location.getY(), location.getZ(), lineWidth);
    }
    
    public final void renderLineTo(final double x, final double y, final double z, final float lineWidth) {
        enableDefaults();
        glLineWidth(lineWidth);
        glBegin(GL11.GL_LINES);
        {
            glVertex2d(0.0D, 0.0D);
            glVertex3d(x, y, z);
        }
        glEnd();
        disableDefaults();
    }
    
    public final void renderLine(final double minX, final double minY, final double minZ,
            final double maxX, final double maxY, final double maxZ,
            final float lineWidth) {
        enableDefaults();
        glLineWidth(lineWidth);
        glBegin(GL_LINES);
        {
            glVertex3d(minX, minY, minZ);
            glVertex3d(maxX, maxY, maxZ);
        }
        glEnd();
        disableDefaults();
    }
    
    private void line(final double minX, final double minY, final double minZ,
            final double maxX, final double maxY, final double maxZ) {
        line(new AxisAlignedBB(minX,minY, minZ, maxX, maxY, maxZ));
    }
    
    private void line(final AxisAlignedBB boundingBox) {
        glBegin(GL_LINES); {
            glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
            glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        }
        glEnd();
    }
    
    public final void renderFilledBox(final Location location, final int hex) {
        renderFilledBox(location.getX(), location.getY(), location.getZ(),
                        location.getX() + 1, location.getY() + 1, location.getZ() + 1,
                        hex);
    }
    
    public final void renderFilledBox(final double minX, final double minY, final double minZ,
            final double maxX, final double maxY, final double maxZ,
            final int hex) {
        enableDefaults();
        glColor4Hex(hex);
        /** FRONT FACE **/
        glBegin(GL_QUADS);
        {
            glVertex3d(minX, minY, minZ);
            glVertex3d(minX, maxY, minZ);
            glVertex3d(maxX, minY, minZ);
            glVertex3d(maxX, maxY, minZ);
            glVertex3d(maxX, minY, maxZ);
            glVertex3d(maxX, maxY, maxZ);
            glVertex3d(minX, minY, maxZ);
            glVertex3d(minX, maxY, maxZ);
        }
        glEnd();
        
        glBegin(GL_QUADS);
        {
            glVertex3d(maxX, maxY, minZ);
            glVertex3d(maxX, minY, minZ);
            glVertex3d(minX, maxY, minZ);
            glVertex3d(minX, minY, minZ);
            glVertex3d(minX, maxY, maxZ);
            glVertex3d(minX, minY, maxZ);
            glVertex3d(maxX, maxY, maxZ);
            glVertex3d(maxX, minY, maxZ);
        }
        glEnd();
        
        glBegin(GL_QUADS);
        {
            glVertex3d(minX, maxY, minZ);
            glVertex3d(maxX, maxY, minZ);
            glVertex3d(maxX, maxY, maxZ);
            glVertex3d(minX, maxY, maxZ);
            glVertex3d(minX, maxY, minZ);
            glVertex3d(minX, maxY, maxZ);
            glVertex3d(maxX, maxY, maxZ);
            glVertex3d(maxX, maxY, minZ);
        }
        glEnd();
        
        glBegin(GL_QUADS);
        {
            glVertex3d(minX, minY, minZ);
            glVertex3d(maxX, minY, minZ);
            glVertex3d(maxX, minY, maxZ);
            glVertex3d(minX, minY, maxZ);
            glVertex3d(minX, minY, minZ);
            glVertex3d(minX, minY, maxZ);
            glVertex3d(maxX, minY, maxZ);
            glVertex3d(maxX, minY, minZ);
        }
        glEnd();
        
        glBegin(GL_QUADS);
        {
            glVertex3d(minX, minY, minZ);
            glVertex3d(minX, maxY, minZ);
            glVertex3d(minX, minY, maxZ);
            glVertex3d(minX, maxY, maxZ);
            glVertex3d(maxX, minY, maxZ);
            glVertex3d(maxX, maxY, maxZ);
            glVertex3d(maxX, minY, minZ);
            glVertex3d(maxX, maxY, minZ);
        }
        glEnd();
        
        glBegin(GL_QUADS);
        {
            glVertex3d(minX, maxY, maxZ);
            glVertex3d(minX, minY, maxZ);
            glVertex3d(minX, maxY, minZ);
            glVertex3d(minX, minY, minZ);
            glVertex3d(maxX, maxY, minZ);
            glVertex3d(maxX, minY, minZ);
            glVertex3d(maxX, maxY, maxZ);
            glVertex3d(maxX, minY, maxZ);
        }
        glEnd();
        disableDefaults();
    }
    
    public final void renderBoxOutline(final Location location, final float lineWidth, final int color) {
        renderBoxOutline(location.getX(), location.getY(), location.getZ(),
                location.getX() + 1, location.getY() + 1, location.getZ() + 1,
                         lineWidth, color);
    }
    
    public final void renderBoxOutline(final double minX, final double minY, final double minZ,
            final double maxX, final double maxY, final double maxZ,
            final float lineWidth, final int color) {
        renderBoxOutline(new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ), lineWidth, color);
    }
    
    public final void renderBoxOutline(final AxisAlignedBB boundingBox, final float lineWidth, final int color) {
        enableDefaults();
        glLineWidth(lineWidth);
        glColor4Hex(color);
    
        // These are written as if you're looking at this from the north, facing towards it.
        /*** Z : TOP ***/
        // Left
        line(boundingBox.minX, boundingBox.maxY, boundingBox.minZ,
             boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
    
        // Right
        line(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ,
             boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
    
        /*** Z : BOTTOM ***/
        // Left
        line(boundingBox.minX, boundingBox.minY, boundingBox.minZ,
             boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
    
        // Right
        line(boundingBox.maxX, boundingBox.minY, boundingBox.minZ,
             boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
    
        /*** X : TOP ***/
        // Back
        line(boundingBox.minX, boundingBox.maxY, boundingBox.minZ,
             boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
    
        // Front
        line(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ,
             boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
    
        /*** Z : BOTTOM ***/
        // Back
        line(boundingBox.minX, boundingBox.minY, boundingBox.minZ,
             boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
    
        // Front
        line(boundingBox.minX, boundingBox.minY, boundingBox.maxZ,
             boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
    
        /*** SUPPORTS : BACK ***/
        // Left
        line(boundingBox.minX, boundingBox.minY, boundingBox.minZ,
             boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
    
        // Right
        line(boundingBox.maxX, boundingBox.minY, boundingBox.minZ,
             boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
    
        /*** SUPPORTS : FRONT ***/
        // Left
        line(boundingBox.minX, boundingBox.minY, boundingBox.maxZ,
             boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
    
        // Right
        line(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ,
             boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        
        disableDefaults();
    }
    
    public final void renderBoxCrossOutline(final Location location, final float lineWidth) {
        renderBoxCrossOutline(location.getX(), location.getY(), location.getZ(),
                          location.getX() + 1, location.getY() + 1, location.getZ() + 1,
                              lineWidth);
    }
    
    public final void renderBoxCrossOutline(final double minX, final double minY,
            final double minZ, final double maxX, final double maxY, final double maxZ,
            final float lineWidth) {
        enableDefaults();
        glLineWidth(lineWidth);
        /** FRONT FACE **/
        glBegin(GL_LINES);
        {
            /** 1ST LINE **/
            glVertex3d(minX, minY, minZ);
            glVertex3d(minX, maxY, maxZ);
            /** 2ND LINE **/
            glVertex3d(minX, minY, maxZ);
            glVertex3d(minX, maxY, minZ);
        }
        glEnd();
        
        glBegin(GL_LINES);
        {
            /** 1ST LINE **/
            glVertex3d(maxX, minY, minZ);
            glVertex3d(maxX, maxY, maxZ);
            /** 2ND LINE **/
            glVertex3d(maxX, minY, maxZ);
            glVertex3d(maxX, maxY, minZ);
        }
        glEnd();
        
        glBegin(GL_LINES);
        {
            /** 1ST LINE **/
            glVertex3d(maxX, minY, minZ);
            glVertex3d(minX, maxY, minZ);
            /** 2ND LINE **/
            glVertex3d(minX, minY, minZ);
            glVertex3d(maxX, maxY, minZ);
        }
        glEnd();
        
        glBegin(GL_LINES);
        {
            /** 1ST LINE **/
            glVertex3d(maxX, minY, maxZ);
            glVertex3d(minX, maxY, maxZ);
            /** 2ND LINE **/
            glVertex3d(minX, minY, maxZ);
            glVertex3d(maxX, maxY, maxZ);
        }
        glEnd();
        disableDefaults();
    }
    
    public void renderString(final FontRenderer font, final String text, final float x, final float y,
            final float z, final int color) {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-0.02666667F, -0.02666667F, 0.02666667F);
        font.drawStringWithShadow(text, 0, 0, color);
        GL11.glPopMatrix();
    }
    
    public void renderCenteredString(final FontRenderer font, final String text, final float x, final float y,
            final float z, final int color) {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-0.02666667F, -0.02666667F, 0.02666667F);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3553);
        font.drawStringWithShadow(text, ((float)-font.getStringWidth(text) / 2), 0, color);
        GL11.glDisable(3553);
        GL11.glPopMatrix();
    }
    
    private void enableDefaults() {
        Minecraft.getMinecraft().entityRenderer.disableLightmap();
        glEnable(3042 /* GL_BLEND */);
        glDisable(3553 /* GL_TEXTURE_2D */);
        glDisable(2896 /* GL_LIGHTING */);
        glDisable(2929 /* GL_DEPTH_TEST */);
        glDepthMask(false);
        glBlendFunc(770, 771);
        
        if (Minecraft.getMinecraft().gameSettings.fancyGraphics && Minecraft.getDebugFPS() >= 40)
            glEnable(2848 /* GL_LINE_SMOOTH */);
        
        glPushMatrix();
    }
    
    private void disableDefaults() {
        glPopMatrix();
        glDisable(2848 /* GL_LINE_SMOOTH */);
        glDepthMask(true);
        glEnable(2929 /* GL_DEPTH_TEST */);
        glEnable(3553 /* GL_TEXTURE_2D */);
        glEnable(2896 /* GL_LIGHTING */);
        glDisable(3042 /* GL_BLEND */);
        Minecraft.getMinecraft().entityRenderer.enableLightmap();
    }
    
    public void drawTexturedModalRect(final int textureId, final int posX, final int posY, final int width,
            final int height) {
        final double halfHeight = height / 2;
        final double halfWidth = width / 2;
        glDisable(GL_CULL_FACE);
        glBindTexture(GL_TEXTURE_2D, textureId);
        glPushMatrix();
        glTranslated(posX + halfWidth, posY + halfHeight, 0);
        glScalef(width, height, 0.0f);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glColor4f(1F, 1F, 1F, 1f);
        glBegin(GL_TRIANGLES);
        glNormal3f(0, 0, 1);
        glTexCoord2f(1, 1);
        glVertex2d(1, 1);
        glTexCoord2f(0, 1);
        glVertex2d(-1, 1);
        glTexCoord2f(0, 0);
        glVertex2d(-1, -1);
        glTexCoord2f(0, 0);
        glVertex2d(-1, -1);
        glTexCoord2f(1, 0);
        glVertex2d(1, -1);
        glTexCoord2f(1, 1);
        glVertex2d(1, 1);
        glEnd();
        glDisable(GL_BLEND);
        glBindTexture(GL_TEXTURE_2D, 0);
        glPopMatrix();
    }
    
    public final void renderCrosses(final AxisAlignedBB boundingBox) {
        //renderLine(boundingBox.minX, boundingBox.minY, boundingBox.minZ,
        //           boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ, 2);
        //renderLine(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ,
        //           boundingBox.minX, boundingBox.maxY, boundingBox.minZ, 2);
        
        // These are written as if you're looking at this from the north, facing towards it.
        // Z
        // Top
        // Top Left
        renderLine(boundingBox.minX, boundingBox.maxY, boundingBox.minZ,
                   boundingBox.minX, boundingBox.maxY, boundingBox.maxZ, 2);
    
        // Top Right
        renderLine(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ,
                   boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ, 2);
    
        // Bottom
        // Bottom Left
        renderLine(boundingBox.minX, boundingBox.minY, boundingBox.minZ,
                   boundingBox.minX, boundingBox.minY, boundingBox.maxZ, 2);
    
        // Bottom Right
        renderLine(boundingBox.maxX, boundingBox.minY, boundingBox.minZ,
                   boundingBox.maxX, boundingBox.minY, boundingBox.maxZ, 2);
    
        // X
        // Top
        // Top Back
        renderLine(boundingBox.minX, boundingBox.maxY, boundingBox.minZ,
                   boundingBox.maxX, boundingBox.maxY, boundingBox.minZ, 2);
    
        // Top Front
        renderLine(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ,
                   boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ, 2);
        
        // Bottom
        // Bottom Back
        renderLine(boundingBox.minX, boundingBox.minY, boundingBox.minZ,
                   boundingBox.maxX, boundingBox.minY, boundingBox.minZ, 2);
    
        // Bottom Front
        renderLine(boundingBox.minX, boundingBox.minY, boundingBox.maxZ,
                   boundingBox.maxX, boundingBox.minY, boundingBox.maxZ, 2);
        
        // Supports
        // Back
        // Support Left
        renderLine(boundingBox.minX, boundingBox.minY, boundingBox.minZ,
                   boundingBox.minX, boundingBox.maxY, boundingBox.minZ, 2);
    
        // Support Right
        renderLine(boundingBox.maxX, boundingBox.minY, boundingBox.minZ,
                   boundingBox.maxX, boundingBox.maxY, boundingBox.minZ, 2);
        
        // Front
        // Support Left
        renderLine(boundingBox.minX, boundingBox.minY, boundingBox.maxZ,
                   boundingBox.minX, boundingBox.maxY, boundingBox.maxZ, 2);
    
        // Support Right
        renderLine(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ,
                   boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ, 2);
    }
    
    public void renderNameTag(final String name, final Location location, final boolean showDistance, final int hex) {
        renderNameTag(name, location.getX(), location.getY(), location.getZ(), location.distanceToPlayer(), showDistance, hex);
    }
    
    public void renderNameTag(final String name, final double x, final double y, final double z,
            final double distance, final boolean showDistance, final int hex) {
        final Minecraft mc = Minecraft.getMinecraft();
        final FontRenderer font = mc.fontRendererObj;
        final RenderManager renderManager = mc.getRenderManager();
        final float var0 = 1.6F; // ??
        float scaleAmount = 0.01666667F * var0;
        enableDefaults();
        
        if (distance >= 6F && distance <= 60F) {
            scaleAmount = (float) (scaleAmount * (0.27999999999999999D * distance));
        }
        
        if (distance >= 60F && distance <= 100F) {
            scaleAmount = (float) (scaleAmount * (0.33D * distance));
        }
        
        if (distance >= 100F) {
            scaleAmount = (float) (scaleAmount * (0.40000000000000001D * distance));
        }
        
        glTranslatef((float) x, (float) y, (float) z);
        glNormal3f(0.0F, 1.0F, 0.0F);
        glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        glScalef(-scaleAmount, -scaleAmount, scaleAmount);
        
        if (showDistance) {
            final String display = name + " (" + Colourz.CLIENT_COLOUR + (int) distance + Colourz.RESET + ")";
            final int centeredWidth = font.getStringWidth(display) / 2;
            
            Gui.drawRect(-centeredWidth - 2,
                         -12,
                         centeredWidth / 2 + 2,
                         0,
                         0x70000000);
            font.drawStringWithShadow(display, centeredWidth, -10, hex);
        } else {
            final int centeredWidth = font.getStringWidth(name) / 2;
            
            Gui.drawRect(-centeredWidth - 2, -12, centeredWidth + 2, 0, 0x70000000);
            font.drawStringWithShadow(name, -centeredWidth, -10, hex);
        }
        
        glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        disableDefaults();
    }
    
    public void glColor4Hex(final int hex) {
        final float[] rgba = Colourz.getRgba(hex);
        glColor4f(rgba[0], rgba[1], rgba[2], rgba[3]);
    }
    
    private RenderManager getRenderManager() {
        return getMinecraft().getRenderManager();
    }
    
    private Minecraft getMinecraft() {
        return Minekraft.instance();
    }
    
    private Kanon getKanon() {
        return Kanon.getInstance();
    }
}