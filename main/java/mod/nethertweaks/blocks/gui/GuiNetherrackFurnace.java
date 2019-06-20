package mod.nethertweaks.blocks.gui;

import org.lwjgl.opengl.GL11;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.container.ContainerNetherrackFurnace;
import mod.nethertweaks.blocks.tile.TileNetherrackFurnace;
import mod.sfhcore.blocks.tiles.TileInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiNetherrackFurnace extends GuiContainer
{
	private int xSize, ySize;
	private static final ResourceLocation GUI_FURNACE = new ResourceLocation(NetherTweaksMod.MODID, "textures/gui/guinetherrackfurnace.png");
	private TileNetherrackFurnace entity;
	
	public GuiNetherrackFurnace(InventoryPlayer inventory, TileNetherrackFurnace te) {
        super(new ContainerNetherrackFurnace(inventory, te));
        entity = te;
        xSize = 176;
        ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		/*
        fontRendererObj.drawString("BaseGenerator", 8, 6, 4210752);
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
        */
    }
	
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 10.F);
        this.mc.getTextureManager().bindTexture(GUI_FURNACE);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
       
        this.getTE();
        
        if(TileInventory.isWorking(this.entity)){
        	int k = this.entity.getWorkTimeRemainingScaled(13);
        	x += 57;
        	y += 36;
        	int k_inv = 13 - k;
        	drawTexturedModalRect(x, y + k_inv, 176, k_inv, 14, k + 2);
        }
    }
    
    private void getTE ()
    {
    	this.entity = (TileNetherrackFurnace) this.mc.player.world.getTileEntity(this.entity.getPos());
    }
    
    @Override
    public boolean doesGuiPauseGame() {
    	return false;
    }

}
