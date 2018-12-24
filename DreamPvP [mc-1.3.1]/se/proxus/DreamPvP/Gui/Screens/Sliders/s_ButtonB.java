package se.proxus.DreamPvP.Gui.Screens.Sliders;

import java.text.DecimalFormat;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;


public class s_ButtonB extends Base_Slider {

    public s_ButtonB(int i, int j, int k, float f) {
        super(i, j, k, f);
    	sliderValue = f;
    }

    public s_ButtonB(int i, int j, int k, int l, int i1, float f) {
        super(i, j, k, l, i1, f);
    }

    protected void onSliderValueChanged() {
    	DecimalFormat df = new DecimalFormat();
    	df.setMinimumFractionDigits(1);
    	df.setMaximumFractionDigits(1);
    	
    	mc.dp.settings.bB = sliderValue * 1F;
        displayString = "Button blue : " + df.format(mc.dp.settings.bB);
    }
}
