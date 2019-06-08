package mod.nethertweaks.world;

import java.util.*;

import net.minecraft.entity.player.EntityPlayer;

public class BonfireInfo {
	private String name;
	private boolean seeable;
	private List<UUID> lastSpawnPlayer;
	
	public BonfireInfo()
	{
		this("");
	}
	
	public BonfireInfo(String name)
	{
		this(name, false);
	}
	
	public BonfireInfo(String name, boolean seeable)
	{
		this(name, seeable, null);
	}
	
	public BonfireInfo(String name, boolean seeable, List<UUID> lastSpawnPlayer)
	{
		this.name = name;
		this.seeable = seeable;
		if (lastSpawnPlayer == null)
			this.lastSpawnPlayer = new ArrayList<UUID>();
		else
			this.lastSpawnPlayer = lastSpawnPlayer;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void isPublic(boolean seeable)
	{
		this.seeable = seeable;
	}
	
	public boolean isPublic()
	{
		return this.seeable;
	}
	
	public void setLastPlayerSpawn(List<UUID> lastSpawnPlayer)
	{
		this.lastSpawnPlayer = lastSpawnPlayer;
	}
	
	public List<UUID> getLastPlayerSpawn()
	{
		return this.lastSpawnPlayer;
	}

	public void addPlayer(EntityPlayer player)
	{
		this.addPlayer(player.getUUID(player.getGameProfile()));
	}
	
	public void addPlayer(UUID player)
	{
		if(!this.lastSpawnPlayer.contains(player))
		{
			this.lastSpawnPlayer.add(player);
		}
	}
	
	public void removePlayer(EntityPlayer player)
	{
		this.removePlayer(player.getUUID(player.getGameProfile()));
	}
	
	public void removePlayer(UUID player)
	{
		int index = this.lastSpawnPlayer.indexOf(player);
		if (index == -1) return;
		this.lastSpawnPlayer.remove(index);
	}
	
	public boolean hasPlayer(EntityPlayer player)
	{
		return this.hasPlayer(player.getUUID(player.getGameProfile()));
	}
	
	public boolean hasPlayer(UUID player)
	{
		return (this.lastSpawnPlayer.indexOf(player) != -1);
	}
	
	public BonfireInfo copy()
	{
		BonfireInfo bi = new BonfireInfo();
		bi.setName(this.getName());
		bi.isPublic(this.isPublic());
		bi.setLastPlayerSpawn(this.getLastPlayerSpawn());
		return bi;
	}
	
}
