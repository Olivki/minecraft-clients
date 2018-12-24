package se.proxus.hacks;

import net.minecraft.src.*;

import static org.lwjgl.opengl.GL11.*;

public class h_Tracer extends Base_Hack {

	public h_Tracer() {
		super('5', "Tracer", "Draws lines to players.", "Render", "NONE");
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
			for(Object o : this.mc.theWorld.playerEntities) {
				EntityPlayer e = (EntityPlayer)o;
				EntityPlayerSP ep = this.mc.thePlayer;

				if(e != ep) {
					drawTracerTo(e.posX, e.posY, e.posZ, ep.getDistanceToEntity(e) * 256, ep.getDistanceToEntity(e), ep.getDistanceToEntity(e) / 256, 2.0F);
				}
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

	private void drawTracerTo(double x, double y, double z, double r, double g, double b, float lWidth) {
		EntityPlayerSP ep = this.mc.thePlayer;

		double d = ep.lastTickPosX + (ep.posX - ep.lastTickPosX) * (double)this.mc.timer.renderPartialTicks;
		double d1 = ep.lastTickPosY + (ep.posY - ep.lastTickPosY) * (double)this.mc.timer.renderPartialTicks;
		double d2 = ep.lastTickPosZ + (ep.posZ - ep.lastTickPosZ) * (double)this.mc.timer.renderPartialTicks;
		double d3 = x - d;
		double d4 = y - d1;
		double d5 = z - d2;

		enableDefaults();
		glColor4d(r, g, b, 1.0D);
		glLineWidth(lWidth);

		glBegin(GL_LINES);
		glVertex2d(0.0D, 0.0D);
		glVertex3d(d3, d4, d5);
		glEnd();

		disableDefaults();
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