package se.proxus.eien.mods.list.world;

import java.lang.reflect.Field;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import se.proxus.eien.events.EventHandler;
import se.proxus.eien.events.EventManager;
import se.proxus.eien.events.list.client.EventDisplayGuiScreen;
import se.proxus.eien.mods.Mod;
import se.proxus.eien.mods.ModCategory;
import se.proxus.eien.tools.Tools;

public class SignFiller extends Mod {

	public SignFiller() {
		super("Sign Filler", ModCategory.WORLD, false);
	}

	@Override
	public void init() {
		addValue("Line 1", "EK", "First line of the text displayed on the sign.", 15, true, true);
		addValue("Line 2", "IS", "Second line of the text displayed on the sign.", 15, true, true);
		addValue("Line 3", "LOVE", "Third line of the text displayed on the sign.", 15, true, true);
		addValue("Line 4", "<333", "Fourth line of the text displayed on the sign.", 15, true, true);
		setValue("Description", "Fills signs you place down with the set text.", false);
		checkState();
	}

	@Override
	public void onEnable() {
		EventManager.registerListener(this);
	}

	@Override
	public void onDisable() {
		EventManager.unregisterListener(this);
	}

	@EventHandler
	public void onDisplayGuiScreen(final EventDisplayGuiScreen event) throws Exception {
		GuiScreen screen = event.getScreen();

		if (!(screen instanceof GuiEditSign)) {
			return;
		}

		GuiEditSign guiSign = (GuiEditSign) screen;
		Field fieldSign = Tools.getPrivateField(guiSign.getClass(), "tileSign");
		TileEntitySign entitySign = (TileEntitySign) fieldSign.get(guiSign);
		BlockPos signPos = entitySign.getPos();
		ITextComponent[] signText = new ITextComponent[] {
				new TextComponentString(getValue("Line 1").getString()),
				new TextComponentString(getValue("Line 2").getString()),
				new TextComponentString(getValue("Line 3").getString()),
				new TextComponentString(getValue("Line 4").getString()) };
		Tools.sendPacket(new CPacketUpdateSign(signPos, signText));
		event.setScreen(null);
	}
}