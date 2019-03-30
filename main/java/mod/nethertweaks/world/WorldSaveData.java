package mod.nethertweaks.world;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.*;
import net.minecraft.world.storage.*;

public class WorldSaveData extends WorldSavedData {
	
	public final static String key = "NTMWorldSaveData";
	
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
		String key;
		NBTTagList nbtList = nbt.getTagList("InM.Network", 10);
		
		for(int i = 0; i < nbtList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) nbtList.getCompoundTagAt(i);
			key = tag.getString("InM.network_name");
			this.current_network.put(key, tag.getLong("InM.current_network"));
			this.maximal_network.put(key, tag.getLong("InM.maximal_network"));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		
		NBTTagList tagList = new NBTTagList();
		
		for(String key : this.maximal_network.keySet()) {
			
			NBTTagCompound tag = new NBTTagCompound();
			
			if(this.maximal_network.get(key) == 0) {
				tag.setLong("InM.maximal_network", (long)0);
			} else {
				tag.setLong("InM.maximal_network", this.maximal_network.get(key));
			}
			
			if(this.current_network.containsKey(key)) {
				if(this.current_network.get(key) == 0) {
					tag.setLong("InM.current_network", (long)0);
				} else {
					tag.setLong("InM.current_network", this.current_network.get(key));
				}
			} else {
				tag.setLong("InM.current_network", (long)0);
			}
			
			tag.setString("InM.network_name", key);
			
			tagList.appendTag(tag);
			
		}
		
		nbt.setTag("InM.Network", tagList);
		
		return nbt;
	}
	
	public Map<String, Long> getMaximalNetwork() {
		return this.maximal_network;
	}
	
	public void setMaximalNetwork(Map<String, Long> network) {
		this.maximal_network = network;
		this.markDirty();
	}

	public Map<String, Long> getCurrentNetwork() {
		return this.current_network;
	}
	
	public void setCurrentNetwork(Map<String, Long> network) {
		this.current_network = network;
		this.markDirty();
	}

}
