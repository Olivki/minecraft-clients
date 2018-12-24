package se.proxus.commands;

public class m_Font extends Base_Command {

	@Override
	public void runCommand(String[] var1, String var2) {
		try {
			if(!(Integer.parseInt(var1[0]) > 3) && !(Integer.parseInt(var1[0]) < 1)) {
				this.vars.fontMode = Integer.parseInt(var1[0]);
			} else {
				addChat("The mode can't be [greater/lower] then [0/3].");
				addSyntax(getSyntax());
			}
		} catch (Exception e) {
			addSyntax(getSyntax());
		}
	}

	@Override
	public String getSyntax() {
		return "font [1/3]";
	}

	@Override
	public String getUsage() {
		return "Used to change the fonts mode.";
	}

	@Override
	public String getName(boolean var1) {
		return var1 ? "fo" : "font";
	}
}