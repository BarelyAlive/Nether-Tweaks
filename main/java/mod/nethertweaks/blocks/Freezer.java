package mod.nethertweaks.blocks;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tileentities.TileEntityCondenser;
import mod.nethertweaks.blocks.tileentities.TileEntityFreezer;
import mod.nethertweaks.blocks.tileentities.TileEntityFreezer;
import mod.sfhcore.blocks.CubeContainerHorizontal;
import mod.sfhcore.handler.RegisterTileEntity;
import mod.sfhcore.helper.FluidHelper;
import mod.sfhcore.helper.StackUtils;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.EnumFaceDirection;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Freezer extends CubeContainerHorizontal{
	
    private static final PropertyDirection FACING = BlockHorizontal.FACING;
    private static boolean keepInventory;
	
	public Freezer() {
		super(Material.ROCK, new ResourceLocation("nethertweaksmod", INames.FREEZER));
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		setUnlocalizedName(INames.FREEZER);
		setResistance(17.5f);
		setHardness(3.5f);
		setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isBlockLoaded(pos)) {
			if (!worldIn.isRemote) {
				if (worldIn.getTileEntity(pos) != null) {
					playerIn.openGui(NetherTweaksMod.instance, 2, worldIn, pos.getX(), pos.getY(), pos.getZ());
				}
				if (worldIn.getTileEntity(pos) instanceof TileEntityFreezer
						&& playerIn.inventory.getCurrentItem() != null) {
					TileEntityFreezer fr = (TileEntityFreezer) worldIn.getTileEntity(pos);
					IFluidTankProperties[] tank = fr.getTankProperties();
					ItemStack item = playerIn.inventory.getCurrentItem();
					if (item != null) {
						FluidStack fluid = FluidHelper.getFluidForFilledItem(item.getItem());
						if (FluidHelper.isFillableContainerWithRoom(item) && fr.fill(fluid, false) <= fluid.amount) {
							if (playerIn.capabilities.isCreativeMode) {
								fr.fill(fluid, true);
							} else {
								ItemStack c = null;
								if (item.getItem().hasContainerItem(item)) {
									c = item.getItem().getContainerItem(item);
								}
								if ((c == null) || (item.getCount() == 1) || (playerIn.inventory.hasItemStack(c))) {
									fr.fill(fluid, true);
									if (item.getCount() == 1) {
										playerIn.inventory.setInventorySlotContents(0, c);
									} else if (item.getCount() > 1) {
										StackUtils.substractFromStackSize(item, 1);
										;
									}
								}
							}
						}
					}
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
	    return new TileEntityFreezer(INames.TEFREEZER);
	}
}
