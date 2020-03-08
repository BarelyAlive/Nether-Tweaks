package mod.nethertweaks.block.gui;

import java.util.Objects;

import org.lwjgl.opengl.GL11;

import mod.nethertweaks.Constants;
import mod.nethertweaks.block.container.ContainerCondenser;
import mod.nethertweaks.block.tile.TileCondenser;
import mod.nethertweaks.config.Config;
import mod.sfhcore.block.tile.TileInventory;
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
public class GuiCondenser extends GuiContainer
{
	private final int xSize;
    private final int ySize;
	private final ResourceLocation backgroundimage = new ResourceLocation(Constants.MOD_ID + ":textures/gui/guicondenser.png");
	private TileCondenser entity;

	public GuiCondenser(final InventoryPlayer inventoryPlayer, final TileCondenser tileEntity) {
		super(new ContainerCondenser(inventoryPlayer, tileEntity));
		entity = tileEntity;
		xSize = 176;
		ySize = 166;
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks)
	{
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY)
	{
		getTE();

		//Fluid
		String fName = "---";
		final FluidStack f = entity.getTank().getFluid();
		if(f != null) fName = f.getLocalizedName() + " : " + entity.getTank().getFluidAmount() + " mB";
		else fName = fName +" : 0 mB";
		final int lenght = fontRenderer.getStringWidth(fName);
		final int x = 174 - lenght;
		fontRenderer.drawStringWithShadow(fName, x, 73, 0xffffff);

		//Temperature in Celsius/Fahrenheit
		final String celsius = " °C";
		final String fahrenheit = " °F";
		String text;

		final double temp = Math.round(10.0 * entity.getTemp()) / 10.0;

		if(Config.useMetricSystem)
			text = temp + celsius;
		else
			text = temp * 1.8f + 32 + fahrenheit;

		int lenght1 = fontRenderer.getStringWidth(text);
		lenght1 /= 2;
		final int x1 = 35 - lenght1;
		fontRenderer.drawStringWithShadow(text, x1, 35, 0xffffff);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 10.F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(backgroundimage);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		getTE();

		final int x_old = x;
		final int y_old = y;
		if(TileInventory.isWorking(entity)){
			final int k = entity.getWorkTimeRemainingScaled(14);
			x += 28;
			y += 18;
			drawTexturedModalRect(x, y, 176, 0, 16, k);
		}

		x = x_old;
		y = y_old;

		if(entity.getCompostMeter() > 0)
		{
			final int k = (int) (entity.getCompostMeter() * 64 / entity.getMaxCompost());
			x += 155;
			y += 6;
			final int k_inv = 64 - k;

			drawTexturedModalRect(x, y + k_inv, 176, 14, 16, k);
		}

		x = x_old;
		y = y_old;

		if(entity.getTank().getFluidAmount() != 0)
		{
			final int k = entity.getTank().getFluidAmount() * 64 / entity.getTank().getCapacity();
			x += 134;
			y += 6;
			final int k_inv = 64 - k;

			GL11.glPushMatrix();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

			final FluidStack fluid = entity.getTank().getFluid();

			final TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(Objects.requireNonNull(fluid).getFluid().getStill().toString());
			ResourceLocation res = Objects.requireNonNull(entity.getTank().getFluid()).getFluid().getStill(entity.getTank().getFluid());
			res = new ResourceLocation(res.getResourceDomain(), "textures/" + res.getResourcePath() + ".png");
			Minecraft.getMinecraft().getTextureManager().bindTexture(res);

			for (int i = 0; i < 16; i++)
			{
				final int frame = (int) ( System.currentTimeMillis() / (6400 / sprite.getFrameCount())) % (sprite.getFrameCount() + 1);
				drawTexturedModalRect(x + i, y + k_inv, i* sprite.getIconHeight(), frame * sprite.getIconHeight() , 1, k);
			}
			GL11.glPopMatrix();
		}

		x = x_old;
		y = y_old;
	}

	private void getTE()
	{
		entity = (TileCondenser) mc.player.world.getTileEntity(entity.getPos());
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
