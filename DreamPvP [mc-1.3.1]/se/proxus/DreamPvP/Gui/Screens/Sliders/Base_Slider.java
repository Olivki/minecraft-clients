package se.proxus.DreamPvP.Gui.Screens.Sliders;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;
import org.lwjgl.opengl.GL11;

import se.proxus.DreamPvP.DreamPvP;

public class Base_Slider extends GuiButton {
	
    public float sliderValue;
    public boolean dragging;
    public Minecraft mc = Minecraft.theMinecraft;

    public Base_Slider(int i, int j, int k, float f) {
        this(i, j, k, 200, 20, f);
    }

    public Base_Slider(int i, int j, int k, int l, int i1, float f) {
        super(i, j, k, l, i1, "");
        dragging = false;
        sliderValue = f;
        onSliderValueChanged();
    }

    protected int getHoverState(boolean flag) {
        return 0;
    }

    protected void mouseDragged(Minecraft minecraft, int i, int j) {
        if(!drawButton) {
            return;
        }
        
        if(dragging) {
            sliderValue = (float)(i - (xPosition + 4)) / (float)(width - 8);
            
            if(sliderValue < 0.0F) {
                sliderValue = 0.0F;
            }
            
            if(sliderValue > 1.0F) {
                sliderValue = 1.0F;
            }
            
            onSliderValueChanged();
        }
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		if(mc.dp.settings.buttonMode == 1) {
			this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (float)(this.width - 8)), this.yPosition, 0, 66, 4, 20);
			this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (float)(this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
		} else if(mc.dp.settings.buttonMode == 2) {
			this.drawRect(this.xPosition + (int)(this.sliderValue * (float)(this.width - 8)), this.yPosition, this.xPosition + (int)(this.sliderValue * (float)(this.width - 8)) + 8, this.yPosition + this.height, 0xFF000000);
		}
    }

    public boolean mousePressed(Minecraft minecraft, int i, int j) {
        if(super.mousePressed(minecraft, i, j)) {
            sliderValue = (float)(i - (xPosition + 4)) / (float)(width - 8);
            
            if(sliderValue < 0.0F) {
                sliderValue = 0.0F;
            }
            
            if(sliderValue > 1.0F) {
                sliderValue = 1.0F;
            }
            
            onSliderValueChanged();
            dragging = true;
            return true;
        } else {
            return false;
        }
    }

    public void mouseReleased(int i, int j) {
        dragging = false;
    }

    protected void onSliderValueChanged() {mc.dp.files.saveSettings();}
}
