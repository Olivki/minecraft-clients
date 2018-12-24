package se.proxus.DreamPvP.Commands;

import se.proxus.DreamPvP.*;

public abstract class Base_Cmd {
	
	protected String cmd;
	
	public Base_Cmd(String cmd) {
		this.cmd = cmd;
	}
	
	protected DreamPvP dp = new DreamPvP();
	
	public abstract void onCommand(String[] s, String s1);
	
	public abstract String getSyntax();
	
	public abstract String getUsage();
	
	public String getCmd() {
		return cmd;
	}
}