package mod.nethertweaks.blocks;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tileentities.TileEntityCondenser;
import mod.nethertweaks.blocks.tileentities.TileEntityFreezer;
import mod.sfhcore.handler.RegisterTileEntity;
import mod.sfhcore.helper.FluidHelper;
import mod.sfhcore.helper.StackUtils;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.EnumFaceDirection;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Freezer extends BlockContainer implements IVariantProvider{
	
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public Freezer() {
		super(Material.ROCK);
		setUnlocalizedName(INames.FREEZER);
		setResistance(17.5f);
		setHardness(3.5f);
		setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
        RegisterTileEntity.add(this, new TileEntityFreezer(INames.TEFREEZER));
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
            if(worldIn.getTileEntity(pos) != null) {
                playerIn.openGui(NetherTweaksMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
	      if (worldIn.getTileEntity(pos) instanceof TileEntityFreezer && playerIn.inventory.getCurrentItem() != null)
	      {
	        TileEntityFreezer fr = (TileEntityFreezer)worldIn.getTileEntity(pos);
	        IFluidTankProperties[] tank = fr.getTankProperties();
	        ItemStack item = playerIn.inventory.getCurrentItem();
	        if (item != null)
	        {
	          FluidStack fluid = FluidHelper.getFluidForFilledItem(item.getItem());
	          if (FluidHelper.isFillableContainerWithRoom(item) && fr.fill(fluid, false) <= fluid.amount)
	          {
	              if (playerIn.capabilities.isCreativeMode)
	              {
	                fr.fill(fluid, true);
	              }
	              else
	              {
	                ItemStack c = null;
	                if (item.getItem().hasContainerItem(item)) {
	                  c = item.getItem().getContainerItem(item);
	                }
	                if ((c == null) || (item.getCount() == 1) || (playerIn.inventory.hasItemStack(c)))
	                {
	                  fr.fill(fluid, true);
	                  if (item.getCount() == 1) {
	                    playerIn.inventory.setInventorySlotContents(0, c);
	                  } else if (item.getCount() > 1) {
	                    StackUtils.substractFromStackSize(item, 1);;
	                  }
	                }
	              }
	          }
	        }
	      }
	    }
        return true;
	}

	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityFreezer("freezer");
	}

	private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock())
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock())
            {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock())
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock())
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }
	
	@Override
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
            ret.add(new ImmutablePair<Integer, String>(0, "type=normal"));
        return ret;
    }
}
