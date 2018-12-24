package se.proxus.tools;

import net.minecraft.src.StringUtils;

import org.lwjgl.opengl.GL11;

import se.proxus.Gallium;
import se.proxus.betterfonts.StringCache;
import se.proxus.events.list.client.EventRenderString;

public class FontFactory {

    private Gallium client;

    public FontFactory(final Gallium client) {
	setClient(client);
    }

    public void drawStringWithShadow(String text, final float x, final float y,
	    int hex, final int type) {
	if (text == null)
	    return;
	getClient().init(1);
	int shade = (hex & 16579836) >> 2 | hex & -16777216;
	EventRenderString event = (EventRenderString) getClient().getEvents()
		.sendEvent(new EventRenderString(text, hex));
	hex = event.getColour();
	text = event.getText();
	switch (type) {
	case 0:
	    getClient().getFont().renderString(
		    StringUtils.stripControlCodes(text), x + 0.5F, y + 0.5F,
		    shade, false);
	    getClient().getFont().renderString(text, x, y, hex, false);
	    break;
	case 1:
	    getClient().getFontPanel().renderString(
		    StringUtils.stripControlCodes(text), x + 0.5F, y + 0.5F,
		    shade, true);
	    getClient().getFontPanel().renderString(text, x, y, hex, false);
	    break;
	case 2:
	    getClient().getFontChat().renderString(
		    StringUtils.stripControlCodes(text), x + 1.0F, y + 1.0F,
		    0xFF000000, false);
	    getClient().getFontChat().renderString(text, x, y, hex, false);
	    break;
	case 3:
	    getClient().getMinecraft().fontRenderer.drawStringWithShadow(text,
		    x, y, hex);
	    break;
	case 4:
	    getClient().getMinecraft().fontRenderer.renderString(text,
		    x + 0.25F, y + 0.25F, hex, true);
	    getClient().getMinecraft().fontRenderer.renderString(text,
		    x - 0.25F, y + 0.25F, hex, true);
	    getClient().getMinecraft().fontRenderer.renderString(text,
		    x + 0.25F, y - 0.25F, hex, true);
	    getClient().getMinecraft().fontRenderer.renderString(text,
		    x - 0.25F, y - 0.25F, hex, true);
	    getClient().getMinecraft().fontRenderer.drawString(text, x, y, hex);
	    break;
	case 5:
	    getClient().getMinecraft().fontRenderer.renderString(text,
		    x + 0.25F, y + 0.25F, hex, true);
	    getClient().getMinecraft().fontRenderer.renderString(text,
		    x - 0.25F, y + 0.25F, hex, true);
	    getClient().getMinecraft().fontRenderer.renderString(text,
		    x + 0.25F, y - 0.5F, hex, true);
	    getClient().getMinecraft().fontRenderer.renderString(text,
		    x - 0.5F, y - 0.25F, hex, true);
	    getClient().getMinecraft().fontRenderer.drawString(text, x, y, hex);
	    break;
	default:
	    if (getClient().getFont() == null) {
		getClient().setFont(
			new StringCache(getClient().getMinecraft().fontRenderer
				.getColorCode()));
		getClient().getFont()
			.setDefaultFont("Lucida Console", 18, true);
	    }
	    getClient().getFont().renderString(
		    StringUtils.stripControlCodes(text), x + 1.0F, y + 1.0F,
		    shade, true);
	    getClient().getFont().renderString(text, x, y, hex, false);
	    break;
	}
    }

    public void drawString(String text, final float x, final float y, int hex,
	    final int type) {
	if (text == null)
	    return;
	EventRenderString event = (EventRenderString) getClient().getEvents()
		.sendEvent(new EventRenderString(text, hex));
	hex = event.getColour();
	text = event.getText();
	switch (type) {
	case 0:
	    getClient().getFont().renderString(text, x, y, hex, false);
	    break;
	case 1:
	    getClient().getFontPanel().renderString(text, x, y, hex, false);
	    break;
	case 2:
	    getClient().getFontChat().renderString(text, x, y, hex, false);
	    break;
	case 3:
	    getClient().getMinecraft().fontRenderer.drawString(text, x, y, hex);
	    break;
	default:
	    getClient().getFont().renderString(text, x, y, hex, false);
	    break;
	}
    }

    public void drawSizedString(final String text, final float x,
	    final float y, final int hex, final int type, final float size,
	    final boolean shadow) {
	GL11.glPushMatrix();
	GL11.glScalef(size, size, size);
	if (shadow) {
	    if (type == 4 && size < 2.0F)
		drawStringWithShadow(text, x / size, y / size, hex, 5);
	    else
		drawStringWithShadow(text, x / size, y / size, hex, type);
	} else
	    drawString(text, x / size, y / size, hex, type);
	GL11.glPopMatrix();
    }

    public void drawCenteredString(final String text, final float x,
	    final float y, final int hex, final int type, final boolean shadow) {
	if (shadow)
	    drawStringWithShadow(text, x - getStringWidth(text, type) / 2, y,
		    hex, type);
	else
	    drawString(text, x / 2 - getStringWidth(text, type) / 2, y, hex,
		    type);
    }

    public void drawCenteredSizedString(final String text, final float x,
	    final float y, final int hex, final int type, final float size,
	    final boolean shadow) {
	drawSizedString(text, x - getSizedStringWidth(text, size) / 2, y, hex,
		type, size, shadow);
    }

    public int getStringWidth(String text, final int type) {
	EventRenderString event = (EventRenderString) getClient().getEvents()
		.sendEvent(new EventRenderString(text, 0));
	text = event.getText();
	getClient().init(1);
	switch (type) {
	case 0:
	    return getClient().getFont().getStringWidth(text);
	case 1:
	    return getClient().getFontPanel().getStringWidth(text);
	case 2:
	    return getClient().getFontChat().getStringWidth(text);
	default:
	    return getClient().getFont().getStringWidth(text);
	}
    }

    public float getSizedStringWidth(String text, final float size) {
	EventRenderString event = (EventRenderString) getClient().getEvents()
		.sendEvent(new EventRenderString(text, 0));
	text = event.getText();
	return getClient().getMinecraft().fontRenderer.getStringWidth(text)
		* size;
    }

    public Gallium getClient() {
	return client;
    }

    public void setClient(final Gallium client) {
	this.client = client;
    }
}