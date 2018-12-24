package se.proxus.kanon.wrapper.minecraft;

import com.sun.istack.internal.NotNull;
import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.Packet;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import se.proxus.kanon.utils.system.Filez;

import java.io.File;
import java.util.Arrays;

@UtilityClass
public class Minekraft {
    
    public static final File DIRECTORY = Filez.getAppDir(".minecraft");
    
    public static void sendMessage(final String message) {
        Player.sendMessage(message);
    }
    
    public static void addMessage(final String message) {
        Player.addMessage(message);
    }
    
    /**
     * @param hostname
     *         The hostname for the server.
     * @param port
     *         The port for the server.
     * @author Ownage
     */
    public void connectToServer(final String hostname, final int port) {
        if (instance().world != null) {
            instance().world.sendQuittingDisconnectingPacket();
            
            try {
                Thread.sleep(2000L);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
        
        instance().displayGuiScreen(new GuiConnecting(null, instance(), hostname, port));
    }
    
    /**
     * @param packet
     *         The Packet to be sent to the server.
     */
    public static void sendPacket(final Packet packet) {
        instance().player.connection.sendPacket(packet);
    }
    
    public static void playSound(final String location, final float volume) {
		/*instance().getSoundHandler().playSound(
				PositionedSoundRecord.func_184371_a(new SoundEvent(new ResourceLocation(location)),
						volume));*/
    }
    
    public static ITextComponent getTextComponent(final String message) {
        return new TextComponentString(message);
    }
    
    public static ITextComponent[] getTextComponents(@NotNull final String... messages) {
        return Arrays.stream(messages)
                .map(Minekraft::getTextComponent)
                .toArray(ITextComponent[]::new);
    }
    
    public static float ticksToSeconds(final int ticks) {
        return (float) (ticks / 20);
    }
    
    public static float secondsToTicks(final float seconds) {
        return seconds * 20;
    }
    
    public String getVersion() {
        return "1.12.2";
    }
    
    public static Minecraft instance() {
        return Minecraft.getMinecraft();
    }
    
    // -- Getter Wrappers -
    public static GameSettings getSettings() {
        return instance().gameSettings;
    }
    
    public static FontRenderer getFont() {
        return instance().fontRendererObj;
    }
    
    public static boolean inGameHasFocus() {
        return instance().inGameHasFocus;
    }
    
    public static float getRenderPartialTicks() {
        return instance().getRenderPartialTicks();
    }
    
    public static PlayerControllerMP getController() {
        return instance().playerController;
    }
    
    public static WorldClient getWorld() {
        return World.instance();
    }
    
    public static EntityPlayerSP getPlayer() {
        return instance().player;
    }
}
