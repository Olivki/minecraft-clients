package se.proxus.screens.slots;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiSlot;
import net.minecraft.src.ImageBufferDownload;
import net.minecraft.src.StringUtils;
import net.minecraft.src.Tessellator;

import org.lwjgl.opengl.GL11;

import se.proxus.mods.list.none.Friends;
import se.proxus.screens.ScreenFriendManager;
import se.proxus.tools.ArrayHelper;
import se.proxus.tools.Colours;

public class SlotFriend extends GuiSlot {

    private final ScreenFriendManager guiScreen;
    private int selected;

    public SlotFriend(final ScreenFriendManager guiScreen) {
	super(Minecraft.getMinecraft(), guiScreen.width, guiScreen.height, 32,
		guiScreen.height - 59, 24);
	this.guiScreen = guiScreen;
	selected = 0;
    }

    @Override
    protected int getSize() {
	return ((Friends) Minecraft.getMinecraft().dp.getMods().getMod(
		"Friends")).getLoadedFriends().size();
    }

    @Override
    protected int getContentHeight() {
	return getSize() * 24;
    }

    @Override
    protected void drawBackground() {
	guiScreen.drawDefaultBackground();
    }

    @Override
    protected void drawSlot(final int selected, final int var1, final int var2,
	    final int var3, final Tessellator tesselator) {
	ArrayHelper friend = ((Friends) Minecraft.getMinecraft().dp.getMods()
		.getMod("Friends")).getLoadedFriends().get(selected);
	FontRenderer font = Minecraft.getMinecraft().fontRenderer;
	guiScreen.drawString(font,
		"Åò0Åò1Åò2Åò3Åò4Åò5Åò6Åò7Åò8Åò9ÅòyÅòr" + friend.getKey(), var1, var2 + 1,
		0xFFFFFFFF);
	guiScreen.drawString(font,
		Colours.DARK_AQUA + (String) friend.getValue(), var1,
		var2 + 11, 0xFF808080);
	drawPlayerHead(guiScreen.width / 2 + 88, var2, (String) friend.getKey());
    }

    @Override
    protected void elementClicked(final int selected, final boolean var0) {
	this.selected = selected;
    }

    @Override
    protected boolean isSelected(final int var0) {
	return selected == var0;
    }

    public int getSelected() {
	return selected;
    }

    public void drawPlayerHead(final int x, final float y, final String username) {
	Minecraft mc = Minecraft.getMinecraft();
	float zLevel = 0;
	GL11.glPushMatrix();
	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	mc.renderEngine.obtainImageData(
		"http://s3.amazonaws.com/MinecraftSkins/"
			+ StringUtils.stripControlCodes(username) + ".png",
		new ImageBufferDownload());
	GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine
		.getTextureForDownloadableImage(
			"http://s3.amazonaws.com/MinecraftSkins/"
				+ StringUtils.stripControlCodes(username)
				+ ".png", "/mob/char.png"));
	float f1 = 2.5f;
	float f = 2.5F;
	int x2 = x + 184;
	GL11.glScalef(f1, f1, f1);
	drawTexturedModalRectZ(x / f, y / f, 8, 8, 8, 8, zLevel);
	drawTexturedModalRectZ(x / f, y / f, 40, 8, 8, 8, zLevel);
	GL11.glPopMatrix();
    }

    public void drawTexturedModalRectZ(final float i, final float y,
	    final int k, final int i1, final int j1, final int k1,
	    final float zLevel) {
	float f = 0.015625F;
	float f1 = 0.03125F;
	Tessellator tesselator = Tessellator.instance;
	tesselator.startDrawingQuads();
	tesselator.addVertexWithUV(i + 0, y + k1, zLevel, (k + 0) * f,
		(i1 + k1) * f1);
	tesselator.addVertexWithUV(i + j1, y + k1, zLevel, (k + j1) * f,
		(i1 + k1) * f1);
	tesselator.addVertexWithUV(i + j1, y + 0, zLevel, (k + j1) * f,
		(i1 + 0) * f1);
	tesselator.addVertexWithUV(i + 0, y + 0, zLevel, (k + 0) * f, (i1 + 0)
		* f1);
	tesselator.draw();
    }
}