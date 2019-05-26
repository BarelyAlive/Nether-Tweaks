package mod.nethertweaks.blocks.tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.mojang.realmsclient.dto.WorldDownload;

import mod.nethertweaks.world.WorldHandler;
import mod.nethertweaks.world.WorldSaveData;
import mod.nethertweaks.world.WorldSpawnLoc;
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

	private ArrayList<UUID> registeredPlayer;
	
	public TileBonfire()
	{
		this.registeredPlayer = new ArrayList<UUID>();
		
	}
	
	public void deleteSpawnLocationsIfDestroyed() {
		if (this.registeredPlayer.size() != 0)
		{
			for(UUID entry : this.registeredPlayer)
			{
				if (WorldSpawnLoc.spawnLocas.containsKey(entry))
				{
					EntityPlayer player = getPlayer(entry);
					
					if(world.isRemote)
						player.sendMessage(new TextComponentString(player.getName() + "'s point of rest is lost!"));
					
					WorldSpawnLoc.spawnLocas.remove(entry);
				}
			}
		}
	}
	
	private EntityPlayer getPlayer(UUID uuid)
	{
		return world.getPlayerEntityByUUID(uuid);
	}
	
	private UUID getUUID(EntityPlayer player) {
		return player.getUUID(player.getGameProfile());
	}
	
	public void setSpawnLocationForPlayer(EntityPlayer player, BlockPos pos) {
		this.registeredPlayer.add(getUUID(player));
		if(world.isRemote)
		    player.sendMessage(new TextComponentString(player.getName() + " rested at: " + player.getPosition() + "!"));
		
		addGlobalEntry(getUUID(player), new BlockPos(player));
				
		//player.bedLocation = new BlockPos(player);
	}
	
	public BlockPos getSpawnLocationForPlayer(EntityPlayer player)
	{
		if(WorldSpawnLoc.spawnLocas.containsKey(getUUID(player))) {
			return WorldSpawnLoc.spawnLocas.get(getUUID(player));
		}
		return null;
	}
	
	private void addGlobalEntry(UUID uuid, BlockPos pos)
	{
		WorldSpawnLoc.spawnLocas.put(uuid, pos);
	}
	
	private void removeGlobalEntry(UUID uuid, BlockPos pos)
	{
		WorldSpawnLoc.spawnLocas.remove(uuid, pos);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		NBTTagList tagList = new NBTTagList();
		for(UUID entry : this.registeredPlayer) {
			
			NBTTagCompound tag = new NBTTagCompound();
			
			tag.setLong("NTM.leastSignificantBits", entry.getLeastSignificantBits());
			tag.setLong("NTM.mostSignificantBits", entry.getMostSignificantBits());
			
			tagList.appendTag(tag);
			
		}	
		nbt.setTag("UserBonefireList", tagList);
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
		NBTTagList nbtList = nbt.getTagList("UserBonefireList", 10);
		
		for(int i = 0; i < nbtList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) nbtList.getCompoundTagAt(i);
			
			lBits = tag.getLong("NTM.leastSignificantBits");
			mBits = tag.getLong("NTM.mostSignificantBits");
			index = new UUID(mBits, lBits);
			
			this.registeredPlayer.add(index);
		}
	}
}
