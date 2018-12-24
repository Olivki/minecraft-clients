package se.proxus.kanon.wrapper.minecraft;

import lombok.experimental.UtilityClass;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

@UtilityClass
public final class Blokk {
    
    public static IBlockState getState(final BlockPos pos) {
        return Minecraft.getMinecraft().world.getBlockState(pos);
    }
    
    public static Block getBlock(final BlockPos pos) {
        return getState(pos).getBlock();
    }
    
    public static int getId(final BlockPos pos) {
        return Block.getIdFromBlock(getBlock(pos));
    }
    
    public static String getName(final Block block) {
        return Block.REGISTRY.getNameForObject(block).toString();
    }
    
    public static Material getMaterial(final BlockPos pos) {
        return getState(pos).getMaterial();
    }
    
    public static int getIntegerProperty(final IBlockState state, final PropertyInteger prop) {
        return state.getValue(prop);
    }
    
    public static AxisAlignedBB getBoundingBox(final BlockPos pos) {
        return getState(pos).getBoundingBox(Minecraft.getMinecraft().world, pos).offset(pos);
    }
    
    public static boolean canBeClicked(final BlockPos pos) {
        return getBlock(pos).canCollideCheck(getState(pos), false);
    }
    
    public static float getHardness(final BlockPos pos) {
        return getState(pos).getPlayerRelativeBlockHardness(Player.getPlayer(), Minecraft.getMinecraft().world, pos);
    }
}
