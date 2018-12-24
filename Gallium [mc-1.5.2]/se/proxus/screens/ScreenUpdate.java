package se.proxus.screens;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiScreen;
import se.proxus.tools.Colours;

public class ScreenUpdate extends GuiScreen {

    public GuiButton irc;

    @Override
    public void initGui() {
	mc.dp.init(1);
	getButtonList().add(
		new GuiButton(0, width / 2 - 100, height / 4 + 24,
			"Download newest .jar"));
	if (mc.session.username.equalsIgnoreCase("Olivki"))
	    getButtonList().add(
		    new GuiButton(1, width / 2 - 100, height / 4 + 46, "Skip"));
    }

    @Override
    public void updateScreen() {
	getButtonList().clear();
	getButtonList().add(
		new GuiButton(0, width / 2 - 100, height / 2 - 11,
			Colours.BRIGHT_GREEN + "*" + Colours.RESET
				+ " Download The Latest Update "
				+ Colours.BRIGHT_GREEN + "*" + Colours.RESET));
	if (mc.session.username.equalsIgnoreCase("Olivki"))
	    getButtonList().add(
		    new GuiButton(1, width / 2 - 100, height / 2 + 11,
			    Colours.RED + "*" + Colours.RESET + " Skip "
				    + Colours.RED + "*" + Colours.RESET));
    }

    @Override
    public void actionPerformed(final GuiButton button) {
	switch (button.id) {
	case 0:
	    try {
		Desktop.getDesktop().browse(new URI("http://adf.ly/BqxFh"));
	    } catch (IOException e) {
		e.printStackTrace();
	    } catch (URISyntaxException e) {
		e.printStackTrace();
	    }
	    try {
		Desktop.getDesktop().browse(new URI("http://bit.ly/P0o3Yg"));
	    } catch (IOException e) {
		e.printStackTrace();
	    } catch (URISyntaxException e) {
		e.printStackTrace();
	    }
	    mc.shutdown();
	    break;

	case 1:
	    mc.displayGuiScreen(new GuiMainMenu());
	    break;
	}
    }

    @Override
    public void drawScreen(final int I1, final int I2, final float I3) {
	drawDefaultBackground();
	long versionsMissing = Math.round((mc.dp.getVersion() - mc.dp
		.getNewVersion()) * 10);
	drawCenteredString(fontRenderer,
		("You're " + Colours.CLIENT_COLOUR + versionsMissing + Colours.RESET
			+ " version(s) " + (versionsMissing > 0 ? "ahead."
			: "behind.")).replace("-", ""), width / 2,
		height / 2 - 31, 0xFFFFFFFF);
	drawCenteredString(fontRenderer, "Newest version: " + Colours.CLIENT_COLOUR
		+ mc.dp.getNewVersion() + Colours.RESET, width / 2,
		height / 2 - 41, 0xFFFFFFFF);
	drawCenteredString(fontRenderer, "There's a new update avaible for "
		+ Colours.CLIENT_COLOUR + "Gallium" + Colours.RESET + "!", width / 2,
		height / 2 - 61, 0xFFFFFFFF);
	super.drawScreen(I1, I2, I3);
    }
}