package mod.nethertweaks.blocks.tileentities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.world.WorldDataNTM;
import mod.nethertweaks.world.WorldHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import scala.collection.script.Remove;

public class TileEntityBonfire extends TileEntity{

	private static Map<UUID, BlockPos> spawnLocs = new HashMap<UUID, BlockPos>();
	
	public TileEntityBonfire()
	{
		
	}
	
	private enum BonfireAction{
		ADD,
		REMOVEALL;
	}
	
	public void deleteSpawnLocationsIfDestroyed() {
		for(Map.Entry<UUID, BlockPos> entry : spawnLocs.entrySet())
		{
			EntityPlayer player = getPlayer(entry.getKey());
			
			int x = player.getEntityData().getInteger(WorldHandler.coodX);
			int y = player.getEntityData().getInteger(WorldHandler.coodY);
			int z = player.getEntityData().getInteger(WorldHandler.coodZ);
			
			player.bedLocation = new BlockPos(x, y, z);
			
			for(Map.Entry<UUID, BlockPos> entryG : WorldDataNTM.spawnLocas.entrySet()) {
				if(entry == entryG) {
					removeGlobalEntry(entry.getKey(), entry.getValue());
				}
			}
			
			if(world.isRemote)
				player.sendMessage(new TextComponentString(player.getName() + "'s point of rest is lost!"));
		}
		
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
		if(this.spawnLocs.containsKey(getUUID(player))) {
			this.spawnLocs.put(getUUID(player), new BlockPos(player));
			if(world.isRemote)
			    player.sendMessage(new TextComponentString(player.getName() + " rested at X: " + player.getPosition() + "!"));
		}
		addGlobalEntry(getUUID(player), pos);
		
		player.bedLocation = pos;
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
		WorldDataNTM.spawnLocas.put(uuid, pos);
	}
	
	private void removeGlobalEntry(UUID uuid, BlockPos pos)
	{
		WorldDataNTM.spawnLocas.remove(uuid, pos);
	}
}
