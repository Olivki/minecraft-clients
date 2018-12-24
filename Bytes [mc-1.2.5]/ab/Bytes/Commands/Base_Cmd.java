package ab.Bytes.Commands;

import ab.Bytes.*;

public abstract class Base_Cmd {
	
	public Bytes bs = new Bytes();
	
	protected String cmdName;
	
	public Base_Cmd(String name) {
		cmdName = name;
	}
	
	public abstract void command(String s[]);
	
	public abstract String getUsage();
	
	public abstract String getHelp();
	
	public String getName() {
		return cmdName;
	}
}