package se.proxus.DreamPvP.Gui.Screens.Sliders;

import java.text.DecimalFormat;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

public class s_FlySpeed extends Base_Slider {

    public s_FlySpeed(int i, int j, int k, float f) {
        super(i, j, k, f);
    	sliderValue = f;
    }

    public s_FlySpeed(int i, int j, int k, int l, int i1, float f) {
        super(i, j, k, l, i1, f);
    }

    protected void onSliderValueChanged() {
    	DecimalFormat df = new DecimalFormat();
    	df.setMinimumFractionDigits(1);
    	df.setMaximumFractionDigits(1);
    	
    	mc.dp.settings.flySpeed = sliderValue * 20.0F;
        displayString = "Fly speed : " + df.format(mc.dp.settings.flySpeed);
        mc.dp.files.saveSettings();
    }
}
