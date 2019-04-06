package mod.nethertweaks.blocks;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tileentities.TileEntityBarrel;
import mod.nethertweaks.blocks.tileentities.TileEntityCondenser;
import mod.nethertweaks.blocks.tileentities.TileEntityNetherrackFurnace;
import mod.nethertweaks.blocks.tileentities.TileEntityCondenser;
import mod.sfhcore.blocks.CubeContainerHorizontal;
import mod.sfhcore.handler.RegisterTileEntity;
import mod.sfhcore.proxy.IVariantProvider;
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
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
 
public class Condenser extends CubeContainerHorizontal{
     
    private static boolean keepInventory;
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public Condenser() {
        super(Material.ROCK);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        setRegistryName("nethertweaksmod", INames.CONDENSER);
        setUnlocalizedName(INames.CONDENSER);
        setResistance(30.0f);
        setHardness(4.0f);
        setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
    }
    
    @Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	if (world.isBlockLoaded(pos)) {
			if (!world.isRemote) {
				// ...
				if (player.isSneaking()) {
					// ...
				} else {
					TileEntity te = world.getTileEntity(pos);

					if (te instanceof TileEntityCondenser)
						player.openGui(NetherTweaksMod.instance, 1, world, pos.getX(), pos.getY(), pos.getZ());
				}
			}
			return true;
		}
    	return false;
    }
    
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
    
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityCondenser(INames.TECONDENSER);
    }
}