package ab.Bytes.Commands;

import java.util.ArrayList;

import net.minecraft.src.*;

import ab.Bytes.Utils.*;

public class c_Waypoint extends Base_Cmd {

	public c_Waypoint() {
		super(".waypoint");
	}

	@Override
	public void command(String[] s) {
		try {
			EntityPlayerSP tp = bs.mc.thePlayer;
			ArrayList I1 = bs.baserenderlist.waypoint.waypointArray;
			String s1 = s[0];
			double s2 = Double.parseDouble(s[1]);
			double s3 = Double.parseDouble(s[2]);
			double s4 = Double.parseDouble(s[3]);
			
			if(s1.equalsIgnoreCase("add")) {
				I1.add(new Waypoint("Herp", tp.posX, tp.posY, tp.posZ, s2, s3, s4));
				bs.utils.addChat("fgot");
			}
					
		} catch (Exception e) {
			e.printStackTrace();
			bs.utils.addChat("Syntax : .waypoint [add / del] [Red] [Green] [Blue]");
		}
	}

	@Override
	public String getUsage() {
		return ".waypoint [add / del] [Red] [Green] [Blue]";
	}

	@Override
	public String getHelp() {
		return "Used to add or delete waypoints.";
	}
}