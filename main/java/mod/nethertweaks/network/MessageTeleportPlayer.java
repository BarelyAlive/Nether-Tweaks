package mod.nethertweaks.network;

import java.nio.charset.Charset;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import mod.nethertweaks.world.BonfireInfo;
import mod.nethertweaks.world.WorldSpawnLocation;
import mod.sfhcore.vars.PlayerPosition;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageTeleportPlayer implements IMessage {

	int x, y, z;
	int uuid_size;
	BlockPos looking_block;
	String uuid;
	
	public MessageTeleportPlayer() {}
	
	public MessageTeleportPlayer(int x, int y, int z, BlockPos looking_block, EntityPlayer player) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.looking_block = looking_block;
		this.uuid = player.getUUID(player.getGameProfile()).toString();
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.looking_block = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		this.uuid_size = buf.readInt();
		byte[] temp_char_arr = new byte[this.uuid_size];
		buf.readBytes(temp_char_arr);
		this.uuid = new String(temp_char_arr, Charset.forName("ASCII"));
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(this.looking_block.getX());
		buf.writeInt(this.looking_block.getY());
		buf.writeInt(this.looking_block.getZ());
		buf.writeInt(uuid.getBytes(Charset.forName("ASCII")).length);
		buf.writeBytes(uuid.getBytes(Charset.forName("ASCII")));
	}
	
	public static class MessageTeleportPlayerHandler implements IMessageHandler<MessageTeleportPlayer, IMessage>
	{
		@Override
		public IMessage onMessage(MessageTeleportPlayer message, MessageContext ctx) {
			
			EntityPlayer player = Minecraft.getMinecraft().world.getPlayerEntityByUUID(UUID.fromString(message.uuid));
			
			player.setPositionAndRotation(message.x + 0.5, message.y, message.z + 0.5, player.cameraYaw, player.cameraPitch);
			
			LookAt(message.looking_block.getX() + 0.5, message.looking_block.getY(), message.looking_block.getZ() + 0.5, player);
			
			ctx.getServerHandler().setPlayerLocation(message.x + 0.5, message.y, message.z + 0.5, player.cameraYaw, player.cameraPitch);
			
			WorldSpawnLocation.lastSpawnLocations.put(player.getUUID(player.getGameProfile()), new PlayerPosition(new BlockPos(player), player.cameraYaw, player.cameraPitch));
			
			BonfireInfo binfo;
			if (!WorldSpawnLocation.bonfire_info.containsKey(message.looking_block))
			{
				binfo = new BonfireInfo(player.getUniqueID());
			}
			else
			{
				binfo = WorldSpawnLocation.bonfire_info.get(message.looking_block);
			}
			
			for (BonfireInfo entry : WorldSpawnLocation.bonfire_info.values())
			{
				if (entry.hasPlayer(player))
				{
					entry.removePlayer(player);
				}
			}
			
			binfo.addPlayer(player);
			
			WorldSpawnLocation.bonfire_info.put(message.looking_block, binfo.copy());
			
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
