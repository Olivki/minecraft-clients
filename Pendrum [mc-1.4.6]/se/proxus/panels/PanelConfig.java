package se.proxus.panels;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import net.minecraft.client.Minecraft;

import se.proxus.Pendrum;

public class PanelConfig {

	protected Pendrum pe;

	protected BasePanel panel;

	protected File config;

	protected PrintWriter configWriter;

	protected BufferedReader configReader;

	public PanelConfig(BasePanel panel, Pendrum pe) {
		this.config = new File(Minecraft.getMinecraftDir() + "/Pendrum/Panels/", panel.getName() + ".cfg");
		this.panel = panel;
		this.pe = pe;
	}

	public void saveConfig() {
		try {
			try {
				this.setConfigWriter(new PrintWriter(new FileWriter(this.config)));
			} catch(IOException var0) {
				var0.printStackTrace();
			}

			this.getConfigWriter().println("Name: " + this.getPanel().getName());
			this.getConfigWriter().println("X: " + this.getPanel().getInfo().getX());
			this.getConfigWriter().println("Y: " + this.getPanel().getInfo().getY());
			this.getConfigWriter().println("Width: " + this.getPanel().getInfo().getWidth());
			this.getConfigWriter().println("Height: " + this.getPanel().getInfo().getHeight());
			this.getConfigWriter().println("Open: " + this.getPanel().getInfo().isOpen());
			this.getConfigWriter().println("Pinned: " + this.getPanel().getInfo().isPinned());
			this.getConfigWriter().println("Showing: " + this.getPanel().getPendrum().panels.panelArray.contains(this.getPanel()));
			this.getConfigWriter().println("Index: " + this.getPanel().getIndex(this.getPanel().getName()));

			this.getConfigWriter().close();
		} catch(Exception var0) {
			var0.printStackTrace();
		}
	}

	public void loadConfig() {
		if(this.getConfig().exists()) {
			try {
				try {
					this.setConfigReader(new BufferedReader(new FileReader(this.config)));
				} catch(IOException var0) {
					var0.printStackTrace();
				}

				for(String var0 = ""; (var0 = this.configReader.readLine()) != null;) {
					try {
						String[] var1 = var0.split(": ");
						boolean var2 = false;
						
						if(var1[0].equalsIgnoreCase("X")) {
							this.getPanel().getInfo().setX(Integer.parseInt(var1[1]));
						} if(var1[0].equalsIgnoreCase("Y")) {
							this.getPanel().getInfo().setY(Integer.parseInt(var1[1]));
						} if(var1[0].equalsIgnoreCase("Width")) {
							this.getPanel().getInfo().setWidth(Integer.parseInt(var1[1]));
						} if(var1[0].equalsIgnoreCase("Height")) {
							this.getPanel().getInfo().setHeight(Integer.parseInt(var1[1]));
						} if(var1[0].equalsIgnoreCase("Open")) {
							this.getPanel().getInfo().setOpened(Boolean.parseBoolean(var1[1]));
						} if(var1[0].equalsIgnoreCase("Pinned")) {
							this.getPanel().getInfo().setPinned(Boolean.parseBoolean(var1[1]));
						} if(var1[0].equalsIgnoreCase("Showing")) {
							if(Boolean.parseBoolean(var1[1]) == true) {
								this.getPendrum().panels.addPanel(this.getPanel());
							}
						}
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			} catch(Exception var0) {
				var0.printStackTrace();
			}
		} else {
			this.saveConfig();
		}
	}

	public Pendrum getPendrum() {
		return pe;
	}

	public void setPendrum(Pendrum pe) {
		this.pe = pe;
	}

	public File getConfig() {
		return config;
	}

	public void setConfig(File config) throws IOException {
		this.config = config;
	} 

	public PrintWriter getConfigWriter() {
		return configWriter;
	}

	public void setConfigWriter(PrintWriter configWriter) throws IOException {
		this.configWriter = configWriter;
	}

	public BufferedReader getConfigReader() {
		return configReader;
	}

	public void setConfigReader(BufferedReader configReader) {
		this.configReader = configReader;
	}

	public BasePanel getPanel() {
		return panel;
	}

	public void setPanel(BasePanel panel) {
		this.panel = panel;
	}
}