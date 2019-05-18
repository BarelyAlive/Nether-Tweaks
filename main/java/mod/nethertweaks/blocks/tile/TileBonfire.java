package mod.nethertweaks.blocks.tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.world.WorldHandler;
import mod.nethertweaks.world.WorldSaveData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import scala.collection.script.Remove;

public class TileBonfire extends TileEntity {

	private Map<UUID, BlockPos> spawnLocs;
	
	public TileBonfire()
	{
		this.spawnLocs = new HashMap<UUID, BlockPos>();
		
	}
	
	public void deleteSpawnLocationsIfDestroyed() {
		/*
		for(Map.Entry<UUID, BlockPos> entry : spawnLocs.entrySet())
		{
			EntityPlayer player = getPlayer(entry.getKey());
			
			int x = player.getEntityData().getInteger(WorldHandler.coodX);
			int y = player.getEntityData().getInteger(WorldHandler.coodY);
			int z = player.getEntityData().getInteger(WorldHandler.coodZ);
			
			player.bedLocation = new BlockPos(x, y, z);
			
			for(Map.Entry<UUID, BlockPos> entryG : WorldSaveData.spawnLocas.entrySet()) {
				if(entry == entryG) {
					removeGlobalEntry(entry.getKey(), entry.getValue());
				}
			}
			
			if(world.isRemote)
				player.sendMessage(new TextComponentString(player.getName() + "'s point of rest is lost!"));
		}
				*/
	
		this.spawnLocs.clear();
	}
	
	private EntityPlayer getPlayer(UUID uuid)
	{
		return world.getPlayerEntityByUUID(uuid);
	}
	
	private UUID getUUID(EntityPlayer player) {
		return player.getUUID(player.getGameProfile());
	}
	
	public void setSpawnLocationForPlayer(EntityPlayer player, BlockPos pos) {
		this.spawnLocs.put(getUUID(player), new BlockPos(player));
		if(world.isRemote)
		    player.sendMessage(new TextComponentString(player.getName() + " rested at: " + player.getPosition() + "!"));
		
		addGlobalEntry(getUUID(player), new BlockPos(player));
				
		//player.bedLocation = new BlockPos(player);
	}
	
	public BlockPos getSpawnLocationForPlayer(EntityPlayer player)
	{
		if(this.spawnLocs.containsKey(getUUID(player))) {
			return this.spawnLocs.get(getUUID(player));
		}
		return null;
	}
	
	private void addGlobalEntry(UUID uuid, BlockPos pos)
	{
		WorldSaveData.spawnLocas.put(uuid, pos);
	}
	
	private void removeGlobalEntry(UUID uuid, BlockPos pos)
	{
		WorldSaveData.spawnLocas.remove(uuid, pos);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		NBTTagList tagList = new NBTTagList();
		for(Map.Entry<UUID, BlockPos> entry : WorldSaveData.spawnLocas.entrySet()) {
			
			NBTTagCompound tag = new NBTTagCompound();
			
			tag.setLong("NTM.leastSignificantBits", entry.getKey().getLeastSignificantBits());
			tag.setLong("NTM.mostSignificantBits", entry.getKey().getMostSignificantBits());
			
			tag.setInteger("NTM.PosX", entry.getValue().getX());
			tag.setInteger("NTM.PosY", entry.getValue().getY());
			tag.setInteger("NTM.PosZ", entry.getValue().getZ());
			
			tagList.appendTag(tag);
			
		}	
		nbt.setTag("BonFireList", tagList);
		return super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		long lBits;
		long mBits;
		UUID index;
		int x, y, z;
		NBTTagList nbtList = nbt.getTagList("BonFireList", 10);
		
		for(int i = 0; i < nbtList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) nbtList.getCompoundTagAt(i);
			
			lBits = tag.getLong("NTM.leastSignificantBits");
			mBits = tag.getLong("NTM.mostSignificantBits");
			index = new UUID(mBits, lBits);
			
			x = tag.getInteger("NTM.PosX");
			y = tag.getInteger("NTM.PosY");
			z = tag.getInteger("NTM.PosZ");
			
			WorldSaveData.spawnLocas.put(index, new BlockPos(x, y, z));			
		}
	}
}
