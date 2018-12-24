package se.proxus.kanon.event4j.events.player;

import lombok.Data;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import se.proxus.kanon.event4j.events.EventCancellable;

@Data
public class EventPlayerBlockDamaged extends EventCancellable {

    private final BlockPos blockPos;
    private final EnumFacing facing;
    private final World world;
    private float threshold = 1.0F;
    private int delay = 5;
    
    public final Block getBlock() {
        return getBlockState().getBlock();
    }
    
    public final IBlockState getBlockState() {
        return world.getBlockState(blockPos);
    }
}
