package se.proxus.tools;

import java.awt.image.BufferedImage;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.Chunk;
import net.minecraft.src.EnumSkyBlock;
import net.minecraft.src.MapColor;
import net.minecraft.src.Tessellator;

import org.lwjgl.opengl.GL11;

public class RenderMap {

    public final int scale = 1;
    public int[] buffer;
    public int mapTexture;
    public int center = 64;
    public int colorOffset;
    public int prevheight = 0;
    public int heightDiff;
    public int mapSize = 128;
    public int posX = 1;
    public int posY = 1;
    public int width = 128;
    public int height = 128;
    public BufferedImage mapImage;

    public RenderMap() {
	mapImage = new BufferedImage(mapSize, mapSize, 2);
	this.mapTexture = Minecraft.getMinecraft().renderEngine
		.allocateAndSetupTexture(mapImage);
	this.buffer = new int[mapSize * mapSize];
    }

    public void render() {
	GL11.glPushMatrix();
	if (Minecraft.getMinecraft().theWorld == null)
	    return;
	mapSize = 150;
	center = mapSize / 2;
	if (mapSize != mapImage.getWidth()) {
	    mapImage = new BufferedImage(mapSize, mapSize, 2);
	    this.mapTexture = Minecraft.getMinecraft().renderEngine
		    .allocateAndSetupTexture(mapImage);
	    this.buffer = new int[mapSize * mapSize];
	} else {
	    int var2 = this.posX;
	    int var3 = this.posY;
	    int var4 = this.width;
	    int var5 = this.height;
	    Minecraft.getMinecraft().renderEngine.createTextureFromBytes(
		    this.buffer, mapSize, mapSize, this.mapTexture);
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    Tessellator var6 = Tessellator.instance;
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mapTexture);

	    var6.startDrawingQuads();
	    var6.addVertexWithUV(var2 - 1.5D, var3 + 0.5D + var5, 0.0D, 0.0D,
		    1.0D);
	    var6.addVertexWithUV(var2 + var4 - 1.5D, var3 + 0.5D + var5, 0.0D,
		    1.0D, 1.0D);
	    var6.addVertexWithUV(var2 + var4 - 1.5D, var3 + 0.5D, 0.0D, 1.0D,
		    0.0D);
	    var6.addVertexWithUV(var2 - 1.5D, var3 + 0.5D, 0.0D, 0.0D, 0.0D);
	    var6.draw();
	    /*
	     * float darkNess =
	     * 1F/360F*(Declarations.mc.theWorld.getCelestialAngle(1)*360 -
	     * 180); if(darkNess < 0){ darkNess = -darkNess; }
	     * GL11.glColor3f(0.25F+darkNess*2, 0.25F+darkNess*2,
	     * 0.25F+darkNess*2);
	     */
	    // drawTextureID(var2, var3, var4, var5, this.mapTexture);
	    /*
	     * Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(var2,
	     * var3, var4, var5, this.mapTexture);
	     */
	    tick();

	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, 4);
	}
	GL11.glPopMatrix();
    }

    public void tick() {
	int var1 = (int) Minecraft.getMinecraft().thePlayer.posX - center;
	int var2 = (int) Minecraft.getMinecraft().thePlayer.posZ - center;

	for (int var5 = 0; var5 < mapSize; var5++)
	    for (int var6 = 0; var6 < mapSize; var6++) {
		int var3 = var1 + var5;
		int var4 = var2 + var6;
		this.buffer[var5 + var6 * mapSize] = -16777216
			| this.getMapColorXZ(var3, var4);
	    }
    }

    public int getMapColorXZ(final int var1, final int var2) {
	Chunk var3 = Minecraft.getMinecraft().theWorld.getChunkFromBlockCoords(
		var1, var2);
	int var4 = this.wrapValue(var1);
	int var5 = this.wrapValue(var2);
	int var6 = var3.getHeightValue(var4 % 16, var5 % 16) + 1;

	int lightVal = 1;
	if (Minecraft.getMinecraft().theWorld.chunkExists(var5, var6))
	    lightVal = var3.getSavedLightValue(EnumSkyBlock.Block, var1 & 15,
		    var6 - 1, var2 & 15);

	if (var6 <= 0)
	    return 0;
	else {
	    int var8 = var3.getBlockID(var4, var6, var5);
	    boolean var7;

	    do {
		var7 = true;

		if (var8 == 0)
		    var7 = false;
		else if (var6 > 0
			&& var8 > 0
			&& Block.blocksList[var8].blockMaterial.materialMapColor == MapColor.airColor)
		    var7 = false;

		if (!var7) {
		    --var6;
		    var8 = var3.getBlockID(var4, var6, var5);
		    if (Minecraft.getMinecraft().theWorld.chunkExists(var5,
			    var6))
			lightVal = var3.getSavedLightValue(EnumSkyBlock.Block,
				var1 & 15, var6 + 1, var2 & 15);
		}
	    } while (var6 > 0 && !var7);

	    if (var6 > 0 && Block.blocksList[var8].blockMaterial.isLiquid()) {
		while (var6 > 0 && var8 > 0
			&& Block.blocksList[var8].blockMaterial.isLiquid()) {
		    --var6;
		    var8 = var3.getBlockID(var4, var6, var5);
		    if (Minecraft.getMinecraft().theWorld.chunkExists(var5,
			    var6))
			lightVal = var3.getSavedLightValue(EnumSkyBlock.Block,
				var1 & 15, var6 + 1, var2 & 15);
		}

		++var6;
		var8 = var3.getBlockID(var4, var6, var5);
		if (Minecraft.getMinecraft().theWorld.chunkExists(var5, var6))
		    lightVal = var3.getSavedLightValue(EnumSkyBlock.Block,
			    var1 & 15, var6 + 1, var2 & 15);
	    }

	    int var9 = MapColor.airColor.colorValue;
	    Block var10 = Block.blocksList[var8];

	    if (var10 == null)
		return 0;
	    else {
		var9 = var10.blockMaterial.materialMapColor.colorValue;

		if (var10.blockMaterial.isLiquid())
		    this.heightDiff = (var6 - 64) / 5 + 1;
		else {
		    this.heightDiff = var6 - this.prevheight;
		    this.prevheight = var6;
		}

		this.colorOffset = 220;

		if (this.heightDiff > 0)
		    this.colorOffset = 255;
		else if (this.heightDiff < 0)
		    this.colorOffset = 185;

		float darkNess = Math
			.abs(1F / 360F * (Minecraft.getMinecraft().theWorld
				.getCelestialAngle(1) * 360 - 180)) * 100;
		darkNess = darkNess
			+ Minecraft.getMinecraft().gameSettings.gammaSetting
			* 5;
		if (darkNess < 10)
		    darkNess = 10;
		float max = 50F;
		lightVal *= 2;
		lightVal = (int) (lightVal / 20F * (1F - darkNess / 50F * 1F) * 15);
		int dn = (int) (darkNess / 50F * max + lightVal);

		int par4 = Minecraft.getMinecraft().theWorld
			.getBiomeGenForCoords(var1, var2)
			.getBiomeFoliageColor();
		int par5 = Minecraft.getMinecraft().theWorld
			.getBiomeGenForCoords(var1, var2).getBiomeGrassColor();
		int par6 = Minecraft.getMinecraft().theWorld
			.getBiomeGenForCoords(var1, var2).waterColorMultiplier;
		int lightModifier = dn + lightVal + 1;
		int div = 255;
		int var11 = (var9 + par4 - var9 >> 16 & 255) * this.colorOffset
			/ div;
		int var12 = (var9 + par4 - var9 >> 8 & 255) * this.colorOffset
			/ div;
		int var13 = (var9 + par4 - var9 & 255) * this.colorOffset / div;

		if (Block.blocksList[var8] == Block.grass) {
		    var11 = (var9 + par5 - var9 + 2 - 20 >> 16 & 255)
			    * this.colorOffset / div;
		    var12 = (var9 + par5 - var9 + 2 - 20 >> 8 & 255)
			    * this.colorOffset / div;
		    var13 = (var9 + par5 - var9 + 2 - 20 & 255)
			    * this.colorOffset / div;
		} else if (Block.blocksList[var8] != Block.leaves
			&& Block.blocksList[var8] != Block.grass
			&& Block.blocksList[var8] != Block.tallGrass
			&& Block.blocksList[var8] != Block.vine) {
		    var11 = (var9 >> 16 & 255) * this.colorOffset / div;
		    var12 = (var9 >> 8 & 255) * this.colorOffset / div;
		    var13 = (var9 & 255) * this.colorOffset / div;
		}
		var11 *= dn;
		var12 *= dn;
		var13 *= dn;
		var11 /= 50;
		var12 /= 50;
		var13 /= 50;
		return var11 << 16 | var12 << 8 | var13;
	    }
	}
    }

    protected int wrapValue(int var1) {
	var1 %= 16;

	if (var1 < 0)
	    var1 += 16;

	return var1;
    }

    public void drawTextureID(final int x, final int y, final int width,
	    final int height, final int id) {
	GL11.glPushMatrix();
	GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	GL11.glEnable(GL11.GL_BLEND);
	GL11.glTranslatef(x, y, 0.0F);
	Tessellator tessellator = Tessellator.instance;
	tessellator.startDrawingQuads();
	tessellator.addVertexWithUV(0, height, 0, 0.0D, 1.0D);
	tessellator.addVertexWithUV(width, height, 0, 1.0D, 1.0D);
	tessellator.addVertexWithUV(width, 0, 0, 1.0D, 0.0D);
	tessellator.addVertexWithUV(0, 0, 0, 0.0D, 0.0D);
	tessellator.draw();
	GL11.glBindTexture(GL11.GL_TEXTURE_2D, 2);
	GL11.glDisable(GL11.GL_BLEND);
	GL11.glPopMatrix();
    }
}