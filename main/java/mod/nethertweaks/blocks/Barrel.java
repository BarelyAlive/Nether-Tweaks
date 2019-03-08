package mod.nethertweaks.blocks;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.EnumFaceDirection;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.IScoreCriteria.EnumRenderType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import mod.nethertweaks.INames;
import mod.nethertweaks.NTMBlocks;
import mod.nethertweaks.NTMItems;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tileentities.TileEntityBarrel;
import mod.nethertweaks.blocks.tileentities.TileEntityCondenser;
import mod.nethertweaks.blocks.tileentities.TileEntityBarrel.BarrelMode;
import mod.nethertweaks.blocks.tileentities.TileEntityBarrel.ExtractMode;
import mod.nethertweaks.handler.NTMCompostHandler;
import mod.sfhcore.helper.Tools;
import mod.sfhcore.proxy.IVariantProvider;

public class Barrel extends BlockContainer implements IVariantProvider{
	
	public Barrel() {
		super(Material.WOOD);
		setUnlocalizedName(INames.BARREL);
		setResistance(10.0f);
		setHardness(2.0f);
		setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
		GameRegistry.registerTileEntity(TileEntityBarrel.class, INames.TEBARREL);
	}
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return new AxisAlignedBB(0.06d, 0.0d, 0.06d, 0.94d, 1.0d, 0.94d);
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
	
	public Barrel(Material material) {
        super(material);  
    }
	
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return true;
    }

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (player == null)
		{
			return false;
		}

		TileEntityBarrel barrel = (TileEntityBarrel) world.getTileEntity(pos);

		if(Tools.checkHandEmpty(player)){
			
			if (barrel.getMode() == BarrelMode.DIRT)
			{
				barrel.giveAppropriateItem();
				barrel.setMode(BarrelMode.EMPTY);
				
				return true;
			}
			
			return false;
		}
		
		if(!Tools.checkHandEmpty(player)){
			
			if(player.inventory.getCurrentItem().getItem() == Items.MILK_BUCKET || player.inventory.getCurrentItem().getItem() == NTMItems.bucketStoneMilk || player.inventory.getCurrentItem().getItem() == NTMItems.bucketWoodMilk){
				barrel.setMode(BarrelMode.MILKED);
			}
			else if (player.inventory.getCurrentItem() != null)
			{
				ItemStack item = player.inventory.getCurrentItem();
				if (item != null)
				{
	
					//COMPOST!
						if (barrel.getMode() == BarrelMode.EMPTY || barrel.getMode() == BarrelMode.COMPOST && !barrel.isFull())
						{
							if (NTMCompostHandler.containsItem(item.getItem(), item.getItemDamage()))
							{
								barrel.addCompostItem(NTMCompostHandler.getItem(item.getItem(), item.getItemDamage()));
								if (!player.capabilities.isCreativeMode)
								{
									item.stackSize -= 1;
									if (item.stackSize == 0)
									{
										item = null;
									}
								}
							}
						}
					}
	
	
					//FLUIDS!
					if (barrel.getMode() == BarrelMode.EMPTY || barrel.getMode() == BarrelMode.FLUID)
					{
						FluidStack fluid = new FluidStack(FluidRegistry.WATER, 1000);
						//FILL
						if (player.inventory.getCurrentItem().getItem() == Items.WATER_BUCKET || player.inventory.getCurrentItem().getItem() == NTMItems.bucketStoneWater || player.inventory.getCurrentItem().getItem() == NTMItems.bucketWoodWater)
						{
	
							int capacity = barrel.fill(fluid, false);
	
							if(capacity > 0) //&& fluid.fluidID == FluidRegistry.WATER.getID())
							{
								barrel.fill(fluid, true);
	
								if (!player.capabilities.isCreativeMode)
								{
									if (item.getItem() == Items.POTIONITEM && item.getItemDamage() == 0)
									{
										player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.GLASS_BOTTLE, 1, 0));
									}else
									{
										player.inventory.setInventorySlotContents(player.inventory.currentItem, getContainer(item));
									}
								}
							}
						}
						if(barrel.getMode() == BarrelMode.FLUID && barrel.fluid.getFluid() == FluidRegistry.WATER){
							if(player.inventory.getCurrentItem().getItem() == Item.getItemFromBlock(NTMBlocks.blockDust))
								barrel.setMode(BarrelMode.CLAY);
						}
						//DRAIN
						else if(FluidContainerRegistry.isContainer(item))
						{				
							FluidStack available = barrel.drain(Integer.MAX_VALUE, false);
							if (available != null)
							{
								ItemStack filled = FluidContainerRegistry.fillFluidContainer(available, item);
								FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(filled);
								if (liquid != null) {
	
									if (item.stackSize > 1) {
										if (!player.inventory.addItemStackToInventory(filled)) {
											return false;
										} else {
											item.stackSize -= 1;
										}
									} else {
										player.inventory.setInventorySlotContents(player.inventory.currentItem, filled);
									}
	
									barrel.drain(liquid.amount, true);
									return true;
								}
							}
						}
	
					//XXX BARREL RECIPES!
					if (item!= null)
					{
						if (barrel.getMode() == BarrelMode.FLUID && barrel.isFull())
						{
							if (barrel.fluid.getFluid() == FluidRegistry.WATER)
							{
								//Dust turns water into clay!
								if(item.getItem() == Item.getItemFromBlock(NTMBlocks.blockDust))
								{
									barrel.setMode(BarrelMode.CLAY);
									useItem(player);
								}
								
								if(item.getItem() == NTMItems.itemLightCrystal){
									barrel.fluid = new FluidStack(NTMItems.fluidDemonWater, 1000);
									barrel.setMode(BarrelMode.FLUID);
								}
	
								//Milk + Water = Slime!
								if(item.getItem() == Items.MILK_BUCKET)
								{
									barrel.setMode(BarrelMode.MILKED);
									useItem(player);
								}
	
								//Mushroom stew + Water = Witch Water!
								if(item.getItem() == Items.MUSHROOM_STEW || item.getItem() == NTMItems.mushroomSpores)
								{
									barrel.setMode(BarrelMode.SPORED);
									useItem(player);
								}
	
							} 
							
							if (barrel.fluid.getFluid() == FluidRegistry.LAVA)
							{
	
								//Glowstone + Lava = End Stone
								if(item.getItem() == Items.GLOWSTONE_DUST)
								{
									barrel.setMode(BarrelMode.ENDSTONE);
									useItem(player);
								}
								
							}
							
							if(barrel.fluid.getFluid() == NTMItems.fluidDemonWater){
								if(item.getItem() == Item.getItemFromBlock(NTMBlocks.netherSapling)){
									barrel.setMode(BarrelMode.OAK);
									useItem(player);
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

			ItemStack item = player.inventory.getCurrentItem();
			//Special cases
			if (item.getItem() == Items.MILK_BUCKET)
			{
				player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(Items.BUCKET, 1);
			}
			else if (item.getItem() == Items.MUSHROOM_STEW)
			{
				player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(Items.BOWL, 1);
			}
			//Generic case
			else
			{
				item.stackSize -= 1;

				if (item.stackSize == 0)
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

	private ItemStack getContainer(ItemStack item)
	{
		if (item.stackSize == 1) {
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
	public EnumBlockRenderType getRenderType(IBlockState state) {
		// TODO Auto-generated method stub
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityBarrel();
	}
	
	@Override
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
            ret.add(new ImmutablePair<Integer, String>(0, "type=normal"));
        return ret;
    }

}