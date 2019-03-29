package mod.nethertweaks.world;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderSurface;

public class WorldProviderSurfaceNTM extends WorldProviderSurface {
	
	@Override
	public int getRespawnDimension(EntityPlayerMP player) {
		return -1;
	}
	
	@Override
	public void onPlayerAdded(EntityPlayerMP player) {
		player.changeDimension(-1);
	}
	
	@Override
	public boolean canRespawnHere() {
		return false;
	}
	
	@Override
	public boolean isSurfaceWorld() {
		return true;
	}
	
	@Override
	public boolean canCoordinateBeSpawn(int x, int z) {
		return false;
	}
	
}