package se.proxus.screens;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;

import org.lwjgl.input.Keyboard;

import se.proxus.Gallium;
import se.proxus.tools.ArrayHelper;

public class ScreenMain extends GuiScreen {

    protected GuiScreen parentScreen;
    private Gallium client;
    private final static List<ArrayHelper<String, GuiScreen>> LOADED_SCREENS = new LinkedList<ArrayHelper<String, GuiScreen>>();
    private final static int SCREENS_PER_PAGE = 4;
    private static int currentPage = 1;
    private static int amountOfPages = 0;
    private GuiButton prevButton;
    private GuiButton nextButton;

    public ScreenMain(final Gallium client, final GuiScreen parentScreen) {
	setClient(client);
	setParentScreen(parentScreen);
    }

    public void init() {
	addScreen(new ArrayHelper("Mod Manager", new ScreenModManager(this)));
	addScreen(new ArrayHelper("Friend Manager", new ScreenFriendManager(
		this)));
	addScreen(new ArrayHelper("Mod Values", new ScreenModValues(this)));
	addScreen(new ArrayHelper("Mod Settings", new ScreenModSettings(this)));
    }

    @Override
    public void initGui() {
	byte var1 = -16;
	ArrayHelper[] pages = getRegisteredScreens();
	sortPages(pages);
	setAmountOfPages(pages.length / getScreensPerPage()
		+ (pages.length % getScreensPerPage() > 0 ? 1 : 0));
	for (int i = 0; i < getScreensPerPage(); i++) {
	    if (i + (getCurrentPage() - 1) * getScreensPerPage() + 1 > pages.length)
		break;
	    ArrayHelper page = pages[i + (getCurrentPage() - 1)
		    * getScreensPerPage()];
	    getButtonList().add(
		    new GuiButton(i, width / 2 - 100, height / 4 + 24 + var1
			    + i * 24, (String) page.getKey()));
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
    public void updateScreen() {
	getButtonList().clear();
	byte var1 = -16;
	ArrayHelper[] pages = getRegisteredScreens();
	sortPages(pages);
	setAmountOfPages(pages.length / getScreensPerPage()
		+ (pages.length % getScreensPerPage() > 0 ? 1 : 0));
	for (int i = 0; i < getScreensPerPage(); i++) {
	    if (i + (getCurrentPage() - 1) * getScreensPerPage() + 1 > pages.length)
		break;
	    ArrayHelper page = pages[i + (getCurrentPage() - 1)
		    * getScreensPerPage()];
	    getButtonList().add(
		    new GuiButton(i, width / 2 - 100, height / 4 + 24 + var1
			    + i * 24, (String) page.getKey()));
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
	ArrayHelper[] pages = getRegisteredScreens();
	sortPages(pages);
	for (int i = 0; i < getScreensPerPage(); i++) {
	    if (i + (getCurrentPage() - 1) * getScreensPerPage() + 1 > pages.length)
		break;
	    ArrayHelper page = pages[i + (getCurrentPage() - 1)
		    * getScreensPerPage()];
	    if (i == button.id)
		mc.displayGuiScreen((GuiScreen) page.getValue());
	}
	switch (button.id) {
	case 98:
	    setCurrentPage(getCurrentPage() - 1);
	    break;

	case 99:
	    setCurrentPage(getCurrentPage() + 1);
	    break;
	}
    }

    @Override
    public void drawScreen(final int I1, final int I2, final float I3) {
	drawDefaultBackground();

	drawCenteredString(fontRenderer, "Gallium Main", width / 2, 40,
		0xFFFFFFFF);
	drawCenteredString(fontRenderer, "Gui Screens (" + getCurrentPage()
		+ "/" + getAmountOfPages() + ")", width / 2, 50, 0xFFFFFFFF);
	super.drawScreen(I1, I2, I3);
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

    public ArrayHelper addScreen(final ArrayHelper array) {
	if (!getLoadedScreens().contains(array)) {
	    getClient().getLogger().log(Level.INFO,
		    "Loaded the screen '" + array.getKey() + "'.");
	    getLoadedScreens().add(array);
	}
	return array;
    }

    public static List<ArrayHelper<String, GuiScreen>> getLoadedScreens() {
	return LOADED_SCREENS;
    }

    public ArrayHelper[] getRegisteredScreens() {
	return getLoadedScreens().toArray(
		new ArrayHelper[getLoadedScreens().size()]);
    }

    public ArrayHelper[] sortPages(final ArrayHelper[] pages) {
	Arrays.sort(pages, new Comparator<ArrayHelper>() {
	    @Override
	    public int compare(final ArrayHelper page1, final ArrayHelper page2) {
		String pageName1 = (String) page1.getKey();
		String pageName2 = (String) page2.getKey();
		return pageName1.compareTo(pageName2);
	    }
	});
	return pages;
    }

    public static int getScreensPerPage() {
	return SCREENS_PER_PAGE;
    }

    public static int getCurrentPage() {
	return currentPage;
    }

    public static int getAmountOfPages() {
	return amountOfPages;
    }

    public static void setAmountOfPages(final int amountOfPages) {
	ScreenMain.amountOfPages = amountOfPages;
    }

    public static void setCurrentPage(final int currentPage) {
	ScreenMain.currentPage = currentPage;
    }

    public Gallium getClient() {
	return client;
    }

    public void setClient(final Gallium client) {
	this.client = client;
    }

    public GuiScreen getParentScreen() {
	return parentScreen;
    }

    public void setParentScreen(final GuiScreen parentScreen) {
	this.parentScreen = parentScreen;
    }
}