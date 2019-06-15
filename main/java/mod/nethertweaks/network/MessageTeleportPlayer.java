package mod.nethertweaks.network;

import java.nio.charset.Charset;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.world.BonfireInfo;
import mod.nethertweaks.world.WorldSpawnLocation;
import mod.sfhcore.vars.PlayerPosition;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Type;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import p455w0rdslib.util.ChunkUtils;

public class MessageTeleportPlayer implements IMessage {

	int uuid_size;
	BlockPos bonfire_pos;
	String uuid;
	
	public MessageTeleportPlayer() {}
	
	public MessageTeleportPlayer(BlockPos bonfire_pos, EntityPlayer player) {
		this.bonfire_pos = bonfire_pos;
		this.uuid = player.getUUID(player.getGameProfile()).toString();
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.bonfire_pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		this.uuid_size = buf.readInt();
		byte[] temp_char_arr = new byte[this.uuid_size];
		buf.readBytes(temp_char_arr);
		this.uuid = new String(temp_char_arr, Charset.forName("ASCII"));
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.bonfire_pos.getX());
		buf.writeInt(this.bonfire_pos.getY());
		buf.writeInt(this.bonfire_pos.getZ());
		buf.writeInt(uuid.getBytes(Charset.forName("ASCII")).length);
		buf.writeBytes(uuid.getBytes(Charset.forName("ASCII")));
	}
	
	public static class MessageTeleportPlayerHandler implements IMessageHandler<MessageTeleportPlayer, IMessage>
	{
		@Override
		public IMessage onMessage(MessageTeleportPlayer message, MessageContext ctx) {
			
			EntityPlayer player = Minecraft.getMinecraft().world.getPlayerEntityByUUID(UUID.fromString(message.uuid));

			BonfireInfo binfo;
			if (!WorldSpawnLocation.bonfire_info.containsKey(message.bonfire_pos))
			{
				binfo = new BonfireInfo(player.getUniqueID());
			}
			else
			{
				binfo = WorldSpawnLocation.bonfire_info.get(message.bonfire_pos);
			}
			
			player.setPositionAndRotation(binfo.getSpawnPos().getX() + 0.5, binfo.getSpawnPos().getY(), binfo.getSpawnPos().getZ() + 0.5, player.cameraYaw, player.cameraPitch);
			
			LookAt(message.bonfire_pos.getX() + 0.5, message.bonfire_pos.getY(), message.bonfire_pos.getZ() + 0.5, player);
			
			ctx.getServerHandler().setPlayerLocation(binfo.getSpawnPos().getX() + 0.5, binfo.getSpawnPos().getY(), binfo.getSpawnPos().getZ() + 0.5, player.cameraYaw, player.cameraPitch);
			
			WorldSpawnLocation.lastSpawnLocations.put(player.getUUID(player.getGameProfile()), new PlayerPosition(new BlockPos(player), player.cameraYaw, player.cameraPitch));
			
			for (BonfireInfo entry : WorldSpawnLocation.bonfire_info.values())
			{
				if (entry.hasPlayer(player))
				{
					entry.removePlayer(player);
				}
			}
			
			binfo.addPlayer(player);
			
			WorldSpawnLocation.bonfire_info.put(message.bonfire_pos, binfo);
			
			player.sendMessage(new TextComponentString(player.getName() + " rested at: " + binfo.getSpawnPos() + "!"));
			
			player.closeScreen();
			
			return null;
		}
		
		public static void LookAt(double px, double py, double pz , EntityPlayer player)
		{
		    double dirx = player.getPosition().getX() - px;
		    double diry = player.getPosition().getY() - py;
		    double dirz = player.getPosition().getZ() - pz;

		    double len = Math.sqrt(dirx*dirx + diry*diry + dirz*dirz);

		    dirx /= len;
		    diry /= len;
		    dirz /= len;

		    double pitch = Math.asin(diry);
		    double yaw = Math.atan2(dirz, dirx);

		    //to degree
		    pitch = pitch * 180.0 / Math.PI;
		    yaw = yaw * 180.0 / Math.PI;

		    yaw += 90f;
		    player.rotationPitch = (float)pitch;
		    player.rotationYaw = (float)yaw;
		    player.cameraPitch = player.rotationPitch;
		    player.cameraYaw = player.rotationYaw;
		}
	}
}
