package mod.nethertweaks.world;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldProviderEnd;

public class WorldProviderEndNTM extends WorldProviderEnd {
	
	@Override
	public int getRespawnDimension(EntityPlayerMP player) {
		return -1;
	}
	
	@Override
	public boolean canRespawnHere() {
		return false;
	}
	
}
