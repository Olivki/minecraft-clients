package se.proxus.panels.utils;

import net.minecraft.src.Tessellator;

import org.lwjgl.input.Mouse;

import se.proxus.hacks.*;
import se.proxus.panels.Base_Panels;
import se.proxus.panels.Base_Util;

public class u_Slider extends Base_Util {

	public Base_Hack
	hack = null;

	public Base_Options
	options = null;

	public double
	maxAmount = 0,
	curAmount = 0;
	
	public boolean
	dragging = false;
	
	public String
	value = "";

	public u_Slider(String text, Base_Hack hack, Base_Options options, double maxAmount, int x2) {
		super(text, 0, 0, x2, 11, 16, "");
		this.hack = hack;
		this.options = options;
		this.maxAmount = maxAmount;
	}

	@Override
	public void draw(int var1, int var2, int var3) {	
		if(hack.getOption(options) instanceof Double) {
			this.value = "" + Math.floor(this.getDoubleFromOptions() * 10D) / 10D;
		} else if(hack.getOption(options) instanceof Float) {
			this.value = "" + Math.floor(this.getFloatFromOptions() * 10F) / 10D;
		} else if(hack.getOption(options) instanceof Integer) {
			this.value = "" + Math.floor(this.getIntegerFromOptions() * 10) / 10D;
		} else if(hack.getOption(options) instanceof Long) {
			this.value = "" + Math.floor(this.getLongFromOptions() * 10L) / 10D;
		}

		if(hack.getOption(options) instanceof Double) {
			this.curAmount = (float)(this.getDoubleFromOptions() / this.maxAmount);
		} else if(hack.getOption(options) instanceof Float) {
			this.curAmount = (float)((double)this.getFloatFromOptions() / this.maxAmount);
		} else if(hack.getOption(options) instanceof Integer) {
			this.curAmount = (float)((double)this.getIntegerFromOptions() / this.maxAmount);
		} else if(hack.getOption(options) instanceof Long) {
			this.curAmount = (float)((double)this.getLongFromOptions() / this.maxAmount);
		}
		
		if(this.mouseInRec(var1, var2) && this.mc.currentScreen instanceof Base_Panels) {
			this.drawRect((this.x + (int)(this.curAmount * (float)this.x2)) - 1, this.y, (this.x + (int)(this.curAmount * (float)this.x2) + 1), this.y + this.y2, 0x20000000);
		}
		
		this.drawRect(this.x, this.y + 4, this.x + this.x2, this.y + this.y2 - 4, 0x40000000);
		this.drawRect((this.x + (int)(this.curAmount * (float)this.x2)) - 1, this.y, (this.x + (int)(this.curAmount * (float)this.x2) + 1), this.y + this.y2, this.dragging ? 0x60007143 : 0x60960028);
		this.drawCenteredString(this.mc.fontRenderer, this.text + " : " + this.value, this.x + this.x2 / 2, this.y + 1.5F, 0xFFFFFFFF, false);
        
		this.mouseDragged(var1, var2);
	}

	@Override
	public void mouseClicked(int var1, int var2) {
		if(this.mouseInRec(var1, var2)) {
			this.utils.playSound();
			this.curAmount = (float)(((double)var1 - (double)this.x) / (double)this.x2);
			this.setAmtInOptions(Double.valueOf((double)this.curAmount * this.maxAmount));
			this.dragging = true;
			this.files.saveConfig();
		}
	}

	@Override
	public boolean mouseInRec(int var1, int var2) {
		return var1 >= this.x && var2 >= this.y && var1 <= this.x + this.x2 && var2 <= this.y + this.y2;
	}

	public void mouseDragged(int var1, int var2) {
		if(this.dragging && Mouse.isButtonDown(0)) {
			this.curAmount = (float)(((double)var1 - (double)this.x) / (double)this.x2);

			if(var1 < this.x) {
				this.curAmount = 0.0F;
			}  if(var1 > this.x + this.x2) {
				this.curAmount = 1.0F;
			}
			
			this.setAmtInOptions(Double.valueOf((double)this.curAmount * this.maxAmount));
			this.files.saveConfig();
		} else {
			this.dragging = false;
		}
	}

	public double getDoubleFromOptions() {
		try {
			return ((Double)hack.getOption(options)).doubleValue();
		} catch (Exception exception) {
			return 1.0D;
		}
	}

	public float getFloatFromOptions() {
		try {
			return ((Float)hack.getOption(options)).floatValue();
		} catch (Exception exception) {
			return 1.0F;
		}
	}

	public int getIntegerFromOptions() {
		try {
			return ((Integer)hack.getOption(options)).intValue();
		} catch (Exception exception) {
			return 1;
		}
	}

	public long getLongFromOptions() {
		try {
			return ((Long)hack.getOption(options)).longValue();
		} catch (Exception exception) {
			return 1L;
		}
	}

	public void setAmtInOptions(Object obj) {
		try {
			if(hack.getOption(options) instanceof Double) {
				hack.setOption(options, (Double)obj);
			} else if(hack.getOption(options) instanceof Float) {
				hack.setOption(options, Float.valueOf(((Double)obj).floatValue()));
			} else if(hack.getOption(options) instanceof Integer) {
				hack.setOption(options, Integer.valueOf(((Double)obj).intValue()));
			} else if(hack.getOption(options) instanceof Long) {
				hack.setOption(options, Long.valueOf(((Double)obj).longValue()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
    public void updateRenders() {
        int var1 = (int)mc.thePlayer.posX, var2 = (int)mc.thePlayer.posY, var3 = (int)mc.thePlayer.posZ;
        mc.renderGlobal.markBlockRangeNeedsUpdate(var1 - 200, var2 - 200, var3 - 200, var1 + 200, var2 + 200, var3 + 200);
    }
}