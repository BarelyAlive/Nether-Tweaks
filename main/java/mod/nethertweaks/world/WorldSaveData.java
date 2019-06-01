package mod.nethertweaks.world;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
	public Map<UUID, PlayerPosition> spawnLocas = new HashMap<UUID, PlayerPosition>();
	
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
		float yaw, ang;
		NBTTagList nbtList = nbt.getTagList("NTM.Network", 10);
		
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
			
			spawnLocas.put(index, new PlayerPosition(new BlockPos(x, y, z), yaw, ang));			
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{	
		NBTTagList tagList = new NBTTagList();
		for(Map.Entry<UUID, PlayerPosition> entry : spawnLocas.entrySet()) {
			
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
		
		nbt.setTag("NTM.Network", tagList);
		
		return nbt;
	}
	
	public void setSpawnLocations(Map<UUID, PlayerPosition> spawnLocas)
	{
		this.spawnLocas = spawnLocas;
	}
	
	public Map<UUID, PlayerPosition> getSpawnLocations()
	{
		return this.spawnLocas;
	}
	
}
