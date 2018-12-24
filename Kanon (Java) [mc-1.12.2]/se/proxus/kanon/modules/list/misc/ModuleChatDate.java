package se.proxus.kanon.modules.list.misc;

import net.minecraft.network.play.client.CPacketChatMessage;
import se.proxus.kanon.event4j.EventSubscribe;
import se.proxus.kanon.event4j.events.server.EventPacketReceived;
import se.proxus.kanon.modules.Module;
import se.proxus.kanon.modules.ModuleSignature;
import se.proxus.kanon.utils.minecraft.client.Colourz;
import se.proxus.kanon.utils.lang.Datez;
import se.proxus.kanon.utils.system.Reflectionz;

import java.lang.reflect.Field;

@ModuleSignature(author = "Oliver Berg", date = "2018/09/12 (10:32)")
public final class ModuleChatDate extends Module {

    public ModuleChatDate() {
        super("Chat Date",
              "Adds a date before chat incoming messages.",
              Controller.TOGGLE,
              Category.MISC);
    }

    @EventSubscribe
    public final void onEventChat(final EventPacketReceived event) {
        if (!(event.getPacket() instanceof CPacketChatMessage))
            return;

        try {
            final CPacketChatMessage chatPacket = (CPacketChatMessage) event.getPacket();
            final Field fieldMessage = Reflectionz.getNonAccessibleField(chatPacket.getClass(), "message");
            final String message = (String) fieldMessage.get(chatPacket);

            fieldMessage.set(chatPacket, String.format("%s[%s]: %s%s",
                                                       Colourz.GOLD,
                                                       Datez.getDate(),
                                                       Colourz.RESET,
                                                       message));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}