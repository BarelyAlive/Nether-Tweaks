package mod.nethertweaks.handler;

import mod.nethertweaks.blocks.container.ContainerBonfire;
import mod.nethertweaks.blocks.container.ContainerCondenser;
import mod.nethertweaks.blocks.container.ContainerFreezer;
import mod.nethertweaks.blocks.container.ContainerHellmart;
import mod.nethertweaks.blocks.container.ContainerNetherrackFurnace;
import mod.nethertweaks.blocks.gui.GuiBonfire;
import mod.nethertweaks.blocks.gui.GuiCondenser;
import mod.nethertweaks.blocks.gui.GuiFreezer;
import mod.nethertweaks.blocks.gui.GuiHellmart;
import mod.nethertweaks.blocks.gui.GuiNetherrackFurnace;
import mod.nethertweaks.blocks.tile.TileCondenser;
import mod.nethertweaks.blocks.tile.TileFreezer;
import mod.nethertweaks.blocks.tile.TileHellmart;
import mod.nethertweaks.blocks.tile.TileNetherrackFurnace;
import mod.sfhcore.blocks.container.ContainerBase;
import mod.sfhcore.handler.GuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandlerNTM implements IGuiHandler
{
	public static final int idFurnace = 0;
	public static final int idCondenser = 1;
	public static final int idFreezer = 2;
	public static final int idHellmart = 3;
	public static final int idBonfire = 4;
	
	@Override
	public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (ID)
		{
		case idFurnace:
			return new ContainerNetherrackFurnace(player.inventory, (TileNetherrackFurnace)world.getTileEntity(new BlockPos(x, y, z)));
		case idCondenser:
			return new ContainerCondenser(player.inventory, (TileCondenser)world.getTileEntity(new BlockPos(x, y, z)));
		case idFreezer:
			return new ContainerFreezer(player.inventory, (TileFreezer)world.getTileEntity(new BlockPos(x, y, z)));
		case idHellmart:
			return new ContainerHellmart(player.inventory, (TileHellmart)world.getTileEntity(new BlockPos(x, y, z)));
		case idBonfire:
			return new ContainerBonfire();
		default:
			return null;
		}
	}
		
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (ID)
		{
		case idFurnace:
			return new GuiNetherrackFurnace(player.inventory, (TileNetherrackFurnace) world.getTileEntity(new BlockPos(x, y, z)));
		case idCondenser:
			return new GuiCondenser(player.inventory, (TileCondenser) world.getTileEntity(new BlockPos(x, y, z)));
		case idFreezer:
			return new GuiFreezer(player.inventory, (TileFreezer) world.getTileEntity(new BlockPos(x, y, z)));
		case idHellmart:
			return new GuiHellmart(player.inventory, (TileHellmart) world.getTileEntity(new BlockPos(x, y, z)));
		case idBonfire:
			return new GuiBonfire(new BlockPos(x, y, z), world, player);
		default:
			return null;
		}
	}
}
