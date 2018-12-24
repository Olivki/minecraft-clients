package se.proxus.kanon.event4j.events.player;

import lombok.Data;
import net.minecraft.util.math.BlockPos;
import se.proxus.kanon.event4j.events.EventCancellable;

@Data
public class EventPlayerBlockDestroyed extends EventCancellable {

    private final BlockPos blockPos;
    
}
