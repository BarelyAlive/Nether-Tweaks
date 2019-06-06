package mod.nethertweaks.blocks.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.container.ContainerNetherrackFurnace;
import mod.nethertweaks.blocks.tile.TileNetherrackFurnace;
import mod.sfhcore.Constants;
import mod.sfhcore.blocks.container.ContainerBase;

public class GuiNetherrackFurnace extends GuiContainer
{
	private int xSize, ySize;
	private final ResourceLocation backgroundimage = new ResourceLocation(NetherTweaksMod.MODID, "textures/gui/guinetherrackfurnace.png");
	private TileNetherrackFurnace entity;
	
	public GuiNetherrackFurnace(InventoryPlayer inventory, TileNetherrackFurnace te) {
        super(new ContainerNetherrackFurnace(inventory, te));
        entity = te;
        xSize = 175;
        ySize = 165;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
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
        
        if(this.entity.isWorking(this.entity)){
        	int k = this.entity.getWorkTimeRemainingScaled(12);
        	x += 57;
        	y += 36;
        	int k_inv = 12 - k;
        	drawTexturedModalRect(x, y + k_inv, 176, k_inv, 14, k + 2);
        }
        
    }
    
    @Override
    public boolean doesGuiPauseGame() {
    	return false;
    }

}
