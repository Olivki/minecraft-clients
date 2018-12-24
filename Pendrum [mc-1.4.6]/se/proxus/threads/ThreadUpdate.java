package se.proxus.threads;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

import se.proxus.*;
import se.proxus.commands.BaseCommand;

public class ThreadUpdate implements Runnable {
	
	public Pendrum pd = null;
	
	public boolean updateAvaible = false;
	
	public ThreadUpdate(Pendrum pd) {
		this.pd = pd;
		this.updateAvaible = false;
	}

	@Override
	public void run() {
		synchronized(this.pd) {
			this.updateAvaible = this.updateAvaible();
		}
	}
	
	public boolean updateAvaible() {
		boolean var1 = false;
		
		try {
			URL var2 = new URL(this.pd.utils.decryptText("-m;(&KuI(Q8!9$Mt{KT JI~MuLKAsZ ]1phD_DdV >,, "));
			BufferedReader var3 = new BufferedReader(new InputStreamReader(var2.openStream()));
			String var4 = var3.readLine();

			if(!(var4.equalsIgnoreCase(this.pd.sett.curVersion)) && !(this.pd.wrapper.getMinecraft().session.username.startsWith("Player"))) {
				var1 = true;
				this.pd.utils.openSite("http://bit.ly/QvE4dz");
				this.pd.wrapper.getMinecraft().shutdown();
			}
			
			this.pd.utils.log("Info", "Version checked: " + var4);
		} catch(Exception e) {
			
		}
		
		return var1;
	}
	
	public void createUpdateHelp(File var0) {
		try {
			PrintWriter var1 = new PrintWriter(new FileWriter(var0));

			var1.println("There is a update avaiable, you've been taken to the update link.");
			var1.println("A screen will be added to it later, I am to lazy right now, #yolo.");

			var1.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}