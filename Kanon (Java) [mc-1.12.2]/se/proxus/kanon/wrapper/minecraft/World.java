package se.proxus.kanon.wrapper.minecraft;

import lombok.experimental.UtilityClass;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

@UtilityClass
public class World {
    
    public List<Entity> getLoadedEntities() {
        return instance().loadedEntityList;
    }
    
    public List<TileEntity> getLoadedTileEntities() {
        return instance().loadedTileEntityList;
    }
    
    public WorldClient instance() {
        return Minekraft.instance().world;
    }
}
