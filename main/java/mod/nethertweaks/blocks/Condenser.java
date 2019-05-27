package mod.nethertweaks.blocks;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tile.TileCondenser;
import mod.sfhcore.blocks.CubeContainerHorizontal;
import mod.sfhcore.network.NetworkHandler;
import mod.sfhcore.proxy.IVariantProvider;
import mod.sfhcore.util.TankUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.common.registry.GameRegistry;
 
public class Condenser extends CubeContainerHorizontal
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public Condenser()
    {
        super(Material.ROCK, new ResourceLocation(NetherTweaksMod.MODID, INames.CONDENSER));
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        setResistance(17.5f);
        setHardness(3.5f);
        setCreativeTab(NetherTweaksMod.TABNTM);
    }
    
    @Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ)
    {
		if(!world.isBlockLoaded(pos)) return false;
		TileCondenser te = (TileCondenser) world.getTileEntity(pos);
		if(te ==  null) return false;
		if(!(te instanceof TileCondenser)) return false;
    	if(world.isRemote) return true;
    	if(player.isSneaking()) return false;
    	
		if(TankUtil.drainWaterIntoBottle(te, player, te.getTank()))	return true;
    	
    	IFluidHandlerItem item = FluidUtil.getFluidHandler(player.getHeldItem(hand));
    	
    	if (item != null) {
			FluidUtil.interactWithFluidHandler(player, hand, te.getTank());
			return true;
		}
		player.openGui(NetherTweaksMod.instance, 1, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
    }
    
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
    
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileCondenser();
    }
}