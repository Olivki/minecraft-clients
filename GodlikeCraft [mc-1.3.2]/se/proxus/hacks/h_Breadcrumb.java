package se.proxus.hacks;

import se.proxus.utils.placeholders.p_Breadcrum;
import net.minecraft.src.*;

import static org.lwjgl.opengl.GL11.*;

public class h_Breadcrumb extends Base_Hack {

	public h_Breadcrumb() {
		super('6', "Breadcrumb", "Draws lines after where you've walked.", "Render", "NONE");
	}

	@Override
	public void onEnabled() {

	}

	@Override
	public void onDisabled() {

	}

	@Override
	public void onToggled() {

	}

	@Override
	public void onUpdate() {

	}

	@Override
	public void onRendered3D() {
		if(this.getState()) {
			for(p_Breadcrum var1 : this.vars.trailArray) {
				double d = this.mc.thePlayer.lastTickPosX + (this.mc.thePlayer.posX - this.mc.thePlayer.lastTickPosX) * (double)this.mc.timer.renderPartialTicks;
				double d1 = this.mc.thePlayer.lastTickPosY + (this.mc.thePlayer.posY - this.mc.thePlayer.lastTickPosY) * (double)this.mc.timer.renderPartialTicks;
				double d2 = this.mc.thePlayer.lastTickPosZ + (this.mc.thePlayer.posZ - this.mc.thePlayer.lastTickPosZ) * (double)this.mc.timer.renderPartialTicks;
				double d3 = this.vars.trailX - d;
				double d4 = this.vars.trailY - d1;
				double d5 = this.vars.trailZ - d2;
				double x = var1.x - d;
				double y = var1.y - d1;
				double z = var1.z - d2;
				
				this.enableDefaults();
				//this.drawBox(d3, d4, d5, d3 + 1D, d4 + 1D, d5 + 1D);

				//glBegin(GL_LINES);
				//glVertex2d(0.0D, 0.0D);
				/*glVertex3d(d3, d4, d5);
				glVertex3d(x, y, z);*/
				glColor4d(1.0D, 0.0D, 0.0D, 1.0D);
				this.drawBox(x + 0.4D, y + 0.4D, z + 0.4D, x + 0.6D, y + 0.6D, z + 0.6D);
				//glEnd();
				
				this.disableDefaults();
			}
		}
	}

	@Override
	public String[] getModString() {
		return (new String[] {});
	}

	@Override
	public int[] getModInteger() {
		return (new int[] {});
	}

	@Override
	public float[] getModFloat() {
		return (new float[] {});
	}

	@Override
	public long[] getModLong() {
		return (new long[] {});
	}

	@Override
	public boolean[] getModBoolean() {
		return (new boolean[] {});
	}
	
	private void drawBox(double x, double y, double z, double x2, double y2, double z2) {
		glBegin(GL_QUADS);
		glVertex3d(x, y, z); 
		glVertex3d(x, y2, z);
		glVertex3d(x2, y, z);
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x, y2, z2);
		glEnd();

		glBegin(GL_QUADS);
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y, z);
		glVertex3d(x, y2, z);
		glVertex3d(x, y, z);
		glVertex3d(x, y2, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y, z2);
		glEnd();

		glBegin(GL_QUADS);      
		glVertex3d(x, y2, z);        
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y2, z2);
		glVertex3d(x, y2, z2);
		glVertex3d(x, y2, z);
		glVertex3d(x, y2, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y2, z);
		glEnd();

		glBegin(GL_QUADS);       
		glVertex3d(x, y, z);        
		glVertex3d(x2, y, z);
		glVertex3d(x2, y, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x, y, z);
		glVertex3d(x, y, z2);
		glVertex3d(x2, y, z2);
		glVertex3d(x2, y, z);
		glEnd();

		glBegin(GL_QUADS);
		glVertex3d(x, y, z); 
		glVertex3d(x, y2, z);
		glVertex3d(x, y, z2);
		glVertex3d(x, y2, z2);
		glVertex3d(x2, y, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y, z);
		glVertex3d(x2, y2, z);
		glEnd();

		glBegin(GL_QUADS);
		glVertex3d(x, y2, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x, y2, z);
		glVertex3d(x, y, z);
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y, z);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y, z2);
		glEnd();
	}

	private void drawOutlinedBox(double x, double y, double z, double x2, double y2, double z2, float l1) {
		glLineWidth(l1);

		glBegin(GL_LINES);
		glVertex3d(x, y, z); 
		glVertex3d(x, y2, z);
		glVertex3d(x2, y, z);
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x, y2, z2);
		glEnd();

		glBegin(GL_LINES);
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y, z);
		glVertex3d(x, y2, z);
		glVertex3d(x, y, z);
		glVertex3d(x, y2, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y, z2);
		glEnd();

		glBegin(GL_LINES);      
		glVertex3d(x, y2, z);        
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y2, z2);
		glVertex3d(x, y2, z2);
		glVertex3d(x, y2, z);
		glVertex3d(x, y2, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y2, z);
		glEnd();

		glBegin(GL_LINES);       
		glVertex3d(x, y, z);        
		glVertex3d(x2, y, z);
		glVertex3d(x2, y, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x, y, z);
		glVertex3d(x, y, z2);
		glVertex3d(x2, y, z2);
		glVertex3d(x2, y, z);
		glEnd();

		glBegin(GL_LINES);
		glVertex3d(x, y, z); 
		glVertex3d(x, y2, z);
		glVertex3d(x, y, z2);
		glVertex3d(x, y2, z2);
		glVertex3d(x2, y, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y, z);
		glVertex3d(x2, y2, z);
		glEnd();

		glBegin(GL_LINES);
		glVertex3d(x, y2, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x, y2, z);
		glVertex3d(x, y, z);
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y, z);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y, z2);
		glEnd();
	}

	private void enableDefaults() {
		this.mc.entityRenderer.disableLightmap(1.0D);
		glEnable(3042 /*GL_BLEND*/);
		glDisable(3553 /*GL_TEXTURE_2D*/);
		glDisable(2896 /*GL_LIGHTING*/);
		glDisable(2929 /*GL_DEPTH_TEST*/);
		glDepthMask(false);
		glBlendFunc(770, 771);
		glEnable(2848 /*GL_LINE_SMOOTH*/);
		glPushMatrix();
	}

	private void disableDefaults() {
		glPopMatrix();
		glDisable(2848 /*GL_LINE_SMOOTH*/);
		glDepthMask(true);
		glEnable(2929 /*GL_DEPTH_TEST*/);
		glEnable(3553 /*GL_TEXTURE_2D*/);
		glEnable(2896 /*GL_LIGHTING*/);
		glDisable(3042 /*GL_BLEND*/);
		this.mc.entityRenderer.enableLightmap(1.0D);
	}
}