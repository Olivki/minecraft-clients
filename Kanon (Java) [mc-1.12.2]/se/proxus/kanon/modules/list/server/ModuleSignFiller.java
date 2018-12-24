package se.proxus.kanon.modules.list.server;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import se.proxus.kanon.event4j.EventSubscribe;
import se.proxus.kanon.event4j.events.client.EventDisplayGuiScreen;
import se.proxus.kanon.modules.Module;
import se.proxus.kanon.modules.ModuleSignature;
import se.proxus.kanon.utils.system.Reflectionz;
import se.proxus.kanon.wrapper.minecraft.Minekraft;

import java.lang.reflect.Field;

@ModuleSignature(author = "Oliver Berg", date = "2018/09/10 (16:06)")
public final class ModuleSignFiller extends Module {

    public ModuleSignFiller() {
        super("Sign Filler",
              "H",
              "Fills signs you place down with the set text.",
              Controller.TOGGLE,
              Category.SERVER);
    }

    @Override
    public void initialize() {
        getConfig().addEntry("Lines", new String[]{ "EK", "IS", "LOVE", "<333" })
                    .setDescription("The text lines for the sign.")
                    .setRange(0, 15)
                    .setGeneric(String.class);
    }

    @EventSubscribe
    public final void onDisplayGuiScreen(final EventDisplayGuiScreen event) {
        final GuiScreen screen = event.getScreen();

        if (!(screen instanceof GuiEditSign))
            return;

        try {
            final String[] lines = getConfig().getArray("Lines", String.class);
            final GuiEditSign guiSign = (GuiEditSign) screen;
            final Field fieldSign = Reflectionz.getNonAccessibleField(guiSign.getClass(), "tileSign");
            final TileEntitySign entity = (TileEntitySign) fieldSign.get(guiSign);
            final BlockPos position = entity.getPos();
    
            final ITextComponent[] text = Minekraft.getTextComponents(lines);
    
            Minekraft.sendPacket(new CPacketUpdateSign(position, text));

            event.closeScreen();
        } catch (final Exception exception) {
            exception.printStackTrace();
        }
    }
}