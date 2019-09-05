package mod.nethertweaks.blocks.gui;

import org.lwjgl.opengl.GL11;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.container.ContainerFreezer;
import mod.nethertweaks.blocks.tile.TileFreezer;
import mod.nethertweaks.config.Config;
import mod.sfhcore.blocks.tiles.TileInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiFreezer extends GuiContainer
{
	private int xSize, ySize;
	private final ResourceLocation backgroundimage = new ResourceLocation(NetherTweaksMod.MODID + ":textures/gui/guifreezer.png");
	private TileFreezer entity;
	
	public GuiFreezer(InventoryPlayer inventoryPlayer, TileFreezer tileEntity) {
        super(new ContainerFreezer(inventoryPlayer, tileEntity));
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
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		getTE();
		
		//Fluid & amount
		String fName = "---";
		FluidStack f = this.entity.getTank().getFluid();
		if(f != null) fName = f.getLocalizedName() + " : " + this.entity.getTank().getFluidAmount() + " mB";
		else fName = fName +" : 0 mB";
		int lenght0 = fontRenderer.getStringWidth(fName);
		int x0 = 174 - lenght0;
		fontRenderer.drawStringWithShadow(fName, x0, 73, 0xffffff);
		
		//Temperature in Celsius/Fahrenheit
		String celsius = " °C";
		String fahrenheit = " °F";
		String text = "";
		
		double temp = Math.round(10.0 * this.entity.getTemp()) / 10.0;
		
		if(Config.useMetricSystem) 
			text = temp + celsius;
		else
			text = (temp * 1.8f + 32) + fahrenheit;
		
		lenght0 = fontRenderer.getStringWidth(text);
		lenght0 /= 2;
		int x1 = 35 - lenght0;
		fontRenderer.drawStringWithShadow(text, x1, 35, 0xffffff);
    }
	
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 10.F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(backgroundimage);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        
        this.getTE();
        
        int x_old = x;
        int y_old = y;
        if(TileInventory.isWorking(this.entity)){
        	int k = this.entity.getWorkTimeRemainingScaled(12);
        	x += 28;
        	y += 17;
        	int k_inv = 12 - k;
        	drawTexturedModalRect(x, y + k_inv, 176, k_inv, 14, k + 2);
        }
        
        x = x_old;
        y = y_old;
        
        if(this.entity.getTank().getFluidAmount() != 0)
        {
    		int k = this.entity.getTank().getFluidAmount() * 64 / this.entity.getTank().getCapacity();
        	x += 134;
        	y += 6;
        	int k_inv = 64 - k;
        	
        	GL11.glPushMatrix();
    		GlStateManager.enableBlend();
    		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

    		FluidStack fluid = this.entity.getTank().getFluid();
    		
        	TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluid.getFluid().getStill().toString());
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
    
    private void getTE()
    {
    	this.entity = (TileFreezer) this.mc.player.world.getTileEntity(this.entity.getPos());
    }
    
    @Override
    public boolean doesGuiPauseGame() {
    	return false;
    }
}
