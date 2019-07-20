package mod.nethertweaks.world;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public class BonfireInfo {
	
	private int dim;
	private String name;
	private boolean seeable;
	private UUID owner;
	private List<UUID> lastSpawnPlayer;
	private BlockPos spawnPos;
	
	public BonfireInfo(UUID owner, int dim)
	{
		this("", owner, dim);
	}
	
	public BonfireInfo(String name, UUID owner, int dim)
	{
		this(name, true, owner, dim);
	}
	
	public BonfireInfo(String name, boolean seeable, UUID owner, int dim)
	{
		this(name, seeable, owner, null, dim);
	}
	
	public BonfireInfo(String name, boolean seeable, UUID owner, List<UUID> lastSpawnPlayer, int dim)
	{
		this.name = name;
		this.seeable = seeable;
		this.dim = dim;
		this.owner = owner;
		this.spawnPos = null;
		if (lastSpawnPlayer == null)
			this.lastSpawnPlayer = new ArrayList<UUID>();
		else
			this.lastSpawnPlayer = lastSpawnPlayer;
	}
	
	public void setOwner(UUID owner)
	{
		this.owner = owner;
	}
	
	public UUID getOwner()
	{
		return this.owner;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setSpawnPos(BlockPos pos)
	{
		this.spawnPos = pos;
	}
	
	public BlockPos getSpawnPos()
	{
		return this.spawnPos;
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
		this.addPlayer(EntityPlayer.getUUID(player.getGameProfile()));
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
		this.removePlayer(EntityPlayer.getUUID(player.getGameProfile()));
	}
	
	public void removePlayer(UUID player)
	{
		int index = this.lastSpawnPlayer.indexOf(player);
		if (index == -1) return;
		this.lastSpawnPlayer.remove(index);
	}
	
	public boolean hasPlayer(EntityPlayer player)
	{
		return this.hasPlayer(EntityPlayer.getUUID(player.getGameProfile()));
	}
	
	public boolean hasPlayer(UUID player)
	{
		return (this.lastSpawnPlayer.indexOf(player) != -1);
	}
	
	public int getDimension()
	{
		return this.dim;
	}
	
	public void setDimension(int dim)
	{
		this.dim = dim;
	}
	
	public BonfireInfo copy()
	{
		BonfireInfo bi = new BonfireInfo(this.owner, this.dim);
		bi.setName(this.getName());
		bi.isPublic(this.isPublic());
		bi.setLastPlayerSpawn(this.getLastPlayerSpawn());
		return bi;
	}
	
}
