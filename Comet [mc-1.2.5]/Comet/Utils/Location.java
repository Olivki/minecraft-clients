package Comet.Utils;

import net.minecraft.src.Entity;

public class Location
{

    public Location clone()
    {
        return new Location(posX, posY, posZ, rotationYaw, rotationPitch, name);
    }

    public Location(Entity entity)
    {
        this(entity, "");
    }

    public Location(Entity entity, String s)
    {
        this(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch, s);
    }

    public Location()
    {
        this(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, "");
    }

    public Location(double d, double d1, double d2, String s)
    {
        this(d, d1, d2, 0.0F, 0.0F, s);
    }

    public Location(double d, double d1, double d2)
    {
        this(d, d1, d2, 0.0F, 0.0F, "");
    }

    public Location(double d, double d1, double d2, float f, 
            float f1)
    {
        this(d, d1, d2, f, f1, "");
    }

    public Location(double d, double d1, double d2, float f, 
            float f1, String s)
    {
        posX = d;
        posY = d1;
        posZ = d2;
        rotationYaw = f;
        rotationPitch = f1;
        name = s;
    }

    public double distance(Location location)
    {
        return Math.sqrt(distanceSquare(location));
    }

    public double distanceSquare(Location location)
    {
        double d = location.posX - posX;
        double d1 = location.posY - posY;
        double d2 = location.posZ - posZ;
        return d * d + d1 * d1 + d2 * d2;
    }

    public double distance2D(Location location)
    {
        return Math.sqrt(distance2DSquare(location));
    }

    public double distance2DSquare(Location location)
    {
        double d = location.posX - posX;
        double d1 = location.posZ - posZ;
        return d * d + d1 * d1;
    }

    public double distanceY(Location location)
    {
        return location.posY - posY;
    }

    public Location(String s)
        throws Exception
    {
        String as[] = s.split(";", 6);
        if(as.length != 6)
        {
            throw new Exception("Invalid line!");
        } else
        {
            name = as[5];
            posX = Double.parseDouble(as[0]);
            posY = Double.parseDouble(as[1]);
            posZ = Double.parseDouble(as[2]);
            rotationYaw = Float.parseFloat(as[3]);
            rotationPitch = Float.parseFloat(as[4]);
            return;
        }
    }

    public String export()
    {
        return (new StringBuilder()).append(posX).append(";").append(posY).append(";").append(posZ).append(";").append(rotationYaw).append(";").append(rotationPitch).append(";").append(name).toString();
    }

    public double posX;
    public double posY;
    public double posZ;
    public float rotationYaw;
    public float rotationPitch;
    public String name;
}
