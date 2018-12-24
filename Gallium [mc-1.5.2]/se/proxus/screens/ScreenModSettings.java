package se.proxus.screens;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;

import org.lwjgl.input.Keyboard;

import se.proxus.mods.Mod;
import se.proxus.mods.ModValue;

public class ScreenModSettings extends GuiScreen {

    protected GuiScreen parentScreen;
    private final static List<ModValue> LOADED_SETTINGS = new LinkedList<ModValue>();
    private final static int SETTINGS_PER_PAGE = 4;
    private static int currentPage = 1;
    private static int amountOfPages = 0;
    private GuiButton prevButton;
    private GuiButton nextButton;

    public ScreenModSettings(final GuiScreen parentScreen) {
	setParentScreen(parentScreen);
    }

    @Override
    public void initGui() {
	getLoadedSettings().clear();
	for (Mod mod : mc.dp.getMods().getLoadedMods()) {
	    Set set = mod.getLoadedSettings().entrySet();
	    Iterator iterator = set.iterator();
	    while (iterator.hasNext()) {
		final Map.Entry entry = (Map.Entry) iterator.next();
		final ModValue value = (ModValue) entry.getValue();
		if (value.getName().equalsIgnoreCase("N/A")
			|| value.getName().replace(mod.getName() + " ", "")
				.equalsIgnoreCase("N/A")
			|| value.getName().contains("N/A"))
		    continue;
		if (value.getValue() instanceof Boolean)
		    getLoadedSettings().add(value);
	    }
	}
	byte var1 = -16;
	ModValue[] settings = getRegisteredSettings();
	sortSettings(settings);
	setAmountOfPages(settings.length / getSettingsPerPage()
		+ (settings.length % getSettingsPerPage() > 0 ? 1 : 0));
	for (int i = 0; i < getSettingsPerPage(); i++) {
	    if (i + (getCurrentPage() - 1) * getSettingsPerPage() + 1 > settings.length)
		break;
	    ModValue value = settings[i + (getCurrentPage() - 1)
		    * getSettingsPerPage()];
	    getButtonList()
		    .add(new GuiButton(i, width / 2 - 100, height / 4 + 24
			    + var1 + i * 24, value.getName() + ": "
			    + value.getValue()));
	}
	this.getButtonList().add(
		prevButton = new GuiButton(98, this.width / 2 - 100,
			this.height / 4 + 120 + var1, 98, 20, "<<"));
	prevButton.enabled = getCurrentPage() != 1;
	this.getButtonList().add(
		nextButton = new GuiButton(99, this.width / 2 + 2, this.height
			/ 4 + 120 + var1, 98, 20, ">>"));
	nextButton.enabled = getCurrentPage() != getAmountOfPages()
		&& getAmountOfPages() > 1;
    }

    @Override
    public void actionPerformed(final GuiButton button) {
	ModValue[] settings = getRegisteredSettings();
	sortSettings(settings);
	for (int i = 0; i < getSettingsPerPage(); i++) {
	    if (i + (getCurrentPage() - 1) * getSettingsPerPage() + 1 > settings.length)
		break;
	    ModValue setting = settings[i + (getCurrentPage() - 1)
		    * getSettingsPerPage()];
	    if (i == button.id) {
		setting.getMod()
			.registerSetting(
				setting.getId(),
				!(Boolean) setting.getMod().getSetting(
					setting.getId()), setting.getName(),
				0.0D, false, true, true);
		button.displayString = setting.getName() + ": "
			+ setting.getValue();
		setting.getMod().saveSettings();
		getButtonList().clear();
		initGui();
	    }
	}
	switch (button.id) {
	case 98:
	    setCurrentPage(getCurrentPage() - 1);
	    getButtonList().clear();
	    initGui();
	    break;

	case 99:
	    setCurrentPage(getCurrentPage() + 1);
	    getButtonList().clear();
	    initGui();
	    break;
	}
    }

    @Override
    public void keyTyped(final char keyChar, final int keyID) {
	try {
	    if (Keyboard.getKeyName(keyID).equalsIgnoreCase("ESCAPE"))
		mc.displayGuiScreen(parentScreen);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public ModValue[] sortSettings(final ModValue[] settings) {
	Arrays.sort(settings, new Comparator<ModValue>() {
	    @Override
	    public int compare(final ModValue setting1, final ModValue setting2) {
		String modName1 = setting1.getName();
		String modName2 = setting2.getName();
		return modName1.compareTo(modName2);
	    }
	});
	return settings;
    }

    @Override
    public void drawScreen(final int x, final int y, final float ticks) {
	drawDefaultBackground();
	drawCenteredString(fontRenderer, "Mod Settings (" + getCurrentPage()
		+ "/" + getAmountOfPages() + ")", width / 2, 40, 0xFFFFFFFF);
	super.drawScreen(x, y, ticks);
    }

    public GuiScreen getParentScreen() {
	return parentScreen;
    }

    public void setParentScreen(final GuiScreen parentScreen) {
	this.parentScreen = parentScreen;
    }

    public static List<ModValue> getLoadedSettings() {
	return LOADED_SETTINGS;
    }

    public static int getCurrentPage() {
	return currentPage;
    }

    public static void setCurrentPage(final int currentPage) {
	ScreenModSettings.currentPage = currentPage;
    }

    public static int getAmountOfPages() {
	return amountOfPages;
    }

    public static void setAmountOfPages(final int amountOfPages) {
	ScreenModSettings.amountOfPages = amountOfPages;
    }

    public static int getSettingsPerPage() {
	return SETTINGS_PER_PAGE;
    }

    public ModValue[] getRegisteredSettings() {
	return getLoadedSettings().toArray(
		new ModValue[getLoadedSettings().size()]);
    }
}