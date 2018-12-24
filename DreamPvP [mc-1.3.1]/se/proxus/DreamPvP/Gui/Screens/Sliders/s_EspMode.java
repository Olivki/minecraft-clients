package se.proxus.DreamPvP.Gui.Screens.Sliders;

import java.text.DecimalFormat;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;


public class s_EspMode extends Base_Slider {

    public s_EspMode(int i, int j, int k, float f) {
        super(i, j, k, f);
    	sliderValue = f;
    }

    public s_EspMode(int i, int j, int k, int l, int i1, float f) {
        super(i, j, k, l, i1, f);
    }

    protected void onSliderValueChanged() {	
    	mc.dp.settings.espMode = (int)sliderValue * 1;
        displayString = "ESP Mode : " + mc.dp.utils.getESPModeNames(mc.dp.settings.espMode);
        mc.dp.files.saveSettings();
    }
}
