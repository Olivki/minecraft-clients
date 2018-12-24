package ab.Bytes.Commands;

import java.util.ArrayList;

import ab.Bytes.Mods.*;

import net.minecraft.src.*;

public class c_xRay extends Base_Cmd {

	public c_xRay() {
		super(".xray");
	}

	@Override
	public void command(String s[]) {
		try {
			ArrayList I1 = bs.settings.blockList;
			String I2 = s[0];
			int I3 = Integer.parseInt(s[1]);
			m_xRay xray = new m_xRay();
			
			if(I2.equalsIgnoreCase("add")) {
				bs.utils.addBlock(I1, I3);
			}
			
			if(I2.equalsIgnoreCase("del")) {
				bs.utils.removeBlock(I1, I3);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			bs.utils.addChat("Syntax : .xray [add / del] [id]");
		}
	}

	@Override
	public String getUsage() {
		return ".xray [add / del] [id]";
	}

	@Override
	public String getHelp() {
		return "Adds / deletes block to / from the block list.";
	}
}