/*Credits to Marshall Eriksen / ECB2 for this.*/

package Comet;

import java.util.ArrayList;

public class vBools implements Runnable {
	public boolean[] HState;
	public ArrayList HA = new ArrayList();

	public vBools(){
		HState = new boolean [255];
	}

	public void run() {

	}	

	public void toggle(String NM){
		getHackID(NM);
		HState[getHackID(NM)] = !HState[getHackID(NM)];
	}

	public int getHackID(String hackname){
		if(HA.contains(hackname)){
			for(int i = 0; i < HA.size(); i++){
				if(HA.get(i) == hackname){
					return i;}}
		}else{
			HA.add(hackname);
			for(int i = 0; i < HA.size(); i++){
				if(HA.get(i) == hackname){
					return i;
				}}}
		return 1;
	}

	public boolean habo(String NM){
		return HState[getHackID(NM)];
	}

}

