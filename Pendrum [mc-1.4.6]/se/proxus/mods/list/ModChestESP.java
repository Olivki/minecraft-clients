package se.proxus.mods.list;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.*;
import se.proxus.events.Event;
import se.proxus.events.render.EventRender;
import se.proxus.mods.*;
import se.proxus.utils.Position;

public class ModChestESP extends BaseMod {

	public int refreshTime = 10;

	public ArrayList<Position> loadedChestsArray = new ArrayList<Position>();

	public ModChestESP() {
		super("Chest_ESP", new ModInfo("Renders a box and a tracer around/to chests.", "Oliver", "NONE", true), ModType.RENDER, false);
		this.setOption("Chest_ESP_range", Integer.valueOf(64), false);
		this.setOption("Chest_ESP_tracer", Boolean.valueOf(true), false);
		this.getInfo().setMod(this);
		this.getConfig().loadConfig();
	}

	@Override
	public void registerEvents() {
		this.getEvent().registerEvent(EventRender.class);
	}

	@Override
	public void onEnabled() {

	}

	@Override
	public void onDisabled() {

	}

	@Override
	public void onEvent(Event var0) {
		if(this.getState()) {
			if(var0 instanceof EventRender) {
				EventRender var1 = (EventRender)var0;

				if(var1.getType() == EventRender.Type.ThreeDimensional) {
					this.refreshTime--;

					if(this.refreshTime == 0) {
						this.loadedChestsArray.clear();
						this.refresh();
						this.refreshTime = 200;
					}

					for(int var2 = 0; var2 < this.loadedChestsArray.size(); var2++) {
						Position var3 = (Position)this.loadedChestsArray.get(var2);

						double var4 = var3.x - this.utils3d.getFixedPos(null)[0];
						double var5 = var3.y - this.utils3d.getFixedPos(null)[1];
						double var6 = var3.z - this.utils3d.getFixedPos(null)[2];

						this.utils3d.enableDefaults();
						this.utils3d.glColor4Hex(0x4063B8FF);
						this.utils3d.renderBox(AxisAlignedBB.getBoundingBox(var4, var5, var6, var4 + 1.0D, var5 + 1.0D, var6 + 1.0D));

						this.utils3d.glColor4Hex(0xFF63B8FF);
						this.utils3d.renderOutlinedBox(AxisAlignedBB.getBoundingBox(var4, var5, var6, var4 + 1.0D, var5 + 1.0D, var6 + 1.0D));
						this.utils3d.renderCrossedBox(AxisAlignedBB.getBoundingBox(var4, var5, var6, var4 + 1.0D, var5 + 1.0D, var6 + 1.0D));

						if(((Boolean)this.getOption("Chest_ESP_tracer")).booleanValue()) {
							this.utils3d.drawTracerTo(var3.x, var3.y, var3.z, this.utils.getRGBA(0xFF63B8FF)[0], this.utils.getRGBA(0xFF63B8FF)[1], this.utils.getRGBA(0xFF63B8FF)[2], 1.5F, null);
						}

						this.utils3d.disableDefaults();
					}
				}
			}
		}
	}

	public void refresh() {
		for(int var0 = 0; var0 < ((Integer)this.getOption("Chest_ESP_Range")).intValue(); var0++) {
			for(int var1 = 0; var1 < 256; var1++) {
				for(int var2 = 0; var2 < ((Integer)this.getOption("Chest_ESP_Range")).intValue(); var2++) {
					int var3 = (int)this.wrapper.getPlayer().posX - ((Integer)this.getOption("Chest_ESP_Range")).intValue() / 2 + var0;
					int var4 = var1;
					int var5 = (int)this.wrapper.getPlayer().posZ - ((Integer)this.getOption("Chest_ESP_Range")).intValue() / 2 + var2;
					int var6 = this.wrapper.getWorld().getBlockId(var3, var4, var5);

					if(var6 == Block.chest.blockID) {						
						if(!(this.loadedChestsArray.contains(new Position(var3, var4, var5)))) {
							this.loadedChestsArray.add(new Position(var3, var4, var5));
						}
					}
				}
			}
		}
	}
}