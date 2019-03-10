package mod.nethertweaks.blocks.gui;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.container.ContainerCondenser;
import mod.nethertweaks.blocks.container.ContainerFreezer;
import mod.nethertweaks.blocks.container.ContainerNetherrackFurnace;
import mod.nethertweaks.blocks.tileentities.TileEntityCondenser;
import mod.nethertweaks.blocks.tileentities.TileEntityFreezer;
import mod.nethertweaks.blocks.tileentities.TileEntityNetherrackFurnace;
import mod.sfhcore.handler.GuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class GuiHandlerNTM{
	
	/*
	public GuiHandler() {
		NetworkRegistry.INSTANCE.registerGuiHandler(NetherTweaksMod.instance, this);
	}
	*/
	
	public static void addGuiToHandler() {
		GuiHandler.addGUIRelation(new TileEntityCondenser(null), new ContainerCondenser(null, null));
		GuiHandler.addGUIRelation(new TileEntityFreezer(null), new ContainerFreezer(null, null));
		GuiHandler.addGUIRelation(new TileEntityNetherrackFurnace(null), new ContainerNetherrackFurnace(null, null));
	}
	
	/*
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity1 = world.getTileEntity(new BlockPos(x, y, z));
        TileEntity tileEntity2 = world.getTileEntity(new BlockPos(x, y, z));
        TileEntity tileEntity3 = world.getTileEntity(new BlockPos(x, y, z));
        TileEntity tileEntity4 = world.getTileEntity(new BlockPos(x, y, z));
        if(tileEntity1 instanceof TileEntityCondenser) return new ContainerCondenser(player.inventory, (TileEntityCondenser) tileEntity1);
        else if(tileEntity2 instanceof TileEntityNetherrackFurnace) return new ContainerNetherrackFurnace(player.inventory, (TileEntityNetherrackFurnace) tileEntity3);
        else if(tileEntity3 instanceof TileEntityFreezer) return new ContainerFreezer(player.inventory, (TileEntityFreezer) tileEntity4);
        else return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity1 = world.getTileEntity(new BlockPos(x, y, z));
		TileEntity tileEntity2 = world.getTileEntity(new BlockPos(x, y, z));
		TileEntity tileEntity3 = world.getTileEntity(new BlockPos(x, y, z));
		TileEntity tileEntity4 = world.getTileEntity(new BlockPos(x, y, z));
        if(tileEntity1 instanceof TileEntityCondenser) return new GuiCondenser(player.inventory, (TileEntityCondenser) tileEntity1);
        else if(tileEntity2 instanceof TileEntityNetherrackFurnace) return new GuiNetherrackFurnace(player.inventory, (TileEntityNetherrackFurnace) tileEntity3);
        else if(tileEntity3 instanceof TileEntityFreezer) return new GuiFreezer(player.inventory, (TileEntityFreezer) tileEntity4);
        else return null;
	}
	*/
}
