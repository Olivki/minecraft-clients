package se.proxus.kanon.gui.frames;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.darkstorm.minecraft.gui.AbstractGuiManager;
import org.darkstorm.minecraft.gui.component.Button;
import org.darkstorm.minecraft.gui.component.CheckButton;
import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.component.Frame;
import org.darkstorm.minecraft.gui.component.basic.BasicButton;
import org.darkstorm.minecraft.gui.component.basic.BasicFrame;
import org.darkstorm.minecraft.gui.component.basic.BasicToggleButton;
import org.darkstorm.minecraft.gui.layout.GridLayoutManager;
import org.darkstorm.minecraft.gui.listener.ButtonListener;
import org.darkstorm.minecraft.gui.theme.Theme;
import se.proxus.kanon.Kanon;
import se.proxus.kanon.gui.frames.theme.rori.RoriTheme;
import se.proxus.kanon.modules.Module;

import java.awt.*;

public class FramesManager extends AbstractGuiManager {

    private class ModuleFrame extends BasicFrame {

        private ModuleFrame() {
        }

        private ModuleFrame(final String title) {
            super(title);
        }
    }

    @Override
    public void setup() {
        final Theme theme = new RoriTheme();
        setTheme(theme);

        //Sample module frame setup

        final Frame hubFrame = new ModuleFrame("Hub");
        hubFrame.setTheme(theme);
        hubFrame.setLayoutManager(new GridLayoutManager(2, 0));
        hubFrame.setVisible(true);
        hubFrame.setClosable(false);
        hubFrame.setMinimized(true);

        for (final Module.Category category : Module.Category.values()) {
            if (contains(category.getName()) || category.equals(Module.Category.NONE) ||
                Kanon.MODULES.getByCategory(category).length <= 0) {
                continue;
            }

            final String name = category.getName();
            final BasicToggleButton categoryToggle = new BasicToggleButton(name, contains(name));

            categoryToggle.addButtonListener(new ButtonListener() {
                @Override
                public void onButtonPress(final Button button, final int clickType) {
                    if (clickType == 0) {
                        if (!contains(name)) {
                            createCategoryFrame(category);
                        } else {
                            removeFrame(getFrame(name));
                        }
                    }

                    ((CheckButton) button).setSelected(contains(name));
                }
            });

            hubFrame.add(categoryToggle, GridLayoutManager.HorizontalGridConstraint.FILL);
        }

        addFrame(hubFrame);

        // Optional equal sizing and auto-positioning
        resizeComponents();
        scaleSizes();
    }

    private void createCategoryFrame(final Module.Category category) {
        final Theme theme = getTheme();
        Frame categoryFrame = getFrame(category.getName());

        if (categoryFrame == null) {
            categoryFrame = new ModuleFrame(category.getName());
            categoryFrame.setTheme(getTheme());
            final Dimension defaultDimension =
                    theme.getUIForComponent(categoryFrame).getDefaultSize(categoryFrame);
            categoryFrame.setWidth(defaultDimension.width);
            categoryFrame.setHeight(defaultDimension.height);
            categoryFrame.layoutChildren();
            categoryFrame.setLayoutManager(new GridLayoutManager(1, 0));
            categoryFrame.setVisible(true);
            categoryFrame.setClosable(true);
            categoryFrame.setMinimized(true);
        }

        for (final Module module : Kanon.MODULES.getModules()) {
            if (!module.isToggleable() || module.getCategory().equals(Module.Category.NONE) ||
                    !module.getCategory().equals(category)) {
                continue;
            }

            final BasicToggleButton moduleToggle =
                    new BasicToggleButton(module.getName(), module.getState());

            moduleToggle.addButtonListener(new ButtonListener() {
                @Override
                public void onButtonPress(final Button button, final int clickType) {
                    if (clickType == 0) {
                        module.toggle();
                    } else if (clickType == 1) {
                        final String name = module.getName();

                        if (!contains(name)) {
                            createModuleFrame(module);
                        } else {
                            removeFrame(getFrame(name));
                        }
                    }

                    ((CheckButton) button).setSelected(module.getState());
                }
            });

            categoryFrame.add(moduleToggle, GridLayoutManager.HorizontalGridConstraint.FILL);
        }

        if (!contains(category.getName())) {
            addFrame(categoryFrame);
        }

        resizeComponents();
    }

    private void createModuleFrame(final Module module) {
        /*final Theme theme = getTheme();
        final String title = module.getKey();
        Frame moduleFrame = getFrame(title);

        if (moduleFrame == null) {
            moduleFrame = new ModuleFrame(title);
            moduleFrame.setTheme(getTheme());
            final Dimension defaultDimension =
                    theme.getUIForComponent(moduleFrame).getDefaultSize(moduleFrame);
            moduleFrame.setWidth(defaultDimension.width);
            moduleFrame.setHeight(defaultDimension.height);
            moduleFrame.layoutChildren();
            moduleFrame.setLayoutManager(new GridLayoutManager(1, 0));
            moduleFrame.setVisible(true);
            moduleFrame.setClosable(true);
            moduleFrame.setMinimized(true);
        }

        final Config config = module.getConfig();

        for (final ConfigValue value : config.getValues()) {
            if (!value.isEditableViaFile() || !value.isEditableViaCommand()) {
                continue;
            }

            if (value.getKey().equalsIgnoreCase("State")) {
                continue;
            }

            final String name = value.getKey();
            final Object objectValue = value.getValue();

            if (objectValue instanceof Boolean) {
                final BasicToggleButton booleanToggle = new BasicToggleButton(name, value.getBoolean());

                booleanToggle.addButtonListener(new ButtonListener() {
                    @Override
                    public void onButtonPress(final Button button, final int clickType) {
                        if (clickType == 0) {
                            config.toggle(name, true);
                            ((CheckButton) button).setSelected(value.getBoolean());
                        }
                    }
                });

                moduleFrame.add(booleanToggle, GridLayoutManager.HorizontalGridConstraint.FILL);
            } else if (objectValue instanceof Number) {
                BasicSlider slider = null;

                if (NumberUtils.isDecimalInstance(objectValue)) {
                    slider = new BasicSlider(name, value.getDouble(), 0, value.getMax(), 1,
                            BoundedRangeComponent.ValueDisplay.DECIMAL);
                } else if (objectValue instanceof Integer) {
                    final double valueInt = 0.0D + ((Number) objectValue).intValue();
                    slider = new BasicSlider(name, valueInt, 0, value.getMax(), 1,
                            BoundedRangeComponent.ValueDisplay.INTEGER);
                }

                if (slider == null) {
                    continue;
                }

                slider.addSliderListener(new SliderListener() {
                    @Override
                    public void onSliderValueChanged(final Slider slider) {
                        System.out.println(slider.getValue());
                    }
                });

                moduleFrame.add(slider);
            }
        }

        if (!contains(title)) {
            addFrame(moduleFrame);
        }

        resizeComponents();*/
    }

    private void scaleSizes() {
        final Minecraft minecraft = Minecraft.getMinecraft();
        final Dimension maxSize = recalculateSizes();
        int offsetX = 5, offsetY = 5;
        int scale = minecraft.gameSettings.guiScale;
        if (scale == 0) { scale = 1000; }
        int scaleFactor = 0;
        while (scaleFactor < scale && minecraft.displayWidth / (scaleFactor + 1) >= 320 &&
                minecraft.displayHeight / (scaleFactor + 1) >= 240) { scaleFactor++; }
        for (final org.darkstorm.minecraft.gui.component.Frame frame : getFrames()) {
            frame.setX(offsetX);
            frame.setY(offsetY);
            offsetX += maxSize.width + 5;
            if (offsetX + maxSize.width + 5 > minecraft.displayWidth / scaleFactor) {
                offsetX = 5;
                offsetY += maxSize.height + 5;
            }
        }
    }

    @Override
    protected void resizeComponents() {
        final Theme theme = getTheme();
        final Frame[] frames = getFrames();
        final Button enable = new BasicButton("Enable");
        final Button disable = new BasicButton("Disable");
        final Dimension enableSize = theme.getUIForComponent(enable).getDefaultSize(enable);
        final Dimension disableSize = theme.getUIForComponent(disable).getDefaultSize(disable);
        final int buttonWidth = Math.max(enableSize.width, disableSize.width);
        final int buttonHeight = Math.max(enableSize.height, disableSize.height);
        for (final Frame frame : frames) {
            if (frame instanceof ModuleFrame) {
                for (final Component component : frame.getChildren()) {
                    if (component instanceof Button) {
                        component.setWidth(buttonWidth);
                        component.setHeight(buttonHeight);
                    }
                }
            }
        }
        recalculateSizes();
    }

    private Dimension recalculateSizes() {
        final Frame[] frames = getFrames();
        int maxWidth = 0, maxHeight = 0;

        for (final Frame frame : frames) {
            final Dimension defaultDimension =
                    frame.getTheme().getUIForComponent(frame).getDefaultSize(frame);
            maxWidth = Math.max(maxWidth, defaultDimension.width);
            frame.setHeight(defaultDimension.height);

            if (frame.isMinimized()) {
                for (final Rectangle area : frame.getTheme().getUIForComponent(frame).getInteractableRegions(
                        frame)) {
                    maxHeight = Math.max(maxHeight, area.height);
                }
            } else {
                maxHeight = Math.max(maxHeight, defaultDimension.height);
            }
        }

        for (final Frame frame : frames) {
            frame.setWidth(maxWidth);
            frame.layoutChildren();
        }

        return new Dimension(maxWidth, maxHeight);
    }

    public Frame getFrame(final String title) {
        for (final Frame frame : getFrames()) {
            if (frame.getTitle().equalsIgnoreCase(title)) {
                return frame;
            }
        }

        return null;
    }

    public boolean contains(final String title) {
        return getFrame(title) != null;
    }

    private GuiScreen getScreen() {
        return Kanon.FRAMES_SCREEN;
    }

    private Kanon getKanon() {
        return Kanon.getInstance();
    }
}
