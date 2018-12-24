package se.proxus.DreamPvP.Gui.Screens;

import se.proxus.DreamPvP.DreamPvP;
import net.minecraft.src.*;

public class WaypointList extends GuiScreen {

	private WaypointListSlot mSlot;
	
	public boolean deletePressed = false;

	@Override
	public void initGui() {
		controlList.add(new GuiButton(0, width / 2 - 100, height - 48, 98, 20, "Add waypoint."));
		controlList.add(new GuiButton(1, width / 2 + 2, height - 48, 98, 20, "Delete waypoint."));
		controlList.add(new GuiButton(2, width / 2 - 100, height - 26, "Done."));

		mSlot = new WaypointListSlot(this);
		mSlot.registerScrollButtons(controlList, 7, 8);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if(button.enabled) {
			if(button.id == 0) {
				DreamPvP.mc.displayGuiScreen(new WaypointAdd());
			} if(button.id == 1) {
				String s = mc.dp.settings.waypointArray.get(mSlot.getSelected()).name;

				if (s != null) {
					deletePressed = true;
					StringTranslate stringtranslate = StringTranslate.getInstance();
					String s1 = "Are you sure you want to delete this waypoint?";
					String s2 = "\"" + s + "\" will be lost forever! (A long time!)";
					String s3 = "Delete.";
					String s4 = "Cancel.";
					GuiYesNo guiyesno = new GuiYesNo(this, s1, s2, s3, s4, mSlot.getSelected());
					mc.displayGuiScreen(guiyesno);
					mc.dp.files.saveWaypoints();
				}
			} if(button.id == 2) {
				DreamPvP.mc.displayGuiScreen(new Main());
				mc.dp.files.saveWaypoints();
			} else {
				mSlot.actionPerformed(button);
			}
		}
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		mSlot.drawScreen(I1, I2, I3);

		drawCenteredString(fontRenderer, "Waypoint list.", width / 2, 10, 0xFFFFFFFF);
		super.drawScreen(I1, I2, I3);
	}
	
	@Override
	public void confirmClicked(boolean flag, int i1) {
		if(deletePressed) {
			deletePressed = false;
			
            if(flag) {
                mc.dp.settings.waypointArray.remove(i1);
				mc.dp.files.saveWaypoints();
            }

            mc.displayGuiScreen(this);
		}
	}
}