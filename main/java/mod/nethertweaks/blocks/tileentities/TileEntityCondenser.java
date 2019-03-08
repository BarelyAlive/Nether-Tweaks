package mod.nethertweaks.blocks.tileentities;

import javax.annotation.Nullable;

import mod.nethertweaks.Config;
import mod.nethertweaks.INames;
import mod.nethertweaks.NTMBlocks;
import mod.nethertweaks.NTMItems;
import mod.nethertweaks.handler.NTMDryHandler;
import mod.nethertweaks.handler.RecipeHandler;
import mod.nethertweaks.vars.Dryable;
import mod.sfhcore.helper.FluidHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TileEntityCondenser extends TileNTMBase{
	
	public Item[] buckets = {Items.BUCKET, NTMItems.bucketStone, NTMItems.bucketWood};
	private boolean canDry = false;
	private int a = 0;
	
    public TileEntityCondenser() {
		super(2, Config.dryTimeCondenser);
		// TODO Auto-generated constructor stub
	}

	@Override
    public void update() {
    	checkInv();
		if(canDry){
			dry(inv[1].getItem(), inv[0].getItem());
		}
	}
	
	public void dry(Item material, Item bucket){
		if(!canDry){
			a = 0;
			workTime = 0;
		}
		if(canDry) {
			a = 0;
			a = inv[1].stackSize;
			checkHeatSource();
		}
		else {
			return;
		}
		if(a >= NTMDryHandler.getItem(material, material.getDamage(inv[1])).value) {
			if(canDry && workTime == 0) {
			int amount = NTMDryHandler.getItem(inv[1].getItem(), inv[1].getItemDamage()).value;
			decrStackSize(1, amount);
			}
			if(canDry) {
				workTime++;
				if(workTime == totalWorkTime)
				{
					if(bucket.equals(Items.BUCKET)){
						decrStackSize(0, 1);
						setInventorySlotContents(0, new ItemStack(Items.WATER_BUCKET, 1));
					}
					if(bucket == NTMItems.bucketStone){
						decrStackSize(0, 1);
						setInventorySlotContents(0, new ItemStack(NTMItems.bucketStoneWater, 1));
							
					}
					if(bucket == NTMItems.bucketWood){
						decrStackSize(0, 1);
						setInventorySlotContents(0, new ItemStack(NTMItems.bucketWoodWater, 1));
					}
					FluidHelper.fillContainer(inv[0], FluidRegistry.WATER, 1000);
					canDry = false;
					workTime = 0;
				}
			}
		}
	return;
	}
	
	public int checkHeatSource(){
		World world = getWorld();
		Block block = world.getBlockState(pos.add(0, -1, 0)).getBlock();
		if(Config.dryTimeCondenser != 2400)
			return totalWorkTime = Config.dryTimeCondenser;
		if(block.getDefaultState().getMaterial() == Material.FIRE){
			return totalWorkTime = 1800;
		}
		if(block.getDefaultState().getMaterial() == Material.LAVA){
			return totalWorkTime = 1200;
		}else {
			return totalWorkTime = 2400;
		}
	}
	
	public void checkInv(){
		boolean canDry1 = false;
		boolean canDry2 = false;
		canDry = false;
		if(inv[0] != null && inv[1] != null){
			if(inv[0].stackSize < 2){
				
				if(NTMDryHandler.containsItem(inv[1].getItem(), inv[1].getItemDamage())){
					int wert = NTMDryHandler.getItem(inv[1].getItem(), inv[1].getItemDamage()).value;
					if(inv[1].stackSize >= wert){
					canDry1 = true;
					}
				}else{
					canDry1 = false;
					canDry2 = false;
					return;
				}
				
				for (Item item : buckets){
					if (item == inv[0].getItem()){
						canDry2 = true;
						break;
					}
				}
				if(FluidHelper.isFillableContainerWithRoom(inv[0])){
					canDry2 = true;
				}
			}else
			{
				canDry = false;
			}
			if(canDry1 && canDry2){
				canDry = true;
			}else
			{
				canDry = false;
			}
			return;
		}
	}

}