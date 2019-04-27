package mod.nethertweaks.world;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.storage.*;

public class WorldSaveData extends WorldSavedData {
	
	//Strings
	public final static String key = "NTMWorldSaveData";
	
	//Bonfire
	public static Map<UUID, BlockPos> spawnLocas = new HashMap<UUID, BlockPos>();
	
	public WorldSaveData(String key) {
		super(key);
	}

	public WorldSaveData() {
		super(key);
	}
	
	public static WorldSaveData get(World world) {
		MapStorage storage = world.getMapStorage();
		WorldSaveData result;
		result = (WorldSaveData) storage.getOrLoadData(WorldSaveData.class, key);
		if(result == null) {
			result = new WorldSaveData();
			storage.setData(key, result);
		}
		return result;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		long lBits;
		long mBits;
		UUID index;
		int x, y, z;
		NBTTagList nbtList = nbt.getTagList("NTM.Network", 10);
		
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

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		
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
		
		nbt.setTag("NTM.Network", tagList);
		
		return nbt;
	}
	
}
