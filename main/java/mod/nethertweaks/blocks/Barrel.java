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
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import mod.nethertweaks.BucketLoader;
import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tileentities.TileEntityBarrel;
import mod.nethertweaks.blocks.tileentities.TileEntityCondenser;
import mod.nethertweaks.blocks.tileentities.TileEntityBarrel.BarrelMode;
import mod.nethertweaks.blocks.tileentities.TileEntityBarrel.ExtractMode;
import mod.nethertweaks.handler.NTMCompostHandler;
import mod.nethertweaks.items.NTMItems;

public class Barrel extends BlockContainer
{	
	
	public Barrel() {
		super(Material.WOOD);
		//setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 1.0F, 0.9F);
		setUnlocalizedName(INames.BARREL);
		setResistance(10.0f);
		setHardness(2.0f);
		setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
		GameRegistry.registerTileEntity(TileEntityBarrel.class, INames.TEBARREL);
	}

	public Barrel(Material material) {
        super(material);  
    }
	
	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (player == null)
		{
			return false;
		}

		TileEntityBarrel barrel = (TileEntityBarrel) world.getTileEntity(pos);

		if (barrel.getMode().canExtract == ExtractMode.Always)
		{
			barrel.giveAppropriateItem();
		}
		else if (player.getHeldItemMainhand() != null)
		{
			ItemStack item = player.getHeldItemMainhand();
			if (item!=null)
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
					FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(item);
					//FILL
					if (fluid != null)
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
								barrel.fluid = new FluidStack(BucketLoader.fluidDemonWater, 1000);
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
						
						if(barrel.fluid.getFluid() == BucketLoader.fluidDemonWater){
							if(item.getItem() == Item.getItemFromBlock(NTMBlocks.blockNetherSapling)){
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

	public void useItem(EntityPlayer player)
	{
		if (!player.capabilities.isCreativeMode)
		{

			ItemStack item = player.inventory.mainInventory[player.inventory.currentItem];
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

	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		// TODO Auto-generated method stub
		return super.canRenderInLayer(state, layer);
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
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityBarrel();
	}
}