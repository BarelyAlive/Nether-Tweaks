package mod.nethertweaks.blocks.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.EnumFaceDirection;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import mod.nethertweaks.Compostable;
import mod.nethertweaks.blocks.*;
import mod.nethertweaks.handler.NTMCompostHandler;
import mod.nethertweaks.items.*;

public class TileEntityBarrel extends TileEntity implements  net.minecraftforge.fluids.capability.IFluidHandler, ISidedInventory{	
	private static final float MIN_RENDER_CAPACITY = 0.1f;
	private static final float MAX_RENDER_CAPACITY = 0.9f;
	private static final int MAX_COMPOSTING_TIME = 1000;
	private static final int MAX_FLUID = 1000;
	private static final int UPDATE_INTERVAL = 10;

	private static final int MOSS_SPREAD_X_POS = 2;
	private static final int MOSS_SPREAD_X_NEG = -2;
	private static final int MOSS_SPREAD_Y_POS = 2;
	private static final int MOSS_SPREAD_Y_NEG = -1;
	private static final int MOSS_SPREAD_Z_POS = 2;
	private static final int MOSS_SPREAD_Z_NEG = -2;

	public enum BarrelMode
	{
		EMPTY(0, ExtractMode.None), 
		FLUID(1, ExtractMode.None), 
		COMPOST(2, ExtractMode.None), 
		DIRT(3, ExtractMode.Always), 
		CLAY(4, ExtractMode.Always), 
		SPORED(5, ExtractMode.None), 
		SLIME(6, ExtractMode.Always), 
		ENDSTONE(7, ExtractMode.Always),
		MILKED(8, ExtractMode.None), 
		OBSIDIAN(9, ExtractMode.Always),
		COBBLESTONE(10, ExtractMode.Always),
		OAK(11, ExtractMode.Always);

		private BarrelMode(int v, ExtractMode extract){this.value = v; this.canExtract = extract;}
		public int value;
		public ExtractMode canExtract;
	}

	public enum ExtractMode
	{
		None,
		Always;
	}

	public FluidStack fluid;
	private float volume;
	private int timer;
	private BarrelMode mode;

	private boolean needsUpdate = false;
	private int updateTimer = 0;

	public BarrelMode getMode() {
		return mode;
	}
	public void setMode(BarrelMode mode) {
		this.mode = mode;
		this.needsUpdate = true;
	}

	public TileEntityBarrel()
	{
		setMode(BarrelMode.EMPTY);
		volume = 0;
		timer = 0;
		fluid = new FluidStack(FluidRegistry.WATER, 0);
	}

	public void update() {
		//XXX Barrel state logic.
				if (updateTimer >= UPDATE_INTERVAL)
				{
					updateTimer = 0;
					if (needsUpdate)
					{
						needsUpdate = false;
						worldObj.markBlockRangeForRenderUpdate(this.pos.getX(), this.pos.getY(), this.pos.getZ(), this.pos.getX(), this.pos.getY(), this.pos.getZ());
					}
				}
				else
				{
					updateTimer++;
				}

				switch(this.getMode())
				{
				case EMPTY:
					//Handle Rain
					if (!worldObj.isRemote && worldObj.isRaining() && this.pos.getY() >= worldObj.getBiomeForCoordsBody(pos).getRainfall())
					{
						fluid = new FluidStack(FluidRegistry.WATER, 0);
						setMode(BarrelMode.FLUID);
					}
					break;

				case FLUID:
					//WATER!
					if (fluid.getFluid() == FluidRegistry.WATER)
					{
						//Handle Rain
						if (!worldObj.isRemote && !isFull() && worldObj.isRaining() && this.pos.getY() >= worldObj.getTopSolidOrLiquidBlock(pos).getY() - 1 && worldObj.getBiomeForCoordsBody(pos).getRainfall() > 0.0f)
						{
							volume += worldObj.getBiomeForCoordsBody(pos).getRainfall() / (float)1000;

							if (volume > 1)
							{
								volume = 1;
							}

							fluid.amount = (int)(MAX_FLUID * volume);
							needsUpdate = true;
						}

						//Check for spores.
						if(!worldObj.isRemote && isFull() && worldObj.getBlockState(pos.add(0, -1, 0)) == Blocks.MYCELIUM.getDefaultState())
						{
							setMode(BarrelMode.SPORED);
							needsUpdate = true;
						}

						//Turn into cobblestone?
						if (isFull() && worldObj.getBlockState(pos.add(0, 1, 0)) == FluidRegistry.LAVA.getBlock())
						{
							setMode(BarrelMode.COBBLESTONE);
						}
						
						if(fluid.getFluid() == NTMItems.fluidDemonWater){
							if(worldObj.getBlockState(pos.add(0, 1, 0)) == FluidRegistry.LAVA.getBlock()){
								setMode(BarrelMode.COBBLESTONE);
							}
						}

						//Spread moss.
						if(!worldObj.isRemote && fluid.amount > 0 && worldObj.getBlockState(pos).getMaterial().getCanBurn() && worldObj.rand.nextInt(500) == 0)
						{
							int x = this.pos.getX() + (worldObj.rand.nextInt(MOSS_SPREAD_X_POS - MOSS_SPREAD_X_NEG + 1) + MOSS_SPREAD_X_NEG);
							int y = this.pos.getY() + (worldObj.rand.nextInt(MOSS_SPREAD_Y_POS - MOSS_SPREAD_Y_NEG + 1) + MOSS_SPREAD_Y_NEG);
							int z = this.pos.getZ() + (worldObj.rand.nextInt(MOSS_SPREAD_Z_POS - MOSS_SPREAD_Z_NEG + 1) + MOSS_SPREAD_Z_NEG);
							int lightLevel = worldObj.getBlockState(pos.add(0, 1, 0)).getLightValue(getWorld(), pos);

							if(!worldObj.isAirBlock(pos) && worldObj.getTopSolidOrLiquidBlock(pos).getY() > y && lightLevel >= 9 && lightLevel <= 11)
							{
								Block selected = worldObj.getBlockState(pos).getBlock();
								int meta = blockType.getMetaFromState(this.blockType.getDefaultState());

								if (selected == Blocks.STONEBRICK && meta == 0)
								{
									worldObj.setBlockState(pos, Blocks.STONEBRICK.getDefaultState(), 3);
									drain(100, true);
								}

								if (selected == Blocks.COBBLESTONE)
								{
									worldObj.setBlockState(pos, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 3);
									drain(100, true);
								}
							}
						}
					}

					//LAVA!
					if (fluid.getFluid() == FluidRegistry.LAVA)
					{
						//Burn the barrel it is flammable.
						if(worldObj.getBlockState(pos).getMaterial().getCanBurn())
						{
							timer++;
							if (timer % 30 == 0)
							{
								worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double)this.pos.getX() + Math.random(), (double)this.pos.getY() + 1.2D, (double)this.pos.getZ() + Math.random(), 0.0D, 0.0D, 0.0D);
							}

							if (timer % 5 == 0)
							{
								worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double)this.pos.getX() + Math.random(), (double)this.pos.getY() + 1.2D, (double)this.pos.getZ() + Math.random(), 0.0D, 0.0D, 0.0D);
							}

							if (timer >= 400)
							{
								timer = 0;
								if (fluid.amount < 1000)
								{
									//burn
									worldObj.setBlockState(pos.add(0, 2, 0), Blocks.FIRE.getDefaultState());
									return;
								}
								else
								{
									//spit lava on the ground
									worldObj.setBlockState(pos, Blocks.LAVA.getDefaultState(), 3);
									return;
								}	
							}
						}
						
						//Demon Water
						if(fluid.getFluid() == NTMItems.fluidDemonWater){
							needsUpdate = true;
							worldObj.markBlockRangeForRenderUpdate(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ());
						}
						
						//Turn into obsidian
						if (isFull() && worldObj.getBlockState(pos.add(0, 1, 0)) == FluidRegistry.WATER.getBlock())
						{
							setMode(BarrelMode.OBSIDIAN);
						}
						if (isFull() && worldObj.getBlockState(pos.add(0, 1, 0)) == NTMItems.blockDemonWater)
						{
							setMode(BarrelMode.OBSIDIAN);
						}
					}
					break;

				case COMPOST:
					if (volume >= 1.0F)
					{
						timer++;

						//Are we done yet?
						if(timer >= TileEntityBarrel.MAX_COMPOSTING_TIME)
						{
							setMode(BarrelMode.DIRT);
							timer = 0;
							worldObj.markBlockRangeForRenderUpdate(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ());
						}
					}
					break;

				case MILKED:
					timer++;

					if (isDone())
					{
						timer = 0;
						setMode(BarrelMode.SLIME);
						worldObj.markBlockRangeForRenderUpdate(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ());
					}
					break;

				case SPORED:
					int nearbyMYCELIUM = getNearbyBlocks(Blocks.MYCELIUM.getDefaultState());

					timer += 1 + (nearbyMYCELIUM / 2);

					if(!worldObj.isRemote && nearbyMYCELIUM > 0)
					{
						//Spawn Mushrooms
						for (int x = -2; x <= 2; x++)
						{
							for (int y = -1; y <= 1; y++)
							{
								for (int z = -2; z <= 2; z++)
								{
									if(worldObj.getBlockState(pos.add(x, y, z)) == Blocks.MYCELIUM && worldObj.isAirBlock(pos.add(x, y, z +1)) && worldObj.rand.nextInt(1500) == 0)
									{
										int choice = worldObj.rand.nextInt(2);

										if (choice == 0)
											worldObj.setBlockState(pos.add(x, y+1, z), Blocks.BROWN_MUSHROOM.getDefaultState(), 3);
										if (choice == 1)
											worldObj.setBlockState(pos.add(x, y+1, z), Blocks.RED_MUSHROOM.getDefaultState(), 3);
									}
								}
							}
						}
					}

				default:
					break;
				}
			}

			public boolean addCompostItem(Compostable item)
			{
				if (getMode() == BarrelMode.EMPTY)
				{
					setMode(BarrelMode.COMPOST);
					timer = 0;
				}

				if (getMode() == BarrelMode.COMPOST && volume < 1.0f)
				{
					volume += item.value;

					if (volume > 1.0f)
					{
						volume = 1.0f;
					}

					worldObj.markBlockRangeForRenderUpdate(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ());
					needsUpdate = true;
					return true;
				}
				else
				{
					return false;
				}
	}

	public boolean isFull()
	{
		if (volume >= 1.0f)
		{
			return true;
		}else
		{
			return false;
		}
	}

	public boolean isDone()
	{
		return timer >= MAX_COMPOSTING_TIME;
	}

	public void giveAppropriateItem()
	{
		giveItem(getExtractItem());
	}

	private void giveItem(ItemStack item)
	{
		if(!worldObj.isRemote)
		{
			EntityItem entityitem = new EntityItem(worldObj, (double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 1.5D, (double)this.pos.getZ() + 0.5D, item);

			double f3 = 0.05F;
			entityitem.motionX = worldObj.rand.nextGaussian() * f3;
			entityitem.motionY = (0.2d);
			entityitem.motionZ = worldObj.rand.nextGaussian() * f3;

			worldObj.spawnEntityInWorld(entityitem);

			timer = 0;
		}

		resetBarrel();
	}

	private ItemStack getExtractItem()
	{
		//XXX getExtractItem
		switch (getMode())
		{
		case CLAY:
			return new ItemStack(Blocks.CLAY, 1, 0);

		case DIRT:
			return new ItemStack(Blocks.DIRT, 1, 0);

		case ENDSTONE:
			return new ItemStack(Blocks.END_STONE, 1, 0);

		case SLIME:
			return new ItemStack(Items.SLIME_BALL, 1 + worldObj.rand.nextInt(4));

		case OBSIDIAN:
			return new ItemStack(Blocks.OBSIDIAN, 1, 0);

		case COBBLESTONE:
			return new ItemStack(Blocks.COBBLESTONE, 1, 0);

		case OAK:
			return new ItemStack(Blocks.SAPLING, 1, worldObj.rand.nextInt(6));
			
		default:
			return null;
		}
	}
	
	public float getVolume() {
		return volume;
	}
	
	public int getTimer() {
		return timer;
	}

	public float getAdjustedVolume()
	{
		float capacity = MAX_RENDER_CAPACITY - MIN_RENDER_CAPACITY;
		float adjusted = volume * capacity;		
		adjusted += MIN_RENDER_CAPACITY;
		return adjusted;
	}

	private void resetBarrel()
	{
		fluid = new FluidStack(FluidRegistry.WATER, 0);
		volume = 0;
		setMode(BarrelMode.EMPTY);
		needsUpdate = true;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		switch (compound.getInteger("mode"))
		{
		case 0:
			setMode(BarrelMode.EMPTY);
			break;

		case 1:
			setMode(BarrelMode.FLUID);
			break;

		case 2:
			setMode(BarrelMode.COMPOST);
			break;

		case 3:
			setMode(BarrelMode.DIRT);
			break;	

		case 4:
			setMode(BarrelMode.CLAY);
			break;

		case 5:
			setMode(BarrelMode.SPORED);
			break;

		case 6:
			setMode(BarrelMode.SLIME);
			break;	

		case 7:
			setMode(BarrelMode.ENDSTONE);
			break;	

		case 8:
			setMode(BarrelMode.MILKED);
			break;	

		case 9:
			setMode(BarrelMode.OBSIDIAN);
			break;

		case 10:
			setMode(BarrelMode.COBBLESTONE);
			break;
			
		case 11:
			setMode(BarrelMode.OAK);
			break;
			
		}

		volume = compound.getFloat("volume");
		timer = compound.getInteger("timer");
		
		fluid = new FluidStack(FluidRegistry.getFluid(compound.getShort("fluid")), (int)(volume * MAX_FLUID));
		needsUpdate = true;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger("mode", getMode().value);
		compound.setFloat("volume", volume);
		compound.setInteger("timer", timer);
		compound.setString("fluid", fluid.getFluid().toString());
		return compound;
	}



	//IFluidHandler!

	public int getNearbyBlocks(IBlockState block)
	{
		int count = 0;

		for (int x = -1; x <= 1; x++)
		{
			for (int y = -1; y <= 1; y++)
			{
				for (int z = -1; z <= 1; z++)
				{
					if(worldObj.getBlockState(pos.add(x, y, z)) == block)
					{
						count++;
					}
				}
			}
		}

		return count;
	}

	public int getLightLevel()
	{
		if (getMode() == BarrelMode.FLUID)
		{
			return fluid.getFluid().getLuminosity();
		}

		return 0;
	}



	//ISidedInventory!
	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (slot == 0)
		{
			return getExtractItem();
		}

		return null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (slot == 0)
		{
			ItemStack item = getExtractItem();

			resetBarrel();
			return item;
		}

		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		//XXX addItemFromPipe
		Item item = stack.getItem();
		int meta = stack.getItemDamage();
		
		if (slot == 0)
		{
			if (item == null)
			{
				resetBarrel();
			}
		}

		if (slot == 1)
		{
			if (getMode() == BarrelMode.COMPOST || getMode() == BarrelMode.EMPTY)
			{
				if(NTMCompostHandler.containsItem(item, meta))
				{
					this.addCompostItem(NTMCompostHandler.getItem(item, meta));
				}
			}

			if(getMode() == BarrelMode.FLUID && this.isFull())
			{
				if(fluid.getFluid() == FluidRegistry.WATER)
				{
					if (Block.getBlockFromItem(item) == NTMBlocks.blockDust)
					{
						setMode(BarrelMode.CLAY);
					}
				}

				if(fluid.getFluid() == FluidRegistry.LAVA)
				{

					if (item == Items.GLOWSTONE_DUST)
					{
						setMode(BarrelMode.ENDSTONE);
					}
					
				}
				
			}
		}

		worldObj.markBlockRangeForRenderUpdate(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {	
		if (slot == 1)
		{
			return isItemValid(item);
		}

		return false;
	}

	public boolean isItemValid(ItemStack stack)
	{
		///XXX isItemValid
		Item item = stack.getItem();
		int meta = stack.getItemDamage();
		
		if (!this.isFull() && getMode() == BarrelMode.COMPOST || getMode() == BarrelMode.EMPTY)
		{
			if(NTMCompostHandler.containsItem(item, meta))
			{
				return true;
			}
		}

		if(getMode() == BarrelMode.FLUID && this.isFull())
		{
			if(fluid.getFluid() == FluidRegistry.WATER)
			{
				if (Block.getBlockFromItem(item) == NTMBlocks.blockDust)
				{
					return true;
				}
			}


			if(fluid.getFluid() == FluidRegistry.LAVA)
			{
				if (item == Items.REDSTONE)
				{
					return true;
				}

				if (item == Items.GLOWSTONE_DUST)
				{
					return true;
				}
				
			}
			
		}

		return false;
	}
	//HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
	@Override
	public ItemStack removeStackFromSlot(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void openInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void closeInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean hasCustomName() {
		return false;
	}
	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		if (side == EnumFacing.UP)
		{
			return new int[]{0};
		}else if (side == EnumFacing.DOWN)
		{
			return new int[]{1};
		}

		return new int[0];
	}
	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		if (direction == EnumFacing.UP && index == 1)
		{
			return isItemValid(itemStackIn);
		}

		return false;
	}
	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		if (direction == EnumFacing.UP && index == 0)
		{
			if (getMode().canExtract == ExtractMode.Always)
			{
				return true;
			}

		}

		return false;
	}
	@Override
	public IFluidTankProperties[] getTankProperties() {
		IFluidTankProperties[] prop = new IFluidTankProperties[fluid.amount];
		return prop;
	}
	@Override
	public int fill(FluidStack resource, boolean doFill) {
		//Simulate the fill to see if there is room for incoming liquids.
				int capacity = MAX_FLUID - fluid.amount;

				if (!doFill)
				{
					if (getMode() == BarrelMode.EMPTY)
					{
						return resource.amount;
					}

					if (getMode() == BarrelMode.FLUID && resource.getFluid() == fluid.getFluid())
					{
						if (capacity >= resource.amount)
						{
							return resource.amount;
						}else
						{
							return capacity;
						}
					}
				}else
					//Really fill the barrel.
				{
					if (getMode() == BarrelMode.EMPTY)
					{
						if (resource.getFluid() != fluid.getFluid())
						{
							fluid =  new FluidStack(resource.getFluid(),resource.amount);
						}else
						{
							fluid.amount = resource.amount;
						}
						setMode(BarrelMode.FLUID);
						volume = (float)fluid.amount / (float)MAX_FLUID;
						worldObj.markBlockRangeForRenderUpdate(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ());
						//needsUpdate = true;
						return resource.amount;
					}

					if (getMode() == BarrelMode.FLUID && resource.getFluid() == fluid.getFluid())
					{
						if (capacity >= resource.amount)
						{
							fluid.amount += resource.amount;
							volume = (float)fluid.amount / (float)MAX_FLUID;
							//worldObj.markBlockForUpdate(this.pos.getX(), this.pos.getY(), this.pos.getZ());
							needsUpdate = true;
							return resource.amount;
						}else
						{
							fluid.amount = MAX_FLUID;
							volume = 1.0f;
							worldObj.markBlockRangeForRenderUpdate(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ());
							//needsUpdate = true;
							return capacity;
						}
					}
				}

				return 0;
	}
	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if (resource == null || getMode() != BarrelMode.FLUID || !resource.isFluidEqual(fluid))
			return null;

		if (!doDrain)
		{
			if (fluid.amount >= resource.amount)
			{
				FluidStack simulated = new FluidStack(resource.getFluid(),resource.amount);
				return simulated;
			}else
			{
				FluidStack simulated = new FluidStack(resource.getFluid(),fluid.amount);
				return simulated;
			}
		}else
		{
			if (fluid.amount > resource.amount)
			{
				FluidStack drained = new FluidStack(resource.getFluid(),resource.amount);
				fluid.amount -= resource.amount;
				volume = (float)fluid.amount / (float)MAX_FLUID;
				//worldObj.markBlockForUpdate(this.pos.getX(), this.pos.getY(), this.pos.getZ());
				needsUpdate = true;
				//System.out.println("A Server: " + worldObj.isRemote + ". Amount: " + fluid.amount);
				return drained;
			}else
			{
				FluidStack drained = new FluidStack(resource.getFluid(),fluid.amount);
				fluid.amount = 0;
				volume = 0;
				setMode(BarrelMode.EMPTY);
				timer = 0;
				//worldObj.markBlockForUpdate(this.pos.getX(), this.pos.getY(), this.pos.getZ());
				needsUpdate = true;
				//System.out.println("A Server: " + worldObj.isRemote + ". Amount: " + fluid.amount);
				return drained;
			}
		}
	}
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		if (getMode() != BarrelMode.FLUID)
			return null;

		if (!doDrain)
		{
			if (fluid.amount >= maxDrain)
			{
				FluidStack simulated = new FluidStack(fluid.getFluid(),maxDrain);
				return simulated;
			}else
			{
				FluidStack simulated = new FluidStack(fluid.getFluid(),fluid.amount);
				return simulated;
			}
		}else
		{
			if (fluid.amount > maxDrain)
			{
				FluidStack drained = new FluidStack(fluid.getFluid(),maxDrain);
				fluid.amount -= maxDrain;
				volume = (float)fluid.amount / (float)MAX_FLUID;
				//worldObj.markBlockForUpdate(this.pos.getX(), this.pos.getY(), this.pos.getZ());
				needsUpdate = true;
				//System.out.println("B Server: " + worldObj.isRemote + ". Amount: " + fluid.amount);
				return drained;
			}else
			{
				FluidStack drained = new FluidStack(fluid.getFluid(),fluid.amount);
				fluid.amount = 0;
				volume = 0;
				setMode(BarrelMode.EMPTY);
				timer = 0;
				//worldObj.markBlockForUpdate(this.pos.getX(), this.pos.getY(), this.pos.getZ());
				needsUpdate = true;
				//System.out.println("B Server: " + worldObj.isRemote + ". Amount: " + fluid.amount);
				return drained;
			}
		}
	}
	
	
}