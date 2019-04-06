package mod.nethertweaks.blocks;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tileentities.TileEntityBarrel;
import mod.nethertweaks.blocks.tileentities.TileEntityFreezer;
import mod.nethertweaks.blocks.tileentities.TileEntitySieve;
import mod.nethertweaks.blocks.tileentities.TileEntityBarrel.BarrelMode;
import mod.nethertweaks.blocks.tileentities.TileEntityBarrel.ExtractMode;
import mod.nethertweaks.blocks.tileentities.TileEntitySieve.SieveMode;
import mod.nethertweaks.handler.BucketNFluidHandler;
import mod.nethertweaks.handler.ItemHandler;
import mod.nethertweaks.handler.NTMCompostHandler;
import mod.nethertweaks.handler.NTMSieveHandler;
import mod.sfhcore.handler.RegisterTileEntity;
import mod.sfhcore.helper.FluidHelper;
import mod.sfhcore.helper.StackUtils;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Sieve extends BlockContainer implements IVariantProvider{
	
	public Sieve() {
		super(Material.ROCK);
		setUnlocalizedName(INames.SIEVE);
		setRegistryName("nethertweaksmod", INames.SIEVE);
		setResistance(15.0f);
		setHardness(2.0f);
		this.setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isBlockLoaded(pos)) {
			if (playerIn == null) {
				return false;
			}
			TileEntitySieve sieve = (TileEntitySieve) worldIn.getTileEntity(pos);
			if (sieve.mode == SieveMode.EMPTY && playerIn.inventory.getCurrentItem() != null) {
				ItemStack held = playerIn.inventory.getCurrentItem();

				if (NTMSieveHandler.Contains(Block.getBlockFromItem(held.getItem()), held.getItemDamage())) {
					sieve.addSievable(Block.getBlockFromItem(held.getItem()), held.getItemDamage());
					removeCurrentItem(playerIn);
				}
				if (FluidHelper.hasFluidAmount(FluidRegistry.WATER, held, 1000)) {
					sieve.addSievable(Blocks.WATER, 0);
					FluidUtil.getFluidHandler(held).drain(1000, true);
				}
			} else {
				if (worldIn.isRemote) {
					sieve.ProcessContents(false);
				} else {
					if (sieve.mode != SieveMode.EMPTY) {
						sieve.ProcessContents(false);
					}
				}
			}
			return true;
		}
		return false;
	}
	
	private void removeCurrentItem(EntityPlayer player)
	{
		ItemStack item = player.inventory.getCurrentItem();

		if (!player.capabilities.isCreativeMode)
		{
			StackUtils.substractFromStackSize(item, 1);;
			if (item.getCount() == 0)
			{
				item = null;
			}
		}

	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntitySieve();
	}
	
	public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
        ret.add(new ImmutablePair<Integer, String>(0, "normal"));
        return ret;
    }
}
