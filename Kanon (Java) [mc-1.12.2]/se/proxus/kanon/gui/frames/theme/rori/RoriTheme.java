package se.proxus.kanon.gui.frames.theme.rori;

import net.minecraft.client.gui.FontRenderer;
import org.darkstorm.minecraft.gui.theme.AbstractTheme;
import se.proxus.kanon.wrapper.minecraft.Minekraft;

public class RoriTheme extends AbstractTheme {

    private final FontRenderer fontRenderer;

    public RoriTheme() {
        fontRenderer = Minekraft.getFont();

        installUI(new RoriFrameUI(this));
        installUI(new RoriPanelUI(this));
        installUI(new RoriLabelUI(this));
        installUI(new RoriButtonUI(this));
        installUI(new RoriCheckButtonUI(this));
        installUI(new RoriToggleButtonUI(this));
        installUI(new RoriComboBoxUI(this));
        installUI(new RoriSliderUI(this));
        installUI(new RoriProgressBarUI(this));
    }

    public FontRenderer getFontRenderer() {
        return fontRenderer;
    }
}
