package se.proxus.kanon.utils.minecraft.world;

import lombok.experimental.UtilityClass;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;
import se.proxus.kanon.wrapper.minecraft.Player;
import se.proxus.kanon.wrapper.minecraft.World;

import java.util.Comparator;

@UtilityClass
public final class Entitiez {
    
    public EntityLiving getClosest() {
        return World.getLoadedEntities()
                .stream()
                .filter(entity -> entity != null && !(entity instanceof EntityPlayerSP) &&
                                  entity instanceof EntityLiving)
                .map(entity -> (EntityLiving) entity)
                .filter(entityLiving -> !(entityLiving.isDead))
                .min(Comparator.comparingDouble(Player::distanceTo))
                .orElse(null);
    }
    
    public Vec3d getEyeVector(final EntityLivingBase entity) {
        return new Vec3d(entity.posX, (entity.posY + entity.getEyeHeight()), entity.posZ);
    }
}
