package se.proxus.DreamPvP.Commands;

public class c_ForceOP extends Base_Cmd {

	public c_ForceOP() {
		super(".forceop");
	}

	@Override
	public void onCommand(String[] s, String s1) {
		dp.utils.addChat("Nope.avi, force-op is not possible, don't trust any .exe or .jar files from people who claim they have a force-op.");
	}

	@Override
	public String getSyntax() {
		return "Syntax : .forceop";
	}

	@Override
	public String getUsage() {
		return "Used to give yourself op on servers!";
	}
}