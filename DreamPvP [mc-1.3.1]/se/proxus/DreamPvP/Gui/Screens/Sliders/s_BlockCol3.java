package se.proxus.DreamPvP.Gui.Screens.Sliders;

import java.text.DecimalFormat;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;


public class s_BlockCol3 extends Base_Slider {

    public s_BlockCol3(int i, int j, int k, float f) {
        super(i, j, k, f);
    	sliderValue = f;
    }

    public s_BlockCol3(int i, int j, int k, int l, int i1, float f) {
        super(i, j, k, l, i1, f);
    }

    protected void onSliderValueChanged() {
    	DecimalFormat df = new DecimalFormat();
    	df.setMinimumFractionDigits(1);
    	df.setMaximumFractionDigits(1);
    	
    	mc.dp.settings.bC3 = sliderValue * 1F;
        displayString = "Block ESP blue : " + df.format(mc.dp.settings.bC3);
        mc.dp.files.saveSettings();
    }
}
