package se.proxus.gui;

import java.awt.image.BufferedImage;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

import se.proxus.*;

public class RenderMap extends Pendrum {

	public int mapTexture;

	public int[] buffer;

	public final int mapSize = 128;

	public final int scale = 1;

	public final int center = 64;

	public int colorOffset;

	public int prevheight = 0;

	public int heightDiff;

	public int posX = 1;

	public int posY = 1;

	public int width = 128;

	public int height = 128;
	
	public String test = "N/A";

	public RenderMap() {
		this.mapTexture = Minecraft.getMinecraft().renderEngine.allocateAndSetupTexture(new BufferedImage(128, 128, 2));
		this.buffer = new int[16384];
	}

	public void render(float var1) {
		int var2 = this.posX;
		int var3 = this.posY;
		int var4 = this.width;
		int var5 = this.height;

		this.wrapper.getMinecraft().renderEngine.createTextureFromBytes(this.buffer, 128, 128, this.mapTexture);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Tessellator var6 = Tessellator.instance;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mapTexture);

		var6.startDrawingQuads();
		var6.addVertexWithUV((double)var2 - 1.5D, (double)(var3 + 0.5D + var5), 0.0D, 0.0D, 1.0D);
		var6.addVertexWithUV((double)(var2 + var4) - 1.5D, (double)(var3 + 0.5D + var5), 0.0D, 1.0D, 1.0D);
		var6.addVertexWithUV((double)(var2 + var4) - 1.5D, (double)var3 + 0.5D, 0.0D, 1.0D, 0.0D);
		var6.addVertexWithUV((double)var2 - 1.5D, (double)var3 + 0.5D, 0.0D, 0.0D, 0.0D);
		var6.draw();

		this.tick();
	}

	public void tick() {
		int var1 = (int)this.wrapper.getPlayer().posX - 64;
		int var2 = (int)this.wrapper.getPlayer().posZ - 64;

		for (int var5 = 8; var5 < 120; var5++) {
			for (int var6 = 8; var6 < 120; var6++) {
				int var3 = var1 + var5;
				int var4 = var2 + var6;
				this.buffer[var5 + var6 * 128] = -16777216 | this.getMapColorXZ(var3, var4);
			}
		}

		this.buffer[8256] = -1;
	}

	public int getMapColorXZ(int var1, int var2) {
		Chunk var3 = this.wrapper.getWorld().getChunkFromBlockCoords(var1, var2);
		int var4 = this.wrapValue(var1);
		int var5 = this.wrapValue(var2);
		int var6 = var3.getHeightValue(var4 % 16, var5 % 16) + 1;

		if(var6 <= 0) {
			return 0;
		} else {
			int var8 = var3.getBlockID(var4, var6, var5);
			boolean var7;

			do {
				var7 = true;

				if(var8 == 0) {
					var7 = false;
				} else if (var6 > 0 && var8 > 0 && Block.blocksList[var8].blockMaterial.materialMapColor == MapColor.airColor) {
					var7 = false;
				}

				if(!var7) {
					--var6;
					var8 = var3.getBlockID(var4, var6, var5);
				}
			}
			
			while (var6 > 0 && !var7);

			if(var6 > 0 && Block.blocksList[var8].blockMaterial.isLiquid()) {
				while(var6 > 0 && var8 > 0 && Block.blocksList[var8].blockMaterial.isLiquid()) {
					--var6;
					var8 = var3.getBlockID(var4, var6, var5);
				}

				++var6;
				var8 = var3.getBlockID(var4, var6, var5);
			}

			int var9 = MapColor.airColor.colorValue;
			Block var10 = Block.blocksList[var8];

			if(var10 == null) {
				return 0;
			} else {
				var9 = var10.blockMaterial.materialMapColor.colorValue;

				if(var10.blockMaterial.isLiquid()) {
					this.heightDiff = (var6 - 64) / 5 + 1;
				} else {
					this.heightDiff = var6 - this.prevheight;
					this.prevheight = var6;
				}

				this.colorOffset = 220;

				if(this.heightDiff > 0) {
					this.colorOffset = 255;
				} else if (this.heightDiff < 0) {
					this.colorOffset = 185;
				}

				int par4 = this.wrapper.getWorld().getBiomeGenForCoords(var1, var2).getBiomeFoliageColor();
				int par5 = this.wrapper.getWorld().getBiomeGenForCoords(var1, var2).getBiomeGrassColor();
				int par6 = this.wrapper.getWorld().getBiomeGenForCoords(var1, var2).waterColorMultiplier;
				int var11 = (var9 + (par4 - var9) >> 16 & 255) * this.colorOffset / 255;
				int var12 = (var9 + (par4 - var9) >> 8 & 255) * this.colorOffset / 255;
				int var13 = (var9 + (par4 - var9) & 255) * this.colorOffset / 255;

				if(Block.blocksList[var8] == Block.grass) {
					var11 = (var9+(par5 - var9 + 2) - 20 >> 16 & 255) * this.colorOffset / 255;
					var12 = (var9+(par5 - var9 + 2) - 20 >> 8 & 255) * this.colorOffset / 255;
					var13 = (var9+(par5 - var9 + 2) - 20 & 255) * this.colorOffset / 255;
				} else if(Block.blocksList[var8] != Block.leaves && Block.blocksList[var8] != Block.grass && Block.blocksList[var8] != Block.tallGrass) {
					var11 = (var9 >> 16 & 255) * this.colorOffset / 255;
					var12 = (var9 >> 8 & 255) * this.colorOffset / 255;
					var13 = (var9 & 255) * this.colorOffset / 255;
				}
				
				int var15 = var11 << 16
						| var12 << 8
						| var13;
				
				return var15;
			}
		}
	}

	protected int wrapValue(int var1) {
		var1 %= 16;

		if(var1 < 0) {
			var1 += 16;
		}

		return var1;
	}
}