package se.proxus.kanon.utils.minecraft.world;

import com.google.common.collect.AbstractIterator;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import se.proxus.kanon.utils.minecraft.player.Rotationz;
import se.proxus.kanon.wrapper.minecraft.Blokk;

import java.util.*;

public final class Blockz {
    
    public static Iterable<BlockPos> getValidBlocksByDistance(final double range, final boolean ignoreVisibility,
            final String... query) {
        // prepare range check
        final List<String> names = Arrays.asList(query);
        final Vec3d eyesPos = Rotationz.getEyeVector().subtract(0.5, 0.5, 0.5);
        final double rangeSq = Math.pow(range + 0.5, 2);
        
        // set start pos
        final BlockPos startPos = new BlockPos(Rotationz.getEyeVector());
        
        return () -> new AbstractIterator<BlockPos>() {
            // initialize queue
            private final ArrayDeque<BlockPos> queue = new ArrayDeque<>(Collections.singletonList(startPos));
            private final HashSet<BlockPos> visited = new HashSet<>();
            
            @Override
            protected BlockPos computeNext() {
                // find block using breadth first search
                while(!queue.isEmpty()) {
                    final BlockPos current = queue.pop();
    
                    // check range
                    if(eyesPos.squareDistanceTo(new Vec3d(current)) > rangeSq)
                        continue;
                    
                    final boolean canBeClicked = Blokk.canBeClicked(current);
                    
                    if(ignoreVisibility || !canBeClicked) {
                        // add neighbors
                        for (final EnumFacing facing : EnumFacing.values()) {
                            final BlockPos next = current.offset(facing);
    
                            if (visited.contains(next))
                                continue;
    
                            queue.add(next);
                            visited.add(next);
                        }
                    }
                    
                    // check if block is valid
                    if(canBeClicked && names.contains(Blokk.getBlock(current).getUnlocalizedName()))
                        return current;
                }
                
                return endOfData();
            }
        };
    }
    
    public static Iterable<BlockPos> getFilteredBlocksByDistance(final double range, final boolean ignoreVisibility,
            final String... filter) {
        // prepare range check
        final List<String> names = Arrays.asList(filter);
        final Vec3d eyesPos = Rotationz.getEyeVector().subtract(0.5, 0.5, 0.5);
        final double rangeSq = Math.pow(range + 0.5, 2);
        
        // set start pos
        final BlockPos startPos = new BlockPos(Rotationz.getEyeVector());
        
        return () -> new AbstractIterator<BlockPos>() {
            // initialize queue
            private final ArrayDeque<BlockPos> queue = new ArrayDeque<>(Collections.singletonList(startPos));
            private final HashSet<BlockPos> visited = new HashSet<>();
            
            @Override
            protected BlockPos computeNext() {
                // find block using breadth first search
                while(!queue.isEmpty()) {
                    final BlockPos current = queue.pop();
                    
                    // check range
                    if(eyesPos.squareDistanceTo(new Vec3d(current)) > rangeSq)
                        continue;
                    
                    final boolean canBeClicked = Blokk.canBeClicked(current);
                    
                    if(ignoreVisibility || !canBeClicked) {
                        // add neighbors
                        for (final EnumFacing facing : EnumFacing.values()) {
                            final BlockPos next = current.offset(facing);
                            
                            if (visited.contains(next))
                                continue;
                            
                            queue.add(next);
                            visited.add(next);
                        }
                    }
                    
                    // check if block is valid
                    if(canBeClicked && !names.contains(Blokk.getBlock(current).getUnlocalizedName()))
                        return current;
                }
                
                return endOfData();
            }
        };
    }
    
    public static Iterable<BlockPos> getValidBlocksByDistanceReversed(final double range,
            final boolean ignoreVisibility, final String... query) {
        final ArrayDeque<BlockPos> validBlocks = new ArrayDeque<>();
        
        getValidBlocksByDistance(range, ignoreVisibility, query)
                .forEach(validBlocks::push);
        
        return validBlocks;
    }
}
