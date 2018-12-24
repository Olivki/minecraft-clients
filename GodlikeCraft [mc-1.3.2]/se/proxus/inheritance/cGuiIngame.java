package se.proxus.inheritance;

import java.awt.Color;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class cGuiIngame extends Gui {
	
    private static final RenderItem itemRenderer = new RenderItem();
    private final Random rand = new Random();
    private final Minecraft mc;

    /** ChatGUI instance that retains all previous chat data */
    private final GuiNewChat persistantChatGUI;
    private int updateCounter = 0;

    /** The string specifying which record music is playing */
    private String recordPlaying = "";

    /** How many ticks the record playing message will be displayed */
    private int recordPlayingUpFor = 0;
    private boolean recordIsPlaying = false;

    /** Previous frame vignette brightness (slowly changes by 1% each frame) */
    public float prevVignetteBrightness = 1.0F;

    public cGuiIngame(Minecraft par1Minecraft) {
        this.mc = par1Minecraft;
        this.persistantChatGUI = new GuiNewChat(par1Minecraft);
    }

    /**
     * Render the ingame overlay with quick icon bar, ...
     */
    public void renderGameOverlay(float par1, boolean par2, int par3, int par4)
    {
        ScaledResolution var5 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        int var6 = var5.getScaledWidth();
        int var7 = var5.getScaledHeight();
        FontRenderer var8 = this.mc.fontRenderer;
        this.mc.entityRenderer.setupOverlayRendering();
        GL11.glEnable(GL11.GL_BLEND);

        if (Minecraft.isFancyGraphicsEnabled())
        {
            this.renderVignette(this.mc.thePlayer.getBrightness(par1), var6, var7);
        }
        else
        {
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        }

        ItemStack var9 = this.mc.thePlayer.inventory.armorItemInSlot(3);

        if (this.mc.gameSettings.thirdPersonView == 0 && var9 != null && var9.itemID == Block.pumpkin.blockID)
        {
            this.renderPumpkinBlur(var6, var7);
        }

        if (!this.mc.thePlayer.isPotionActive(Potion.confusion))
        {
            float var10 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * par1;

            if (var10 > 0.0F)
            {
                this.renderPortalOverlay(var10, var6, var7);
            }
        }

        boolean var11;
        int var12;
        int var13;
        int var17;
        int var16;
        int var19;
        int var20;
        int var23;
        int var22;
        int var47;

        if (!this.mc.playerController.func_78747_a())
        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/gui.png"));
            InventoryPlayer var31 = this.mc.thePlayer.inventory;
            this.zLevel = -90.0F;
            this.drawTexturedModalRect(var6 / 2 - 91, var7 - 22, 0, 0, 182, 22);
            this.drawTexturedModalRect(var6 / 2 - 91 - 1 + var31.currentItem * 20, var7 - 22 - 1, 0, 22, 24, 22);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/icons.png"));
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR);
            this.drawTexturedModalRect(var6 / 2 - 7, var7 / 2 - 7, 0, 0, 16, 16);
            GL11.glDisable(GL11.GL_BLEND);
            var11 = this.mc.thePlayer.hurtResistantTime / 3 % 2 == 1;

            if (this.mc.thePlayer.hurtResistantTime < 10)
            {
                var11 = false;
            }

            var12 = this.mc.thePlayer.getHealth();
            var13 = this.mc.thePlayer.prevHealth;
            this.rand.setSeed((long)(this.updateCounter * 312871));
            boolean var14 = false;
            FoodStats var15 = this.mc.thePlayer.getFoodStats();
            var16 = var15.getFoodLevel();
            var17 = var15.getPrevFoodLevel();
            this.mc.mcProfiler.startSection("bossHealth");
            this.renderBossHealth();
            this.mc.mcProfiler.endSection();
            int var18;

            if (this.mc.playerController.shouldDrawHUD())
            {
                var18 = var6 / 2 - 91;
                var19 = var6 / 2 + 91;
                this.mc.mcProfiler.startSection("expBar");
                var20 = this.mc.thePlayer.xpBarCap();

                if (var20 > 0)
                {
                    short var21 = 182;
                    var22 = (int)(this.mc.thePlayer.experience * (float)(var21 + 1));
                    var23 = var7 - 32 + 3;
                    this.drawTexturedModalRect(var18, var23, 0, 64, var21, 5);

                    if (var22 > 0)
                    {
                        this.drawTexturedModalRect(var18, var23, 0, 69, var22, 5);
                    }
                }

                var47 = var7 - 39;
                var22 = var47 - 10;
                var23 = this.mc.thePlayer.getTotalArmorValue();
                int var24 = -1;

                if (this.mc.thePlayer.isPotionActive(Potion.regeneration))
                {
                    var24 = this.updateCounter % 25;
                }

                this.mc.mcProfiler.endStartSection("healthArmor");
                int var25;
                int var26;
                int var29;
                int var28;

                for (var25 = 0; var25 < 10; ++var25)
                {
                    if (var23 > 0)
                    {
                        var26 = var18 + var25 * 8;

                        if (var25 * 2 + 1 < var23)
                        {
                            this.drawTexturedModalRect(var26, var22, 34, 9, 9, 9);
                        }

                        if (var25 * 2 + 1 == var23)
                        {
                            this.drawTexturedModalRect(var26, var22, 25, 9, 9, 9);
                        }

                        if (var25 * 2 + 1 > var23)
                        {
                            this.drawTexturedModalRect(var26, var22, 16, 9, 9, 9);
                        }
                    }

                    var26 = 16;

                    if (this.mc.thePlayer.isPotionActive(Potion.poison))
                    {
                        var26 += 36;
                    }

                    byte var27 = 0;

                    if (var11)
                    {
                        var27 = 1;
                    }

                    var28 = var18 + var25 * 8;
                    var29 = var47;

                    if (var12 <= 4)
                    {
                        var29 = var47 + this.rand.nextInt(2);
                    }

                    if (var25 == var24)
                    {
                        var29 -= 2;
                    }

                    byte var30 = 0;

                    if (this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled())
                    {
                        var30 = 5;
                    }

                    this.drawTexturedModalRect(var28, var29, 16 + var27 * 9, 9 * var30, 9, 9);

                    if (var11)
                    {
                        if (var25 * 2 + 1 < var13)
                        {
                            this.drawTexturedModalRect(var28, var29, var26 + 54, 9 * var30, 9, 9);
                        }

                        if (var25 * 2 + 1 == var13)
                        {
                            this.drawTexturedModalRect(var28, var29, var26 + 63, 9 * var30, 9, 9);
                        }
                    }

                    if (var25 * 2 + 1 < var12)
                    {
                        this.drawTexturedModalRect(var28, var29, var26 + 36, 9 * var30, 9, 9);
                    }

                    if (var25 * 2 + 1 == var12)
                    {
                        this.drawTexturedModalRect(var28, var29, var26 + 45, 9 * var30, 9, 9);
                    }
                }

                this.mc.mcProfiler.endStartSection("food");
                int var52;

                for (var25 = 0; var25 < 10; ++var25)
                {
                    var26 = var47;
                    var52 = 16;
                    byte var53 = 0;

                    if (this.mc.thePlayer.isPotionActive(Potion.hunger))
                    {
                        var52 += 36;
                        var53 = 13;
                    }

                    if (this.mc.thePlayer.getFoodStats().getSaturationLevel() <= 0.0F && this.updateCounter % (var16 * 3 + 1) == 0)
                    {
                        var26 = var47 + (this.rand.nextInt(3) - 1);
                    }

                    if (var14)
                    {
                        var53 = 1;
                    }

                    var29 = var19 - var25 * 8 - 9;
                    this.drawTexturedModalRect(var29, var26, 16 + var53 * 9, 27, 9, 9);

                    if (var14)
                    {
                        if (var25 * 2 + 1 < var17)
                        {
                            this.drawTexturedModalRect(var29, var26, var52 + 54, 27, 9, 9);
                        }

                        if (var25 * 2 + 1 == var17)
                        {
                            this.drawTexturedModalRect(var29, var26, var52 + 63, 27, 9, 9);
                        }
                    }

                    if (var25 * 2 + 1 < var16)
                    {
                        this.drawTexturedModalRect(var29, var26, var52 + 36, 27, 9, 9);
                    }

                    if (var25 * 2 + 1 == var16)
                    {
                        this.drawTexturedModalRect(var29, var26, var52 + 45, 27, 9, 9);
                    }
                }

                this.mc.mcProfiler.endStartSection("air");

                if (this.mc.thePlayer.isInsideOfMaterial(Material.water))
                {
                    var25 = this.mc.thePlayer.getAir();
                    var26 = MathHelper.ceiling_double_int((double)(var25 - 2) * 10.0D / 300.0D);
                    var52 = MathHelper.ceiling_double_int((double)var25 * 10.0D / 300.0D) - var26;

                    for (var28 = 0; var28 < var26 + var52; ++var28)
                    {
                        if (var28 < var26)
                        {
                            this.drawTexturedModalRect(var19 - var28 * 8 - 9, var22, 16, 18, 9, 9);
                        }
                        else
                        {
                            this.drawTexturedModalRect(var19 - var28 * 8 - 9, var22, 25, 18, 9, 9);
                        }
                    }
                }

                this.mc.mcProfiler.endSection();
            }

            GL11.glDisable(GL11.GL_BLEND);
            this.mc.mcProfiler.startSection("actionBar");
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.enableGUIStandardItemLighting();

            for (var18 = 0; var18 < 9; ++var18)
            {
                var19 = var6 / 2 - 90 + var18 * 20 + 2;
                var20 = var7 - 16 - 3;
                this.renderInventorySlot(var18, var19, var20, par1);
            }

            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            this.mc.mcProfiler.endSection();
        }

        float var33;

        if (this.mc.thePlayer.getSleepTimer() > 0)
        {
            this.mc.mcProfiler.startSection("sleep");
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            int var32 = this.mc.thePlayer.getSleepTimer();
            var33 = (float)var32 / 100.0F;

            if (var33 > 1.0F)
            {
                var33 = 1.0F - (float)(var32 - 100) / 10.0F;
            }

            var12 = (int)(220.0F * var33) << 24 | 1052704;
            drawRect(0, 0, var6, var7, var12);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            this.mc.mcProfiler.endSection();
        }

        int var38;
        int var40;

        if (this.mc.playerController.func_78763_f() && this.mc.thePlayer.experienceLevel > 0)
        {
            this.mc.mcProfiler.startSection("expLevel");
            var11 = false;
            var12 = var11 ? 16777215 : 8453920;
            String var35 = "" + this.mc.thePlayer.experienceLevel;
            var40 = (var6 - var8.getStringWidth(var35)) / 2;
            var38 = var7 - 31 - 4;
            var8.drawString(var35, var40 + 1, var38, 0);
            var8.drawString(var35, var40 - 1, var38, 0);
            var8.drawString(var35, var40, var38 + 1, 0);
            var8.drawString(var35, var40, var38 - 1, 0);
            var8.drawString(var35, var40, var38, var12);
            this.mc.mcProfiler.endSection();
        }

        if (this.mc.isDemo())
        {
            this.mc.mcProfiler.startSection("demo");
            String var36 = "";

            if (this.mc.theWorld.getWorldTime() >= 120500L)
            {
                var36 = StatCollector.translateToLocal("demo.demoExpired");
            }
            else
            {
                var36 = String.format(StatCollector.translateToLocal("demo.remainingTime"), new Object[] {StringUtils.ticksToElapsedTime((int)(120500L - this.mc.theWorld.getWorldTime()))});
            }

            var12 = var8.getStringWidth(var36);
            var8.drawStringWithShadow(var36, var6 - var12 - 10, 5, 16777215);
            this.mc.mcProfiler.endSection();
        }

        if (this.mc.gameSettings.showDebugInfo)
        {
            this.mc.mcProfiler.startSection("debug");
            GL11.glPushMatrix();
            var8.drawStringWithShadow("Minecraft 1.3.2 (" + this.mc.debug + ")", 2, 2, 16777215);
            var8.drawStringWithShadow(this.mc.debugInfoRenders(), 2, 12, 16777215);
            var8.drawStringWithShadow(this.mc.getEntityDebug(), 2, 22, 16777215);
            var8.drawStringWithShadow(this.mc.debugInfoEntities(), 2, 32, 16777215);
            var8.drawStringWithShadow(this.mc.getWorldProviderName(), 2, 42, 16777215);
            long var41 = Runtime.getRuntime().maxMemory();
            long var34 = Runtime.getRuntime().totalMemory();
            long var42 = Runtime.getRuntime().freeMemory();
            long var43 = var34 - var42;
            String var45 = "Used memory: " + var43 * 100L / var41 + "% (" + var43 / 1024L / 1024L + "MB) of " + var41 / 1024L / 1024L + "MB";
            this.drawString(var8, var45, var6 - var8.getStringWidth(var45) - 2, 2, 14737632);
            var45 = "Allocated memory: " + var34 * 100L / var41 + "% (" + var34 / 1024L / 1024L + "MB)";
            this.drawString(var8, var45, var6 - var8.getStringWidth(var45) - 2, 12, 14737632);
            this.drawString(var8, String.format("x: %.5f", new Object[] {Double.valueOf(this.mc.thePlayer.posX)}), 2, 64, 14737632);
            this.drawString(var8, String.format("y: %.3f (feet pos, %.3f eyes pos)", new Object[] {Double.valueOf(this.mc.thePlayer.boundingBox.minY), Double.valueOf(this.mc.thePlayer.posY)}), 2, 72, 14737632);
            this.drawString(var8, String.format("z: %.5f", new Object[] {Double.valueOf(this.mc.thePlayer.posZ)}), 2, 80, 14737632);
            this.drawString(var8, "f: " + (MathHelper.floor_double((double)(this.mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3), 2, 88, 14737632);
            var47 = MathHelper.floor_double(this.mc.thePlayer.posX);
            var22 = MathHelper.floor_double(this.mc.thePlayer.posY);
            var23 = MathHelper.floor_double(this.mc.thePlayer.posZ);

            if (this.mc.theWorld != null && this.mc.theWorld.blockExists(var47, var22, var23))
            {
                Chunk var48 = this.mc.theWorld.getChunkFromBlockCoords(var47, var23);
                this.drawString(var8, "lc: " + (var48.getTopFilledSegment() + 15) + " b: " + var48.getBiomeGenForWorldCoords(var47 & 15, var23 & 15, this.mc.theWorld.getWorldChunkManager()).biomeName + " bl: " + var48.getSavedLightValue(EnumSkyBlock.Block, var47 & 15, var22, var23 & 15) + " sl: " + var48.getSavedLightValue(EnumSkyBlock.Sky, var47 & 15, var22, var23 & 15) + " rl: " + var48.getBlockLightValue(var47 & 15, var22, var23 & 15, 0), 2, 96, 14737632);
            }

            this.drawString(var8, String.format("ws: %.3f, fs: %.3f, g: %b", new Object[] {Float.valueOf(this.mc.thePlayer.capabilities.getWalkSpeed()), Float.valueOf(this.mc.thePlayer.capabilities.getFlySpeed()), Boolean.valueOf(this.mc.thePlayer.onGround)}), 2, 104, 14737632);
            GL11.glPopMatrix();
            this.mc.mcProfiler.endSection();
        } else {
        	this.mc.dm.ingame.drawOverlay(par1, par3, par4);
        }

        if (this.recordPlayingUpFor > 0)
        {
            this.mc.mcProfiler.startSection("overlayMessage");
            var33 = (float)this.recordPlayingUpFor - par1;
            var12 = (int)(var33 * 256.0F / 20.0F);

            if (var12 > 255)
            {
                var12 = 255;
            }

            if (var12 > 0)
            {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(var6 / 2), (float)(var7 - 48), 0.0F);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                var13 = 16777215;

                if (this.recordIsPlaying)
                {
                    var13 = Color.HSBtoRGB(var33 / 50.0F, 0.7F, 0.6F) & 16777215;
                }

                var8.drawString(this.recordPlaying, -var8.getStringWidth(this.recordPlaying) / 2, -4, var13 + (var12 << 24));
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glPopMatrix();
            }

            this.mc.mcProfiler.endSection();
        }

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, (float)(var7 - 48), 0.0F);
        this.mc.mcProfiler.startSection("chat");
        this.persistantChatGUI.func_73762_a(this.updateCounter);
        this.mc.mcProfiler.endSection();
        GL11.glPopMatrix();

        if (this.mc.gameSettings.keyBindPlayerList.pressed && (!this.mc.isIntegratedServerRunning() || this.mc.thePlayer.sendQueue.playerInfoList.size() > 1))
        {
            this.mc.mcProfiler.startSection("playerList");
            NetClientHandler var37 = this.mc.thePlayer.sendQueue;
            List var39 = var37.playerInfoList;
            var13 = var37.currentServerMaxPlayers;
            var40 = var13;

            for (var38 = 1; var40 > 20; var40 = (var13 + var38 - 1) / var38)
            {
                ++var38;
            }

            var16 = 300 / var38;

            if (var16 > 150)
            {
                var16 = 150;
            }

            var17 = (var6 - var38 * var16) / 2;
            byte var44 = 10;
            drawRect(var17 - 1, var44 - 1, var17 + var16 * var38, var44 + 9 * var40, Integer.MIN_VALUE);

            for (var19 = 0; var19 < var13; ++var19)
            {
                var20 = var17 + var19 % var38 * var16;
                var47 = var44 + var19 / var38 * 9;
                drawRect(var20, var47, var20 + var16 - 1, var47 + 8, 553648127);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(GL11.GL_ALPHA_TEST);

                if (var19 < var39.size())
                {
                    GuiPlayerInfo var46 = (GuiPlayerInfo)var39.get(var19);
                    var8.drawStringWithShadow(var46.name, var20, var47, 16777215);
                    this.mc.renderEngine.bindTexture(this.mc.renderEngine.getTexture("/gui/icons.png"));
                    byte var51 = 0;
                    boolean var49 = false;
                    byte var50;

                    if (var46.responseTime < 0)
                    {
                        var50 = 5;
                    }
                    else if (var46.responseTime < 150)
                    {
                        var50 = 0;
                    }
                    else if (var46.responseTime < 300)
                    {
                        var50 = 1;
                    }
                    else if (var46.responseTime < 600)
                    {
                        var50 = 2;
                    }
                    else if (var46.responseTime < 1000)
                    {
                        var50 = 3;
                    }
                    else
                    {
                        var50 = 4;
                    }

                    this.zLevel += 100.0F;
                    this.drawTexturedModalRect(var20 + var16 - 12, var47, 0 + var51 * 10, 176 + var50 * 8, 10, 8);
                    this.zLevel -= 100.0F;
                }
            }
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
    }

    /**
     * Renders dragon's (boss) health on the HUD
     */
    private void renderBossHealth()
    {
        if (RenderDragon.entityDragon != null)
        {
            EntityDragon var1 = RenderDragon.entityDragon;
            RenderDragon.entityDragon = null;
            FontRenderer var2 = this.mc.fontRenderer;
            ScaledResolution var3 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
            int var4 = var3.getScaledWidth();
            short var5 = 182;
            int var6 = var4 / 2 - var5 / 2;
            int var7 = (int)((float)var1.getDragonHealth() / (float)var1.getMaxHealth() * (float)(var5 + 1));
            byte var8 = 12;
            this.drawTexturedModalRect(var6, var8, 0, 74, var5, 5);
            this.drawTexturedModalRect(var6, var8, 0, 74, var5, 5);

            if (var7 > 0)
            {
                this.drawTexturedModalRect(var6, var8, 0, 79, var7, 5);
            }

            String var9 = "Boss health";
            var2.drawStringWithShadow(var9, var4 / 2 - var2.getStringWidth(var9) / 2, var8 - 10, 16711935);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/icons.png"));
        }
    }

    private void renderPumpkinBlur(int par1, int par2)
    {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("%blur%/misc/pumpkinblur.png"));
        Tessellator var3 = Tessellator.instance;
        var3.startDrawingQuads();
        var3.addVertexWithUV(0.0D, (double)par2, -90.0D, 0.0D, 1.0D);
        var3.addVertexWithUV((double)par1, (double)par2, -90.0D, 1.0D, 1.0D);
        var3.addVertexWithUV((double)par1, 0.0D, -90.0D, 1.0D, 0.0D);
        var3.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
        var3.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Renders the vignette. Args: vignetteBrightness, width, height
     */
    private void renderVignette(float par1, int par2, int par3)
    {
        par1 = 1.0F - par1;

        if (par1 < 0.0F)
        {
            par1 = 0.0F;
        }

        if (par1 > 1.0F)
        {
            par1 = 1.0F;
        }

        this.prevVignetteBrightness = (float)((double)this.prevVignetteBrightness + (double)(par1 - this.prevVignetteBrightness) * 0.01D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_ZERO, GL11.GL_ONE_MINUS_SRC_COLOR);
        GL11.glColor4f(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0F);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("%blur%/misc/vignette.png"));
        Tessellator var4 = Tessellator.instance;
        var4.startDrawingQuads();
        var4.addVertexWithUV(0.0D, (double)par3, -90.0D, 0.0D, 1.0D);
        var4.addVertexWithUV((double)par2, (double)par3, -90.0D, 1.0D, 1.0D);
        var4.addVertexWithUV((double)par2, 0.0D, -90.0D, 1.0D, 0.0D);
        var4.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
        var4.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    /**
     * Renders the portal overlay. Args: portalStrength, width, height
     */
    private void renderPortalOverlay(float par1, int par2, int par3)
    {
        if (par1 < 1.0F)
        {
            par1 *= par1;
            par1 *= par1;
            par1 = par1 * 0.8F + 0.2F;
        }

        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, par1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/terrain.png"));
        float var4 = (float)(Block.portal.blockIndexInTexture % 16) / 16.0F;
        float var5 = (float)(Block.portal.blockIndexInTexture / 16) / 16.0F;
        float var6 = (float)(Block.portal.blockIndexInTexture % 16 + 1) / 16.0F;
        float var7 = (float)(Block.portal.blockIndexInTexture / 16 + 1) / 16.0F;
        Tessellator var8 = Tessellator.instance;
        var8.startDrawingQuads();
        var8.addVertexWithUV(0.0D, (double)par3, -90.0D, (double)var4, (double)var7);
        var8.addVertexWithUV((double)par2, (double)par3, -90.0D, (double)var6, (double)var7);
        var8.addVertexWithUV((double)par2, 0.0D, -90.0D, (double)var6, (double)var5);
        var8.addVertexWithUV(0.0D, 0.0D, -90.0D, (double)var4, (double)var5);
        var8.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Renders the specified item of the inventory slot at the specified location. Args: slot, x, y, partialTick
     */
    private void renderInventorySlot(int par1, int par2, int par3, float par4)
    {
        ItemStack var5 = this.mc.thePlayer.inventory.mainInventory[par1];

        if (var5 != null)
        {
            float var6 = (float)var5.animationsToGo - par4;

            if (var6 > 0.0F)
            {
                GL11.glPushMatrix();
                float var7 = 1.0F + var6 / 5.0F;
                GL11.glTranslatef((float)(par2 + 8), (float)(par3 + 12), 0.0F);
                GL11.glScalef(1.0F / var7, (var7 + 1.0F) / 2.0F, 1.0F);
                GL11.glTranslatef((float)(-(par2 + 8)), (float)(-(par3 + 12)), 0.0F);
            }

            itemRenderer.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, var5, par2, par3);

            if (var6 > 0.0F)
            {
                GL11.glPopMatrix();
            }

            itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, var5, par2, par3);
        }
    }

    /**
     * The update tick for the ingame UI
     */
    public void updateTick()
    {
        if (this.recordPlayingUpFor > 0)
        {
            --this.recordPlayingUpFor;
        }

        ++this.updateCounter;
    }

    public void setRecordPlayingMessage(String par1Str)
    {
        this.recordPlaying = "Now playing: " + par1Str;
        this.recordPlayingUpFor = 60;
        this.recordIsPlaying = true;
    }

    /**
     * returns a pointer to the persistant Chat GUI, containing all previous chat messages and such
     */
    public GuiNewChat getChatGUI()
    {
        return this.persistantChatGUI;
    }

    public int getUpdateCounter()
    {
        return this.updateCounter;
    }
}