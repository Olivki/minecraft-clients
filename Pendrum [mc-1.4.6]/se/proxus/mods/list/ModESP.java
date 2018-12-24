package se.proxus.mods.list;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.*;
import se.proxus.events.render.EventRender;
import se.proxus.mods.*;

public class ModESP extends BaseMod {

	public int espColour = 0xFFFFFFFF;

	public ModESP() {
		super("ESP", new ModInfo("Renders a box around players.", "Oliver", "NONE", true), ModType.RENDER, false);
		this.getInfo().setMod(this);
		this.getConfig().loadConfig();
	}

	@Override
	public void registerEvents() {
		
	}
	
	@Override
	public void onEnabled() {

	}

	@Override
	public void onDisabled() {

	}

	public void renderESP(EntityPlayer var1, double var2, double var3, double var4) {
		if(this.getState()) {
			if(var1 != this.wrapper.getPlayer()) {
				if(this.mods.forcefield.current instanceof EntityPlayer && this.mods.forcefield.current == var1) {
					this.espColour = 0xFFEE6363;
				} else if(this.mods.forcefield.current != var1) {
					this.espColour = 0xFFFFFFFF;
				} if(this.sett.isFriend(var1.username)) {
					this.espColour = 0xFF4F94CD;
				} if(var1.hurtTime > 0) {
					this.espColour = 0xFFFFA500;
				}

				this.utils3d.enableDefaults();
				//GL11.glColor4f(this.utils.getRGBA(this.espColour)[0], this.utils.getRGBA(this.espColour)[1], this.utils.getRGBA(this.espColour)[2], this.utils.getRGBA(this.espColour)[3]);
				this.utils3d.glColor4Hex(this.espColour);
				GL11.glTranslated(var2 + 0.5D, var3, var4 + 0.5D);
				GL11.glRotatef(-var1.rotationYaw, 0, 1, 0);
				GL11.glTranslated(-var2 - 0.5D, -var3, -var4 - 0.5D);
				GL11.glLineWidth(1.5F);
				this.utils3d.renderOutlinedBox(AxisAlignedBB.getBoundingBox(var2, var3, var4, var2 + 1.0D, var3 + 2.0D, var4 + 1.0D));
				this.utils3d.renderCrossedBox(AxisAlignedBB.getBoundingBox(var2, var3, var4, var2 + 1.0D, var3 + 2.0D, var4 + 1.0D));
				this.utils3d.disableDefaults();
			}
		}
	}
}