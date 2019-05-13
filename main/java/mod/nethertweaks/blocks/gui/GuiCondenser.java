package mod.nethertweaks.blocks.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import mod.nethertweaks.blocks.container.ContainerCondenser;
import mod.nethertweaks.blocks.tile.TileCondenser;
import mod.sfhcore.Constants;
import mod.sfhcore.util.Util;

public class GuiCondenser extends GuiContainer {
	private int xSize, ySize;
	private final ResourceLocation backgroundimage = new ResourceLocation(Constants.MOD + ":textures/gui/guicondenser.png");
	private TileCondenser entity;
	
	public GuiCondenser(InventoryPlayer inventoryPlayer, TileCondenser tileEntity) {
        super(new ContainerCondenser(inventoryPlayer, tileEntity));
        entity = tileEntity;
   		xSize = 176;
		ySize = 166;
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
        	y += 18;
        	int k_inv = 12 - k;
        	drawTexturedModalRect(x, y + k_inv, 176, k_inv, 14, k + 2);
        }
        
        x = x_old;
        y = y_old;
        
        if(this.entity.getTank().getFluidAmount() != 0)
        {
    		int k = this.entity.getTank().getFluidAmount() * 64 / this.entity.getMaxCapacity();
        	x += 134;
        	y += 6;
        	int k_inv = 64 - k;
        	
        	GL11.glPushMatrix();
    		GlStateManager.enableBlend();
    		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

    		FluidStack fluid = this.entity.getTank().getFluid();
    		
        	TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluid.getFluid().getStill().toString());
        	//System.out.println(sprite.getFrameCount());
        	//System.out.println(sprite.getFrameCount());
    		ResourceLocation res = this.entity.getTank().getFluid().getFluid().getStill(this.entity.getTank().getFluid());
    		res = new ResourceLocation(res.getResourceDomain(), "textures/" + res.getResourcePath() + ".png");
        	Minecraft.getMinecraft().getTextureManager().bindTexture(res);

        	for (int i = 0; i < 16; i++)
        	{
        		int frame = (int) ( System.currentTimeMillis() / (6400 / sprite.getFrameCount())) % (sprite.getFrameCount() + 1);
        		drawTexturedModalRect(x + i, y + k_inv, i* sprite.getIconHeight(), frame * sprite.getIconHeight() , 1, k);
        	}
    		GL11.glPopMatrix();
        }
    }
    
    @Override
    public boolean doesGuiPauseGame() {
    	return false;
    }
}
