package mod.nethertweaks.blocks.tileentities;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import mod.nethertweaks.handler.NTMSieveHandler;
import mod.nethertweaks.vars.SieveReward;

public class TileEntitySieve extends TileEntity{
	private static final float MIN_RENDER_CAPACITY = 0.70f;
	private static final float MAX_RENDER_CAPACITY = 0.9f;
	private static final float PROCESSING_INTERVAL = 0.075f;
	private static final int UPDATE_INTERVAL = 20;

	public Block content;
	public int contentMeta = 0;

	private float volume = 0;
	public SieveMode mode = SieveMode.EMPTY;

	private int timer = 0;
	private boolean update = false;
	private boolean particleMode = false;
	private int timesClicked = 0;

	public enum SieveMode
	{EMPTY(0), FILLED(1);
	private SieveMode(int v){this.value = v;}
	public int value;
	}

	public TileEntitySieve()
	{
		mode = SieveMode.EMPTY;
	}

	public void addSievable(Block block, int blockMeta)
	{
		this.content = block;
		this.contentMeta = blockMeta;

		this.mode = SieveMode.FILLED;

		volume = 1.0f;
		worldObj.markBlockRangeForRenderUpdate(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), this.getPos().getX(), this.getPos().getY(), this.getPos().getZ());
	}

	@Override
	public void updateContainingBlockInfo() {
		timer++;
		if (timer >= UPDATE_INTERVAL)
		{
			timesClicked = 0;
			
			timer = 0;

			if (update)
			{
				update();
			}
		}
		super.updateContainingBlockInfo();
	}

	public void ProcessContents(boolean creative)
	{	
		if (creative)
		{
			volume = 0;
		}else
		{
			timesClicked++;
			if (timesClicked <= 6)
			{
				volume -= PROCESSING_INTERVAL;
				timesClicked = 0;
			}
		}

		if (volume <= 0)
		{
			mode = SieveMode.EMPTY;
			//give rewards!
			if (!worldObj.isRemote)
			{
				ArrayList<SieveReward> rewards = NTMSieveHandler.getRewards(content, contentMeta);
				if (rewards.size() > 0)
				{
					Iterator<SieveReward> it = rewards.iterator();
					while(it.hasNext())
					{
						SieveReward reward = it.next();

						if (worldObj.rand.nextInt(reward.rarity) == 0)
						{
							EntityItem entityitem = new EntityItem(worldObj, (double)this.getPos().getX() + 0.5D, (double)this.getPos().getY() + 1.5D, (double)this.getPos().getZ() + 0.5D, new ItemStack(reward.item, 1, reward.meta));

							double f3 = 0.05F;
							entityitem.motionX = worldObj.rand.nextGaussian() * f3;
							entityitem.motionY = (0.2d);
							entityitem.motionZ = worldObj.rand.nextGaussian() * f3;

							worldObj.spawnEntityInWorld(entityitem);

						}
					}
				}
			}
		}

		update = true;
	}
	
	public float getVolume() {
		return volume;
	}

	public float getAdjustedVolume()
	{
		float capacity = MAX_RENDER_CAPACITY - MIN_RENDER_CAPACITY;
		float adjusted = volume * capacity;		
		adjusted += MIN_RENDER_CAPACITY;
		return adjusted;
	}

	private void update()
	{
		update = false;
		worldObj.markBlockRangeForRenderUpdate(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), this.getPos().getX(), this.getPos().getY(), this.getPos().getZ());
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		switch (compound.getInteger("mode"))
		{
		case 0:
			mode = SieveMode.EMPTY;
			break;

		case 1:
			mode = SieveMode.FILLED;
			break;
		}
		if(!compound.getString("content").equals("")) {
			content = (Block)Block.REGISTRY.getObject(new ResourceLocation("content"));
		}else{
			content = null;
		}
		contentMeta = compound.getInteger("contentMeta");
		volume = compound.getFloat("volume");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger("mode", mode.value);
		
		if(content == null) {
			compound.setString("content", "");
		}else{
			compound.setString("content", Block.REGISTRY.getNameForObject(content).toString());
		}
		compound.setInteger("contentMeta", contentMeta);
		compound.setFloat("volume", volume);
		return compound;
	}

}