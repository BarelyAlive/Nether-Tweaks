package mod.nethertweaks.world;

import java.util.*;

import forestry.core.utils.NBTUtilForestry.NBTList;
import mod.sfhcore.vars.PlayerPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.storage.*;

public class WorldSaveData extends WorldSavedData {
	
	//Strings
	public final static String key = "ntm.world_save_data";
	
	//Bonfire
	public Map<UUID, PlayerPosition> lastSpawnLocas = new HashMap<UUID, PlayerPosition>();
	public Map<BlockPos, BonfireInfo> bonfire_info = new HashMap<BlockPos, BonfireInfo>();
	
	public WorldSaveData(String key) {
		super(key);
	}

	public WorldSaveData() {
		super(key);
	}
	
	public static WorldSaveData get(World world) {
		MapStorage storage = world.getPerWorldStorage();
		WorldSaveData result;
		result = (WorldSaveData) storage.getOrLoadData(WorldSaveData.class, key);
		if(result == null) {
			result = new WorldSaveData();
			storage.setData(key, result);
		}
		return result;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		long lBits;
		long mBits;
		UUID index;
		int x, y, z;
		boolean is_public;
		String name;
		float yaw, ang;
		List<UUID> player_list = null;
		NBTTagList nbtList = nbt.getTagList("NTM.Network", 10);
		NBTTagList bonfireNbtList = nbt.getTagList("NTM.Bonfires", 10);
		
		for(int i = 0; i < nbtList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) nbtList.getCompoundTagAt(i);
			
			lBits = tag.getLong("NTM.leastSignificantBits");
			mBits = tag.getLong("NTM.mostSignificantBits");
			index = new UUID(mBits, lBits);
			
			x = tag.getInteger("NTM.PosX");
			y = tag.getInteger("NTM.PosY");
			z = tag.getInteger("NTM.PosZ");
			
			yaw = tag.getFloat("NTM.Yaw");
			ang = tag.getFloat("NTM.Ang");
			
			lastSpawnLocas.put(index, new PlayerPosition(new BlockPos(x, y, z), yaw, ang));			
		}
		
		for(int i = 0; i < bonfireNbtList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) bonfireNbtList.getCompoundTagAt(i);
			
			NBTTagList nbt_player_list = tag.getTagList("NTM.UUIDs", 10);
			
			player_list = new ArrayList<UUID>();
			
			for(int j = 0; j < nbt_player_list.tagCount(); j++)
			{
				NBTTagCompound player_tag = (NBTTagCompound) nbt_player_list.getCompoundTagAt(i);
				lBits = player_tag.getLong("NTM.leastSignificantBits");
				mBits = player_tag.getLong("NTM.mostSignificantBits");
				index = new UUID(mBits, lBits);
				player_list.add(index);
			}
			
			is_public = tag.getBoolean("NTM.seeable");
			name = tag.getString("NTM.Name");
			
			long ownerlBits = tag.getLong("NTM.ownerLeastSignificantBits");
			long ownermBits = tag.getLong("NTM.ownerMostSignificantBits");
		
			UUID owner = new UUID(ownermBits, ownerlBits);
			
			x = tag.getInteger("NTM.PosX");
			y = tag.getInteger("NTM.PosY");
			z = tag.getInteger("NTM.PosZ");
			
			bonfire_info.put(new BlockPos(x, y, z), new BonfireInfo(name, is_public, owner, player_list));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{	
		NBTTagList tagList = new NBTTagList();
		for(Map.Entry<UUID, PlayerPosition> entry : lastSpawnLocas.entrySet()) {
			
			NBTTagCompound tag = new NBTTagCompound();
			
			tag.setLong("NTM.leastSignificantBits", entry.getKey().getLeastSignificantBits());
			tag.setLong("NTM.mostSignificantBits", entry.getKey().getMostSignificantBits());
			
			tag.setInteger("NTM.PosX", entry.getValue().getPos().getX());
			tag.setInteger("NTM.PosY", entry.getValue().getPos().getY());
			tag.setInteger("NTM.PosZ", entry.getValue().getPos().getZ());
			
			tag.setFloat("NTM.Yaw", entry.getValue().getYaw());
			tag.setFloat("NTM.Ang", entry.getValue().getAng());
			
			tagList.appendTag(tag);
			
		}
		
		nbt.setTag("NTM.Network", tagList.copy());
		
		tagList = new NBTTagList();
		
		for(Map.Entry<BlockPos, BonfireInfo> entry : this.bonfire_info.entrySet())
		{
			NBTTagCompound tag = new NBTTagCompound();
			
			tag.setInteger("NTM.PosX", entry.getKey().getX());
			tag.setInteger("NTM.PosY", entry.getKey().getY());
			tag.setInteger("NTM.PosZ", entry.getKey().getZ());
			
			tag.setLong("NTM.ownerLeastSignificantBits", entry.getValue().getOwner().getLeastSignificantBits());
			tag.setLong("NTM.ownermostSignificantBits", entry.getValue().getOwner().getMostSignificantBits());
			
			tag.setString("NTM.Name", entry.getValue().getName());
			tag.setBoolean("NTM.seeable", entry.getValue().isPublic());
			
			NBTTagList list = new NBTTagList();
			
			for(UUID uuid : entry.getValue().getLastPlayerSpawn())
			{
				NBTTagCompound player_tag = new NBTTagCompound();
				player_tag.setLong("NTM.leastSignificantBits", uuid.getLeastSignificantBits());
				player_tag.setLong("NTM.mostSignificantBits", uuid.getMostSignificantBits());
				list.appendTag(player_tag);
			}
			
			nbt.setTag("NTM.UUIDs", list);
			
			tagList.appendTag(tag);
			
		}
		
		nbt.setTag("NTM.Bonfires", tagList.copy());
		
		return nbt;
	}
	
	public void setLastSpawnLocations(Map<UUID, PlayerPosition> spawnLocas)
	{
		this.lastSpawnLocas = spawnLocas;
	}
	
	public Map<UUID, PlayerPosition> getLastSpawnLocations()
	{
		return this.lastSpawnLocas;
	}
	
	public void setBonfireInfo(Map<BlockPos, BonfireInfo> bonefire_info)
	{
		this.bonfire_info = bonefire_info;
	}
	
	public Map<BlockPos, BonfireInfo> getBonfireInfo()
	{
		return this.bonfire_info;
	}

}
