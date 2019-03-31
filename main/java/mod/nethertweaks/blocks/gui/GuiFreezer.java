package mod.nethertweaks.blocks.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import mod.nethertweaks.blocks.container.ContainerFreezer;
import mod.nethertweaks.blocks.tileentities.TileEntityFreezer;
import mod.sfhcore.Constants;

public class GuiFreezer extends GuiContainer {
	private int xSize, ySize;
	private final ResourceLocation backgroundimage = new ResourceLocation(Constants.TEX + "textures/gui/guifreezer.png");
	private TileEntityFreezer entity;
	
	public GuiFreezer(InventoryPlayer inventoryPlayer, TileEntityFreezer tileEntity) {
        super(new ContainerFreezer(inventoryPlayer, tileEntity));
        entity = tileEntity;
   		xSize = 175;
		ySize = 165;
	}
	
	/**
     * Draws the screen and all the components in it.
     */
	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
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
        
        int x_old = x;
        int y_old = y;
        if(this.entity.isWorking(this.entity)){
        	int k = this.entity.getWorkTimeRemainingScaled(12);
        	x += 28;
        	y += 17;
        	int k_inv = 12 - k;
        	drawTexturedModalRect(x, y + k_inv, 176, k_inv, 14, k + 2);
        }
        
        x = x_old;
        y = y_old;
        
        if(this.entity.tank.amount != 0)
        {
    		int k = this.entity.tank.amount * 64 / this.entity.MAX_CAPACITY;
        	x += 134;
        	y += 6;
        	int k_inv = 64 - k;
        	drawTexturedModalRect(x, y + k_inv, 176, 14 + k_inv, 16, k);
        }
    }
    
    @Override
    public boolean doesGuiPauseGame() {
    	return false;
    }

}
