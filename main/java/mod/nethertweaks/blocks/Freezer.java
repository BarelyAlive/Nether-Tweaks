package mod.nethertweaks.blocks;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tileentities.TileEntityFreezer;
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
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Freezer extends BlockContainer{
	
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public Freezer() {
		super(Material.ROCK);
		setUnlocalizedName(INames.FREEZER);
		setResistance(17.5f);
		setHardness(3.5f);
		setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
		GameRegistry.registerTileEntity(TileEntityFreezer.class, INames.TEFREEZER);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
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
	          
	          if (FluidContainerRegistry.isFilledContainer(item))
	          {
	            FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(item);
	            if (fr.fill(fluid, false) == fluid.amount)
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
	                if ((c == null) || (item.stackSize == 1) || (playerIn.inventory.hasItemStack(c)))
	                {
	                  fr.fill(fluid, true);
	                  if (item.stackSize == 1) {
	                    playerIn.inventory.setInventorySlotContents(0, c);
	                  } else if (item.stackSize > 1) {
	                    item.stackSize -= 1;
	                  }
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
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		// TODO Auto-generated method stub
		return new TileEntityFreezer();
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
}
