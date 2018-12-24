package se.proxus.DreamPvP.Gui.Screens.Sliders;

import java.text.DecimalFormat;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;


public class s_MineSpeed extends Base_Slider {

    public s_MineSpeed(int i, int j, int k, float f) {
        super(i, j, k, f);
    	sliderValue = f;
    }

    public s_MineSpeed(int i, int j, int k, int l, int i1, float f) {
        super(i, j, k, l, i1, f);
    }

    protected void onSliderValueChanged() {
    	DecimalFormat df = new DecimalFormat();
    	df.setMinimumFractionDigits(1);
    	df.setMaximumFractionDigits(1);
    	
    	mc.dp.bModList.miner.mineSpeed = sliderValue * 1.0F;
        displayString = "Minespeed : " + df.format(mc.dp.bModList.miner.mineSpeed);
        mc.dp.files.saveSettings();
    }
}
