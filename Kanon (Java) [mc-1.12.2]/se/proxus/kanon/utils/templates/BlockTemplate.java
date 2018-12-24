package se.proxus.kanon.utils.templates;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import se.proxus.kanon.utils.minecraft.player.Rotationz;
import se.proxus.kanon.wrapper.minecraft.Blokk;
import se.proxus.kanon.wrapper.minecraft.Minekraft;
import se.proxus.kanon.wrapper.minecraft.Player;

import java.util.Objects;

@Data
@RequiredArgsConstructor
public final class BlockTemplate {
    
    private final Location location;
    private final Block block;
    private EnumFacing facing;
    private boolean done;
    
    public BlockTemplate(final BlockPos pos) {
        this.location = new Location(pos);
        this.block = Blokk.getBlock(pos);
    }
    
    public final boolean isVisible() {
        final Vec3d eyesVector = Rotationz.getEyeVector();
        final Vec3d positionVector = location.getVector().addVector(0.5, 0.5, 0.5);
        final double distanceSqPosVec = eyesVector.squareDistanceTo(positionVector);
    
        for(final EnumFacing side : EnumFacing.values()) {
            final Vec3d sideVector = positionVector.add(new Vec3d(side.getDirectionVec()).scale(0.5));
            final double distanceSqHitVec = eyesVector.squareDistanceTo(sideVector);
        
            // check if hitVec is within range (4.25 blocks)
            if(distanceSqHitVec > 18.0625)
                continue;
        
            // check if side is facing towards player
            if(distanceSqHitVec >= distanceSqPosVec)
                continue;
        
            if(Player.getPlayer().world.rayTraceBlocks(eyesVector,
                                                       sideVector,
                                                       false,
                                                       true,
                                                       false) != null)
                continue;
        
            return true;
        }
    
        return false;
    }
    
    public final boolean faceBlock() {
        if (!isVisible())
            return  false;
        
        final Vec3d positionVector = new Vec3d(location.toBlockPos()).addVector(0.5, 0.5, 0.5);
    
        for(final EnumFacing side : EnumFacing.values()) {
            final Vec3d sideVector = positionVector.add(new Vec3d(side.getDirectionVec()).scale(0.5));
            
            if(Rotationz.Server.faceVector(sideVector)) {
                setFacing(side);
                return true;
            }
        }
    
        return false;
    }
    
    public final void breakBlock(final Type type) {
        final BlockPos pos = location.toBlockPos();
        
        switch (type) {
            case CLICK: {
                break;
            }
            
            case PACKET: {
                if (faceBlock()) {
                    Minekraft.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK,
                                                                  pos,
                                                                  facing));
                    Player.swing(true);
                    Minekraft.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK,
                                                                  pos,
                                                                  facing));
                }
                break;
            }
            
            case METHOD: {
                if (faceBlock()) {
                    Player.getController().onPlayerDamageBlockSpecial(pos, facing, 0);
                    Player.swing(true);
                }
                break;
            }
        }
    }
    
    public final boolean isAir() {
        return Objects.equals(Blokk.getMaterial(location.toBlockPos()), Material.AIR)
               || Blokk.getMaterial(location.toBlockPos()).isLiquid();
    }
    
    @Override
    public boolean equals(final Object object) {
        return location.equals(((BlockTemplate) object).getLocation());
    }
    
    public enum Type {
        CLICK,
        PACKET,
        METHOD
    }
}
