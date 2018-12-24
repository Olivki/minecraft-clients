package se.proxus.DreamPvP.Utils;

import java.util.ArrayList;

public class Bools {

	public boolean[] bool = new boolean [256];
	public ArrayList boolList = new ArrayList();

	public void toggle(String name) {
		getBoolean(name);
		bool[getBoolean(name)] =! bool[getBoolean(name)];

		if(!name.equalsIgnoreCase("needsUpdate")) {
			Utils.log("[Boolean] " + name + " : " + getState(name));
		}
	}

	public int getBoolean(String name) {
		if(boolList.contains(name)) {
			for(int I1 = 0; I1 < boolList.size(); I1++) {
				if(boolList.get(I1) == name) {
					return I1;
				}
			}
		} else {
			boolList.add(name);
			for(int I2 = 0; I2 < boolList.size(); I2++) {
				if(boolList.get(I2) == name) {
					return I2;
				}
			}
		}
		return 1;
	}

	public boolean getState(String name) {
		return bool[getBoolean(name)];
	}

	public void setState(String name, boolean bol) {
		bool[getBoolean(name)] = bol;
		Utils.log("[Boolean] " + name + " : " + getState(name));
	}
}