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

	public BonfireInfo(final UUID owner, final int dim)
	{
		this("", owner, dim);
	}

	public BonfireInfo(final String name, final UUID owner, final int dim)
	{
		this(name, true, owner, dim);
	}

	public BonfireInfo(final String name, final boolean seeable, final UUID owner, final int dim)
	{
		this(name, seeable, owner, null, dim);
	}

	public BonfireInfo(final String name, final boolean seeable, final UUID owner, final List<UUID> lastSpawnPlayer, final int dim)
	{
		this(name, seeable, owner, lastSpawnPlayer, null, dim);
	}

	public BonfireInfo(final String name, final boolean seeable, final UUID owner, final List<UUID> lastSpawnPlayer, final BlockPos spawnPos, final int dim)
	{
		this.name = name;
		this.seeable = seeable;
		this.dim = dim;
		this.owner = owner;
		this.spawnPos = spawnPos;
		if (lastSpawnPlayer == null)
			this.lastSpawnPlayer = new ArrayList<>();
		else
			this.lastSpawnPlayer = lastSpawnPlayer;
	}

	public void setOwner(final UUID owner)
	{
		this.owner = owner;
	}

	public UUID getOwner()
	{
		return owner;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setSpawnPos(final BlockPos pos)
	{
		spawnPos = pos;
	}

	public BlockPos getSpawnPos()
	{
		return spawnPos;
	}

	public void isPublic(final boolean seeable)
	{
		this.seeable = seeable;
	}

	public boolean isPublic()
	{
		return seeable;
	}

	public void setLastPlayerSpawn(final List<UUID> lastSpawnPlayer)
	{
		this.lastSpawnPlayer = lastSpawnPlayer;
	}

	public List<UUID> getLastPlayerSpawn()
	{
		return lastSpawnPlayer;
	}

	public void addPlayer(final EntityPlayer player)
	{
		this.addPlayer(EntityPlayer.getUUID(player.getGameProfile()));
	}

	public void addPlayer(final UUID player)
	{
		if(!lastSpawnPlayer.contains(player))
			lastSpawnPlayer.add(player);
	}

	public void removePlayer(final EntityPlayer player)
	{
		this.removePlayer(EntityPlayer.getUUID(player.getGameProfile()));
	}

	public void removePlayer(final UUID player)
	{
		int index = lastSpawnPlayer.indexOf(player);
		if (index == -1) return;
		lastSpawnPlayer.remove(index);
	}

	public boolean hasPlayer(final EntityPlayer player)
	{
		return this.hasPlayer(EntityPlayer.getUUID(player.getGameProfile()));
	}

	public boolean hasPlayer(final UUID player)
	{
		return lastSpawnPlayer.indexOf(player) != -1;
	}

	public int getDimension()
	{
		return dim;
	}

	public void setDimension(final int dim)
	{
		this.dim = dim;
	}

	public BonfireInfo copy()
	{
		BonfireInfo bi = new BonfireInfo(owner, dim);
		bi.setName(getName());
		bi.isPublic(this.isPublic());
		bi.setLastPlayerSpawn(getLastPlayerSpawn());
		return bi;
	}

}
