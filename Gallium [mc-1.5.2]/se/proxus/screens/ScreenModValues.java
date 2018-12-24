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

public class ScreenModValues extends GuiScreen {

    protected GuiScreen parentScreen;
    private final static List<ModValue> LOADED_VALUES = new LinkedList<ModValue>();
    private final static int VALUES_PER_PAGE = 4;
    private static int currentPage = 1;
    private static int amountOfPages = 0;
    private GuiButton prevButton;
    private GuiButton nextButton;

    public ScreenModValues(final GuiScreen parentScreen) {
	setParentScreen(parentScreen);
    }

    @Override
    public void initGui() {
	getLoadedValues().clear();
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
		if (value.getValue() instanceof Integer
			|| value.getValue() instanceof Double
			|| value.getValue() instanceof Long
			|| value.getValue() instanceof Float)
		    getLoadedValues().add(value);
	    }
	}
	byte var1 = -16;
	ModValue[] values = getRegisteredValues();
	sortValues(values);
	setAmountOfPages(values.length / getValuesPerPage()
		+ (values.length % getValuesPerPage() > 0 ? 1 : 0));
	for (int i = 0; i < getValuesPerPage(); i++) {
	    if (i + (getCurrentPage() - 1) * getValuesPerPage() + 1 > values.length)
		break;
	    ModValue value = values[i + (getCurrentPage() - 1)
		    * getValuesPerPage()];
	    getButtonList().add(
		    new ButtonModSlider(value.getName(), i, width / 2 - 100,
			    height / 4 + 24 + var1 + i * 24, value.getId(),
			    value.getMod(), value.getMax()));
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
	switch (button.id) {
	case 98:
	    getButtonList().clear();
	    setCurrentPage(getCurrentPage() - 1);
	    initGui();
	    break;

	case 99:
	    getButtonList().clear();
	    setCurrentPage(getCurrentPage() + 1);
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

    public ModValue[] sortValues(final ModValue[] values) {
	Arrays.sort(values, new Comparator<ModValue>() {
	    @Override
	    public int compare(final ModValue value1, final ModValue value2) {
		String modName1 = value1.getName();
		String modName2 = value2.getName();
		return modName1.compareTo(modName2);
	    }
	});
	return values;
    }

    @Override
    public void drawScreen(final int x, final int y, final float ticks) {
	drawDefaultBackground();
	drawCenteredString(fontRenderer, "Mod Values (" + getCurrentPage()
		+ "/" + getAmountOfPages() + ")", width / 2, 40, 0xFFFFFFFF);
	super.drawScreen(x, y, ticks);
    }

    public GuiScreen getParentScreen() {
	return parentScreen;
    }

    public void setParentScreen(final GuiScreen parentScreen) {
	this.parentScreen = parentScreen;
    }

    public static List<ModValue> getLoadedValues() {
	return LOADED_VALUES;
    }

    public static int getCurrentPage() {
	return currentPage;
    }

    public static void setCurrentPage(final int currentPage) {
	ScreenModValues.currentPage = currentPage;
    }

    public static int getAmountOfPages() {
	return amountOfPages;
    }

    public static void setAmountOfPages(final int amountOfPages) {
	ScreenModValues.amountOfPages = amountOfPages;
    }

    public static int getValuesPerPage() {
	return VALUES_PER_PAGE;
    }

    public ModValue[] getRegisteredValues() {
	return getLoadedValues()
		.toArray(new ModValue[getLoadedValues().size()]);
    }
}