package se.proxus.mods;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import se.proxus.*;

import net.minecraft.client.Minecraft;

public class ModConfig {

	public BaseMod mod;

	public Pendrum pe;

	public File config;

	public PrintWriter configWriter;

	public BufferedReader configReader;

	public ModConfig(BaseMod mod, Pendrum pe) {
		this.config = new File(Minecraft.getMinecraftDir() + "/Pendrum/Mods/", mod.getName() + ".cfg");
		this.mod = mod;
		this.pe = pe;
	}

	public void saveConfig() {
		try {
			this.setupWriter();

			/** Kony 2448 **/
			this.configWriter.println("Name: " + this.mod.getName());
			this.configWriter.println("Description: " + this.mod.getInfo().getDescription());
			this.configWriter.println("Type: " + this.mod.getType().getName());
			this.configWriter.println("Key: " + this.mod.getInfo().getKey());
			this.configWriter.println("State: " + this.mod.getState());

			Set var0 = (Set)this.mod.options.entrySet();
			Iterator var1 = var0.iterator();

			while(var1.hasNext()) {
				Map.Entry var2 = (Map.Entry)var1.next();

				this.configWriter.println(var2.getKey() + ": " + this.pe.utils.setFixedObject(var2.getValue()));
			}

			this.configWriter.close();
		} catch(Exception var0) {
			var0.printStackTrace();
		}
	}


	public void loadConfig() {
		try {			
			if(this.config.exists()) {
				this.setupReader();
			}
			
			for(String var0 = ""; (var0 = this.configReader.readLine()) != null;) {
				try {
					String[] var1 = var0.split(": ");
					
					Set var2 = (Set)this.mod.options.entrySet();
					Iterator var3 = var2.iterator();

					while(var3.hasNext()) {
						Map.Entry var4 = (Map.Entry)var3.next();
						
						if(var1[0].equalsIgnoreCase(((String)var4.getKey()))) {
							this.mod.setOption(var1[0], this.pe.utils.getFixedObject(var1[1]), false);
						}
					} if(var1[0].equalsIgnoreCase("Key")) {
						this.mod.getInfo().setKey(var1[1].toUpperCase(), false);
					} if(var1[0].equalsIgnoreCase("State")) {
						this.mod.setState(Boolean.parseBoolean(var1[1]), false);
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} catch(Exception var0) {
			var0.printStackTrace();
		}
	}


	public void setupWriter() {
		try {
			this.configWriter = new PrintWriter(new FileWriter(this.config));
		} catch(IOException var0) {
			var0.printStackTrace();
		}
	}

	public void setupReader() {
		try {
			this.configReader = new BufferedReader(new FileReader(this.config));
		} catch(IOException var0) {
			var0.printStackTrace();
		}
	}
}