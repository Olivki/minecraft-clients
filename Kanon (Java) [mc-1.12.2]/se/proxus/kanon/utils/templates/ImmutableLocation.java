package se.proxus.kanon.utils.templates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

@Getter
@Setter
@AllArgsConstructor
public final class ImmutableLocation {
    
    private final double x;
    private final double y;
    private final double z;
    
    public ImmutableLocation(final int x, final int y, final int z) {
        this((double) x, (double) y, (double) z);
    }
    
    public ImmutableLocation(final Entity source) {
        this(source.posX, source.posY, source.posZ);
    }
    
    public ImmutableLocation(final Vec3d vec) {
        this(vec.xCoord, vec.yCoord, vec.zCoord);
    }
    
    public ImmutableLocation(final Vec3i source) {
        this(source.getX(), source.getY(), source.getZ());
    }
    
    public ImmutableLocation(final BlockPos pos) {
        this(pos.getX(), pos.getY(), pos.getZ());
    }
    
    public ImmutableLocation(final AxisAlignedBB box) {
        this(box.minX, box.minY, box.minZ);
    }
    
    
    
    public final Vec3d toVec3d() {
        return new Vec3d(x, y, z);
    }
    
    public final BlockPos toBlockPos() {
        return new BlockPos(toVec3d());
    }
    
    public final AxisAlignedBB toBoundingBox() {
        return new AxisAlignedBB(toBlockPos());
    }
    
}
