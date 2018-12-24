package se.proxus.kanon.event4j.events.player;

import lombok.Data;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;
import se.proxus.kanon.event4j.events.EventCancellable;

@Data
public class EventPlayerUpdate extends EventCancellable {
    
    private final EntityPlayerSP player;
    private final World world;

    public long getCurrentMilliseconds() {
        return getNanoTime() / 1000000;
    }

    public long getNanoTime() {
        return System.nanoTime();
    }
}
