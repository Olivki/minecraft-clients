package se.proxus.threads;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import se.proxus.*;

public class ThreadKey implements Runnable {
	
	public Pendrum pd = null;
	
	public String key = "";
	
	public boolean validKey = false;
	
	public ThreadKey(Pendrum pd, String key) {
		this.pd = pd;
		this.key = key;
		this.validKey = false;
	}

	@Override
	public void run() {
		synchronized(this.pd) {
			this.validKey = this.validKey(this.key);
		}
	}
	
	public boolean validKey(String var0) {
		boolean var1 = false;
		
		try {
			URL var2 = new URL(this.pd.utils.decryptText("-m;(&KuB#I#&:BHd7mF) k/L@=>9Hbb]G3#G]ofL+M") + this.key);
			BufferedReader var3 = new BufferedReader(new InputStreamReader(var2.openStream()));
			String var4 = var3.readLine();

			var1 = var4.startsWith(this.pd.utils.decryptText(";Z3!P"));
		} catch(Exception e) {
			this.pd.wrapper.getMinecraft().shutdown();
		}
		
		try {
			Thread.sleep(200L);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		return var1;
	}
}