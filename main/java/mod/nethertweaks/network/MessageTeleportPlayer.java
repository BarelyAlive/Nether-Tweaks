package mod.nethertweaks.network;

import java.nio.charset.Charset;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import mod.nethertweaks.world.BonfireInfo;
import mod.nethertweaks.world.WorldSpawnLocation;
import mod.sfhcore.vars.PlayerPosition;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import p455w0rdslib.util.ChunkUtils;

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
		private BlockPos testPosition(World world, final BlockPos destination)
		{		
			boolean north = world.isAirBlock(destination.north()) && world.isAirBlock(destination.north().up()) && world.isSideSolid(destination.north().down(), EnumFacing.UP);
			boolean east = world.isAirBlock(destination.east()) && world.isAirBlock(destination.east().up()) && world.isSideSolid(destination.east().down(), EnumFacing.UP);
			boolean south = world.isAirBlock(destination.south()) && world.isAirBlock(destination.south().up()) && world.isSideSolid(destination.south().down(), EnumFacing.UP);
			boolean west = world.isAirBlock(destination.west()) && world.isAirBlock(destination.west().up()) && world.isSideSolid(destination.west().down(), EnumFacing.UP);
			
			boolean northEast = world.isAirBlock(destination.north().east()) && world.isAirBlock(destination.north().east().up()) && world.isSideSolid(destination.north().east().down(), EnumFacing.UP);
			boolean southEast = world.isAirBlock(destination.east().south()) && world.isAirBlock(destination.east().south().up()) && world.isSideSolid(destination.east().south().down(), EnumFacing.UP);
			boolean southWest = world.isAirBlock(destination.south().west()) && world.isAirBlock(destination.south().west().up()) && world.isSideSolid(destination.south().west().down(), EnumFacing.UP);
			boolean northWest = world.isAirBlock(destination.west().north()) && world.isAirBlock(destination.west().north().up()) && world.isSideSolid(destination.west().north().down(), EnumFacing.UP);
			
			if(north) return destination.north();
			if(east) return destination.east();
			if(south) return destination.south();
			if(west) return destination.west();
			
			if(northEast) return destination.north().east();
			if(southEast) return destination.south().east();
			if(southWest) return destination.south().west();
			if(northWest) return destination.north().west();
			
			return null;
		}

		@Override
		public IMessage onMessage(MessageTeleportPlayer message, MessageContext ctx) {
			
			EntityPlayer player = Minecraft.getMinecraft().world.getPlayerEntityByUUID(UUID.fromString(message.uuid));

			Minecraft.getMinecraft().world.getChunkProvider().loadChunk(ChunkUtils.getChunkPos(Minecraft.getMinecraft().world, message.looking_block).x, ChunkUtils.getChunkPos(Minecraft.getMinecraft().world, message.looking_block).z);
			
			System.out.println(Minecraft.getMinecraft().world.getBlockState(message.looking_block.up()).getBlock());
			System.out.println(Minecraft.getMinecraft().world.getBlockState(message.looking_block).getBlock());
			System.out.println(Minecraft.getMinecraft().world.getBlockState(message.looking_block.down()).getBlock());
			System.out.println(Minecraft.getMinecraft().world.getBlockState(message.looking_block.down(2)).getBlock());
			
			BlockPos resultPos = testPosition(Minecraft.getMinecraft().world, message.looking_block.down());
			
			if (resultPos == null)
			{
				resultPos = message.looking_block.up();
			}
			
			player.setPositionAndRotation(resultPos.getX() + 0.5, resultPos.getY(), resultPos.getZ() + 0.5, player.cameraYaw, player.cameraPitch);
			
			LookAt(message.looking_block.getX() + 0.5, message.looking_block.getY(), message.looking_block.getZ() + 0.5, player);
			
			ctx.getServerHandler().setPlayerLocation(resultPos.getX() + 0.5, resultPos.getY(), resultPos.getZ() + 0.5, player.cameraYaw, player.cameraPitch);
			
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
			
			WorldSpawnLocation.bonfire_info.put(message.looking_block, binfo);
			
			player.sendMessage(new TextComponentString(player.getName() + " rested at: " + resultPos + "!"));
			
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
