package se.proxus.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;

import org.lwjgl.opengl.GL11;

import se.proxus.mods.Mod;

public class ButtonModSlider extends GuiButton {

    private double sliderValue;
    private boolean dragging;
    private final Minecraft mc = Minecraft.getMinecraft();
    private Mod mod;
    private double max;
    private int id;
    private String value;

    public ButtonModSlider(final String text, final int i, final int j,
	    final int k, final int id, final Mod mod, final double max) {
	this(text, i, j, k, 200, 20, id, mod, max);
    }

    public ButtonModSlider(final String text, final int i, final int j,
	    final int k, final int l, final int i1, final int id,
	    final Mod mod, final double max) {
	super(i, j, k, l, i1, text);
	dragging = false;
	setId(id);
	setMod(mod);
	setMax(max);
	sliderValue = 0.0F;
    }

    @Override
    public void drawButton(final Minecraft par1Minecraft, final int par2,
	    final int par3) {
	handleValues();
	if (this.drawButton) {
	    FontRenderer var4 = par1Minecraft.fontRenderer;
	    par1Minecraft.renderEngine.bindTexture("/gui/gui.png");
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    this.field_82253_i = par2 >= this.xPosition
		    && par3 >= this.yPosition
		    && par2 < this.xPosition + this.width
		    && par3 < this.yPosition + this.height;
	    int var5 = this.getHoverState(this.field_82253_i);
	    this.drawTexturedModalRect(this.xPosition, this.yPosition, 0,
		    46 + var5 * 20, this.width / 2, this.height);
	    this.drawTexturedModalRect(this.xPosition + this.width / 2,
		    this.yPosition, 200 - this.width / 2, 46 + var5 * 20,
		    this.width / 2, this.height);
	    this.mouseDragged(par1Minecraft, par2, par3);
	    int var6 = 14737632;

	    if (!this.enabled)
		var6 = -6250336;
	    else if (this.field_82253_i)
		var6 = 16777120;

	    this.drawCenteredString(var4, this.displayString + ": "
		    + getValue(), this.xPosition + this.width / 2,
		    this.yPosition + (this.height - 8) / 2, var6);
	}
    }

    @Override
    protected int getHoverState(final boolean flag) {
	return 0;
    }

    @Override
    protected void mouseDragged(final Minecraft minecraft, final int x,
	    final int y) {
	if (!drawButton)
	    return;

	if (dragging) {
	    setSliderValue((float) (((double) x - (double) xPosition) / width));
	    if (sliderValue < 0.0F)
		sliderValue = 0.0F;
	    if (sliderValue > 1.0F)
		sliderValue = 1.0F;
	    setAmtInOptions(Double.valueOf(getSliderValue() * getMax()));
	    getMod().saveSettings();
	}

	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	this.drawTexturedModalRect(this.xPosition
		+ (int) (this.sliderValue * (this.width - 8)), this.yPosition,
		0, 66, 4, 20);
	this.drawTexturedModalRect(this.xPosition
		+ (int) (this.sliderValue * (this.width - 8)) + 4,
		this.yPosition, 196, 66, 4, 20);
    }

    @Override
    public boolean mousePressed(final Minecraft minecraft, final int x,
	    final int y) {
	if (super.mousePressed(minecraft, x, y)) {
	    setSliderValue((float) (((double) x - (double) xPosition) / width));
	    if (sliderValue < 0.0F)
		sliderValue = 0.0F;
	    if (sliderValue > 1.0F)
		sliderValue = 1.0F;
	    setAmtInOptions(Double.valueOf(getSliderValue() * getMax()));
	    getMod().saveSettings();
	    dragging = true;
	    return true;
	} else
	    return false;
    }

    @Override
    public void mouseReleased(final int i, final int j) {
	dragging = false;
    }

    public void handleValues() {
	if (mod.getSetting(getId()) instanceof Double)
	    setValue("" + (int) Math.floor(getDoubleFromOptions() * 10D) / 10D);
	else if (mod.getSetting(getId()) instanceof Float)
	    setValue("" + (int) Math.floor(getFloatFromOptions() * 10F) / 10D);
	else if (mod.getSetting(getId()) instanceof Integer)
	    setValue("" + (int) Math.floor(getIntegerFromOptions() * 10) / 10);
	else if (mod.getSetting(getId()) instanceof Long)
	    setValue("" + (int) Math.floor(getLongFromOptions() * 10L) / 10L);

	if (mod.getSetting(getId()) instanceof Double)
	    setSliderValue((float) (getDoubleFromOptions() / getMax()));
	else if (this.mod.getSetting(getId()) instanceof Float)
	    setSliderValue((float) (getFloatFromOptions() / getMax()));
	else if (this.mod.getSetting(getId()) instanceof Integer)
	    setSliderValue((float) (getIntegerFromOptions() / getMax()));
	else if (this.mod.getSetting(getId()) instanceof Long)
	    setSliderValue((float) (getLongFromOptions() / getMax()));
    }

    public double getDoubleFromOptions() {
	try {
	    return ((Double) mod.getSetting(getId())).doubleValue();
	} catch (Exception exception) {
	    return 1.0D;
	}
    }

    public float getFloatFromOptions() {
	try {
	    return ((Float) mod.getSetting(getId())).floatValue();
	} catch (Exception exception) {
	    return 1.0F;
	}
    }

    public int getIntegerFromOptions() {
	try {
	    return ((Integer) mod.getSetting(getId())).intValue();
	} catch (Exception exception) {
	    return 1;
	}
    }

    public long getLongFromOptions() {
	try {
	    return ((Long) mod.getSetting(getId())).longValue();
	} catch (Exception exception) {
	    return 1L;
	}
    }

    public void setAmtInOptions(final Object obj) {
	try {
	    if (mod.getSetting(getId()) instanceof Double)
		mod.registerSetting(getId(), obj, displayString, max, false,
			true, false);
	    else if (mod.getSetting(getId()) instanceof Float)
		mod.registerSetting(getId(),
			Float.valueOf(((Double) obj).floatValue()),
			displayString, max, false, true, false);
	    else if (mod.getSetting(getId()) instanceof Integer)
		mod.registerSetting(getId(),
			Integer.valueOf(((Double) obj).intValue()),
			displayString, max, false, true, false);
	    else if (mod.getSetting(getId()) instanceof Long)
		mod.registerSetting(getId(),
			Long.valueOf(((Double) obj).longValue()),
			displayString, max, false, true, false);
	} catch (Exception e) {
	    e.printStackTrace();
	    return;
	}
    }

    public double getSliderValue() {
	return sliderValue;
    }

    public void setSliderValue(final float sliderValue) {
	this.sliderValue = sliderValue;
    }

    public boolean isDragging() {
	return dragging;
    }

    public void setDragging(final boolean dragging) {
	this.dragging = dragging;
    }

    public Mod getMod() {
	return mod;
    }

    public void setMod(final Mod mod) {
	this.mod = mod;
    }

    public double getMax() {
	return max;
    }

    public void setMax(final double max) {
	this.max = max;
    }

    public Minecraft getMinecraft() {
	return mc;
    }

    public int getId() {
	return id;
    }

    public void setId(final int id) {
	this.id = id;
    }

    public String getValue() {
	return value;
    }

    public void setValue(final String value) {
	this.value = value;
    }
}