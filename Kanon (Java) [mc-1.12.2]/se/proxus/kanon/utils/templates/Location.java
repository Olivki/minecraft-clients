package se.proxus.kanon.utils.templates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import se.proxus.kanon.wrapper.minecraft.Player;

import javax.annotation.Nullable;

@Getter
@Setter
@AllArgsConstructor
public final class Location {
    
    private double x;
    private double y;
    private double z;
    private Vec3d $vec3d;
    
    public Location(final int x, final int y, final int z) {
        this((double) x, (double) y, (double) z);
    }
    
    public Location(final Entity source) {
        this(source.posX, source.posY, source.posZ);
    }
    
    public Location(final Vec3d vec) {
        this(vec.xCoord, vec.yCoord, vec.zCoord);
    }
    
    public Location(final Vec3i source) {
        this(source.getX(), source.getY(), source.getZ());
    }
    
    public Location(final BlockPos pos) {
        this(pos.getX(), pos.getY(), pos.getZ());
    }
    
    public Location(final AxisAlignedBB box) {
        this(box.minX, box.minY, box.minZ);
    }
    
    public void increaseX(final double amount) {
        x += amount;
    }
    
    public void decreaseX(final double amount) {
        x -= amount;
    }
    
    public void increaseY(final double amount) {
        y += amount;
    }
    
    public void decreaseY(final double amount) {
        y -= amount;
    }
    
    public void increaseZ(final double amount) {
        z += amount;
    }
    
    public void decreaseZ(final double amount) {
        z -= amount;
    }
    
    public double distanceToPlayer() {
        return distanceTo(Player.getLocation());
    }
    
    public double distanceTo(final Location location) {
        return (double) getVector().distanceTo(location.getVector());
    }
    
    public double squareDistanceTo(final Location location) {
        return getVector().squareDistanceTo(location.getVector());
    }
    
    public double squareDistanceTo(final double x, final double y, final double z) {
        return getVector().squareDistanceTo(x, y, z);
    }
    
    public Location scale(final double scaleFactor) {
        return new Location(getVector().scale(scaleFactor));
    }
    
    public double lengthVector() {
        return getVector().lengthVector();
    }
    
    public double lengthSquared() {
        return getVector().lengthSquared();
    }
    
    @Nullable
    @SuppressWarnings("ConstantConditions")
    public Location getIntermediateWithXValue(final Location location, final double x) {
        return new Location(getVector().getIntermediateWithXValue(location.getVector(), x));
    }
    
    @Nullable
    @SuppressWarnings("ConstantConditions")
    public Location getIntermediateWithYValue(final Location location, final double y) {
        return new Location(getVector().getIntermediateWithYValue(location.getVector(), y));
    }
    
    @Nullable
    @SuppressWarnings("ConstantConditions")
    public Location getIntermediateWithZValue(final Location location, final double z) {
        return new Location(getVector().getIntermediateWithZValue(location.getVector(), z));
    }
    
    public final Location rotatePitch(final float pitch) {
        return new Location(getVector().rotatePitch(pitch));
    }
    
    public final Location rotateYaw(final float yaw) {
        return new Location(getVector().rotateYaw(yaw));
    }
    
    public final Vec3d getVector() {
        if ($vec3d == null)
            $vec3d = new Vec3d(x, y, z);
        
        return $vec3d;
    }
    
    public void locationToPlayer() {
        Player.setPosition(this);
    }
    
    public final BlockPos toBlockPos() {
        return new BlockPos(getVector());
    }
    
    public final AxisAlignedBB toBoundingBox() {
        return new AxisAlignedBB(toBlockPos());
    }
    
    public static Location parse(final String location) {
        try {
            final String[] split = location
                    .replace("(", "")
                    .replace(")", "")
                    .split(", ");
            final double x = Double.parseDouble(split[0]);
            final double y = Double.parseDouble(split[1]);
            final double z = Double.parseDouble(split[2]);
            return new Location(x, y, z);
        } catch (final Exception exception) {
            exception.printStackTrace();
            return new Location(0, 0, 0);
        }
    }
    
    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ", " + getZ() + ")";
    }
    
    public String getDisplay() {
        return "(" + (int) getX() + ", " + (int) getY() + ", " + (int) getZ() + ")";
    }
    
    @Override
    public boolean equals(final Object compare) {
        if (this == compare) {
            return true;
        } else if (!(compare instanceof Location)) {
            return false;
        } else {
            final Location location = (Location) compare;
            
            if (Double.compare(location.getX(), getX()) != 0) {
                return false;
            } else if (Double.compare(location.getY(), getY()) != 0) {
                return false;
            } else {
                return Double.compare(location.getZ(), getZ()) == 0;
            }
        }
    }
    
}
