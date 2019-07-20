package mod.nethertweaks.network.bonfire;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import mod.nethertweaks.world.BonfireInfo;
import mod.nethertweaks.world.WorldSaveData;
import mod.nethertweaks.world.WorldSpawnLocation;
import mod.sfhcore.vars.PlayerPosition;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageBonfireGetList implements IMessage
{
	public Map<UUID, PlayerPosition> lastSpawnLocas = new HashMap<UUID, PlayerPosition>();
	public Map<BlockPos, BonfireInfo> bonfire_info = new HashMap<BlockPos, BonfireInfo>();
	
	public MessageBonfireGetList() {
	}
	
	public MessageBonfireGetList(Map<UUID, PlayerPosition> lastSpawnLocas, Map<BlockPos, BonfireInfo> bonfire_info) {
		this.lastSpawnLocas = lastSpawnLocas;
		this.bonfire_info = bonfire_info;
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		// Write to Bytes
		UUID[] uuid_array = lastSpawnLocas.keySet().toArray(new UUID[0]);
		
		System.out.println(uuid_array);
		
		buf.writeInt(uuid_array.length);
		for(UUID uuid : uuid_array)
		{
			buf.writeLong(uuid.getMostSignificantBits());
			buf.writeLong(uuid.getLeastSignificantBits());
			
			PlayerPosition player_pos = this.lastSpawnLocas.get(uuid);
			
			BlockPos pos = player_pos.getPos();
			buf.writeInt(pos.getX());
			buf.writeInt(pos.getY());
			buf.writeInt(pos.getZ());
			
			buf.writeFloat(player_pos.getAng());
			buf.writeFloat(player_pos.getYaw());
		}
		
		BlockPos[] block_pos_array = this.bonfire_info.keySet().toArray(new BlockPos[0]);
		
		buf.writeInt(block_pos_array.length);
		
		for(Map.Entry<BlockPos, BonfireInfo> entry : this.bonfire_info.entrySet())
		{
			buf.writeInt(entry.getKey().getX());
			buf.writeInt(entry.getKey().getY());
			buf.writeInt(entry.getKey().getZ());
			
			BonfireInfo info = entry.getValue();
			
			buf.writeLong(info.getOwner().getMostSignificantBits());
			buf.writeLong(info.getOwner().getLeastSignificantBits());
			buf.writeInt(info.getDimension());
			BlockPos spawnpos = info.getSpawnPos();
			if (spawnpos != null)
			{
				buf.writeInt(spawnpos.getX());
				buf.writeInt(spawnpos.getY());
				buf.writeInt(spawnpos.getZ());
			}
			else
			{
				buf.writeInt(0);
				buf.writeInt(0);
				buf.writeInt(0);
			}
			buf.writeInt(info.getName().length());
			buf.writeCharSequence(info.getName(), Charset.forName("UTF-8"));
			buf.writeBoolean(info.isPublic());
			List<UUID> player_list = info.getLastPlayerSpawn();
			buf.writeInt(player_list.size());
			for(int i = 0; i < player_list.size(); i++)
			{
				buf.writeLong(player_list.get(i).getMostSignificantBits());
				buf.writeLong(player_list.get(i).getLeastSignificantBits());
			}
		}
		
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		int arr_length = buf.readInt();
		UUID uuid;
		PlayerPosition player_pos;
		for(int i = 0; i < arr_length; i++)
		{
			uuid = new UUID(buf.readLong(), buf.readLong());
			player_pos = new PlayerPosition();
			player_pos.setPos(buf.readInt(), buf.readInt(), buf.readInt());
			player_pos.setAng(buf.readFloat());
			player_pos.setYaw(buf.readFloat());
			
			this.lastSpawnLocas.put(uuid, player_pos);
		}
		
		arr_length = buf.readInt();
		
		for(int i = 0; i < arr_length; i++)
		{
			BlockPos pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
			BonfireInfo info = new BonfireInfo(new UUID(buf.readLong(), buf.readLong()), buf.readInt());
			
			info.setSpawnPos(new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
			int length = buf.readInt();
			info.setName(buf.readCharSequence(length, Charset.defaultCharset()).toString());
			info.isPublic(buf.readBoolean());
			
			length = buf.readInt();
			for(int j = 0; j < length; j++)
			{
				info.addPlayer(new UUID(buf.readLong(), buf.readLong()));
			}
			
			this.bonfire_info.put(pos, info);
		}
	}
	
	public static class MessageWorldSaveDataHandler implements IMessageHandler<MessageBonfireGetList, IMessage>
	{

		@Override
		public IMessage onMessage(MessageBonfireGetList message, MessageContext ctx) {
			WorldSpawnLocation.setBonfireInfo(message.bonfire_info);
			WorldSpawnLocation.setLastSpawnLocations(message.lastSpawnLocas);
			return null;
		}
		
	}
	
}
