package mod.nethertweaks.blocks.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import mod.nethertweaks.blocks.container.ContainerNetherrackFurnace;
import mod.nethertweaks.blocks.tileentities.TileEntityNetherrackFurnace;
import mod.sfhcore.Constants;

public class GuiNetherrackFurnace extends GuiContainer {
	private int xSize, ySize;
	private final ResourceLocation backgroundimage = new ResourceLocation(Constants.TEX + "textures/gui/GuiNetherrackFurnace.png");
	private TileEntityNetherrackFurnace entity;
	
	public GuiNetherrackFurnace(InventoryPlayer inventoryPlayer, TileEntityNetherrackFurnace tileEntity) {
        super(new ContainerNetherrackFurnace(inventoryPlayer, tileEntity));
        entity = tileEntity;
   		xSize = 175;
		ySize = 165;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2){
		/*
        fontRendererObj.drawString("BaseGenerator", 8, 6, 4210752);
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
        */
    }
	
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 10.F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(backgroundimage);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        
        if(this.entity.isWorking()){
        	int k = this.entity.getWorkTimeRemainingScaled(12);
        	drawTexturedModalRect(guiLeft + 58, guiTop + 36 + 12 -k, 176, 12-k, 14, k+2);
        }
        
    }
    
    @Override
    public boolean doesGuiPauseGame() {
    	return false;
    }

}
