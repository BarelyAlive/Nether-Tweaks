package mod.nethertweaks.blocks.tileentities;

import javax.annotation.Nullable;

import mod.nethertweaks.BucketLoader;
import mod.nethertweaks.Config;
import mod.nethertweaks.INames;
import mod.nethertweaks.handler.NTMDryHandler;
import mod.sfhcore.helper.FluidHelper;
import mod.sfhcore.tileentities.TileEntityBase;
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

public class TileEntityCondenser extends TileEntityBase{
	
	public Item[] buckets = {Items.BUCKET, BucketLoader.bucketStone, BucketLoader.bucketWood};
	private boolean canDry = false;
	private int a = 0;
	
    public TileEntityCondenser(String field) {
		super(2, field);
		this.maxworkTime = Config.dryTimeCondenser;
	}

	@Override
    public void update() {
    	checkInv();
		if(canDry){
			dry(machineItemStacks.get(1).getItem(), machineItemStacks.get(0).getItem());
		}
	}
	
	public void dry(Item material, Item bucket){
		if(!canDry){
			a = 0;
			workTime = 0;
		}
		if(canDry) {
			a = 0;
			a = machineItemStacks.get(1).getCount();
			checkHeatSource();
		}
		else {
			return;
		}
		if(a >= NTMDryHandler.getItem(material, material.getDamage(machineItemStacks.get(1))).value) {
			if(canDry && workTime == 0) {
			int amount = NTMDryHandler.getItem(machineItemStacks.get(1).getItem(), machineItemStacks.get(1).getItemDamage()).value;
			decrStackSize(1, amount);
			}
			if(canDry) {
				workTime++;
				if(workTime == maxworkTime)
				{
					if(bucket.equals(Items.BUCKET)){
						decrStackSize(0, 1);
						setInventorySlotContents(0, new ItemStack(Items.WATER_BUCKET, 1));
					}
					if(bucket == BucketLoader.bucketStone){
						decrStackSize(0, 1);
						setInventorySlotContents(0, new ItemStack(BucketLoader.bucketStoneWater, 1));
							
					}
					if(bucket == BucketLoader.bucketWood){
						decrStackSize(0, 1);
						setInventorySlotContents(0, new ItemStack(BucketLoader.bucketWoodWater, 1));
					}
					FluidHelper.fillContainer(machineItemStacks.get(0), FluidRegistry.WATER, 1000);
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
			return maxworkTime = Config.dryTimeCondenser;
		if(block.getDefaultState().getMaterial() == Material.FIRE){
			return maxworkTime = 1800;
		}
		if(block.getDefaultState().getMaterial() == Material.LAVA){
			return maxworkTime = 1200;
		}else {
			return maxworkTime = 2400;
		}
	}
	
	public void checkInv(){
		boolean canDry1 = false;
		boolean canDry2 = false;
		canDry = false;
		if(machineItemStacks.get(0) != null && machineItemStacks.get(1) != null){
			if(machineItemStacks.get(0).getCount() < 2){
				
				if(NTMDryHandler.containsItem(machineItemStacks.get(1).getItem(), machineItemStacks.get(1).getItemDamage())){
					int wert = NTMDryHandler.getItem(machineItemStacks.get(1).getItem(), machineItemStacks.get(1).getItemDamage()).value;
					if(machineItemStacks.get(1).getCount() >= wert){
					canDry1 = true;
					}
				}else{
					canDry1 = false;
					canDry2 = false;
					return;
				}
				
				for (Item item : buckets){
					if (item == machineItemStacks.get(0).getItem()){
						canDry2 = true;
						break;
					}
				}
				if(FluidHelper.isFillableContainerWithRoom(machineItemStacks.get(0))){
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