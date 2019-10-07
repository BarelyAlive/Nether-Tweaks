package mod.nethertweaks.network;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import mod.nethertweaks.network.bonfire.MessageLastSpawnUpdate;
import mod.nethertweaks.network.bonfire.UpdateStatus;
import mod.nethertweaks.world.BonfireInfo;
import mod.nethertweaks.world.WorldSpawnLocation;
import mod.sfhcore.network.NetworkHandler;
import mod.sfhcore.vars.PlayerPosition;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageTeleportPlayer implements IMessage {

	int uuid_size;
	BlockPos bonfire_pos;
	String uuid;

	public MessageTeleportPlayer() {}

	public MessageTeleportPlayer(final BlockPos bonfire_pos, final EntityPlayer player) {
		this.bonfire_pos = bonfire_pos;
		uuid = EntityPlayer.getUUID(player.getGameProfile()).toString();
	}

	@Override
	public void fromBytes(final ByteBuf buf) {
		bonfire_pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		uuid_size = buf.readInt();
		byte[] temp_char_arr = new byte[uuid_size];
		buf.readBytes(temp_char_arr);
		uuid = new String(temp_char_arr, StandardCharsets.US_ASCII);
	}

	@Override
	public void toBytes(final ByteBuf buf) {
		buf.writeInt(bonfire_pos.getX());
		buf.writeInt(bonfire_pos.getY());
		buf.writeInt(bonfire_pos.getZ());
		buf.writeInt(uuid.getBytes(StandardCharsets.US_ASCII).length);
		buf.writeBytes(uuid.getBytes(StandardCharsets.US_ASCII));
	}

	public static class MessageTeleportPlayerHandler implements IMessageHandler<MessageTeleportPlayer, IMessage>
	{
		@Override
		public IMessage onMessage(final MessageTeleportPlayer message, final MessageContext ctx) {

			EntityPlayer player = ctx.getServerHandler().player.world.getPlayerEntityByUUID(UUID.fromString(message.uuid));

			BonfireInfo binfo;
			if (!WorldSpawnLocation.bonfire_info.containsKey(message.bonfire_pos))
				binfo = new BonfireInfo(player.getUniqueID(), player.world.provider.getDimension());
			else
				binfo = WorldSpawnLocation.bonfire_info.get(message.bonfire_pos);

			player.setPositionAndRotation(binfo.getSpawnPos().getX() + 0.5, binfo.getSpawnPos().getY(), binfo.getSpawnPos().getZ() + 0.5, player.cameraYaw, player.cameraPitch);

			LookAt(message.bonfire_pos.getX() + 0.5, message.bonfire_pos.getY(), message.bonfire_pos.getZ() + 0.5, player);

			ctx.getServerHandler().setPlayerLocation(binfo.getSpawnPos().getX() + 0.5, binfo.getSpawnPos().getY(), binfo.getSpawnPos().getZ() + 0.5, player.cameraYaw, player.cameraPitch);

			WorldSpawnLocation.lastSpawnLocations.put(EntityPlayer.getUUID(player.getGameProfile()), new PlayerPosition(new BlockPos(player), player.cameraYaw, player.cameraPitch));

			for (BonfireInfo entry : WorldSpawnLocation.bonfire_info.values())
				if (entry.hasPlayer(player))
					entry.removePlayer(player);

			binfo.addPlayer(player);

			WorldSpawnLocation.bonfire_info.put(message.bonfire_pos, binfo);

			player.sendMessage(new TextComponentString(player.getName() + " rested at: " + binfo.getSpawnPos() + "!"));

			player.closeScreen();

			NetworkHandler.INSTANCE.sendToServer(new MessageLastSpawnUpdate(UpdateStatus.UPDATE, WorldSpawnLocation.lastSpawnLocations.get(EntityPlayer.getUUID(player.getGameProfile())), EntityPlayer.getUUID(player.getGameProfile())));

			return null;
		}

		public static void LookAt(final double px, final double py, final double pz , final EntityPlayer player)
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
