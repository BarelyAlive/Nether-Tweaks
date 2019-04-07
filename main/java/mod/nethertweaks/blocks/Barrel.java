package mod.nethertweaks.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.EnumFaceDirection;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tileentities.TileEntityBarrel;
import mod.nethertweaks.blocks.tileentities.TileEntityCondenser;
import mod.nethertweaks.blocks.tileentities.TileEntityNetherrackFurnace;
import mod.nethertweaks.blocks.tileentities.TileEntityBarrel.BarrelMode;
import mod.nethertweaks.blocks.tileentities.TileEntityBarrel.ExtractMode;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.BucketNFluidHandler;
import mod.nethertweaks.handler.ItemHandler;
import mod.nethertweaks.handler.CompostHandler;
import mod.nethertweaks.interfaces.INames;
import mod.sfhcore.handler.RegisterTileEntity;
import mod.sfhcore.helper.FluidHelper;
import mod.sfhcore.helper.StackUtils;

public class Barrel extends BlockContainer
{	
	
	public Barrel() {
		super(Material.WOOD);
		//setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 1.0F, 0.9F);
		setRegistryName("nethertweaksmod", INames.BARREL);
		setUnlocalizedName(INames.BARREL);
		setResistance(10.0f);
		setHardness(2.0f);
		setCreativeTab(NetherTweaksMod.tabNTM);
	}

	public Barrel(Material material) {
        super(material);  
    }
	
	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isBlockLoaded(pos)) {
			if (playerIn == null) {
				return false;
			}
			TileEntityBarrel barrel = (TileEntityBarrel) worldIn.getTileEntity(pos);
			if (barrel.getMode().canExtract == ExtractMode.Always) {
				barrel.giveAppropriateItem();
			} else if (playerIn.getHeldItemMainhand() != null) {
				ItemStack item = playerIn.getHeldItemMainhand();
				if (item != null) {

					//COMPOST!
					if (barrel.getMode() == BarrelMode.EMPTY
							|| barrel.getMode() == BarrelMode.COMPOST && !barrel.isFull()) {
						if (CompostHandler.containsItem(item.getItem(), item.getItemDamage())) {
							barrel.addCompostItem(CompostHandler.getItem(item.getItem(), item.getItemDamage()));

							if (!playerIn.capabilities.isCreativeMode) {
								StackUtils.substractFromStackSize(item, 1);
								if (item.getCount() == 0) {
									item = null;
								}
							}
						}
					}
				}

				//FLUIDS!
				if (barrel.getMode() == BarrelMode.EMPTY || barrel.getMode() == BarrelMode.FLUID) {
					FluidStack fluid = FluidHelper.getFluidForFilledItem(item.getItem());
					//FILL
					if (fluid != null) {

						int capacity = barrel.fill(fluid, false);

						if (capacity > 0) //&& fluid.fluidID == FluidRegistry.WATER.getID())
						{
							barrel.fill(fluid, true);

							if (!playerIn.capabilities.isCreativeMode) {
								if (item.getItem() == Items.POTIONITEM && item.getItemDamage() == 0) {
									playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem,
											new ItemStack(Items.GLASS_BOTTLE, 1, 0));
								} else {
									playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem,
											getContainer(item));
								}
							}
						}
					}
					//DRAIN
					else if (FluidHelper.isFillableContainerWithRoom(item)) {
						FluidStack available = barrel.drain(Integer.MAX_VALUE, false);
						if (available != null) {
							ItemStack filled = FluidHelper.fillContainer(item, available.getFluid(), available.amount);
							;
							FluidStack liquid = FluidHelper.getFluidForFilledItem(filled.getItem());
							if (liquid != null) {

								if (item.getCount() > 1) {
									if (!playerIn.inventory.addItemStackToInventory(filled)) {
										return false;
									} else {
										StackUtils.substractFromStackSize(item, 1);
									}
								} else {
									playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, filled);
								}

								barrel.drain(liquid.amount, true);
								return true;
							}
						}
					}

					//XXX BARREL RECIPES!
					if (item != null) {
						if (barrel.getMode() == BarrelMode.FLUID && barrel.isFull()) {
							if (barrel.fluid.getFluid() == FluidRegistry.WATER) {
								//Dust turns water into clay!
								if (item.getItem() == Item.getItemFromBlock(BlockHandler.DUST)) {
									barrel.setMode(BarrelMode.CLAY);
									useItem(playerIn);
								}

								if (item.getItem() == ItemHandler.LIGHTCRYSTAL) {
									barrel.fluid = new FluidStack(BucketNFluidHandler.fluidDemonWater, 1000);
									barrel.setMode(BarrelMode.FLUID);
								}

								//Milk + Water = Slime!
								if (item.getItem() == Items.MILK_BUCKET) {
									barrel.setMode(BarrelMode.MILKED);
									useItem(playerIn);
								}

								//Mushroom stew + Water = Witch Water!
								if (item.getItem() == Items.MUSHROOM_STEW
										|| item.getItem() == ItemHandler.MUSHROOMSPORES) {
									barrel.setMode(BarrelMode.SPORED);
									useItem(playerIn);
								}

							}

							if (barrel.fluid.getFluid() == FluidRegistry.LAVA) {

								//Glowstone + Lava = End Stone
								if (item.getItem() == Items.GLOWSTONE_DUST) {
									barrel.setMode(BarrelMode.ENDSTONE);
									useItem(playerIn);
								}

							}

							if (barrel.fluid.getFluid() == BucketNFluidHandler.fluidDemonWater) {
								if (item.getItem() == Item.getItemFromBlock(BlockHandler.NETHERSAPLING)) {
									barrel.setMode(BarrelMode.OAK);
									useItem(playerIn);
								}
							}

						}
					}
				}
			}
			//Return true to keep buckets from pouring all over the damn place.
			return true;
		}
		return false;
	}

	public void useItem(EntityPlayer player)
	{
		if (!player.capabilities.isCreativeMode)
		{

			ItemStack item = player.getHeldItemMainhand();
			//Special cases
			if (item.getItem() == Items.MILK_BUCKET)
			{
				player.setHeldItem(player.getActiveHand(), new ItemStack(Items.BUCKET, 1));
			}
			else if (item.getItem() == Items.MUSHROOM_STEW)
			{
				player.setHeldItem(player.getActiveHand(), new ItemStack(Items.BOWL, 1));
			}
			//Generic case
			else
			{
				StackUtils.substractFromStackSize(item, 1);;

				if (item.getCount() == 0)
				{
					item = null;
				}
			}
		}
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		// TODO Auto-generated method stub
		return super.canRenderInLayer(state, layer);
	}

	private ItemStack getContainer(ItemStack item)
	{
		if (item.getCount() == 1) {
			if (item.getItem().hasContainerItem(item)) 
			{
				return item.getItem().getContainerItem(item);
			} else 
			{
				return null;
			}
		} else 
		{
			item.splitStack(1);
			return item;
		}
	}

	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityBarrel(getUnlocalizedName());
	}
}