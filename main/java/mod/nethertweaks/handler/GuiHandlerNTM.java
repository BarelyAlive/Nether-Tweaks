package mod.nethertweaks.handler;

import mod.nethertweaks.blocks.container.ContainerNetherrackFurnace;
import mod.nethertweaks.blocks.gui.GuiNetherrackFurnace;
import mod.nethertweaks.blocks.tileentities.TileEntityNetherrackFurnace;
import mod.sfhcore.blocks.container.ContainerBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandlerNTM implements IGuiHandler {
	
	@Override
	public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case 0:
			return new ContainerNetherrackFurnace(player.inventory, (TileEntityNetherrackFurnace)world.getTileEntity(new BlockPos(x, y, z)));
		default:
			return null;
		}
	}
		

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case 0:
			return new GuiNetherrackFurnace((ContainerBase) getServerGuiElement(ID, player, world, x, y, z), player.inventory);
		default:
			return null;
		}
	}
}
